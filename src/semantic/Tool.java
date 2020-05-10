package semantic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tool {

    public static List<String> readFile(String filePath){
        List<String> list = new ArrayList<>();

        File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
//                System.out.println("line " + line + ": " + tempString);
                list.add(tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return list;
    }

    public static void checkBool(List<String> lines){
        for(String line: lines){
            String target = "";
            if(line.contains("while")){
                target = line.substring(line.indexOf("while") + 5, line.indexOf("do"));
                target = target.trim();
//                System.out.println(target);
            }else if(line.contains("if")){
                target = line.substring(line.indexOf("if") + 2, line.indexOf("then"));
                target = target.trim();
                System.out.println(target);
            }

            if(target.contains("and") || target.contains("or") || target.contains("not")){
                Tool.updateBool(target);
            }
        }
    }

    private static void updateBool(String target){
        List<String> three_addr = Smantic.three_addr;
        List<FourAddr> four_addr = Smantic.four_addr;
        String[] boolExps = target.split(" ");
        for(int i = 0; i < boolExps.length; i++){
            boolExps[i] = boolExps[i].replace("(", "");
            boolExps[i] = boolExps[i].replace("(", "");
            boolExps[i] = boolExps[i].replace(")", "");
            boolExps[i] = boolExps[i].replace(")", "");
            System.out.println(boolExps[i]);
        }
        int index = -1;
        for(int i = 0; i < three_addr.size(); i++){
            if(three_addr.get(i).contains(boolExps[0])){
                index = i;
                break;
            }
        }
//        if(index == -1){
//            System.out.println("error:-1");
//            return;
//        }
        index = 0;
        boolean lastIsNot = false;
        String connect = "and";
        List<String> insertIns = new ArrayList<>();
        for(int i = 0; i < boolExps.length; i++){
            if(boolExps[i].contains("or"))connect = "or";
            else if(boolExps[i].contains("and"))connect = "and";
            else if(boolExps[i].contains("not"))lastIsNot = true;
            else{
                if(connect == "and"){
                    insertIns.add("if " + boolExps[i] + " goto " + (index + insertIns.size() + 3));
                    insertIns.add("goto target");
                }else if(connect == "or"){
                    insertIns.set(insertIns.size()-1, "if " + boolExps[i] + " goto " + (index + insertIns.size() + 3));
                    insertIns.add("goto target");
                }

                if(lastIsNot){
                    if(boolExps[i].contains("<"))boolExps[i].replace("<", ">");
                    else if(boolExps[i].contains(">"))boolExps[i].replace(">", "<");
                    else if(boolExps[i].contains("=="))boolExps[i].replace("==", "!=");
                    else if(boolExps[i].contains("!="))boolExps[i].replace("!=", "==");
                    insertIns.set(insertIns.size()-2, "if not " + boolExps[i] + " goto " + (index + insertIns.size() + 3));
                    lastIsNot = false;
                }
            }
        }

        for(String s: insertIns) System.out.println(s);
    }

    public static void main(String[] args) {
        Tool.checkBool(new ArrayList<String>(){
            {
                add("  while ((a<b) or c>d ) do  ");
                add(" if (a<b or c>c) and (c>d) then");
            }
        });
    }
}
