package MoteurDeRecherche;






import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.google.gson.Gson;

public class Main {
	

	@SuppressWarnings("unchecked")
	
	public static void main(String[] args) throws IOException  {
		
	
	
		CalculateFunction calFunction=new CalculateFunction();
		 Gson gson  = new Gson();
			 @SuppressWarnings("rawtypes")
			 Map<String,HashMap> map_global=new HashMap();
			 //map_global.putAll(corpus.map_global);
			 
			 String text1 = new String(Files.readAllBytes(Paths.get("documents/corpus_tf")), StandardCharsets.UTF_8);
			 map_global = gson.fromJson(text1,map_global.getClass());
		
		    Map<String,Map> map_global_TfIdf=new HashMap<String,Map>();
		    //map_global_TfIdf.putAll(corpus.map_global_TfIdf);
			
		   
	        String text = new String(Files.readAllBytes(Paths.get("documents/corpus_tfidf")), StandardCharsets.UTF_8);
	        map_global_TfIdf = gson.fromJson(text,map_global_TfIdf.getClass());
		

			
Scanner scan = new Scanner(System.in);
	System.out.println("chercher");
	String requet = scan.nextLine();
	System.out.println("\n");
	Formatter output = new Formatter("documents/input.txt", "utf-8");
	output.format("%s\n",requet);	
	output.close();
	
	List<String> racineTermsrequet= calFunction.StemmerKhoja("input.txt");

	Map<String,Integer> Requet_doc =new HashMap();
	Requet_doc.putAll(calFunction.Termes_occurences(racineTermsrequet));
	

  Map<String,Double> Requet_doc_tf =new HashMap();
  
  for(String k:Requet_doc.keySet())
  {
	  Requet_doc_tf.put(k,calFunction.TF(Requet_doc,k));
  }
  
  
  

  
  
  Map<String,Double> RequetCosDoc =new HashMap();
  
  for(String k:map_global_TfIdf.keySet())
  {
	  RequetCosDoc.put(k,calFunction.cosDocuments(Requet_doc_tf, map_global_TfIdf.get(k)));
  }
  
  
  
  List<Double>  coses =new ArrayList<Double>();
  coses.addAll(RequetCosDoc.values());
  
  HashSet<Double> strictcosesList=new HashSet<Double>(coses);
  TreeSet<Double> TreestrictcosesList = new TreeSet<Double>(Collections.reverseOrder());
  TreestrictcosesList.addAll(strictcosesList);
  
  for(double cs:TreestrictcosesList)
  {
	  for(String k:RequetCosDoc.keySet())
	  {
		  if(RequetCosDoc.get(k).equals(cs))
		  {
			  String line = Files.readAllLines(Paths.get("documents/"+k)).get(0);
			  System.out.println(k+"\n"+line+"..."+"\n \n");
			  
			  
		  }
	  }
  }
		
	} // end main

} // end class

