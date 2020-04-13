package parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GetSelectSet {
    public static HashMap<String,String> product = new HashMap(); // 产生式
    public static HashMap<String, Set<String>> firstSet = new HashMap<>(); // first集
    public static HashMap<String, Set<String>> followSet = new HashMap<>(); // follow集
    public static String startSymbol;


    public static void initial(){
        String filePath = System.getProperty("user.dir")+"/src/parser/"+"Product.txt";
        GetFollowSet.getFollowSet(filePath);
        product = GetFollowSet.product;
        firstSet = GetFollowSet.firstSet;
        followSet = GetFollowSet.followSet;
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

    public static void main(String[] args) {
        initial();
        printFisrtSet();
        printFollowSet();
    }
}
