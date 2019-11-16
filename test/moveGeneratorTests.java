import move.MoveGenerator;
import move.MoveSelector;
import org.junit.jupiter.api.Test;

import java.util.*;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class moveGeneratorTests {



    static MoveSelector testSelector = new MoveSelector();
    static MoveGenerator testGenerator = new MoveGenerator();

    public  moveGeneratorTests() {

    }


    /**
     * Tests if MoveList has same amout of elements as target move List
     */
    @Test
    public void MoveSize() {
        String FENString = "8/1t1tt1t1/1twWwwt1/1W2W3/3TW3/4TW2/6TT g";

        testGenerator = new MoveGenerator();
        ArrayList<String> allMoves = testGenerator.calculateAllMoves(FENString);


        assertEquals(19, allMoves.size() );
    }








    // --------------------------------------------------- Testing different FEN Strings -----------------------------------------

    /**
     * Tests if List with Moves contain the same moves
     */
    @Test
    public void AllMovesEqual1() {
        String FENString = "8/1t1tt1t1/1twWwwt1/1W2W3/3TW3/4TW2/6TT g";


        testGenerator = new MoveGenerator();
        ArrayList<String> allMoves = testGenerator.calculateAllMoves(FENString);
        ArrayList<String> wrongMoves = new ArrayList<String>();
        List<String> list = Arrays.asList("b6-d4", "b6-d6", "b5-b4" ,"b5-b7","b5-d7","d6-d5","d6-b6","d6-f4","d6-f6","e6-d5","e6-c6"
                ,"e6-g6","e6-g4","g6-e6","g6-g4","g5-g7","g5-g3","g5-e7","g5-e5");

        Collections.sort(list, Collections.reverseOrder());
        Collections.sort(allMoves, Collections.reverseOrder());

        assertEquals(list, allMoves);


    }


    /**
     * Tests if List with Moves contain the same moves
     */
    @Test
    public void AllMovesEqual2() {
        String FENString = "5ww1/2w1wwW1/w1w1wWT1/2wt3W/1wWwwW2/2Tw1WW1/T6T r";
        boolean equal = true;


        testGenerator = new MoveGenerator();
        ArrayList<String> allMoves = testGenerator.calculateAllMoves(FENString);
        ArrayList<String> wrongMoves = new ArrayList<String>();
        List<String> list = Arrays.asList("a1-a3","a1-c1","a1-c3","c2-a2","c2-b3","c2-d3","c2-d2","g5-g3","g5-f6","h1-f1","h1-f3","h1-h3");

        Collections.sort(list, Collections.reverseOrder());
        Collections.sort(allMoves, Collections.reverseOrder());

        assertEquals(list, allMoves);
    }


    /**
     * Tests if List with Moves contain the same moves
     */
    @Test
    public void AllMovesEqual3() {
        String FENString = "t4t1t/3ttw2/2wt4/4w3/W1TW1T2/W1TT1WW1/6T1 g";
        boolean equal = true;
   testGenerator = new MoveGenerator();
        ArrayList<String> allMoves = testGenerator.calculateAllMoves(FENString);
        ArrayList<String> wrongMoves = new ArrayList<String>();
        List<String> list = Arrays.asList("a7-c7","a7-a5","a7-c5","d6-b6","d6-b4","d6-d4","d6-f4","d6-f6","d5-d7","d5-b7","d5-b5","d5-b3","d5-f5","d5-f7","e6-c6","e6-c4","e6-e4","e6-g4"
                ,"e6-g6","f7-d7","f7-d5","f7-f5","f7-h5","f7-h7","h7-f7","h7-f5","h7-h5");

        Collections.sort(list, Collections.reverseOrder());
        Collections.sort(allMoves, Collections.reverseOrder());

        assertEquals(list, allMoves);
    }


    /**
     * Tests if List with Moves contain the same moves
     * TODO: Test in Wiki if result is true
     */
    @Test
    public void AllMovesEqual4() {
        String FENString = "ttttt1tt/8/5t2/8/8/8/TTTTTTTT g";
        boolean equal = true;
        testGenerator = new MoveGenerator();
        ArrayList<String> allMoves = testGenerator.calculateAllMoves(FENString);
        ArrayList<String> wrongMoves = new ArrayList<String>();
        List<String> list = Arrays.asList("a7-a5","a7-c5","a7-c7","b7-b5","b7-d7","b7-d5","c7-c5","c7-e7","c7-e5","c7-a5","c7-a7"
                                            ,"d7-d5","d7-f7","d7-f5","d7-b5","d7-b7", "e7-e5", "e7-g7", "e7-g5", "e7-c5", "e7-c7",
                                            "f5-h7", "f5-h5", "f5-d5", "f5-d7", "g7-g5", "g7-e5", "g7-e7", "h7-h5", "h7-f5",
                                            "h7-f7", "f5-h3", "f5-f7", "f5-f3", "f5-d3");

        Collections.sort(list, Collections.reverseOrder());
        Collections.sort(allMoves, Collections.reverseOrder());

        assertEquals(list, allMoves);
    }


    /**
     * Tests if List with Moves contain the same moves
     * TODO: Test in Wiki if result is true
     */
    @Test
    public void AllMovesEqual5() {
        String FENString = "tt1ttttt/1w6/w7//W6W/W6W/1TTTTTT1 g";
        boolean equal = true;
        testGenerator = new MoveGenerator();
        ArrayList<String> allMoves = testGenerator.calculateAllMoves(FENString);
        ArrayList<String> wrongMoves = new ArrayList<String>();
        List<String> list = Arrays.asList("a7-a5", "a7-c5", "a7-c7", "b7-b5", "b7-d5", "b7-d7", "d7-b7", "d7-d5", "d7-b5",
                                    "d7-f5", "d7-f7", "e7-c5", "e7-g5", "e7-c7", "e7-e5", "e7-g7", "f7-f5", "f7-d7", "f7-h7",
                                    "f7-d5", "f7-h5", "g7-e7", "g7-g5", "g7-e5", "h7-h5", "h7-f7", "h7-f5");

        Collections.sort(list, Collections.reverseOrder());
        Collections.sort(allMoves, Collections.reverseOrder());

        assertEquals(list, allMoves);
    }


    /**
     * Tests if List with Moves contain the same moves
     * TODO: Test in Wiki if result is true
     */
    @Test
    public void AllMovesEqual6() {
        String FENString = "8/1t1tt1t1/1twWwwt1/1W2W3/3TW3/4TW2/6TT r";
        boolean equal = true;
        testGenerator = new MoveGenerator();
        ArrayList<String> allMoves = testGenerator.calculateAllMoves(FENString);
        ArrayList<String> wrongMoves = new ArrayList<String>();


        List<String> list = Arrays.asList("g1-e1","g1-g3","g1-e3","h1-f3","h1-h3","h1-f1","e2-e4","e2-g2","e2-c4","e2-c2",
                "e2-g4","d3-d5","d3-f3","d3-b3","d3-d1","d3-b1","d3-f1");

        Collections.sort(list, Collections.reverseOrder());
        Collections.sort(allMoves, Collections.reverseOrder());

        assertEquals(list, allMoves);
    }



    /**
     * Tests if List with Moves contain the same moves
     *
     */
    @Test
    public void AllMovesEqual7() {
        String FENString = "3tt2t/wt6/tw2wt2/5w2/T2W1T2/WW1W1WW1/1T2T1T1 r";
        boolean equal = true;
        testGenerator = new MoveGenerator();
        ArrayList<String> allMoves = testGenerator.calculateAllMoves(FENString);
        ArrayList<String> wrongMoves = new ArrayList<String>();


        List<String> list = Arrays.asList("a3-c5","a3-c3","a3-c1","a3-a1","b1-b3","b1-d3","b1-d1","e1-c1","e1-c3","e1-e3","e1-g3","e1-g1","f3-f1"
                ,"f3-f4","f3-d5","f3-h5","f3-h3","f3-d3","f3-d1","f3-h1","g1-g3","g1-e3","g1-e1");

        Collections.sort(list, Collections.reverseOrder());
        Collections.sort(allMoves, Collections.reverseOrder());

        assertEquals(list, allMoves);
    }


    /**
     * Tests if List with Moves contain the same moves
     *
     */
    @Test
    public void AllMovesEqual8() {
        String FENString = "3tt2t/wt6/tw2wt2/5w2/T2W1T2/WW1W1WW1/1T2T1T1 g";
        boolean equal = true;
        testGenerator = new MoveGenerator();
        ArrayList<String> allMoves = testGenerator.calculateAllMoves(FENString);
        ArrayList<String> wrongMoves = new ArrayList<String>();


        List<String> list = Arrays.asList("a5-a7","a5-c7","a5-c5","a5-c3","b6-b4","b6-d4","b6-d6","d7-b7","d7-b5","d7-d5"
                ,"d7-f7","d7-f5","e7-e5","e7-c7","e7-c5","e7-g7","e7-g5","f5-h3","f5-h5","f5-h7","f5-f7","f5-d7","f5-d5"
                ,"h7-h5","h7-f5","h7-f7");

        Collections.sort(list, Collections.reverseOrder());
        Collections.sort(allMoves, Collections.reverseOrder());

        assertEquals(list, allMoves);
    }


    /**
     * Tests if List with Moves contain the same moves
     * TODO: CHeck if valid test in woki
     */
    @Test
    public void AllMovesEqual9() {
        String FENString = "3t4/4wwww/2tt4/3cC3/4TT2/WWWW4/2T5 r";
        boolean equal = true;
        testGenerator = new MoveGenerator();
        ArrayList<String> allMoves = testGenerator.calculateAllMoves(FENString);
        ArrayList<String> wrongMoves = new ArrayList<String>();


        List<String> list = Arrays.asList("c1-a1","c1-a3","c1-c3","c1-e3","c1-e1","e3-e1","e3-c1","e3-c3","e3-g5","e3-g3","e3-g1","f3-f1"
                ,"f3-d1","f3-d3","f3-f5","f3-h5","f3-h3","f3-h1","e4-e6","e4-e7","e4-g6","e4-h7","e4-g4","e4-h4","e4-c4"
                ,"e4-b4","e4-c6","e4-b7","e3-d4-1","e3-d4-2");

        Collections.sort(list, Collections.reverseOrder());
        Collections.sort(allMoves, Collections.reverseOrder());

        assertEquals(list, allMoves);
    }



    /**
     * Tests if List with Moves contain the same moves
     * TODO: CHeck if valid test in woki
     */
    @Test
    public void AllMovesEqual10() {
        String FENString = "7w/1w1w1wtW/2ttt2T/4t1w1/1WWWTwWW/1WTW2T1/8 r";
        boolean equal = true;
        testGenerator = new MoveGenerator();
        ArrayList<String> allMoves = testGenerator.calculateAllMoves(FENString);

        List<String> list = Arrays.asList("h5-f5", "h5-h3", "h5-g4", "e3-g5", "e3-f3", "e3-g1", "e3-e1", "e3-c1",
                "e3-c3", "g2-f3", "g2-e2", "c2-e2", "c2-a2", "c2-c4", "c2-a4");

        Collections.sort(list, Collections.reverseOrder());
        Collections.sort(allMoves, Collections.reverseOrder());

        assertEquals(list, allMoves);
    }



    /**
     * Tests if List with Moves contain the same moves
     * TODO: CHeck if valid test in woki
     */
    @Test
    public void AllMovesEqual11() {
        String FENString = "7w/1w1w1w1W/3w1w2/1w1ww1wW/wWwWTtW1/1WWWWWwW/2W4W r";

        boolean equal = true;
        testGenerator = new MoveGenerator();
        ArrayList<String> allMoves = testGenerator.calculateAllMoves(FENString);


        List<String> list = Arrays.asList("e3-c1", "e3-e1", "e3-g1", "e3-e4", "e3-d4", "e3-g5");

        Collections.sort(list, Collections.reverseOrder());
        Collections.sort(allMoves, Collections.reverseOrder());

        assertEquals(list, allMoves);
    }


    /**
     * Tests if List with Moves contain the same moves
     * TODO: CHeck if valid test in woki
     */
    @Test
    public void AllMovesEqual12() {
        String FENString = "tttttttt/8/8/2T5/8/8/1TTTTTTT r";
        boolean equal = true;
        testGenerator = new MoveGenerator();
        ArrayList<String> allMoves = testGenerator.calculateAllMoves(FENString);

        List<String> list = Arrays.asList("c4-a6", "c4-c6", "c4-e6", "c4-a4", "c4-e4", "c4-a2", "c4-c2", "c4-e2", "b1-b3", "b1-d3",
                "c1-a3", "c1-c3", "c1-e3", "d1-b3", "d1-d3", "d1-f3", "e1-c3", "e1-e3", "e1-g3", "f1-d3", "f1-f3", "f1-h3",
                "g1-e3", "g1-g3", "h1-f3", "h1-h3", "h1-f1", "g1-e1", "f1-h1", "f1-d1", "e1-g1", "e1-c1", "d1-f1",
                "d1-b1", "c1-e1", "c1-a1", "b1-d1");

        Collections.sort(list, Collections.reverseOrder());
        Collections.sort(allMoves, Collections.reverseOrder());

        assertEquals(list, allMoves);
    }



    /**
     * Tests if List with Moves contain the same moves
     * TODO: No valid FEN (too many gauls)

    @Test
    public void AllMovesEqual13() {
        String FENString = "tttttttt/ww6/w1w5/8/8/8/TTTTTTTT g";
        boolean equal = true;
        testGenerator = new MoveGenerator();
        ArrayList<String> allMoves = testGenerator.calculateAllMoves(FENString);
        ArrayList<String> wrongMoves = new ArrayList<String>();


        List<String> list = Arrays.asList("a7-a5", "a7-c5", "b7-b5", "b7-d5", "c7-a5", "c7-c5", "c7-e5", "d7-b5",
                "d7-d5", "d7-f5", "e7-c5", "e7-e5", "e7-g5", "f7-d5", "f7-f5", "f7-h5", "g7-e5", "g7-g5", "h7-f5",
                "h7-h5");

        Collections.sort(list, Collections.reverseOrder());
        Collections.sort(allMoves, Collections.reverseOrder());

        assertEquals(list, allMoves);
    }
     */


    /**
     * Tests if List with Moves contain the same moves
     * TODO: NOT VALID

    @Test
    public void AllMovesEqual14() {
        String FENString = "4t3/3t1w2/6w1/5t2/2t1W1T1/8/8 g";
        boolean equal = true;
        testGenerator = new MoveGenerator();
        ArrayList<String> allMoves = testGenerator.calculateAllMoves(FENString);
        ArrayList<String> wrongMoves = new ArrayList<String>();


        List<String> list = Arrays.asList("c5-c7", "c5-a5", "c5-c3", "c5-a7", "c5-a3", "c5-e7", "c5-e3", "d2-d4",
                "d2-b2", "d2-b4", "d2-f2", "e1-c1", "e1-g1", "e1-e3", "e1-g3", "f4-f2", "f4-h2", "f4-e5", "f4-f6", "f4-d4");

        Collections.sort(list, Collections.reverseOrder());
        Collections.sort(allMoves, Collections.reverseOrder());

        assertEquals(list, allMoves);
    }
     */


    /**
     * Tests if List with Moves contain the same moves
     */
    @Test
    public void AllMovesEqual15() {
        String FENString = "tttttttt/8/8/8/8/8/TTTTTTTT r";
        boolean equal = true;
        testGenerator = new MoveGenerator();
        ArrayList<String> allMoves = testGenerator.calculateAllMoves(FENString);
        ArrayList<String> wrongMoves = new ArrayList<String>();


        List<String> list = Arrays.asList("a1-a3","a1-c3","a1-c1","b1-b3","b1-d3","b1-d1","c1-a1","c1-a3","c1-c3","c1-e3","c1-e1",
                "d1-b1","d1-b3","d1-d3","d1-f3","d1-f1","e1-c1","e1-c3","e1-e3","e1-g3","e1-g1","f1-d1","f1-d3","f1-f3"
                ,"f1-h3","f1-h1","g1-e1","g1-e3","g1-g3","h1-f1","h1-f3","h1-h3");

        Collections.sort(list, Collections.reverseOrder());
        Collections.sort(allMoves, Collections.reverseOrder());

        assertEquals(list, allMoves);
    }



    /**
     * Tests if List with Moves contain the same moves
     */
    @Test
    public void AllMovesEqual16() {
        String FENString = "8/1t6/1Ww1w3/1TWw4/8/1T1w4/8 r";
        boolean equal = true;
        testGenerator = new MoveGenerator();
        ArrayList<String> allMoves = testGenerator.calculateAllMoves(FENString);
        ArrayList<String> wrongMoves = new ArrayList<String>();


        List<String> list = Arrays.asList("b2-b4","b4-c5","b4-b2");

        Collections.sort(list, Collections.reverseOrder());
        Collections.sort(allMoves, Collections.reverseOrder());

        assertEquals(list, allMoves);
    }



    /**
     * Tests if List with Moves contain the same moves
     */
    @Test
    public void AllMovesEqual17() {
        String FENString = "8/1t6/1Ww1w3/1TWw4/8/1T1w4/8 g";
        boolean equal = true;
        testGenerator = new MoveGenerator();
        ArrayList<String> allMoves = testGenerator.calculateAllMoves(FENString);
        ArrayList<String> wrongMoves = new ArrayList<String>();


        List<String> list = Arrays.asList("b6-b5","b6-d6","b6-d4");

        Collections.sort(list, Collections.reverseOrder());
        Collections.sort(allMoves, Collections.reverseOrder());

        assertEquals(list, allMoves);
    }


    /**
     * Tests if List with Moves contain the same moves
     * TODO: CHeck if valid test in woki
     */
    @Test
    public void NoMovePossible() {
        String FENString = "3w4/tT2w3/TT6/8/8/2W5/1WW2W2 g";
        boolean equal = true;


        assertThrows(RuntimeException.class, () -> {
            testGenerator = new MoveGenerator();
            ArrayList<String> allMoves = testGenerator.calculateAllMoves(FENString);
        });
    }






    // --------------------------------------------------- Testing Invalid FEN Strings -----------------------------------------


    /**
     * Tests if Movegenerator throws exception for invalid FENStrings
     * @exception "FENString not valid. Doesn't contain 6 '/'"

    @Test()
    private void MissingForwardSlash() {
        // actual: "8/1t1tt1t1/1twWwwt1/1W2W3/3TW3/4TW2/6TT g"
        String FENString = "81t1tt1t1/1twWwwt1/1W2W3/3TW3/4TW2/6TT g";

        assertThrows(RuntimeException.class, () -> {
            testGenerator = new MoveGenerator(FENString);
        });
    }
    */

    /**
     * Tests if Movegenerator throws exception for invalid FENStrings
     * @exception "FEN String doesn't end with r or g!"
     */
    @Test()
    private void MissingPlayer() {
        // actual: "8/1t1tt1t1/1twWwwt1/1W2W3/3TW3/4TW2/6TT g"
        String FENString = "8/1t1tt1t1/1twWwwt1/1W2W3/3TW3/4TW2/6TT";

        assertThrows(RuntimeException.class, () -> {
            testGenerator = new MoveGenerator(FENString);
        });
    }


    /**
     * Tests if Movegenerator throws exception for invalid FENStrings
     * @exception "No blank between FENString Configuration and Player (r/g)."
     */
    @Test()
    private void MissingBlankBeforePlayer() {
        // actual: "8/1t1tt1t1/1twWwwt1/1W2W3/3TW3/4TW2/6TT g"
        String FENString = "8/1t1tt1t1/1twWwwt1/1W2W3/3TW3/4TW2/6TTg";

        assertThrows(RuntimeException.class, () -> {
            testGenerator = new MoveGenerator(FENString);
        });
    }

    /**
     * Tests if the method gaulsHaveWon() works correctly
     */
    @Test
    void gaulWinMethod() {
        String FENString = "8/8/8/8/8/8/7w r";

        testSelector = new MoveSelector(FENString);
        assertTrue(testSelector.gaulsHaveWon());

        FENString = "8/8/8/8/8/8/6w1 r";

        testSelector = new MoveSelector(FENString);
        assertTrue(testSelector.gaulsHaveWon());

        FENString = "8/8/8/8/8/8/5w2 r";

        testSelector = new MoveSelector(FENString);
        assertTrue(testSelector.gaulsHaveWon());

        FENString = "8/8/8/8/8/8/4w3 r";

        testSelector = new MoveSelector(FENString);
        assertTrue(testSelector.gaulsHaveWon());

        FENString = "8/8/8/8/8/8/3w4 r";

        testSelector = new MoveSelector(FENString);
        assertTrue(testSelector.gaulsHaveWon());

        FENString = "8/8/8/8/8/8/2w5 r";

        testSelector = new MoveSelector(FENString);
        assertTrue(testSelector.gaulsHaveWon());

        FENString = "8/8/8/8/8/8/1w6 r";

        testSelector = new MoveSelector(FENString);
        assertTrue(testSelector.gaulsHaveWon());
        FENString = "8/8/8/8/8/8/w7 r";

        testSelector = new MoveSelector(FENString);
        assertTrue(testSelector.gaulsHaveWon());

        FENString = "8/8/8/8/8/w6w/8 r";

        testSelector = new MoveSelector(FENString);
        assertTrue(testSelector.gaulsHaveWon());
    }
    /**
     * Tests if the method gaulsHaveWon() works correctly
     */
    @Test
    void romanWinMethod() {
        String FENString = "W7/8/8/8/8/8/8 g";

        testSelector = new MoveSelector(FENString);
        assertTrue(testSelector.romansHaveWon());

        FENString = "1W6/8/8/8/8/8/8 g";

        testSelector = new MoveSelector(FENString);
        assertTrue(testSelector.romansHaveWon());

        FENString = "2W5/8/8/8/8/8/8 g";

        testSelector = new MoveSelector(FENString);
        assertTrue(testSelector.romansHaveWon());

        FENString = "3W4/8/8/8/8/8/8 g";

        testSelector = new MoveSelector(FENString);
        assertTrue(testSelector.romansHaveWon());

        FENString = "4W3/8/8/8/8/8/8 g";

        testSelector = new MoveSelector(FENString);
        assertTrue(testSelector.romansHaveWon());

        FENString = "5W2/8/8/8/8/8/8 g";

        testSelector = new MoveSelector(FENString);
        assertTrue(testSelector.romansHaveWon());

        FENString = "6W1/8/8/8/8/8/8 g";

        testSelector = new MoveSelector(FENString);
        assertTrue(testSelector.romansHaveWon());

        FENString = "7W/8/8/8/8/8/8 g";

        testSelector = new MoveSelector(FENString);
        assertTrue(testSelector.romansHaveWon());

        FENString = "8/W6W/8/8/8/8/8 g";

        testSelector = new MoveSelector(FENString);
        assertFalse(testSelector.romansHaveWon());
    }
// --------------------------------------------------- Testing createNewState -----------------------------------------
  /**
   * Tests if createNewState returns the correct State after doing a move
   */
  @Test()
  public void simpleMoves() {
    String FENString = "tttttttt/8/8/8/8/8/TTTTTTTT r";
    String move = "a1-a3";
    String expectedState = "tttttttt/8/8/8/W7/W7/1TTTTTTT g";
    String actualState;
    MoveSelector moveSelector = new MoveSelector();

    actualState = moveSelector.createNewState(FENString,move, 1,0L);
    assertEquals(expectedState, actualState);

    move = "f1-d3";
    expectedState = "tttttttt/8/8/8/3W4/4W3/TTTTT1TT g";
    actualState = moveSelector.createNewState(FENString,move, 1,0L);
    assertEquals(expectedState, actualState);

    move = "h1-h3";
    expectedState = "tttttttt/8/8/8/7W/7W/TTTTTTT1 g";
    actualState = moveSelector.createNewState(FENString,move, 1,0L);
    assertEquals(expectedState, actualState);

    move = "d1-b1";
    expectedState = "tttttttt/8/8/8/8/8/TCC1TTTT g";
    actualState = moveSelector.createNewState(FENString,move, 1,0L);
    assertEquals(expectedState, actualState);

    move = "b1-d3";
    expectedState = "tttttttt/8/8/8/3W4/2W5/T1TTTTTT g";
    actualState = moveSelector.createNewState(FENString,move, 1,0L);
    assertEquals(expectedState, actualState);

    move = "f1-f3";
    expectedState = "tttttttt/8/8/8/5W2/5W2/TTTTT1TT g";
    actualState = moveSelector.createNewState(FENString,move, 1,0L);
    assertEquals(expectedState, actualState);

  }

  /**
   * Tests if createNewState returns the correct State after doing a move
   */
  @Test()
  public void complexMoves() {
    String FENString = "8/1t6/1Ww1w3/1TWw4/8/1T1w4/8 r";
    String move = "b2-b4";
    String expectedState = "8/1t6/1Ww1w3/1CWw4/1W6/3w4/8 g";
    String actualState;
    MoveSelector moveSelector = new MoveSelector();

    actualState = moveSelector.createNewState(FENString,move, 1,0L);
    assertEquals(expectedState, actualState);

    move = "b4-c5";
    expectedState = "8/1t6/1W2w3/1WWw4/8/1T1w4/8 g";
    actualState = moveSelector.createNewState(FENString,move, 1,0L);
    assertEquals(expectedState, actualState);

    move = "b4-b2";
    expectedState = "8/1t6/1Ww1w3/2Ww4/1W6/1C1w4/8 g";
    actualState = moveSelector.createNewState(FENString,move, 1,0L);
    assertEquals(expectedState, actualState);

    FENString = "8/1t1tt1t1/1twWwwt1/1W2W3/3TW3/4TW2/6TT g";
    move = "b6-d4";
    expectedState = "8/3tt1t1/1ttWwwt1/1W1wW3/3TW3/4TW2/6TT r";
    actualState = moveSelector.createNewState(FENString,move, -1,0L);
    assertEquals(expectedState, actualState);

    move = "b5-b4";
    expectedState = "8/1t1tt1t1/1wwWwwt1/4W3/3TW3/4TW2/6TT r";
    actualState = moveSelector.createNewState(FENString,move, -1,0L);
    assertEquals(expectedState, actualState);

    move = "d6-f6";
    expectedState = "8/1t2cwt1/1twWwwt1/1W2W3/3TW3/4TW2/6TT r";
    actualState = moveSelector.createNewState(FENString,move, -1,0L);
    assertEquals(expectedState, actualState);
  }

  /**
   * Tests if createNewState returns the correct State after doing a move
   */
  @Test
  public void advancedMoves(){
    //Tower Sacrificy -1 roman
    String FENString =     "3t4/4wwww/2tt4/3cC3/4TT2/WWWW4/2T5 r";
    String move = "e3-d4-1";
    String expectedState = "3t4/4wwww/2tt4/3tC3/4WT2/WWWW4/2T5 g";
    String actualState;
    MoveSelector moveSelector = new MoveSelector();

    actualState = moveSelector.createNewState(FENString,move, 1,0L);
    assertEquals(expectedState, actualState);

    //Tower Sacrificy -2 roman
    move = "e3-d4-2";
    expectedState = "3t4/4wwww/2tt4/3wC3/5T2/WWWW4/2T5 g";
    actualState = moveSelector.createNewState(FENString,move, 1,0L);
    assertEquals(expectedState, actualState);

    //Catapult
    move = "e4-b7";
    expectedState = "1W1t4/4wwww/2tt4/3cT3/4TT2/WWWW4/2T5 g";
    actualState = moveSelector.createNewState(FENString,move, 1,0L);
    assertEquals(expectedState, actualState);

    //Catapult
    move = "e4-c6";
    expectedState = "3t4/2W1wwww/2tt4/3cT3/4TT2/WWWW4/2T5 g";
    actualState = moveSelector.createNewState(FENString,move, 1,0L);
    assertEquals(expectedState, actualState);

    //Catapult
    move = "e4-b4";
    expectedState = "3t4/4wwww/2tt4/1W1cT3/4TT2/WWWW4/2T5 g";
    actualState = moveSelector.createNewState(FENString,move, 1,0L);
    assertEquals(expectedState, actualState);

    //Catapult
    move = "e4-c4";
    expectedState = "3t4/4wwww/2tt4/2WcT3/4TT2/WWWW4/2T5 g";
    actualState = moveSelector.createNewState(FENString,move, 1,0L);
    assertEquals(expectedState, actualState);

    //Catapult
    move = "e4-h4";
    expectedState = "3t4/4wwww/2tt4/3cT2W/4TT2/WWWW4/2T5 g";
    actualState = moveSelector.createNewState(FENString,move, 1,0L);
    assertEquals(expectedState, actualState);

    //Catapult
    move = "e4-g4";
    expectedState = "3t4/4wwww/2tt4/3cT1W1/4TT2/WWWW4/2T5 g";
    actualState = moveSelector.createNewState(FENString,move, 1,0L);
    assertEquals(expectedState, actualState);

    //Catapult
    move = "e4-h7";
    expectedState = "3t3W/4wwww/2tt4/3cT3/4TT2/WWWW4/2T5 g";
    actualState = moveSelector.createNewState(FENString,move, 1,0L);
    assertEquals(expectedState, actualState);

    //Catapult
    move = "e4-g6";
    expectedState = "3t4/4ww1w/2tt4/3cT3/4TT2/WWWW4/2T5 g";
    actualState = moveSelector.createNewState(FENString,move, 1,0L);
    assertEquals(expectedState, actualState);

    //Catapult
    move = "e4-e7";
    expectedState = "3tW3/4wwww/2tt4/3cT3/4TT2/WWWW4/2T5 g";
    actualState = moveSelector.createNewState(FENString,move, 1,0L);
    assertEquals(expectedState, actualState);

    //Catapult
    move = "e4-e6";
    expectedState = "3t4/5www/2tt4/3cT3/4TT2/WWWW4/2T5 g";
    actualState = moveSelector.createNewState(FENString,move, 1,0L);
    assertEquals(expectedState, actualState);

    //Catapult attack Tower
    FENString =     "3t4/4twww/2tt4/3tC3/4TT2/WWWW4/2T5 r";
    move = "e4-e6";
    expectedState = "3t4/4wwww/2tt4/3tT3/4TT2/WWWW4/2T5 g";
    actualState = moveSelector.createNewState(FENString,move, 1,0L);
    assertEquals(expectedState, actualState);

    //Catapult attack Catapult
    FENString =     "3t4/4cwww/2tt4/3wC3/4TT2/WWWW4/2T5 r";
    move = "e4-e6";
    expectedState = "3t4/4twww/2tt4/3wT3/4TT2/WWWW4/2T5 g";
    actualState = moveSelector.createNewState(FENString,move, 1,0L);
    assertEquals(expectedState, actualState);

    //Tower Sacrificy-1 Gaul
    FENString = "3t4/4wwww/2tt4/3cC3/4TT2/WWWW4/2T5 g";
    move = "d5-e4-1";
    expectedState = "3t4/4wwww/2tw4/3cT3/4TT2/WWWW4/2T5 r";
    actualState = moveSelector.createNewState(FENString,move, -1,0L);
    assertEquals(expectedState, actualState);

    //Tower Sacrificy-2 Gaul
    move = "d5-e4-2";
    expectedState = "3t4/4wwww/2t5/3cW3/4TT2/WWWW4/2T5 r";
    actualState = moveSelector.createNewState(FENString,move, -1,0L);
    assertEquals(expectedState, actualState);
  }

    @Test
    public void superEasyBarelyAnInconvenienceTest() {
        // Gauls have a guaranteed win
        MoveSelector moveSelector = new MoveSelector();

        String move = moveSelector.getMove("8/8/8/8/t7/8/6WT g", 5);
        System.out.println(move);
        assertTrue(move.equals("a3-a1") || move.equals("a3-c1"));
    }

    @Test
    public void easyWinIn2Test() {
        // Gauls have a guaranteed win
        MoveSelector moveSelector = new MoveSelector();

        String move = moveSelector.getMove("8/8/8/t7/w7/8/6WT g", 5);
        System.out.println(move);
        assertEquals("a4-a2", move);
    }

  @Test
  public void testEasyWinIn3() {
    // Gauls have a guaranteed win
    MoveSelector moveSelector = new MoveSelector();

        String move = moveSelector.getMove("8/6W1/t1t5/8/8/8/6WT g", 5);
        System.out.println(move);
        assertTrue(move.equals("a5-a3") || move.equals("c5-a3") || move.equals("a5-c3") || move.equals("c5-c3"));

      switch (move) {
          case "a5-a3":
              move = moveSelector.getMove("8/6W1/2t5/w7/w7/8/6WT g", 3);
              assertEquals("c5-a3", move);
              move = moveSelector.getMove("8/6W1/8/ww6/t7/8/6WT g", 1);
              assertTrue(move.equals("a3-a1") || move.equals("a3-c1"));
              break;
          case "c5-a3":
              move = moveSelector.getMove("8/6W1/t7/1w6/w7/8/6WT g", 3);
              assertEquals("a5-a3", move);
              move = moveSelector.getMove("8/6W1/8/ww6/t7/8/6WT g", 1);
              assertTrue(move.equals("a3-a1") || move.equals("a3-c1"));
              break;
          case "a5-c3":
              move = moveSelector.getMove("8/6W1/2t5/1w6/2w5/8/6WT g", 3);
              assertEquals("c5-c3", move);
              move = moveSelector.getMove("8/6W1/8/1ww5/2t5/8/6WT g", 1);
              assertTrue(move.equals("c3-a1") || move.equals("c3-c1") || move.equals("c3-e1"));
              break;
          default:
              move = moveSelector.getMove("8/6W1/t7/2w5/2w5/8/6WT g", 3);
              assertEquals("a5-c3", move);
              move = moveSelector.getMove("8/6W1/8/1ww5/2t5/8/6WT g", 1);
              assertTrue(move.equals("c3-a1") || move.equals("c3-c1") || move.equals("c3-e1"));
              break;
      }
    }




    // ------------------------------------------------------------ Heatmap tests -------------------------------------------------------
/*
    @Test
    //todo obsolete test??
    public void heatMapIndex100() {

        String fen = "8/6W1/8/ww2TTTt/t3TTT1/1WW5/8 g";
        testSelector = new MoveSelector(fen);

        int player = testSelector.getPlayer();

        if(player == 1) {
            List<Integer> heatmap = testSelector.heatmapR;
            assertEquals(100, heatmap.get(15));
            assertEquals(100, heatmap.get(14));
            assertEquals(100, heatmap.get(13));
            assertEquals(100, heatmap.get(10));
            assertEquals(100, heatmap.get(9));
            assertEquals(100, heatmap.get(8));


        } else {
            List<Integer> heatmap = testSelector.getHeatmapG();
            assertEquals(100, heatmap.get(47));
            assertEquals(100, heatmap.get(46));
            assertEquals(100, heatmap.get(45));
            assertEquals(100, heatmap.get(42));
            assertEquals(100, heatmap.get(41));
            assertEquals(100, heatmap.get(40));
        }

    }



    @Test
    public void heatMapIndexZero() {

        String fen = "8/6W1/8/ww2TTTt/t3TTT1/1WW5/8 g";

        testSelector = new MoveSelector(fen);
        int player = testSelector.getPlayer();

        if(player == 1) {
            List<Integer> heatmap = testSelector.getHeatmapR();
            for(int i = 0; i < 8; i ++) {
                assertEquals(0, heatmap.get(i));
            }

            for(int i = 40; i < 56; i ++) {
                assertEquals(0, heatmap.get(i));
            }



        } else {
            List<Integer> heatmap = testSelector.getHeatmapG();
            for(int i = 48; i < 56; i ++) {
                assertEquals(0, heatmap.get(i));

            }

            for(int i = 0; i < 16; i ++) {
                assertEquals(0, heatmap.get(i));

            }
        }

    }


    @Test
    public void heatMapIndex200() {

        String fen = "8/6W1/8/ww2TTTt/t3TTT1/1WW5/8 g";

        testSelector = new MoveSelector(fen);
        int player = testSelector.getPlayer();

        if(player == 1) {
            List<Integer> heatmap = testSelector.getHeatmapR();
            for(int i = 16; i < 24; i ++) {
                if( i == 19 || i == 20)
                    continue;
                assertEquals(200, heatmap.get(i));
            }

            assertEquals(200, heatmap.get(12));
            assertEquals(200, heatmap.get(11));





        } else {
            List<Integer> heatmap = testSelector.getHeatmapG();
            for(int i = 32; i < 40; i ++) {
                if( i == 36 || i == 35)
                    continue;
                assertEquals(200, heatmap.get(i));
            }

            assertEquals(200, heatmap.get(44));
            assertEquals(200, heatmap.get(43));
        }
    }


    @Test
    public void heatMapIndex250() {

        String fen = "8/6W1/8/ww2TTTt/t3TTT1/1WW5/8 g";

        testSelector = new MoveSelector(fen);
        int player = testSelector.getPlayer();

        if(player == 1) {
            List<Integer> heatmap = testSelector.getHeatmapR();
            assertEquals(250, heatmap.get(20));
            assertEquals(250, heatmap.get(19));

        } else {
            List<Integer> heatmap = testSelector.getHeatmapG();
            assertEquals(250, heatmap.get(36));
            assertEquals(250, heatmap.get(35));
        }
    }


 // ------------------------------------------------ Test valueFunction -------------------------------------------------

    @Test
    public void valueFunctionOnlyTowers() {

        String fen = "8/1t1tt1t1/1twWwwt1/1W2W3/3TW3/4TW2/6TT g";

        testSelector = new MoveSelector(fen);
        List<Integer> heatmap = testSelector.getHeatmapG();
        int player = testSelector.getPlayer();

        assertEquals(250, testSelector.valueFunction(player));
    }



    @Test
    public void valueFunctionOnlyTowers2() {

        String fen = "8/1t1tt1t1/1twWwwt1/1W2W3/3TW3/4TW2/6TT r";

        testSelector = new MoveSelector(fen);
        List<Integer> heatmap = testSelector.getHeatmapR();
        int player = testSelector.getPlayer();

        assertEquals(112, testSelector.valueFunction(player));
    }



    @Test
    public void valueFunctionCatapult() {

        String fen = "7w/1t1tt1t1/1twWwwt1/1W2W3/3TW3/4CW2/6TT r";

        testSelector = new MoveSelector(fen);
        List<Integer> heatmap = testSelector.getHeatmapR();
        int player = testSelector.getPlayer();

        assertEquals(262, testSelector.valueFunction(player));
    }


*/


    @Test
    public void winningMove1_1Step() {

        String fen = "1t6/t1W3w1/1Ww5/w2ww3/tw1w4/wTT5/2WWTT1T g";

        String move = testSelector.getMove(fen, 3);
        System.out.println("winner move: " + move);
        assertEquals("a3-a1", move);
    }


    @Test
    public void winningMove2_1Step() {

        String fen = "1ww1w1w1/2www1w1/3ww2w/4CWtw/4WTWw/8/TTT4W r";

        String move = testSelector.getMove(fen, 3);
        System.out.println("winner move: " + move);
        //todo e4-g4 ist auch gültig da die gauler keine züge mehr ziehen können.
        assertEquals("e4-h7", move);
    }


    @Test
    public void winningMove3_1Step() {

        String fen = "tt6/6w1/twt3T1/3twTT1/W1Tt1T2/1TW1W3/8 r";

        String move = testSelector.getMove(fen, 3);
        System.out.println("winner move: " + move);
        assertEquals("g5-e7", move);
    }

    @Test
    public void winningMove4_1Step() {

        String fen = "2wwwww1/3tct2/1w1TT1w1/W1TC2t1/4T3/8/3T3T r";

        String move = testSelector.getMove(fen, 3);
        System.out.println("winner move: " + move);
        assertEquals("d5-b7", move);
    }


    @Test
    public void winningMove5_1Step() {

        String fen = "ttttw3/5tc1/4Ww1w/2W1C3/2TC1TW1/3W4/T7 r";

        String move = testSelector.getMove(fen, 3);
        System.out.println("winner move: " + move);
        assertEquals("e4-h7", move);
    }



    @Test
    public void winningMove1_2Steps() {

        String fen = "tt6/6w1/twtt2W1/3wwWT1/W1TwTT2/1TW1W3/8 r";

        String move = testSelector.getMove(fen, 5);
        System.out.println("First move Roman: " + move);


        String newFen = testSelector.createNewState(fen, move, 1,0L);
        String gaule_move = testSelector.getMove(newFen, 3);
        System.out.println("Move Gaule: " + gaule_move);


        String newFen2 = testSelector.createNewState(newFen, gaule_move, 1,0L);
        String move2 = testSelector.getMove(newFen2, 3);
        System.out.println("Second move Roman: " + move2);

        String newFen3 = testSelector.createNewState(newFen2, move2, 1,0L);
        String move3 = testSelector.getMove(newFen3, 3);
        System.out.println("Third move Gaul: " + move3);


        String newFen4 = testSelector.createNewState(newFen3, move3, 1,0L);
        String move4 = testSelector.getMove(newFen4, 3);
        System.out.println("4th move Roman: " + move4);



    }



    @Test
    public void winningMove2_2Steps() {

        String fen = "2wwwww1/3tctt1/1w1TT3/W1WTT1w1/4T3/8/3T3T r";

        String move = testSelector.getMove(fen, 3);
        System.out.println("First move Roman: " + move);
        String gaule_move = testSelector.getMove(move, 3);
        System.out.println("Move Gaule: " + gaule_move);
        System.out.println("Second move Roman: " + testSelector.getMove(gaule_move, 3));
    }



    @Test
    public void winningMove3_2Steps() {

        String fen = "2wwwww1/3tctt1/1w1TT3/W1WTT1w1/4T3/8/3T3T r";

        String move = testSelector.getMove(fen, 3);
        System.out.println("First move Roman: " + move);
        String gaule_move = testSelector.getMove(move, 3);
        System.out.println("Move Gaule: " + gaule_move);
        System.out.println("Second move Roman: " + testSelector.getMove(gaule_move, 3));
    }



    @Test
    public void winningMove4_2Steps() {

        String fen = "tttt4/5wc1/5wtw/2W1T3/2TCTTW1/3W4/T7 r";

        String move = testSelector.getMove(fen, 3);
        System.out.println("First move Roman: " + move);
        String gaule_move = testSelector.getMove(move, 3);
        System.out.println("Move Gaule: " + gaule_move);
        System.out.println("Second move Roman: " + testSelector.getMove(gaule_move, 3));
    }



    @Test
    public void winningMove1_3Steps() {

        String fen = "tt6/6w1/twtt1tW1/3w1WT1/W1TTWW2/1TW1W3/8 r";

        String move = testSelector.getMove(fen, 3);
        System.out.println("First move Roman: " + move);

        String gaule_move = testSelector.getMove(move, 3);
        System.out.println("First Move Gaule: " + gaule_move);

        String secondMoveRoman = testSelector.getMove(gaule_move, 3);
        System.out.println("Second move Roman: " + secondMoveRoman);


        String secondMoveGaule = testSelector.getMove(secondMoveRoman, 3);
        System.out.println("Second move Gaule: " + secondMoveGaule);

        String winningMove = testSelector.getMove(secondMoveGaule, 3);
        System.out.println("Winning move Roman: " + winningMove);

    }




    @Test
    public void winningMove2_3Steps() {

        String fen = "2wwwww1/2twttt1/1w1TT3/W1WWWTw1/4T3/8/3T3T r";

        String move = testSelector.getMove(fen, 3);
        System.out.println("First move Roman: " + move);

        String gaule_move = testSelector.getMove(move, 3);
        System.out.println("First Move Gaule: " + gaule_move);

        String secondMoveRoman = testSelector.getMove(gaule_move, 3);
        System.out.println("Second move Roman: " + secondMoveRoman);


        String secondMoveGaule = testSelector.getMove(secondMoveRoman, 3);
        System.out.println("Second move Gaule: " + secondMoveGaule);

        String winningMove = testSelector.getMove(secondMoveGaule, 3);
        System.out.println("Winning move Roman: " + winningMove);

    }



    @Test
    public void winningMove3_3Steps() {

        String fen = "ttttt3/6c1/5www/4T3/2WCTTW1/2TW4/T7 r";

        String move = testSelector.getMove(fen, 3);
        System.out.println("First move Roman: " + move);

        String gaule_move = testSelector.getMove(move, 3);
        System.out.println("First Move Gaule: " + gaule_move);

        String secondMoveRoman = testSelector.getMove(gaule_move, 3);
        System.out.println("Second move Roman: " + secondMoveRoman);


        String secondMoveGaule = testSelector.getMove(secondMoveRoman, 3);
        System.out.println("Second move Gaule: " + secondMoveGaule);

        String winningMove = testSelector.getMove(secondMoveGaule, 3);
        System.out.println("Winning move Roman: " + winningMove);

    }











}
