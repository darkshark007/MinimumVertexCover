package graph;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

public class Node implements Comparable<Node> {
	
	public int degree = 0;
	public LinkedList<Node> edges = new LinkedList<Node>();
	public int id;
	
	public Node(int in) {
		id = in;
	}
	
	public boolean addEdge(Node in) {
		if (!(edges.contains(in)) && !(in.equals(this))) {
			edges.add(in);
			degree++;
			return true; 
		}
		return false;
	}
	
	public int compareTo(Node in) {
		if ( in.degree == degree ) return 0;
		else if ( in.degree < degree ) return -1;
		return 1;
	}
	
}
