package parser;


import java.util.HashMap;
import java.util.Set;

public class PredictiveAnalysis {

    public void predict(){
        GetSelectSet.initial();
        HashMap<String, Set<String>> select = GetSelectSet.selectSet;

    }

    public static void main(String[] args) {
        GetSelectSet.initial();
        GetSelectSet.printSelectSet();
    }
}
