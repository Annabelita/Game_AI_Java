package evolution;

import move.MoveSelector;

public class Individual {
  MoveSelector moveSelector;
  int id;
  int wins = 0;
  int loses = 0;
  int paramWall;
  int paramTower;
  int paramCatapult;
  long time1;
  long time2;
  long time3;
  long time4;
  long time5;
  long time6;
  long time7;
  long time8;
  boolean lost = false;

  public Individual(int paramWall, int paramTower, int paramCatapult, boolean valuefunc2, int id){
    this.moveSelector = new MoveSelector(paramWall,paramTower,paramCatapult,valuefunc2);
    this.wins = 0;
    this.loses = 0;
    this.paramWall = paramWall;
    this.paramTower = paramTower;
    this.paramCatapult = paramCatapult;
    this.id = id;
  }

  public Individual(int paramWall, int paramTower, int paramCatapult, boolean valuefunc2, int id,
                    long time1, long time2, long time3, long time4, long time5, long time6, long time7,
                    long time8,boolean lost){
    this.moveSelector = new MoveSelector(paramWall,paramTower,paramCatapult,valuefunc2);
    this.wins = 0;
    this.loses = 0;
    this.paramWall = paramWall;
    this.paramTower = paramTower;
    this.paramCatapult = paramCatapult;
    this.id = id;
    this.time1 = time1;
    this.time2 = time2;
    this.time3 = time3;
    this.time4 = time4;
    this.time5 = time5;
    this.time6 = time6;
    this.time7 = time7;
    this.time8 = time8;
    this.lost = lost;
  }


  public void updateLoses() {
    this.loses++;
  }

  public void updateWins() {
    this.wins++;
  }

  public int getLoses() {
    return loses;
  }

  public int getWins() {
    return wins;
  }

  public int getFitness(){
    return wins;
  }

  public int getParamWall() {
    return paramWall;
  }

  public int getParamTower() {
    return paramTower;
  }

  public int getParamCatapult() {
    return paramCatapult;
  }

  public MoveSelector getMoveSelector() {
    return this.moveSelector;
  }

  public int getId() {
    return id;
  }

  public void setParamWall(int paramWall) {
    this.paramWall = paramWall;
  }

  public void setParamTower(int paramTower) {
    this.paramTower = paramTower;
  }

  public void setParamCatapult(int paramCatapult) {
    this.paramCatapult = paramCatapult;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "# KI : "+id+"/"+paramWall+"/"+paramTower+"/"+paramCatapult+"/"+wins+"/"+loses;
  }

}
