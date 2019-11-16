package move;

 class TTEntry {
     int depth;
     String flag;
    int value;

     TTEntry(){}

     TTEntry(int depth, String flag, int value){
        this.depth = depth;
        this.flag = flag;
        this.value = value;
    }
}
