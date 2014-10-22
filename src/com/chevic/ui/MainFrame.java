package com.chevic.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.chevic.thread.DownLoadThread;
import com.chevic.util.ImgFetcher;

public class MainFrame extends JFrame {
	private MainFrame self ;
	private JTextField tf_path; // 目标地址
	private JButton btn_search; // 搜索
	private JScrollPane scrollpane;
	private JTable table;
	private JButton downLoad;
	private ImageIcon img;
	private int x = 0, y = 0;
	private JLabel lab_img  ;
	private JLabel lab_info  ;
	Set<String> imgs ;

	public MainFrame() {
		this.self = this;
		JLabel lab_target = new JLabel("目标:");
		tf_path = new JTextField(300);
		btn_search = new JButton("搜索");

		lab_target.setBounds(50 + x, 10 + y, 100, 25);
		tf_path.setBounds(110 + x, 10 + y, 500, 25);
		btn_search.setBounds(620 + x, 10 + y, 80, 25);
		this.add(lab_target);
		this.add(tf_path);
		this.add(btn_search);

		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		separator.setBounds(0, 50 + y, 800, 5);
		this.add(separator);

		table = new JTable();
		scrollpane = new JScrollPane(table);
		scrollpane.setBounds(10 + x, 60 + y, 200, 450);
		this.add(scrollpane);

		lab_info= new JLabel("待机") ;
		lab_info.setBounds(10+x, 520+y,100,30);
		downLoad = new JButton("下载");
		downLoad.setBounds(130 + x, 520 + y, 80, 30);
		this.add(lab_info) ;
		this.add(downLoad);
		

		JSeparator separator2 = new JSeparator(SwingConstants.VERTICAL);
		separator2.setBounds(225 + x, 50 + y, 5, 600);
		this.add(separator2);

		lab_img = new JLabel(img);
		lab_img.setText("<html>人总要有梦想</html>");
		lab_img.setBounds(235 + x, 60 + y, 550, 500);
		this.add(lab_img);

		this.setLayout(null);
		this.setResizable(false);
		this.setLocation(300, 200);
		this.setSize(800, 600);
		this.setTitle("抓图工具");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		// 事件
		this.regListener();
	}

	private void regListener() {
		this.btn_search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tf_path.getText().trim().equals("")) {
					return;
				} else {
					try {
						imgs = ImgFetcher.getImgs(tf_path.getText()
								.trim());
						Object[][] columns = new Object[imgs.size()][1];
						int i = 0;
						for (String img : imgs) {
							columns[i][0] = img;
							i++;
						}
						lab_info.setText("查找完："+imgs.size()+ "张");
						table = new JTable(columns, new Object[] { "url" });
						table.getSelectionModel().addListSelectionListener(
								new ListSelectionListener() {
									public void valueChanged(
											ListSelectionEvent e) {
										int firstRow = e.getFirstIndex();
										lab_img.setText("<html><img width='500px' height='500px' src='"+table.getValueAt(firstRow, 0)+"'/></html>");
									}
								});
						refreshTable(table);
					} catch (Exception ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(null, ex.toString(),
								"提示：", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		//download imgs.
		downLoad.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new Thread(new DownLoadThread(self)).start() ;
			}
			
		});
	}

	public void refreshTable(JTable table) {
		this.remove(scrollpane);
		this.scrollpane = new JScrollPane(table);
		scrollpane.setBounds(10 + x, 60 + y, 200, 450);
		this.add(scrollpane);

	}

	public JTextField getTf_path() {
		return tf_path;
	}

	public JButton getBtn_search() {
		return btn_search;
	}

	public JScrollPane getScrollpane() {
		return scrollpane;
	}

	public JTable getTable() {
		return table;
	}

	public JButton getDownLoad() {
		return downLoad;
	}

	public ImageIcon getImg() {
		return img;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public JLabel getLab_img() {
		return lab_img;
	}

	public JLabel getLab_info() {
		return lab_info;
	}

	public Set<String> getImgs() {
		return imgs;
	}
	public void setImgs(Set<String> imgs) {
		this.imgs = imgs ;
	}
	
}
