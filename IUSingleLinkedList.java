import java.util.*;


/**
 * linked list based implementation of IndexedUnsortedList
 * @author adamberridge
 *
 * @param <T> generic type of object stored in list
 */
public class IUSingleLinkedList<T> implements IndexedUnsortedList<T>{
	
	private SLLNode<T> head, tail; //first/last node in linked list
	private int count; //number of elements in list
	private int modCount; //number of modificiations to the single linked list
	
	/**
	 * default constructor
	 */
	public IUSingleLinkedList() {
		count = 0;
		modCount = 0;
		head = null;
		tail = null;
	}

	@Override
	public void addToFront(T element) {

		SLLNode<T> newNode = new SLLNode<T>(element); 	
		newNode.setNext(head);
		head = newNode;
		
		if (count == 0) {
			tail = newNode;
		}
		count++;
		modCount++;
		
	}

	@Override
	public void addToRear(T element) {
		
		add(element);
		
	}

	@Override
	public void add(T element) {
		
		SLLNode<T> newNode = new SLLNode<T>(element); 
		if(count == 0)
		{
			head = newNode; // if it is empty
			tail = newNode;
		}
		else
		{
			tail.setNext(newNode); 
		}		
		tail = newNode; 
		count++; 
		modCount ++;
						
	}

	@Override
	public void addAfter(T element, T target) {
		
		int index = indexOf(target);
		
		if(index == -1)
		{
			throw new NoSuchElementException("IUArrayList - addAfter - Element not found");
		}
		if(index == count-1)
		{
			add(element);
		}
		else
		{
			SLLNode<T> current = head;
			// go to index
			for(int i = 0; i < index; i++)
			{
				current = current.getNext();
			}
			
			SLLNode<T> newNode = new SLLNode<T>(element); 
			SLLNode<T> next = current.getNext();
			current.setNext(newNode);
			newNode.setNext(next);
			count++;
			modCount ++;
		}	
		
	}

	@Override
	public void add(int index, T element) {
		
		//check if index is in bounds
		if (index < 0 || index > count) {
			throw new IndexOutOfBoundsException("IUSingleLinkedlist - add(index, element) - index is out of bounds");
		}
		//if adding to end of list
		if(index == count)
		{
			add(element);
		}
		else if(index == 0) //add to the front
		{
			addToFront(element);

		}
		else {
			
			SLLNode<T> newNode = new SLLNode<T>(element); 
			SLLNode<T> current = head;
			//iterate to the node before the node to be removed
			for (int i=0; i<index-1; i++) {
				current = current.getNext();
			}
			SLLNode<T> next = current.getNext();
			current.setNext(newNode);
			newNode.setNext(next);
			
			count++; 
			modCount++;
		}		
	}

	@Override
	public T removeFirst() {
		
		if (isEmpty()) {
			throw new NoSuchElementException("IUSingleLinkedlist - removeFirst - list is empty");
		}
		T theRemoved = head.getElement();
		//If removing last item
		if(count == 1) 
		{
			tail = null;
			head = null;
		}
		else
		{

			SLLNode<T> next = head.getNext();
			head.setNext(null);
			head = next;
		}
		count--;
		modCount ++;	
		return theRemoved;
			
	}

	@Override
	public T removeLast() {
		
		if (isEmpty()) {
			throw new NoSuchElementException("IUSingleLinkedlist - removeFirst - list is empty");
		}
		T theRemoved = head.getElement();
		//If removing last item
		if(count == 1) 
		{
			tail = null;
			head = null;
		}
		else {
			SLLNode<T> next;
			SLLNode<T> current = head;
			//iterate to the node before the node to be removed
			for(int i = 0; i < count-2; i++)
			{
				current = current.getNext();
			}
			next = current.getNext();
			theRemoved = next.getElement();
			tail = current;
			current.setNext(null);
		}
		count--;
		modCount++;
		return theRemoved;
	}

	@Override
	public T remove(T element) {
		
		int index = indexOf(element);
		//if element not in list
		if (index == -1) {
			throw new NoSuchElementException("IUSingleLinkedlist - remove(element) - element not in list");
		}
		return remove(index);
	}

	@Override
	public T remove(int index) {
		
		//if index out of bounds
		if (index < 0 || index >= count) {
			throw new IndexOutOfBoundsException("IUSingleLinkedlist - remove(index) - index is out of bounds");
		}
		//if removing first element
		if (index == 0) {
			return removeFirst();
		}
		//if removing last element
		else if (index == count-1) {
			return removeLast();
		}
		
		else {
			SLLNode<T> current = head;
			//iterate to the node before the node to be removed
			for (int i=0; i<index-1; i++) {
				current = current.getNext();
			}
			
			SLLNode<T> next = current.getNext();
			current.setNext(next.getNext());
			T theRemoved = next.getElement();
			next.setNext(null);;
			
			count--;
			modCount++;
			return theRemoved;
		}
	}

	@Override
	public void set(int index, T element) {
		
		if(index < 0 || index >= count)
		{
			throw new IndexOutOfBoundsException("IUSingleLinkedList - set - invalid index"); 
		}
		
		SLLNode<T> current = head;
		//iterate to the node to be set 
		for(int i = 0; i < index; i++)
		{
			current = current.getNext();
		}
		current.setElement(element);
		modCount ++;
		
	}

	@Override
	public T get(int index) {
		
		if (index < 0 || index >= count) {
			throw new IndexOutOfBoundsException("IUSingleLinkedList - get - invalid index");
		}
		SLLNode<T> current = head;
		//iterate to node at index
		for (int i=0; i<index; i++) {
			current = current.getNext();
		}
		return current.getElement();
	}

	@Override
	public int indexOf(T element) {	
	
			int index = 0;
			boolean found = false;
			SLLNode<T> current = head;
			
			while(!found && index < count)
			{
				if(current.getElement() == element)
				{
					found = true;
				}
				else
				{
					index++;
					current = current.getNext();
				}
			}
			if(!found)
			{
				index = -1;
			}
			
			return index;
	}

	@Override
	public T first() {
		if(isEmpty())
		{
			throw new NoSuchElementException("IUSingleLinkedList - first - invalid element"); 
		}
		return head.getElement();
	}

	@Override
	public T last() {
		if(isEmpty())
		{
			throw new NoSuchElementException("IUSingleLinkedList - last - invalid element"); 
		}
		return tail.getElement();
	}

	@Override
	public boolean contains(T target) {
		
		int index = 0;
		boolean found = false;
		SLLNode<T> current = head;
		
		while(!found && index < count)
		{
			if(current.getElement() == target)
			{
				found = true;
			}
			else
			{
				index++;
				current = current.getNext();
			}
		}
		return found;
	}

	@Override
	public boolean isEmpty() {
		
		return count==0;
	}

	@Override
	public int size() {
		
		return count;
	}

	@Override
	public Iterator<T> iterator() {
		return new AListIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		{
			throw new UnsupportedOperationException("List - listIterator - listIterator not implemented");

		}
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		{
			throw new UnsupportedOperationException("List - listIterator - listIterator not implemented");

		}
	}
	

	/**
	 * Iterator for the IUArrayList Class
	 * @author 
	 *
	 */
	private class AListIterator implements Iterator<T>
	{

		private SLLNode<T> next, current, previous; // nodes containing reference to next, current, and previous objects
		private int itrModCount; // number of modifications made to list when iterator instantiated
 		private boolean canRemove; //whether remove precondition is met

		/**  
		 * Default constructor
		 */
		public AListIterator()
		{
			current = null;
			next = head;
			previous = null;
			itrModCount = modCount; 
			canRemove = false;

		}
			
		@Override
		public boolean hasNext() 
		{
			return (next != null);
		}

		@Override
		public T next() 
		{
			if (modCount != itrModCount) {
				throw new NoSuchElementException("ListIterator - next - end of iteration"); 
			}

			if(!hasNext()) //Check precondition 
			{
				throw new NoSuchElementException("AListIterator - next - end of iteration"); 
			}
			T item = next.getElement(); //variable to return the object before it gets removed
			previous = current;
			current = next;
			next = next.getNext(); 
			canRemove = true;
			return item;
		}
		
		public void remove(){
		
			if (modCount != itrModCount) {
				throw new NoSuchElementException("ListIterator - next - end of iteration"); 
			}
			if (!canRemove){
				throw new IllegalStateException("AListIterator - remove - can't remove");
			}
		
			//if removing first element
			if (current.equals(head)){
				
				head = next;
				current = null;
			}
			// if removing last element
			else if (current.equals(tail)){
			
				tail = previous;
				current = null;
			}
			else {
				
				previous.setNext(next);
				current = null;
			}
			
			count --;
			current = previous;
			canRemove = false;
		}

	}
}


