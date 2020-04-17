package parser;

public class packer {
    public String value = null;
    public int length = 0;
    public packer(String value, int length) {
        this.value = value;
        this.length = length;
    }
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
    
    
}
