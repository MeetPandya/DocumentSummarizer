package com.meet.RTD.DocumentSummarizer;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class TrainingSet {
	
	public Set<String> trainingSet = new TreeSet<String>();
	
	public void trainSet(String s){
		String[] set = s.split(" ");
		for (String string : set) {
			trainingSet.add(string);
		}
	}
	
	public int getScore(String[] in){
		Iterator<String> itr = trainingSet.iterator();
		int rowScore = 0;
		for (String string : in) {
			while (itr.hasNext()) {
				String m = itr.next();
				if(string.indexOf(m) >= 0)
					rowScore++;
			}
		}
		return rowScore;
	}
	

}
