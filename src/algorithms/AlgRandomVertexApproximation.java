package algorithms;

import graph.Graph;
import graph.Node;

import java.util.LinkedList;
import java.util.Random;

public class AlgRandomVertexApproximation implements IAlg {

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
	    	
		    // Determine point A
			rFactor = r.nextInt(weight)+1;
			pointA = 0;
			while ( rFactor > weights[pointA] ) {
				rFactor = rFactor - weights[pointA];
				pointA++;
			}
				
	    	MVC.add(gr.nodes[pointA]);
	    	gr.removeVertex(pointA);
	    }
	    	    
	    return MVC;
	}
	
	private Node getLowestInternalEdges(LinkedList<Node> in) {
		Node result = null;
		int lowestDeg = gr.size;
		
		int count;
		for ( Node i1 : in ) {
			count = 0;
			for ( Node i2 : in ) {
				if ( i1.edges.contains(i2) ) count++;
			}
			//System.out.println(i1.id+":  "+count);
			if ( count < lowestDeg ) {
				result = i1;
				lowestDeg = count;
			}
		}
		
		return result;
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
