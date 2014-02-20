package algorithms;

import graph.Graph;
import graph.Node;

import java.util.LinkedList;
import java.util.PriorityQueue;

public class AlgGreedyApproximation implements IAlg {

	public Graph gr;
	
	public LinkedList<Node> getMinVertCover(Graph in) {
	
		LinkedList<Node> MVC = new LinkedList<Node>();
		LinkedList<Node> toCheck = new LinkedList<Node>();
		
	    gr = in.clone();
	    
	    for ( Node n : gr.nodes) {
	    	toCheck.add(n);
	    }
	    
	    while ( toCheck.size() > 0 ) {
	    	
	    	// Create the Priority Queue
	    	PriorityQueue<Node> Q1 = new PriorityQueue<Node>();
	    	
	    	LinkedList<Node> toRemove = new LinkedList<Node>();
	    	for ( Node n : toCheck ) {
	    		if ( n.degree == 0 ) {
	    			toRemove.add(n);
	    		}
	    		else Q1.add(n);
	    	}
	    	if ( !(toRemove.isEmpty()) ) {
	    		for ( Node n : toRemove ) toCheck.remove(n);
	    	}
	    	if ( Q1.isEmpty() ) {
	    		break;
	    	}
	    	
	    	Node firstItem = Q1.remove();
	    	MVC.add(firstItem);
	    	gr.removeVertex(firstItem.id);
	    	toCheck.remove(firstItem);
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
