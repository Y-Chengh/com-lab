package tool;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Tool {
    public List<List<String>> listOfTempNames = new ArrayList<>();// 表示参数的变量名
    public List<List<String>> listOfTempCodes = new ArrayList<>(); // 在测试文件中添加的临时代码
    public List<List<String>> listOfParamCodes = new ArrayList<>();// 最后向3地址指令添加的代码

    public static void main(String[] args) {
        Tool tool = new Tool();
        tool.ana("testCall.txt");
        System.out.println(tool.listOfTempNames.get(0));
        System.out.println(tool.listOfTempCodes.get(0));
        System.out.println(tool.listOfParamCodes.get(0));
    }

    public void removeUnusedVar(){

    }

    public void ana(String filePath){
        int index=0;
        List<String> lines = readFile(filePath);
        for(int i = 0; i < lines.size(); i++){
            if(lines.get(i).contains("call")){
                processCall(lines, i, "_PARAM"+(++index));
            }
        }
        writeFile("testCallOut.txt", lines);
    }

    public void processCall(List<String> lines, int index, String prefix){
        List<String> tempNames = new ArrayList<>();  // 表示参数的变量名
        List<String> tempCodes = new ArrayList<>(); // 在测试文件中添加的临时代码
        List<String> paramCodes = new ArrayList<>(); // 最后向3地址指令添加的代码

        tempNames.add(prefix + "temp");
        tempCodes.add("int " + prefix + "temp" + ";");
        tempCodes.add("int " + prefix + "temp" + " = 0"  + ";");

        String target = lines.get(index).trim();
        String first = target.substring(0, target.indexOf("(")).trim();
        String param = target.substring(target.indexOf("(") + 1, target.lastIndexOf(")")).trim();

        String id = first.substring(5).trim();
        String[] params = param.split(",");

        int sort = 0;
        for(String p: params){
            String tempName = prefix + sort++;
            tempNames.add(tempName);
            tempCodes.add("int " + tempName + ";");
            tempCodes.add("int " + tempName + " = " + p + ";");
            paramCodes.add("param " + tempName);
        }
        paramCodes.add("call " + id);

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
}
