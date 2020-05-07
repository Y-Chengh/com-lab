package grammar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

public class GrammarProc 
{
	public static String emp = "��";  // �մ�
	public static String end = "#";  // ������
	public static TreeSet<String> VN = new TreeSet<String>();  // ���ս����
	public static TreeSet<String> VT = new TreeSet<String>();  // �ս����
	public static ArrayList<Production> F = new ArrayList<Production>();  // ����ʽ��
	//  ÿ�����ŵ�first��
	public static HashMap<String,TreeSet<String> > firstMap = new HashMap<String,TreeSet<String>>();
	
	public static HashMap<String,TreeSet<String> > followMap = new HashMap<String,TreeSet<String> >();  // follow
	//public static TreeSet<String> keywords = new TreeSet<String>();  // �ؼ��ּ�
	static
	{
		// ���ļ��ж�ȡ�ķ��������Ӧ�Ĳ���ʽ
		try 
		{
			read("grammar.txt");
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}

		// ��ӷ��ս��   18 ��
		VN.add("P'");  VN.add("P");   VN.add("D");   VN.add("S");   VN.add("T");  
		VN.add("X");   VN.add("C");   VN.add("E");   VN.add("E1");  VN.add("E2");  
		VN.add("S1");  VN.add("S2");  VN.add("L");   VN.add("B");   VN.add("B1");  
		VN.add("B2");  VN.add("R");   VN.add("EL");  VN.add("S3");  VN.add("S0");
		VN.add("M0");   VN.add("M");  VN.add("N");   VN.add("N1");  VN.add("N2");
		
		// ����ս��  35 ��
		VT.add("proc");  VT.add("id");    VT.add(";");     VT.add("record"); VT.add("integer");
		VT.add("real");  VT.add("[");     VT.add("]");     VT.add("num");    VT.add("=");  
		VT.add("+");     VT.add("*");     VT.add("-");     VT.add("(");      VT.add(")");  
		VT.add("if");    VT.add("then");  VT.add("else");  VT.add("while");  VT.add("do");    
		VT.add("or");     VT.add("and");  VT.add("not");   VT.add("true");   VT.add("false"); 
		VT.add("<");      VT.add("<=");   VT.add("==");    VT.add("!=");     VT.add(">");     
		VT.add(">=");     VT.add("call"); VT.add("begin");     VT.add("end");    VT.add(",");
		//VT.add(emp);  
		
		addFirst();
		//addFollow();
	}
	
	public static void main(String[] args)
	{
		//addFirst();
		//Iterator<String> iterVT = VN.iterator();
		for(int i=0; i<F.size(); i++)
		{
			//String vt = iterVT.next();
			System.out.println(i+":  "+F.get(i).toString());			
		}
	}
	
	/**
	 * ���ļ��ж�ȡ�ķ����Ҵ洢��CFG��ľ�̬�����У���ž���������index
	 * @param filename
	 * @throws FileNotFoundException
	 */
	private static void read(String filename) throws FileNotFoundException
	{
		File file = new File(filename);
		Scanner scanner = new Scanner(file);
		while(scanner.hasNext())
		{
			String line = scanner.nextLine().trim();
			if(!line.equals(""))
			{
				String[] div = line.split("->");
				String[] right = div[1].split("\\|");//���ϲ���д�Ķ�����ʽ�����ɶ��
				for(String r:right)
				{
					Production derivation = new Production(div[0].trim()+"->"+r.trim());
					F.add(derivation);//�洢����̬��������
				}
			}			
		}
		scanner.close();
	}
	
	/**
	 * �������з��ŵ�first����
	 * �м���Ҫ���ɲ��Ƶ���ʹ��һ���ݹ鷽���������
	 */
	private static void addFirst()
	{
		//�����е��ս����first����Ϊ����
		Iterator<String> iterVT = VT.iterator();
		while(iterVT.hasNext())
		{
			String vt = iterVT.next();
			firstMap.put(vt,new TreeSet<String>());
			firstMap.get(vt).add(vt);
		}
		//�������з��ս����first����
		Iterator<String> iterVN = VN.iterator();
		while(iterVN.hasNext())
		{
			String vn = iterVN.next();
			firstMap.put(vn, new TreeSet<String>());//��Ϊ��������û�н����漰firstMap�����Բ��طֳ�����whileѭ�����ϳ�һ�˼���
			firstMap.get(vn).addAll(findFirst(vn));
			/*int dSize = F.size();
			for(int i = 0;i < dSize;i++)
			{
				Production d = F.get(i);
				if(d.left.equals(vn))
				{//��ʵ���Ե���������һ��������ȡ���������ø���
					if(VT.contains(d.list.get(0)) || d.list.get(0).equals(emp))
					{//����ǲ���ʽ�Ҷ˵�һ���ķ�������һ���ս����շ��ţ���ֱ�����
						firstMap.get(vn).add(d.list.get(0));
					} 
					else 
					{//�������ʽ�Ҷ˵�һ���ķ������Ǹ����ս��������Ҫ���еݹ����
						firstMap.get(vn).addAll(findFirst(d.list.get(0)));
					}
				}
			}*/
		}
	}
	
	/**
	 * һ�����ڲ���first�ĵݹ麯��
	 * @param vn
	 * @return ����first��
	 */
	private static TreeSet<String> findFirst(String vn)
	{
		TreeSet<String> set = new TreeSet<String>();
		int size1 = 0;
		int size2 = 0;
		while(true)
		{
			size1 = set.size(); 
			for(Production d:F)
			{
				if(d.left.equals(vn))
				{
					if(VT.contains(d.list.get(0)))  // �ս����ֱ�Ӽ���
					{
						set.add(d.list.get(0));
					} 
					else if(d.list.get(0).equals(emp))  // �շ��ţ�ֱ�Ӽ���
					{
						set.add(emp);
					}
					else if(VN.contains(d.list.get(0)))  // ���ս�����ݹ�
					{
						if(!vn.equals(d.list.get(0)))  // ȥ��������E->E*E��������ݹ�
						{
							TreeSet<String> set2 = findFirst(d.list.get(0));
							set.addAll(set2);  // �ٴεݹ�
							if(set2.contains(emp))
							{
								for(int j=1; j<d.list.size(); j++)
								{
									TreeSet<String> set3 = findFirst(d.list.get(j));									
									set.addAll(set3);  // �ٴεݹ�
									if(!set3.contains(emp))
									{
										break;
									}
								}
							}							
						}
					}
				}
			}
			size2 = set.size(); 
			if(size1 == size2)
				break;
		}
		
		return set;
	}
	
	
	/**
	 * ���ڼ�����ս����follow
	 */
	private static void addFollow()
	{
		Iterator<String> iterVN = VN.iterator();
		HashMap<String,ArrayList<String> > hashmap = new HashMap<String,ArrayList<String> >();
		while(iterVN.hasNext())
		{
			String vn = iterVN.next();
			followMap.put(vn, new TreeSet<String>());
			hashmap.put(vn, new ArrayList<String>());
			for(Production d:F)
			{
				if(d.list.contains(vn))
				{//������һ��ѭ������Ϊ�����˲���ʽ�Ҷ˴��ڶ����ͬԪ��
					ArrayList<Integer> index = new ArrayList<Integer>();
					for(int i = 0;i < d.list.size();i++)
					{
						if(d.list.get(i).equals(vn))
						{
							index.add(i);
						}
					}
					for(int i:index)
					{
						if(i == (d.list.size()-1))
						{//�����һ������ʽ�Ҷ˵�ĩβ������Ҫ����$
							followMap.get(vn).add(end);
							hashmap.get(vn).add(d.left);
						} 
						else 
						{//�������ĩβ����ֱ�Ӽ��Ϻ�һ��Ԫ�ص�first
							TreeSet<String> add = new TreeSet<String>();
							Iterator<String> iter = firstMap.get(d.list.get(i+1)).iterator();
							while(iter.hasNext())
							{
								String value = iter.next();
								if(!value.equals(emp))
								{//�˵��մ�
									add.add(value);
								}
							}
							followMap.get(vn).addAll(add);
						}
					}
				}
			}
			Iterator<String> iter = hashmap.keySet().iterator();
			while(iter.hasNext())
			{
				String key = iter.next();
				ArrayList<String> value = hashmap.get(key);
				if(value.size() != 0)
				{
					for(String v:value)
					{
						followMap.get(key).addAll(followMap.get(v));
						followMap.get(v).addAll(followMap.get(key));
					}
				}
			}
		}
	}

}
