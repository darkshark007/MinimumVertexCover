package tests;

import java.util.LinkedList;

import algorithms.*;
import graph.Graph;
import graph.GraphGenerator;
import graph.Node;

public class GraphWorksTest {

	
	public static void main(String[] args) throws InterruptedException {
		
		Graph g;

		//g = (new GraphGenerator()).genGraph(16,451,180,62194303);  // Case Study #3
		//g = (new GraphGenerator()).genGraph(54,269,288,-474853187);  // Case Study #4
		
		g = (new GraphGenerator()).genGraph(20,146,24,89716427);
		

		
		
		g.printGraph();
		
		LinkedList<Node> MVC;

		
		// Brute Force
		if ( g.size <= 30 ) {
			MVC = (new AlgBruteForceThreaded()).getMinVertCover(g);
			//MVC = (new AlgBruteForce()).getMinVertCover(g);
			System.out.print("MVC Brute Force\r\n   Len: "+MVC.size()+"\r\n     ");
			for ( Node n : MVC ) {
				System.out.print(n.id+"->");
			}
			System.out.println();
		}

		
		
		// New Approx Alg
		MVC = (new AlgNewApproximation()).getMinVertCover(g);
		System.out.print("MVC New Approx\r\n   Len: "+MVC.size()+"\r\n     ");
		for ( Node n : MVC ) {
			System.out.print(n.id+"->");
		}
		System.out.println();

		
		
		// New Approx Extended Alg
		MVC = (new AlgNewApproximationExtended()).getMinVertCover(g);
		System.out.print("MVC New Approx Extended\r\n   Len: "+MVC.size()+"\r\n     ");
		for ( Node n : MVC ) {
			System.out.print(n.id+"->");
		}
		System.out.println();

		
		
		// New Approx Alg
		MVC = (new AlgReversedNewApproximation()).getMinVertCover(g);
		System.out.print("MVC Reversed New Approx\r\n   Len: "+MVC.size()+"\r\n     ");
		for ( Node n : MVC ) {
			System.out.print(n.id+"->");
		}
		System.out.println();

		
		
		// Greedy Approx Alg
		MVC = (new AlgGreedyApproximation()).getMinVertCover(g);
		System.out.print("MVC Greedy Approx\r\n   Len: "+MVC.size()+"\r\n     ");
		for ( Node n : MVC ) {
			System.out.print(n.id+"->");
		}
		System.out.println();

		
		
		// Factor-2 Approx Alg
		MVC = (new AlgFactorTwoApproximation()).getMinVertCover(g);
		System.out.print("MVC Factor-Two Approx\r\n   Len: "+MVC.size()+"\r\n     ");
		for ( Node n : MVC ) {
			System.out.print(n.id+"->");
		}
		System.out.println();

		
		
		// Random-Vertex Approxmation Algorithm
		MVC = (new AlgRandomVertexApproximation()).getMinVertCover(g);
		System.out.print("MVC Random Vert Approx\r\n   Len: "+MVC.size()+"\r\n     ");
		for ( Node n : MVC ) {
			System.out.print(n.id+"->");
		}
		System.out.println();
	}
}
