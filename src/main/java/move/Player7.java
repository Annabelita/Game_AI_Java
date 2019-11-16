package move;
import versus.interfaces.*;

import static java.lang.Math.pow;
import static java.lang.System.nanoTime;

public class Player7 implements Player{
  /**
   * Checks whether the player can handle a game given by its name. This is
   * used to find players supporting a specific game.
   *
   * @param game
   *            The name of the game which will be used.
   * @return True if the player can handle the game, false otherwise.
   */
  public boolean acceptGame(String game){
    return game.equals("Murus Gallicus");
  }

  /**
   * Gets a name for the player. Used for some debug and error outputs. This
   * will not really effect the computation itself.
   *
   * @return The name of the player.
   */
  public String getPlayerName(){
    return "BIG_BOI";
  }

  /**
   * This is the main call to all players. The player should calculate his
   * next move and return it. The engine will track the time only during this
   * call. If the player exceeds the time given an empty move will be
   * forwarded to the game and he will lose the game.
   *
   * @param representation
   *            The current string encode representation of the game.
   * @param player
   *            The position of this player in the game. 0 = Roman, 1 = Gaul
   * @param time
   *            The time left for this game.
   * @param bonustime
   *            The time earned for each additional round in the game. This
   *            time will be added to the available time before the next move.
   *            Still it does not effect the time limit for this move.
   * @return An string encoded move to be done.
   */
  public String requestMove(String representation, int player, long time, long bonustime){
    MoveSelector selector = new MoveSelector();
    //Algorithmus | FEN | Move | Time | Depth | Nodes
    //System.out.println("Bonustime: " + bonustime);

    //System.out.println("TIME: "+time);
    //long startTime = nanoTime();
    int depth = Move.dynamicTimeManagement(representation, player, time, bonustime);
    String move = selector.getMoveTT(representation,depth);
    //long endTime = nanoTime();
    //System.out.println("###|NegaScoutTT|"+representation+"|"+ move + "|" + (endTime - startTime)/pow(10,6)+"|" + depth+ "|"+selector.nodeCounter);

    return move;
  }
}
