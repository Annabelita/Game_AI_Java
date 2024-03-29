# Projekt AI - Murus Gallicus AI

This project was about implementing a Murus Gallicus AI for a study project in the field of symbolic artifical intelligence. The code is in Java. The project was completed within 3 months. 

<br>
<br>
## Prequeries
Versus System Package must be installed in order to use the contest environment. 

<br>
<br>

## Build the project
```
mvn build
```

<br>
<br>

## Code
- The code can be found in [src/main/java](https://github.com/Annabelita/Game_AI_Java/tree/master/src/main/java).
- Test (JUnit) can be found here: [tests](https://github.com/Annabelita/Game_AI_Java/tree/master/test). 
- In[Target](https://github.com/Annabelita/Game_AI_Java/tree/master/target) you will find our JAR file.  

<br>
<br>

### Java Classes
The folder __board__ contains all classes required for the bitboards. We decided to use bitboards over arrays for speedup reasons. There you will find...
- __Bitboard.java__: Implementation of our Bitboards.
- __Pieces.java__: Representation of the bitboards & switch for easy access within other classes. 
- __Parser.java__: Contains methods to convert a FENString to a bitboard and vice versa. 

<br>
<br>

The folder __move__ contains the implementation of the MoveGenerator.  
- __Move.java__: Implementation of the dynamic time management. Also contains a converter (String-Move to Index-move) 
- __MoveGenerator.java__: Implementation of the MoveGenerator & calculation of all valid moves. 
- __MoveSelector.java__: Input: FENString, returns a valid move. Contains the Piece-and-Square tables, Transposition Tables with Zobrist Hashing, NegaScout, NegaMax, getMove()-method & valueFunction()-method. 
- __Player7.java__: Implementation of the Player class for Versus System
- __TTEntry.java__: Helper class for Transposition Tables. 
- __Type.java__: Helper class for classification of moves. 

<br>
<br>

The folder __versus/interface__ contains the interface _Player.java_ which was implemented in Player7.java. 

<br>
<br>

The folder __evolution__ contains all machine learning related code. 
- __CustomFormatter.java__: Helper class that defines formatting of our logger. 
- __God.java__: Contains the crossover() and mutation() method. Main class which was used for training the population. 
- __Individual.java__: A individual represents a AI. Each class contains 2 constructors as well as all required helper methods. 
- __Population.java__: 2 or more individuals form a population. This class saves a list of Individuals and allows accessing objects, calculation of the strongest (local) AI. Furthermore this class contains a sorting method. 

<br>
<br>

The folder __Montecarlo__  contains our (not complete) MCTS-algorithm. 
- __MCTS.java__: Main class for MCTS algorithm
- __Main.java__: Main class that calls MCTS()
- __Node.java__: Class representing the nodes of the search tree
- __State.java__: Class showing the win rate of a node
- __Tree.java__: Class for Searchtree
- __UCT.java__: Algorithm determing the next move


<br>
<br>

## Performance & Analysis

### Comparison NegaMax, NegaScout & NegaScout with Transposition Tables

We started the project implementing NegaMax in order to search the game tree. 

Later we switched to NegaScout. Finally we added Transposition Tabes. 

The following plot shows the increased performance in each iteration. 

The x-axis shows the depth in which we searched. 

The y-axis shows the amount created nodes. 

<img src="https://github.com/Annabelita/Game_AI_Java/blob/master/images/comp_NM.png" width="900">

<br>
<br>

### Speedup using Transposition Tables

The x-axis shows the depth in which we searched. 

The y-axis shows the amount created nodes.

The upper plot shows the computation time for each depth. The plot below shows the amount of computed nodes. 

Using NegaScout we can calculate almost 1/3 more nodes than with NegaScout and need less than 50% of the time. 

<img src="https://github.com/Annabelita/Game_AI_Java/blob/master/images/speedup.png" width="500">


<br>
<br>

### Contest Results Comparison

During the whole contest we could decrease our computation time and always need equal or less time than the average other groups.

The upper plot shows our minimum computation time for each milestone. 

The plot below compares our average computation time to the average computation time of the other groups. 

<img src="https://github.com/Annabelita/Game_AI_Java/blob/master/images/comparison.png" width="500"> 
