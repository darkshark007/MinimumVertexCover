package pList;

public class DLPSLTester {
	
	public static void main(String[] args) {
		
		DoubleLinkedPrioritySwapList list = new DoubleLinkedPrioritySwapList();
		list.printList();
		
		list.insert(1, 10);
		list.insert(2, 5);
		list.insert(3, 20);
		list.insert(4, 0);
		list.insert(5, 15);
		list.insert(6, 10);
		list.setPriority(1,10);
		list.printList();

	
	}

}
