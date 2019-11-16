/**
 * @author: Andrej Savinov
 */

package board;

import java.util.ArrayList;

/**
 * TODO: Add description
 */
public class Bitboard {
    /**
     * Amount of bitboards
     */
    public static final int NUMBER_OF_BITBOARDS = 9;

    /**
     * Bitboards represented as array
     */
    long[] bitboards;

    /**
     * Bitboard representation for a single board
     */
    long bitboard;

    /**
     * Constructor for Empty bitboards
     */
    public Bitboard(){
        bitboards = new long[NUMBER_OF_BITBOARDS];
        for (int i = 0; i < NUMBER_OF_BITBOARDS; i++){
            setBitboardByIndex(i,~0xFFFFFFFFFFFFFFFFL);

        }
    }

    /**
     * Constructor for starting position
     * @param startingPos temporary boolean to determine which constructor to use
     */
    public Bitboard(Boolean startingPos){
        if(startingPos){
            bitboards = new long[NUMBER_OF_BITBOARDS];
            for (int i = 0; i < NUMBER_OF_BITBOARDS; i++){
                if(i == 2){
                    setBitboardByIndex(i,0xFFL );
                }
                else if(i == 3){
                    setBitboardByIndex(i,0xFF000000000000L);
                }
                else if(i == 6){
                    setBitboardByIndex(i, bitboards[0] | bitboards[2] | bitboards[4]);
                }
                else if(i == 7){
                    setBitboardByIndex(i, bitboards[1] | bitboards[3] | bitboards[5]);
                }
                else if(i == 8){
                    setBitboardByIndex(i, bitboards[6] | bitboards[7]);
                }
                else {
                    setBitboardByIndex(i,~0xFFFFFFFFFFFFFFFFL);
                }
            }
        }
    }

    /**
     * Gives a list containing all piece positions for a given board
     * @param bitboardIndex index for the bitboard which shall be checked (eg. Pieces.WALL_ROMANS)
     * @return
     */
    public ArrayList<Integer> getAllPiecesIndex(int bitboardIndex){
        ArrayList<Integer> piecesIndex = new ArrayList<Integer>();
        boolean done = false;
        int startingPos = 55;

        while (!done){
            if (isPieceAtPos(bitboardIndex,startingPos)){
                piecesIndex.add(startingPos);
            }
            if(startingPos == 0){
                done = true;
            }
            startingPos--;
        }
        return piecesIndex;
    }

    /**
     * Determines if a Piece is at the given position
     * @param bitBoardIndex to determine which board to check
     * @param pos long for each board position
     * @return true/false
     */
    public boolean isPieceAtPos(final int bitBoardIndex,final long pos){
        return (getBitboard(bitBoardIndex) & pos) != 0;
    }

    /**
     * Determines if a Piece is at the given position
     * @param bitBoardIndex to determine which board to check
     * @param pos Int between 0-55 for each board position
     * @return true/false
     */
    public boolean isPieceAtPos(final int bitBoardIndex,final int pos){
        return (getBitboard(bitBoardIndex) & Parser.convertPos(pos)) != 0;
    }

    /**
     * Determines if a Piece is at the given position
     * @param pos Int between 0-55 for each board position
     * @return true/false
     */
    public int whichPieceIsAtPos(final int pos){
        if (isPieceAtPos(Pieces.WALL_ROMANS, pos)) return 0;
        else if (isPieceAtPos(Pieces.WALL_GAULS, pos)) return 1;
        else if (isPieceAtPos(Pieces.TOWER_ROMANS, pos)) return 2;
        else if (isPieceAtPos(Pieces.TOWER_GAULS, pos)) return 3;
        else if (isPieceAtPos(Pieces.CATAPULT_ROMANS, pos)) return 4;
        else if (isPieceAtPos(Pieces.CATAPULT_GAULS, pos)) return 5;
        else return 6;
    }


    /**
     * Sets a bitboard by its index (create/update bitboard)
     * @param index bitboard index
     * @param bitboard bitboard representation
     */
    public void setBitboardByIndex(final int index, final long bitboard){
        this.bitboards[index] = bitboard;
    }

    /**
     * Bitboard getter
     * @param index
     * @return bitboard for given index
     */
    public long getBitboard(final int index){
        return this.bitboards[index];
    }

    /**
     * Bitboards getter
     * @return all bitboards
     */
    public long[] getBoards(){
        return this.bitboards;
    }

    /**
     * TODO: Remove once not needed !FOR TESTING!
     * TODO: Maybe create better method to create long with leading zeros
     * Converts bitboard long to String with Bitboardname and 56 leading zeros
     * @param bitboard
     * @return
     */
    public String getBoardRepresentation(int pieceNum,Long bitboard){
        String boardRep = Pieces.getPiece(pieceNum)+"\n";
        String fullBoard = (String.format("%56s",Long.toBinaryString(bitboard)).replaceAll(" ","0"));
        int rowlen = 8;
        for (int i = 0; i < rowlen-1; i++){
            boardRep += fullBoard.substring(rowlen*i,rowlen*(i+1))+"\n";
        }
        return boardRep;
    }





}

