package semantic;

import lexer.Lexer;
import lexer.Pack;
import parser.Parser;

import java.util.ArrayList;
import java.util.List;

public class Control implements Statements{
    // 李国庆 分支 循环语句
    public static List<String> acceptList = new ArrayList<String>();
    static {
        acceptList.add("stmt_if(bool)stmt");
        acceptList.add("bool");
        acceptList.add("rel_expr");
        acceptList.add("join");
        acceptList.add("bool'");
        acceptList.add("equality'");
        acceptList.add("jion'");
        acceptList.add("stmt");
    }



    @Override
    public void analysis(List<Pack> packList, List<String> productList, List<String> instructs, List<String> quadruple) {
        String productLeft = productList.get(0).split("→")[0].trim();  // 获取第一条产生式的左部.
        if(!acceptList.contains(productLeft))return;
        String productRight = productList.get(0).split("→")[1];

        if (productLeft.equals("")){

        }else if (productLeft.equals("")){

        }else if (productLeft.equals("")){

        }else if (productLeft.equals("")){

        }else if (productLeft.equals("")){

        }else if (productLeft.equals("")){

        }else if (productLeft.equals("")){

        }


    }

    public static void main(String[] args) {
        String filePath = System.getProperty("user.dir") + "/src/parser/" + "test.txt";
        String filePath2 = System.getProperty("user.dir")+"/src/parser/"+"dragonBookGrammar.txt";
        Parser parser = new Parser(filePath2);
        String text = parser.readFile(filePath);

        parser.predict(text);

        Lexer lexer = new Lexer(text, null, null);
        lexer.scan(1);
        List<Pack> packList = new ArrayList<>();
        packList.addAll(lexer.acceptTokens);
        packList.add(new Pack("$", "-", -1, null, "$"));
        System.out.println("--------------------------------------");
        parser.productList.forEach(x -> System.out.println(x));
        System.out.println("------------");
        packList.forEach(x-> System.out.println(x.orginString));
        System.out.println(packList.size());
        System.out.println(parser.productList.size());


    }
}
