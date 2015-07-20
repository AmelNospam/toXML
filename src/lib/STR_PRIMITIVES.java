package lib;

public enum STR_PRIMITIVES {
	_left("<"), _right(">"), _slash("/"), _value("Value"), _space(" "), _field("field"), _name("name"), _bullet("\""), _cr("\n"), _eq("=");
	
	private StringBuffer val;
	
	private STR_PRIMITIVES(String template){
		this.val = new StringBuffer(template);
	}
	public StringBuffer get() {
		return val;
	}
}
