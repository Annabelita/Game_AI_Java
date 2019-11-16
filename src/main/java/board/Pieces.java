/**
 * @author: Andrej Savinov
 */


package board;

/**
 * Representation of all bitboards
 */
public class Pieces {
    /**
     * Number of all types of figures (romans/gauls)
     */
    public static final int NUMBER_OF_FIGURES = 6;

    /**
     * Index for the bitboard representing the roman walls.
     */
    public static final int WALL_ROMANS = 0;

    /**
     * Index for the bitboard representing the gaul walls.
     */
    public static final int WALL_GAULS = 1;

    /**
     * Index for the bitboard representing the roman towers.
     */
    public static final int TOWER_ROMANS = 2;

    /**
     * Index for the bitboard representing the gaul towers.
     */
    public static final int TOWER_GAULS = 3;

    /**
     * Index for the bitboard representing the roman catapults.
     */
    public static final int CATAPULT_ROMANS = 4;

    /**
     * Index for the bitboard representing the gaul catapults.
     */
    public static final int CATAPULT_GAULS = 5;

    /**
     * Index for the bitboard representing all roman figures.
     */
    public static final int ALL_ROMAN = 6;

    /**
     * Index for the bitboard representing all gaul figures.
     */
    public static final int ALL_GAUL = 7;

    /**
     * Index for the bitboard representing all figures.
     */
    public static final int ALL_FIGURES = 8;


    public static String getPiece(final int number){
        switch (number){
            case 0:
                return "WALL_ROMANS";
            case 1:
                return "WALL_GAULS";
            case 2:
                return "TOWER_ROMANS";
            case 3:
                return "TOWER_GAULS";
            case 4:
                return "CATAPULT_ROMANS";
            case 5:
                return "CATAPULT_GAULS";
            case 6:
                return "ALL_ROMAN";
            case 7:
                return "ALL_GAUL";
            case 8:
                return "ALL_FIGURES";
            default:
                return "Something went wrong";
        }
    }
}