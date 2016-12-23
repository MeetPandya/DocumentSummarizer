import os

IN_PATH= os.getcwd()+"\landingFolder"
OUT_PATH = os.getcwd()+"\Category"
#defines the bucket of size 0-100, 101-500 , 501-1000 
#each bucket stores files whose common words are in range * count of common words
bucket = (100,500,1000)

stop_words = [
"a", "about", "above", "across", "after", "afterwards", "again", "against",
"all", "almost", "alone", "along", "already", "also", "although", "always",
"am", "among", "amongst", "amoungst", "amount", "an", "and", "another",
"any", "anyhow", "anyone", "anything", "anyway", "anywhere", "are",
"around", "as", "at", "back", "be", "became", "because", "become",
"becomes", "becoming", "been", "before", "beforehand", "behind", "being",
"below", "beside", "besides", "between", "beyond", "bill", "both",
"bottom", "but", "by", "call", "can", "cannot", "cant", "co", "con",
"could", "couldnt", "cry", "de", "describe", "detail", "do", "done",
"down", "due", "during", "each", "eg", "eight", "either", "eleven", "else",
"elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone",
"everything", "everywhere", "except", "few", "fifteen", "fifty", "fill",
"find", "fire", "first", "five", "for", "former", "formerly", "forty",
"found", "four", "from", "front", "full", "further", "get", "give", "go",
"had", "has", "hasnt", "have", "he", "hence", "her", "here", "hereafter",
"hereby", "herein", "hereupon", "hers", "herself", "him", "himself", "his",
"how", "however", "hundred", "i", "ie", "if", "in", "inc", "indeed",
"interest", "into", "is", "it", "its", "itself", "keep", "last", "latter",
"latterly", "least", "less", "ltd", "made", "many", "may", "me",
"meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly",
"move", "much", "must", "my", "myself", "name", "namely", "neither",
"never", "nevertheless", "next", "nine", "no", "nobody", "none", "noone",
"nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on",
"once", "one", "only", "onto", "or", "other", "others", "otherwise", "our",
"ours", "ourselves", "out", "over", "own", "part", "per", "perhaps",
"please", "put", "rather", "re", "same", "see", "seem", "seemed",
"seeming", "seems", "serious", "several", "she", "should", "show", "side",
"since", "sincere", "six", "sixty", "so", "some", "somehow", "someone",
"something", "sometime", "sometimes", "somewhere", "still", "such",
"system", "take", "ten", "than", "that", "the", "their", "them",
"themselves", "then", "thence", "there", "thereafter", "thereby",
"therefore", "therein", "thereupon", "these", "they", "thick", "thin",
"third", "this", "those", "though", "three", "through", "throughout",
"thru", "thus", "to", "together", "too", "top", "toward", "towards",
"twelve", "twenty", "two", "un", "under", "until", "up", "upon", "us",
"very", "via", "was", "we", "well", "were", "what", "whatever", "when",
"whence", "whenever", "where", "whereafter", "whereas", "whereby",
"wherein", "whereupon", "wherever", "whether", "which", "while", "whither",
"who", "whoever", "whole", "whom", "whose", "why", "will", "with",
"within", "without", "would", "yet", "you", "your", "yours", "yourself",
"yourselves"]

def parseDoc(fileName):
	"parse the file and return a dictionary"
	wordFreq = {}
	with open(fileName) as targetFile:
		for line in targetFile:
			"split the words from the line"
			words = line.split(" ")
			for word in words:
				if word in stop_words:
					continue
				word = word.translate(None,",./\\(){}[]!@#$%^&*`~+-\n\r\t_")
				count = wordFreq.get(word,0)
				wordFreq[word]=count+1
	return wordFreq.keys()


if __name__ == "__main__":
	"main function"
	filesList = os.listdir(IN_PATH)
	fileMaps = {}
	for file in filesList:
		fileMaps[file]=parseDoc(IN_PATH+"\\"+file)
	
	#now we have the map, we need to cluster files with same word frequencies
	
	WordMatchBucket = {}
	for i in range(len(filesList)):
		for j in range(len(filesList)-1,i,-1):
			wordFreqMap = fileMaps[filesList[i]]
			wordFreqMap2 = fileMaps[filesList[j]]
			diff = set(wordFreqMap).intersection(set(wordFreqMap2));
			#for commWord in diff:
			#	commWordCount += min(wordFreqMap[commWord],wordFreqMap2[commWord])
			percent1 = int((len(diff)*10)/len(wordFreqMap))
			percent2 = int((len(diff)*10)/len(wordFreqMap2))
			percent = min([percent1,percent2])
			freq = WordMatchBucket.get(percent,[])
			freq.append((filesList[i],filesList[j]))
			WordMatchBucket[percent] = freq
	for p in WordMatchBucket.keys():
		freq = WordMatchBucket[p]
		#freq[0][0] cluster with other 2 groups to form one big groups
		for j in range(len(freq)):
			for i in range(len(freq)-1,j,-1):
				if freq[j][0] != freq[i][0]:
					diff = set(fileMaps[freq[j][0]]).intersection(set(fileMaps[freq[i][0]]));
				else:
					diff = set(fileMaps[freq[j][0]]).intersection(set(fileMaps[freq[i][1]]));
				percent = int((len(diff)*10)/min(len(fileMaps[freq[j][0]]),len(fileMaps[freq[i][1]])))
				if(percent>=p):
					freq[j] = list(set(freq[j]).union(set(freq[i])))
					freq.remove(freq[i])
		
	for p in WordMatchBucket.keys():
		print "======= FOR Percent "+str(p)+" =============="
		for b in WordMatchBucket[p]:
			print "\nBucket =>"
			print b
			print "\n"
		
	

	