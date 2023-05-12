package edu.yu.da;

import java.util.*;

import edu.yu.da.GeneticAlgorithmConfig.SelectionType;



public class ArithmeticPuzzle extends ArithmeticPuzzleBase{
	
	class GASolution implements SolutionI{
		List<Character> solution;
		String augend="";
		String Addend="";
		String Sum="";
		int nGenerations=0;
		
		public GASolution(List<Character> solution, String augend, String Addend, String Sum, int nGenerations) {
			this.solution=solution;
			this.augend=augend;
			this.Addend=Addend;
			this.Sum=Sum;
			this.nGenerations=nGenerations;
		}
		
		@Override
		public List<Character> solution() {
			return this.solution;
		}

		@Override
		public String getAugend() {
			return this.augend;
		}

		@Override
		public String getAddend() {
			return this.Addend;
		}

		@Override
		public String getSum() {
			return this.Sum;
		}

		@Override
		public int nGenerations() {
			return this.nGenerations;
		}
		
	}
	
	public String augend="";
	public String Addend="";
	public String Sum="";
	private Set<Character> lettersUsed;
	
	public ArithmeticPuzzle(String augend, String addend, String sum) {
		super(augend, addend, sum);
		this.augend=augend;
		this.Addend=addend;
		this.Sum=sum;
		
		this.lettersUsed= new HashSet<>();
		if(augend.isBlank() || augend.contains(" ") || addend.isBlank() || sum.isBlank() || addend.contains(" ") || sum.contains(" ")   ) {
			throw new IllegalArgumentException();
		}
		//Determine the letters used and add them to the set
		for(int i=0; i< this.Addend.length(); i++) {	// process addend
			this.lettersUsed.add(this.Addend.charAt(i));
		}
		
		for(int i=0; i< this.augend.length(); i++) { //process augend
			this.lettersUsed.add(this.augend.charAt(i));
		}
		
		for(int i=0; i< this.Sum.length(); i++) {	//process the sum
			this.lettersUsed.add(this.Sum.charAt(i));
		}
		
		
	}

	
	@Override
	public SolutionI solveIt(GeneticAlgorithmConfig gac) {
		
		if(this.lettersUsed.size()>10) {
			return new GASolution(Collections.EMPTY_LIST, augend, Addend, Sum, 1); 
		}
		
		//extract the data from the genetic algorithm's config
		final int populationSize=gac.getInitialPopulationSize();
        final int maxGenerations=gac.getMaxGenerations();
        final SelectionType choice=gac.getSelectionType();
        final double mutationProbability=gac.getMutationProbability();
        final double crossoverProbability=gac.getCrossoverProbability();
		
        //track the generations with 1 referring to the initial population
        int generation=1;
        //generate the initial population
		List<List<Character>> initialPopulation= generateInitialPopulation(populationSize);
		List<List<Character>> newPopulation= new ArrayList<>();
	
		while(generation<=maxGenerations) {
			//loop through our initial population to see if we discovered a solution
			for(List<Character> chromosome: initialPopulation) {
		
				//if gradeChromosome returns 1 we have a solution
				if(gradeChromosome(chromosome)==1) {
					
					return new GASolution(chromosome, this.augend, this.Addend, this.Sum, generation);
				}
			}
			
			//shuffle initial population for sake of randomness
			Collections.shuffle(initialPopulation);
			
			//generate the new population from the old one based on SelectionType, mutation probability, and CrossoverProbability
			newPopulation=generateNewPopulation(initialPopulation,choice, mutationProbability, crossoverProbability);
			
			//clear and copy new population into initial to prepare for the next round
			initialPopulation.clear();
			initialPopulation.addAll(newPopulation);
			
			generation++;
		}
		
		//no solution was encountered
		return new GASolution(Collections.EMPTY_LIST, augend, Addend, Sum, generation);
	}

	/**
	 * This method generates the new population for the genetic algorithim. It is guranteed that every chromosome is valid and the newPopulation.size()== oldPopulation.size()
	 * @usage this method relies on crosOver and MutateGene
	 * @param initialPopulation
	 * @param choice
	 * @param mutationProbaility
	 * @param crossoverProbability
	 * @return the new population
	 */
	private List<List<Character>> generateNewPopulation(List<List<Character>> initialPopulation, SelectionType choice, double mutationProbaility, double crossoverProbability){
		List<List<Character>> newPopulation= new ArrayList<>();
		Random rd= new Random();
		//first do one pass through the initial population note:=> size will be less than initial population this is only for roullete
		
		if(choice== SelectionType.ROULETTE) {
			//for(int i=0; i<initialPopulation.size(); i++) {
			while(newPopulation.size()<initialPopulation.size()) {
				List<Character> chromosome= initialPopulation.get(rd.nextInt(initialPopulation.size()));
				
				//If we are using a ROULETTE based selection technique then see if it meets conditions to add
				if(Math.random()<= gradeChromosome(chromosome)) {
					
					//determine if we mutate
					boolean mutated=rd.nextDouble()<=mutationProbaility;
					
					//determine if we crossover
					boolean crossover=rd.nextDouble()<=crossoverProbability;
					
					//determine which one we select if both are selected
					//-1 has no effect on this algorithims decision
					int mutate_or_crossover=-1;
					
					if(mutated && crossover) {
						mutate_or_crossover=rd.nextInt(2); // 0 for mutate, 1 for crossover 
					}
					
					//determine whether we are having a crossover
					if(		!(crossover && (mutate_or_crossover==-1 || mutate_or_crossover==1 )))		{
						crossover=false;
					}
					
					//determine whether we are having a mutation
					if(		!(mutated && (mutate_or_crossover==-1 || mutate_or_crossover==0 )))		{
						mutated=false;
					}
					
					
					//mutate the winner see above for how this is decided
					if(mutated) {
					
						newPopulation.add(mutateGene(chromosome));
					}
					//crossover the winner and some other random chromsoome
					if(crossover) {
					
						newPopulation.add(crossOver(chromosome, initialPopulation.get(rd.nextInt(initialPopulation.size()))));
					}
					//no mutation or crossover happened so add the winner to the new population
					else if (!mutated && !crossover){
					
						newPopulation.add(chromosome);
					}
					
				}
					
			}
		}
		
		//self explanatory
		if(choice== SelectionType.TOURNAMENT) {
		
			//tournament size is a factor of populationSize
			int tournamentSize= (int) Math.ceil(Math.log(initialPopulation.size())/(double)Math.log(2));
			
	
			
			if(initialPopulation.size()<40 && initialPopulation.size()>10) {	//added for edge cases
				tournamentSize=3;
			}
			//incase really small population
			if(tournamentSize<2) {
				tournamentSize=2;
			}
			
			//keep on doing the tournament until we fill up population
			while(newPopulation.size()<initialPopulation.size()) {
				//contestents in our tournament
				List<List<Character>> contestents = new ArrayList();
				
				//Fill up the tournament set
				for(int i= 0; i<tournamentSize; i++) {
					//randomly add contestends from [0,n)
					int randomlyGeneratedInt= rd.nextInt(initialPopulation.size());
					contestents.add(initialPopulation.get(randomlyGeneratedInt));
				}
				
				//loop through our contestents and add the highest fitness value to our new population
	
				List<Character> winner= contestents.get(0);
				
				//other guy will be the chromosome with the second highest fitness
				List<Character> otherGuy= null;
				
				for(List<Character> chromosome: contestents ) {
					
					//if current chromosome's fitness is higher than the current winner then replace 
					if(gradeChromosome(chromosome)>gradeChromosome(winner)) {
						otherGuy=winner;
						winner= chromosome;
					}
					
				}
				
				//for the edge case that the most fit individual is selected, we select a random chromosome
				if(otherGuy==null) {
					otherGuy=contestents.get(rd.nextInt(contestents.size()));
				}
				
				//determine if we mutate
				boolean mutated=rd.nextDouble()<=mutationProbaility;
				
				//determine if we crossover
				boolean crossover=rd.nextDouble()<=crossoverProbability;
				
				//determine which one we select if both are selected
				//-1 has no effect on this algorithims decision
				int mutate_or_crossover=-1;
				
				if(mutated && crossover) {
					mutate_or_crossover=rd.nextInt(2); // 0 for mutate, 1 for crossover 
				}
				
				//determine whether we are having a crossover
				if(		!(crossover && (mutate_or_crossover==-1 || mutate_or_crossover==1 )))		{
					crossover=false;
				}
				
				//determine whether we are having a mutation
				if(		!(mutated && (mutate_or_crossover==-1 || mutate_or_crossover==0 )))		{
					mutated=false;
				}
				
				
				//mutate the winner see above for how this is decided
				if(mutated) {
				newPopulation.add(mutateGene(winner));
				}
				//crossover the winner see above for how this is determined
				if(crossover) {
					newPopulation.add(crossOver(winner, otherGuy));
				}
				//no mutation or crossover happened so add the winner to the new population
				else if (!mutated && !crossover){
				
					newPopulation.add(winner);
				}
				
			}
		
			
		}

		return newPopulation;
		
	}

	/**
	 * This method returns the crossover (child of two genes). 
	 * It is guaranteed the resulting gene will be valid (meaning no conflicts) 
	 * It is not guaranteed for the resulting gene to be unique-> meaning it is possible the resulting child will equal winner
	 * This method is  biased towards winner for resolving conflicts as it represents the chromosome with the greater fitness value
	 * @param winne the first gene
	 * @param otherGuy the second gene
	 * @return a child (mixture) of the two genes
	 */
	private List<Character> crossOver(List<Character> winner, List<Character> otherGuy) {
		Random rd = new Random();
		
		//child to return
		List<Character> child= new ArrayList<>();
		
		//decide which half gets which characters
		List<Character> ch= new ArrayList<>(this.lettersUsed);
	
		//shuffle for randomization
		Collections.shuffle(ch);
		
		//determine the point where we are dividing
		int divide=rd.nextInt(ch.size());
		
		//Sets to make it easier to decide what belongs to what
		Set<Character> otherHalf=new HashSet(ch.subList(0, divide));
		Set<Character> winnerHalf= new HashSet(ch.subList(divide, ch.size()));
		
		//Maps to store the values of what we need
		Map<Character, Integer> win= new HashMap();
		Map<Character, Integer> oth= new HashMap();
		
		//fill up the tournament winner's map
		for(int i=0; i<winner.size(); i++) {
			
			if(winnerHalf.contains(winner.get(i))) {
				win.put(winner.get(i), i);
			}
		}
		
		//fill up the other map
		for(int i=0; i<otherGuy.size(); i++) {
			
			if(otherHalf.contains(otherGuy.get(i))) {
				oth.put(otherGuy.get(i), i);
			}
		}
		
		//begin generating the child
		for(int i=0; i<winner.size(); i++) {
			child.add(' ');
		}
			
		//we start to fill values up with the winner to bias our decision towards him incase of a "merge conflict" where different letters take the same digit
		for(Character chLarry: win.keySet()) {
			child.set(win.get(chLarry), chLarry);
		}
		
		for(Character chLarry: oth.keySet()) {
			//we check for merge conflict meaning that 2 characters represent the same digit 
			//in this case we radomly re assign the opposer to create a valid chromosome
			if(!child.get(oth.get(chLarry)).equals(' ')) {
				int index= rd.nextInt(child.size());
				
				//keep searching until we resolve the conflict
				while(!child.get(index).equals(' ')) {
					index= rd.nextInt(child.size());
				}
				child.set(index, chLarry);
			}
			//Valid child
			else{
				child.set(oth.get(chLarry), chLarry);
			}
		}
		
		return child;
	}

	/**
	 * This method guarantees that the new chromosome will not equal the old and that there will be no conflict where 2 characters represent the same digit 
 * @param chromosome
 * @return This method returns a mutated chromosome where one gene is changed
 */
	private List<Character> mutateGene(List<Character> chromosome) {

		List<Character> newChromosome= new ArrayList<>();
		newChromosome.addAll(chromosome);
			
		//randomly select a gene to mutate and track it
		Random rd= new Random();
		int index=rd.nextInt(newChromosome.size());
		
		Character toMutate =' ';
		
		//do the actual selection
		while(toMutate.equals(' ')) {
			index=rd.nextInt(newChromosome.size());
			toMutate= newChromosome.get(index);	
		}
		
		//delete the old gene
		newChromosome.set(index, ' ');
		
		//make sure we dont  insert at the old index and in extension make no mutations
		int oldIndex=index;
		
	
		//keep on searching until we find a blank spot since we want to change one gene at a time
		index=rd.nextInt(newChromosome.size());
		
		while(	(!newChromosome.get(index).equals(' '))	&&	index!=oldIndex) {
			index=rd.nextInt(newChromosome.size());
		}
		
		//randomly insert this chromosome into a new spot
		newChromosome.set(index, toMutate);
		
		return newChromosome;
	}

	/**
	 * This method generates the initial population
	 * It is guranteed every chromosome will be valid
	 * @param populationSize
	 * @return the initial population
	 */
	private List<List<Character>> generateInitialPopulation(int populationSize) {
		List<List<Character>> initialPopulation= new ArrayList();
		Random rd= new Random();
		
		for(int i =0; i<populationSize; i++) {
			List<Character> chromosome= new ArrayList<>(10);
			for(int j=0; j< 10; j++ ) {
				chromosome.add(' ');
			}
			int count =0; //count the amount of digits inserted
			Queue<Character> letters= new LinkedList(this.lettersUsed);	//add the letters to a queue 
			Character insertion= letters.poll();
			while(count<lettersUsed.size()) {
				int randomInt=rd.nextInt(10);

				if(chromosome.get(randomInt).equals(' ')) {
					chromosome.set(randomInt, insertion);
					count++;
					insertion=letters.poll();
				}
			}
			initialPopulation.add(chromosome);		
		}
		
		return initialPopulation;
	}
	
	/**
	 * This method returns the fitness value of the chromosome
	 *  This ratio is determined by (augend+addend)/sum if the denominator is greater or sum/(augend+addend) otherwise
	 * @param chromosome
	 * @return The fitness value
	 * returns 1 if it is a solution or a ratio otherwise.
	 */
	private double gradeChromosome(List<Character> chromosome) {

    	if(new HashSet<Character>(chromosome).size()<this.lettersUsed.size()) {	//rule out any defective stuff
    		return 0;
    	}
    	
    	Map<Character, Integer> extractedData= new HashMap<>();
    	//Extract all the values into a map
    	for(int i=0; i< chromosome.size(); i++) {
    		
    		if(extractedData.containsKey(chromosome.get(i))	&& !chromosome.get(i).equals(' ')){	// fail safe in case we have a duplicate letter
    		
    			return 0;
    		}
    		extractedData.put(chromosome.get(i), i);

    	}
    	
    	//Process the augend
    	StringBuilder bob= new StringBuilder();
    	double augendSum=0;
    	for(int i=0; i<this.augend.length(); i++) {
    		bob.append(extractedData.get(this.augend.charAt(i)));
    	}
    	 
    	augendSum=Double.valueOf(bob.toString());
    	
    	//reset the StringBuilder and Process the addend
    	bob.setLength(0);
    	double addendSum=0;
    	for(int i=0; i<this.Addend.length(); i++) {
    		bob.append(extractedData.get(this.Addend.charAt(i)));
    	}
    	addendSum=Double.valueOf(bob.toString());
    	
    	//reset the StringBuilder and Process the sum
    	bob.setLength(0);
    	double sumSum=0;
    	for(int i=0; i<this.Sum.length(); i++) {
    		bob.append(extractedData.get(this.Sum.charAt(i)));
    	}
    	sumSum=Double.valueOf(bob.toString());
  	
    	
    	//System.out.println("addend sum is: "+ addendSum + " augendSum is: "+ augendSum+ " the sumSum is "+ sumSum);
    	
    	
    	if(addendSum+augendSum==sumSum) {	//If this holds true we have our solution
    	
    		return 1.0;
    	}
   

    	if(sumSum>(addendSum+augendSum)) {
    		return ((addendSum+augendSum)/(double)sumSum);
    	}
    		
    		return sumSum/(double)(addendSum+augendSum);
    		
    		}
    	

}
