package MoteurDeRecherche;

import java.io.IOException;
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

import khoja.ArabicStemmerKhoja;

public class CalculateFunction {
	
	
	public List<String> StemmerKhoja(String text) throws IOException
	{
		
		@SuppressWarnings("resource")
		
		// chargement les mots vides 
		String stopwords = new Scanner(Paths.get("stopwords.txt"), "UTF-8").useDelimiter("\\A").next();
		
		// Listes des mots vides
		List<String> stopwordsList = Arrays.asList(stopwords.split("\n"));
		
		
		// create l'objet de notre steamer khoja 
		ArabicStemmerKhoja mystemmer = new ArabicStemmerKhoja();
		
		List<Character> ponctuations = Arrays.asList('،','.','؛',':','?','!');
		
		
		String fin = "documents/" + text;
		String fout = "documents/out.txt";

		Scanner input; Formatter output;
		
			input = new Scanner(Paths.get(fin),"utf-8");
			output = new Formatter(fout, "utf-8");
			while (input.hasNextLine()) {
				String line = input.nextLine();
				if (line.length()==0) continue;
				String lineout = new String();
				String[] tokens = line.split("\\s");
				for (String token : tokens) {
					if (!stopwordsList.contains(token)) 
					{    // ignore stopwords
						
						if(!token.isEmpty() && ponctuations.contains(token.charAt(token.length()-1)))
						{
						     token=token.substring(0,token.length()-1);
						
						}
						
						String result = mystemmer.stem(token);    // Khoja rooting algorithm
						output.format("%s\n", result);
					}
				}
				
			}
			input.close();
			output.close();
			
			// la liste des racines de termes de document
			
			@SuppressWarnings("resource")
			String racineTerms = new Scanner(Paths.get("documents/out.txt"), "UTF-8").useDelimiter("\\A").next();
			 
			// list
			List<String> racineTermsList = Arrays.asList(racineTerms.split("\n"));
			
			//System.out.println(racineTermsList);
			racineTermsList.sort(Comparator.naturalOrder()); 

			return racineTermsList;
	}
		
	public  double TF(Map m,String term) 
		  { 
			  int size_document=0;  
			  List<Integer> recurentsTermes = new ArrayList(m.values());
			  size_document = recurentsTermes.stream().mapToInt(Integer::intValue).sum();
			  int recurentTerme=(int) m.get(term);
			   double tf= (double) recurentTerme/size_document;
			   return  tf;
		
		  }
		 
	public  double IDF(Map<String,HashMap> glob,String term)
	{    int numberOfDocuments=glob.size();
	     int    numberOfDocumentsExistTerm=0;
	     for(HashMap mapDoc: glob.values())
	     {	 
	    	 List<String> termes = new ArrayList(mapDoc.keySet());
	    	 
	    	 if(termes.contains(term)) 
	    	 {
	    		 numberOfDocumentsExistTerm+=1;
	    	 }
	    	 
	      }
	    
	     
		
		  return (double) Math.log((double) numberOfDocuments/numberOfDocumentsExistTerm);
	}

	public double norme(Map<String,Double> doc)
	{   double norm=0.0;
	     for(double v:doc.values())
	     {
	    	 norm+=Math.pow(v,2);
	     }
	     
		return Math.sqrt(norm);
	}

	public double cosDocuments(Map doc1,Map doc2)
	{  Map<String,Double> petit_doc=new HashMap();
	   Map<String,Double> grand_doc=new HashMap();
		int size1=doc1.size();
		int size2=doc1.size();
		if(size1>=size2) 
		{	
			petit_doc.putAll(doc2);
			grand_doc.putAll(doc1);
		}
		else 
		{
			petit_doc.putAll(doc1);
			grand_doc.putAll(doc2);
		}
		
		double cos=0;
		
		
		for(String k:petit_doc.keySet())
		{ 
		   
		   for(String kk:grand_doc.keySet())
		   {
			   if(k.equals(kk))
			   {   
			       
				   cos+=petit_doc.get(k)*grand_doc.get(kk);
			   }
		   }
		}
	   
		cos=cos/(norme(doc1)*norme(doc2));
		
		return cos;
	}

	public  Map<String,Integer> Termes_occurences(List<String> ListeRacinTerms)
	{
		HashSet<String> strictRacineTermsList=new HashSet<String>(ListeRacinTerms);
		
		
		 Map<String,Integer> map_document=new HashMap<String, Integer>();
		
		for (String term :  strictRacineTermsList) 
		{
			int count=0;
				
			for (String elem:ListeRacinTerms) 
			{
					if (term.equalsIgnoreCase(elem)) count+=1;
					
			}
			
			map_document.put(term, count);
			
			
		}
		
		return  map_document;
		
	}


	public  Map<String,Map> Map_global_TfIdf(Map<String, HashMap> map_docs_occur_terme)
	{
		Map<String,Map> map_global_TfIdf=new HashMap<String,Map>();
		for(String k:map_docs_occur_terme.keySet())
		{
			Map<String,Double> doc2=new HashMap<String, Double>();
			Map<String,Integer> doc=new HashMap<String, Integer>();
			doc=map_docs_occur_terme.get(k);
			for(String kk:doc.keySet())
			{
				double idf=(double)IDF(map_docs_occur_terme,kk);
				double tf=(double)TF(doc,kk);
			    
				double tf_idf=(double)idf*tf;
				doc2.put(kk,tf_idf);
			
	             
			}
			
			//System.out.println(doc2);
			map_global_TfIdf.put(k,doc2);
			
					
			
		}
		return map_global_TfIdf;
	}
	

}
