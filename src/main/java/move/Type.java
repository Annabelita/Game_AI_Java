/*
 * @author: Annabella Kadavanich
 */

package move;

public class Type {

    /**
     * A Move to a empty field, creates a wall there
     */
    public static final int MOVES_TO_EMPTY_FIELD = 0;


    /**
     * Creates a new tower while moving
     */
    public static final int CREATES_NEW_TOWER = 1;


    /**
     * Catapult move: Removes Wall
     */
    public static final int CATAPULT_REMOVES_OPPONENT_WALL = 2;

    /**
     * Catapult move: Removes Tower
     */
    public static final int CATAPULT_REMOVES_OPPONENT_TOWER = 3;

    /**
     * Catapult move: Removes Catapult
     */
    public static final int CATAPULT_REMOVES_OPPONENT_CATAPULT = 4;

    /**
     * Catapult move: Removes Catapult
     */
    public static final int TOWER_REMOVES_OPPONENT_CATAPULT_TO_TOWER = 5;

    /**
     * Catapult move: Removes Catapult
     */
    public static final int TOWER_REMOVES_OPPONENT_CATAPULT_TO_WALL = 6;


    public static String getType(final int number){
        switch (number) {
            case 0:
                return "MOVES_TO_EMPTY_FIELD";
            case 1:
                return "CREATES_NEW_TOWER";
            case 2:
                return "CATAPULT_REMOVES_OPPONENT_WALL";
            case 3:
                return "CATAPULT_REMOVES_OPPONENT_TOWER";
            case 4:
                return "CATAPULT_REMOVES_OPPONENT_CATAPULT";
            case 5:
                return "TOWER_REMOVES_OPPONENT_CATAPULT_TO_TOWER";
            case 6:
                return "TOWER_REMOVES_OPPONENT_CATAPULT_TO_WALL";
            default:
                return "Something went wrong";
        }
        }
}
