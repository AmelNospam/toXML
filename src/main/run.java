package main;

import java.util.HashMap;

import testClasses.testClass;
import lib.XMLencoder;
import lib.xmlTag;

public class run {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		String arg1 = "just a string";
		Integer arg2 = 10;
		testClass arg3 = new testClass();
		XMLencoder enc = new XMLencoder();
		xmlTag root = new xmlTag(0, "xml");
		
//		public xmlTag(StringBuffer spaces, StringBuffer tag, HashMap<String, StringBuffer> attrs, StringBuffer value) {
//		System.out.println(XMLencoder.encode(arg1, spaces));
//		System.out.println(XMLencoder.encode(arg2, spaces));
//		System.out.println(XMLencoder.encode(arg3, spaces));
		XMLencoder.encode(root, arg3, 0);
		System.out.println(root.toString(false));
		System.out.println(root.toString(true));
	}

}
