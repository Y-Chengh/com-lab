package parser.Semantic;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FourtupleTOThree {
    public  static  List<String> threeTupleList=new ArrayList<>();

    public static List<String> fourTOThree(){
        try {
            FileInputStream inputStream = new FileInputStream("fourTuples.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line=null;
            while ((line=bufferedReader.readLine())!=null){
                String threeTuple = processLine(line);
                threeTupleList.add(threeTuple);
                System.out.println(threeTuple);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return threeTupleList;
    }

    public  static String processLine(String line){
        String[] words = line.split("\\t");
        String fourTuple = words[1];
        String w1 = fourTuple.split(",")[0].replace("(","");
        String w2 = fourTuple.split(",")[1];
        String w3 = fourTuple.split(",")[2];
        String w4 = fourTuple.split(",")[3].replace(")","");

        String threeTuple = "";
        if(w1.equals("=")){
            threeTuple = w4 + " = " + w2;
        }else if(w1.equals("+")||w1.equals("-")||w1.equals("*")||w1.equals("/")){
            threeTuple = w4 +" = "+ w2+" " +w1+" "  +w3;
        }else if (w1.contains("j")){
            if(!w1.equals("j")) {threeTuple = "if "+ w2 + w1.replace("j","")+w3+" ";}
            threeTuple += "goto " + w4;
        }else if(w1.contains(")")){
            threeTuple = w4 + "= (" + w2 +")";
        }else {
            threeTuple = w4 + " = "+ w1 + " "+ w2;
        }

        return words[0]+"\t" + threeTuple;
    }

    public static void main(String[] args) {
        List<String> list = fourTOThree();
        for(String line: list){
            System.out.println(line);
        }
    }

}
