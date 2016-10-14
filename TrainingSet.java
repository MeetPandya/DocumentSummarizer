package com.meet.RTD.DocumentSummarizer;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class TrainingSet {
	
	public Set<String> trainingSet = new TreeSet<String>();
	String[] noiseWords = {"The" ,"This", "There", "That", "a", "an", "is", "to", "be", "by", "were"};
	
	public void trainSet(String s){
		String[] set = s.split(" ");
		for (String string : set) {
			if(noiseWords.toString().indexOf(string)<0)
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
