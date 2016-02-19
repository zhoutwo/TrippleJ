package tests;

import backend.*;
import data.*;
import utils.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class Testing {
	
	Map testMap = new Map();
	
	@Test 
	public void testNoHop(){
//		System.out.println("from Salina to Salina is "+testMap.getRoute(" Salina"," Salina","distance"));
//		System.out.println("from Topeka to Topeka is "+testMap.getRoute(" Topeka"," Topeka","distance"));
	}

	@Test
	public void testOneHop() {
//		System.out.println("from Salina to Manhattan is "+testMap.getRoute(" Salina"," Manhattan","distance"));
//		System.out.println("from Salina to Topeka is "+testMap.getRoute(" Salina"," Topeka","distance"));
//		System.out.println("from Salina to Lawrence is "+testMap.getRoute(" Salina"," Lawrence","distance"));
//		System.out.println("from Manhattan to Salina is "+testMap.getRoute(" Manhattan"," Salina","distance"));
//		System.out.println("from Manhattan to topeka is "+testMap.getRoute(" Manhattan"," Topeka","distance"));
	}
	
	@Test 
	public void testTwoHop(){
		testMap.getRoute(" Salina"," Kansas City","distance");
		System.out.println("from Salina to Kansas City is "+testMap.returnRoute());
//		System.out.println("from Salina to Shawnee is "+testMap.getRoute(" Salina"," Shawnee","distance"));
//		System.out.println("from Salina to Lawrence is "+testMap.getRoute(" Salina"," Lawrence","distance"));
//		System.out.println("from Topeka to Lenexa is "+testMap.getRoute(" Topeka"," Lenexa","distance"));
//		System.out.println("from Topeka to Shawnee is "+testMap.getRoute(" Topeka"," Shawnee","distance"));
	}
	
	@Test
	public void testAll(){
		
	}

}
