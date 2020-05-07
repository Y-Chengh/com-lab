package grammar;

import java.util.ArrayList;

public class Production 
{
	public String left;  // ����ʽ��
	public ArrayList<String> list = new ArrayList<String>();  //����ʽ�Ҳ�
	
	/**
	 * �洢����ʽ����ʱ��ʾ�����ڡ�A->BCD������ʽ
	 * @param s ����ʽ�ַ���
	 */
	public Production(String s)
	{
		String[] div = s.split("->");
		this.left = div[0].trim();
		String[] v = div[1].split(" ");
		for(int i = 0;i < v.length;i++)
		{
			if(!v[i].trim().equals(""))	
			{
				list.add(v[i].trim());
			}	
		}
	}
	
	public String toString()
	{
		String result = left+" -> ";
		for(String r:list)
		{
			result += r;
			result += " ";
		}
		return result.trim();
	}
	
	public boolean equalTo(Production d)
	{
		if(this.toString().equals(d.toString()))
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}
	
	public void print()
	{
		System.out.println(this.toString());
	}
}
