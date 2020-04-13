package parser;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GetFirstSet {
    public static HashMap<String,String> product = new HashMap(); // 产生式
    public static  HashMap<String,Boolean> getFisrtFlag = new HashMap<>(); // 判断一个非终结符的Fisrt集是否已经计算出
    public static HashMap<String, Set<String>> firstSet = new HashMap<>(); // first集
    public static HashMap<String, Set<String>> followSet = new HashMap<>(); // follow集
    public static HashMap<String,Boolean> getFollowFlag = new HashMap<>();
    public static Set<String> lastWordSet = new HashSet<>();
    public static String startSymbol;

    // 判断一个字符是否为非终结符.
    public static Boolean isTerminal(String s){
        return !product.keySet().contains(s);
    }

    // 读取产生式, 并将其存入product中.
    public static void readProduct(String filePath)  {
        File file = new File(filePath);
        try{
            InputStream is = new FileInputStream(file);
            Reader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = null;
            int count = 0;
            while((line = bufferedReader.readLine()) != null){
                count++;
                String productLeft = line.split("→")[0].trim();
                String productRight = line.split("→")[1].trim();
                product.put(productLeft,productRight);

                getFisrtFlag.put(productLeft,false);
                Set<String> newSet = new HashSet<>();
                firstSet.put(productLeft,newSet);

                Set<String> newSet1 = new HashSet<>();
                if(count==1){
                    newSet1.add("$");
                    startSymbol = productLeft;
                }
                followSet.put(productLeft,newSet1);
                getFollowFlag.put(productLeft,true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 处理一条产生式, 并生成产生式左部的fisrt集合.
    public static void getOneFisrtSet(String productLeft,String productRights){
        String[] rights = productRights.split("\\|");
        for(int k=0;k<rights.length;k++) {
            String productRight = rights[k].trim();
            String[] words = productRight.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                String nextString = words[i];
                if (isTerminal(nextString)) { // 终结符直接添加
                    firstSet.get(productLeft).add(nextString);
                    Boolean a = true;
                    getFisrtFlag.put(productLeft, true);
                    break;
                }
                if (!product.keySet().contains(nextString)) { //判断当前非终结符是否包含在产生式左边的集合中.
                    System.out.println("Wrong Products !");
                    System.exit(0);
                }

                if (getFisrtFlag.get(nextString)) {
                    firstSet.get(productLeft).addAll(firstSet.get(nextString));
                } else {
                    getOneFisrtSet(nextString, product.get(nextString));
                    firstSet.get(productLeft).addAll(firstSet.get(nextString));
                }

                if (!firstSet.get(nextString).contains("ε")) {
                    getFisrtFlag.put(productLeft, true);
                    break;
                }

            }
        }
    }


    // 处理所有产生式
    public static void getAllFisrtSet(){
        for(Map.Entry<String,String> entry : product.entrySet()){
            if(!getFisrtFlag.get(entry.getKey())){
                getOneFisrtSet(entry.getKey(),entry.getValue());
            }
        }
    }


    public static void printFisrtSet(){
        System.out.println("FirstSet of Product:");
        for(Map.Entry<String,Set<String>> entry:firstSet.entrySet()){
            String right = "";
            for(String s:entry.getValue()){
                right = right+ " "+s;
            }
            System.out.println(entry.getKey().toString()+": {"+right +" }");
        }
    }

    public static void main(String[] args) {
        String filePath = System.getProperty("user.dir")+"/src/parser/"+"Product.txt";
        readProduct(filePath);
        getAllFisrtSet();
        printFisrtSet();
    }


}
