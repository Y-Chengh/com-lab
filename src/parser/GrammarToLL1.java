package parser;

import com.sun.xml.internal.bind.v2.TODO;

import java.io.*;
import java.util.*;

// 将文法改造成LL1文法
public class GrammarToLL1 {
    public static HashMap<String,String> product = new HashMap(); // 产生式
//    public static HashMap<String,String> tempProduct = new HashMap<>();

    public static String startSymbol;

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
                if(count==1){
                    startSymbol = productLeft;
                }
                if(product.keySet().contains(productLeft)){  // 若有多个相同左部的产生式,合并为一个
                    productRight = product.get(productLeft)+"|"+ productRight;
                }
                product.put(productLeft,productRight);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void trans2LL1(String inputFilePath,String outputFilePath){
        readProduct(inputFilePath);
        ELR();
        ELCF();
        removeSelf();
        printProduct();
        write2file(outputFilePath);
    }

    /*
    *Eliminate Left Recursion, 消除左递归
    * 左递归如: A → Aα1 | Aα2 | … | Aαn | β1 | β2 | … | βm
    * 转为: A →β1 A′ | β2 A′ | … | βm A′;
    *      A′ →α1 A′ | α2 A′ | … | αn A′ | ε
     * */
    public static void ELR(){
        HashMap<String,String> tempProduct = new HashMap<>();
        //消除左递归
        for(Map.Entry<String,String> entry : product.entrySet()) {
            String productLeft = entry.getKey();
            String productRights = entry.getValue();
            String[] rights = productRights.split("\\|");
            if(rights.length==1)continue;
            List<String> leftRecursionList = new ArrayList<>();
            List<String> nonleftRecursionList = new ArrayList<>();
            for (int k = 0; k < rights.length; k++) {
                String productRight = rights[k].trim();
                String[] words = productRight.split("\\s+");
                if(words[0].equals(productLeft)){
                    String temp = productRight;
                    while (temp.length()>=productLeft.length()){
                        if(temp.substring(0,productLeft.length()).equals(productLeft)){
                            temp = temp.substring(productLeft.length(),temp.length()).trim();
                        }else {
                            break;
                        }
                    }
                    leftRecursionList.add(temp);
//                    leftRecursionList.add(
//                            productRight.substring(productLeft.length()-1,productRight.length()));
                }else {
                    nonleftRecursionList.add(productRight);
                }
            }
            if(leftRecursionList.size()==0)continue;

            String newSymbol = productLeft+"'";
            String newProductRight1 = "";
            for(String s:nonleftRecursionList){
                newProductRight1 = newProductRight1 + s +" " + newSymbol + "|";
            }
            tempProduct.put(productLeft, newProductRight1.substring(0,newProductRight1.length()-1));
            String newProductRight2 = "";
            for(String s:leftRecursionList){
                if(s.equals("ε"))continue;
                newProductRight2 = newProductRight2 + s + " " + newSymbol + "|";
            }
            tempProduct.put(newSymbol,newProductRight2+"ε");
        }
        product.putAll(tempProduct);
    }



    // Algorithm of extracting left common factor, 提取左公因子, 需要多次执行
    public static void ELCF(){
        HashMap<String,String> tempProduct = new HashMap<>();
        for(Map.Entry<String,String> entry : product.entrySet()) {
            String productLeft = entry.getKey();
            String productRights = entry.getValue();
            String[] rights = productRights.split("\\|");
            if (rights.length == 1) continue;
            List<String> leftRecursionList = new ArrayList<>();
            List<String> nonleftRecursionList = new ArrayList<>();
            HashMap<String,Set<String>> prefixMap = new HashMap<>(); // 前缀相同的放到一起
            for (int k = 0; k < rights.length; k++) {
                String productRight = rights[k].trim();
                String[] words = productRight.split("\\s+");
                if(prefixMap.keySet().contains(words[0])){
                    prefixMap.get(words[0]).add(productRight);
                }else {
                    Set<String> tempSet = new HashSet<>();
                    tempSet.add(productRight);
                    prefixMap.put(words[0],tempSet);
                }
            }
            if(prefixMap.size() == rights.length)continue;
            String newProductRights = "";
            for(Set<String> set :prefixMap.values()){
                if(set.size()==1){
                        newProductRights = newProductRights + getSamePrefix(set) +"|";
                }else {
                    String prefix = getSamePrefix(set);
                    String newProductLeft = productLeft +"_"+prefix.replace(" ","");
                    String newProductRight = "";
                    for(String s:set){
                        newProductRight = newProductRight + s.substring(prefix.length(),s.length())+"|";
                    }
                    tempProduct.put(newProductLeft,newProductRight.substring(0,newProductRight.length()-1));
                    newProductRights = newProductRights + prefix+" "+newProductLeft +"|";
                }
            }
            tempProduct.put(productLeft,newProductRights.substring(0,newProductRights.length()-1));
        }
        product.putAll(tempProduct);

    }


    public static String getSamePrefix(Set<String> set){
        String prefix ="";
        int minLen=100;
        for(int i=0;i<minLen;i++){
            boolean firstFlag = true;
            String nextString = "";
            for(String s:set){
                String[] words = s.split("\\s+");
                if(words.length<minLen){
                    minLen = words.length;
                }
                if(firstFlag){
                    firstFlag = false;
                    nextString = words[i];
                }else {
                    if(!words[i].equals(nextString))return prefix.trim();
                }
            }
            prefix = prefix + " "+ nextString;
        }
        return prefix.trim();
    }


    public static void printProduct(){
        for(Map.Entry<String,String>entry:product.entrySet()){
            System.out.printf(entry.getKey()+" → "+entry.getValue()+"\n");
        }
    }

    // 消除 类似于A==>A的无用产生式
    public static void removeSelf(){
        HashMap<String,String> tempProduct = new HashMap<>();
        for(Map.Entry<String,String>entry:product.entrySet()){
            String productLeft = entry.getKey();
            String productRights = entry.getValue();
            String[] rights = productRights.split("\\|");
            String newProducyRight = "";
            for(String s:rights){
                if(!productLeft.equals(s.trim())){
                    newProducyRight = newProducyRight + s +"|";
                }
            }
            tempProduct.put(productLeft,newProducyRight.substring(0,newProducyRight.length()-1));
        }
        product.putAll(tempProduct);
    }

    public static void write2file(String filePath){

        try {

            File file = new File(filePath);
            FileWriter fileWritter = new FileWriter(file);
            for(Map.Entry<String,String> entry: product.entrySet()){
                fileWritter.write(entry.getKey()+" → "+entry.getValue()+"\n");
            }
            fileWritter.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        String inputFilePath = System.getProperty("user.dir")+"/src/parser/"+"OriginProduct.txt";
        String outputFilePath = System.getProperty("user.dir")+"/src/parser/"+"Product.txt";
        trans2LL1(inputFilePath,outputFilePath);
    }
}
