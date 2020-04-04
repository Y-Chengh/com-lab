package lexer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import lexer.Lexer.Pack;



public class Lexer {
    private List<List<Map<String, Integer>>> tableList = new ArrayList<>();
    private List<List<State>> statesList = new ArrayList<>();// 记录每个转换表中的哪些状态是可接受的，以及type和value
    private List<Integer> flagList = new ArrayList<>();
    private static String[] tablePaths = Path.tablePaths;

    private JTable jtable1;
	private JTable jtable2;
	private String text;
    
    public Lexer(String text,JTable jtable1, JTable jtable2)
	{
    	this.text=text;
		this.jtable1 = jtable1;
		this.jtable2 = jtable2;
		for(String filePath: tablePaths){
            //这里不知道为什么用相对路径没法读文件
            this.readDFATable(System.getProperty("user.dir") + "/src/lexer/" + filePath, 1);
        }
	}

    public Lexer()
    {
        for(String filePath: tablePaths){
            //这里不知道为什么用相对路径没法读文件
            this.readDFATable(System.getProperty("user.dir") + "/src/lexer/" + filePath, 1);
        }
    }

    // this method is for testing
    public static void main(String args[]) {
        Lexer lexer = new Lexer();
//        lexer.readDFATable(System.getProperty("user.dir") + "/src/lexer/" + "formalTable/comments.txt", 1);
//        lexer.check(new StringBuilder("while"), lexer.statesList.get(0), lexer.tableList.get(0), 1);
//        lexer.scan("test", System.getProperty("user.dir") + "/src/lexer/" + "test.c");
    }

    public void scan(){
        List<Pack> acceptTokenList = new ArrayList<>();
        List<String> errorTokenList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb = new StringBuilder(("" + text).trim());
        boolean isLastCharAccept = true;//记录上一个字符是否被接收，如果没有被接收的同时，下一个字符也没有被接收，那么就将这两个字符合并
        while(sb.length() != 0){
            Pack acceptPack = null; // 最终被接受的pack，可能会有很多的table都有接受的串，但是选最长的串接受
            for(int i = 0; i < tableList.size(); i++){
                List<Map<String, Integer>> table = tableList.get(i);
                List<State> state = statesList.get(i);
                Pack pack = check(sb, statesList.get(i), tableList.get(i), flagList.get(i));
                if (pack == null) {
                    continue;
                }
                if(acceptPack == null || acceptPack.length < pack.length){ // 这里没有等于的情况，因此一定要先比较“while”之类的关键字，然后在比较id
                    acceptPack = pack;
                }
            }
            if (acceptPack == null) {
                // 没有识别出任何的可接受的串
//                System.out.println("pass");
                String errString = "" + sb.charAt(0);
                if (errString.trim().length() == 0) {
                    sb.deleteCharAt(0);
                    isLastCharAccept = true;
                    continue;
                }
                if (!isLastCharAccept) {
                    errString = errorTokenList.get(errorTokenList.size() - 1) + errString;
                    errorTokenList.set(errorTokenList.size() - 1, errString);
                } else {
                    errorTokenList.add(errString);
                    isLastCharAccept = false;
                }
                sb.deleteCharAt(0);
            } else {
                isLastCharAccept = true;
                acceptPack.token = "" + sb.substring(0, acceptPack.length);
                sb.delete(0, acceptPack.length);
                acceptTokenList.add(acceptPack);
            }
        }
        acceptTokenList.forEach(x-> System.out.println("acc:" + x.token + "  <" + x.type + "," + x.value + ">"));
        for (Pack pp:acceptTokenList) {
        	DefaultTableModel tableModel = (DefaultTableModel) jtable1.getModel();
            tableModel.addRow(new Object[] {pp.token, "  <" + pp.type + "," + pp.value + ">"});
            jtable1.invalidate();
        }
        System.out.println(errorTokenList);
        for (String pp:errorTokenList) {
        	DefaultTableModel tableModel2 = (DefaultTableModel) jtable2.getModel();
            tableModel2.addRow(new Object[] {"...",pp});
            jtable2.invalidate();
        }
    }

    private Pack check(StringBuilder sb, List<State> states, List<Map<String, Integer>> table, int flag) {
        int currentStateIndex = 0;
//        System.out.println(sb);
        for (int i = 0; i < sb.length(); i++) {
            String input = tokenToTableInput(sb.charAt(i), flag);
//            System.out.println("input:" + input + "  currentState:" + currentStateIndex);
//            System.out.println(table);
            int targetStateIndex = table.get(currentStateIndex).getOrDefault(input, -1);
            if (targetStateIndex == -1) {
//                System.out.println("targetState=-1" + "  currentState=" + currentStateIndex);
                return null;
            }

            currentStateIndex = targetStateIndex;
            State currentState = states.get(currentStateIndex);
            if (currentState.getAccept()) {
                if (i < sb.length() - 1) {
                    // 这里主要是确保每次匹配得到的可接受的串都是最长的
                    // 因为identifier每一个subString(0,n)都是可接受的，才可以这么处理
                    char nextChar = sb.charAt(i+1);
                    String nextInput = tokenToTableInput(nextChar, flag);
                    int nextCurrentStateIndex = targetStateIndex; //本质上就是targetStateIndex
                    int nextTargetStateIndex = table.get(nextCurrentStateIndex).getOrDefault(nextInput, -1);
                    if (nextTargetStateIndex != -1) {
                        State nextTargetState = states.get(nextTargetStateIndex);
                        if(nextTargetState.getAccept()){
                            continue;
                        }
                    }
                }
                String string = sb.substring(0, i+1);
                String type = currentState.generateType(string);
                String value = currentState.generateValue(string);
                return new Pack(type, value, string.length());
            }
        }
        return null;
    }

    class Pack {
        String type = null;
        String value = null;
        int length = -1;
        String token = null;
        public Pack(String type, String value, int length) {
            this.type = type;
            this.value = value;
            this.length = length;
        }
    }

    private String tokenToTableInput(char s, int flag) {
    	if(flag==9) {//用于识别16进制
        	if (s=='0') {
        		return "" + s;
        	}else if (s=='x'||s=='X') {
        		return "" + s;
        	}else if(s >= '0' && s <= '9') {
        		return "[0-9]";
        	}else if((s >= 'A' && s <= 'F')) {
        		return "[A-F]";
        	}
        }
    	
        if (flag == 1) {
            return "" + s;
        }else if (flag == 2) { // 用于在识别科学计数法时使用
            if (s >= '0' && s <= '9') {
                return "[0-9]";
            } else if (s == 'e') {
                return "" + s;
            } else if (s == '.') {
                return "" + s;
            } else {
                return "others";
            }
        } else if (flag == 3) { // 用于注释
            if (s == '*' || s == '/' || s == '"') {
                return "" + s;
            } else {
                return "others";
            }
        } 
        if (s >= '0' && s <= '9') {
            return "[0-9]";
        } else if ((s >= 'a' && s <= 'z') || (s >= 'A' && s <= 'Z')) {
            return "[a-zA-Z]";
        } else if (";{}[]()=!+-/*&|.\"'_".indexOf(s) != -1) {
            return "" + s;
        } else {
            return "others";
        }
    }

    public void readDFATable(String filePath){
        List<Map<String, Integer>> table = new ArrayList<>();
        List<State> states = new ArrayList<>();
        File file = new File(filePath);
//        System.out.println(file);
        try{
            InputStream is = new FileInputStream(file);
            Reader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = null;
            int count = 0;
            line = bufferedReader.readLine();
            int flag = Integer.parseInt(line.substring(5, 6));
            flagList.add(flag);
            while((line = bufferedReader.readLine()) != null){
//                System.out.println("line:" + line);
                String[] strings = line.split(" ");
                String state = strings[0];
                if(state.contains("<")){
                    int indexOfState = state.indexOf("<");// 状态号的最后一个字符的index
                    int tableState = Integer.parseInt(state.substring(0, indexOfState));
                    assert tableState == count;
                    String subString = state.substring(indexOfState + 1, state.length() - 1);
                    String type = subString.split(",")[0];
                    String value = subString.split(",")[1];
                    states.add(new State(tableState, true, type, value));
                }else{
                    int tableState = Integer.parseInt(state);
                    assert tableState == count;
                    states.add(new State(tableState));
//                    System.out.println("get to line 206");
                }
//                System.out.println("get to line 207");
                Map<String, Integer> map = new HashMap<>();
                for(int i = 1; i < strings.length; i++){
                    String input = strings[i].split(":")[0];
//                    System.out.println("input207:" + input);
                    int targetState = Integer.parseInt(strings[i].split(":")[1]);
                    map.put(input, targetState);
                }
//                System.out.println(map);
                table.add(map);
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tableList.add(table);

        statesList.add(states);

        assert tableList.size() == statesList.size();
    }

    public void readDFATable(String filePath, int flag) {
        List<Map<String, Integer>> table = new ArrayList<>();
        List<State> states = new ArrayList<>();
        List<String> inputList = new ArrayList<>();
        File file = new File(filePath);
//        System.out.println(file);
        try{
            InputStream is = new FileInputStream(file);
            Reader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = null;
            int count = 0;
            line = bufferedReader.readLine();
            flag = Integer.parseInt(line.substring(5, 6));
            flagList.add(flag);
            line = bufferedReader.readLine();
            String[] inputs = line.split("\t\t");
            for (int i = 1; i < inputs.length; i++) {
                inputList.add(inputs[i]);
            }
            while((line = bufferedReader.readLine()) != null){
//                System.out.println("line:" + line);
                String[] strings = line.split("\t\t");
                String state = strings[0];
                if(state.contains("<")){
                    int indexOfState = state.indexOf("<");// 状态号的最后一个字符的index
                    int tableState = Integer.parseInt(state.substring(0, indexOfState));
                    assert tableState == count;
                    String subString = state.substring(indexOfState + 1, state.length() - 1);
                    String type = subString.split(",")[0];
                    String value = subString.split(",")[1];
                    states.add(new State(tableState, true, type, value));
                }else{
                    int tableState = Integer.parseInt(state);
                    assert tableState == count;
                    states.add(new State(tableState));
//                    System.out.println("get to line 206");
                }
//                System.out.println("get to line 207");
                Map<String, Integer> map = new HashMap<>();
                for(int i = 1; i < strings.length; i++){
//                    System.out.println("input207:" + input);
                    if (strings[i].indexOf("-") != -1) {
                        continue;
                    }
                    int targetState = Integer.parseInt(strings[i]);
                    map.put(inputList.get(i-1), targetState);
                }
//                System.out.println(map);
                table.add(map);
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tableList.add(table);

        statesList.add(states);

        assert tableList.size() == statesList.size();
    }
}
