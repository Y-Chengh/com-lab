package grammar;

public class ProductionState implements java.lang.Cloneable
{
	public Production d;  // ����ʽ
	public String lr;  // ��̷�
	public int index;  // ��̷�λ��
	
	/**
	 * DFA״̬����ÿһ��״̬
	 * ��ʱ��ʾ�����ڡ�A->BC.D, a������ʽ
	 * @param d  ����ʽ
	 * @param lr ��̷�
	 * @param index ��̷�λ��
	 */
	public ProductionState(Production d,String lr,int index)
	{
		this.d = d;
		this.lr = lr;
		this.index = index;
	}
	
	public String toString()
	{
		String result = d.left+"->";
		int length = d.list.size();
		for(int i = 0;i < length;i++)
		{
			if(length == 1 && d.list.get(0).equals("��"))
			{
				result += " .";
				break;
			}
			else
			{
				result += " ";
				if(i == index)
				{
					result += ".";
				}
				result += d.list.get(i);
			}
		}
		if(index == length && !d.list.get(0).equals("��"))
		{
			result += ".";
		}
		result += " ,";
		result += lr;
		return result;
	}
	
	public boolean equalTo(ProductionState lrd)
	{
		if(d.equalTo(lrd.d)&&lr.hashCode()==lrd.lr.hashCode()&&index==lrd.index)
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
	
	public Object clone()
	{
		return new ProductionState(d,lr,index);
	}

}
