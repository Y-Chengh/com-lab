package lexical;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class util
{
    //�ؼ���
	public static String keywords[] = { "proc", "record", "integer", "real",  
        "if", "else", "then", "while", "do", "call", "true", "false", "begin", "end"};
	public static Map<String, Integer> keywords_code = new HashMap<String, Integer>()
	{
		private static final long serialVersionUID=1L;
		{
			for (int i = 0; i < keywords.length; i++)
			{
				put(keywords[i], i + 101);
				//System.out.println(keywords[i]+"    "+ (i+101));
			}
		}	
	};	
	public static Boolean isKeyword(String s) 
	{  
        return keywords_code.containsKey(s);  
    }
	
	
    public static String operator[] = { "+", "-", "*", "<", "<=", ">", ">=", "==", "!="};

	public static Map<String, Integer> operator_code = new HashMap<String, Integer>()
	{
		private static final long serialVersionUID=1L;
		{
			for (int i = 0; i < operator.length; i++)
			{
				put(operator[i], i + 201);
				//System.out.println(operator[i]+"    "+ (i+201));
			}
		}	
	};
	public static Boolean isOperator(String s) 
    {  
		return operator_code.containsKey(s);
    }
	 
	
	public static String delimiter[] = { ",", ";", "[", "]", "(", ")","="};
	public static Map<String, Integer> delimiter_code = new HashMap<String, Integer>()
	{
		private static final long serialVersionUID=1L;
		{
			for (int i = 0; i < delimiter.length; i++)
			{
				put(delimiter[i], i + 301);
				//System.out.println(delimiter[i]+"    "+ (i+301));
			}
		}	
	};
	public static Boolean isDelimiter(String s) 
    {  
		return delimiter_code.containsKey(s);
    }
	
	
	public String toUpper(String s)
	{
		return s.toUpperCase();
	}
	
	public static Boolean isPlusEqu(char ch)  // ��Щ���ź���ɸ������"="
    {  
        return ch == '=' || ch == '>' || ch == '<' || ch == '!' ;  
    }
	
	/*public static Boolean isPlusSame(char ch)  // ��Щ���ź�����ٸ���ͬ�����  
    {  
        return ch == ' ';  
    }*/
	
	public static Boolean isAlpha(char ch)
	{
	    return ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '_');
	}

	public static Boolean isDigit(char ch)
	{  
        return (ch >= '0' && ch <= '9');  
    }


	// String DFA : a���������ַ���b�����\��"֮����ַ�
	public static String stringDFA[] = 
	{ 
		"#\\b#", 
		"##a#", 
		"#\\b\"", 
		"####" 
	};
	
	/**
	 * �ַ���DFA״̬ƥ�亯��
	 * @param ch ��ǰ�ַ�
	 * @param key ״̬���е��ַ�
	 * @return ƥ��ɹ�����true�����򷵻�false 
	 */
	public static Boolean in_stringDFA(char ch, char key) 
	{  
        if (key == 'a')  
            return true;  
        if (key == '\\')  
            return ch == key;  
        if (key == '"')  
            return ch == key;  
        if (key == 'b')  
            return ch != '\\' && ch != '"';  
        return false;  
    }
	
	// char DFA : a���������ַ���b�����\��'֮����ַ�
	public static String charDFA[] = 
	{
		"#\\b#", 
		"##a#", 
		"###\'", 
		"####"
	}; 
	
	/**
	 * �ַ�DFA״̬ƥ�亯��
	 * @param ch ��ǰ�ַ�
	 * @param key ״̬���е��ַ�
	 * @return ƥ��ɹ�����true�����򷵻�false 
	 */
	public static Boolean in_charDFA(char ch, char key) 
	{  
        if (key == 'a')  
            return true;  
        if (key == '\\')  
            return ch == key;  
        if (key == '\'')  
            return ch == key;  
        if (key == 'b')  
            return ch != '\\' && ch != '\'';  
        return false;  
    }
	public static Boolean isEsSt(char ch) 
	{  
        return ch == 'a' || ch == 'b' || ch == 'f' || ch == 'n' || ch == 'r'  
                || ch == 't' || ch == 'v' || ch == '?' || ch == '0';  
    }
	
    // �޷�����DFA
	public static String digitDFA[] = 
	{
	    "#d#####",
		"#d.#e##", 
		"###d###", 
	    "###de##", 
	    "#####-d", 
		"######d", 
		"######d"
	};
	/**
	 * ����DFA״̬ƥ�亯��
	 * @param ch ��ǰ�ַ�
	 * @param test ״̬���е��ַ�
	 * @return ƥ��ɹ�����true�����򷵻�false 
	 */
	public static int in_digitDFA(char ch, char test) 
	{  
        if (test == 'd') 
        {  
            if (isDigit(ch))  
                return 1;  
            else  
                return 0;  
        }  
        else if (test == '-')
        {
        	if (ch == '-' || ch == '+')
        		return 1;
        	else
        		return 0;
        }
        else if (test == 'e')
        {
        	if (ch == 'e' || ch == 'E')
        		return 1;
        	else
        		return 0;
        }
        else
        {
        	if (ch == test)
        		return 1;
        	else
        		return 0;
        }
    }
	

	public static String noteDFA[] = 
	{
		"#####", 
		"##*##", 
		"##c*#", 
		"##c*/", 
		"#####" 
	};
	/**
	 * ע��DFA״̬ƥ�亯��
	 * @param ch ��ǰ�ַ�
	 * @param test ״̬���е��ַ�
	 * @return ƥ��ɹ�����true�����򷵻�false 
	 */
	public static Boolean in_noteDFA(char ch, char nD, int s) 
	{  
        if (s == 2) 
        {  
            if (nD == 'c') 
            {  
                if (ch != '*') 
                	return true;  
                else 
                	return false;  
            }  
        }  
        if (s == 3) 
        {  
            if (nD == 'c') 
            {  
                if (ch != '*' && ch != '/') 
                	return true;  
                else 
                	return false;  
            }  
        }  
        return (ch == nD) ? true : false;  
    }
}
