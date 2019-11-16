package move;

import board.Bitboard;
import board.Parser;
import board.Pieces;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.*;
import static java.lang.Math.pow;
import static java.lang.System.nanoTime;

public class MoveSelector {


    private static String bestMove = "";
    ArrayList<String> bestMoves = new ArrayList<String>();

    private static int MAX_VALUE = Integer.MAX_VALUE;
    private static int MIN_VALUE = Integer.MIN_VALUE;
    private long[][] zobristTable = new long[56][6];
    public long zobristHash = 0L;
    private HashMap<Long, TTEntry> TTable = new HashMap<>();
    String FENString;
    static Bitboard currentBitboard = new Bitboard();
    Bitboard testBitboard = new Bitboard();
    int nodeCounter = 0;
    MoveGenerator generator = new MoveGenerator();


    int paramWall;
    int paramTower;
    int paramCatapult;
    boolean valueefun2;



    /**
     * TODO: Walls in den Ecken schlecht bewerten, Walls in 2. Reihe schlecht bewerten
     */


    // ---------------------------------------------------- Heatmaps ---------------------------------------------------
    /**
     * Romans: Wall
     */
    public List<Integer> heatmapRWall = Arrays.asList(
            -10, -1, -1, -1, -1, -1, -1, -10,
            -1, 0, 0, 0, 0, 0, 0, -1,
            -1, 0, 0, 0, 0, 0, 0, -1,
            -1, 1, 1, 1, 1, 1, 1, -1,
            -1, 0, 0, 0, 0, 0, 0, -1,
            -1, 0, 1, 1, 1, 1, 0, -1,
            5000,5000,5000,5000,5000,5000,5000,5000);



    /**
     * Romans: Tower
     */
    public  List<Integer> heatmapRTower = Arrays.asList(
            -1, -1, -1, -1, -1, -1, -1, -1,
            10, 25, 25, 20, 20, 25, 25, 10,
            30, 35, 50, 50, 50, 50, 35, 30,
            35, 35, 35, 100, 100, 35, 35, 35,
            50, 50, 50, 150, 150, 50, 50, 50,
            0, 0, 0, 0, 0, 0, 0, 0,
            5000,5000,5000,5000,5000,5000,5000,5000);


    /**
     * Romans: Catapult
     */
    public List<Integer> heatmapRCatapult = Arrays.asList(
            -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1,
            -1, 0, 35, 35, 35, 35, 0, -1,
            -1, 0, 35, 150, 150, 35, 0, -1,
            -1, 0, 0, 0, 0, 0, 0, -1,
            -1, 0, 0, 0, 0, 0, 0, -1,
            5000,5000,5000,5000,5000,5000,5000,5000);



    // ----------------------------------------------------------------------------------------------------------

    /**
     * Gaul: Walls
     */

    public  List<Integer> heatmapGWall = Arrays.asList(
            5000,5000,5000,5000,5000,5000,5000,5000,
            -1, 0, 1, 1, 1, 1, 0, -1,
            -1, 0, 0, 0, 0, 0, 0, -1,
            -1, 1, 1, 1, 1, 1, 1, -1,
            -1, 0, 0, 0, 0, 0, 0, -1,
            -1, 0, 0, 0, 0, 0, 0, -1,
            -10, -1, -1, -1, -1, -1, -1, -10);


    /**
     * Gaul: Tower
     */
    public  List<Integer> heatmapGTower = Arrays.asList(
            5000,5000,5000,5000,5000,5000,5000,5000,
            0, 0, 0, 0, 0, 0, 0, 0,
            50, 50, 50, 150, 150, 50, 50, 50,
            35, 35, 35, 100, 100, 35, 35, 35,
            30, 35, 50, 50, 50, 50, 35, 30,
            10, 25, 25, 20, 20, 25, 25, 10,
            -1, -1, -1, -1, -1, -1, -1, -1
    );


    /**
     * Gaul: Catapult
     */
    public  List<Integer> heatmapGCatapult = Arrays.asList(
            5000,5000,5000,5000,5000,5000,5000,5000,
            -1, 0, 0, 0, 0, 0, 0, -1,
            -1, 0, 0, 0, 0, 0, 0, -1,
            -1, 0, 35, 150, 150, 35, 0, -1,
            -1, 0, 35, 35, 35, 35, 0, -1,
            -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1);


    // ----------------------------------------------------------------------------------------------------

    public MoveSelector(){
        initZobrist();
        zobristHash = hash(FENString);
    }

    public MoveSelector(String state){
        FENString = state;
        initZobrist();
        zobristHash = hash(FENString);
    }

    //todo
    public MoveSelector(List<Integer> parameters){
        parseParam(parameters);
        initZobrist();
        zobristHash = hash(FENString);
    }

    // For Figure Parameter
    public MoveSelector(int paramWall, int paramTower, int paramCatapult, boolean valuefunc2){
        this.paramWall = paramWall;
        this.paramTower = paramTower;
        this.paramCatapult = paramCatapult;
        this.valueefun2 = valuefunc2;
        initZobrist();
        zobristHash = hash(FENString);
    }



    //todo
    public void parseParam(List<Integer> parameters){

    }

    /**
     * Main Method for Testing & Time Measurement
     * @param
     */



    public void measureTime(String FENString, int depth) {
        int cumNodes = 0;
        for (int i = 1; i <= depth; i++){
            long startTime = nanoTime();
            String bestMove = getMoveTT(FENString,i);
            long endTime = nanoTime();
            System.out.print(bestMove + " is selected move within "
                    + (endTime - startTime)/pow(10,6)+" ms for depth: " + i + " and " + (nodeCounter-cumNodes) +" generated Nodes\n");
            //cumNodes += nodeCounter - cumNodes;
        }
    }


    /**
     * Checks if Romnas have Won
     * Criteria:
     *          - Roman Wall on row 1
     *          - No movable Piece
     * @return
     */
    public boolean romansHaveWon() {
        ArrayList<Integer> catG = testBitboard.getAllPiecesIndex(Pieces.CATAPULT_GAULS);
        ArrayList<Integer> towerG = testBitboard.getAllPiecesIndex(Pieces.TOWER_GAULS);
        return (testBitboard.getBitboard(Pieces.WALL_ROMANS) & 71776119061217280L) != 0 || (catG.isEmpty() && towerG.isEmpty() && (testBitboard.getBitboard(Pieces.WALL_GAULS) & 255L) == 0);
    }


    /**
     * Checks if Gauls have Won
     * Criteria:
     *          - Gaulish Wall on row 1
     *          - No movable Piece
     * @return
     */
    public boolean gaulsHaveWon() {
        ArrayList<Integer> catR = testBitboard.getAllPiecesIndex(Pieces.CATAPULT_ROMANS);
        ArrayList<Integer> towerR = testBitboard.getAllPiecesIndex(Pieces.TOWER_ROMANS);
        return (testBitboard.getBitboard(Pieces.WALL_GAULS) & 255L) != 0 || (catR.isEmpty() && towerR.isEmpty() && (testBitboard.getBitboard(Pieces.WALL_ROMANS) & 71776119061217280L) == 0);
    }



    /**
     * GetMove with TranspositionTables
     * @param state
     * @param maxDepth
     * @return
     */
    public String getMoveTT(String state, int maxDepth){
        this.FENString = state;
        currentBitboard = Parser.fenToBoard(FENString);
        initZobrist();
        zobristHash = hash(state);
        long localHash = zobristHash;
        int color = FENString.substring(FENString.length() - 1).equals("r")? 1 : -1;
        ArrayList<String> allMoves = generator.calculateAllMoves(state);

        int maxV = MIN_VALUE;
        String maxM = "Can't Move, REEEEEEE";
        for (int depth = 1; depth <= maxDepth; depth++){
            //System.out.print("DEPTH: "+depth+"\n");
            nodeCounter = 0;
            HashMap<String, Integer> movesValue = new HashMap<String, Integer>();
            ArrayList<String> newSortedMoves = new ArrayList<String>();

            for (String move: allMoves) {
                //System.out.print("GETMOVE: "+move+"\n");
                nodeCounter += 1;
                //int curr = -negaMaxAB(createNewState(state, move, color), depth - 1, MIN_VALUE+1, MAX_VALUE, -color);
                int curr = -negaScoutTT(createNewState(state, move, color, localHash), depth - 1, MIN_VALUE+1, MAX_VALUE, -color);
                //int curr = -negaMax(createNewState(state, move, color), depth - 1, -color);
                //System.out.print("Move: " + move + ", Value: " + curr + "\n");
                if (curr > maxV){
                    //Auskommentieren für ohne cutoffs testen
                    if (curr == MAX_VALUE){
                        return move;
                    }
                    maxV = curr;
                    maxM = move;

                    movesValue.put(move,curr);
                    newSortedMoves.add(0,move);
                }

                if(!newSortedMoves.contains(move)){
                    int indexToadd = 0;
                    movesValue.put(move,curr);
                    for (String sortedMove : newSortedMoves){
                        if(curr > movesValue.get(sortedMove)){
                            indexToadd = newSortedMoves.indexOf(sortedMove);
                            break;
                        }
                    }
                    if(indexToadd != 0){
                        newSortedMoves.add(indexToadd,move);
                    }

                    else {
                        newSortedMoves.add(move);
                    }
                }
            }
            /**
             * Comment to remove Zugsortierung
             */

            allMoves = newSortedMoves;

            //TODO what if allMoves is empty?
            if (!allMoves.isEmpty()){
                bestMove = allMoves.get(0);
            }
        }
        //System.out.println("SortedMoves: "+allMoves);
        //System.out.println("BestMove: "+maxM);
        return maxM;
    }


    /**
     * Find the best next move given a fenString and the maximum Search Depth
     * @param state
     * @param maxDepth
     * @return
     */
    public String getMove(String state, int maxDepth){
        this.FENString = state;
        currentBitboard = Parser.fenToBoard(FENString);

        int color = FENString.substring(FENString.length() - 1).equals("r")? 1 : -1;
        ArrayList<String> allMoves = generator.calculateAllMoves(state);

        int maxV = MIN_VALUE;
        String maxM = "Can't Move, REEEEEEE";
        for (int depth = 1; depth <= maxDepth; depth++){
            //System.out.print("DEPTH: "+depth+"\n");
            nodeCounter = 0;

            HashMap<String, Integer> movesValue = new HashMap<String, Integer>();
            ArrayList<String> newSortedMoves = new ArrayList<String>();

            for (String move: allMoves) {
                //System.out.print("GETMOVE: "+move+"\n");
                nodeCounter += 1;
                //int curr = -negaMaxAB(createNewState(state, move, color, zobristHash), depth - 1, MIN_VALUE+1, MAX_VALUE, -color);
                int curr = -negaScout(createNewState(state, move, color, zobristHash), depth - 1, MIN_VALUE+1, MAX_VALUE, -color);
                //int curr = -negaMax(createNewState(state, move, color), depth - 1, -color);
                //System.out.print("Move: " + move + ", Value: " + curr + "\n");
                if (curr > maxV){

                    /**
                     * Auskommentieren für ohne cutoffs testen
                     */
                    if (curr == MAX_VALUE){
                        return move;
                    }
                    maxV = curr;
                    maxM = move;
                    movesValue.put(move,curr);
                    newSortedMoves.add(0,move);
                }

                if(!newSortedMoves.contains(move)){
                    int indexToadd = 0;
                    movesValue.put(move,curr);
                    for (String sortedMove : newSortedMoves){
                        if(curr > movesValue.get(sortedMove)){
                            indexToadd = newSortedMoves.indexOf(sortedMove);
                            break;
                        }
                    }
                    if(indexToadd != 0){
                        newSortedMoves.add(indexToadd,move);
                    }
                    else {
                        newSortedMoves.add(move);
                    }
                }
            }

            /**
             * Comment to remove Zugsortierung
             */
            allMoves = newSortedMoves;

            if (!allMoves.isEmpty()){
                bestMove = allMoves.get(0);
            }
        }
        return maxM;
    }




    /**
     * NegaMax Algorithmen
     * @param state
     * @param depth
     * @param colorFlag
     * @return
     */
    public int negaMax(String state, int depth, int colorFlag){
        testBitboard = Parser.fenToBoard(state);
        if (romansHaveWon()){
            return colorFlag * MAX_VALUE ;
        }
        if (gaulsHaveWon()){
            return colorFlag * (MIN_VALUE+1);
        }
        if (depth == 0){
            if(valueefun2)
                return colorFlag * valueFunction(valueefun2,state);
            else
                return colorFlag * valueFunction(state);
        }

        int value = MIN_VALUE+1;

        for(String move : generator.calculateAllMoves(state)){
            nodeCounter += 1;
            value = max(value, -negaMax(createNewState(state, move, colorFlag, zobristHash), depth - 1, -colorFlag));
        }
        return value;
    }



    /**
     * NegaScout Alogrithmn
     * @param state
     * @param depth
     * @param alpha
     * @param beta
     * @param color
     * @return
     */
    public int negaScout(String state, int depth, int alpha, int beta, int color){
        testBitboard = Parser.fenToBoard(state);
        if (romansHaveWon()){
            return color * MAX_VALUE ;
        }
        if (gaulsHaveWon()){
            return color * (MIN_VALUE+1);
        }
        if (depth == 0){
            if(valueefun2)
                return color * valueFunction(valueefun2,state);
            else
                return color * valueFunction(state);
        }

        int value = MIN_VALUE+1;
        //TODO SortMoves to cutoff more efficiently
        for(String move :  generator.calculateAllMoves(state)){
            nodeCounter += 1;
            //if(!bestMoves.contains(move)){
            if(!move.equals(bestMove)){
                //Null Window
                value = max(value, -negaScout(createNewState(state, move, color,zobristHash), depth - 1, -alpha-1, -alpha, -color));
                if (value == MAX_VALUE){
                    return value;
                }
                if (alpha < value && value < beta){
                    value = max(value, -negaScout(createNewState(state, move, color,zobristHash), depth - 1, -beta, -value, -color));
                    if (value == MAX_VALUE){
                        return value;
                    }
                }
            }
            else {
                value = max(value, -negaScout(createNewState(state, move, color,zobristHash), depth - 1, -beta, -alpha, -color));
                if (value == MAX_VALUE){
                    return value;
                }
            }
            alpha = max(alpha, value);
            if (alpha >= beta)
                break;
        }
        return value;
    }


    /**
     * NegaMax Algorithmn with Alpha-Beta Cutoffs
     * @param state
     * @param depth
     * @param alpha
     * @param beta
     * @param color
     * @return
     */
    public int negaMaxAB(String state, int depth, int alpha, int beta, int color){
        testBitboard = Parser.fenToBoard(state);
        if (romansHaveWon()){
            return color * MAX_VALUE ;
        }
        if (gaulsHaveWon()){
            return color * (MIN_VALUE+1);
        }
        if (depth == 0){
            if(valueefun2)
                return color * valueFunction(valueefun2,state);
            else
                return color * valueFunction(state);
        }

        int value = MIN_VALUE+1;
        //TODO SortMoves to cutoff more efficiently
        for(String move :  generator.calculateAllMoves(state)){
            nodeCounter += 1;
            value = max(value, -negaMaxAB(createNewState(state, move, color,zobristHash), depth - 1, -beta, -alpha, -color));
            if (value == MAX_VALUE){
                return value;
            }
            alpha = max(alpha, value);
            if (alpha >= beta)
                break;
        }
        return value;
    }



    /**
     * NegaScout with Transposition tables
     * @param state
     * @param depth
     * @param alpha
     * @param beta
     * @param color
     * @return
     */
    public int negaScoutTT(String state, int depth, int alpha, int beta, int color){
        testBitboard = Parser.fenToBoard(state);
        long localHash = zobristHash;
        int alphaOrig = alpha;
        TTEntry ttEntry = TTable.get(localHash);
        //New state
        if (ttEntry == null){
            ttEntry = new TTEntry();
        }
        //Found same state with worse information
        else if(ttEntry.depth >= depth){
            nodeCounter--;
            switch (ttEntry.flag) {
                case "exact":
                    return ttEntry.value;
                case "lower":
                    alpha = max(alpha, ttEntry.value);
                    break;
                case "upper":
                    beta = min(beta, ttEntry.value);
                    break;
            }
            if (alpha >= beta){
                return ttEntry.value;
            }
        }
        if (romansHaveWon()){
            return color * MAX_VALUE ;
        }
        if (gaulsHaveWon()){
            return color * (MIN_VALUE+1);
        }
        if (depth == 0){
            int value = 0;
            if(valueefun2){
                value = color * valueFunction(valueefun2,state);
            } else {
                value = color * valueFunction(state);
            }
            ttEntry.value = value;
            if (value <= alphaOrig){
                ttEntry.flag = "upper";
            }
            else if (value >= beta){
                ttEntry.flag = "lower";
            }
            else {
                ttEntry.flag = "exact";
            }
            ttEntry.depth = depth;
            TTable.put(localHash, ttEntry);
            return value;
        }

        int value = MIN_VALUE+1;
        //TODO SortMoves to cutoff more efficiently
        for(String move :  generator.calculateAllMoves(state)){
            nodeCounter += 1;
            //if(!bestMoves.contains(move)){
            if(!move.equals(bestMove)){
                //Null Window
                value = max(value, -negaScoutTT(createNewState(state, move, color,localHash), depth - 1, -alpha-1, -alpha, -color));
                if (value == MAX_VALUE){
                    return value;
                }
                if (alpha < value && value < beta){
                    value = max(value, -negaScoutTT(createNewState(state, move, color,localHash), depth - 1, -beta, -value, -color));
                    if (value == MAX_VALUE){
                        return value;
                    }
                }
            }
            else {
                value = max(value, -negaScoutTT(createNewState(state, move, color,localHash), depth - 1, -beta, -alpha, -color));
                if (value == MAX_VALUE){
                    return value;
                }
            }
            alpha = max(alpha, value);
            if (alpha >= beta)
                break;
        }
        //System.out.println(value);
        ttEntry.value = value;
        if (value <= alphaOrig){
            ttEntry.flag = "upper";
        }
        else if (value >= beta){
            ttEntry.flag = "lower";
        }
        else {
            ttEntry.flag = "exact";
        }
        ttEntry.depth = depth;
        TTable.put(localHash, ttEntry);
        return value;
    }



    /**
     * NegaMax with Transposition Tables
     * @param state
     * @param depth
     * @param alpha
     * @param beta
     * @param color
     * @return
     */
    public int negaMaxTT(String state, int depth, int alpha, int beta, int color){
        testBitboard = Parser.fenToBoard(state);
        long localHash = zobristHash;
        int alphaOrig = alpha;
        TTEntry ttEntry = TTable.get(localHash);
        if (ttEntry == null){
            ttEntry = new TTEntry();
        }
        else if(ttEntry.depth >= depth){
            switch (ttEntry.flag) {
                case "exact":
                    return ttEntry.value;
                case "lower":
                    alpha = max(alpha, ttEntry.value);
                    break;
                case "upper":
                    beta = min(beta, ttEntry.value);
                    break;
            }
            if (alpha >= beta){
                return ttEntry.value;
            }
        }
        if (romansHaveWon()){
            return color * MAX_VALUE ;
        }
        if (gaulsHaveWon()){
            return color * (MIN_VALUE+1);
        }
        if (depth == 0){
            int value = 0;
            if(valueefun2)
                value = color * valueFunction(valueefun2,state);
            else
                value = color * valueFunction(state);
            ttEntry.value = value;
            if (value <= alphaOrig){
                ttEntry.flag = "upper";
            }
            else if (value >= beta){
                ttEntry.flag = "lower";
            }
            else {
                ttEntry.flag = "exact";
            }
            ttEntry.depth = depth;
            TTable.put(localHash, ttEntry);
            return value;
        }

        int value = MIN_VALUE+1;
        //TODO SortMoves to cutoff more efficiently
        for(String move :  generator.calculateAllMoves(state)){
            nodeCounter += 1;
            value = max(value, -negaMaxTT(createNewState(state, move, color, localHash), depth - 1, -beta, -alpha, -color));
            if (value == MAX_VALUE){
                return value;
            }
            alpha = max(alpha, value);
            if (alpha >= beta)
                break;
        }


        ttEntry.value = value;
        if (value <= alphaOrig){
            ttEntry.flag = "upper";
        }
        else if (value >= beta){
            ttEntry.flag = "lower";
        }
        else {
            ttEntry.flag = "exact";
        }
        ttEntry.depth = depth;
        TTable.put(localHash, ttEntry);
        return value;
    }

    // With state and move creates next state
    // Also updates zobrist hash
    public String createNewState(String state, String move, int color, long zobristHash) {
        testBitboard = Parser.fenToBoard(state);
        MoveGenerator generator = new MoveGenerator();
        int departure = generator.moveToIndex(move.substring(0,2));
        int arrival = generator.moveToIndex(move.substring(3,5));
        int testInt = abs(departure - arrival);
        int centerInd = (departure + arrival) / 2;
        long center = (long) pow(2, centerInd);
        long departureL = (long) pow(2, departure);
        long arrivalL = (long) pow(2, arrival);
        long wallsR = testBitboard.getBitboard(Pieces.WALL_ROMANS);
        long towersR = testBitboard.getBitboard(Pieces.TOWER_ROMANS);
        long catapultsR = testBitboard.getBitboard(Pieces.CATAPULT_ROMANS);
        long wallsG = testBitboard.getBitboard(Pieces.WALL_GAULS);
        long towersG = testBitboard.getBitboard(Pieces.TOWER_GAULS);
        long catapultsG = testBitboard.getBitboard(Pieces.CATAPULT_GAULS);
        //System.out.println(center);

        if (move.length() == 5){
            if (color == 1){
                // Tower
                if ((testBitboard.getBitboard(Pieces.CATAPULT_ROMANS) & departureL) == 0){
                    //Sacrifice
                    if ((testInt & 1) != 0 || testInt == 8){
                        testBitboard.setBitboardByIndex(Pieces.TOWER_ROMANS, towersR - departureL);
                        testBitboard.setBitboardByIndex(Pieces.WALL_ROMANS, wallsR + departureL);
                        testBitboard.setBitboardByIndex(Pieces.WALL_GAULS, wallsG - arrivalL);
                        zobristHash ^= zobristTable[departure][Pieces.TOWER_ROMANS] ^ zobristTable[departure][Pieces.WALL_ROMANS] ^ zobristTable[arrival][Pieces.WALL_GAULS];
                    }
                    // Standard Move
                    else{
                        zobristHash ^= zobristTable[departure][Pieces.TOWER_ROMANS];
                        if (testBitboard.isPieceAtPos(Pieces.WALL_ROMANS, center)){
                            testBitboard.setBitboardByIndex(Pieces.TOWER_ROMANS, towersR + center);
                            testBitboard.setBitboardByIndex(Pieces.WALL_ROMANS, wallsR - center);
                            zobristHash ^= zobristTable[centerInd][Pieces.TOWER_ROMANS];
                            zobristHash ^= zobristTable[(centerInd)][Pieces.WALL_ROMANS];
                        }
                        else if (testBitboard.isPieceAtPos(Pieces.TOWER_ROMANS, center)){
                            testBitboard.setBitboardByIndex(Pieces.TOWER_ROMANS, towersR - center);
                            testBitboard.setBitboardByIndex(Pieces.CATAPULT_ROMANS, catapultsR + center);
                            zobristHash ^= zobristTable[(centerInd)][Pieces.TOWER_ROMANS];
                            zobristHash ^= zobristTable[(centerInd)][Pieces.CATAPULT_ROMANS];
                        }
                        else {
                            testBitboard.setBitboardByIndex(Pieces.WALL_ROMANS, wallsR + center);
                            zobristHash ^= zobristTable[centerInd][Pieces.WALL_ROMANS];
                        }
                        if (testBitboard.isPieceAtPos(Pieces.WALL_ROMANS, arrivalL)){
                            testBitboard.setBitboardByIndex(Pieces.TOWER_ROMANS, testBitboard.getBitboard(Pieces.TOWER_ROMANS) + arrivalL);
                            testBitboard.setBitboardByIndex(Pieces.WALL_ROMANS, testBitboard.getBitboard(Pieces.WALL_ROMANS) - arrivalL);
                            zobristHash ^= zobristTable[arrival][Pieces.TOWER_ROMANS];
                            zobristHash ^= zobristTable[arrival][Pieces.WALL_ROMANS];
                        }
                        else if (testBitboard.isPieceAtPos(Pieces.TOWER_ROMANS, arrivalL)){
                            testBitboard.setBitboardByIndex(Pieces.TOWER_ROMANS, testBitboard.getBitboard(Pieces.TOWER_ROMANS) - arrivalL);
                            testBitboard.setBitboardByIndex(Pieces.CATAPULT_ROMANS, testBitboard.getBitboard(Pieces.CATAPULT_ROMANS) + arrivalL);
                            zobristHash ^= zobristTable[arrival][Pieces.TOWER_ROMANS];
                            zobristHash ^= zobristTable[arrival][Pieces.CATAPULT_ROMANS];
                        }
                        else {
                            testBitboard.setBitboardByIndex(Pieces.WALL_ROMANS, testBitboard.getBitboard(Pieces.WALL_ROMANS) + arrivalL);
                            zobristHash ^= zobristTable[arrival][Pieces.WALL_ROMANS];
                        }
                        testBitboard.setBitboardByIndex(Pieces.TOWER_ROMANS, testBitboard.getBitboard(Pieces.TOWER_ROMANS) - departureL);
                    }
                }
                else {
                    // Catapult Throw
                    testBitboard.setBitboardByIndex(Pieces.CATAPULT_ROMANS, catapultsR - departureL);
                    testBitboard.setBitboardByIndex(Pieces.TOWER_ROMANS, towersR + departureL);
                    if ((testBitboard.getBitboard(Pieces.WALL_GAULS) & arrivalL ) != 0){
                        testBitboard.setBitboardByIndex(Pieces.WALL_GAULS, wallsG - arrivalL);
                        zobristHash ^= zobristTable[departure][Pieces.CATAPULT_ROMANS] ^ zobristTable[departure][Pieces.TOWER_ROMANS] ^ zobristTable[arrival][Pieces.WALL_GAULS];
                    }
                    else if ((testBitboard.getBitboard(Pieces.TOWER_GAULS) & arrivalL ) != 0){
                        testBitboard.setBitboardByIndex(Pieces.TOWER_GAULS, towersG - arrivalL);
                        testBitboard.setBitboardByIndex(Pieces.WALL_GAULS, wallsG + arrivalL);
                        zobristHash ^= zobristTable[departure][Pieces.CATAPULT_ROMANS] ^ zobristTable[departure][Pieces.TOWER_ROMANS] ^ zobristTable[arrival][Pieces.WALL_GAULS] ^ zobristTable[arrival][Pieces.TOWER_GAULS];
                    }
                    else if ((testBitboard.getBitboard(Pieces.CATAPULT_GAULS) & arrivalL ) != 0){
                        testBitboard.setBitboardByIndex(Pieces.CATAPULT_GAULS, catapultsG - arrivalL);
                        testBitboard.setBitboardByIndex(Pieces.TOWER_GAULS, towersG + arrivalL);
                        zobristHash ^= zobristTable[departure][Pieces.CATAPULT_ROMANS] ^ zobristTable[departure][Pieces.TOWER_ROMANS] ^ zobristTable[arrival][Pieces.CATAPULT_GAULS] ^ zobristTable[arrival][Pieces.TOWER_GAULS];
                    }
                    else {
                        testBitboard.setBitboardByIndex(Pieces.WALL_ROMANS, wallsR + arrivalL);
                        zobristHash ^= zobristTable[departure][Pieces.CATAPULT_ROMANS] ^ zobristTable[departure][Pieces.TOWER_ROMANS] ^ zobristTable[arrival][Pieces.WALL_ROMANS];
                    }
                }

            }
            else {
                // Tower
                if ((testBitboard.getBitboard(Pieces.CATAPULT_GAULS) & departureL) == 0){
                    //Sacrifice
                    if ((testInt & 1) != 0 || testInt == 8){
                        testBitboard.setBitboardByIndex(Pieces.TOWER_GAULS, towersG - departureL);
                        testBitboard.setBitboardByIndex(Pieces.WALL_GAULS, wallsG + departureL);
                        testBitboard.setBitboardByIndex(Pieces.WALL_ROMANS, wallsR - arrivalL);
                        zobristHash ^= zobristTable[departure][Pieces.TOWER_GAULS] ^ zobristTable[departure][Pieces.WALL_GAULS] ^ zobristTable[arrival][Pieces.WALL_ROMANS];
                    }
                    //Standard Move
                    else{
                        //towerMoveGauls(departure, arrival);
                        zobristHash ^= zobristTable[departure][Pieces.TOWER_GAULS];
                        if (testBitboard.isPieceAtPos(Pieces.WALL_GAULS, center)){
                            testBitboard.setBitboardByIndex(Pieces.TOWER_GAULS, towersG + center);
                            testBitboard.setBitboardByIndex(Pieces.WALL_GAULS, wallsG - center);
                            zobristHash ^= zobristTable[centerInd][Pieces.TOWER_GAULS];
                            zobristHash ^= zobristTable[(centerInd)][Pieces.WALL_GAULS];
                        }
                        else if (testBitboard.isPieceAtPos(Pieces.TOWER_GAULS, center)){
                            testBitboard.setBitboardByIndex(Pieces.TOWER_GAULS, towersG - center);
                            testBitboard.setBitboardByIndex(Pieces.CATAPULT_GAULS, catapultsG + center);
                            zobristHash ^= zobristTable[(centerInd)][Pieces.TOWER_GAULS];
                            zobristHash ^= zobristTable[(centerInd)][Pieces.CATAPULT_GAULS];
                        }
                        else {
                            testBitboard.setBitboardByIndex(Pieces.WALL_GAULS, wallsG + center);
                            zobristHash ^= zobristTable[centerInd][Pieces.WALL_GAULS];
                        }
                        if (testBitboard.isPieceAtPos(Pieces.WALL_GAULS, arrivalL)){
                            testBitboard.setBitboardByIndex(Pieces.TOWER_GAULS, testBitboard.getBitboard(Pieces.TOWER_GAULS) + arrivalL);
                            testBitboard.setBitboardByIndex(Pieces.WALL_GAULS, testBitboard.getBitboard(Pieces.WALL_GAULS) - arrivalL);
                            zobristHash ^= zobristTable[arrival][Pieces.TOWER_GAULS];
                            zobristHash ^= zobristTable[arrival][Pieces.WALL_GAULS];
                        }
                        else if (testBitboard.isPieceAtPos(Pieces.TOWER_GAULS, arrivalL)){
                            testBitboard.setBitboardByIndex(Pieces.TOWER_GAULS, testBitboard.getBitboard(Pieces.TOWER_GAULS) - arrivalL);
                            testBitboard.setBitboardByIndex(Pieces.CATAPULT_GAULS, testBitboard.getBitboard(Pieces.CATAPULT_GAULS) + arrivalL);
                            zobristHash ^= zobristTable[arrival][Pieces.TOWER_GAULS];
                            zobristHash ^= zobristTable[arrival][Pieces.CATAPULT_GAULS];
                        }
                        else {
                            testBitboard.setBitboardByIndex(Pieces.WALL_GAULS, testBitboard.getBitboard(Pieces.WALL_GAULS) + arrivalL);
                            zobristHash ^= zobristTable[arrival][Pieces.WALL_GAULS];
                        }
                        testBitboard.setBitboardByIndex(Pieces.TOWER_GAULS, testBitboard.getBitboard(Pieces.TOWER_GAULS) - departureL);
                    }
                }
                else {
                    // Catapult Throw
                    testBitboard.setBitboardByIndex(Pieces.CATAPULT_GAULS, catapultsG - departureL);
                    testBitboard.setBitboardByIndex(Pieces.TOWER_GAULS, towersG + departureL);
                    if ((testBitboard.getBitboard(Pieces.WALL_ROMANS) & arrivalL ) != 0){
                        testBitboard.setBitboardByIndex(Pieces.WALL_ROMANS, wallsR - arrivalL);
                        zobristHash ^= zobristTable[departure][Pieces.CATAPULT_GAULS] ^ zobristTable[departure][Pieces.TOWER_GAULS] ^ zobristTable[arrival][Pieces.WALL_ROMANS];
                    }
                    else if ((testBitboard.getBitboard(Pieces.TOWER_ROMANS) & arrivalL ) != 0){
                        testBitboard.setBitboardByIndex(Pieces.TOWER_ROMANS, towersR - arrivalL);
                        testBitboard.setBitboardByIndex(Pieces.WALL_ROMANS, wallsR + arrivalL);
                        zobristHash ^= zobristTable[departure][Pieces.CATAPULT_GAULS] ^ zobristTable[departure][Pieces.TOWER_GAULS] ^ zobristTable[arrival][Pieces.TOWER_ROMANS] ^ zobristTable[arrival][Pieces.WALL_ROMANS];
                    }
                    else if ((testBitboard.getBitboard(Pieces.CATAPULT_ROMANS) & arrivalL ) != 0){
                        testBitboard.setBitboardByIndex(Pieces.CATAPULT_ROMANS, catapultsR - arrivalL);
                        testBitboard.setBitboardByIndex(Pieces.TOWER_ROMANS, towersR + arrivalL);
                        zobristHash ^= zobristTable[departure][Pieces.CATAPULT_GAULS] ^ zobristTable[departure][Pieces.TOWER_GAULS] ^ zobristTable[arrival][Pieces.CATAPULT_ROMANS] ^ zobristTable[arrival][Pieces.TOWER_ROMANS];
                    }
                    else {
                        testBitboard.setBitboardByIndex(Pieces.WALL_GAULS, wallsG + arrivalL);
                        zobristHash ^= zobristTable[departure][Pieces.CATAPULT_GAULS] ^ zobristTable[departure][Pieces.TOWER_GAULS] ^ zobristTable[arrival][Pieces.WALL_GAULS];
                    }
                }
            }
        }
        else {
            // Sacrifice 2 pieces vs Catapult
            if (move.charAt(6) == '2') {
                if (color == 1){
                    testBitboard.setBitboardByIndex(Pieces.TOWER_ROMANS, towersR - departureL);
                    testBitboard.setBitboardByIndex(Pieces.CATAPULT_GAULS, catapultsG - arrivalL);
                    testBitboard.setBitboardByIndex(Pieces.WALL_GAULS, wallsG + arrivalL);
                    zobristHash ^= zobristTable[departure][Pieces.TOWER_ROMANS] ^ zobristTable[arrival][Pieces.CATAPULT_GAULS] ^ zobristTable[arrival][Pieces.WALL_GAULS];
                }
                else{
                    testBitboard.setBitboardByIndex(Pieces.TOWER_GAULS, towersG - departureL);
                    testBitboard.setBitboardByIndex(Pieces.CATAPULT_ROMANS, catapultsR - arrivalL);
                    testBitboard.setBitboardByIndex(Pieces.WALL_ROMANS, wallsR + arrivalL);
                    zobristHash ^= zobristTable[departure][Pieces.TOWER_GAULS] ^ zobristTable[arrival][Pieces.CATAPULT_ROMANS] ^ zobristTable[arrival][Pieces.WALL_ROMANS];
                }
            }
            //Sacrifice 1 piece vs Catapult
            else{
                if (color == 1){
                    testBitboard.setBitboardByIndex(Pieces.TOWER_ROMANS, towersR - departureL);
                    testBitboard.setBitboardByIndex(Pieces.WALL_ROMANS, wallsR + departureL);
                    testBitboard.setBitboardByIndex(Pieces.CATAPULT_GAULS, catapultsG - arrivalL);
                    testBitboard.setBitboardByIndex(Pieces.TOWER_GAULS, towersG + arrivalL);
                    zobristHash ^= zobristTable[departure][Pieces.TOWER_ROMANS] ^ zobristTable[departure][Pieces.WALL_ROMANS] ^ zobristTable[arrival][Pieces.CATAPULT_GAULS] ^ zobristTable[arrival][Pieces.TOWER_GAULS];
                }
                else{
                    testBitboard.setBitboardByIndex(Pieces.TOWER_GAULS, towersG - departureL);
                    testBitboard.setBitboardByIndex(Pieces.WALL_GAULS, wallsG + departureL);
                    testBitboard.setBitboardByIndex(Pieces.CATAPULT_ROMANS, catapultsG - arrivalL);
                    testBitboard.setBitboardByIndex(Pieces.TOWER_ROMANS, towersR + arrivalL);
                    zobristHash ^= zobristTable[departure][Pieces.WALL_GAULS] ^ zobristTable[departure][Pieces.TOWER_GAULS] ^ zobristTable[arrival][Pieces.CATAPULT_ROMANS] ^ zobristTable[arrival][Pieces.TOWER_ROMANS];
                }
            }
        }
        this.zobristHash = zobristHash;
        return Parser.boardToFen(testBitboard, !(color == 1));
    }


    /**
     * Initialises the Zobrist Table with random values
     */
    public void initZobrist(){
        for (int i = 0; i < 56; i++){
            for (int j = 0; j < 6; j++){
                zobristTable[i][j] = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
            }
        }
    }


    /**
     * Returns the hash given a fenString
     * @param state -- fenString
     * @return h -- the according hash
     */
    public long hash(String state){
        long h = 0L;
        for (int i = 0; i < 56; i++){
            if (currentBitboard.isPieceAtPos(Pieces.ALL_FIGURES, i)){
                //System.out.println(i);
                h ^= zobristTable[i][currentBitboard.whichPieceIsAtPos(i)];
            }
        }
        return h;
    }



    /**
     * Generate a new state and calculates the payoff given this move
     * Priority: Tower > Catapult > Wall
     *
     * Strategy:
     *          - Build 4 Walls on the third row in column c - f for DEFENSE
     *          - After Wall is established, don't use static heatmaps anymore
     *
     * @return
     */

    public int valueFunction(String state) {
        int sum = 0;
        int sub = 0;
        ArrayList<Integer> catapultIndiciesR = testBitboard.getAllPiecesIndex(Pieces.CATAPULT_ROMANS);
        ArrayList<Integer> towerIndiciesR = testBitboard.getAllPiecesIndex(Pieces.TOWER_ROMANS);
        ArrayList<Integer> wallIndiciesR = testBitboard.getAllPiecesIndex(Pieces.WALL_ROMANS);
        ArrayList<Integer> catapultIndiciesG = testBitboard.getAllPiecesIndex(Pieces.CATAPULT_GAULS);
        ArrayList<Integer> towerIndiciesG = testBitboard.getAllPiecesIndex(Pieces.TOWER_GAULS);
        ArrayList<Integer> wallIndiciesG = testBitboard.getAllPiecesIndex(Pieces.WALL_GAULS);
        List<Integer> middleRow = Arrays.asList(31, 30, 29, 28, 27, 26, 25, 24);

        for (Integer wallIndicy : wallIndiciesR) {
            sum += 228 * (heatmapRWall.get(wallIndicy));
        }

        for (Integer catapultIndicy : catapultIndiciesR) {
            sum +=  787 * heatmapRCatapult.get(catapultIndicy);
        }

        for (Integer towerIndicy : towerIndiciesR) {
            sum +=  583 * (heatmapRTower.get(towerIndicy));
        }

        for (Integer wallIndicy : wallIndiciesG) {
            sub += 228* (heatmapGWall.get(wallIndicy));
        }

        for (Integer catapultIndicy : catapultIndiciesG) {
            sub +=  787 * heatmapGCatapult.get(catapultIndicy);
        }

        for (Integer towerIndicy : towerIndiciesG) {
            sub +=  583 * (heatmapGTower.get(towerIndicy));
        }

        int RomanCatapults = Move.countCatapult(state, 0);
        int RomanTowers = Move.countTowers(state, 0);
        int RomanWalls = Move.countWall(state,0);
        int GaulCatapults = Move.countCatapult(state, 1);
        int GaulTowers = Move.countTowers(state, 1);
        int GaulWalls = Move.countWall(state,1);

        //Take points for Amount Towers>Walls, Catapults>Walls
        //Give/Take points for having more Towers than the enemy
        if(RomanWalls > RomanCatapults){
            sum -= (RomanWalls-RomanCatapults)*100;
        }
        if(RomanWalls > RomanTowers){
            sum -= (RomanWalls-RomanTowers)*400;
        }
        if(GaulWalls > GaulCatapults){
            sub -= (GaulWalls-GaulCatapults)*100;
        }
        if(GaulWalls > GaulTowers){
            sub -= (GaulWalls-GaulTowers)*400;
        }
        if(RomanTowers < GaulTowers){
            sum -= (GaulTowers-RomanTowers)*1000;
            sub += (GaulTowers-RomanTowers)*1000;
        }
        if(RomanTowers > GaulTowers){
            sum += (RomanTowers-GaulTowers)*1000;
            sub -= (RomanTowers-GaulTowers)*1000;
        }
        //Give points for mobility
        int posMoves = generator.calculateAllMoves(state).size()*250;

        //Middlerow dominance
        for(int middleFigure: middleRow){
            if(catapultIndiciesG.contains(middleFigure)){
                sum -= 1000;
            }
            if(catapultIndiciesR.contains(middleFigure)){
                sub -= 1000;
            }
        }
        /**
         * TODO: Uncomment for more randomness
         */
        //sum += (int)(random() * 10000000);



        return sum - sub + posMoves;
    }



    public int valueFunction(boolean testParam, String state) {

        int sum = 0;
        int sub = 0;
        ArrayList<Integer> catapultIndiciesR = testBitboard.getAllPiecesIndex(Pieces.CATAPULT_ROMANS);
        ArrayList<Integer> towerIndiciesR = testBitboard.getAllPiecesIndex(Pieces.TOWER_ROMANS);
        ArrayList<Integer> wallIndiciesR = testBitboard.getAllPiecesIndex(Pieces.WALL_ROMANS);
        ArrayList<Integer> catapultIndiciesG = testBitboard.getAllPiecesIndex(Pieces.CATAPULT_GAULS);
        ArrayList<Integer> towerIndiciesG = testBitboard.getAllPiecesIndex(Pieces.TOWER_GAULS);
        ArrayList<Integer> wallIndiciesG = testBitboard.getAllPiecesIndex(Pieces.WALL_GAULS);
      List<Integer> middleRow = Arrays.asList(31, 30, 29, 28, 27, 26, 25, 24);

        for (Integer wallIndicy : wallIndiciesR) {
            sum += paramWall * (heatmapRWall.get(wallIndicy));
        }

        for (Integer catapultIndicy : catapultIndiciesR) {
            sum +=  paramCatapult * heatmapRCatapult.get(catapultIndicy);
        }

        for (Integer towerIndicy : towerIndiciesR) {
            sum +=  paramTower * (heatmapRTower.get(towerIndicy));
        }

        for (Integer wallIndicy : wallIndiciesG) {
            sub += paramWall * (heatmapGWall.get(wallIndicy));
        }

        for (Integer catapultIndicy : catapultIndiciesG) {
            sub +=  paramCatapult * heatmapGCatapult.get(catapultIndicy);
        }

        for (Integer towerIndicy : towerIndiciesG) {
            sub +=  paramTower * (heatmapGTower.get(towerIndicy));
        }

      int RomanCatapults = Move.countCatapult(state, 0);
      int RomanTowers = Move.countTowers(state, 0);
      int RomanWalls = Move.countWall(state,0);
      int GaulCatapults = Move.countCatapult(state, 1);
      int GaulTowers = Move.countTowers(state, 1);
      int GaulWalls = Move.countWall(state,1);

      //Take points for Amount Towers>Walls, Catapults>Walls
      //Give/Take points for having more Towers than the enemy
      if(RomanWalls > RomanCatapults){
        sum -= (RomanWalls-RomanCatapults)*100;
      }
      if(RomanWalls > RomanTowers){
        sum -= (RomanWalls-RomanTowers)*400;
      }
      if(GaulWalls > GaulCatapults){
        sub -= (GaulWalls-GaulCatapults)*100;
      }
      if(GaulWalls > GaulTowers){
        sub -= (GaulWalls-GaulTowers)*400;
      }
      if(RomanTowers < GaulTowers){
        sum -= (GaulTowers-RomanTowers)*1000;
        sub += (GaulTowers-RomanTowers)*1000;
      }
      if(RomanTowers > GaulTowers){
        sum += (RomanTowers-GaulTowers)*1000;
        sub -= (RomanTowers-GaulTowers)*1000;
      }
      //Give points for mobility
      int posMoves = generator.calculateAllMoves(state).size()*250;

      //Middlerow dominance
      for(int middleFigure: middleRow){
        if(catapultIndiciesG.contains(middleFigure)){
          sum -= 1000;
        }
        if(catapultIndiciesR.contains(middleFigure)){
          sub -= 1000;
        }
      }

      /**
       * TODO: Uncomment for more randomness
       */
      //sum += (int)(random() * 10000000);

      return sum - sub + posMoves;
    }


    /**
     * NOT USED METHOD HERE #######################################################################################################
     */

    // TODO: Delete if not needed
    public void playMatch(int depth){
        for (int j = 0; j < 10; j++){
            String FENString = "tttttttt/8/8/8/8/8/TTTTTTTT r";
            for (int i = 0; i < 50; i++ ){
                String bigMove = this.getMoveTT(FENString, depth);
                System.out.println("BIG: " + bigMove);
                if (bigMove.equals("Can't Move, REEEEEEE")){
                    System.out.println("BIG BOI LOSES " + i);
                    break;
                }
                FENString = createNewState(FENString, bigMove, 1,0L);
                //System.out.println(FENString);
                String dummyMove = this.getMove(FENString, depth);
                //System.out.println(dummyMove);
                if (dummyMove.equals("Can't Move, REEEEEEE")){
                    System.out.println("DUMMY LOSES " + i);
                    break;
                }
                FENString = createNewState(FENString, dummyMove, -1,0L);
                //System.out.println(FENString);
            }
        }
    }


    // TODO: Use to compare performance
    public String OldgetMove(String state, int depth){
        nodeCounter = 0;
        this.FENString = state;
        currentBitboard = Parser.fenToBoard(FENString);

        int color = FENString.substring(FENString.length() - 1).equals("r")? 1 : -1;

        int maxV = MIN_VALUE;
        String maxM = "Can't Move, REEEEEEE";
        ArrayList<String> moves = generator.calculateAllMoves(state);

        for (String move: moves) {
            nodeCounter += 1;
            int curr = -negaMaxAB(createNewState(state, move, color, zobristHash), depth, MIN_VALUE+1, MAX_VALUE, -color);
            //int curr = -negaMax(createNewState(state, move, color), depth - 1, -color);
            //System.out.print("Move: " + move + ", Value: " + curr + "\n");
            if (curr > maxV){
                if (curr == MAX_VALUE){
                    return move;
                }
                maxV = curr;
                maxM = move;
            }
        }
        return maxM;
    }

}
