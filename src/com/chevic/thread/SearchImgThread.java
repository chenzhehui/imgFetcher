package com.chevic.thread;

import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.chevic.ui.MainFrame;
import com.chevic.util.ImgFetcher;

public class SearchImgThread implements Runnable{
	
	private MainFrame mf ;
	public SearchImgThread(MainFrame mf){
		this.mf = mf ;
	}

	public void run() {
		if (mf.getTf_path().getText().trim().equals("")) {
			return;
		} else {
			try {
				Set<String> imgs = ImgFetcher.getImgs(mf.getTf_path().getText()
						.trim());
				mf.setImgs(imgs);
				Object[][] columns = new Object[imgs.size()][1];
				int i = 0;
				for (String img : imgs) {
					columns[i][0] = img;
					i++;
				}
				mf.getLab_info().setText("查找完："+imgs.size()+ "张");
				final JTable table = new JTable(columns, new Object[] { "url" });
				table.getSelectionModel().addListSelectionListener(
						new ListSelectionListener() {
							public void valueChanged(
									ListSelectionEvent e) {
								int firstRow = e.getFirstIndex();
								mf.getLab_img().setText("<html><img width='500px' height='500px' src='"+table.getValueAt(firstRow, 0)+"'/></html>");
							}
						});
				mf.refreshTable(table);
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, ex.toString(),
						"提示：", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
