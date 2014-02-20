package algorithms;

import graph.Graph;
import graph.Node;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class AlgBruteForceThreaded implements IAlg {

	public Graph gr;
	public int segments = 8;
	public Semaphore sem;
	boolean[][] results;
	boolean[] bestResult;
	int bestLength;
	
	public LinkedList<Node> getMinVertCover(Graph in) throws InterruptedException {
	
		LinkedList<Node> res = new LinkedList<Node>();
		
		sem = new Semaphore(segments);
		
	    gr = in.clone();
	    
	    int counter = 1;
	    int maxLoops = (int)Math.pow(2.0, gr.size);
	    int loopUntil;
	    results = new boolean[segments][gr.size];
	    for (int c = 1; c < segments; c++ ) {
	    	sem.acquire(1);
	    	loopUntil = (int)(( (double)c / (double)segments ) * (double)maxLoops);
	    	Thread Thd = new Thread(new BruteForceThreaded(counter,loopUntil,c-1));
	    	Thd.start();
	    	counter = loopUntil+1;
	    }
    	sem.acquire(1);
    	Thread Thd = new Thread(new BruteForceThreaded(counter,maxLoops-1,segments-1));
    	Thd.start();
	    
    	// Wait until all threads are done
    	sem.acquire(segments);
    	
    	// Check the results obtained by each thread
    	int len, bestLen = gr.size;
    	boolean[] bestList = null;
    	for ( boolean[] b : results ) {
    		// Calc the length
    		len = 0;
    		for ( boolean b1 : b ) if ( b1 == true ) len++;
    		
    		// Update the length
    		if ( len < bestLen ) {
    			bestLen = len;
    			bestList = b;
    		}
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
	
	public class BruteForceThreaded implements Runnable {
		
		int start;
		int finish;
		int output;
		
		public BruteForceThreaded(int st, int fn, int otp) {
			start = st;
			finish = fn;
			output = otp;
		}
		
		public void run() {
		    int counter = start;
		    int loopUntil = finish;
		    boolean[] bool;
		    boolean[] bestList = new boolean[gr.size];
		    for ( int i = 0; i < gr.size; i++ ) {
		    	bestList[i] = true;
		    }
		    
		    int len,bestLen = gr.size - 1;
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
			//setResult(bestList);
			results[output] = bestList.clone();
			sem.release(1);
		}
	}
	
}
