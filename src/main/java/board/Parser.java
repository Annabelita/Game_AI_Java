/**
 * @author: Andrej Savinov
 */

package board;


public class Parser {
    /**
     *
     * @param bitboards current bitBoard representation
     * @param isRoman if it is a roman FEN
     * @return
     */
    public static String boardToFen(Bitboard bitboards, boolean isRoman){
        String fenString = "";
        long boardPos = 0x80000000000000L;
        int skip = 0;
        int rowLen = 8;

        for (int i = 0; i < rowLen-1; i++){
            //Reset skip after each row
            skip = 0;

            for (int j = 0; j < rowLen; j++){
                if(bitboards.isPieceAtPos(Pieces.WALL_ROMANS,boardPos)){
                    if(placeSkip(skip)){
                        fenString += Integer.toString(skip);
                        skip = 0;
                    }
                    fenString += "W";
                }
                else if(bitboards.isPieceAtPos(Pieces.WALL_GAULS,boardPos)){
                    if(placeSkip(skip)){
                        fenString += Integer.toString(skip);
                        skip = 0;
                    }
                    fenString += "w";
                }
                else if(bitboards.isPieceAtPos(Pieces.TOWER_ROMANS,boardPos)){
                    if(placeSkip(skip)){
                        fenString += Integer.toString(skip);
                        skip = 0;
                    }
                    fenString += "T";
                }
                else if(bitboards.isPieceAtPos(Pieces.TOWER_GAULS,boardPos)){
                    if(placeSkip(skip)){
                        fenString += Integer.toString(skip);
                        skip = 0;
                    }
                    fenString += "t";
                }
                else if(bitboards.isPieceAtPos(Pieces.CATAPULT_ROMANS,boardPos)){
                    if(placeSkip(skip)){
                        fenString += Integer.toString(skip);
                        skip = 0;
                    }
                    fenString += "C";
                }
                else if(bitboards.isPieceAtPos(Pieces.CATAPULT_GAULS,boardPos)){
                    if(placeSkip(skip)){
                        fenString += Integer.toString(skip);
                        skip = 0;
                    }
                    fenString += "c";
                }
                else {
                    skip++;
                }
                boardPos >>>= 1;
            }
            //Check to skip at row end
            if(placeSkip(skip)){
                fenString += Integer.toString(skip);
                skip = 0;
            }
            //Don't place / at last row
            if(i != rowLen-2){
                fenString += "/";
            }
        }
        if(isRoman){
            fenString += " r";
        }
        else {
            fenString += " g";
        }

        return fenString;
    }
    /**
     * Converts fenString to bitboard representation
     * @param fenString
     * @return
     */
    public static Bitboard fenToBoard(String fenString){
        String onlyBoard = fenString.substring(0,fenString.lastIndexOf(""));
        Bitboard newBoards = new Bitboard();
        char[] fenSymbols = onlyBoard.toCharArray();
        long boardPos = 0x80000000000000L;

        for (char fenSymbol : fenSymbols){
            //Character check
            if (Character.isLetter(fenSymbol)){
                if(fenSymbol == 'W'){
                    newBoards.getBoards()[Pieces.WALL_ROMANS] |= boardPos;
                }
                else if(fenSymbol == 'T'){
                    newBoards.getBoards()[Pieces.TOWER_ROMANS] |= boardPos;
                }
                else if(fenSymbol == 'C'){
                    newBoards.getBoards()[Pieces.CATAPULT_ROMANS] |= boardPos;
                }
                else if(fenSymbol == 'w'){
                    newBoards.getBoards()[Pieces.WALL_GAULS] |= boardPos;
                }
                else if(fenSymbol == 't'){
                    newBoards.getBoards()[Pieces.TOWER_GAULS] |= boardPos;
                }
                else if(fenSymbol == 'c'){
                    newBoards.getBoards()[Pieces.CATAPULT_GAULS] |= boardPos;
                }
                //Move to next position
                boardPos >>>= 1;
            }
            //Number check
            else if(Character.isDigit(fenSymbol)){
                boardPos >>>= Character.digit(fenSymbol, 10);
            }
        }
        //Update other bitboards
        newBoards.setBitboardByIndex(Pieces.ALL_ROMAN, newBoards.getBoards()[Pieces.WALL_ROMANS]
                | newBoards.getBoards()[Pieces.TOWER_ROMANS] | newBoards.getBoards()[Pieces.CATAPULT_ROMANS]);
        newBoards.setBitboardByIndex(Pieces.ALL_GAUL, newBoards.getBoards()[Pieces.WALL_GAULS]
                | newBoards.getBoards()[Pieces.TOWER_GAULS] | newBoards.getBoards()[Pieces.CATAPULT_GAULS]);
        newBoards.setBitboardByIndex(Pieces.ALL_FIGURES, newBoards.getBoards()[Pieces.ALL_ROMAN]
                | newBoards.getBoards()[Pieces.ALL_GAUL]);

        return newBoards;
    }

    /**
     * Determine if skip has to be set or not
     * @param skipAmount
     * @return true/false
     */
    private static boolean placeSkip(int skipAmount){
        if(0 < skipAmount){
            return true;
        }
        else {
            return false;
        }
    }

    public static long convertPos(int posIndex){
        long convertedPos = 0x1L;
        if(posIndex == 0){
            return convertedPos;
        }
        else {
            return (convertedPos << posIndex);
        }
    }
}
