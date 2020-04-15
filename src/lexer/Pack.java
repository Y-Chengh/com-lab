package lexer;

public class Pack {
    public String type = null;
    public String value = null;
    public int lineNumber = -1;
    int length = -1;
    String token = null;
    String parse;
    public Pack(String type, String value, int length, String parse) {
        this.type = type;
        this.value = value;
        this.length = length;
        this.parse = parse;
    }

}
