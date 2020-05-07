package grammar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DFAStateSet 
{
	// ������Ŀ���б�ÿ��Ԫ��Ϊһ��DFA״̬
	public ArrayList<DFAState> states = new ArrayList<DFAState>();
	
	
	public DFAState get(int i)
	{
		return states.get(i);
	}
	
	public int size()
	{
		return states.size();
	}
	/*
	public int contains(DFAState state)
	{
		for(int i = 0;i <states.size();i++)
		{
			if(states.get(i).equals(state))
			{
				return i;
			}
		}
		return -1;
	}
	*/
	/*public void printAllStates()
	{
		int size = states.size();
		for(int i = 0;i < size;i++)
		{
			System.out.println("I"+i+":");
			states.get(i).print();
		}
	}*/
	
	
	
	public void writefile()
	{		
        String path = "LR_DFA_State_Set.txt";
        try 
        {
            File file = new File(path);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            
    		int size = states.size();
    		for(int i = 0;i < size;i++)
    		{
    			bw.write("\n"+"I"+i+":"+"\n"); 
    			//System.out.println("I"+i+":");
    			bw.write(states.get(i).toString());
    			bw.write("\n");
    		} 
            bw.close(); 
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
	}
	
	
	/*
	public DFA()
	{
		
	}*/

}
