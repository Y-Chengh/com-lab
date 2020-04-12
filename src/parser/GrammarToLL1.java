package parser;

import com.sun.xml.internal.bind.v2.TODO;

import java.io.*;
import java.util.*;

// 将文法改造成LL1文法
public class GrammarToLL1 {
    public static HashMap<String,String> product = new HashMap(); // 产生式
    public static HashMap<String,String> tempProduct = new HashMap<>();

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

    /*
    *
    * 左递归如: A → Aα1 | Aα2 | … | Aαn | β1 | β2 | … | βm
    * 转为: A →β1 A′ | β2 A′ | … | βm A′;
    *      A′ →α1 A′ | α2 A′ | … | αn A′ | ε
     * */
    public static void trans2LL1(){
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


    // Algorithm of extracting left common factor, 提取左公因子
    public static void ELCF(){

    }
    public static void printProduct(){
        for(Map.Entry<String,String>entry:product.entrySet()){
            System.out.printf(entry.getKey()+" → "+entry.getValue()+"\n");
        }
    }

    public static void write2file(){
        // TODO
    }

    public static void main(String[] args) {
        String filePath = System.getProperty("user.dir")+"/src/parser/"+"OriginProduct.txt";
        readProduct(filePath);
//        String s = "abc";
//        System.out.println(s.substring(1,s.length()-1));
        trans2LL1();
        printProduct();

    }
}
