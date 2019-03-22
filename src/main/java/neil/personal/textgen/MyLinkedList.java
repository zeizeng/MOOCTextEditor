package neil.personal.textgen;

import java.util.AbstractList;


/**
 * A class that implements a doubly linked list
 *
 * @param <E> The type of the elements stored in the list
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MyLinkedList<E> extends AbstractList<E> {
    LLNode<E> head;
    LLNode<E> tail;
    int size;

    /**
     * Create a new empty LinkedList
     */
    public MyLinkedList() {
        // TODO: Implement this method
        head = new LLNode<>(null);
        tail = new LLNode<>(null);
        size = 0;
        head.next = tail;
        tail.prev = head;
    }

    /**
     * Appends an element to the end of the list
     *
     * @param element The element to add
     */
    public boolean add(E element) {
        // TODO: Implement this method
        LLNode newNode = new LLNode(element);
        newNode.prev = tail.prev;
        tail.prev.next = newNode;
        tail.prev = newNode;
        newNode.next = tail;
        size++;
        return true;
    }

    /**
     * Get the element at position index
     *
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public E get(int index) {
        // TODO: Implement this method.

        return node(index).data;

    }

    private LLNode<E> node(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException();
        if (index < (size >> 1)) {
            LLNode<E> llNode = head.next;
            for (int i = 0; i < index; i++) {
                llNode = llNode.next;
            }
            return llNode;
        } else {
            LLNode<E> llNode = tail.prev;
            for (int i = size - 1; i > index; i--) {
                llNode = llNode.prev;
            }
            return llNode;
        }
    }

    /**
     * Add an element to the list at the specified index
     *
     * @param index The index where the element should be added
     * @param element The element to add
     */
    public void add(int index, E element) {
        // TODO: Implement this method
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();
        if (index == size) {
            add(element);
        } else {
            LLNode indexNode = node(index);
            linkBefore(element, indexNode);
            size++;
        }
    }

    private void linkBefore(E element, LLNode<E> llNode) {
        LLNode<E> newNode = new LLNode<E>(element);
        newNode.next = llNode;
        newNode.prev = llNode.prev;
        llNode.prev = newNode;


    }


    /**
     * Return the size of the list
     */
    public int size() {
        // TODO: Implement this method
        return size;
    }

    /**
     * Remove a node at the specified index and return its data element.
     *
     * @param index The index of the element to remove
     * @return The data element removed
     * @throws IndexOutOfBoundsException If index is outside the bounds of the list
     */
    public E remove(int index) {
        // TODO: Implement this method
        LLNode<E> llNode = node(index);
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        if (size == 1) {
            head.next = tail;
            tail.prev = head;
        } else {
            llNode.next.prev = llNode.prev;
            llNode.prev.next = llNode.next;
        }
        size--;
        return llNode.data;
    }

    /**
     * Set an index position in the list to a new element
     *
     * @param index The index of the element to change
     * @param element The new element
     * @return The element that was replaced
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public E set(int index, E element) {
        LLNode<E> llNode = node(index);
        E e = llNode.data;
        llNode.data = element;
        return e;
    }
}

class LLNode<E> {
    LLNode<E> prev;
    LLNode<E> next;
    E data;

    public LLNode(E e) {
        this.data = e;
        this.prev = null;
        this.next = null;
    }

    public LLNode(LLNode<E> prev, E data, LLNode<E> next) {
        this.data = data;
        this.prev = prev;
        this.next = next;

    }
}
