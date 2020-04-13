package parser;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static parser.GetFirstSet.*;

public class GetFollowSet {
    public static HashMap<String,String> product = new HashMap(); // 产生式
    public static HashMap<String, Set<String>> firstSet = new HashMap<>(); // first集
    public static HashMap<String, Set<String>> followSet = new HashMap<>(); // follow集
    public static HashMap<String,Boolean> getFollowFlag = new HashMap<>();
    public static Set<String> lastWordSet = new HashSet<>();    // 产生式右部的最后一个非终结符需记录,稍后后处理.
    public static HashMap<String,String> cycleRefenrence = new HashMap(); // 循环引用
    public static String startSymbol;

    // 求一个非终结符的follow集合.
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

    // 求所有非终结符的follow集.
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
                if(cycleRefenrence.keySet().contains(productLeft)
                        &&cycleRefenrence.get(productLeft).equals(lastWord)){
                    followSet.get(lastWord).addAll(followSet.get(productLeft));
                    continue;
                }
                if(getFollowFlag.get(productLeft)){
                    cycleRefenrence.put(lastWord,productLeft);
                    followSet.get(lastWord).addAll(followSet.get(productLeft));
                }else {
                    cycleRefenrence.put(lastWord,productLeft);
                    getFollowlast(productLeft);
                    followSet.get(lastWord).addAll(followSet.get(productLeft));
                }
            }
        }
        getFollowFlag.put(s,true);
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

    // 启动程序,总程序.
    public static void getFollowSet(String filePath){
        readProduct(filePath);
        getAllFisrtSet();
        product = GetFirstSet.product;
        firstSet = GetFirstSet.firstSet;
        lastWordSet = GetFirstSet.lastWordSet;
        followSet = GetFirstSet.followSet;
        getFollowFlag = GetFirstSet.getFollowFlag;
        getAllFollowSet();
    }

    public static void main(String[] args) {
        String filePath = System.getProperty("user.dir")+"/src/parser/"+"Product.txt";
        getFollowSet(filePath);
        printFollowSet();
    }


}
