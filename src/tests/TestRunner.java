package tests;

import graph.Graph;
import graph.GraphGenerator;
import graph.Node;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

import algorithms.AlgBruteForce;
import algorithms.AlgFactorTwoApproximation;
import algorithms.AlgGreedyApproximation;
import algorithms.AlgNewApproximation;
import algorithms.AlgNewApproximationExtended;
import algorithms.AlgRandomVertexApproximation;
import algorithms.AlgReversedNewApproximation;

public class TestRunner implements Runnable {

	int size;
	int completeness;
	int distribution;
	Semaphore sem;

	public TestRunner(int sz, int cmp, int dst,Semaphore outSem) {
		size = sz;
		completeness = cmp;
		distribution = dst;
		sem = outSem;
	}
	
	public void run() {
		
		Random r = new Random();
		int seed = r.nextInt();
		
		Graph g = (new GraphGenerator()).genGraph(size, completeness, distribution,seed);
		
		//g.printGraph();
		
		LinkedList<Node> MVC,MVC2,MVC3,MVC4,MVC5,MVC6;

		
		// Brute Force
		MVC = (new AlgBruteForce()).getMinVertCover(g);
		
		// New Approx Alg
		MVC2 = (new AlgNewApproximation()).getMinVertCover(g);
		
		// Reversed New Approx Alg
		MVC6 = (new AlgReversedNewApproximation()).getMinVertCover(g);

		// Greedy Approx Alg
		MVC3 = (new AlgGreedyApproximation()).getMinVertCover(g);

		// Factor-2 Approx Alg
		MVC4 = (new AlgFactorTwoApproximation()).getMinVertCover(g);

		// Random-Vertex Approxmation Algorithm
		//MVC5 = (new AlgRandomVertexApproximation()).getMinVertCover(g);
		MVC5 = (new AlgNewApproximationExtended()).getMinVertCover(g);

		System.out.println(size+"\t"+completeness+"\t"+distribution+"\t"+seed+"\t"+MVC.size()+"\t"+MVC2.size()+"\t"+MVC6.size()+"\t"+MVC3.size()+"\t"+MVC4.size()+"\t"+MVC5.size());
		sem.release(1);
	}

}
