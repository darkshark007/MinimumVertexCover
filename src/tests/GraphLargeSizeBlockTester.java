package tests;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

import algorithms.*;
import graph.Graph;
import graph.GraphGenerator;
import graph.Node;

public class GraphLargeSizeBlockTester {

	static Semaphore sem = new Semaphore(16);
	
	public static void main(String[] args) {
		Random r = new Random();
		/* Non-Threaded
		for ( int i = 0; i < 94267; i++ ) (new LargeTestRunner((r.nextInt(81)+40), (r.nextInt(1000)+1), (r.nextInt(1001)),sem)).run();
		/* */
		
		
		/* Threaded */
		for ( int i = 0; i < 9932; i++ ) {
			try { sem.acquire(1); } catch (InterruptedException e) { e.printStackTrace(); }
			Thread Thd = new Thread(new LargeTestRunner((r.nextInt(141)+60), (r.nextInt(1000)+1), (r.nextInt(1001)),sem));
			Thd.start();
		}
		/* */

	}
	
		
}
