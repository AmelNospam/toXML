package lib;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class xmlTag {
	private static StringBuffer _left = new StringBuffer("<");
	private static StringBuffer _right = new StringBuffer(">");
	private static StringBuffer _slash = new StringBuffer("/");
	private static StringBuffer _value = new StringBuffer("Value");
	private static StringBuffer _space = new StringBuffer(" ");
	private static StringBuffer _field = new StringBuffer("field");
	private static StringBuffer _name = new StringBuffer("name");
	private static StringBuffer _bullet = new StringBuffer("\"");
	// private static StringBuffer _cr = new StringBuffer("\n");
	private static StringBuffer _eq = new StringBuffer("=");

	private StringBuffer spaces;
	private StringBuffer tag;
	private HashMap<String, StringBuffer> attrs = null;
	private StringBuffer value = null;
	private List<xmlTag> children = new LinkedList<xmlTag>();

	public xmlTag(StringBuffer spaces, StringBuffer tag) {
		this.spaces = spaces;
		this.tag = tag;
	};

	public xmlTag(StringBuffer spaces, String tag) {
		this.spaces = spaces;
		this.tag = new StringBuffer(tag);
	};

	public xmlTag(String spaces, String tag) {
		this.spaces = new StringBuffer(spaces);
		this.tag = new StringBuffer(tag);
	};

	public List<xmlTag> getChildren() {
		return children;
	}

	public void setChildren(List<xmlTag> children) {
		this.children = children;
	}

	public void addChild(xmlTag child) {
		child.addSpaces("  ");
		this.children.add(child);
	}

	public StringBuffer getTag() {
		return tag;
	}

	public void setTag(StringBuffer tag) {
		this.tag = tag;
	}

	public StringBuffer getValue() {
		return value;
	}

	public void setValue(StringBuffer value) {
		this.value = value;
	}

	public void setValue(String value) {
		this.value = new StringBuffer(value);
	}

	public StringBuffer getSpaces() {
		return spaces;
	}

	public void setSpaces(StringBuffer spaces) {
		this.spaces = new StringBuffer();
		this.spaces.append(spaces);
	}

	public void setSpaces(String spaces) {
		this.spaces = new StringBuffer();
		this.spaces.append(spaces);
	}

	public void addSpaces(StringBuffer added_spaces) {
		this.spaces.append(added_spaces);
	}

	public void addSpaces(String added_spaces) {
		this.spaces.append(added_spaces);
	}

	public HashMap<String, StringBuffer> getAttrs() {
		return attrs;
	}

	public void setAttrs(HashMap<String, StringBuffer> attrs) {
		this.attrs = attrs;
	}

	private StringBuffer attrsToStringBuffer() {
		myStringBuffer res = new myStringBuffer();
		if(attrs == null){return res.getBuffer();};
		res.append(_space);
		for (Map.Entry<String, StringBuffer> entry : attrs.entrySet()) {
			String key = entry.getKey();
			StringBuffer value = entry.getValue();
			res.append(new StringBuffer[] { new StringBuffer(key), _eq, _bullet, value, _bullet });
		}
		;
		return res.getBuffer();
	}

	private String attrsToString() {
		return attrsToStringBuffer().toString();
	}

	public StringBuffer toStringBuffer() {
		myStringBuffer res = new myStringBuffer();
		if (value == null && children == null) {
			res.append(new StringBuffer[] { spaces, _left, tag, attrsToStringBuffer(), _space, _slash, _right });
		} else if (children == null) {
			res.append(new StringBuffer[] { spaces, _left, tag, attrsToStringBuffer(), _right, spaces, value,
					spaces, _left, _slash, tag, _right });
		} else {
			res.append(new StringBuffer[] { spaces, _left, tag, attrsToStringBuffer(), _right, spaces});
			for(xmlTag child: children){
				res.append(child.toStringBuffer());
			}
			res.append(new StringBuffer[] {spaces, _left, _slash, tag, _right });
		}
		return res.getBuffer();
	}

	public String toString() {
		return toStringBuffer().toString();
	}

}
