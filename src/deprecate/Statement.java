package semantic;

import lexer.Pack;

import java.util.List;
import java.util.Stack;

public class Statement implements Statements{
    @Override
    public void analysis(List<Pack> packList, List<String> productList, List<String> instructs, List<String> quadruple) {
        Stack<AtomicAction> stack = new Stack<>();
//        stack.push(new )
        String product = productList.get(0);
        switch (product){
//            case ""
        }
    }


}
