package com.meet.RTD.DocumentSummarizer;

import java.util.Arrays;

public class Heap {
	public Node[] heapArray;
	public int maxSize;
	public int currentIndex = 0;
	public Heap(int size){
		heapArray = new Node[size];
		maxSize = size;
	}
	
	public void insert(String key, int data){
		if(currentIndex == maxSize){
			heapArray = Arrays.copyOf(heapArray, heapArray.length*2);
			maxSize = heapArray.length;
		}	
		heapArray[currentIndex] = new Node(key,data);
		percolateUp(currentIndex++);
	}
	
	public Node remove(){
		Node top = heapArray[0];
		heapArray[0] = heapArray[--currentIndex];
		percolateDown(0);
		return top;
	}
	
	public void percolateUp(int index){
		int parent = (index-1)/2;
		Node current = heapArray[index];
		while(index > 0 && heapArray[parent].getData() < current.getData()){
			heapArray[index] = heapArray[parent];
			index = parent;
			parent = (parent-1)/2;
		}
		heapArray[index] = current;
	}
	
	public void percolateDown(int index){
		Node current = heapArray[index];
		int large;
		while(index < currentIndex/2){
			int left = 2*index + 1;
			int right = left + 1;
			if(right < currentIndex && heapArray[left].getData() < 
										heapArray[right].getData())
				
				large = right;
			else
				large = left;
			if(current.getData() >= heapArray[large].getData())
				break;
			heapArray[index] = heapArray[large];
			index = large;
		}
		heapArray[index] = current;
	}
	
	public void display(){
		for (int i = 0; i < currentIndex; i++) {
			System.out.print(heapArray[i].getData()+" ");
		}
		System.out.println();
	}

}
class Node{
	private int data;
	private String key;
	
	public Node(String key, int data){
		this.key = key;
		this.data = data;
	}

	public int getData() {
		return data;
	}
	
	public String getKey(){
		return key;
	}
	
}
