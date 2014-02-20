package algorithms;

import graph.Graph;
import graph.Node;

import java.util.LinkedList;
import java.util.Random;

public class AlgFactorTwoApproximation implements IAlg {

	public Graph gr;
	
	public LinkedList<Node> getMinVertCover(Graph in) {
	
		LinkedList<Node> MVC = new LinkedList<Node>();
		LinkedList<Node> toCheck = new LinkedList<Node>();
		
	    gr = in.clone();
	    
	    int weight,pointA,pointB,rFactor;
	    int[] weights;
    	Node current;
    	Random r = new Random();
	    while ( true ) {
	    	// Determine the remaining vertices
		    for ( Node n : gr.nodes) {
		    	if ( n.degree > 0 ) toCheck.add(n);
		    }
		    
		    if ( toCheck.isEmpty() ) break;

		    //System.out.println("LOOP:  MAIN > "+toCheck.size());
	    	// Create the weight vector
	    	weights = new int[gr.size];
	    	weight = 0;
	    	for ( int i = toCheck.size()-1; i >= 0; i-- ) {
	    		current = toCheck.remove();
	    		weights[current.id] = current.degree;
	    		weight += current.degree;
	    	}
	    	
	    	do { 

			    // Determine point A
				rFactor = r.nextInt(weight)+1;
				pointA = 0;
				while ( rFactor > weights[pointA] ) {
					rFactor = rFactor - weights[pointA];
					pointA++;
				}
				
				// Determine point b
				rFactor = r.nextInt(weight)+1;
				pointB = 0;
				while ( rFactor > weights[pointB] ) {
					rFactor = rFactor - weights[pointB];
					pointB++;
				}

				//System.out.println("LOOP:  DO-W > "+pointA+": "+gr.nodes[pointA].degree+", "+pointB+": "+gr.nodes[pointB].degree);
				
	    	} while (!(gr.nodes[pointA].edges.contains(gr.nodes[pointB]))); 
	    	MVC.add(gr.nodes[pointA]);
	    	MVC.add(gr.nodes[pointB]);
	    	gr.removeVertex(pointA);
	    	gr.removeVertex(pointB);
	    }
	    	    
	    return MVC;
	}
	
	public boolean checkGraph(boolean[] in) {
		for (Node n : gr.nodes) {
			for ( Node n2 : n.edges ) {
				if ( ( in[n.id] == true ) || ( in[n2.id] == true ) ) continue;
				else return false;
			}
		}
		return true;
	}
}
