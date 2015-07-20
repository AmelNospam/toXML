package lib;

import java.lang.reflect.Field;
import java.util.*;

public class XMLencoder {
	private static ArrayList primitiveClasses = new ArrayList();

	{
		Class[] types = { Integer.class, Double.class, Float.class, Long.class, Byte.class, String.class };
		for (Class el : types) {
			primitiveClasses.add(el);
		}
	}

	public XMLencoder() {

	}

	public static xmlTag encode(xmlTag toXMLobject, Object obj, StringBuffer spaces)
			throws IllegalArgumentException, IllegalAccessException {

		if (obj == null) {
			return null;
		}
		;
		if (obj.getClass().isPrimitive() || primitiveClasses.contains(obj.getClass())) {
			// this is primitive type
			xmlTag tag = new xmlTag(spaces, "Value");
			tag.setValue(obj.toString());
			toXMLobject.addChild(tag);
		} else {
			StringBuffer className = new StringBuffer(obj.getClass().toString());
			xmlTag tagClass = new xmlTag(spaces, className);
			xmlTag tagFields = new xmlTag(spaces, "fields");
			tagClass.addChild(tagFields);
			for (Field the_field : obj.getClass().getDeclaredFields()) {
				the_field.setAccessible(true);
				StringBuffer field_name = new StringBuffer(the_field.getName());
				StringBuffer _spacesChild = new StringBuffer(spaces);
				_spacesChild.append("  ");
				try {
					xmlTag tagField = new xmlTag(spaces, "field");
					HashMap<String, StringBuffer> attrs = new HashMap<String, StringBuffer>();
					attrs.put("name", field_name);
					tagField.setAttrs(attrs);
					tagFields.addChild(tagField);
					Object val = the_field.get(obj);
					if (val != null) {
						encode(toXMLobject, val, _spacesChild);
					}
					;
				} catch (IllegalAccessException ex) {
					System.out.println(ex);
				}
			}
		}
		;
		return toXMLobject;

	}

}
