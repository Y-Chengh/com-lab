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
            for (int i = 0; i < productRight.length(); i++) {
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

    public static void getOneFollowSet( String productRights){
        String[] rights = productRights.split("\\|");
        for(int k=0;k<rights.length;k++) {
            String productRight = rights[k].trim();
            String[] words = productRight.split("\\s+");
            for(int i=1;i<=words.length;i++){
                String lastWord = words[words.length-i];
                if(!isTerminal(lastWord)){ // 若最后一个词是非终结符, 加入lastWordSet以后用.
                    lastWordSet.add(lastWord);
                    getFollowFlag.put(lastWord,false);
                    followSet.get(lastWord).add("$"); // 如果A是某个句型的的最右符号，则将结束符“$”添加到FOLLOW(A)中
                    if(!firstSet.get(lastWord).contains("ε"))break;
                }else {
                    break;
                }

            }

            for (int i = 0; i <words.length-1; i++) {
                int j = i + 1;
                String iString = words[i];
                String jString = words[j];
                if(isTerminal(iString)){
                    continue;
                }
                while (j<words.length){
                    jString = words[j];
                    if(isTerminal(jString)){//?
                        followSet.get(iString).add(jString);
                        break;
                    }else {
                        followSet.get(iString).addAll(firstSet.get(jString));
                        if(!firstSet.get(jString).contains("ε"))break;
                        j++;
                    }
                }
            }
        }
    }

    public static void getAllFollowSet(){
        for(String productRight:product.values()){
            getOneFollowSet(productRight);
        }
        for(String s:lastWordSet){
            getFollowlast(s);
        }
        for(Set<String> set:followSet.values()){
            set.remove("ε"); // 去掉空集
        }

    }

    // 若A ==> .*B, 那么A的follow集需要并入B的follow集.
    public static void getFollowlast(String s){
        if(getFollowFlag.get(s))return;

        for(Map.Entry<String,String> entry : product.entrySet()){
            String productLeft = entry.getKey();
            String productRights = entry.getValue();
            String[] rights = productRights.split("\\|");
         s1:   for(int k=0;k<rights.length;k++) {
                String productRight = rights[k].trim();
                String[] words = productRight.split("\\s+");
                String lastWord = words[words.length-1];
           s2:   for(int i=1;i<=words.length;i++){
                     lastWord = words[words.length-i];
                    if(!isTerminal(lastWord)){ // 若最后一个词是非终结符, 加入lastWordSet以后用.
                        if(lastWord.equals(s))break s2;
                        // 如果A是某个句型的的最右符号，则将结束符“$”添加到FOLLOW(A)中
                        if(!firstSet.get(lastWord).contains("ε"))continue s1;
                    }else {
                        continue s1;
                    }
                }

                if(!lastWord.equals(s))continue;
                if(productLeft.equals(s))continue;
                if(getFollowFlag.get(productLeft)){
                    followSet.get(lastWord).addAll(followSet.get(productLeft));
                }else {

                    getFollowlast(productLeft);
                    followSet.get(lastWord).addAll(followSet.get(productLeft));
                }
            }
        }
        getFollowFlag.put(s,true);
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

    public static void printFollowSet(){
        System.out.println("FollowSet of Product:");
        for(Map.Entry<String,Set<String>> entry:followSet.entrySet()){
            String right = "";
            for(String s:entry.getValue()){
                right = right+ " "+s;
            }
            System.out.println(entry.getKey().toString()+": {"+right +" }");
        }
    }

    public static void printSelectSet(){

    }


    public static void main(String[] args) {
        String filePath = System.getProperty("user.dir")+"/src/parser/"+"Product.txt";
        readProduct(filePath);
        getAllFisrtSet();
        printFisrtSet();


    }


}
