package graph;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

public class Graph {
	
	public Node[] nodes;
	public int size;
	public int edgeCount = 0;
	
	public Graph(int sz) {
		size = sz;
		nodes = new Node[size];
		for ( int i = 0; i < size; i++ )
			nodes[i] = new Node(i);
	}
	
	public Graph clone() {
		Graph g = new Graph(size);
		for ( Node in : nodes) {
			for ( Node in2 : in.edges ) {
				g.addEdge(in.id, in2.id);
			}
		}
		return g;
	}
	
	public boolean addEdge(int e1, int e2) {
		if ( nodes[e1].addEdge(nodes[e2]) ) edgeCount += 2;
		return nodes[e2].addEdge(nodes[e1]);
	}
	
	public void removeVertex(int in) {
		Node temp = nodes[in];
		nodes[in] = new Node(in);

		//for ( Node n : temp.edges ) {
		Node n;
		while( temp.edges.size() > 0 ) {
			//System.out.print("Loop:\t"+temp.edges.isEmpty()+"\t"+temp.edges.size());
			n = temp.edges.remove();
			//System.out.println("\t----:\t"+temp.edges.isEmpty()+"\t"+temp.edges.size());
			if (n == null) {
				System.out.println(">>> N IS NULL!!!");
				if (temp == null) { System.out.println(">>> nodes[in] IS NULL!!!"); }
				continue;
			}
			if (n.id == in) {
				System.out.println(">>> EQUALS!!!");
				continue;
			}
			if (nodes[n.id] == null) {
				System.out.println(">>> nodes[n] IS NULL!!!");
				continue;
			}
			if (temp == null) {
				System.out.println(">>> nodes[in] IS NULL!!!");
				continue;
			}
			if (nodes[n.id].edges == null) {
				System.out.println(">>> n.EDGES IS NULL!!!");
				continue;
			}
			nodes[n.id].edges.remove(temp);
			if ( nodes[n.id].degree > 0 ) nodes[n.id].degree = nodes[n.id].degree - 1;
		}
	}

	public void printGraph() {
		for ( int i = 0; i < size; i++) {
			LinkedList<Node> temp = nodes[i].edges;
			System.out.println("Node  "+i+"  Links to:");
			for ( Node n : temp ) {
				System.out.println("   Node "+n.id);
			}
		}
	}
}
