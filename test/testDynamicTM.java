import board.Bitboard;
import board.Parser;
import board.Pieces;
import move.Move;
import org.junit.jupiter.api.BeforeAll;
import static  org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;



public class testDynamicTM {


    /**
     * Board used for tests
     *
     */
    private static Bitboard testBoard;



    /**
     * Tests if MoveList has same amout of elements as target move List
     */
    @Test
    public void checkAmount1() {
        String FENString = "8/1t1tt1t1/1twWwwt1/1W2W3/3TW3/4TW2/6TT g";
        testBoard = Parser.fenToBoard(FENString);


        assertEquals(4,  Move.countTowers(FENString, 1));
        assertEquals(0,  Move.countCatapult(FENString, 1));

    }


    @Test
    public void checkAmount2() {
        String FENString = "8/1t1tt1t1/1twWwwt1/1W2W3/3TW3/4TW2/6TT r";
        testBoard = Parser.fenToBoard(FENString);

        assertEquals(6,  Move.countTowers(FENString, 0));
        assertEquals(0,  Move.countCatapult(FENString, 0));

    }



    @Test
    public void checkAmount3() {
        String FENString = "5ww1/2w1wwW1/w1w1wWT1/2wt3W/1wWwwW2/2Tw1WW1/T6T r";
        testBoard = Parser.fenToBoard(FENString);

        assertEquals(1,  Move.countTowers(FENString, 0));
        assertEquals(0,  Move.countCatapult(FENString, 0));

    }



    @Test
    public void checkAmount4() {
        String FENString = "5ww1/2w1wwW1/w1w1wWT1/2wt3W/1wWwwW2/2Tw1WW1/T6T g";
        testBoard = Parser.fenToBoard(FENString);

        assertEquals(4,  Move.countTowers(FENString, 1));
        assertEquals(0,  Move.countCatapult(FENString, 1));

    }


    @Test
    public void checkAmount5() {
        String FENString = "t4t1t/3ttw2/2wt4/4w3/W1TW1T2/W1TT1WW1/6T1 g";
        testBoard = Parser.fenToBoard(FENString);


        assertEquals(5,  Move.countTowers(FENString, 1));
        assertEquals(0,  Move.countCatapult(FENString, 1));

    }


    @Test
    public void checkAmount6() {
        String FENString = "t4t1t/3ttw2/2wt4/4w3/W1TW1T2/W1TT1WW1/6T1 r";
        testBoard = Parser.fenToBoard(FENString);

        assertEquals(6,  Move.countTowers(FENString, 0));
        assertEquals(0,  Move.countCatapult(FENString, 0));

    }



    @Test
    public void checkTimes() {
        String FENString = "t4t1t/3ttw2/2wt4/4w3/W1TW1T2/W1TT1WW1/6T1 r";
        testBoard = Parser.fenToBoard(FENString);

        assertEquals(6,  Move.countTowers(FENString, 0));
        assertEquals(0,  Move.countCatapult(FENString, 0));

    }






    @Test
    public void playParty() throws IOException {
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec("java -jar MS1/playerJars/Versus.jar MS1/playerJars/roundrobin.xml");


    }





}
