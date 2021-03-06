package GUI;

/*
 * Frame.java
 *
 * Created on __DATE__, __TIME__
 */


import lexer.Lexer;
import parser.Parser;
import parser.getFourAddrInstruction;
import tool.Tool;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 *
 */
public class Frame3 extends javax.swing.JFrame {
    //GEN-BEGIN:variables
    // Variables declaration - do not modify
    public static String fileString;
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
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;

    Tool tool = new Tool();
    // End of variables declaration//GEN-END:variables

    private static final long serialVersionUID = 1L;

    public static String sourcePath="src/lexer/program/test.c";
    public static String grammarPath = "src/parser/Grammar_Good.txt";

    /** Creates new form Frame */
    public Frame3() {
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
        jTextArea1.setFont(new Font("黑体",Font.BOLD,24));//设置字体大小
        jTextArea2 = new javax.swing.JTextArea();//文本区域
        jTextArea2.setFont(new Font("黑体",Font.BOLD,19));//设置字体大小
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();//表格
        jTable2.setFont(new Font("Menu.font", Font.PLAIN, 19));
        jTable2.setRowHeight(20);
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTable1.setFont(new Font("Menu.font", Font.PLAIN, 19));
        jTable1.setRowHeight(20);
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jTable3.setFont(new Font("Menu.font", Font.PLAIN, 19));
        jTable3.setRowHeight(20);
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
        jScrollPane1.setRowHeaderView(new LineNumberHeaderView());
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                }, new String[] { "Error at Line : 说明" }) {
            boolean[] canEdit = new boolean[] { false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable2);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                }, new String[] { "序号", "四元式","三地址指令"}) {
            boolean[] canEdit = new boolean[] { false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);


        jTable3.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                }, new String[] { "表号","符号","类型","offset" }) {
            boolean[] canEdit = new boolean[] { false, false,false,false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTextArea2);

        jMenu1.setText("文件");

        jMenuItem1.setText("打开测试文件");
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
                try {
                    jMenuItem2ActionPerformed(evt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                                                        443, Short.MAX_VALUE))
                                .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(
                                        layout.createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                false))
                                .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        450,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane4,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        500,
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
                                                .addComponent(
                                                        jScrollPane2,
                                                        javax.swing.GroupLayout.Alignment.TRAILING,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        476, Short.MAX_VALUE)
                                                .addComponent(
                                                        jScrollPane4,
                                                        javax.swing.GroupLayout.Alignment.TRAILING,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        476, Short.MAX_VALUE)
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
                                                                                        151,
                                                                                        Short.MAX_VALUE))))
                                .addContainerGap()));

        pack();
    }// </editor-fold>
    //GEN-END:initComponents


    private void lexerReadSourceCode() throws IOException {
        //BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sourcePath)));
        //bufferedWriter.toString();
        //bufferedWriter.close();
        Lexer lexer = new Lexer(sourcePath);
    }

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        Lexer lexer = new Lexer(sourcePath);
        //System.out.println(sourcePath);
        lexer.scan();
        Parser parser = new Parser(grammarPath);
        parser.items();
        parser.outputLRTableToFile();
        lexerReadSourceCode();
        PrintStream origin = System.out;
        PrintStream printStream = new PrintStream(new FileOutputStream("src/parser/interCode.txt"));
        System.setOut(printStream);
        parser.reduce(lexer.getTokens());
        System.setOut(origin);
        //interCodeArea.setText(readFromFile("src/parser/interCode.txt"));
        //interCodeArea.setEditable(false);
        tool.post();
        File file=new File("src/parser/interCodeOut.txt");
        //getFourAddrInstruction.getFourAddrInstruction("src/parser/interCode.txt");
        FileReader filereader = new FileReader(file);
        BufferedReader bufferreader = new BufferedReader(filereader);
        String aline;
        System.out.println("********************************************");
        while ((aline = bufferreader.readLine()) != null){
            System.out.println(aline);
            String line = getFourAddrInstruction.dealLine(aline);
            String[] aa=aline.split(":");
            DefaultTableModel tableModel2 = (DefaultTableModel) jTable1.getModel();
            tableModel2.addRow(new Object[]{aa[0],aa[1],line});
            jTable1.invalidate();
        }
        filereader.close();
        bufferreader.close();

        //symbolTable.setEditable(false);
        //symbolTable.setText(parser.getTable().toString());
        System.out.println(parser.getTable().toString());
        StringBuilder sb = new StringBuilder();
        sb = new StringBuilder(parser.getTable().toString());
        jTextArea2.setText(tool.toStringRemoveUnusedToken(parser.getTable().toString()));


        StringBuilder parserError = new StringBuilder();
        for (String error : parser.getErrorMessages()){
            parserError.append(error).append("\n");
            DefaultTableModel tableModel2 = (DefaultTableModel) jTable2.getModel();
            tableModel2.addRow(new Object[]{error});
            jTable2.invalidate();
        }
    }

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        jTextArea1.setText("");
    }

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        tool.pre();

        FileDialog fileDialog; //{@code FileDialog}类显示一个对话框窗口用户可以从中选择文件。
        File file = null;
        Frame frame = null;
        fileDialog = new FileDialog(frame, "Open", FileDialog.LOAD);
        fileDialog.setVisible(true);
        //sourcePath=fileDialog.getFile();

        try {
            //将textarea清空
            jTextArea1.setText("");
            file = new File(fileDialog.getDirectory(), fileDialog.getFile());
            //sourcePath=fileDialog.getFile();
            //System.out.println(sourcePath);
            fileString=fileDialog.getFile();
            System.out.println(fileString);
            FileReader filereader = new FileReader(file);
            BufferedReader bufferreader = new BufferedReader(filereader);
            String aline;
            String aa = null;
            while ((aline = bufferreader.readLine()) != null){
                //System.out.println(aline);
                jTextArea1.append(aline + "\r\n");
            }

            filereader.close();
            bufferreader.close();

        } catch (IOException e) {
            System.out.println(e);
            System.out.println("*************8");
        }
    }



    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame3().setVisible(true);
            }
        });
    }

}
