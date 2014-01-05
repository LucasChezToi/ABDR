package Projet;

import java.util.ArrayList;
import java.util.List;

public class TestArg {
	
	public static void main(String[] args){
		List<Integer> listInt = new ArrayList<Integer>();
		updateList(listInt);
		System.out.println(listInt);
	}
	
	public static void updateList(List<Integer> listNext){
		for(int i=0;i<10;i++){
			listNext.add(i);
		}
	}
	
		
}
