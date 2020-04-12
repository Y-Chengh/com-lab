package parser;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

// 将文法改造成LL1文法
public class grammarToLL1 {
    public static HashMap<String,String> product = new HashMap(); // 产生式
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
                if(product.keySet().contains(productLeft)){  // 若有多个相同左部的产生式,合并为一个
                    productRight = product.get(productLeft)+"|"+ productRight;
                }
                product.put(productLeft,productRight);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void trans2LL1(){
        //消除左递归

    }

    public static void main(String[] args) {

    }
}
