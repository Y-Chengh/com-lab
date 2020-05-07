package semantic;

public class AtomicAction {
    //表示该对象为一个文法符号还是一个语义动作
    //为0时表示文法符号，为1时表示语义动作
    final private int type;
    public double inh;
    public double val;

    public AtomicAction(int type){
        this.type = type;
    }


    public boolean isSymbol(){
        return type == 0;
    }

}
