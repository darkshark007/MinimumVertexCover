package algorithms;

import graph.Graph;
import graph.Node;

import java.util.LinkedList;

public class AlgBruteForce implements IAlg {

	public Graph gr;
	
	public LinkedList<Node> getMinVertCover(Graph in) {
	
		LinkedList<Node> res = new LinkedList<Node>();
		
	    gr = in.clone();
	    
	    	    
	    int counter = 1;
	    int loopUntil = (int)Math.pow(2.0, gr.size);
	    boolean[] bool;
	    boolean[] bestList = null;
	    int len,bestLen = gr.size;
	    while ( counter < loopUntil) {
	    	bool = getBoolTable(gr.size,counter);
	    	if ( checkGraph(bool) ) {
	    		// Calc the length
	    		len = 0;
	    		for ( boolean b : bool ) if ( b == true ) len++;
	    		
	    		// Update the length
	    		if ( len < bestLen ) {
	    			bestLen = len;
	    			bestList = bool;
	    		}
	    	}
	    	counter++;
	    }
	    
	    // Build the list
	    for ( int i = 0; i < gr.size; i++ ) {
	    	if ( bestList[i] == true ) res.add(gr.nodes[i]);
	    }
		
		return res;
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
	
	public static boolean[] getBoolTable(int size, int value) {
		boolean[] bool = new boolean[size];
		int count = (int)Math.pow(2,size-1);
		int loop = size-1;
		while ( count > 0 ) {
			//System.out.println("V: "+value+"\t L:"+loop+"\tC: "+count+"\tM: "+(value % count));
			if ( value/count >= 1 ) {
				bool[loop] = true;
				value -= count;
			}
			loop--;
			count /= 2;
		}
		
		return bool;
	}
	
}
