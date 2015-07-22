package lib;

import java.lang.reflect.Field;
import java.lang.reflect.Array;
import java.util.*;

public class XMLencoder {
	private static ArrayList primitiveClasses = new ArrayList();

	{
		Class[] types = { Integer.class, Double.class, Float.class, Long.class, Byte.class, String.class, Boolean.class,
				Character.class, Void.class };
		for (Class el : types) {
			primitiveClasses.add(el);
		}
	}

	public XMLencoder() {

	}

	public static xmlTag encode(xmlTag toXMLobject, Object obj, int spaces, ArrayList<Object> beenObjects)
			throws IllegalArgumentException, IllegalAccessException {

		if (obj == null) {
			return null;
		}
		;
		Class<?> the_class = obj.getClass();
		if (the_class.isPrimitive() || "java.lang.Boolean".equals(the_class.getName())
				|| primitiveClasses.contains(obj.getClass())) {
			// this is primitive type
			if (obj.toString() != "") {
				xmlTag tag = new xmlTag(spaces, "value");
				tag.setValue(obj);
				toXMLobject.addChild(tag);
			}
		} else {
			if (beenObjects.contains(obj)) {
				// circling!
				xmlTag tagCircle = new xmlTag(spaces + 4, "circling1");
				toXMLobject.addChild(tagCircle);
				beenObjects.add(obj);
			} else {
				if (the_class.isArray()) {
					// it is an Array!
					Class<?> elementType = the_class.getComponentType();
					xmlTag arrayTag = new xmlTag(spaces, "Array");
					arrayTag.addAttr("of", elementType.getName());
					int len = Array.getLength(obj);
					Object[] tmpElements = new Object[len];
//					System.out.println("Number of elements: " + len);
					for (int index = 0; index < len; index++) {
						tmpElements[index] = Array.get(obj, index);
//						System.out.println("element ¹" + index + ": " + tmpElements[index]);
					}
					for (int index = 0; index < len; index++) {
						xmlTag elementTag = new xmlTag(spaces + 2, "Element");
						elementTag.addAttr("number", ""+index);
						encode(elementTag, tmpElements[index], spaces + 4, beenObjects);
						arrayTag.addChild(elementTag);
					}
					toXMLobject.addChild(arrayTag);
				} else {
//					System.out.println("encodeElement: " + obj + " - primitive? " + the_class.isPrimitive() + "("
//							+ the_class.getName() + ")");
					xmlTag tagClass = new xmlTag(spaces, "class");
					tagClass.addAttr("name", obj.getClass().getName());
					xmlTag tagFields = new xmlTag(spaces + 2, "fields");
					tagClass.addChild(tagFields);
					for (Field the_field : obj.getClass().getDeclaredFields()) {
						the_field.setAccessible(true);
						StringBuffer field_name = myStringBuffer.newStringBuffer(the_field.getName());
						try {
							xmlTag tagField = new xmlTag(spaces + 4, "field");
							HashMap<String, StringBuffer> attrs = new HashMap<String, StringBuffer>();
							attrs.put("name", field_name);
							tagField.setAttrs(attrs);
							Object val = the_field.get(obj);
							if (val == obj) {
								// circling!
								xmlTag tagCircle = new xmlTag(spaces + 4, "circling2");
								tagField.addChild(tagCircle);
							} else if (beenObjects.contains(val)) {
								// circling!
								xmlTag tagCircle = new xmlTag(spaces + 4, "circling3");
								tagField.addChild(tagCircle);
							} else if (val != null) {
								encode(tagField, val, spaces + 6, beenObjects);
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
			}
		}
		;
		return toXMLobject;

	}

}
