package lexer;

/*
 类功能: 将NFA转为DFA, 方法为子集构造法.
 格式说明: 1.DNA与DFA中全部用 'a b:c' 表示: 状态a遇到状态输入b时转到状态c.
         2.NFA中: 第一行放入状态必须是起始状态.最后一行留一个接收状态.
         3.NFA中:  'a b:c|d' 表示状态a中遇到输入b,既可以到状态c也可以到状态d.
         4.DFA中:   '[1,3] 0:[0, 1]'表示状态[1,3]遇到 输入0可以转到状态[0,1].
*/

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NFA2DFA {
    public static Map<String,Map<String,Set>> NFAmap = new HashMap();  // 用于存储NFA.
    public static Map<Set,Map<String,Set>> DFAmap = new HashMap<>();   // 用于存储DFA.
    public static HashSet<Set> DFAstates = new HashSet();              // 用于储存DFA的状态,每个状态都表示为set.
    public static HashSet<Set> newStates = new HashSet();              // 转换过程中会出现新的状态,要并入DFAstates.
    public static int fisrtFlag = 1;                                   // 仅初始状态产生的新状态集合要加入产生新状态.
    public static String endState;                                     // NFA中的结束状态, 暂时无用.



    // 根据输入的一行构建NFA, 此函数多次执行, 直到处理完所有输入文件中的所有行.
    public static void getNFA(String input){
        HashMap lineMap = new HashMap();
        String trans,inchar;
        String currentState= input.split(" ")[0];
        int len = input.split(" ").length;
        if(len == 1){ // 将NFA的最后一行留给结束状态
            endState = input;
            NFAmap.put(currentState,lineMap);
            return;
        }
        for(int i =1;i<len;i++){ // 将字符串划分为多个部分
            trans  = input.split(" ")[i];
            inchar = trans.split(":")[0];
            String states = trans.split(":")[1];
            int len2 = states.split("\\|").length;
            HashSet nextStateSet = new HashSet();
            for(int j=0;j<len2;j++){
                nextStateSet.add(states.split("\\|")[j]);
            }
            lineMap.put(inchar,nextStateSet);
            if(nextStateSet.size()>1 && fisrtFlag == 1){ // 存在新产生的状态集合
                DFAstates.add(nextStateSet);
                fisrtFlag -= 1;
            }
        }
        NFAmap.put(currentState,lineMap);
    }

    // 合并两个set, 结果为: 子集构造法的一个新起始状态集合.
    public static Set unionSet(Set set1,Set set2){

        HashSet aSet = new HashSet();
        aSet.addAll(set1);
        aSet.addAll(set2);
        newStates.add(aSet);
        return aSet;
    }

    // 根据一个DFA的起始状态集合和它的输入确定下一个状态集合.
    public static void getSetTrans(Set<String> startSet){
        HashMap<String,Set> aMap = new HashMap(); // 下一个状态集合, 此状态用set
        for(String state: startSet){
            HashSet aSet = new HashSet();
            if(NFAmap.get(state) == null)continue;
            NFAmap.get(state).forEach((key, value) -> aMap.merge(key, value, (v1, v2) -> unionSet(v1,v2)));
        }
        DFAmap.put(startSet,aMap); // 将<输入, 下一个状态> 放入DFA中

    }

    // 从文件中读入NFA.
    public static void readFile(String filePath){
        File file = new File(filePath);
        try{
            InputStream is = new FileInputStream(file);
            Reader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = null;
            int count = 0;
            while((line = bufferedReader.readLine()) != null){
                getNFA(line.trim()); // 处理每一行, 形成DFA.
                if(count == 0){ // 将初始状态加入DFA集合.
                    Set<String> aSet = new HashSet();
                    aSet.add(line.split(" ")[0]);
                    DFAstates.add(aSet);
                }
                count ++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // 将NFA转为DFA.
    public static void NFA2DFA(){
        int len0 = 0,len1= DFAstates.size();
        while (len0 != len1){  // 若DFA中没有新增的状态则停止.
            len0 = len1;
            for(Set aset: DFAstates){
                getSetTrans(aset);
            }
            DFAstates.addAll(newStates); // 将新状态并入DFA.
            len1 = DFAstates.size();
        }
    }

    // 将集合中元素以一定格式返回
    public static String set2String(Set<String> set){
        String res = "{";
        for(String s:set){
            res = res + s +",";
        }
        res = res.substring(0,res.length() -1);
        res += "}";
        return res;
    }

    // 将NFA转为DFA后, 将DFA输出到文件.
    public static  void writeFile(String filePath){
        try{

            File file =new File(filePath);
            FileWriter fileWritter = new FileWriter(file);
            fileWritter.write("flag:\n");
            int line_count = 1;
            for(Map.Entry<Set,Map<String,Set>> entry:DFAmap.entrySet()){
                fileWritter.write(set2String(entry.getKey()));
                fileWritter.write(" ");
                for(Map.Entry<String,Set> entry1 : entry.getValue().entrySet()){
                    fileWritter.write(entry1.getKey()+":");
                    fileWritter.write(set2String(entry1.getValue())+" ");
                }
                if(line_count<DFAmap.size()){
                    fileWritter.write("\n");
                    line_count ++;
                }

            }
            fileWritter.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        readFile( System.getProperty("user.dir")+"/src/lexer/"+"NFA.txt");
        NFA2DFA();
        writeFile(System.getProperty("user.dir")+"/src/lexer/"+"DFA.txt");
    }
}
