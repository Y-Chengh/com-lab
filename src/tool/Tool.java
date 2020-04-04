package tool;

import java.io.*;
import java.util.*;
import lexer.Path.*;
/**
 * 用于将自己定义的转换表转换为正规的转换表
 */
public class Tool {


    public static void main(String args[]){
        for(String file: tablePaths1){
            Tool.convert(file);
        }
    }

    static void convert(String path){
        String filePath = System.getProperty("user.dir") + "/src/tool/" + path;
        System.out.println(filePath);
        File file = new File(filePath);
        List<String> lines = new ArrayList<>();
        List<String> inputs = new ArrayList<>();
        List<String> states = new ArrayList<>();
        try {
            InputStream is = new FileInputStream(file);
            Reader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }

        for (int i = 1; i < lines.size(); i++) {
            String[] strings = lines.get(i).split(" ");
            states.add(strings[0]);
            for (int j = 1; j < strings.length; j++) {
                if (inputs.contains(strings[j].split(":")[0])) {
                    continue;
                }
                inputs.add(strings[j].split(":")[0]);
            }
        }

        int[][] table = new int[lines.size() - 1][inputs.size()];
        for (int i = 1; i < lines.size(); i++) {
            String[] strings = lines.get(i).split(" ");
            for (int j = 1; j < strings.length; j++) {
                String input = strings[j].split(":")[0];
                int inputIndex = inputs.indexOf(input);
                int targetState = Integer.parseInt(strings[j].split(":")[1]);
                table[i-1][inputIndex] = targetState;
            }
        }
//        System.out.println(inputs);
//        for (int[] temp : table) {
//            System.out.println(Arrays.toString(temp));
//        }
        file = new File(System.getProperty("user.dir") + "/src/tool/formal/" + path);
        System.out.println(file);
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bufferedWriter = null;
        try{
            if(!file.exists()){
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            osw = new OutputStreamWriter(fos);
            bufferedWriter = new BufferedWriter(osw);
//            FileWriter bufferedWriter = new FileWriter(file.getName(),true);
            bufferedWriter.write(lines.get(0) + "\n");
//            System.out.print(lines.get(0) + "\n");
            for (String string : inputs) {
                bufferedWriter.write("\t\t" + string);
            }
            bufferedWriter.write("\n");
            for(int i = 1; i < lines.size(); i++){
                bufferedWriter.write(states.get(i-1));
                for (int j = 0; j < inputs.size(); j++) {
                    System.out.println(i + " " + j);
                    if (table[i - 1][j] == 0) {
                        bufferedWriter.write("\t\t" + "-");
                    } else {
                        bufferedWriter.write("\t\t" + table[i-1][j]);
                    }
                }
                bufferedWriter.write("\n");
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] tablePaths1 = {
            "table/keyword/bool.txt",
            "table/keyword/break.txt",
            "table/keyword/char.txt",
            "table/keyword/continue.txt",
            "table/keyword/do.txt",
            "table/keyword/else.txt",
            "table/keyword/false.txt",
            "table/keyword/float.txt",
            "table/keyword/if.txt",
            "table/keyword/int.txt",
            "table/keyword/struct.txt",
            "table/keyword/true.txt",
            "table/keyword/false.txt",
            "table/keyword/return.txt",

            "table/operator/arithmetic_divide.txt",
            "table/operator/arithmetic_minus.txt",
            "table/operator/arithmetic_multiply.txt",
            "table/operator/arithmetic_plus.txt",
            "table/operator/logic_and.txt",
            "table/operator/logic_not.txt",
            "table/operator/logic_or.txt",
            "table/operator/relational_equal.txt",
            "table/operator/relational_larger.txt",
            "table/operator/relational_largerEqual.txt",
            "table/operator/relational_less.txt",
            "table/operator/relational_lessEqual.txt",
            "table/operator/relational_notEqual.txt",

            "table/delimiters/dot.txt",
            "table/delimiters/equal.txt",
            "table/delimiters/left_middle_parentheses.txt",
            "table/delimiters/left_big_parentheses.txt",
            "table/delimiters/left_small_parentheses.txt",
            "table/delimiters/right_big_parentheses.txt",
            "table/delimiters/right_middle_parentheses.txt",
            "table/delimiters/right_small_parentheses.txt",
            "table/delimiters/semicolon.txt",
            "table/delimiters/comma.txt",

            "table/string.txt",
            "table/multi_line_comments.txt",
            "table/number2.txt",
            "table/number1.txt",
            "table/number.txt",

            "table/identifier.txt",
            "table/sixteen.txt",};
}
