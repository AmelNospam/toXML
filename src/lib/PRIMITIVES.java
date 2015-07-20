package lib;

public enum PRIMITIVES {
	INT(Integer.class),
	DOUBLE(Double.class),
	FLOAT(Float.class),
	LONG(Long.class),
	BYTE(Byte.class),
	STRING(String.class);

	private Class class1;

	PRIMITIVES(Class class1){
		this.class1 = class1;
	}
}
