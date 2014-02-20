package algorithms;

import graph.Graph;
import graph.Node;

import java.util.LinkedList;
import java.util.PriorityQueue;

import pList.DoubleLinkedPrioritySwapList;

public class AlgNewApproximationExtended implements IAlg {

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
	    	//System.out.println("First: "+firstItem.id);
	    	if ( !(Q1.isEmpty()) ) if ( Q1.peek().degree == firstItem.degree ) {
	    		firstItem = getLowestInternalEdges(firstItem,Q1);
	    	}
	    	if ( firstItem == null ) System.out.println("NULL!");
	    	MVC.add(firstItem);
	    	gr.removeVertex(firstItem.id);
	    	toCheck.remove(firstItem);
	    }
	    	    
	    return MVC;
	}
	
	private Node getLowestInternalEdges(Node firstItem, PriorityQueue<Node> q1) {
		DoubleLinkedPrioritySwapList toCheck = new DoubleLinkedPrioritySwapList(); 
		DoubleLinkedPrioritySwapList degReduc = new DoubleLinkedPrioritySwapList();
		
		toCheck.insert(firstItem.id, firstItem.degree);
		degReduc.insert(firstItem.id, firstItem.degree);
		// System.out.println("Adding Element "+firstItem.id+" to DegReduc");
		for ( Node n : q1 ) {
			// System.out.println("Adding Element "+n.id+" to DegReduc");
			toCheck.insert(n.id, n.degree);
			degReduc.insert(n.id, n.degree);
		}
		int count = 0;
		Node curr;
		while ( degReduc.getElement(0).getPriority() == degReduc.getElement(1).getPriority() ) {
			
			// Create a List
			LinkedList<DoubleLinkedPrioritySwapList.Node> list = new LinkedList<DoubleLinkedPrioritySwapList.Node>();
			DoubleLinkedPrioritySwapList.Node first = toCheck.getElement(count++);
			list.add(first);
			while ( count < toCheck.size() && toCheck.getElement(count).getPriority() == first.getPriority() ) list.add(toCheck.getElement(count++));
			
			//degReduc.printList();
			
			// Reduce the list
			for ( DoubleLinkedPrioritySwapList.Node DLN : list ) {
				curr = gr.nodes[DLN.getID()]; 
				
				for ( Node N : curr.edges ) {
					//System.out.println("Reducing "+N.id+" by 1");
					degReduc.setPriority(N.id,degReduc.getPriority(N.id)-1);
					//degReduc.printList();
				}
		
			}
			
			System.out.println("<<Reduc Block Completed>>");
			degReduc.printList();
			
			if ( degReduc.getElement(0).getPriority() == 0 ) break;			
		}
		
		System.out.println("Selected: "+degReduc.getElement(0).getID());
		
		return gr.nodes[degReduc.getElement(0).getID()];
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
