package tool;

import parser.InterCode;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tool {
    public List<List<String>> listOfTempNames = new ArrayList<>();// 表示参数的变量名
    public List<List<String>> listOfTempCodes = new ArrayList<>(); // 在测试文件中添加的临时代码
    public List<List<String>> listOfParamCodes = new ArrayList<>();// 最后向3地址指令添加的代码

    public void pre(){
        ana("lexer/program/test.c");
    }

    public void post(){
        List<String> list = readFile("parser/interCode.txt");
        addCallFunctionCode(list);
        writeFileWith("parser/interCodeOut.txt", list);
    }

    public static void main(String[] args) {
//        String table = "<a, int, 1, 0>\n" +
//                "<b, int, 3, 4>\n" +
//                "<c, int, 4, 8>\n" +
//                "<d, int, 7, 12>\n" +
//                "<e, int, 9, 16>\n" +
//                "<h, float, 34, 64>\n" +
//                "<i, int, 31, 56>\n" +
//                "<j, int, 32, 60>\n" +
//                "<list, int[2][3], 29, 32>\n" +
//                "<PARAM1temp, int, 37, 72>\n" +
//                "<PARAM22, int, 47, 88>\n" +
//                "<PARAM21, int, 45, 84>\n" +
//                "<PARAM20, int, 43, 80>\n" +
//                "<function, proc, 51, 92>\n" +
//                "function Table:\n" +
//                "{\n" +
//                "<a, int, 51, 0>\n" +
//                "<c, int, 51, 4>\n" +
//                "<d, int, 53, 8>\n" +
//                "}\n" +
//                "<x, int, 11, 20>\n" +
//                "<y, int, 12, 24>\n" +
//                "<z, int, 14, 28>\n" +
//                "<PARAM2temp, int, 41, 76>\n";

        Tool tool = new Tool();
        tool.pre();
        tool.post();

//        tool.ana("testCall.txt");//输入的测试文件
//
//
//
//        List<String> list = tool.readFile("parser/interCode.txt");
//        tool.addCallFunctionCode(list);
//        tool.writeFileWith("parser/interCodeOut.txt", list);
//        System.out.println(tool.toStringRemoveUnusedToken(table, tool.listOfTempNames));
    }

    // 去掉符号表中的临时变量
    public String toStringRemoveUnusedToken(String tokenTable){
        String[] s = tokenTable.split("\n");
//        List<String> stringList = Arrays.asList(s);
        List<String> stringList = new ArrayList<>();
        for(String string: s)stringList.add(string);
        for(List<String> tempNames: listOfTempNames){
            for(String tempName: tempNames){
                removeToken(stringList, tempName);
            }
        }

        String res = "";
        for(String string: stringList)res += string + "\n";
        return res;
    }

    public void ana(String filePath){
        int index=0;
        List<String> lines = readFile(filePath);
        for(int i = 0; i < lines.size(); i++){
            if(lines.get(i).contains("call")){
                processCall(lines, i, "PARAM"+(++index));
            }
        }
        writeFile("lexer/program/testout.c", lines);
    }

    //向指令中添加调用函数的指令
    public void addCallFunctionCode(List<String> interCode){
        for (int i = 0; i < listOfTempNames.size(); i++) {
            callFunctionCodeHelper(interCode, i);
        }
    }



    private boolean callFunctionCodeHelper(List<String> interCode, int index){
        List<String> tempNames = listOfTempNames.get(index);
        List<String> tempCodes = listOfTempCodes.get(index);
        List<String> paramCodes = listOfParamCodes.get(index);
        if(tempNames.size() == 1){
            for(int i = 0; i < interCode.size(); i++){
                if(interCode.get(i).contains(tempNames.get(0))){
                    interCode.remove(i);
                    interCode.addAll(i, paramCodes);
                    return true;
                }
            }
        }else{
            for(int i = 0; i < interCode.size(); i++){
                if(interCode.get(i).contains(tempNames.get(0))){
                    interCode.remove(i);
                    break;
                }
            }
            //goto x中的x可能在修改之后目标位置发生错误
            for(int i = 0; i < interCode.size(); i++){
                if(interCode.get(i).contains(tempNames.get(tempNames.size()-1))){
                    interCode.addAll(i+1, paramCodes);
                    return true;
                }
            }
        }
        return false;
    }

    private void processCall(List<String> lines, int index, String prefix){
        List<String> tempNames = new ArrayList<>();  // 表示参数的变量名
        List<String> tempCodes = new ArrayList<>(); // 在测试文件中添加的临时代码
        List<String> paramCodes = new ArrayList<>(); // 最后向3地址指令添加的代码

        tempNames.add(prefix + "temp");
        tempCodes.add("int " + prefix + "temp" + ";");
        tempCodes.add(prefix + "temp" + " = 0"  + ";");

        String target = lines.get(index).trim();
        String first = target.substring(0, target.indexOf("(")).trim();
        String param = target.substring(target.indexOf("(") + 1, target.lastIndexOf(")")).trim();

        String id = first.substring(5).trim();
        String[] params = param.split(",");
        if(param.length() != 0){
            int sort = 0;
            for(String p: params){
                String tempName = prefix + sort++;
                tempNames.add(tempName);
                tempCodes.add("int " + tempName + ";");
                tempCodes.add(tempName + " = " + p + ";");
                paramCodes.add(" param " + tempName);
            }
        }
        paramCodes.add(" call " + id);

        lines.remove(index);
        lines.addAll(index, tempCodes);

        listOfTempNames.add(tempNames);
        listOfParamCodes.add(paramCodes);
        listOfTempCodes.add(tempCodes);
    }

    public List<String> readFile(String fileName){
        fileName = System.getProperty("user.dir") + "/src/" + fileName;
        System.out.println(fileName);
        List<String> res = new ArrayList<>();

        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                if(tempString.contains(":")){
                    tempString = tempString.substring(tempString.indexOf(":") + 1);
                }
                res.add(tempString);
//                System.out.println("line " + line + ": " + tempString);
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

        return res;
    }

    public void writeFile(String fileName, List<String> lines){
        fileName = System.getProperty("user.dir") + "/src/" + fileName;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for(String line: lines)
                bw.write(line + "\n");
            bw.close();
            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFileWith(String fileName, List<String> lines){
        fileName = System.getProperty("user.dir") + "/src/" + fileName;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i = 0; i < lines.size(); i++)
                bw.write(i + " : " + lines.get(i) + "\n");
//            for(String line: lines)
//                bw.write(line + "\n");
            bw.close();
            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean removeToken(List<String> stringList, String tempName){
        for(int i = 0; i < stringList.size(); i++){
            if(stringList.get(i).contains(tempName)){
                stringList.remove(i);
                return true;
            }
        }
        System.out.println("error in removeToken!");
        return false;
    }
}
