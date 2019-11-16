package evolution;

import move.Move;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import static java.lang.Math.pow;
import static java.lang.System.*;

public class God {

    // -------------------------- LOGGER STUFF -------------------------
    static String logPath = "log.txt";
    static boolean append = false;
    static FileHandler handler;

    static {
        try {
            handler = new FileHandler(logPath, append);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static final Logger logger = Logger.getLogger(logPath);

    // -------------------------- END LOGGER -------------------------

    // -------------------------- Global variables -------------------------
    static ArrayList<Integer> featureSelector = new ArrayList<>(Arrays.asList(1, 2, 3));
    static ArrayList<Integer> timeFeatureSelector = new ArrayList<>(Arrays.asList(1, 2, 3,4,5,6,7,8));
    static ArrayList<Integer> wallSet = new ArrayList<>();
    static ArrayList<Integer> towerSet = new ArrayList<>();
    static ArrayList<Integer> catapultSet = new ArrayList<>();
    static boolean elitism = true;
    static int tournamentSize = 4;
    static boolean winnerKInotFound = true;
    // -------------------------- END Global variables -------------------------
    /**
     * Prints the population
     * @param currentPopulation
     */
    public static void logPopulation(Population currentPopulation){
        for(Individual i : currentPopulation.getIndividuals()) {
            logger.info(i.toString());
        }
    }


    /**
     * Performes cross over a population
     * @param percentage
     */
    public static void crossover_v1(double percentage, Population currentPopulation) {
        if(currentPopulation.getIndividuals().isEmpty())
            throw new RuntimeException("Population is empty!");


        int crossoverCount = (int) (currentPopulation.getIndividuals().size() * percentage);
        ArrayList<Individual> crossoverPopulation = new ArrayList<Individual>();
        for(int i = 0; i < crossoverCount; i++){
            crossoverPopulation.add(currentPopulation.getSortedPopulation().get(i));
        }

        for(Individual tmp : crossoverPopulation){
            wallSet.add(tmp.paramWall);
            towerSet.add(tmp.paramTower);
            catapultSet.add(tmp.paramCatapult);
        }

        Random randomizer = new Random();
        int random = featureSelector.get(randomizer.nextInt(featureSelector.size()));

        switch (random){
            case 1:
                crossoverWall(crossoverPopulation);
            case 2:
                crossoverTower(crossoverPopulation);
            case 3:
                crossoverCatapult(crossoverPopulation);
        }
    }

    private static Individual crossover_v2(Individual individual1, Individual individual2, int id){
        int newParamWall = (individual1.getParamWall()+individual2.getParamWall())/2;
        int newParamTower = (individual1.getParamTower()+individual2.getParamTower())/2;
        int newParamCatapult = (individual1.getParamCatapult()+individual2.getParamCatapult())/2;

        return new Individual(newParamWall,newParamTower,newParamCatapult,true,id);
    }

    private static Individual crossover_v3(Individual individual1, Individual individual2, int id){
        int newParamWall = (individual1.getParamWall()+individual2.getParamWall())/2;
        int newParamTower = (individual1.getParamTower()+individual2.getParamTower())/2;
        int newParamCatapult = (individual1.getParamCatapult()+individual2.getParamCatapult())/2;
        Individual newIndividual = new Individual(newParamWall,newParamTower,newParamCatapult,true,id);

        //Heatmap.size() is 56
        for(int i = 0; i < 56; i++){
            int wallValue = (individual1.moveSelector.heatmapRWall.get(i) + individual2.moveSelector.heatmapRWall.get(i))/2;
            int towerValue = (individual1.moveSelector.heatmapRTower.get(i) + individual2.moveSelector.heatmapRTower.get(i))/2;
            int catapultValue = (individual1.moveSelector.heatmapRTower.get(i) + individual2.moveSelector.heatmapRTower.get(i))/2;
            //Roman
            newIndividual.moveSelector.heatmapRWall.set(i,wallValue);
            newIndividual.moveSelector.heatmapRTower.set(i,towerValue);
            newIndividual.moveSelector.heatmapRCatapult.set(i,catapultValue);
            //Gaul
            newIndividual.moveSelector.heatmapGWall.set(55-i,wallValue);
            newIndividual.moveSelector.heatmapGTower.set(55-i,towerValue);
            newIndividual.moveSelector.heatmapGCatapult.set(55-i,catapultValue);
        }

        return newIndividual;
    }

    /**
     * Helper Functions
     * Crossover v1:
     * Best AI is at index 0 in list
     * We don't change the params of the best AI
     *
     * For all other AIs: From the set of best AIs, randomly choose a new Wall, Tower or Catapult feature and update the current AIs param
     * @param list -- list of best individuals for crossover
     */
    public static void crossoverWall(ArrayList<Individual> list) {
        if(list.isEmpty())
            throw new RuntimeException("Error in crossoverWall: Population-List is empty!");

        logger.info("### Changing Wall Feature with crossover");
        for(int i = 0; i < list.size()-1; i++){
            if(i == 0)
                continue;

            Individual current = list.get(i);

            Random randomizer = new Random();
            int randomWallParam = wallSet.get(randomizer.nextInt(wallSet.size()));
            current.paramWall = randomWallParam;
        }

    }

    public static void crossoverTower(ArrayList<Individual> list) {
        if(list.isEmpty())
            throw new RuntimeException("Error in crossoverTower: Population-List is empty!");

        logger.info("### Changing Tower Feature with crossover");
        for(int i = 0; i < list.size()-1; i++){
            if(i == 0)
                continue;

            Individual current = list.get(i);

            Random randomizer = new Random();
            int randomTowerParam = towerSet.get(randomizer.nextInt(towerSet.size()));
            current.paramTower = randomTowerParam;
        }

    }

    public static void crossoverCatapult(ArrayList<Individual> list) {
        if(list.isEmpty())
            throw new RuntimeException("Error in crossoverCatapult: Population-List is empty!");


        logger.info("### Changing Catapult Feature with crossover");
        for(int i = 0; i < list.size(); i++){
            if(i == 0)
                continue;

            Individual current = list.get(i);

            Random randomizer = new Random();
            int randomCatapultParam = catapultSet.get(randomizer.nextInt(catapultSet.size()));
            current.paramCatapult = randomCatapultParam;
        }

    }


    /**
     * Mutates a random feature of all AIs
     * Offsetrange: [lowerbound, upperbound]
     */
    public static void mutate(int lowerbound, int upperbound, Population population) {
        Random randomizer = new Random();
        int random = featureSelector.get(randomizer.nextInt(featureSelector.size()));
        int randomOffset =  ThreadLocalRandom.current().nextInt(lowerbound, upperbound + 1);

        if(random == 1)
            logger.info("### Wall Feature is mutating! Offset: " + randomOffset);
        else if(random == 2)
            logger.info("### Tower Feature is mutating! Offset: " + randomOffset);
        else if(random == 3)
            logger.info("### Catapult Feature is mutating! Offset: " + randomOffset);

        for(Individual tmp : population.getIndividuals()){
            if(random == 1)
                tmp.paramWall = tmp.paramWall + randomOffset;

            else if(random == 2)
                tmp.paramTower = tmp.paramTower + randomOffset;

            else if(random == 3)
                tmp.paramCatapult = tmp.paramCatapult + randomOffset;

        }
    }


    private static void mutateV2(int lowerbound, int upperbound, Individual individual){
        //TODO: Change mutation to select between -50 and -75 or 50 and 75
        Random randomizer = new Random();
        int random = featureSelector.get(randomizer.nextInt(featureSelector.size()));
        int randomOffset =  ThreadLocalRandom.current().nextInt(lowerbound, upperbound + 1);

        if(random == 1)
            logger.info("### Wall Feature is mutating! KI:"+individual.getId()+" Offset: " + randomOffset);
        else if(random == 2)
            logger.info("### Tower Feature is mutating! KI:"+individual.getId()+" Offset: " + randomOffset);
        else if(random == 3)
            logger.info("### Catapult Feature is mutating! KI:"+individual.getId()+" Offset: " + randomOffset);

        if(random == 1) {
            individual.setParamWall(individual.getParamWall() + randomOffset);
        }
        else if(random == 2) {
            individual.setParamTower(individual.getParamTower() + randomOffset);
        }
        else{
            individual.setParamCatapult(individual.getParamCatapult() + randomOffset);
        }
    }

    /**
     * Standard mutation for evolving
     * @param lowerbound
     * @param upperbound
     * @param individual
     */
    private static void mutateV3(int lowerbound, int upperbound, Individual individual){
        //TODO: Change mutation to select between -50 and -75 or 50 and 75
        Random randomizer = new Random();
        int random = featureSelector.get(randomizer.nextInt(featureSelector.size()));
        int randomOffset =  ThreadLocalRandom.current().nextInt(lowerbound, upperbound + 1);

        if(random == 1)
            logger.info("### Wall Feature is mutating! KI:"+individual.getId()+" Offset: " + randomOffset);
        else if(random == 2)
            logger.info("### Tower Feature is mutating! KI:"+individual.getId()+" Offset: " + randomOffset);
        else if(random == 3)
            logger.info("### Catapult Feature is mutating! KI:"+individual.getId()+" Offset: " + randomOffset);

        if(random == 1) {
            individual.setParamWall(individual.getParamWall() + randomOffset);
        }
        else if(random == 2) {
            individual.setParamTower(individual.getParamTower() + randomOffset);
        }
        else{
            individual.setParamCatapult(individual.getParamCatapult() + randomOffset);
        }
        calculateNewHeatmap(individual);
    }

    /**
     * Standard mutation for evolving time
     * @param individual
     */
    private static void mutateTime(Individual individual){
        Random randomizer = new Random();
        int random = timeFeatureSelector.get(randomizer.nextInt(timeFeatureSelector.size()));
        int randomOffset =  ThreadLocalRandom.current().nextInt(-1000, 1000 + 1);

        if(random == 1)
            logger.info("### Time 1 Feature is mutating! KI:"+individual.getId()+" Offset: " + randomOffset);
        else if(random == 2)
            logger.info("### Time 2 Feature is mutating! KI:"+individual.getId()+" Offset: " + randomOffset);
        else if(random == 3)
            logger.info("### Time 3 Feature is mutating! KI:"+individual.getId()+" Offset: " + randomOffset);
        else if(random == 4)
            logger.info("### Time 4 Feature is mutating! KI:"+individual.getId()+" Offset: " + randomOffset);
        else if(random == 5)
            logger.info("### Time 5 Feature is mutating! KI:"+individual.getId()+" Offset: " + randomOffset);
        else if(random == 6)
            logger.info("### Time 6 Feature is mutating! KI:"+individual.getId()+" Offset: " + randomOffset);
        else if(random == 7)
            logger.info("### Time 7 Feature is mutating! KI:"+individual.getId()+" Offset: " + randomOffset);
        else if(random == 8)
            logger.info("### Time 8 Feature is mutating! KI:"+individual.getId()+" Offset: " + randomOffset);

        if(random == 1) {
            individual.time1 += randomOffset;
        }
        else if(random == 2) {
            individual.time2 += randomOffset;
        }
        else if(random == 3) {
            individual.time3 += randomOffset;
        }
        else if(random == 4) {
            individual.time4 += randomOffset;
        }
        else if(random == 5) {
            individual.time5 += randomOffset;
        }
        else if(random == 6) {
            individual.time6 += randomOffset;
        }
        else if(random == 7) {
            individual.time7 += randomOffset;
        }
        else if(random == 8) {
            individual.time8 += randomOffset;
        }
    }


    private static void forcedMutation(Individual individual){
        //TODO: Change mutation to select between -50 and -75 or 50 and 75
        Random randomizer = new Random();
        int random = featureSelector.get(randomizer.nextInt(featureSelector.size()));
        int randomOffset =  ThreadLocalRandom.current().nextInt(-75, 75 + 1);

        logger.info("### Natural Selection KI is mutating with Value: "+randomOffset);
        individual.setParamWall(individual.getParamWall() + randomOffset);
        individual.setParamTower(individual.getParamTower() + randomOffset);
        individual.setParamCatapult(individual.getParamCatapult() + randomOffset);

        calculateNewHeatmap(individual);
    }

    private static void calculateNewHeatmap(Individual individual) {

        for(int i = 0; i < 56; i++){
            int towerValue = ThreadLocalRandom.current().nextInt(-1, 11);
            int wallValue = ThreadLocalRandom.current().nextInt(-1, 11);
            int catapultValue = ThreadLocalRandom.current().nextInt(-1, 11);

            individual.moveSelector.heatmapRTower.set(i, towerValue);
            individual.moveSelector.heatmapRWall.set(i, wallValue);
            individual.moveSelector.heatmapRCatapult.set(i, catapultValue);

            individual.moveSelector.heatmapGTower.set(55- i, towerValue);
            individual.moveSelector.heatmapGWall.set(55- i, wallValue);
            individual.moveSelector.heatmapGCatapult.set(55 - i, catapultValue);
        }
    }

    /**
     * Plays a KO-Match
     * Modi: KO
     * @param currentPopulation
     */
    public static Population playMatchKO(Population currentPopulation){
        Population newPopulation = new Population();
        Individual best = null;
        for (Individual firstKI: currentPopulation.getIndividuals()){
            if(!firstKI.lost){
                for (Individual secondKI: currentPopulation.getIndividuals()){
                    if(firstKI.equals(secondKI)){
                        continue;
                    }
                    else if(secondKI.lost){
                        continue;
                    }
                    //logger.info("Current game: "+firstKI.id+" VS "+secondKI.id);
                    System.out.println("Current game: "+firstKI.id+" VS "+secondKI.id);
                    String FENString = "tttttttt/8/8/8/8/8/TTTTTTTT r";
                    long firstKITime = 120000;
                    long secondKITime = 120000;
                    long startTime;
                    long endTime;
                    int depth;

                    while (true){
                        //TODO add move limit 100
                        //####################
                        //First KI KI doing move
                        //####################
                        startTime = nanoTime();
                        depth = Move.dynamicTimeManagement(FENString,0, firstKITime, 0,
                                firstKI.time1,firstKI.time2,firstKI.time3,firstKI.time4,firstKI.time5,
                                firstKI.time6,firstKI.time7,firstKI.time8);
                        String currentMove = firstKI.getMoveSelector().getMove(FENString, depth);
                        //System.gc();
                        endTime = nanoTime();
                        firstKITime -= ((endTime - startTime)/pow(10,6));

                        //####################
                        //First KI move done
                        //Check for timeout or lose
                        //Mutate - Add to new Population - Remove from Old
                        //####################
                        if(firstKITime <= 0){
                            logger.info("KI: "+firstKI.getId()+ " lost against: "+secondKI.getId());
                            mutateTime(firstKI);
                            newPopulation.getIndividuals().add(firstKI);
                            firstKI.lost = true;
                            break;
                        }
                        if (currentMove.equals("Can't Move, REEEEEEE")){
                            logger.info("KI: "+firstKI.getId()+ " lost against: "+secondKI.getId());
                            mutateTime(firstKI);
                            newPopulation.getIndividuals().add(firstKI);
                            firstKI.lost = true;
                            break;
                        }
                        //####################
                        //Update FENstring
                        //####################
                        FENString = firstKI.getMoveSelector().createNewState(FENString, currentMove, 1,0L);
                        //System.gc();
                        //####################
                        //Second KI doing move
                        //####################
                        startTime = nanoTime();
                        depth = Move.dynamicTimeManagement(FENString,1, secondKITime, 0,
                                secondKI.time1,secondKI.time2,secondKI.time3,secondKI.time4,
                                secondKI.time5,secondKI.time6,secondKI.time7,secondKI.time8);
                        String individualMove = secondKI.getMoveSelector().getMove(FENString, depth);
                        //System.gc();
                        endTime = nanoTime();
                        secondKITime -= ((endTime - startTime)/pow(10,6));
                        //####################
                        //Second KI move done
                        //Check for timeout or lose
                        //####################
                        if(secondKITime <= 0){
                            logger.info("KI: "+secondKI.getId()+ " lost against: "+firstKI.getId());
                            mutateTime(secondKI);
                            newPopulation.getIndividuals().add(secondKI);
                            secondKI.lost = true;
                            break;
                        }
                        if (individualMove.equals("Can't Move, REEEEEEE")){
                            logger.info("KI: "+secondKI.getId()+ " lost against: "+firstKI.getId());
                            mutateTime(secondKI);
                            newPopulation.getIndividuals().add(secondKI);
                            secondKI.lost = true;
                            break;
                        }
                        //####################
                        //Update FENstring
                        //####################
                        FENString = secondKI.getMoveSelector().createNewState(FENString, individualMove, -1,0L);
                        //System.gc();

                    }
                    if(firstKI.lost){
                        best = secondKI;
                        break;
                    }
                    else {
                        best = firstKI;
                    }
                }
            }
        }
        if(best != null){
            best.wins += 1;
            if(!newPopulation.getIndividuals().contains(best)){
                newPopulation.getIndividuals().add(0,best);
            }
        }
        else if(best == null){
            throw new RuntimeException("SOMETHING WENT WRONG AT THE END OF THE GAME"+"\nOLD POPULATION: "+currentPopulation.getIndividuals()+"\nNEW POPULATION: "+newPopulation.getIndividuals());

        }

        //Result
        logger.info(" ~~~~~~ Results ~~~~~");
        logger.info("Best Player: " + newPopulation.getFittest().toString());

        for(Individual individual : newPopulation.getIndividuals()){
            if(individual.getWins() == 3){
                winnerKInotFound = false;
            }
            individual.lost = false;
            logger.info(individual.toString());
        }
        return newPopulation;
    }

    /**
     * Plays a match according to Versus rules
     * Modi: Round-Robin
     * @param currentPopulation
     */
    public static void playMatch(Population currentPopulation){
        for (int j = 0; j < currentPopulation.getIndividuals().size(); j++){
            Individual firstKI = currentPopulation.getIndividuals().get(j);
            for (Individual secondKI: currentPopulation.getIndividuals()){
                if(firstKI.equals(secondKI)){
                    continue;
                }
                //logger.info("Current game: "+firstKI.id+" VS "+secondKI.id);
                System.out.println("Current game: "+firstKI.id+" VS "+secondKI.id);
                String FENString = "tttttttt/8/8/8/8/8/TTTTTTTT r";
                long firstKITime = 40000;
                long secondKITime = 40000;
                long startTime;
                long endTime;
                int depth;

                while (true){
                    //TODO add move limit 100
                    //####################
                    //First KI KI doing move
                    //####################
                    startTime = nanoTime();
                    //depth = Move.dynamicTimeManagement(FENString,0, firstKITime, 0);
                    String currentMove = firstKI.getMoveSelector().getMove(FENString, 3);
                    //System.gc();
                    endTime = nanoTime();
                    firstKITime -= ((endTime - startTime)/pow(10,6));

                    //####################
                    //First KI move done
                    //Check for timeout or lose
                    //####################
                    if(firstKITime <= 0){
                        firstKI.updateLoses();
                        secondKI.updateWins();
                        break;
                    }
                    if (currentMove.equals("Can't Move, REEEEEEE")){
                        firstKI.updateLoses();
                        secondKI.updateWins();
                        break;
                    }
                    //####################
                    //Update FENstring
                    //####################
                    FENString = firstKI.getMoveSelector().createNewState(FENString, currentMove, 1,0L);
                    //System.gc();
                    //####################
                    //Second KI doing move
                    //####################
                    startTime = nanoTime();
                    //depth = Move.dynamicTimeManagement(FENString,1, secondKITime, 0);
                    String individualMove = secondKI.getMoveSelector().getMove(FENString, 3);
                    //System.gc();
                    endTime = nanoTime();
                    secondKITime -= ((endTime - startTime)/pow(10,6));
                    //####################
                    //Second KI move done
                    //Check for timeout or lose
                    //####################
                    if(secondKITime <= 0){
                        secondKI.updateLoses();
                        firstKI.updateWins();
                        break;
                    }
                    if (individualMove.equals("Can't Move, REEEEEEE")){
                        firstKI.updateWins();
                        secondKI.updateLoses();
                        break;
                    }
                    //####################
                    //Update FENstring
                    //####################
                    FENString = secondKI.getMoveSelector().createNewState(FENString, individualMove, -1,0L);
                    //System.gc();

                }
            }
        }
      //Result
      logger.info(" ~~~~~~ Results ~~~~~");
      logger.info("Best Player: " + currentPopulation.getFittest().toString());
      for(Individual individual : currentPopulation.getIndividuals()){
          if(individual.getFitness() == 0 && individual.getId() == 1){
              winnerKInotFound = false;
          }
          logger.info(individual.toString());
      }
    }

    /**
     * Evolves the current population to form the next generation
     * With CrossOver and Mutation
     * @param currentPopulation
     */
    private static void evolvePopulation(Population currentPopulation){
        logger.info("Best Player: " + currentPopulation.getFittest().toString());

        /**
         * Cross over
         * Change: 80%
         */
        int randomNumCrossover = ThreadLocalRandom.current().nextInt(0, 100 + 1);
        if(randomNumCrossover < 80){
            logger.info("------------------> Crossover performed on population!");
            crossover_v1(0.5, currentPopulation);
        }

        /**
         * Mutation
         * Chance: 50%
         */
        int randomNumMutation = ThreadLocalRandom.current().nextInt(0, 100 + 1);
        if(randomNumMutation < 50){
            logger.info("------------------> Population is mutating!!");
            mutate(-50, 50, currentPopulation);
        }

        /**
         * Clean up
         */
        logger.info(" ~~~~~~ Results ~~~~~");
        for(Individual individual : currentPopulation.getIndividuals()){
            logger.info(individual.toString());
            individual.wins = 0;
            individual.loses = 0;
        }

        wallSet.clear();
        towerSet.clear();
        catapultSet.clear();
    }

    /**
     * Evolves the current population to form the next generation
     * With updated Crossover, Mutation and elitism
     * @param currentPopulation
     * @return
     */
    private static Population evolvePopulationV2(Population currentPopulation){
        //Inits
        ArrayList<Individual> newKIs = new ArrayList<Individual>();
        int elitismOffset;

        //Elitism
        if(elitism){
            Individual fittest;
            //If our standard KI is the best, get the second best
            if(currentPopulation.getFittest().getId() == 1){
                fittest = currentPopulation.getSortedPopulation().get(1);
                //fittest.setId(0);
                Individual individualt1 = new Individual(fittest.paramWall,fittest.paramTower,fittest.paramCatapult,true,0);
                Individual individualt2 = new Individual(currentPopulation.getIndividual(1).paramWall,currentPopulation.getIndividual(1).paramTower,currentPopulation.getIndividual(1).paramCatapult,true,1);
                newKIs.add(0,individualt1);
                newKIs.add(1,individualt2);
            }
            //If the best KI id is not 0
            else if(currentPopulation.getFittest().getId() != 0){
                fittest = currentPopulation.getFittest();
                //fittest.setId(0);
                Individual individualt1 = new Individual(fittest.paramWall,fittest.paramTower,fittest.paramCatapult,true,0);
                Individual individualt2 = new Individual(currentPopulation.getIndividual(1).paramWall,currentPopulation.getIndividual(1).paramTower,currentPopulation.getIndividual(1).paramCatapult,true,1);
                newKIs.add(0,individualt1);
                newKIs.add(1,individualt2);
            }
            //If the best KI id is 0
            else {
                newKIs.add(0,currentPopulation.getFittest());
                newKIs.add(1,currentPopulation.getIndividual(1));
            }
            elitismOffset = 2;
        }
        else {
            elitismOffset = 0;
        }

        naturalSelection(currentPopulation);

        /**
         * Cross over
         * Chance: 80%
         *
         * Mutation
         * Chance: 50%
         */
        int randomNumCrossover = ThreadLocalRandom.current().nextInt(0, 100 + 1);
        int randomNumMutation = ThreadLocalRandom.current().nextInt(0, 100 + 1);
        //Crossover
        if(randomNumCrossover < 80){
            logger.info("------------------> Crossover performed on population!");
            for(int i = elitismOffset; i < currentPopulation.getIndividuals().size(); i++){
                Individual individual1 = tournamentSelection(currentPopulation);
                Individual individual2 = tournamentSelection(currentPopulation);
                //TODO: Change for different Crossover
                //Individual newIndividual = crossover_v2(individual1,individual2,i);
                Individual newIndividual = crossover_v3(individual1,individual2,i);
                newKIs.add(i,newIndividual);
            }
            if(randomNumMutation < 50){
                logger.info("------------------> Population is mutating!!");
                //Mutation
                for(int i = elitismOffset; i < newKIs.size(); i++){
                    //TODO: Change for different mutation
                    //mutateV2(-75,75, newKIs.get(i));
                    mutateV3(-200,200, newKIs.get(i));
                }
            }
            Population newPopulation = new Population(currentPopulation.getIndividuals().size(),newKIs);

            //Clean History
            for(Individual individual : currentPopulation.getIndividuals()){
                individual.wins = 0;
                individual.loses = 0;
            }

            //Result
            logger.info(" ~~~~~~ CrossOver/Mutation Results ~~~~~");
            for(Individual individual : newPopulation.getIndividuals()){
             logger.info(individual.toString());
            }
            //Return modified population
            return newPopulation;
        }
        //No Crossover
        else {
            if(randomNumMutation < 50){
                logger.info("------------------> Population is mutating!!");
                //Mutation
                for(int i = elitismOffset; i < currentPopulation.getIndividuals().size(); i++){
                    //TODO: Change for different mutation
                    //mutateV2(-75, 75, currentPopulation.getIndividual(i));
                    mutateV3(-200, 200, currentPopulation.getIndividuals().get(i));
                }
            }
            //Clean History
            for(Individual individual : currentPopulation.getIndividuals()){
                individual.wins = 0;
                individual.loses = 0;
            }
            //Result
            logger.info(" ~~~~~~ Mutation Results ~~~~~");
            for(Individual individual : currentPopulation.getIndividuals()){
            }
            //Return modified population
            return currentPopulation;
        }
    }

    private static Individual tournamentSelection(Population currentPopulation){
        ArrayList<Individual> KIs = new ArrayList<Individual>();
        for(int i = 0; i < tournamentSize; i++){
            int randomIndex = (int) (Math.random() * currentPopulation.getIndividuals().size());
            KIs.add(i,currentPopulation.getIndividuals().get(randomIndex));
        }
        Population tournamentPopulation = new Population(tournamentSize,KIs);

        return tournamentPopulation.getFittest();
    }

    private static void naturalSelection(Population currentPopulation){
        for (Individual currentIndividual : currentPopulation.getIndividuals()){
            if(currentIndividual.getFitness() <= 1 && currentIndividual.getId() != 1){
                logger.info("### Natural Selection hits KI: "+currentIndividual.getId());
                currentIndividual.setParamWall(currentPopulation.getFittest().paramWall);
                currentIndividual.setParamTower(currentPopulation.getFittest().paramTower);
                currentIndividual.setParamCatapult(currentPopulation.getFittest().paramCatapult);

                forcedMutation(currentIndividual);
            }
        }
    }

    /**
     * Creates the first population to start the learning
     * @return
     */
    private static Population createPopulation(){
        Individual KI1 = new Individual(90,688,1342,true, 0);
        Individual KI2 = new Individual(50,1000,750,true, 1);
        Individual KI3 = new Individual(20,200,150,true, 2);
        Individual KI4 = new Individual(510,125,450,true, 3);
        Individual KI5 = new Individual(305,112,275,true, 4);
        Individual KI6 = new Individual(159,687,1194,true, 5);
        Individual KI7 = new Individual(100,500,500,true, 6);
        Individual KI8 = new Individual(456,362,673,true, 7);
        Individual KI9 = new Individual(50,1000,3000,true, 8);
        Individual KI10 = new Individual(50,3000,500,true, 9);
        ArrayList<Individual> KIS = new ArrayList<>(Arrays.asList(KI1,KI2,KI3,KI4,KI5,KI6,KI7,KI8,KI9,KI10));
        Population population = new Population(KIS.size(),KIS);
        return population;
        /*
        Winning KIs
        456/362/673
        159/687/1194
        305/112/275/
        */
    }

    /**
     * Creates the first population to start the learning with KO games
     * @return
     */
    private static Population createPopulationKO(){
        long time1 = 20000;
        long time2 = 115000;
        long time3 = 20000;
        long time4 = 80000;
        long time5 = 80000;
        long time6 = 100000;
        long time7 = 70000;
        long time8 = 50000;
        Individual KI1 = new Individual(159,687,1194,true, 1,time1,time2,time3,time4,time5,time6,time7,time8,false);
        Individual KI2 = new Individual(159,687,1194,true, 2,time1,time2,time3,time4,time5,time6,time7,time8,false);
        Individual KI3 = new Individual(159,687,1194,true, 3,time1,time2,time3,time4,time5,time6,time7,time8,false);
        Individual KI4 = new Individual(159,687,1194,true, 4,time1,time2,time3,time4,time5,time6,time7,time8,false);
        Individual KI5 = new Individual(159,687,1194,true, 5,time1,time2,time3,time4,time5,time6,time7,time8,false);
        Individual KI6 = new Individual(159,687,1194,true, 6,time1,time2,time3,time4,time5,time6,time7,time8,false);
        Individual KI7 = new Individual(159,687,1194,true, 7,time1,time2,time3,time4,time5,time6,time7,time8,false);
        Individual KI8 = new Individual(159,687,1194,true, 8,time1,time2,time3,time4,time5,time6,time7,time8,false);
        ArrayList<Individual> KIS = new ArrayList<>(Arrays.asList(KI1,KI2,KI3,KI4,KI5,KI6,KI7,KI8));

        return new Population(KIS.size(),KIS);
        /*
        Winning KIs
        456/362/673
        159/687/1194
        305/112/275/
        */
    }


    /*Alle möglichen parameter:
    - Heatmaps
    - Such-Tiefe bzw. wie lange man sucht
    - Minimal Window (alpha,beta)
    */
    public static void main(String[] args){
        logger.addHandler(handler);
        handler.setFormatter(new CustomFormatter.MyCustomFormatter());
        logger.setUseParentHandlers(false);
        logger.info("-------------- Setting up population --------------");
        //Population currentPopulation = createPopulation();
        Population currentPopulation = createPopulationKO();
        logger.info("-------------- Population created --------------");
        logger.info("-------------- Preparing evolutionary learning --------------");
        int generation = 1;

        while(winnerKInotFound) {
            logger.info("-------------- Generation " + generation + " --------------");
            logger.info("-------------- Current population --------------");
            logPopulation(currentPopulation);

            //playMatch(currentPopulation);
            currentPopulation = playMatchKO(currentPopulation);
            //System.gc();
            //evolvePopulation(currentPopulation);
            if(winnerKInotFound){
                //currentPopulation = evolvePopulationV2(currentPopulation);
                generation++;
            }
        }
        logger.info("-------------- KI:"+ currentPopulation.getFittest().id+" won --------------");
        logger.info("Time 1 Feature: "+ currentPopulation.getFittest().time1);
        logger.info("Time 2 Feature: "+ currentPopulation.getFittest().time2);
        logger.info("Time 3 Feature: "+ currentPopulation.getFittest().time3);
        logger.info("Time 4 Feature: "+ currentPopulation.getFittest().time4);
        logger.info("Time 5 Feature: "+ currentPopulation.getFittest().time5);
        logger.info("Time 6 Feature: "+ currentPopulation.getFittest().time6);
        logger.info("Time 7 Feature: "+ currentPopulation.getFittest().time7);
        logger.info("Time 8 Feature: "+ currentPopulation.getFittest().time8);

    }
}