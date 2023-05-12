package edu.yu.da;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import java.util.Random;

import org.junit.jupiter.api.Test;

import edu.yu.da.ArithmeticPuzzleBase.SolutionI;
import edu.yu.da.GeneticAlgorithmConfig.SelectionType;

class ArithmeticPuzzleTest {

	@Test
	void testTournament() {
		Random rd= new Random();
		ArithmeticPuzzleBase o = new ArithmeticPuzzle("Y", "ER", "W");
		SolutionI g=o.solveIt(new GeneticAlgorithmConfig(100,1, SelectionType.TOURNAMENT, 1, 1));
		System.out.println(g.solution());		
		
	}
	
	@Test
	void testRoulette() {
		Random rd= new Random();
		ArithmeticPuzzleBase o = new ArithmeticPuzzle("Y", "ER", "W");
		SolutionI g=o.solveIt(new GeneticAlgorithmConfig(100,5, SelectionType.ROULETTE, 1, 1));
		System.out.println(g.solution());		
	}
	
	@Test 
	void demoApiTest() {
		final String augend="Q";
		final String addend="R";
		final String sum="AB";
		
		final double MUTATION_PROBABILITY = 0.2;
		final double CROSSOVER_PROBABILITY = 0.7;
		final int MAX_GENERATIONS=100;
		final int POPULATION_SIZE = 1000;
		SelectionType selectionType = SelectionType.TOURNAMENT;	
		
		final GeneticAlgorithmConfig gac = new GeneticAlgorithmConfig(POPULATION_SIZE, MAX_GENERATIONS, selectionType , MUTATION_PROBABILITY, CROSSOVER_PROBABILITY);
		final ArithmeticPuzzleBase aqb= new ArithmeticPuzzle(augend, addend, sum);
		final SolutionI solution = aqb.solveIt(gac);
		System.out.println(solution.solution());
		
	}
	
	@Test 
	void experimentRoueletteandTournament() {
		
	int genRou=0;
	int genTou=0;
	int solRou=0;
	int solTou=0;
	
	for(int i=0; i<50; i++) {
		ArithmeticPuzzleBase roulette = new ArithmeticPuzzle("T", "H", "AE");
		SolutionI rou=roulette.solveIt(new GeneticAlgorithmConfig(100,50, SelectionType.ROULETTE, 1, 1));
		
		ArithmeticPuzzleBase tournament = new ArithmeticPuzzle("T", "H", "AE");
		SolutionI tou=tournament.solveIt(new GeneticAlgorithmConfig(100,50, SelectionType.TOURNAMENT,1,1));
		
		genRou+=rou.nGenerations();
		genTou+=tou.nGenerations();
		
		if(!rou.solution().equals(Collections.EMPTY_LIST)) {
			solRou++;
		}
		
		if(!tou.solution().equals(Collections.EMPTY_LIST)) {
			
			solTou++;
		}
		
		
		
	}
	System.out.println("Displaying data for ROULETTE: Total number of times solution reached " +solRou + " generations to reach them " + genRou);
	System.out.println("Displaying data for TOURNAMENT: Total number of times solution reached " +solTou + " generations to reach them " + genTou);
		
	}
	
	@Test 
	void experimentMutationandCrossover() {
		
	int genRou=0;
	int genTou=0;
	int solRou=0;
	int solTou=0;
	

	
	for(int i=0; i<500; i++) {
		ArithmeticPuzzleBase roulette = new ArithmeticPuzzle("TU", "HU", "AE");
		SolutionI rou=roulette.solveIt(new GeneticAlgorithmConfig(100,50, SelectionType.ROULETTE, .7, .7));
		
		ArithmeticPuzzleBase tournament = new ArithmeticPuzzle("TU", "HU", "AE");
		SolutionI tou=tournament.solveIt(new GeneticAlgorithmConfig(100,50, SelectionType.ROULETTE,1,1));

		genRou+=rou.nGenerations();
		genTou+=tou.nGenerations();
		
		if(!rou.solution().equals(Collections.EMPTY_LIST)) {
			solRou++;
		}
		
		if(!tou.solution().equals(Collections.EMPTY_LIST)) {
			
			solTou++;
		}
		
		
		
	}
	System.out.println("Displaying data for Mutation/Crossover1: Total number of times solution reached " +solRou + " generations to reach them " + genRou);
	System.out.println("Displaying data for Mutation/Crossover2: Total number of times solution reached " +solTou + " generations to reach them " + genTou);
		
	}
	@Test
	void testFailures() {
		ArithmeticPuzzleBase roulette = new ArithmeticPuzzle("ABCDE", "FGHIJK", "LMNOPQ");
		SolutionI rou=roulette.solveIt(new GeneticAlgorithmConfig(100,50, SelectionType.ROULETTE, .7, .7));
		
		roulette = new ArithmeticPuzzle("ABCE", "FD", "LMNO");
		SolutionI rotu=roulette.solveIt(new GeneticAlgorithmConfig(100,100, SelectionType.ROULETTE, 1, 1));
		
		assertThrows( IllegalArgumentException.class,		()->new ArithmeticPuzzle(" ", "FD", "LMNO"));

		assertThrows( IllegalArgumentException.class,		()->new ArithmeticPuzzle("P", "J", "K "));
		
	}

}
