/*
	 * @author: Dikshant Gupta
	 * 
	 */

// Min-Heap Implementation
public class MinHeap {
	private Building[] heapArray;
	int size = 0;

	public MinHeap() {
		heapArray = new Building[2000];
	}
// Function for extracting the minimum element from the Heap
	public Building extractMin() {
		if (size <= 0)
			return null;
		else {
			Building min = heapArray[0];
			
			heapArray[0] = heapArray[size-1]; // Move last to position 0
			size--;
			minHeapify(0);
			return min;
		}
	}

	public void insert(Building element) {
		heapArray[size++] = element;
		int loc = size - 1; // and get its location

		// Swap logic
		while (loc > 0 && heapArray[loc].compareTo(heapArray[parent(loc)]) < 0) {
			swap(loc, parent(loc));
			loc = parent(loc);
		}
	}

	
	public boolean isEmpty() {
		return size == 0;
	}
	
	private void minHeapify(int i) {
		int left = leftChild(i); // index of node i's left child
		int right = rightChild(i); // index of node i's right child
		int small; // will hold the index of the node with the smallest
						

		
		if (left <= size - 1 && heapArray[left].compareTo(heapArray[i]) < 0)
			small = left; 
		else
			small = i; // no, so node i is the smallest so far

		
		if (right <= size - 1
				&& heapArray[right].compareTo(heapArray[small]) < 0)
			small = right; // yes, so the right child is the largest

		if (small != i) {
			swap(i, small);
			minHeapify(small);
		}
	}


	/*
	 * Swap two positions i and j in ArrayList a.
	 * 
	 * a -->   the arrayList
	 *          
	 * i --> first position
	 *            
	 * j --> second position           
	 */
	private void swap(int i, int j) {
		Building t = heapArray[i];
		heapArray[i] = heapArray[j];
		heapArray[j] = t;
	}
    /*
	 *
	 * i-->index of the parent node	
	 */
	private static int parent(int i) {
		return (i - 1) / 2;
	}
	/*
	 * Return the index of the left child of node i.
	 * 
	 * i-->index of the parent node
	 */
	private static int leftChild(int i) {
		return 2 * i + 1;
	}

	/*
	 * Return the index of the right child of node i.
	 
	 * i-->index of the parent node	 
	 */
	private static int rightChild(int i) {
		return 2 * i + 2;
	}

	
}

