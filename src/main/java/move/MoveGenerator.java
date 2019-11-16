/*
 * @author: Annabella Kadavanich
 */

package move;

import board.Bitboard;
import board.Pieces;
import board.Parser;

import java.util.*;

import static java.lang.Math.*;
import static java.lang.System.nanoTime;


public class MoveGenerator {
    private long[][] zobristTable = new long[56][6];
    String FENString;
    static Bitboard currentBitboard = new Bitboard();
    Bitboard testBitboard = new Bitboard();
    int color;
    static int nodeCounter = 0;


    /**
     * Constructor
     * @param FENString
     */
    public MoveGenerator(String FENString) {
        this.FENString = FENString;
        testBitboard = Parser.fenToBoard(FENString);

        color = FENString.substring(FENString.length() - 1).equals("r") ? 1 : -1;


    }



    public  MoveGenerator() {
    }

    public static void main(String[] args){
        String FENString = "tttttttt/8/8/8/8/8/TTTTTTTT r";
        MoveSelector selector = new MoveSelector();
        selector.measureTime(FENString, 7);
    }

    /**
     * Calculates all moves given a FEN String
     *
     * @param FENString
     * @return Returns Array List with all moves: {"a7-a6", "b3-b5", ....}
     */
    public ArrayList<String> calculateAllMoves(String FENString) {
        //this.FENString = FENString;
        currentBitboard = Parser.fenToBoard(FENString);


        color = FENString.substring(FENString.length() - 1).equals("r")? 1 : -1;

        ArrayList<String> validMoves = new ArrayList<>();

        ArrayList<String> towerMoves = calculateAllTowerMoves();
        for(int i = 0; i < towerMoves.size(); i++) {
            //System.out.println("tower move: " + towerMoves.get(i));
        }
        ArrayList<String> catapultMoves = calculateAllCatapultMoves();
        for(int i = 0; i < catapultMoves.size(); i++) {
            //System.out.println("catapult move: " + catapultMoves.get(i));
        }

        validMoves.addAll(towerMoves);
        validMoves.addAll(catapultMoves);

        // TODO Merge tower + catapult moves

        return validMoves;

    }


    /**
     * Calculates all move a tower can make
     * @return
     */
    public ArrayList<String> calculateAllTowerMoves() {
        HashMap<Integer, HashMap<Integer, ArrayList<String>>> mapTowerMoves = new HashMap<>();
        ArrayList<String> moveList = new ArrayList<>();

        HashMap<Integer, Integer> validIndicies = new HashMap<>();
        // TODO: Change to ArrayList ?
        validIndicies.put(-2, -1);
        validIndicies.put(-18, -9);
        validIndicies.put(-16, -8);
        validIndicies.put(-14, -7);
        validIndicies.put(2, 1);
        validIndicies.put(18, 9);
        validIndicies.put(16, 8);
        validIndicies.put(14, 7);

        if (color == 1) {
            ArrayList<Integer> allTowersRoman = currentBitboard.getAllPiecesIndex(Pieces.TOWER_ROMANS);


            for (Integer tower : allTowersRoman) {
                int currentTower = tower;

                if (currentTower > 55) {
                    throw new RuntimeException("Invalid Index (greater 55)!");
                }

                Iterator it = validIndicies.entrySet().iterator();
                for (HashMap.Entry<Integer, Integer> entry : validIndicies.entrySet()) {
                    int adjacentCalculator = entry.getValue();
                    int targetCalculator = entry.getKey();

                    if (checkIfValidTowerTargetcell(currentTower, targetCalculator)) {
                        int target = currentTower + targetCalculator;

                        if (towerMovePossible(target, color)) {
                            adjacentCalculator += currentTower;
                            String move = calculateTowerMove(currentTower, target, adjacentCalculator, color);
                            if(!move.isEmpty()) {
                                moveList.add(move);
                            }
                        }
                    }
                }

                // TODO: Check if this merges the two lists moveList and attackingTowerMoves
                if(!(attackingTowerMoves(currentTower, color).isEmpty())) {
                    moveList.addAll(attackingTowerMoves(currentTower, color));
                }
            }
        }

        // PLAYER: GAUL
        else {
            ArrayList<Integer> allTowersGaul = currentBitboard.getAllPiecesIndex(Pieces.TOWER_GAULS);

            for (Integer tower : allTowersGaul) {
                int currentTower = tower;

                if (currentTower > 55) {
                    throw new RuntimeException("Invalid Index (greater 55)!");
                }

                Iterator it = validIndicies.entrySet().iterator();
                for (HashMap.Entry<Integer, Integer> entry : validIndicies.entrySet()) {
                    int adjacentCalculator = entry.getValue();
                    int targetCalculator = entry.getKey();


                    if (checkIfValidTowerTargetcell(currentTower, targetCalculator)) {
                        int target = currentTower + targetCalculator;

                        if (towerMovePossible(target, color)) {
                            adjacentCalculator += currentTower;
                            String move = calculateTowerMove(currentTower, target, adjacentCalculator, color);
                            if(!move.isEmpty()) {
                                moveList.add(move);
                            }
                        }
                    }
                }
                // TODO: Check if this merges the two lists moveList and attackingTowerMoves
                if(!attackingTowerMoves(currentTower, color).isEmpty()) {
                    moveList.addAll(attackingTowerMoves(currentTower, color));
                }
            }

        }
        return moveList;
    }




    /**
     * Calculates all valid moves for a catapult
     *
     * @return ArrayList containing all valid Moves as String (f.e. {"a7-a6", "c5-c7", ....}
     */
    public ArrayList<String> calculateAllCatapultMoves() {

        ArrayList<String> moveList = new ArrayList<>();

        HashMap<Integer, Integer> validIndiciesCatapult = new HashMap<>();
        validIndiciesCatapult.put(-2, -3);
        validIndiciesCatapult.put(-18, -27);
        validIndiciesCatapult.put(-16, -24);
        validIndiciesCatapult.put(-14, -21);
        validIndiciesCatapult.put(2, 3);
        validIndiciesCatapult.put(18, 27);
        validIndiciesCatapult.put(16, 24);
        validIndiciesCatapult.put(14, 21);

        if (color == 1) {

            ArrayList<Integer> allCatapultsRoman = currentBitboard.getAllPiecesIndex(Pieces.CATAPULT_ROMANS);

            for (Integer catapult : allCatapultsRoman) {
                int currentCatapult = catapult;

                if (currentCatapult > 55) {
                    throw new RuntimeException("Invalid Index (greater 55)!");
                }


                Iterator it = validIndiciesCatapult.entrySet().iterator();
                for (HashMap.Entry<Integer, Integer> entry : validIndiciesCatapult.entrySet()) {
                    int threeStepIndexCalculator = entry.getValue();
                    int twoStepIndexCalculator = entry.getKey();

                    // Can be used for 2-Step
                    if (checkIfValidTwoSepTargetcell(currentCatapult, twoStepIndexCalculator, color)) {
                        int target = currentCatapult + twoStepIndexCalculator;

                        if (catapultMovePossible(target, color)) {
                            String move = calculateCatapultMove(currentCatapult, target, color);
                            moveList.add(move);

                        }
                    }

                    // TODO: 3 Step
                    if (checkIfValidThreeStepCatapultTargetcell(currentCatapult, threeStepIndexCalculator, color)) {
                        int target = currentCatapult + threeStepIndexCalculator;

                        if (catapultMovePossible(target, color)) {
                            String move = calculateCatapultMove(currentCatapult, target, color);
                            moveList.add(move);

                        }
                    }
                }
            }

            // PLAYER: GAULE
        } else {

            ArrayList<Integer> allCatapultsGaul = currentBitboard.getAllPiecesIndex(Pieces.CATAPULT_GAULS);

            for (Integer catapult : allCatapultsGaul) {
                int currentCatapult = catapult;

                if (currentCatapult > 55) {
                    throw new RuntimeException("Invalid Index (greater 55)!");
                }


                Iterator it = validIndiciesCatapult.entrySet().iterator();
                for (HashMap.Entry<Integer, Integer> entry : validIndiciesCatapult.entrySet()) {
                    int threeStepIndexCalculator = entry.getValue();
                    int twoStepIndexCalculator = entry.getKey();


                    // Can be used for 2-Step
                    if (checkIfValidTwoSepTargetcell(currentCatapult, twoStepIndexCalculator, color)) {
                        int target = currentCatapult + twoStepIndexCalculator;

                        if (catapultMovePossible(target, color)) {
                            String move = calculateCatapultMove(currentCatapult, target, color);
                            moveList.add(move);

                        }
                    }

                    // TODO: CHECK
                    if (checkIfValidThreeStepCatapultTargetcell(currentCatapult, threeStepIndexCalculator, color)) {
                        int target = currentCatapult + threeStepIndexCalculator;

                        if (catapultMovePossible(target, color)) {
                            String move = calculateCatapultMove(currentCatapult, target, color);
                            moveList.add(move);

                        }
                    }
                }
            }
        }
        return moveList;
    }




    /**
     * FOR TOWER MOVES
     * Given a targetCellIndex, this method checks, whether a move is possible
     *
     * @param targetCellIndex
     * @return
     */
    public boolean towerMovePossible(int targetCellIndex, int color) {
        boolean move = false;

        if (color == 1) {
            if (!currentBitboard.isPieceAtPos(Pieces.CATAPULT_ROMANS, targetCellIndex)
                && !currentBitboard.isPieceAtPos(Pieces.CATAPULT_GAULS, targetCellIndex)
                && !currentBitboard.isPieceAtPos(Pieces.WALL_GAULS, targetCellIndex)
                && !currentBitboard.isPieceAtPos(Pieces.TOWER_GAULS, targetCellIndex) ) {
                    move = true;
            }


        } else {
            if (!currentBitboard.isPieceAtPos(Pieces.CATAPULT_GAULS, targetCellIndex)
                    && !currentBitboard.isPieceAtPos(Pieces.CATAPULT_ROMANS, targetCellIndex)
                    && !currentBitboard.isPieceAtPos(Pieces.WALL_ROMANS, targetCellIndex)
                    && !currentBitboard.isPieceAtPos(Pieces.TOWER_ROMANS, targetCellIndex) ) {
                        move = true;
            }
        }

        return move;
    }


    /**
     *
     * FOR TOWER MOVES
     * Return a String representing a valid move given a targetCellIndex and the adjacentCellIndex of the current Player
     *
     * @param targetCellIndex
     * @param adjacentCellIndex
     * @param color
     * @return
     */


    public String calculateTowerMove(int currentTower, int targetCellIndex, int adjacentCellIndex, int color) {

        if(color == 1) {
            // TargetIndex contains Roman Tower. Check adjacent neighbor of currentTower to determine, if move is possible.
            if (currentBitboard.isPieceAtPos(Pieces.TOWER_ROMANS,  targetCellIndex)) {
                return checkTowerPreCondition(targetCellIndex, adjacentCellIndex, currentTower, color);
            }
            // TargetIndex contains Roman Wall. Check adjacent neighbor of currentTower to determine, if move is possible.
            else if (currentBitboard.isPieceAtPos(Pieces.WALL_ROMANS,  targetCellIndex)) {
                return checkTowerPreCondition(targetCellIndex, adjacentCellIndex, currentTower, color);
            }
            // TargetIndex is empty. Check adjacent neighbor of currentTower to determine, if move is possible.
            else if (isEmptyCell(targetCellIndex)) {
                return checkTowerPreCondition(targetCellIndex, adjacentCellIndex, currentTower, color);
            }

        } else {
            // TargetIndex contains Gaule Tower. Check adjacent neighbor of currentTower to determine, if move is possible.
            if (currentBitboard.isPieceAtPos(Pieces.TOWER_GAULS,  targetCellIndex)) {
                return checkTowerPreCondition(targetCellIndex, adjacentCellIndex, currentTower, color);
            }
            // TargetIndex contains Gaule Wall. Check adjacent neighbor of currentTower to determine, if move is possible.
            else if (currentBitboard.isPieceAtPos(Pieces.WALL_GAULS,  targetCellIndex)) {
                return checkTowerPreCondition(targetCellIndex, adjacentCellIndex, currentTower, color);
            }
            // TargetIndex is empty. Check adjacent neighbor of currentTower to determine, if move is possible.
            else if (isEmptyCell(targetCellIndex)) {
                return checkTowerPreCondition(targetCellIndex, adjacentCellIndex, currentTower, color);
            }

        }

        return "";
    }




    /**
     *
     * FOR TOWER MOVES
     *
     * Pre-Condition: targetCellIndex is a valid move
     *
     * This Method checks, if the move to targetCellIndex is possible by checking the adjacentCellIndex of currentTower
     *
     * @param targetCellIndex
     * @param adjacentCellIndex
     * @param currentTower
     * @param color
     * @return Move as String (f.e. a7-b7)
     */
    public String checkTowerPreCondition(int targetCellIndex, int adjacentCellIndex, int currentTower, int color) {

        if (color == 1) {
            if (isEmptyCell(adjacentCellIndex)) {
                return (indexToMoveString(currentTower) + "-" + indexToMoveString(targetCellIndex));
            } else if (currentBitboard.isPieceAtPos(Pieces.WALL_ROMANS, adjacentCellIndex)) {
                return (indexToMoveString(currentTower) + "-" + indexToMoveString(targetCellIndex));
            } else if (currentBitboard.isPieceAtPos(Pieces.TOWER_ROMANS, adjacentCellIndex)) {
                return (indexToMoveString(currentTower) + "-" + indexToMoveString(targetCellIndex));
            }
        } else {
            if (isEmptyCell(adjacentCellIndex)) {
                //System.out.println("TEST:" + indexToMoveString(currentTower) + "-" + indexToMoveString(targetCellIndex));
                return (indexToMoveString(currentTower) + "-" + indexToMoveString(targetCellIndex));
            } else if (currentBitboard.isPieceAtPos(Pieces.WALL_GAULS, adjacentCellIndex)) {
                return (indexToMoveString(currentTower) + "-" + indexToMoveString(targetCellIndex));
            } else if (currentBitboard.isPieceAtPos(Pieces.TOWER_GAULS, adjacentCellIndex)) {
                return (indexToMoveString(currentTower) + "-" + indexToMoveString(targetCellIndex));
            }
        }
        return "";
    }



    /**
     *
     * Given a targetCellIndex checks whether this cell is empty.
     *
     * @param targetCellIndex
     * @return true/false
     */
    public boolean isEmptyCell(int targetCellIndex) {
        return !currentBitboard.isPieceAtPos(Pieces.ALL_FIGURES, targetCellIndex);
    }


    /**
     *  TOWER MOVES
     *
     *  Calculates all moves, where a tower sacrifies itself by either one stone or two
     *
     *  Pre-conditions: opponent target must be adjacent + either: wall, tower, catapult
     *
     * @param currentTower
     * @param color
     * @return
     */
    public ArrayList<String> attackingTowerMoves(int currentTower, int color) {
        ArrayList<Integer> adjacentIndicies = new ArrayList<>();
        ArrayList<String> attackingTowerMoves = new ArrayList<>();
        // TODO: Intialize ArrayList directly with values (f.e. myList = [-1, -9, ...])
        adjacentIndicies.add(-1);
        adjacentIndicies.add(-9);
        adjacentIndicies.add(-8);
        adjacentIndicies.add(-7);
        adjacentIndicies.add(1);
        adjacentIndicies.add(9);
        adjacentIndicies.add(8);
        adjacentIndicies.add(7);


        for (Integer ind : adjacentIndicies) {
                int currentAdjacentCalculationIndex = ind;

                // TODO: In Methode auslagern & TESTEN!
                if ( currentAdjacentCalculationIndex == -1 && ( currentTower < 1 || currentTower % 8 == 0)) {
                    continue;
                }
                if ( currentAdjacentCalculationIndex == -9 && ( currentTower < 9 || currentTower % 8 == 0) ) {
                    continue;
                }
                if ( currentAdjacentCalculationIndex == -8 && currentTower < 8) {
                    continue;
                }

                if (currentAdjacentCalculationIndex  == -7 && ( currentTower < 7 || Arrays.asList(7,15,23,31,39,47,55).contains(currentTower) ) ) {
                    continue;
                }

                if (currentAdjacentCalculationIndex  == 1 && ( currentTower > 54 || Arrays.asList(7,15,23,31,39,47,55).contains(currentTower) )){
                    continue;
                }
                if (currentAdjacentCalculationIndex  == 9 && ( currentTower > 46 || Arrays.asList(7,15,23,31,39,47,55).contains(currentTower) )) {
                    continue;
                }

                if (currentAdjacentCalculationIndex == 8 && currentTower > 47 ) {
                    continue;
                }
                if ( currentAdjacentCalculationIndex == 7 &&   ( currentTower > 49 || currentTower % 8 == 0)  )  {
                    continue;
                }

                int targetCellIndex = currentTower + currentAdjacentCalculationIndex;

                if(color == 1) {

                    if( !currentBitboard.isPieceAtPos(Pieces.TOWER_GAULS,  targetCellIndex)) {
                        if( currentBitboard.isPieceAtPos(Pieces.WALL_GAULS,  targetCellIndex)) {
                            attackingTowerMoves.add(indexToMoveString(currentTower) + "-" + indexToMoveString(targetCellIndex));
                        }
                        if( currentBitboard.isPieceAtPos(Pieces.CATAPULT_GAULS,  targetCellIndex)) {
                            attackingTowerMoves.add(indexToMoveString(currentTower) + "-" + indexToMoveString(targetCellIndex)+ "-1");
                            attackingTowerMoves.add(indexToMoveString(currentTower) + "-" + indexToMoveString(targetCellIndex)+ "-2");

                        }
                    }
                }

                // PLAYER: GAUL
                else {
                    if( !currentBitboard.isPieceAtPos(Pieces.TOWER_ROMANS,  targetCellIndex)) {
                        if( currentBitboard.isPieceAtPos(Pieces.WALL_ROMANS,  targetCellIndex)) {
                            attackingTowerMoves.add(indexToMoveString(currentTower) + "-" + indexToMoveString(targetCellIndex));
                        }
                        if( currentBitboard.isPieceAtPos(Pieces.CATAPULT_ROMANS,  targetCellIndex)) {
                            attackingTowerMoves.add(indexToMoveString(currentTower) + "-" + indexToMoveString(targetCellIndex)+ "-1");
                            attackingTowerMoves.add(indexToMoveString(currentTower) + "-" + indexToMoveString(targetCellIndex)+ "-2");
                        }
                    }
                }
        }
        return attackingTowerMoves;

    }


    /**
     * FOR TOWER MOVES
     *
     * @param currentTower
     * @param targetCalculator
     * @return true/false
     */
    public boolean checkIfValidTowerTargetcell(int currentTower, int targetCalculator) {

        int cur = currentTower - 1;

        if ( targetCalculator == -2 && ( currentTower < 2 || currentTower % 8 == 0 || cur % 8 == 0)) {
            return false;
        }
        if ( targetCalculator == -18 && ( currentTower < 18 || currentTower % 8 == 0 || cur % 8 == 0) ) {
            return false;
        }
        if ( targetCalculator == -16 && currentTower < 16) {
            return false;
        }

        if (targetCalculator  == -14 && ( currentTower < 14 || Arrays.asList(6,7,14,15,22,23,30,31,38,39,46,47,54,55).contains(currentTower) ) ) {
            return false;
        }

        if (targetCalculator  == 2 && ( currentTower > 53 || Arrays.asList(6,7,14,15,22,23,30,31,38,39,46,47,54,55).contains(currentTower) )){
            return false;
        }
        if (targetCalculator  == 18 && ( currentTower > 37 || Arrays.asList(6,7,14,15,22,23,30,31,38,39,46,47,54,55).contains(currentTower) )) {
            return false;
        }

        if (targetCalculator == 16 && currentTower > 39 ) {
            return false;
        }
        if ( targetCalculator == 14 &&   ( currentTower > 41 || currentTower % 8 == 0 || cur % 8 == 0)  )  {
            return false;
        }

        return true;
    }


    /**
     * FOR CATAPULT MOVES
     *
     * Checks if threeStepIndexCalculator is a valid index for a catapult that throws 3 fields
     *
     * @param currentCatapult
     * @param threeStepIndexCalculator
     * @return true /false
     *
     * TODO: CHECK IF THIS WORKS
     */
    public boolean checkIfValidThreeStepCatapultTargetcell(int currentCatapult, int threeStepIndexCalculator, int color) {
        if(color == 1){
            int cur = currentCatapult - 1;
            int cur_2 = currentCatapult - 2;

            if ( threeStepIndexCalculator == -3 && ( currentCatapult < 3 || currentCatapult % 8 == 0 || cur % 8 == 0 || cur_2 % 8 == 0)) {
                return false;
            }
            if (threeStepIndexCalculator  == 3 && ( currentCatapult > 52 || Arrays.asList(5,6,7,13,14,15,21,22,23,29,30,31,37,38,39,45,46,47,53,54,55).contains(currentCatapult) )){
                return false;
            }
            if (threeStepIndexCalculator  == 27 && ( currentCatapult > 28 || Arrays.asList(5,6,7,13,14,15,21,22,23,29,30,31,37,38,39,45,46,47,53,54,55).contains(currentCatapult) )) {
                return false;
            }

            if (threeStepIndexCalculator == 24 && currentCatapult > 31 ) {
                return false;
            }
            if ( threeStepIndexCalculator == 21 &&   ( currentCatapult > 34 || currentCatapult % 8 == 0 || cur % 8 == 0 || cur_2 % 8 == 0)  )  {
                return false;
            }
            if (Arrays.asList(-21, -24, -27).contains(threeStepIndexCalculator)){
                return false;
            }

            return true;


        }
        else {
            int cur = currentCatapult - 1;
            int cur_2 = currentCatapult - 2;

            if ( threeStepIndexCalculator == -3 && ( currentCatapult < 3 || currentCatapult % 8 == 0 || cur % 8 == 0 || cur_2 % 8 == 0)) {
                return false;
            }
            if ( threeStepIndexCalculator == -27 && ( currentCatapult < 27 || currentCatapult % 8 == 0 || cur % 8 == 0 || cur_2 % 8 == 0) ) {
                return false;
            }
            if ( threeStepIndexCalculator == -24 && currentCatapult < 24) {
                return false;
            }

            if (threeStepIndexCalculator  == -21 && ( currentCatapult < 21 || Arrays.asList(5,6,7,13,14,15,21,22,23,29,30,31,37,38,39,45,46,47,53,54,55).contains(currentCatapult) ) ) {
                return false;
            }

            if (threeStepIndexCalculator  == 3 && ( currentCatapult > 52 || Arrays.asList(5,6,7,13,14,15,21,22,23,29,30,31,37,38,39,45,46,47,53,54,55).contains(currentCatapult) )){
                return false;
            }

            if (Arrays.asList(21, 24, 27).contains(threeStepIndexCalculator)){
                return false;
            }
            return true;


        }


    }


    /**
     * FOR CATAPULT MOVES
     *
     * Checks if twoStepIndexCalculator is a valid index for a catapult that throws 2 fields
     * Romans: Can only throw horizontally or upwards
     * Gauls: Can only throw horizontally or downoards
     *
     * @param currentCatapult
     * @param twoStepIndexCalculator
     * @return true/false
     */
    public boolean checkIfValidTwoSepTargetcell(int currentCatapult, int twoStepIndexCalculator, int color) {

        if(color == 1){
            int cur = currentCatapult - 1;

            if ( twoStepIndexCalculator == -2 && ( currentCatapult < 2 || currentCatapult % 8 == 0 || cur % 8 == 0)) {
                return false;
            }
            if (twoStepIndexCalculator  == 2 && ( currentCatapult > 53 || Arrays.asList(6,7,14,15,22,23,30,31,38,39,46,47,54,55).contains(currentCatapult) )){
                return false;
            }
            if (twoStepIndexCalculator  == 18 && ( currentCatapult > 37 || Arrays.asList(6,7,14,15,22,23,30,31,38,39,46,47,54,55).contains(currentCatapult) )) {
                return false;
            }

            if (twoStepIndexCalculator == 16 && currentCatapult > 39 ) {
                return false;
            }
            if ( twoStepIndexCalculator == 14 &&   ( currentCatapult > 41 || currentCatapult % 8 == 0 || cur % 8 == 0)  )  {
                return false;
            }
            if (Arrays.asList(-14, -16, -18).contains(twoStepIndexCalculator)){
                return false;
            }

            return true;


        }
        else {
            int cur = currentCatapult - 1;

            if ( twoStepIndexCalculator == -2 && ( currentCatapult < 2 || currentCatapult % 8 == 0 || cur % 8 == 0)) {
                return false;
            }
            if ( twoStepIndexCalculator == -18 && ( currentCatapult < 18 || currentCatapult % 8 == 0 || cur % 8 == 0) ) {
                return false;
            }
            if ( twoStepIndexCalculator == -16 && currentCatapult < 16) {
                return false;
            }

            if (twoStepIndexCalculator  == -14 && ( currentCatapult < 14 || Arrays.asList(6,7,14,15,22,23,30,31,38,39,46,47,54,55).contains(currentCatapult) ) ) {
                return false;
            }

            if (twoStepIndexCalculator  == 2 && ( currentCatapult > 53 || Arrays.asList(6,7,14,15,22,23,30,31,38,39,46,47,54,55).contains(currentCatapult) )){
                return false;
            }

            if (Arrays.asList(14, 16, 18).contains(twoStepIndexCalculator)){
                return false;
            }
            return true;


        }

    }


    /**
     * FOR CATAPULT MOVES
     *
     * Calculates for the 2-step catapult whether the move to targetCellIndex is valid
     *
     * @param targetCellIndex
     * @param color
     * @return true/false
     */
    public boolean catapultMovePossible(int targetCellIndex, int color) {

        if(color == 1){
            return !currentBitboard.isPieceAtPos(Pieces.WALL_ROMANS, targetCellIndex)
                    && !currentBitboard.isPieceAtPos(Pieces.TOWER_ROMANS, targetCellIndex)
                    && !currentBitboard.isPieceAtPos(Pieces.CATAPULT_ROMANS, targetCellIndex);

         // PLAYER: GAULE
        } else {
            return !currentBitboard.isPieceAtPos(Pieces.WALL_GAULS, targetCellIndex)
                    && !currentBitboard.isPieceAtPos(Pieces.TOWER_GAULS, targetCellIndex)
                    && !currentBitboard.isPieceAtPos(Pieces.CATAPULT_GAULS, targetCellIndex);

        }

    }


    /**
     * FOR CATAPULT MOVES
     *
     * Calculates the move for a given TargetIndex and CurrentCatapult(Index)
     * @param currentCatapult
     * @param targetCellIndex
     * @param color
     * @return Move String ( f.e. a7-a6)
     */
    public String calculateCatapultMove(int currentCatapult, int targetCellIndex, int color) {

        if(color == 1) {
            if (isEmptyCell(targetCellIndex)) {
                return (indexToMoveString(currentCatapult) + "-" + indexToMoveString(targetCellIndex));
            }
            if (currentBitboard.isPieceAtPos(Pieces.WALL_GAULS, targetCellIndex)) {
                return (indexToMoveString(currentCatapult) + "-" + indexToMoveString(targetCellIndex));
            }
            if (currentBitboard.isPieceAtPos(Pieces.TOWER_GAULS, targetCellIndex)) {
                return (indexToMoveString(currentCatapult) + "-" + indexToMoveString(targetCellIndex));
            }
            if (currentBitboard.isPieceAtPos(Pieces.CATAPULT_GAULS, targetCellIndex)) {
                return (indexToMoveString(currentCatapult) + "-" + indexToMoveString(targetCellIndex));
            }
        }
        else {
            if (isEmptyCell(targetCellIndex)) {
                return (indexToMoveString(currentCatapult) + "-" + indexToMoveString(targetCellIndex));
            }
            if (currentBitboard.isPieceAtPos(Pieces.WALL_ROMANS, targetCellIndex)) {
                return (indexToMoveString(currentCatapult) + "-" + indexToMoveString(targetCellIndex));
            }
            if (currentBitboard.isPieceAtPos(Pieces.TOWER_ROMANS, targetCellIndex)) {
                return (indexToMoveString(currentCatapult) + "-" + indexToMoveString(targetCellIndex));
            }
            if (currentBitboard.isPieceAtPos(Pieces.CATAPULT_ROMANS, targetCellIndex)) {
                return (indexToMoveString(currentCatapult) + "-" + indexToMoveString(targetCellIndex));
            }

        }
        return "";
    }





    /**
     * Converts a Index-Move (f.e. 34-32) to a String-Move (f.e. a7-a6)
     *
     * @param indexString
     * @return String Move (f.e. a7-a6)
     */

    public String indexToMoveString(int indexString) {
        switch (indexString) {
            case 55:
                return "a7";
            case 54:
                return "b7";
            case 53:
                return "c7";
            case 52:
                return "d7";
            case 51:
                return "e7";
            case 50:
                return "f7";
            case 49:
                return "g7";
            case 48:
                return "h7";
            case 47:
                return "a6";
            case 46:
                return "b6";
            case 45:
                return "c6";
            case 44:
                return "d6";
            case 43:
                return "e6";
            case 42:
                return "f6";
            case 41:
                return "g6";
            case 40:
                return "h6";
            case 39:
                return "a5";
            case 38:
                return "b5";
            case 37:
                return "c5";
            case 36:
                return "d5";
            case 35:
                return "e5";
            case 34:
                return "f5";
            case 33:
                return "g5";
            case 32:
                return "h5";
            case 31:
                return "a4";
            case 30:
                return "b4";
            case 29:
                return "c4";
            case 28:
                return "d4";
            case 27:
                return "e4";
            case 26:
                return "f4";
            case 25:
                return "g4";
            case 24:
                return "h4";
            case 23:
                return "a3";
            case 22:
                return "b3";
            case 21:
                return "c3";
            case 20:
                return "d3";
            case 19:
                return "e3";
            case 18:
                return "f3";
            case 17:
                return "g3";
            case 16:
                return "h3";
            case 15:
                return "a2";
            case 14:
                return "b2";
            case 13:
                return "c2";
            case 12:
                return "d2";
            case 11:
                return "e2";
            case 10:
                return "f2";
            case 9:
                return "g2";
            case 8:
                return "h2";
            case 7:
                return "a1";
            case 6:
                return "b1";
            case 5:
                return "c1";
            case 4:
                return "d1";
            case 3:
                return "e1";
            case 2:
                return "f1";
            case 1:
                return "g1";
            case 0:
                return "h1";
            default:
                return "Index out of bounds.";

        }
    }

    public int moveToIndex(String move) {
        switch (move) {
            case "a7":
                return 55;
            case "b7":
                return 54;
            case "c7":
                return 53;
            case "d7":
                return 52;
            case "e7":
                return 51;
            case "f7":
                return 50;
            case "g7":
                return 49;
            case "h7":
                return 48;
            case "a6":
                return 47;
            case "b6":
                return 46;
            case "c6":
                return 45;
            case "d6":
                return 44;
            case "e6":
                return 43;
            case "f6":
                return 42;
            case "g6":
                return 41;
            case "h6":
                return 40;
            case "a5":
                return 39;
            case "b5":
                return 38;
            case "c5":
                return 37;
            case "d5":
                return 36;
            case "e5":
                return 35;
            case "f5":
                return 34;
            case "g5":
                return 33;
            case "h5":
                return 32;
            case "a4":
                return 31;
            case "b4":
                return 30;
            case "c4":
                return 29;
            case "d4":
                return 28;
            case "e4":
                return 27;
            case "f4":
                return 26;
            case "g4":
                return 25;
            case "h4":
                return 24;
            case "a3":
                return 23;
            case "b3":
                return 22;
            case "c3":
                return 21;
            case "d3":
                return 20;
            case "e3":
                return 19;
            case "f3":
                return 18;
            case "g3":
                return 17;
            case "h3":
                return 16;
            case "a2":
                return 15;
            case "b2":
                return 14;
            case "c2":
                return 13;
            case "d2":
                return 12;
            case "e2":
                return 11;
            case "f2":
                return 10;
            case "g2":
                return 9;
            case "h2":
                return 8;
            case "a1":
                return 7;
            case "b1":
                return 6;
            case "c1":
                return 5;
            case "d1":
                return 4;
            case "e1":
                return 3;
            case "f1":
                return 2;
            case "g1":
                return 1;
            case "h1":
                return 0;
            default:
                return -1;
        }
    }
}

