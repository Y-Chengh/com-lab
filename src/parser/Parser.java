package parser;


import lexer.Lexer;

import java.io.*;
import java.util.HashMap;
import java.util.Set;

public class Parser {

    // 用于将种别码映射为文法的输入
    private static HashMap<String, String> typeToInput = new HashMap<String, String>(){
        {
            put("identifier", "id");
            put("semicolon", ";");
            put("right_middle_parentheses", "]");
            put("left_middle_parentheses", "[");
            put("right_small_parentheses", ")");
            put("left_small_parentheses", "(");
            put("right_big_parentheses", "}");
            put("left_big_parentheses", "{");
            put("arithmetic_plus", "+");
            put("arithmetic_minus", "-");
            put("arithmetic_multiply", "*");
            put("arithmetic_divide", "/");
            put("integer", "num");
            put("num", "number");
        }
    };


    public void predict(String filePath){
        GetSelectSet.initial();
        HashMap<String, Set<String>> select = GetSelectSet.selectSet;

        String text = readFile(filePath);
        Lexer lexer = new Lexer(text, null, null);
        lexer.scan(1);


    }



    private String readFile(String filePath) {
        StringBuilder sb = new StringBuilder();
        try{
            InputStream is = new FileInputStream(filePath);
            Reader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = null;

            while((line = bufferedReader.readLine()) != null){
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(sb);
        return "" + sb;
    }

    public static void main(String[] args) {
        String filePath = System.getProperty("user.dir") + "/src/parser/" + "test.txt";
        GetSelectSet.initial();

        System.out.println(GetSelectSet.startSymbol);

//        Parser parser = new Parser();
//        parser.predict(filePath);
//
        GetSelectSet.printSelectSet();
        System.out.println(GetSelectSet.startSymbol);
    }
}
