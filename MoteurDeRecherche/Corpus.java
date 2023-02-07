package MoteurDeRecherche;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;

import khoja.ArabicStemmerKhoja;


public class Corpus {
	

	
	@SuppressWarnings("rawtypes")
	public Map<String,HashMap> map_global=new HashMap();
	public Map<String,Map> map_global_TfIdf=new HashMap<String,Map>();
	
	public Corpus() throws IOException {
		super();
		CalculateFunction calFunction=new CalculateFunction();
		Gson gson  = new Gson();
		List<String> documents = Arrays.asList("document1.txt","document2.txt","document3.txt","document4.txt","document5.txt");
		
		
		for(String doc : documents) 
			 {
				
				 List<String> racineTermsList= calFunction.StemmerKhoja(doc);
				
				 Map<String,Integer> map_document=new HashMap<String, Integer>();
				 map_document.putAll(calFunction.Termes_occurences(racineTermsList));
				 
				 this.map_global.put(doc, (HashMap<String, Integer>)map_document);
		
				
			 }
		this.map_global_TfIdf.putAll(calFunction.Map_global_TfIdf(map_global));
		 
		 
	        
	        //save objectMap in file
	        String jsonFormat1 = gson.toJson(this.map_global_TfIdf);
	        Files.write(Paths.get("documents/corpus_tfidf"), jsonFormat1.getBytes());
		
	        String jsonFormat2 = gson.toJson(this.map_global);
	        Files.write(Paths.get("documents/corpus_tf"), jsonFormat2.getBytes());
	}
	
	
	public static void main(String[] args) throws IOException  {
		
		Corpus corpus=new Corpus();
		System.out.println("le corpus est enregstré dans les fichiers document/corpus.*");
	}


}
