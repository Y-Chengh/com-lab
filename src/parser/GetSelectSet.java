package parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GetSelectSet {
    public static HashMap<String,String> product = new HashMap(); // 产生式
    public static HashMap<String, Set<String>> firstSet = new HashMap<>(); // first集
    public static HashMap<String, Set<String>> followSet = new HashMap<>(); // follow集
    public static HashMap<String, Set<String>> selectSet = new HashMap<>(); // select集
    public static String startSymbol;
    public static  HashMap<String,Boolean> getSelectFlag = new HashMap<>(); // 判断一个非终结符的Fisrt集是否已经计算出
    public static Set<String> symbolSet=new HashSet<>();

    public static void initial(){
        String filePath = System.getProperty("user.dir")+"/src/parser/"+"Product.txt";
        GetFollowSet.getFollowSet(filePath);
        product = GetFollowSet.product;
        firstSet = GetFollowSet.firstSet;
        followSet = GetFollowSet.followSet;
        for(Map.Entry<String,String> entry:product.entrySet()){
        	String a1=entry.getKey();
        	String a2=entry.getValue();
        	String[] words=a2.split("\\|");
        	for(int i=0;i<words.length;i++) {
        		Set<String> aa=new HashSet<>();
        		selectSet.put(a1+"→"+words[i],aa);
        	}
        }
    }
    
 // 判断一个字符是否为非终结符.
    public static Boolean isTerminal(String s){
        return !product.keySet().contains(s);
    }
    
    public static void getSelectSet(String productLeft,String productRights){
    	String[] rights = productRights.split("\\|");
        for(int k=0;k<rights.length;k++) {
            String productRight = rights[k].trim();
            String[] words = productRight.split("\\s+");
            if(isTerminal(words[0]) && !words[0].equals("ε")) {
            	//System.out.println(words[0]);
            	selectSet.get(productLeft+"→"+rights[k]).add(words[0]);
            	
            }else if(words[0].equals("ε")){
            	//System.out.println(words[0]);
            	selectSet.get(productLeft+"→"+rights[k]).addAll(followSet.get(productLeft));	
            } else {
            	if(firstSet.get(words[0]).contains("ε")) {
            		Set<String> bb=new HashSet<>();
            		bb=firstSet.get(words[0]);
            		bb.remove("ε");
            		selectSet.get(productLeft+"→"+rights[k]).addAll(bb);
            		selectSet.get(productLeft+"→"+rights[k]).addAll(followSet.get(productLeft));
            	}else {
            		selectSet.get(productLeft+"→"+rights[k]).addAll(firstSet.get(productLeft));
            	}
            }       
        }
    	
    }
    
 // 处理所有产生式
    public static void getAllSelectSet(){
        for(Map.Entry<String,String> entry : product.entrySet()){
                getSelectSet(entry.getKey(),entry.getValue());
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
        System.out.println("SelectSet of Product:");
        for(Map.Entry<String,Set<String>> entry:selectSet.entrySet()){
            String right = "";
            for(String s:entry.getValue()){
                right = right+ " "+s;
            }
            System.out.println(entry.getKey().toString()+": {"+right +" }");
        }
    }
    public static void main(String[] args) {
        initial();
        getAllSelectSet();
        printFisrtSet();
        printFollowSet();
        printSelectSet();
    }
}