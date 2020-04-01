package lexer;

public class State {
    private boolean accept;
    private String type;
    private String value;
    public int state;

    public State(int state){
        this.state = state;
        this.accept = false;
        this.type = null;
        this.value = null;
    }

    public State(int state, boolean accept, String type, String value){
        assert accept;
        this.state = state;
        this.accept = true;
        this.type = type;
        this.value = value;
    }

    public boolean getAccept() {
        return accept;
    }

    public String generateType(String string) {
        assert accept;
        return type;
    }

    public String generateValue(String string) {
        assert accept;
        return value;
    }
}
