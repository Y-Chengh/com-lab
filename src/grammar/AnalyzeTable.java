package grammar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class AnalyzeTable 
{
	
	public static String error = "--";  // �������
	public static String acc = "acc";  // ACC�����ճɹ�����
	public DFAStateSet dfa;  // ����DFA״̬
	public int stateNum;  // DFA״̬��
	
	public int actionLength;  // Action������
	public int gotoLength;  // GoTo������
	private String[] actionCol;  // Action����������
	private String[] gotoCol;  // GoTo����������
	private String[][] actionTable;  // Action����ά����
	private int[][] gotoTable;  // GoTo����ά����
	
	//  ����x��DFA״̬,����S����ʱ,ת�Ƶ���y��DFA״̬,��:
	private ArrayList<Integer> gotoStart = new ArrayList<Integer>();  // �洢��x��DFA״̬
	private ArrayList<Integer> gotoEnd = new ArrayList<Integer>();  // �洢��y��DFA״̬
	private ArrayList<String> gotoPath = new ArrayList<String>();  // �洢S����
	
	/**
	 * ���������
	 */
	public AnalyzeTable()
	{
		createTableHeader();//����
		this.actionLength = actionCol.length;
		this.gotoLength = gotoCol.length;
		createDFA();//����DFA
		this.stateNum = dfa.size();
		this.gotoTable = new int[stateNum][gotoLength+actionLength-1];
		this.actionTable = new String[stateNum][actionLength];
		createAnalyzeTable();//����﷨��������������
	}
	
	/**
	 * ���������������һ��LR(1)�﷨������ı�ͷ
	 */
	private void createTableHeader()
	{
		//�����ǽ���һ�������
		this.actionCol = new String[GrammarProc.VT.size()+1];
		this.gotoCol = new String[GrammarProc.VN.size()+GrammarProc.VT.size()];
		Iterator<String> iter1 = GrammarProc.VT.iterator();
		Iterator<String> iter2 = GrammarProc.VN.iterator();
		int i = 0;
		int j = 0;
		while(iter1.hasNext())  // �ս������
		{
			String vt = iter1.next();
			if(!vt.equals(GrammarProc.emp))
			{
				actionCol[i] = vt;
				gotoCol[i] = vt;
				i++;
			}
		}
		actionCol[i] = "#";
		while(iter2.hasNext())
		{
			String vn = iter2.next();
			gotoCol[i] = vn;
			i++;
		}
	}
	
	//private ArrayList<DFAState> stateList = new ArrayList<DFAState>();//�������еݹ鷽����һ������������

	/**
	 * ��������ݹ鷽������һ�������﷨������DFA
	 * ��������ɲ�����ж���׼Ϊ:
	 */
	private void createDFA()
	{
		this.dfa = new DFAStateSet();
		DFAState state0 = new DFAState(0);
		state0.addNewDerivation(new ProductionState(getDerivation("P'").get(0),"#",0));  // ���ȼ���S'->��S,#
		for(int i = 0;i < state0.set.size();i++)
		{
			ProductionState lrd = state0.set.get(i);
			if(lrd.index < lrd.d.list.size())  // �ǹ�Լ״̬
			{
				String A = lrd.d.list.get(lrd.index);  // ��ȡ"."����ķ���
				//String b = null;  // ����A�ķ��ţ���"."����ĺ���ĵķ���
				Set<String> firstB = new HashSet<String>();
				if(lrd.index==lrd.d.list.size()-1)  // �����ڡ�A->BBB.C, #����״̬
				{
					//b = lrd.lr;
					firstB.add(lrd.lr);
				} 
				else 
				{
					//b = lrd.d.list.get(lrd.index+1);
					//firstB = first(lrd.d.list.get(lrd.index+1));
					Boolean flag = true;
					for(int m = lrd.index+1; m < lrd.d.list.size(); m++)
					{
						Set<String> list1 = first(lrd.d.list.get(m));	
						firstB.addAll(list1);
						if(!list1.contains("��"))
						{
							flag = false;
							break;
						}						
					}
					if(flag)
					{
						firstB.add(lrd.lr);
					}
				}
				if(GrammarProc.VN.contains(A))
				{
					ArrayList<Production> dA = getDerivation(A);
					for(int j=0,length1=dA.size();j<length1;j++)
					{
						for(String f:firstB)
						{
							if(!f.equals("��"))
							{
								ProductionState lrd1;
								if(dA.get(j).list.get(0).equals("��"))
								{
									lrd1 = new ProductionState(dA.get(j), f, 1);		
								}
								else
								{
									lrd1 = new ProductionState(dA.get(j), f, 0);		
								}					
								if(!state0.contains(lrd1))
								{
									state0.addNewDerivation(lrd1);
								}
							}
						}
					}
				}
			}
		}
		dfa.states.add(state0);
		//state0�����ɹ���ʼ�ݹ齨��������״̬
		ArrayList<String> gotoPath = state0.getGotoPath();
		for(String path:gotoPath)
		{
			ArrayList<ProductionState> list = state0.getLRDs(path);//ֱ��ͨ��·��������һ��״̬�����
			addState(0,path,list);//��ʼ���еݹ飬�������ڷ�����DFA
		}
	}
	
	/**
	 * ͨ������һ������һ��״̬��������LR����ʽ��list��ȡ��һ��״̬��
	 * �����״̬�Ѿ����ڣ������κβ����������ݹ飬�����״̬�����ڣ�������״̬���������еݹ�
	 * @param list
	 * @param lastState ��һ��״̬�ı��
	 */
	private void addState(int lastState,String path,ArrayList<ProductionState> list)
	{
		DFAState temp = new DFAState(0);
		for(int i = 0;i < list.size();i++)
		{
			list.get(i).index++;
			temp.addNewDerivation(list.get(i));
		}
		for(int i = 0;i < temp.set.size();i++)
		{
			if(temp.set.get(i).d.list.size() != temp.set.get(i).index)  // �ǹ�Լ
			{
				String A = temp.set.get(i).d.list.get(temp.set.get(i).index);
						
				Set<String> firstB = new HashSet<String>();
				if(temp.set.get(i).index+1 == temp.set.get(i).d.list.size())  // �����ڡ�A->BBB.C, #����״̬
				{
					firstB.add(temp.set.get(i).lr);
				} 
				else 
				{
					Boolean flag = true;
					for(int m = temp.set.get(i).index+1; m < temp.set.get(i).d.list.size(); m++)
					{
						Set<String> list1 = first(temp.set.get(i).d.list.get(m));	
						firstB.addAll(list1);
						if(!list1.contains("��"))
						{
							flag = false;
							break;
						}						
					}
					if(flag)
					{
						firstB.add(temp.set.get(i).lr);
					}
				}
							
				ArrayList<Production> dA = getDerivation(A);

				//ArrayList<String> firstB = first(B);
				for(int j = 0;j < dA.size();j++)
				{
					for(String f:firstB)
					{
						if(!f.equals("��"))
						{
							ProductionState lrd;
							if(dA.get(j).list.get(0).equals("��"))
							{
								lrd = new ProductionState(dA.get(j), f, 1);		
							}
							else
							{
								lrd = new ProductionState(dA.get(j), f, 0);	
							}	
							if(!temp.contains(lrd))
							{
								temp.addNewDerivation(lrd);
							}
						}
					}
				}
			}
		}
		for(int i = 0;i < dfa.states.size();i++)
		{
			if(dfa.states.get(i).equalTo(temp))
			{
				gotoStart.add(lastState);
				gotoEnd.add(i);
				gotoPath.add(path);
				return;
			}
		}
		temp.id = dfa.states.size();
		dfa.states.add(temp);
		gotoStart.add(lastState);
		gotoEnd.add(temp.id);
		gotoPath.add(path);
		ArrayList<String> gotoPath = temp.getGotoPath();
		for(String p:gotoPath)
		{
			ArrayList<ProductionState> l = temp.getLRDs(p);//ֱ��ͨ��·��������һ��״̬�����
			addState(temp.id,p,l);
		}
	}
	
	/**
	 * ��ȡ����ʽ��Ϊv�Ĳ���ʽ�б�
	 * @param v
	 * @return
	 */
	public ArrayList<Production> getDerivation(String v)
	{
		ArrayList<Production> result = new ArrayList<Production>();
		Iterator<Production> iter = GrammarProc.F.iterator();
		while(iter.hasNext())
		{
			Production d = iter.next();
			if(d.left.equals(v))
			{
				result.add(d);
			}
		}
		return result;
	}
	
	/**
	 * ���ڻ�ȡ�ķ�����v��first���б�
	 * @param v
	 * @return
	 */
	private Set<String> first(String v)
	{
		Set<String> result = new HashSet<String>();
		if(v.equals("#"))
		{
			result.add("#");
		} 
		else 
		{
			//System.out.println(v);
			Iterator<String> iter = GrammarProc.firstMap.get(v).iterator();
			while(iter.hasNext())
			{
				result.add(iter.next());
			}
		}
		return result;
	}
	
	/**
	 * ���������������﷨��������������
	 */
	private void createAnalyzeTable()
	{
		for(int i = 0;i < gotoTable.length; i++)
		{
			for(int j = 0;j < gotoTable[0].length;j++)
			{
				gotoTable[i][j] = -1;
			}
		}
		for(int i = 0;i < actionTable.length;i++)
		{
			for(int j = 0;j < actionTable[0].length;j++)
			{
				actionTable[i][j] = AnalyzeTable.error;
			}
		}
		//�����﷨�������goto����
		int gotoCount = this.gotoStart.size();
		for(int i = 0;i < gotoCount;i++)
		{
			int start = gotoStart.get(i);
			int end = gotoEnd.get(i);
			String path = gotoPath.get(i);
			int pathIndex = gotoIndex(path);
			//if(this.gotoTable[start][pathIndex]!=-1)
			//	System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvv");
			this.gotoTable[start][pathIndex] = end;
		}
		//�����﷨�������action����
		int stateCount = dfa.states.size();
		for(int i = 0;i < stateCount;i++)
		{
			DFAState state = dfa.get(i);//��ȡdfa�ĵ���״̬
			for(ProductionState lrd:state.set)
			{//��ÿһ�����з���
				if(lrd.index == lrd.d.list.size())
				{
					if(!lrd.d.left.equals("P'"))
					{
						int derivationIndex = derivationIndex(lrd.d);
						String value = "r"+derivationIndex;
						//if(!actionTable[i][actionIndex(lrd.lr)].equals("--"))
							//System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvv");
						actionTable[i][actionIndex(lrd.lr)] = value;//��Ϊ��Լ
					} 
					else 
					{
						actionTable[i][actionIndex("#")] = AnalyzeTable.acc;//��Ϊ����
					}
				} 
				else 
				{
					String next = lrd.d.list.get(lrd.index);//��ȡ��������ķ�����
					if(GrammarProc.VT.contains(next))
					{//������һ���ս����
						//if(!actionTable[i][actionIndex(next)].equals("--"))
							//System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvv");
						if(gotoTable[i][gotoIndex(next)] != -1)
						{
							actionTable[i][actionIndex(next)] = "s"+gotoTable[i][gotoIndex(next)];
						}
					}
				}
			}
		}
	}
	
	private int gotoIndex(String s)
	{//����goto�е�����
		for(int i = 0;i < gotoLength;i++)
		{
			if(gotoCol[i].equals(s))
			{
				return i;
			}
		}
		return -1;
	}
	
	private int actionIndex(String s)
	{//����action�е�����
		for(int i = 0;i < actionLength;i++)
		{
			if(actionCol[i].equals(s))
			{
				return i;
			}
		}
		return -1;
	}
	
	private int derivationIndex(Production d)
	{//�����ǵڼ������ʽ
		int size = GrammarProc.F.size();
		for(int i = 0;i < size;i++)
		{
			if(GrammarProc.F.get(i).equals(d))
			{
				return i;
			}
		}
		return -1;
	}
	
	public String ACTION(int stateIndex,String vt)
	{
		int index = actionIndex(vt);
		return actionTable[stateIndex][index];
	}
	
	public int GOTO(int stateIndex,String vn)
	{
		int index = gotoIndex(vn);
		return gotoTable[stateIndex][index];
	}
	
	/**
	 * ��ӡ�﷨������
	 */
	public void print()
	{
		StringBuffer result = new StringBuffer();
		String colLine = form("");
		for(int i = 0;i < actionCol.length;i++)
		{
			if(!actionCol[i].equals("integer")&&!actionCol[i].equals("record"))
				colLine += "\t";
			colLine += form(actionCol[i]);
		}
		for(int j = actionCol.length-1;j < gotoCol.length;j++)
		{
			colLine += "\t";
			colLine += form(gotoCol[j]);
		}
		result.append(colLine + "\n");
		//System.out.println(colLine);
		int index = 0;
		for(int i = 0;i < dfa.states.size();i++)
		{
			String line = form(String.valueOf(i));
			while(index < actionCol.length)
			{
				line += "\t";
				line += form(actionTable[i][index]);
				index++;
			}
			index = actionCol.length-1;
			while(index < gotoCol.length)
			{
				line += "\t";
				if(gotoTable[i][index] == -1)
				{
					line += form("--");
				} 
				else 
				{
					line += form(String.valueOf(gotoTable[i][index]));
				}
				index++;
			}
			index = 0;
			line += "\t";
			result.append(line + "\n");
			writefile(result);
			//System.out.println(line);
		}
	}
	
	public String form(String str)
	{
		for(int i = 0; i < 9-str.length(); i++)
		{
			str += " ";
		}
		return str;
	}
	
	
	public void writefile(StringBuffer str)
	{		
        String path = "LR_Analysis_Table.txt";
        try 
        {
            File file = new File(path);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(str.toString()); 
            bw.close(); 
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
	}
	
	public int getStateNum()
	{
		return dfa.states.size();
	}

}
