package com.devheon.datastructure;

import java.io.Serializable;

/**
 * <pre>
 * Description
 *     자료구조 - 원형 큐
 * ===============================================
 * Member fields
 *     int front
 *     int rear
 *     int maxSize
 *     Object[] queueArray
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2020-01-05
 * </pre>
 */
public class CircularQueue implements Serializable {

	private static final long serialVersionUID = 1552145526783181606L;
	
	private int front;
	private int rear;
	private int maxSize;
	private Object[] queueArray;
	
	public CircularQueue(int maxSize) {
		this.front = 0;
		this.rear = 0;
		/* Size를 1로 잡은 경우 full, empty 판단을 위해 2로 설정해줘야함 */
		this.maxSize = maxSize == 1 ? 2 : maxSize;
		this.queueArray = new Object[this.maxSize];
	}
	
	/**
	 * <pre>
	 * Description
	 *     Queue가 꽉 차있으면, 100ms 마다 쉬면서 full check
	 * ===============================================
	 * Parameters
	 *     Object item
	 * Returns
	 *     void
	 * Throws
	 *     none
	 * ===============================================
	 *
	 * Author : HeonSeung Kim
	 * Date   : 2020-01-05
	 * </pre>
	 */
	public synchronized void enqueue(Object item) {
		while(isFull()) { try { Thread.sleep(100); } catch(InterruptedException e) {} }
		rear = (rear + 1) % maxSize;
		queueArray[rear] = item;
	}
	
	/**
	 * <pre>
	 * Description
	 *     Queue가 비어있으면 null 반환
	 * ===============================================
	 * Parameters
	 *     none
	 * Returns
	 *     Object
	 * Throws
	 *     none
	 * ===============================================
	 *
	 * Author : HeonSeung Kim
	 * Date   : 2020-01-05
	 * </pre>
	 */
	public synchronized Object dequeue() {
		if(isEmpty()) return null;
		front = (front + 1) % maxSize;
		return queueArray[front];
	}
	
	public boolean isFull() { return (rear + 1) % (maxSize) == front; }
	public boolean isEmpty() { return rear == front; }
	
}
