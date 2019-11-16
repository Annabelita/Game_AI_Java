import move.MoveGenerator;
import move.MoveSelector;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ValueFunctionTests {
  static MoveSelector testSelector = new MoveSelector();

  /**
   * Tests if getmove can find win positions
   */
  @Test
  public void oneMoveWin() {
    String FENString = "1t6/t1W3w1/1Ww5/w2ww3/tw1w4/wTT5/2WWTT1T g";
    String move = testSelector.getMove(FENString,1);
    assertEquals("a3-a1",move);

    FENString = "1ww1w1w1/2www1w1/3ww2w/4CWtw/4WTWw/8/TTT4W r";
    move = testSelector.getMove(FENString,1);
    assertEquals("e4-h7",move);

    FENString = "tt6/6w1/twt3T1/3twTT1/W1Tt1T2/1TW1W3/8 r";
    move = testSelector.getMove(FENString,1);
    assertEquals("g5-e7",move);

    FENString = "2wwwww1/3tct2/1w1TT1w1/W1TC2t1/4T3/8/3T3T r";
    move = testSelector.getMove(FENString,1);
    assertTrue(move.equals("d4-a7") || move.equals("d5-b7"));

    FENString = "ttttw3/5tc1/4Ww1w/2W1C3/2TC1TW1/3W4/T7 r";
    move = testSelector.getMove(FENString,1);
    assertEquals("e4-h7",move);

    FENString = "2wwwww1/3tctt1/1w1TT3/W1WTT1w1/4T3/8/3T3T r";
    move = testSelector.getMove(FENString,1);
    assertEquals("d5-b7",move);
  }

  /**
   * Tests if getmove can find win positions
   */
  @Test
  public void twoMoveWin() {
    String FENString = "tt6/6w1/twtt2W1/3wwWT1/W1TwTT2/1TW1W3/8 r";
    String move = testSelector.getMove(FENString,3);
    assertEquals("f3-h5",move);
    FENString = testSelector.createNewState(FENString,move,1,0L);
    move = testSelector.getMove(FENString,1);
    FENString = testSelector.createNewState(FENString,move,-1,0L);
    move = testSelector.getMove(FENString,1);
    assertTrue(move.equals("g4-g7") || move.equals("g4-d7"));

    FENString = "tttt4/5wc1/5wtw/2W1T3/2TCTTW1/3W4/T7 r";
    move = testSelector.getMove(FENString,5);
    assertEquals("f3-d5",move);
    FENString = testSelector.createNewState(FENString,move,1,0L);
    move = testSelector.getMove(FENString,1);
    FENString = testSelector.createNewState(FENString,move,-1,0L);
    move = testSelector.getMove(FENString,1);
    assertTrue(move.equals("e4-e7") || move.equals("e4-h7"));

    FENString = "ttttt3/6c1/5www/4T3/2WCTTW1/2TW4/T7 r";
    move = testSelector.getMove(FENString,5);
    assertEquals("f3-d5",move);
    FENString = testSelector.createNewState(FENString,move,1,0L);
    move = testSelector.getMove(FENString,1);
    FENString = testSelector.createNewState(FENString,move,-1,0L);
    move = testSelector.getMove(FENString,1);
    assertEquals("e4-h7",move);
  }

  /**
   * Tests if getmove can find win positions
   */
  @Test
  public void threeMoveWin() {
    //java -jar MGMatchViewer.jar -w "Player_Gruppe07.jar":"move.Player7" -b "Player_Gruppe02.jar":"Player2" -f "tt6/6w1/twtt1tW1/3w1WT1/W1TTWW2/1TW1W3/8 r"
    //Macht einen Move zu viel richtige moves wären: d3-f3, f3-h5, g4-g7 oder g4-d7
    String FENString = "tt6/6w1/twtt1tW1/3w1WT1/W1TTWW2/1TW1W3/8 r";
    String move = testSelector.getMove(FENString,7);
    assertEquals("b2-d2",move);
    FENString = testSelector.createNewState(FENString,move,1,0L);
    move = testSelector.getMove(FENString,1);
    FENString = testSelector.createNewState(FENString,move,-1,0L);
    move = testSelector.getMove(FENString,5);
    assertEquals("c2-e2",move);
    FENString = testSelector.createNewState(FENString,move,1,0L);
    move = testSelector.getMove(FENString,1);
    FENString = testSelector.createNewState(FENString,move,-1,0L);
    move = testSelector.getMove(FENString,5);
    assertEquals("e2-g4",move);
    FENString = testSelector.createNewState(FENString,move,1,0L);
    move = testSelector.getMove(FENString,1);
    FENString = testSelector.createNewState(FENString,move,-1,0L);
    move = testSelector.getMove(FENString,1);
    assertEquals("g4-g7",move);


    FENString = "2wwwww1/2twttt1/1w1TT3/W1WWWTw1/4T3/8/3T3T r";
    move = testSelector.getMove(FENString,7);
    assertEquals("e3-c5",move);
    FENString = testSelector.createNewState(FENString,move,1,0L);
    move = testSelector.getMove(FENString,1);
    FENString = testSelector.createNewState(FENString,move,-1,0L);
    move = testSelector.getMove(FENString,5);
    assertEquals("f4-d4",move);
    FENString = testSelector.createNewState(FENString,move,1,0L);
    move = testSelector.getMove(FENString,1);
    FENString = testSelector.createNewState(FENString,move,-1,0L);
    move = testSelector.getMove(FENString,1);
    assertEquals("d4-a7",move);
  }
}
