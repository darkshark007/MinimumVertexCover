package hashList;

public class HashList<T> {
	
	Node_Sentinel<T>[] Sentinels;
	int dataCount = 0;
	
	@SuppressWarnings("unchecked")
	public HashList(int size) {
		Sentinels = new Node_Sentinel[size+1];
		
		for ( int c = 0; c < Sentinels.length; c++) {
			Sentinels[c] = new Node_Sentinel<T>();
		}	
	}
	
	public void insertEnd(int index, T data) {
		// Step 0: Pre-Insert Checks
		// Check to ensure proper insert Index
		if ( ( (index+1) > Sentinels.length ) || ( index < 0 ) ) { return; } // FIXME: Maybe return some sort of error flag??
		
		// Step 1: Create the Node
		Node_Data<T> current = new Node_Data<T>(data);
		
		// Step 2: Update new Node's Next Pointer with the Next pointer of the Next Sentinel
		current.next = Sentinels[index+1].nextNode;
		
		// Step 3: Update next Sentinel's Prev Node's Next Reference with the new Node
		if ( Sentinels[index+1].prevNode == null ) { }
		else { Sentinels[index+1].prevNode.next = current; }

		// Step 4: Get Pivot Node.  Overwrite Reference in the Sentinel Chain.
		Node_Data<T> pivot;
		pivot = Sentinels[index+1].prevNode;
		Sentinels[index+1].prevNode = current;
		
		// Step 5: Cascade down the list
		for ( int c = index+2; c < Sentinels.length; c++) {
			if ( Sentinels[c].prevNode == pivot ) Sentinels[c].prevNode = current;
			else break;
		}
		
		// Step 6: Cascade Up the list
		for ( int c = index; c >= 0; c--) {
			if ( Sentinels[c].prevNode == pivot ) Sentinels[c].nextNode = current;
			else break;
		}
		
		dataCount++;
	}
	
	public T peekFirstItem() { return Sentinels[0].nextNode.data; }
	public T peekFirstItem(int index) { return Sentinels[index].nextNode.data; }

	public T popFirstItem() {
		// Step 0: Pre-pop checks
		if ( Sentinels[0].nextNode == null ) {
			// No items in this list.  Do nothing.
			return null;
		}

		Node_Data<T> temp = Sentinels[0].nextNode;
		
		// Step 1: Always update the 0th Sentinel
		Sentinels[0].nextNode = temp.next;
		
		// Step 2: Cascade Down
		for ( int c = 1; c < Sentinels.length; c++) {

			if ( Sentinels[c].prevNode == temp ) Sentinels[c].prevNode = null;
			if ( Sentinels[c].nextNode == temp ) Sentinels[c].nextNode = temp.next;
			else break;
			
		}
		
		dataCount--;
		return temp.data; 
	}
	
	public T popFirstItem(int index) {
		// Step 0: Pre-pop checks
		if ( Sentinels[index].nextNode == Sentinels[index+1].nextNode ) {
			// No items in this bucket.  Do nothing.
			return null;
		}
		Node_Data<T> temp = Sentinels[index].nextNode;
		
		// Step 1: Re-Link the list.
		Sentinels[index].prevNode.next = temp.next;
		
		// Step 2: Cascade Up
		for ( int c = index; c >= 0; c--) {
			if ( Sentinels[c].nextNode == temp ) Sentinels[c].nextNode = temp.next;
			else break;
		}
		
		// Step 3: Cascade Down
		for ( int c = index+1; c < Sentinels.length; c++) {
			if ( Sentinels[c].prevNode == temp ) Sentinels[c].prevNode = Sentinels[index].prevNode;
			else break;
		}
		
		dataCount--;
		return temp.data; 
	}
	
	public boolean isEmpty() { return (dataCount == 0); }
	
	public void printTree() {
		Node_Data<T> current = Sentinels[0].nextNode;
		Node_Data<T> upTo;
		for ( int c = 0; c < Sentinels.length-1; c++) {
			upTo = Sentinels[c+1].prevNode;
			if ( Sentinels[c].prevNode == null ) System.out.println("      [Null]");
			else System.out.println("      [N"+Sentinels[c].prevNode.data.toString()+"]");
			System.out.println("            [S"+c+"]");
			if ( Sentinels[c].nextNode == null ) System.out.println("      [Null]");
			else System.out.println("      [N"+Sentinels[c].nextNode.data.toString()+"]");
			if ( Sentinels[c].nextNode != Sentinels[c+1].nextNode ) {
		        while ( current != null ) {
		        	System.out.println("[N"+current.data.toString()+"]");
		        	if ( current == upTo ) {
			        	current = current.next;
			        	break;
		        	}
		        	current = current.next;
		        }
			}
		}
		if ( Sentinels[Sentinels.length-1].prevNode == null ) System.out.println("      [Null]");
		else System.out.println("      [N"+Sentinels[Sentinels.length-1].prevNode.data.toString()+"]");
		System.out.println("            [S"+(Sentinels.length-1)+"]");
		System.out.println("      [Null]");	
	}
	
	public void printList() {
		Node_Data<T> current = Sentinels[0].nextNode;
		while ( current != null ) {
			System.out.println("[N"+current.data.toString()+"]");
			current = current.next;
		}
	}
	



}
