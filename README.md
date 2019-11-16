# Projekt AI - Murus Gallicus AI

This project was about implementing a Murus Gallicus AI for a study project in the field of symbolic artifical intelligence. The code is in Java. The project was completed within 3 months. 

## CODE
- The code can be found in [src/main/java](https://github.com/Annabelita/Game_AI_Java/tree/master/src/main/java).
- Test (JUnit) can be found here: [tests](https://github.com/Annabelita/Game_AI_Java/tree/master/test). 
- In[Target](https://github.com/Annabelita/Game_AI_Java/tree/master/target) you will find our JAR file.  

### Java Klassen
The folder __board__ contains all classes required for the bitboards. We decided to use bitboards over arrays for speedup reasons. There you will find...
- Bitboard.java: Implementation of our Bitboards.
- Pieces.java: Representation of the bitboards & switch for easy access within other classes. 
- Parser.java: Contains methods to convert a FENString to a bitboard and vice versa. 

The folder __move__ contains the implementation of the MoveGenerator.  
- Move.java: Implementation of the dynamic time management. Also contains a converter (String-Move to Index-move) 
- MoveGenerator.java: Implementation of the MoveGenerator & calculation of all valid moves. 
- MoveSelector.java: Input: FENString, returns a valid move. Contains the Piece-and-Square tables, Transposition Tables with Zobrist Hashing, NegaScout, NegaMax, getMove()-method & valueFunction()-method. 
- Player7.java: Implementation of the Player class for Versus System
- TTEntry.java: Helper class for Transposition Tables. 
- Type.java: Helper class for classification of moves. 

The folder __versus/interface__ contains the interface _Player.java_ which was implemented in Player7.java. 

The folder __evolution__ contains all machine learning related code. 
- CustomFormatter.java: Helper class that defines formatting of our logger. 
- God.java: Contains the crossover() and mutation() method. Main class which was used for training the population. 
- Individual.java: A individual represents a AI. Each class contains 2 constructors as well as all required helper methods. 
- Population.java: 2 or more individuals form a population. This class saves a list of Individuals and allows accessing objects, calculation of the strongest (local) AI. Furthermore this class contains a sorting method. 

The folder __Montecarlo__  contains our (not complete) MCTS-algorithm. 
- MCTS.java: Main class for MCTS algorithm
- Main.java: Main class that calls MCTS()
- Node.java: Class representing the nodes of the search tree
- State.java: Class showing the win rate of a node
- Tree.java: Class for Searchtree
- UCT.java: Algorithm determing the next move
 




