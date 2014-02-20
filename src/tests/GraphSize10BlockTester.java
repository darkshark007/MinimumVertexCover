package tests;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

import algorithms.*;
import graph.Graph;
import graph.GraphGenerator;
import graph.Node;

public class GraphSize10BlockTester {

	static Semaphore sem = new Semaphore(16);
	
	public static void main(String[] args) {
		Random r = new Random();
		for ( int i = 0; i < 100000; i++ ) (new TestRunner((r.nextInt(16)+5), (r.nextInt(1000)+1), (r.nextInt(1001)),sem)).run();
	}
	
		
}
