package lib;

import java.lang.reflect.Field;
import java.util.*;

public class XMLencoder {
	private static ArrayList primitiveClasses = new ArrayList();

	{
		Class[] types = { Integer.class, Double.class, Float.class, Long.class,
				Byte.class, String.class };
		for (Class el : types) {
			primitiveClasses.add(el);
		}
	}

	public XMLencoder() {

	}

	public static xmlTag encode(xmlTag toXMLobject, Object obj,
			int spaces) throws IllegalArgumentException,
			IllegalAccessException {

		if (obj == null) {
			toXMLobject.setValue(obj.toString());
			return null;
		}
		;
		if (obj.getClass().isPrimitive()
				|| primitiveClasses.contains(obj.getClass())) {
			// this is primitive type
			if (obj.toString() != "") {
				xmlTag tag = new xmlTag(spaces, "Value");
				tag.setValue(obj.toString());
				toXMLobject.addChild(tag);
			}
		} else {
			StringBuffer className = new StringBuffer(obj.getClass().toString());
			xmlTag tagClass = new xmlTag(spaces, className);
			xmlTag tagFields = new xmlTag(spaces+2, "fields");
			tagClass.addChild(tagFields);
			for (Field the_field : obj.getClass().getDeclaredFields()) {
				the_field.setAccessible(true);
				StringBuffer field_name = myStringBuffer.newStringBuffer(the_field.getName());
				try {
					xmlTag tagField = new xmlTag(spaces+4, "field");
					HashMap<String, StringBuffer> attrs = new HashMap<String, StringBuffer>();
					attrs.put("name", field_name);
					tagField.setAttrs(attrs);
					Object val = the_field.get(obj);
					if (val != null) {
						encode(tagField, val, spaces+6);
					} else {
						tagField.setValue("");
					}
					;
					tagFields.addChild(tagField);
				} catch (IllegalAccessException ex) {
					System.out.println(ex);
				}
			}
			toXMLobject.addChild(tagClass);
		}
		;
		return toXMLobject;

	}

}
