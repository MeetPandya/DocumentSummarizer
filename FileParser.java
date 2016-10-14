package com.meet.RTD.DocumentSummarizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {
	public static Map<String, Integer> wordMap = new ConcurrentHashMap<String, Integer>();
	public static Map<String, Map> fileMap = new HashMap<String, Map>();
	
	public static void main(String[] args) {
		parseFiles();
	}
	//nextLine = in.readLine()) != null
	public static void parseFiles(){
		try{
			//InputStreamReader in = new InputStreamReader(System.in);
			FileReader in = new FileReader("06_7.xml");
			BufferedReader reader = new BufferedReader(in);
			String reg1 = "^<catchphrase \"id.";
			String reg2 = "<.";
			Pattern pattern1 = Pattern.compile(reg1);
			Pattern pattern2 = Pattern.compile(reg2);
			TrainingSet set = new TrainingSet();
			String nextLine;
			int maxScore = 0;
			Heap wordHeap = new Heap(100);
			while ((nextLine = reader.readLine()) != null) {
				Matcher matcher1 = pattern1.matcher(nextLine);
				Matcher matcher2 = pattern2.matcher(nextLine);
				if(matcher1.find()){
					nextLine = nextLine.replaceAll("<catchphrase \"id=c?\\d+.>", "");
					nextLine = nextLine.replaceAll("</catchphrase>", "");
					set.trainSet(nextLine);
				}
				else if(!matcher2.find() && nextLine.trim().length()>0){
					String[] mystring = nextLine.split("\n\n");
					/*for (String string : mystring) {
						System.out.println(string);
					}*/
					int rowScore = set.getScore(mystring);
					if(rowScore > maxScore)
						maxScore = rowScore;
					if(rowScore>0){
						wordHeap.insert(mystring[0], rowScore);
						wordMap.put(mystring[0], rowScore);
					}
				}
			}
			/*Iterator<String> itr = wordMap.keySet().iterator();
			while(itr.hasNext()){
				String s = itr.next();
				if(wordMap.get(s) < maxScore/2){
					wordMap.remove(s);
				}
			}
			System.out.println(wordMap.size());*/
			System.out.println(wordHeap.remove().getKey());
			System.out.println(wordHeap.remove().getKey());
			System.out.println(wordHeap.remove().getKey());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
