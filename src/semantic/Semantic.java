package semantic;

import lexer.Lexer;
import lexer.Pack;
import parser.Parser;

import java.util.ArrayList;
import java.util.List;

public class Semantic {
    String filePath = System.getProperty("user.dir") + "/src/parser/" + "test.txt";
    String filePath2 = System.getProperty("user.dir")+"/src/parser/"+"dragonBookGrammar.txt";

    //三地址指令
    List<String> instructs = new ArrayList<>();
    //四元式
    List<String> quadruple = new ArrayList<>();


    public void semantic(String grammarFile, String testFile){


        Parser parser = new Parser(grammarFile);
        String text = parser.readFile(testFile);
        parser.predict(text);
        parser.productList.forEach(x -> System.out.println(x));

        Lexer lexer = new Lexer(text, null, null);
        lexer.scan(1);
        List<Pack> packList = new ArrayList<>();
        packList.addAll(lexer.acceptTokens);
        packList.add(new Pack("$", "-", -1, null, "$"));

        for(String product: parser.productList){




        }


    }
}
