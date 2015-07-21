package lib;
import java.lang.*;

public class myStringBuffer {
	private StringBuffer buffer;
	
	public myStringBuffer() {
		this.buffer = new StringBuffer();
	}

	public myStringBuffer(StringBuffer buffer) {
		this.buffer = buffer;
	}

	public StringBuffer getBuffer() {
		return buffer;
	}

	public void setBuffer(StringBuffer buffer) {
		this.buffer = buffer;
	}

	public StringBuffer append(StringBuffer buffer) {
		this.buffer.append(buffer);
		return this.buffer;
	}

	public static StringBuffer newStringBuffer(String src) {
		StringBuffer local_buffer = new StringBuffer();
		local_buffer.append(src);
		return local_buffer;
	}

	public myStringBuffer append(myStringBuffer buf) {
		this.buffer.append(buf.getBuffer());
		return this;
	}

	public static StringBuffer append(StringBuffer toBuffer, StringBuffer[] buf) {
		for (StringBuffer el : buf) {
			toBuffer.append(el);
		}
		return toBuffer;
	}

	public StringBuffer append(StringBuffer[] buf) {
		for (StringBuffer el : buf) {
			buffer.append(el);
		}
		return buffer;
	}
}
