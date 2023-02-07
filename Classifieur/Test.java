package Classifieur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;

public class Test {

	@SuppressWarnings("unchecked")
	public static  void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

		/*
		 HashMap<String,Integer> map = new HashMap<String,Integer>();
		  
				map.put("a",2); 
				map.put("b",3); 
				map.put("c",4); 

        Gson gson  = new Gson();
        
        //save objectMap in file
        String jsonFormat = gson.toJson(map);
        Files.write(Paths.get("temp2"), jsonFormat.getBytes());
     
        //read map in file
        Map<String,Integer> map2 = new HashMap<String, Integer>();
        String text = new String(Files.readAllBytes(Paths.get("temp2")), StandardCharsets.UTF_8);
        map2 = gson.fromJson(text,map2.getClass());
        
        System.out.println(text);
        System.out.println(map2);
        
        

		String str="mane?!!!.";
		
		List<Character> ponctuations = Arrays.asList(',',';','.',':','?','!');
		boolean i=true;
		while(i)
		{
			if(ponctuations.contains(str.charAt(str.length()-1))) 
				{
				      str=str.substring(0,str.length()-1);
				}
			else i=false;
		}

		
		*/
		
		System.out.println(! "ama10.5$righ".matches(".*\\d.*"));
		 String pathFolder="datasetLabeling/";
		 
	   	  File directoryPath = new File(pathFolder);
	   	  List<String> Listfiles = Arrays.asList(directoryPath.list());
	   	  System.out.println(Listfiles);
	}
		
         
}
