package tests;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

import algorithms.*;
import graph.Graph;
import graph.GraphGenerator;
import graph.Node;

public class GraphBlockTester {

	static Semaphore sem = new Semaphore(16);
	
	public static void main(String[] args) {
		runBlockOnSize();
	}
	
	public static void runBlockOnSize() {
		for ( int s = 5; s <= 30; s += 5 ) runBlockOnCompleteness(s);
	}

	public static void runBlockOnCompleteness(int size) {
		for ( int c = 25; c < 1000; c += 50 ) runBlockOnDistribution(size, c);
	}
	
	public static void runBlockOnDistribution(int size,int completeness) {
		for ( int c = 0; c <= 1000; c += 50 ) runTestBlock(size, completeness, c);
	}
	
	public static void runTestBlock(int size, int completeness, int distribution) {
		/* Non-Threaded #1
		for ( int i = 0; i < 10; i++ ) runTest(size, completeness, distribution);
		/* */

		/* Non-Threaded #2
		for ( int i = 0; i < 10; i++ ) (new TestRunner(size, completeness, distribution,sem)).run();
		/* */

		/* Threaded */
		for ( int i = 0; i < 20; i++ ) {
			try { sem.acquire(1); } catch (InterruptedException e) { e.printStackTrace(); }
			Thread Thd = new Thread(new TestRunner(size, completeness, distribution,sem));
			Thd.start();
		}
		/* */
	}
	
	public static void runTest(int size, int completeness, int distribution) {
		
		Random r = new Random();
		int seed = r.nextInt();
		
		Graph g = (new GraphGenerator()).genGraph(size, completeness, distribution,seed);
		
		//g.printGraph();
		
		LinkedList<Node> MVC,MVC2,MVC3,MVC4,MVC5;

		
		// Brute Force
		MVC = (new AlgBruteForce()).getMinVertCover(g);
		
		// New Approx Alg
		MVC2 = (new AlgNewApproximation()).getMinVertCover(g);
		
		// Greedy Approx Alg
		MVC3 = (new AlgGreedyApproximation()).getMinVertCover(g);

		// Factor-2 Approx Alg
		MVC4 = (new AlgFactorTwoApproximation()).getMinVertCover(g);

		// Random-Vertex Approxmation Algorithm
		MVC5 = (new AlgRandomVertexApproximation()).getMinVertCover(g);

		System.out.println(size+"\t"+completeness+"\t"+distribution+"\t"+seed+"\t"+MVC.size()+"\t"+MVC2.size()+"\t"+MVC3.size()+"\t"+MVC4.size()+"\t"+MVC5.size());
	}
}
