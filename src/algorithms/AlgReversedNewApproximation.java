package algorithms;

import graph.Graph;
import graph.Node;
import hashList.HashList;

import java.util.LinkedList;
import java.util.PriorityQueue;

public class AlgReversedNewApproximation implements IAlg {

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
	    	//PriorityQueue<Node> Q1 = new PriorityQueue<Node>();
	    	HashList<Node> Q1 = new HashList<Node>(gr.size-1);
	    	
	    	LinkedList<Node> toRemove = new LinkedList<Node>();
	    	for ( Node n : toCheck ) {
	    		if ( n.degree == 0 ) {
	    			toRemove.add(n);
	    		}
	    		else Q1.insertEnd((n.degree-1), n);
	    		//else Q1.insertEnd(gr.size-n.degree-2, n);
	    	}
	    	if ( !(toRemove.isEmpty()) ) {
	    		for ( Node n : toRemove ) toCheck.remove(n);
	    	}
	    	if ( Q1.isEmpty() ) {
	    		break;
	    	}
	    	
	    	Node firstItem = Q1.popFirstItem();
	    	//System.out.println("First: "+firstItem.id);
	    	if ( !(Q1.isEmpty()) ) if ( Q1.peekFirstItem().degree == firstItem.degree ) {
	    		//System.out.println(Q1.peekFirstItem().degree+" == "+firstItem.degree);
	    		LinkedList<Node> List = new LinkedList<Node>();
	    		List.add(firstItem);
	    		while ( Q1.peekFirstItem().degree == firstItem.degree ) {
		    		//System.out.println("  >"+Q1.peek().id);
		    		List.add(Q1.popFirstItem());
		    		if ( Q1.isEmpty() ) break;
	    		}
	    		
	    		// Get the node with the lowest internal edges
	    		firstItem = getLowestInternalEdges(List);
	    	}
	    	if ( firstItem == null ) System.out.println("NULL!");
	    	
	    	//System.out.println("Selected: "+firstItem.id);
	    	
	    	toRemove = new LinkedList<Node>();
	    	// Remove the selected vertex from the graph; it is not in MVC
	    	toRemove.add(firstItem);
	    	// Add each node it shares an edge with to the MVC;  They are all points in MVC
	    	for ( Node n : firstItem.edges ) {
	    		MVC.add(n);
	    		toRemove.add(n);
	    	}
	    	for ( Node n : toRemove ) { 
	    		gr.removeVertex(n.id);
	    		toCheck.remove(n);
	    	}
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
