package semantic.src.Lexical.DFA;

import semantic.src.Lexical.Analysis.Main;

import static semantic.src.Lexical.Tool.Tool.*;

public class WordDFA {

    int[] Status = {0, 1};
    static int startStatus = 0, endStatus = 1;

    public static String getWord(String input) {
        if (input == null) {
            return null;
        }
        StringBuilder word = new StringBuilder();
        int pos = Main.pos;
        int nowStatus = startStatus;
        int lastStatus = startStatus;
        for (int i = 0; i < input.length() && nowStatus != -1; i++) {
            char x = input.charAt(i);
            word.append(x);
            lastStatus = nowStatus;
            nowStatus = getNextStatus(nowStatus, x);
            pos++;
//            System.out.println(x+" "+nowStatus+" "+lastStatus+" "+(pos-1));
        }
        if (lastStatus != endStatus) {
            return "ERROR";
        }
        if (nowStatus == -1) {
            Main.pos = pos - 1;
            word.deleteCharAt(word.length() - 1);
        } else {
            Main.pos = pos;
        }
        return word.toString();
    }

    public static int getNextStatus(int fromStatus, char x) {
        switch (fromStatus) {
            case 0:
                if (isunderline(x) || isletter(x))
                    return 1;
                return -1;
            case 1:
                if (isdigit(x) || isletter(x) || isunderline(x)) {
                    return 1;
                }
                return -1;
        }
        return -1;
    }


}
