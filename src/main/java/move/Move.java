package move;

import board.Bitboard;
import board.Parser;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Move {





    /**
     * Calculates dynamic time management according to time
     * @param representation
     * @return
     */

    // TODO: Prüfe ob Contest Zeit immer noch 120s
    // TODO: Beziehe maximale Anzahl an Spieler mit ein
    // TODO: Benutze Bonustime
    public static int dynamicTimeManagement(String representation, int player, long time, long bonustime) {


        int playersRow1 = calculatePlayerInRow(representation, player, 1);
        int catapults = countCatapult(representation, player);
        int towers = countTowers(representation, player);
        int catapults_Towers = catapults+towers;

        //Handle panic
        if (time <= 20000){
          return panicMode(time,towers,catapults,catapults_Towers);
        }

        //Calculate Depth for first rounds
        if(time > 115000) {
            if(playersRow1 < 4 && catapults_Towers <= 4){
              return 7;
            }
            else if(playersRow1 < 6){
              return 5;
            }
            else{
              return 3;
            }
        }
        //Only 4 Figures which can be moved
        else if (catapults_Towers <= 3 && time >= 20000){
          return 7;
        }

        //Only 4 Figures which can be moved
        else if (catapults_Towers <= 4 && time >= 80000){
          return 7;
        }

        //Calculate per row
        else {
          int playersRow2 = calculatePlayerInRow(representation, player, 2);
          int playersRow3 = calculatePlayerInRow(representation, player, 3);
          int playersRow4 = calculatePlayerInRow(representation, player, 4);
          int playersRow5 = calculatePlayerInRow(representation, player, 5);
          int playersRow6 = calculatePlayerInRow(representation, player, 6);
          int row_12 = playersRow2 + playersRow1;
          int row_123 = row_12 + playersRow3;
          int row_1234 = row_123 + playersRow4;

          if(row_12 < 3 && catapults_Towers <= 5 && time >= 80000){
            return 7;
          }


          if(time > 100000){
            if( row_12 > 8)
              return 5;
          }

          if(time >= 70000 && row_123 <= 10 && catapults_Towers <= 5){
            return 5;
          }

          if(time >= 50000 && row_1234 <= 13 && catapults_Towers <= 5){
            return 5;
          }
        }
        return 5;
    }

  /**
   * Training method for evolutionary training
   * @param representation
   * @return
   */
  public static int dynamicTimeManagement(String representation, int player, long time, long bonustime,
                                          long time1, long time2, long time3, long time4, long time5,
                                          long time6, long time7, long time8) {

    int playersRow1 = calculatePlayerInRow(representation, player, 1);
    int catapults = countCatapult(representation, player);
    int towers = countTowers(representation, player);
    int catapults_Towers = catapults+towers;

    //Handle panic
    if (time <= time1){
      return panicMode(time,towers,catapults,catapults_Towers);
    }

    //Calculate Depth for first rounds
    if(time > time2) {
      if(playersRow1 < 4 && catapults_Towers <= 4){
        return 7;
      }
      else if(playersRow1 < 6){
        return 5;
      }
      else{
        return 3;
      }
    }
    //Only 4 Figures which can be moved
    else if (catapults_Towers <= 3 && time >= time3){
      return 7;
    }

    //Only 4 Figures which can be moved
    else if (catapults_Towers <= 4 && time >= time4){
      return 7;
    }

    //Calculate per row
    else {
      int playersRow2 = calculatePlayerInRow(representation, player, 2);
      int playersRow3 = calculatePlayerInRow(representation, player, 3);
      int playersRow4 = calculatePlayerInRow(representation, player, 4);
      int playersRow5 = calculatePlayerInRow(representation, player, 5);
      int playersRow6 = calculatePlayerInRow(representation, player, 6);
      int row_12 = playersRow2 + playersRow1;
      int row_123 = row_12 + playersRow3;
      int row_1234 = row_123 + playersRow4;

      if(row_12 < 3 && catapults_Towers <= 5 && time >= time5){
        return 7;
      }


      if(time > time6){
        if( row_12 > 8)
          return 5;
      }

      if(time >= time7 && row_123 <= 10 && catapults_Towers <= 5){
        return 5;
      }

      if(time >= time8 && row_1234 <= 13 && catapults_Towers <= 5){
        return 5;
      }
    }
    return 5;
  }

  /**
   * If low time amount, handle correctly
   * @param time
   * @param towers
   * @param catapults
   * @param catapults_Towers
   * @return
   */
    public static int panicMode(long time, int towers, int catapults, int catapults_Towers){
      //5 Seconds
      if(time <= 5000 && catapults_Towers == 1){
        return 7;
      }
      else if(time <= 5000 && catapults_Towers == 2){
        return 5;
      }
      else if(time <= 5000 && catapults_Towers == 3){
        return 3;
      }
      else if(time <= 800 && catapults_Towers >= 3){
        return 1;
      }
      // 10 Seconds
      else if(time <= 10000 && catapults_Towers == 1){
        return 7;
      }
      else if(time <= 10000 && catapults_Towers == 2){
        return 5;
      }
      else if(time <= 10000 && catapults_Towers == 3){
        return 3;
      }
      // 15 Seconds
      else if(time <= 15000 && catapults_Towers == 1){
        return 7;
      }
      else if(time <= 15000 && catapults_Towers == 2){
        return 5;
      }
      else if(time <= 15000 && catapults_Towers == 3){
        return 3;
      }
      // 20 Seconds
      else if(time <= 20000 && catapults_Towers == 1){
        return 7;
      }
      else if(time <= 20000 && catapults_Towers <= 3){
        return 5;
      }
      return 3;
    }

    /**
     * Calculates the amount of players in a row
     * @param fen
     * @param player
     * @param rowNumber
     * @return
     */
    public static int calculatePlayerInRow(String fen, int player, int rowNumber) {

        Bitboard board = Parser.fenToBoard(fen);
        List<Integer> row = Arrays.asList();
        int sum = 0;

        // Romans
        if(player == 0){
            if(rowNumber == 1)
                row = Arrays.asList(7, 6, 5, 4, 3, 2, 1, 0);
            if(rowNumber == 2)
                row = Arrays.asList(15, 14, 13, 12, 11, 10, 9, 8);
            if(rowNumber == 3)
                row = Arrays.asList(23, 22, 21, 20, 19, 18, 17, 16);
            if(rowNumber == 4)
                row = Arrays.asList(31, 30, 29, 28, 27, 26, 25, 24);
            if(rowNumber == 5)
                row = Arrays.asList(39, 38, 37, 36, 35, 34, 33, 32);
            if(rowNumber == 6)
                row = Arrays.asList(47, 46, 45, 44, 43, 42, 41, 40);
            if(rowNumber == 7)
                row = Arrays.asList(55, 54, 53, 52, 51, 50, 49, 48);

            for(Integer index : row) {
                if(board.whichPieceIsAtPos(index) == 0)
                    sum += 1;
                if(board.whichPieceIsAtPos(index) == 2)
                    sum += 1;
                if(board.whichPieceIsAtPos(index) == 4)
                    sum += 1;
            }
        }
        // Gauls
        else {
            if(rowNumber == 7)
                row = Arrays.asList(7, 6, 5, 4, 3, 2, 1, 0);
            if(rowNumber == 6)
                row = Arrays.asList(15, 14, 13, 12, 11, 10, 9, 8);
            if(rowNumber == 5)
                row = Arrays.asList(23, 22, 21, 20, 19, 18, 17, 16);
            if(rowNumber == 4)
                row = Arrays.asList(31, 30, 29, 28, 27, 26, 25, 24);
            if(rowNumber == 3)
                row = Arrays.asList(39, 38, 37, 36, 35, 34, 33, 32);
            if(rowNumber == 2)
                row = Arrays.asList(47, 46, 45, 44, 43, 42, 41, 40);
            if(rowNumber == 1)
                row = Arrays.asList(55, 54, 53, 52, 51, 50, 49, 48);


            for(Integer index : row) {
                if(board.whichPieceIsAtPos(index) == 1)
                    sum += 1;
                if(board.whichPieceIsAtPos(index) == 3)
                    sum += 1;
                if(board.whichPieceIsAtPos(index) == 5)
                    sum += 1;
            }
        }

        return sum;

    }


    /**
     * Counts the amount of catapults given a fen String
     * @param fen
     * @param player
     * @return
     */

    public static int countCatapult(String fen, int player){
        int count = 0;
        if(player == 0){
            for(int i = 0; i < fen.length(); i ++){
                if(fen.charAt(i) == 'C')
                    count++;
            }

        } else {
            for(int i = 0; i < fen.length(); i ++){
                if(fen.charAt(i) == 'c')
                    count++;
            }
        }
        return count;
    }


    /**
     * Counts the amount of towers given a fenString
     * @param fen
     * @param player
     * @return
     */
    public static int countTowers(String fen, int player){
        int count = 0;
        if(player == 0){
            for(int i = 0; i < fen.length(); i ++){
                if(fen.charAt(i) == 'T')
                    count++;
            }

        } else {
            for(int i = 0; i < fen.length(); i ++){
                if(fen.charAt(i) == 't')
                    count++;
            }
        }
        return count;
    }

    /**
   * Counts the amount of walls given a fenString
   * @param fen
   * @param player
   * @return
   */
  public static int countWall(String fen, int player){
    int count = 0;
    if(player == 0){
      for(int i = 0; i < fen.length(); i ++){
        if(fen.charAt(i) == 'W')
          count++;
      }

    } else {
      for(int i = 0; i < fen.length(); i ++){
        if(fen.charAt(i) == 'w')
          count++;
      }
    }
    return count;
  }


    public String indexToMoveString(int indexString) {
        switch (indexString) {
            case 0:
                return "a7";
            case 1:
                return "b7";
            case 2:
                return "c7";
            case 3:
                return "d7";
            case 4:
                return "e7";
            case 5:
                return "f7";
            case 6:
                return "g7";
            case 7:
                return "h7";
            case 8:
                return "a6";
            case 9:
                return "b6";
            case 10:
                return "c6";
            case 11:
                return "d6";
            case 12:
                return "e6";
            case 13:
                return "f6";
            case 14:
                return "g6";
            case 15:
                return "h6";
            case 16:
                return "a5";
            case 17:
                return "b5";
            case 18:
                return "c5";
            case 19:
                return "d5";
            case 20:
                return "e5";
            case 21:
                return "f5";
            case 22:
                return "g5";
            case 23:
                return "h5";
            case 24:
                return "a4";
            case 25:
                return "b4";
            case 26:
                return "c4";
            case 27:
                return "d4";
            case 28:
                return "e4";
            case 29:
                return "f4";
            case 30:
                return "g4";
            case 31:
                return "h4";
            case 32:
                return "a3";
            case 33:
                return "b3";
            case 34:
                return "c3";
            case 35:
                return "d4";
            case 36:
                return "e3";
            case 37:
                return "f3";
            case 38:
                return "g3";
            case 39:
                return "h3";
            case 40:
                return "a2";
            case 41:
                return "b2";
            case 42:
                return "c2";
            case 43:
                return "d2";
            case 44:
                return "e2";
            case 45:
                return "f2";
            case 46:
                return "g2";
            case 47:
                return "h2";
            case 48:
                return "a1";
            case 49:
                return "b1";
            case 50:
                return "c1";
            case 51:
                return "d1";
            case 52:
                return "e1";
            case 53:
                return "f1";
            case 54:
                return "g1";
            case 55:
                return "h1";
            default:
                return "Index out of bounds.";

        }
    }

    public int moveToIndex(String move) {
        switch (move) {
            case "a7":
                return 0;
            case "b7":
                return 1;
            case "c7":
                return 2;
            case "d7":
                return 3;
            case "e7":
                return 4;
            case "f7":
                return 5;
            case "g7":
                return 6;
            case "h7":
                return 7;
            case "a6":
                return 8;
            case "b6":
                return 9;
            case "c6":
                return 10;
            case "d6":
                return 11;
            case "e6":
                return 12;
            case "f6":
                return 13;
            case "g6":
                return 14;
            case "h6":
                return 15;
            case "a5":
                return 16;
            case "b5":
                return 17;
            case "c5":
                return 18;
            case "d5":
                return 19;
            case "e5":
                return 20;
            case "f5":
                return 21;
            case "g5":
                return 22;
            case "h5":
                return 23;
            case "a4":
                return 24;
            case "b4":
                return 25;
            case "c4":
                return 26;
            case "d4":
                return 27;
            case "e4":
                return 28;
            case "f4":
                return 29;
            case "g4":
                return 30;
            case "h4":
                return 31;
            case "a3":
                return 32;
            case "b3":
                return 33;
            case "c3":
                return 34;
            case "d3":
                return 35;
            case "e3":
                return 36;
            case "f3":
                return 37;
            case "g3":
                return 38;
            case "h3":
                return 39;
            case "a2":
                return 40;
            case "b2":
                return 41;
            case "c2":
                return 42;
            case "d2":
                return 43;
            case "e2":
                return 44;
            case "f2":
                return 45;
            case "g2":
                return 46;
            case "h2":
                return 47;
            case "a1":
                return 48;
            case "b1":
                return 49;
            case "c1":
                return 50;
            case "d1":
                return 51;
            case "e1":
                return 52;
            case "f1":
                return 53;
            case "g1":
                return 54;
            case "h1":
                return 55;
            default:
                return -1;
        }
    }








}
