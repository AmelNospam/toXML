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
	private static StringBuffer _cr = new StringBuffer("\n");
	private static StringBuffer _eq = new StringBuffer("=");

	private int spaces;
	private StringBuffer spacesString;
	private StringBuffer tag;
	private HashMap<String, StringBuffer> attrs = null;
	private StringBuffer value = null;
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
		if (value.toString().trim() == "") {
			this.value = null;
		} else {
			this.value = value;
		}
	}

	public void setValue(String value) {
		if (value.trim() == "") {
			this.value = null;
		} else {
			this.value = new StringBuffer(value);
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

	private StringBuffer attrsToStringBuffer() {
		myStringBuffer res = new myStringBuffer();
		if (attrs == null) {
			return res.getBuffer();
		}
		;
		res.append(_space);
		for (Map.Entry<String, StringBuffer> entry : attrs.entrySet()) {
			String key = entry.getKey();
			StringBuffer value = entry.getValue();
			res.append(new StringBuffer[] { new StringBuffer(key), _eq,
					_bullet, value, _bullet });
		}
		;
		return res.getBuffer();
	}

	private String attrsToString() {
		return attrsToStringBuffer().toString();
	}

	public StringBuffer toStringBuffer(Boolean withSpaces) {
		// System.out.println("value="+value+", tag="+tag+", children="+children);
		myStringBuffer res = new myStringBuffer();
		if (withSpaces) {
			if (tag.equals(_value) && value == null) {
				;
			} else if (value == null && children.size() == 0) {
				res.append(new StringBuffer[] { spacesString, _left, tag,
						attrsToStringBuffer(), _space, _slash, _right });
			} else if (children.size() == 0) {
				// System.out.println("value="+value);
				res.append(new StringBuffer[] {
						spacesString,
						_left,
						tag,
						attrsToStringBuffer(),
						_right,
						myStringBuffer.newStringBuffer(spacesString.toString() + "  "),
						value, spacesString, _left, _slash,
						tag, _right });
			} else {
				res.append(new StringBuffer[] { spacesString, _left, tag,
						attrsToStringBuffer(), _right });
				for (xmlTag child : children) {
					res.append(child.toStringBuffer(withSpaces));
				}
				res.append(new StringBuffer[] { spacesString, _left, _slash,
						tag, _right });
			}
		} else {
			if (tag.equals(_value) && value == null) {
				;
			} else if (value == null && children.size() == 0) {
				res.append(new StringBuffer[] { _cr, _left, tag,
						attrsToStringBuffer(), _space, _slash, _right });
			} else if (children.size() == 0) {
				// System.out.println("value="+value);
				res.append(new StringBuffer[] {
						_left,
						tag,
						attrsToStringBuffer(),
						_right,
						value, _left, _slash,
						tag, _right });
			} else {
				res.append(new StringBuffer[] { _left, tag,
						attrsToStringBuffer(), _right });
				for (xmlTag child : children) {
					res.append(child.toStringBuffer(withSpaces));
				}
				res.append(new StringBuffer[] { _left, _slash, tag, _right });
			}
		}
		return res.getBuffer();
	}

	public String toString(Boolean withSpaces) {
		return toStringBuffer(withSpaces).toString();
	}

}
