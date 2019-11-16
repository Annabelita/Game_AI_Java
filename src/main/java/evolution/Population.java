package evolution;

import java.util.ArrayList;

public class Population {

  private ArrayList<Individual> individuals = new ArrayList<Individual>();
  private int size = 0;

  /**
   * Basic constructor for a KI population
   * @param size
   */
  public Population(int size, ArrayList<Individual> KIS){
    this.size = size;
    this.individuals = KIS;
  }

  public Population(){
    this.individuals = new ArrayList<Individual>();
  }

  /**
   * Gets KI with given ID
   * @param id
   * @return KI with given ID
   */
  public Individual getIndividual(int id){
    for(Individual individual : individuals){
      if(individual.getId() == id){
        return individual;
      }
    }
    return null;
  }

  /**
   * Determines the best KI
   * @return best KI
   */
  public Individual getFittest(){
    Individual fittest = individuals.get(0);
    for (Individual individual: individuals){
      if(fittest.equals(individual)){
        continue;
      }
      else if(fittest.getFitness() < individual.getFitness()){
        fittest = individual;
      }
    }
    return fittest;
  }

  /**
   * Gets all individuals
   * @return
   */
  public ArrayList<Individual> getIndividuals() {
    return individuals;
  }

  /**
   * Sorts the individual based on the fitness, best is the first one , worst the last one
   * @return sorted individuals
   */
  public ArrayList<Individual> getSortedPopulation(){
    ArrayList<Individual> sortedIndividuals = new ArrayList<Individual>();

    for(Individual individual: individuals){
      int indexToadd = 0;

      if(sortedIndividuals.isEmpty()){
        sortedIndividuals.add(individual);
      }
      else {
        for(Individual sortedIndividual : sortedIndividuals){
          if(individual.getFitness() > sortedIndividual.getFitness()){
            indexToadd = sortedIndividuals.indexOf(sortedIndividual);
            break;
          }
        }
        if(indexToadd == 0 ){
          sortedIndividuals.add(individual);
        }
        else {
          sortedIndividuals.add(indexToadd,individual);
        }
      }
    }
    return sortedIndividuals;
  }

  public void setIndividuals(ArrayList<Individual> individuals) {
    this.individuals = individuals;
  }
}
