package graph;

import java.util.Random;

public class GraphGenerator {

	Random r = new Random();

	public Graph genGraph(int size, int completeness, int distribution, long seed) {
		r = new Random(seed);
		return genGraph(size, completeness, distribution);
	}
	
	public Graph genGraph(int size, int completeness, int distribution) {
		Graph g = new Graph(size);
		
		
		
		// Assemble weight vector
		int weight = (size*100);
		int[] weights = new int[size];
		for ( int i = 0; i < size; i++) weights[i] = 100;
		
		// Determine factors
		int loopUntil = (int)(((double)( (size-1) * (size) )) * ((double)completeness/1000.0));
		if ( loopUntil == 0 ) loopUntil++;
		
		//System.out.println("LU: "+loopUntil);
		int rFactor;
		int pointA,pointB;
		while ( g.edgeCount < loopUntil ) {
		    // Determine point A
			rFactor = (r.nextInt(weight));
			pointA = 0;
			while ( rFactor > weights[pointA] ) {
				rFactor = rFactor - weights[pointA];
				pointA++;
			}
			//System.out.println("Selected A: "+pointA+"\t"+weights[pointA]+"\t"+weight);
			
			// Determine point b
			rFactor = (r.nextInt(weight));
			pointB = 0;
			while ( rFactor > weights[pointB] ) {
				rFactor = rFactor - weights[pointB];
				pointB++;
			}
			//System.out.println("Selected B: "+pointB+"\t"+weights[pointB]);
			
			boolean worked = g.addEdge(pointA, pointB);
			if ( !worked ) continue;
			
			// Add weights.
			weight += (distribution * 2);
			weights[pointA] += distribution;
			weights[pointB] += distribution;
			
			// If a node is maxxed, reduce it's weight to 0.
			if ( g.nodes[pointA].degree == size-1 ) {
				//System.out.println("Capped "+pointA);
				weight = weight-weights[pointA];
				weights[pointA] = 0;
			}
			if ( g.nodes[pointB].degree == size-1 ) {
				//System.out.println("Capped "+pointB);
				weight = weight-weights[pointB];
				weights[pointB] = 0;
			}
			
			
		}
		
		return g;
	}
}
