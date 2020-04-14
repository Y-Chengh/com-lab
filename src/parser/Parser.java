package parser;


import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import lexer.Lexer;
import lexer.Pack;

import java.io.*;
import java.util.*;

import parser.GetSelectSet;

public class Parser {

    // 用于将种别码映射为文法的输入
    private static HashMap<String, String> typeToInput = new HashMap<String, String>() {
        {
            put("identifier", "id");
            put("semicolon", ";");
            put("comma", ",");
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
            put("equal", "=");
            put("relational_equal", "==");
        }
    };
    public String startSymbol = "Start";
    public List<String> productList = new ArrayList<>();

    public void predict(String filePath) {
        GetSelectSet.initial();
        Map<String, Map<String, String>> LL1Map = convertLL1ArrayToMap(GetSelectSet.LL1);

        String text = readFile(filePath);
        Lexer lexer = new Lexer(text, null, null);
        lexer.scan(1);

        List<Pack> packList = new ArrayList<>();
        packList.addAll(lexer.acceptTokens);
        packList.add(new Pack("$", null, -1, null));


        Stack<String> stack = new Stack<>();
        stack.push("$");
        stack.push(startSymbol);

        int index = 0;

        while (true) {
            if (stack.empty()) {
                break;
            }

            String topSymbol = stack.peek();
            Pack pack = packList.get(index);

            if (GetSelectSet.isTerminal(topSymbol)) {
                assert topSymbol.equals(pack.type);
                index++;
                String pop = stack.pop();
                System.out.println("recognize: " + pack.type + " " + pack.value + " " + typeToInput.getOrDefault(pack.type, pack.type));
                System.out.println("pop: " + pop);
                System.out.println(stack);
                continue;
            }

            String inputToken = typeToInput.getOrDefault(pack.type, pack.type);
            System.out.println("stack: " + stack);
            System.out.println("inputToken " + inputToken);
            System.out.println("LL1Map.get(topSymbol): " + LL1Map.get(topSymbol));
            System.out.println("LL1Map.get(topSymbol).get(inputToken).trim(): " + LL1Map.get(topSymbol).get(inputToken).trim());
            String product = LL1Map.get(topSymbol).get(inputToken).trim();
            productList.add(product);
            System.out.println("product: "+ product);
            String rightSideOfProduct = product.substring(product.indexOf("→") + 1).trim();
            if (rightSideOfProduct.contains("ε")) {
                stack.pop();
                System.out.println("pop: " + topSymbol);
                continue;
            } else {
                String[] strings = rightSideOfProduct.split(" ");
//                System.out.println("productRightSide: " + Arrays.toString(strings));
                stack.pop();
                for (int i = strings.length - 1; i >= 0; i--) {
                    stack.push(strings[i]);
                }
            }
        }
    }


    private Map<String, Map<String, String>> convertLL1ArrayToMap(String[][] LL1) {
        Map<String, Map<String, String>> map = new HashMap<>();
        // map nonTerminalSymbol to a map which maps input to product
        for (int i = 1; i < LL1.length; i++) {
            map.put(LL1[i][0], new HashMap<>());
        }
//        System.out.println(LL1.length);
//        System.out.println(LL1[0].length);

        for (int i = 1; i < LL1.length; i++) {
            Map<String, String> nonTerminalSymbolToProduct = map.get(LL1[i][0]);
            for (int j = 1; j < LL1[0].length; j++) {
                if (LL1[i][j] != null) {
//                    System.out.println(i + "  " + j);

                    nonTerminalSymbolToProduct.put(LL1[0][j], LL1[i][j]);
                }
            }
        }
        return map;
    }

    private String readFile(String filePath) {
        StringBuilder sb = new StringBuilder();
        try {
            InputStream is = new FileInputStream(filePath);
            Reader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
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
//        GetSelectSet.initial();

        Parser parser = new Parser();
        parser.predict(filePath);
        parser.productList.forEach(x -> System.out.println(x));
//
//        GetSelectSet.printSelectSet();


    }
}
