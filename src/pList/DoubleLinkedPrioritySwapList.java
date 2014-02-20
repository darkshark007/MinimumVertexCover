package pList;

public class DoubleLinkedPrioritySwapList {

	Node first;
	Node last;
	int size;
	static boolean debug1 = false;
	
	public DoubleLinkedPrioritySwapList() {
		first = null;
		last = null;
		size = 0;
	}
	
	public int size() { return size; }
	
	public void insert(int id, int pri) {
		size++;
		Node n = new Node(id,pri);
		
		if ( last == null ) {
			first = n;
			last = n;
		}
		else { 
			Node current = last;
			
			current.next = n;
			n.prev = current;
			
			while ( current.pri < current.next.pri ) {
				if ( debug1 ) System.out.println("Swapping "+current.id+" with "+current.next.id);
				swap(current);
				current = current.prev.prev;
				if ( current == null ) break;
			}
			// Swapping done
			if ( debug1 ) System.out.println("Swapping Done");
			// Find the new last link
			current = last;
			while ( current.prev != null ) current = current.prev;
			first = current;
			if ( debug1 ) System.out.println("Found first");

			// Find the new first link			
			current = last;
			while ( current.next != null ) current = current.next;
			last = current;
			if ( debug1 ) System.out.println("Found last");
		}
	}
	public void printList() {
		Node current = first;
		while ( current != null ) {
			System.out.println("ID: "+current.id+"\tP: "+current.pri);
			current = current.next;
		}
	}
	
	public int getPriority(int id) {
		Node current = first;
		while ( current.id != id ) current = current.next;
		
		return current.pri;
	}
	
	public Node getElement(int elNum) {
		Node current = first;
		for ( int i = 0; i < elNum; i++ ) current = current.next;
		return current;
	}
	
	public Node findElement(int id) {
		Node current = first;
		while  ( current != null && current.id != id ) current = current.next;
		return current;
	}
	
	public void setPriority(int id, int newPri) {
		Node current = first;
		while ( current.id != id ) current = current.next;
		
		current.pri = newPri;
		if ( current.next != null ) {
			while ( current.pri < current.next.pri ) {
				if ( debug1 ) System.out.println("Swapping "+current.id+" with "+current.next.id);
				swap(current);
				//current = current.prev.prev;
				if ( current.next == null ) break;
			}
		}
		if ( current.prev != null ) {
			current = current.prev;
			while ( current.pri < current.next.pri ) {
				if ( debug1 ) System.out.println("Swapping "+current.id+" with "+current.next.id);
				swap(current);
				current = current.prev.prev;
				if ( current == null ) break;
			}
		}
		
		// Find the new last link
		current = last;
		while ( current.prev != null ) current = current.prev;
		first = current;
		if ( debug1 ) System.out.println("Found first");

		// Find the new first link			
		current = last;
		while ( current.next != null ) current = current.next;
		last = current;
		if ( debug1 ) System.out.println("Found last");

	}
	
	public void swap(Node in) {
		Node curm1 = in.prev;
		Node cur = in;
		Node curp1 = in.next;
		Node curp2 = in.next.next;
		
		curp1.prev = null;
		cur.next = null;
		
		if ( curm1 != null ) {
			curm1.next = curp1;
			curp1.prev = curm1;
			if ( debug1 ) System.out.print("\tCurM1 "+curm1.id+" -> "+curm1.next.id);
			if ( debug1 ) System.out.print("\tCurP1 "+curp1.id+" -> "+curp1.prev.id);
		}
		curp1.next = cur;
		cur.prev = curp1;
		if ( debug1 ) System.out.print("\tCurP1 "+curp1.id+" -> "+curp1.next.id);
		if ( debug1 ) System.out.print("\tCur "+cur.id+" -> "+cur.prev.id);
		if ( curp2 != null ) {
			cur.next = curp2;
			curp2.prev = cur;
			if ( debug1 ) System.out.print("\tCur "+cur.id+" -> "+cur.next.id);
			if ( debug1 ) System.out.print("\tCurP2 "+curp2.id+" -> "+curp2.prev.id);
		}
		if ( debug1 ) System.out.println();
	}
	
	public class Node {
		
		int id;
		int pri;
		Node next;
		Node prev;
		
		public Node(int ID, int PRI) {
			id = ID;
			pri = PRI;
		}
		
		public int getPriority() { return pri; }
		public int getID() { return id; }
	}
}
