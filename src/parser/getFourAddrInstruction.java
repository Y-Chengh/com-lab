package parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class getFourAddrInstruction {
    public  static  int count=0;

    // 输入文件地址, 返回一个四地址指令的字符串List
    public  static  void getFourAddrInstruction(String filePath) {
        List<String> fourAddrIns = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = dealLine(line);
                fourAddrIns.add(line);
                System.out.println(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }




    }

    public static String dealLine(String line){
        System.out.println("*****************************************");
        System.out.println(line);
        String lineNumber = line.split(":")[0];
        line = line.split(":")[1];
        String[] fourAddrIns = {"_","_","_","_"};
        if(line.contains("goto")){
            fourAddrIns[0] ="j";
            fourAddrIns[3] = line.split("goto")[1];
            line = line.split("goto")[0];
            if(line.contains("<")){
                fourAddrIns[0] += "<";
                String[] tempArray = line.split("<")[0].trim().split(" ");
                fourAddrIns[1] = tempArray[tempArray.length -1]; //
                fourAddrIns[2] = line.split("<")[1].trim().split(" ")[0];
            }
            else if(line.contains(">")){
                fourAddrIns[0] += ">";
                String[] tempArray = line.split(">")[0].trim().split(" ");
                fourAddrIns[1] = tempArray[tempArray.length -1]; //
                fourAddrIns[2] = line.split(">")[1].trim().split(" ")[0];
            }
            if(line.contains("=")){
                fourAddrIns[0]  += "=";
                String[] tempArray = line.split("=")[0].trim().split(" ");
                fourAddrIns[1] = tempArray[tempArray.length -1]; //
                fourAddrIns[2] = line.split("=")[1].trim().split(" ")[0];
            }

        }else if(line.contains("param")){
            fourAddrIns[0] = line.split(" ")[0];
            fourAddrIns[3] = line.split(" ")[1];
            count++;
        }else if(line.contains("call")){
            fourAddrIns[0] = line.split(" ")[0];
            fourAddrIns[1] = line.split(" ")[2];
            fourAddrIns[3] = line.split(" ")[1];
        }
        else{
            fourAddrIns[3] = line.split("=")[0];
            line = line.split("=")[1];
            if(line.contains("-")){
                fourAddrIns[0] = "-";
                fourAddrIns[1] = line.split("-")[0].trim();//
                fourAddrIns[2] = line.split("-")[1];
            }
            else if(line.contains("+")){
                fourAddrIns[0] = "+";
                fourAddrIns[1] = line.split("\\+")[0].trim();//
                fourAddrIns[2] = line.split("\\+")[1];
            }
            else if(line.contains("*")){
                fourAddrIns[0] = "*";
                fourAddrIns[1] = line.split("\\*")[0].trim();//
                fourAddrIns[2] = line.split("\\*")[1];
            }
            else if(line.contains("/")){
                fourAddrIns[0] = "/";
                fourAddrIns[1] = line.split("/")[0].trim();//
                fourAddrIns[2] = line.split("/")[1];
            }
            else if(line.contains("call")){

            }
            else if(line.contains("param")){
                fourAddrIns[0] = "param";
                fourAddrIns[1] = line.split("/")[0].trim();//
                fourAddrIns[2] = line.split("/")[1];
            }
            else {
                fourAddrIns[0] = "=";
                fourAddrIns[1] = line; //
            }

        }

        String tempStr = "(";
        for (int i=0;i<3;i++) {
            tempStr  = tempStr + fourAddrIns[i]+",";
        }
        tempStr = tempStr + fourAddrIns[3] +")";
        return lineNumber+" : "+tempStr;
    }

    public static void main(String[] args) {
        getFourAddrInstruction("src/parser/"+"interCode.txt");
    }
}
