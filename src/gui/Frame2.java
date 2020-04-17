
package gui;

//import com.ui.LineNumberHeaderView;

import lexer.*;
import parser.GetSelectSet;
import parser.Parser;
import parser.packer;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * 
 */
public class Frame2 extends javax.swing.JFrame {
		//private static String[] tablePaths = Path.tablePaths;
	    public static String filePath=null;
	//GEN-BEGIN:variables
		// Variables declaration - do not modify
		private javax.swing.JMenu jMenu1;
		private javax.swing.JMenu jMenu2;
		private javax.swing.JMenuBar jMenuBar1;
		private javax.swing.JMenuItem jMenuItem1;
		private javax.swing.JMenuItem jMenuItem2;
		private javax.swing.JMenuItem jMenuItem3;
		private javax.swing.JScrollPane jScrollPane1;
		private javax.swing.JScrollPane jScrollPane2;
		private javax.swing.JScrollPane jScrollPane3;
		private javax.swing.JScrollPane jScrollPane4;
		private javax.swing.JScrollPane jScrollPane6;
		private javax.swing.JScrollPane jScrollPane10;
		private javax.swing.JScrollPane jScrollPane11;
		private javax.swing.JTable jTable1;
		private javax.swing.JTable jTable2;
		private javax.swing.JTable jTable3;
		private javax.swing.JTable jTable10;
		private javax.swing.JTable jTable11;
		private javax.swing.JTextArea jTextArea1;
		private javax.swing.JTextArea jTextArea2;
		// End of variables declaration//GEN-END:variables

	public static final long serialVersionUID = 1L;

	/** Creates new form Frame */
	public Frame2() {
		initComponents();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	@SuppressWarnings("serial")
	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();//设置滚动面板
		jTextArea1 = new javax.swing.JTextArea();//文本区域
		jTextArea2 = new javax.swing.JTextArea();//文本区域
		jTextArea1.setFont(new Font("黑体",Font.BOLD,24));//设置字体大小
		jTextArea2.setFont(new Font("黑体",Font.BOLD,19));//设置字体大小
		jScrollPane6 = new javax.swing.JScrollPane();
		jScrollPane3 = new javax.swing.JScrollPane();
		jTable2 = new javax.swing.JTable();//表格
		jTable2.setFont(new Font("Menu.font", Font.PLAIN, 19));
		jTable2.setRowHeight(22);  
		jScrollPane2 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();
		jTable1.setFont(new Font("Menu.font", Font.PLAIN, 19));
		jTable1.setRowHeight(22);  
		jScrollPane10 = new javax.swing.JScrollPane();
		jTable10 = new javax.swing.JTable();
		jTable10.setFont(new Font("Menu.font", Font.PLAIN, 19));
		jTable10.setRowHeight(22);  
		jScrollPane11 = new javax.swing.JScrollPane();
		jTable11 = new javax.swing.JTable();
		jTable11.setFont(new Font("Menu.font", Font.PLAIN, 19));
		jTable11.setRowHeight(22);  
		jScrollPane4 = new javax.swing.JScrollPane();
		//jScrollPane4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jTable3 = new javax.swing.JTable();
		jTable3.setFont(new Font("Menu.font", Font.PLAIN, 19));
		jTable3.setRowHeight(22);  
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();//菜单
		jMenuItem1 = new javax.swing.JMenuItem();//菜单里面的选项
		jMenuItem3 = new javax.swing.JMenuItem();
		jMenu2 = new javax.swing.JMenu();
		jMenuItem2 = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setFocusable(false);

		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jScrollPane1.setViewportView(jTextArea1);//添加文本主件
		jTextArea2.setColumns(20);
		jTextArea2.setRows(5);
		jScrollPane6.setViewportView(jTextArea2);//添加文本主件
		
		jScrollPane1.setRowHeaderView(new LineNumberHeaderView());
		jTable2.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {

				}, new String[] { "出错的符号","Error at Line","说明" }) {
			boolean[] canEdit = new boolean[] { false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jScrollPane3.setViewportView(jTable2);

		jTable1.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {

				}, new String[] { "产生式", "first集"}) {
			boolean[] canEdit = new boolean[] { false, false};

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jScrollPane2.setViewportView(jTable1);

		
		jTable10.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {

				}, new String[] { "产生式", "follow集"}) {
			boolean[] canEdit = new boolean[] { false, false};

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jTable10.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jScrollPane10.setViewportView(jTable10);
		
		jTable11.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {

				}, new String[] { "产生式", "select集"}) {
			boolean[] canEdit = new boolean[] { false, false};

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jTable11.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jScrollPane11.setViewportView(jTable11);
		
		
		jScrollPane4.setViewportView(jTable3);
		
		jMenu1.setText("文件");

		jMenuItem1.setText("打开文件");
		jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem1ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem1);

		jMenuItem3.setText("清除文件");
		jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem3ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem3);

		jMenuBar1.add(jMenu1);

		jMenu2.setText("分析");

		jMenuItem2.setText("\u8bcd\u6cd5\u5206\u6790");
		jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem2ActionPerformed(evt);
			}
		});
		jMenu2.add(jMenuItem2);

		jMenuBar1.add(jMenu2);

		setJMenuBar(jMenuBar1);
		
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(//设置组件的布局
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout  //设置{@code Group}的位置和大小沿水平轴的分量。
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)//并行组，沿指定方向（水平/垂直）并行排列元素，能够以四种不同方式对齐其子元素。
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING,
												false)
												.addComponent(jScrollPane3, 0,
														0, Short.MAX_VALUE)
												.addComponent(
														jScrollPane1,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														350, Short.MAX_VALUE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING,
												false)
												.addComponent(
														jScrollPane2,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														350,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jScrollPane10, 0,
														0, Short.MAX_VALUE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING,
												false))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								/*.addComponent(jScrollPane2,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										200,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jScrollPane10,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										200,
										javax.swing.GroupLayout.PREFERRED_SIZE)*/
								.addComponent(jScrollPane11,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										350,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jScrollPane4,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										450,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jScrollPane6,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										350,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		layout.setVerticalGroup(layout	//设置{@code Group}的位置和大小沿垂直轴的分量。
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												/*.addComponent(
														jScrollPane2,
														javax.swing.GroupLayout.Alignment.TRAILING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														476, Short.MAX_VALUE)
												.addComponent(
														jScrollPane10,
														javax.swing.GroupLayout.Alignment.TRAILING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														476, Short.MAX_VALUE)*/
												.addComponent(
														jScrollPane11,
														javax.swing.GroupLayout.Alignment.TRAILING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														676, Short.MAX_VALUE)
												.addComponent(
														jScrollPane4,
														javax.swing.GroupLayout.Alignment.TRAILING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														676, Short.MAX_VALUE)
												.addComponent(
														jScrollPane6,
														javax.swing.GroupLayout.Alignment.TRAILING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														676, Short.MAX_VALUE)
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						jScrollPane1,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						317,
																						javax.swing.GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						jScrollPane3,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						351,
																						Short.MAX_VALUE)))
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						jScrollPane2,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						317,
																						javax.swing.GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						jScrollPane10,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						351,
																						Short.MAX_VALUE))))
								.addContainerGap()));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		String program = jTextArea1.getText();
		//program = program.replaceAll("\r|\n", "");
		// 清除原有行
		DefaultTableModel tableModel1 = (DefaultTableModel) jTable1.getModel();
		tableModel1.setRowCount(0);
		jTable1.invalidate();
		DefaultTableModel tableModel2 = (DefaultTableModel) jTable2.getModel();
		tableModel2.setRowCount(0);
		jTable2.invalidate();
		//创建词法分析类
		//GetSelectSet.filePath=this.filePath;
		GetSelectSet.initial(filePath);
		int aa=GetSelectSet.LL1[0].length;
		String[] ccStrings=new String[aa];
		for(int i=0;i<aa;i++) {
			ccStrings[i]=""+i;
		}
		boolean[] canEdit = new boolean[aa];
		for(int i=0;i<aa;i++) {
			canEdit[i]=false;
		}
		jTable3.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {
				}, ccStrings) {

			private static final long serialVersionUID = 1L;
			boolean[] canEdit;
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jTable3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		int temp=GetSelectSet.LL1.length;
		for(int j=0;j<temp;j++) {
			DefaultTableModel tableModel22 = (DefaultTableModel) jTable3.getModel();
	        tableModel22.addRow(GetSelectSet.LL1[j]);
	        jTable3.invalidate();
		}
		
		for(Map.Entry<String,Set<String>> entry:GetSelectSet.firstSet.entrySet()){
            String right = "";
            for(String s:entry.getValue()){
                right = right+ " "+s;
            }
            DefaultTableModel tableModel22 = (DefaultTableModel) jTable1.getModel();
	        tableModel22.addRow(new Object[] {entry.getKey().toString(),"{"+right +" }"});
	        jTable1.invalidate();
            //System.out.println(entry.getKey().toString()+": {"+right +" }");
        }
		
		for(Map.Entry<String,Set<String>> entry:GetSelectSet.followSet.entrySet()){
            String right = "";
            for(String s:entry.getValue()){
                right = right+ " "+s;
            }
            DefaultTableModel tableModel22 = (DefaultTableModel) jTable10.getModel();
	        tableModel22.addRow(new Object[] {entry.getKey().toString(),"{"+right +" }"});
	        jTable10.invalidate();
            //System.out.println(entry.getKey().toString()+": {"+right +" }");
        }
		
		for(Map.Entry<String,Set<String>> entry:GetSelectSet.selectSet.entrySet()){
            String right = "";
            for(String s:entry.getValue()){
                right = right+ " "+s;
            }
            DefaultTableModel tableModel22 = (DefaultTableModel) jTable11.getModel();
	        tableModel22.addRow(new Object[] {entry.getKey().toString(),"{"+right +" }"});
	        jTable11.invalidate();
            //System.out.println(entry.getKey().toString()+": {"+right +" }");
        }
		
		String filePath2 = System.getProperty("user.dir") + "/src/parser/" + "test.txt";
		Parser parser=new Parser(filePath);
		parser.predict(filePath2);
		for(String temp1:parser.errorList) {
			String[] aaStrings=temp1.split("--");
			DefaultTableModel tableModel22 = (DefaultTableModel) jTable2.getModel();
	        tableModel22.addRow(new Object[] {aaStrings[0],aaStrings[2],aaStrings[1]});
	        jTable2.invalidate();
		}
		
		
		jTextArea2.setText("");
		Stack<packer> stack=new Stack<packer>();
		for(String temp2:parser.productList) {
			String[] aaStrings=temp2.split("→");
			String[] bbStrings=aaStrings[1].trim().split(" ");
			if(temp2.contains("program")) {
				packer aa1=new packer("program", 0);
				//System.out.print("program" + "\r\n");
				jTextArea2.append("program" + "\r\n");
				for(int i=bbStrings.length-1;i>=0;i--) {
					packer aa2=new packer(bbStrings[i], 1);
					stack.push(aa2);
				}
			}else {
				if(stack.isEmpty()) {
					break;
				}
				if(aaStrings[1].contains("ε")) {
					stack.pop();
					continue;
				}
				while(!stack.peek().getValue().equals(aaStrings[0])) {
					stack.pop();
				}
				//System.out.println(stack.peek().getValue());
				if(stack.peek().getValue().equals(aaStrings[0])) {
					
					if(aaStrings[1].contains("#")) {
						String[] ccStrings1=aaStrings[1].split("#");
						int len=stack.peek().getLength();
						for(int j=0;j<len;j++) {
							//System.out.print("  ");
							jTextArea2.append("  ");
							//jTextArea2.append("  ");
						}
						//System.out.print(aaStrings[0]+" :"+ccStrings1[0]+"\r\n");
						jTextArea2.append(aaStrings[0]+" :"+ccStrings1[0]+"\r\n");
						stack.pop();
						continue;
					}
					
					int len=stack.peek().getLength();
					for(int j=0;j<stack.peek().getLength();j++) {
						//System.out.print("  ");
						jTextArea2.append("  ");
					}
					//System.out.print(aaStrings[0]+"\r\n");
					jTextArea2.append(aaStrings[0]+"\r\n");
					
					stack.pop();
					for(int i=bbStrings.length-1;i>=0;i--) {
						packer aa2=new packer(bbStrings[i], len+1);
						stack.push(aa2);
					}
				}
			}
		}
        //parser.productList.forEach(x -> System.out.println(x));
        //parser.errorList.forEach(x -> System.out.println(x));
		//Lexer latex = new Lexer(program, jTable1, jTable2);
		//latex.scan();
	}

	private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		jTextArea1.setText("");
	}

	private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:

		FileDialog fileDialog; //{@code FileDialog}类显示一个对话框窗口用户可以从中选择文件。
		File file = null;
		Frame frame = null;
		fileDialog = new FileDialog(frame, "Open", FileDialog.LOAD);
		fileDialog.setVisible(true);

		try {
			//将textarea清空
			jTextArea1.setText("");
			file = new File(fileDialog.getDirectory(), fileDialog.getFile());
			System.out.println(fileDialog.getDirectory()+fileDialog.getFile());
			filePath=fileDialog.getDirectory()+ fileDialog.getFile();
			FileReader filereader = new FileReader(file);
			BufferedReader bufferreader = new BufferedReader(filereader);
			String aline;
			while ((aline = bufferreader.readLine()) != null)

				jTextArea1.append(aline + "\r\n");
			filereader.close();
			bufferreader.close();

		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Frame2().setVisible(true);
			}
		});
	}

}