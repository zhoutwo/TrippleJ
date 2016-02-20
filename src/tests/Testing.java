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
		testMap.getRoute(" Salina"," Salina","distance");
		System.out.println("from Salina to Salina is "+testMap.returnRoute());
		testMap.getRoute(" Topeka"," Topeka","distance");
		System.out.println("from Topeka to Topeka is "+testMap.returnRoute());
	}

	@Test
	public void testOneHop() {
//		testMap.getRoute(" Salina"," Manhattan","distance");
//		System.out.println("from Salina to Manhattan is "+testMap.returnRoute());
		testMap.getRoute(" Salina"," Topeka","distance");
		System.out.println("from Salina to Topeka is "+testMap.returnRoute());
//		System.out.println("from Salina to Lawrence is "+testMap.getRoute(" Salina"," Lawrence","distance"));
//		System.out.println("from Manhattan to Salina is "+testMap.getRoute(" Manhattan"," Salina","distance"));
//		System.out.println("from Manhattan to topeka is "+testMap.getRoute(" Manhattan"," Topeka","distance"));
	}
	
	@Test 
	public void testTwoHop(){
		testMap.getRoute(" Salina"," Kansas City","time");
		System.out.println("from Salina to Kansas City is "+testMap.returnRoute());
//		testMap = new Map();
		testMap.getRoute(" Salina"," Shawnee","time");
		System.out.println("from Salina to Shawnee is "+testMap.returnRoute());
//		testMap = new Map();
		testMap.getRoute(" Salina"," Lawrence","time");
		System.out.println("from Salina to Lawrence is "+testMap.returnRoute());
//		testMap = new Map();
		testMap.getRoute(" Topeka"," Lenexa","time");
		System.out.println("from Topeka to Lenexa is "+testMap.returnRoute());
//		testMap = new Map();
		testMap.getRoute(" Topeka"," Shawnee","time");
		System.out.println("from Topeka to Shawnee is "+testMap.returnRoute());
	}
	
	@Test
	public void testAll(){
		System.out.println(testMap.getPopCityList());
	}

}
