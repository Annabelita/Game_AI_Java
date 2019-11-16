import board.Bitboard;
import board.Parser;
import board.Pieces;
import org.junit.jupiter.api.BeforeAll;
import static  org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Andrej Savinov
 */
class BitboardDefaultTests {

  /**
   * Board used for tests
   *
   */
  private static Bitboard testBoard;

  /**
   * Basic isPieceAtPosTest
   */
  @Test
  public void isPieceAtPosTest(){
    String fen = "c7/1t1tt1t1/1twWwwt1/1W2W3/3TW3/4TW2/5CTT r";

    testBoard = Parser.fenToBoard(fen);

    assertEquals(true,testBoard.isPieceAtPos(Pieces.WALL_ROMANS,10));
    assertEquals(true,testBoard.isPieceAtPos(Pieces.WALL_GAULS,37));
    assertEquals(true,testBoard.isPieceAtPos(Pieces.TOWER_ROMANS,0));
    assertEquals(true,testBoard.isPieceAtPos(Pieces.TOWER_GAULS,46));
    assertEquals(true,testBoard.isPieceAtPos(Pieces.CATAPULT_ROMANS,2));
    assertEquals(true,testBoard.isPieceAtPos(Pieces.CATAPULT_GAULS,55));
  }

  /**
   * Basic getBirboardTest
   */
  @Test
  public void getBitboardTest(){
    String fen = "6cw/1w1w1wtW/2ttt2T/4t1w1/1WWWTwWW/1WTW2T1/7C r";

    testBoard = Parser.fenToBoard(fen);
    long wall_romans = 0x10000735000L;
    long wall_gauls = 0x1540002040000L;
    long tower_romans = 0x100082200L;
    long tower_gauls = 0x23808000000L;
    long catapult_romans = 0x1L;
    long catapult_gauls = 0x2000000000000L;
    long all_romans = wall_romans | tower_romans | catapult_romans;
    long all_gauls = wall_gauls | tower_gauls | catapult_gauls;
    long all_figures = all_romans | all_gauls;

    assertEquals(wall_romans,testBoard.getBitboard(Pieces.WALL_ROMANS));
    assertEquals(wall_gauls,testBoard.getBitboard(Pieces.WALL_GAULS));
    assertEquals(tower_romans,testBoard.getBitboard(Pieces.TOWER_ROMANS));
    assertEquals(tower_gauls,testBoard.getBitboard(Pieces.TOWER_GAULS));
    assertEquals(catapult_romans,testBoard.getBitboard(Pieces.CATAPULT_ROMANS));
    assertEquals(catapult_gauls,testBoard.getBitboard(Pieces.CATAPULT_GAULS));
    assertEquals(all_romans,testBoard.getBitboard(Pieces.ALL_ROMAN));
    assertEquals(all_gauls,testBoard.getBitboard(Pieces.ALL_GAUL));
    assertEquals(all_figures,testBoard.getBitboard(Pieces.ALL_FIGURES));
  }

  /**
   * Basic getAllPiecesIndex Test
   */
  @Test
  public void getAllPiecesIndexTest(){
    String fen = "7c/1t1tt1t1/1twWwwt1/1W2W3/3TW3/4TW2/5CTT r";
    testBoard = Parser.fenToBoard(fen);
    ArrayList<Integer> expectedRomanWall = new ArrayList<Integer>( Arrays.asList(36,30,27,19,10));
    ArrayList<Integer> expectedGaulWall = new ArrayList<Integer>( Arrays.asList(37,35,34));
    ArrayList<Integer> expectedRomanTower = new ArrayList<Integer>( Arrays.asList(20,11,1,0));
    ArrayList<Integer> expectedGaulTower = new ArrayList<Integer>( Arrays.asList(46,44,43,41,38,33));
    ArrayList<Integer> expectedRomanCatapult = new ArrayList<Integer>( Arrays.asList(2));
    ArrayList<Integer> expectedGaulCatapult = new ArrayList<Integer>( Arrays.asList(48));

    assertEquals(expectedRomanWall,testBoard.getAllPiecesIndex(Pieces.WALL_ROMANS));
    assertEquals(expectedGaulWall,testBoard.getAllPiecesIndex(Pieces.WALL_GAULS));
    assertEquals(expectedRomanTower,testBoard.getAllPiecesIndex(Pieces.TOWER_ROMANS));
    assertEquals(expectedGaulTower,testBoard.getAllPiecesIndex(Pieces.TOWER_GAULS));
    assertEquals(expectedRomanCatapult,testBoard.getAllPiecesIndex(Pieces.CATAPULT_ROMANS));
    assertEquals(expectedGaulCatapult,testBoard.getAllPiecesIndex(Pieces.CATAPULT_GAULS));
  }

  @Test
  public void parserFunctionality(){
    String fen = "5ww1/2w1wwW1/w1w1wWT1/2wt3W/1wWwwW2/2Tw1WW1/T6T r";

    testBoard = Parser.fenToBoard(fen);
    assertEquals(fen,Parser.boardToFen(testBoard,true));

    fen = "t4t1t/3ttw2/2wt4/4w3/W1TW1T2/W1TT1WW1/6T1 g";

    testBoard = Parser.fenToBoard(fen);
    assertEquals(fen,Parser.boardToFen(testBoard,false));

    fen = "ttttt1tt/8/5t2/8/8/8/TTTTTTTT g";

    testBoard = Parser.fenToBoard(fen);
    assertEquals(fen,Parser.boardToFen(testBoard,false));

    fen = "tt1ttttt/1w6/w7/8/W6W/W6W/1TTTTTT1 g";

    testBoard = Parser.fenToBoard(fen);
    assertEquals(fen,Parser.boardToFen(testBoard,false));

    fen = "8/1t1tt1t1/1twWwwt1/1W2W3/3TW3/4TW2/6TT r";

    testBoard = Parser.fenToBoard(fen);
    assertEquals(fen,Parser.boardToFen(testBoard,true));

    fen = "8/1t1tt1t1/1twWwwt1/1W2W3/3TW3/4TW2/6TT g";

    testBoard = Parser.fenToBoard(fen);
    assertEquals(fen,Parser.boardToFen(testBoard,false));

    fen = "3tt2t/wt6/tw2wt2/5w2/T2W1T2/WW1W1WW1/1T2T1T1 r";

    testBoard = Parser.fenToBoard(fen);
    assertEquals(fen,Parser.boardToFen(testBoard,true));

    fen = "3tt2t/wt6/tw2wt2/5w2/T2W1T2/WW1W1WW1/1T2T1T1 g";

    testBoard = Parser.fenToBoard(fen);
    assertEquals(fen,Parser.boardToFen(testBoard,false));

    fen = "3t4/4wwww/2tt4/3cC3/4TT2/WWWW4/2T5 r";

    testBoard = Parser.fenToBoard(fen);
    assertEquals(fen,Parser.boardToFen(testBoard,true));

    fen = "7w/1w1w1wtW/2ttt2T/4t1w1/1WWWTwWW/1WTW2T1/8 r";

    testBoard = Parser.fenToBoard(fen);
    assertEquals(fen,Parser.boardToFen(testBoard,true));

    fen = "7w/1w1w1w1W/3w1w2/1w1ww1wW/wWwWTtW1/1WWWWWwW/2W4W r";

    testBoard = Parser.fenToBoard(fen);
    assertEquals(fen,Parser.boardToFen(testBoard,true));

    fen = "tttttttt/8/8/2T5/8/8/1TTTTTTT r";

    testBoard = Parser.fenToBoard(fen);
    assertEquals(fen,Parser.boardToFen(testBoard,true));
  }
}
