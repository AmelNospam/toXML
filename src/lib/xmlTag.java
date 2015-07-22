package lib;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class xmlTag {
	private static StringBuffer _left = new StringBuffer("<");
	private static StringBuffer _right = new StringBuffer(">");
	private static StringBuffer _slash = new StringBuffer("/");
	private static StringBuffer _value = new StringBuffer("value");
	private static StringBuffer _space = new StringBuffer(" ");
	private static StringBuffer _bullet = new StringBuffer("\"");
	private static StringBuffer _cr = new StringBuffer("\n");
	private static StringBuffer _eq = new StringBuffer("=");
	private static StringBuffer _type = new StringBuffer("type");
	private static StringBuffer _empty = new StringBuffer("");

	private int spaces;
	private StringBuffer spacesString;
	private StringBuffer tag;
	private HashMap<String, StringBuffer> attrs = new HashMap<String, StringBuffer>();
	private Object value = null;
	private StringBuffer type = null;

	private List<xmlTag> children = new LinkedList<xmlTag>();

	private void renewSpacesAsString() {
		this.spacesString = new StringBuffer("\n");
		for (int index = 0; index < spaces; index++) {
			spacesString.append(" ");
		}
	}

	public xmlTag(int spaces, StringBuffer tag) {
		this.spaces = spaces;
		renewSpacesAsString();
		this.tag = tag;
	};

	public xmlTag(int spaces, String tag) {
		this.spaces = spaces;
		renewSpacesAsString();
		this.tag = new StringBuffer(tag);
	};

	public List<xmlTag> getChildren() {
		return children;
	}

	public void setChildren(List<xmlTag> children) {
		this.children = children;
	}

	public void addChild(xmlTag child) {
		child.addSpaces(2);
		this.children.add(child);
	}
	
	public StringBuffer getType() {
		return type;
	}

	public void setType(StringBuffer type) {
		this.type = type;
	}

	public StringBuffer getTag() {
		return tag;
	}

	public void setTag(StringBuffer tag) {
		this.tag = tag;
	}

	public Object getValue() {
		return value;
	}

	private StringBuffer getTypeFromClass(Class<?> forClass){
		String str = forClass.getName();
		int position = str.lastIndexOf('.');
		return new StringBuffer(str.substring(position+1));
	}

	public void setValue(Object value) {
		this.type = getTypeFromClass(value.getClass());
		addAttr("type", this.type);
		if (value.toString().trim() == "") {
			this.value = null;
		} else {
			this.value = value;
		}
	}

	public int getSpaces() {
		return spaces;
	}

	public StringBuffer getSpacesAsStringBuffer() {
		return this.spacesString;
	}

	public void setSpaces(int spaces) {
		this.spaces += spaces;
		renewSpacesAsString();
	}

	public void addSpaces(int added_spaces) {
		this.spaces += added_spaces;
		renewSpacesAsString();
	}

	public HashMap<String, StringBuffer> getAttrs() {
		return attrs;
	}

	public void setAttrs(HashMap<String, StringBuffer> attrs) {
		this.attrs = attrs;
	}

	public void addAttr(String key, StringBuffer val) {
		this.attrs.put(key, val);
	}

	public void addAttr(String key, String val) {
		this.attrs.put(key, new StringBuffer(val));
	}

	private StringBuffer attrsToStringBuffer() {
		myStringBuffer res = new myStringBuffer();
		if (attrs.isEmpty()) {
			return res.getBuffer();
		}
		;
		for (Map.Entry<String, StringBuffer> entry : attrs.entrySet()) {
			String key = entry.getKey();
			StringBuffer valueOfAttr = entry.getValue();
			res.append(new StringBuffer[] { _space, new StringBuffer(key), _eq,
					_bullet, valueOfAttr, _bullet });
		}
		;
		return res.getBuffer();
	}

	private static Boolean equals(StringBuffer first, StringBuffer second){
		if(first.length() != second.length()) {return false;};
		for(int index=0; index < first.length(); index++){
			if(first.charAt(index) != second.charAt(index)) {return false;};
		}
		return true;
	}
	private myStringBuffer printBlankTag(Boolean withSpaces){
		myStringBuffer res = new myStringBuffer();
		res.append(new StringBuffer[] { withSpaces?spacesString:_cr, _left, tag,
				attrsToStringBuffer(), _space, _slash, _right });
		return res;
	}
	
	private myStringBuffer printValue(StringBuffer bricks) {
		myStringBuffer res = new myStringBuffer();
		res.append(new StringBuffer[] {
				bricks,
				_left,
				tag,
				attrsToStringBuffer()});
		res.append(new StringBuffer[] { _right,
				myStringBuffer.newStringBuffer(bricks.toString() + "  "),
				new StringBuffer(value.toString()), spacesString, _left, _slash,
				tag, _right });
		return res;
	}
	
	private myStringBuffer printFullTag(Boolean withSpaces){
		StringBuffer bricks = new StringBuffer(withSpaces?spacesString:_empty);
		myStringBuffer res = new myStringBuffer();
		res.append(new StringBuffer[] { bricks, _left, tag,
				attrsToStringBuffer(), _right });
		for (xmlTag child : children) {
			res.append(child.toStringBuffer(withSpaces));
		}
		res.append(new StringBuffer[] { bricks, _left, _slash,
				tag, _right });
		return res;
	}
	
	public StringBuffer toStringBuffer(Boolean withSpaces) {
		StringBuffer bricks = new StringBuffer(withSpaces?spacesString:_empty);
		myStringBuffer res = new myStringBuffer();
		if (value == null && children.size() == 0) {
			res.append(printBlankTag(withSpaces));
		} else if (children.size() == 0) {
			res.append(printValue(bricks));
		} else {
			res.append(printFullTag(withSpaces));
		}
		return res.getBuffer();
	}

	public StringBuffer toStringBuffer() {
		return toStringBuffer(true);
	}

	public String toString(Boolean withSpaces) {
		return toStringBuffer(withSpaces).toString();
	}

	@Override
	public String toString() {
		return toStringBuffer(true).toString();
	}

}
