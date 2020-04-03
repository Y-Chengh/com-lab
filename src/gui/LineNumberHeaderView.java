package gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
//TEXTAREA 行号显示插件
public class LineNumberHeaderView extends javax.swing.JComponent {

    /**
	 * JAVA TextArea行数显示插件
	 */
	private static final long serialVersionUID = 1L;
	private final  Font DEFAULT_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 11);//字体类型+字体风格
    public final Color DEFAULT_BACKGROUD = new Color(228, 228, 228);
    public final Color DEFAULT_FOREGROUD = Color.BLACK;
    public final int nHEIGHT = Integer.MAX_VALUE - 1000000;
    public final int MARGIN = 5;
    private int lineHeight;
    private int fontLineHeight;
    private int currentRowWidth;
    private FontMetrics fontMetrics;

    public LineNumberHeaderView() {
        setFont(DEFAULT_FONT);
        setForeground(DEFAULT_FOREGROUD);
        setBackground(DEFAULT_BACKGROUD);
        setPreferredSize(9999);
    }

    public void setPreferredSize(int row) {
        int width = fontMetrics.stringWidth(String.valueOf(row));
        if (currentRowWidth < width) {
            currentRowWidth = width;
            setPreferredSize(new Dimension(2 * MARGIN + width + 1, nHEIGHT));
        }
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        fontMetrics = getFontMetrics(getFont());
        fontLineHeight = fontMetrics.getHeight();
    }

    public int getLineHeight() {
        if (lineHeight == 0) {
            return fontLineHeight;
        }
        return lineHeight;
    }

    
    //设置开始的地方
    public int getStartOffset() {
        return 4;
    }
    
    
    /*
	javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
			getContentPane());
	getContentPane().setLayout(layout);
	layout.setHorizontalGroup(layout
			.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
					javax.swing.GroupLayout.Alignment.TRAILING,
					layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(
									layout.createParallelGroup(
											javax.swing.GroupLayout.Alignment.TRAILING)
											.addComponent(
													jScrollPane3,
													javax.swing.GroupLayout.Alignment.LEADING,
													javax.swing.GroupLayout.DEFAULT_SIZE,
													433, Short.MAX_VALUE)
											.addComponent(
													jScrollPane1,
													javax.swing.GroupLayout.Alignment.LEADING,
													javax.swing.GroupLayout.DEFAULT_SIZE,
													500, Short.MAX_VALUE))
							.addPreferredGap(
									javax.swing.LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(
									layout.createParallelGroup(
											javax.swing.GroupLayout.Alignment.LEADING)
											.addComponent(
													jScrollPane2,
													javax.swing.GroupLayout.DEFAULT_SIZE,
													298, Short.MAX_VALUE))
							.addContainerGap()));
	layout.setVerticalGroup(layout
			.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
					layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(
									layout.createParallelGroup(
											javax.swing.GroupLayout.Alignment.TRAILING)
											.addComponent(
													jScrollPane2,
													javax.swing.GroupLayout.Alignment.LEADING,
													433, 433, Short.MAX_VALUE)
											.addComponent(
													jScrollPane1,
													javax.swing.GroupLayout.Alignment.LEADING,
													javax.swing.GroupLayout.DEFAULT_SIZE,
													317, Short.MAX_VALUE))
							.addPreferredGap(
									javax.swing.LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(
									layout.createParallelGroup(
											javax.swing.GroupLayout.Alignment.LEADING)
											.addComponent(
													jScrollPane3,
													javax.swing.GroupLayout.DEFAULT_SIZE,
													151, Short.MAX_VALUE)
									)
							.addContainerGap()));

	pack();*/
    
    @Override
    protected void paintComponent(Graphics g) {
        int nlineHeight = getLineHeight();//获得行的间隔
        int startOffset = getStartOffset();//开始的地方
        Rectangle drawHere = g.getClipBounds();//返回当前剪贴区域的边界矩形
        g.setColor(getBackground());
        g.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);
        g.setColor(getForeground());
        int startLineNum = (drawHere.y / nlineHeight) + 1;
        int endLineNum = startLineNum + (drawHere.height / nlineHeight);
        int start = (drawHere.y / nlineHeight) * nlineHeight + nlineHeight - startOffset;
        for (int i = startLineNum; i <= endLineNum; ++i) {
            String lineNum = String.valueOf(i);
            int width = fontMetrics.stringWidth(lineNum);
            g.drawString(lineNum + " ", MARGIN + currentRowWidth - width - 1, start);
            start += nlineHeight;
        }
        setPreferredSize(endLineNum);
    }
}