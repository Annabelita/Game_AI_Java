# Projekt KI - Murus Gallicus KI - Gruppe 07

Das README soll eine Hilfe zur Navigation durch die Projekt Struktur im GitLab sein. 

## CODE
- Sie finden den Code im Ordner [src/main/java](https://github.com/Annabelita/Game_AI_Java/tree/master/src/main/java).
- Test (JUnit) befinden sich im Ordner [tests](https://github.com/Annabelita/Game_AI_Java/tree/master/test). 
- Im [Target](https://github.com/Annabelita/Game_AI_Java/tree/master/target) finden Sie unsere JAR Datei. 

### Java Klassen
Der Ordner __board__ enthält alle Klassen bezogen auf das Bitboard. Sie finden dort ...
- [Bitboard.java](https://gitlab.tubit.tu-berlin.de/saiphon/MG_KI_PJ_Gruppe07/blob/master/src/main/java/board/Bitboard.java): Implementierung unserer Bitboards.
- [Pieces.java](https://gitlab.tubit.tu-berlin.de/saiphon/MG_KI_PJ_Gruppe07/blob/master/src/main/java/board/Pieces.java): Repräsentation der Bitboards & ein switch für leichteren Zugriff in den anderen Klassen. 
- [Parser.java](https://gitlab.tubit.tu-berlin.de/saiphon/MG_KI_PJ_Gruppe07/blob/master/src/main/java/board/Parser.java): Enthält Methoden zur Konvertierung von FenString 
zu Bitboard & vice versa. 

Der Ordner __move__ umfasst die Implementierung des MoveGenerators. 
- Move.java: Implementierung des dynamischen
Zeitmanagement & Übersetzung von String-Move in Index-Move 
- MoveGenerator.java: Implementierung
des MoveGenerator & Berechnung aller valider Züge
- MoveSelector.java: Bekommt einen FENString als input und gibt ein Zug zurück.Enthält die 
Piece-and-Square Tables, TT mit Zobrist Hashing, NegaScout, NegaMax, getMove()-Methode & valueFunction()-Methode (u.v.a. Methoden)
- Player7.java Implementierung des Player Interface für den Versus Contest
- TTEntry.java: Hilfsklasse für Transposition Tables
- Type.java: Hilfsklasse zur Klassifizierung der Züge


Der Ordner __versus/interface__ enthält das Interface _Player.java_ welches in der Klasse Player7.java
implementiert wurde. 

Der Ordner __evolution__ enthält den für das maschinelle Lernen relevanten Code. 
- CustomFormatter.java: Hilfsklasse zur Formatierung des Loggers
- God.java: Enthält die Crossover & Mutation Methoden. Hauptklasse in der die Population trainiert wurde. 
- Individual.java: Ein Individual repräsentiert eine KI. Die Klasse enthält 2 Konstruktoren sowie alle benötigten Hilfsmethoden. 
- Population.java: Mehrere Individuals bilden eine Population. Diese Klasse speichert eine Liste von Individuals und erlaubt den Zugriff auf einzelne Objekte, die Ermittlung der (lokalen) stärksten KI & hat zudem eine Sortierungsmethode. 

Der Ordner __Montecarlo__  enthält den unvollständigen Monte Carlo Suchbaum algorithmus.
- MCTS.java: Hauptklasse die den MCTS algorithmus enthält
- Main.java: Klasse die den MCTS aufruft
- Node.java: Klasse die die Knoten des suchbaums repräsentiert
- State.java: Zeigt die gewinnrate eines Knotens an
- Tree.java: Klasse des suchbaums
- UCT.java: Algorithmus der den nächsten zug aussucht
 



