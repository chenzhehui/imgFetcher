package com.chevic.thread;

import com.chevic.ui.MainFrame;
import com.chevic.util.ImgFetcher;

public class DownLoadThread implements Runnable{
	private MainFrame mf ;
	public DownLoadThread(MainFrame mf){
		this.mf = mf ;
	}

	@Override
	public void run() {
		if(!this.mf.getTf_path().getText().trim().equals("")){
			String directory = ImgFetcher.getDirectory(this.mf.getTf_path().getText()) ;
			int i=1 ;
			for(String img:this.mf.getImgs()){
				try{
					this.mf.getLab_info().setText("下载中："+i+"/"+this.mf.getImgs().size()+ "张");	
					ImgFetcher.saveImg(directory, img, i);
					i++ ;
				}catch(Exception ex){
					System.out.println(img) ;
//					ex.printStackTrace();
				}
			}
			this.mf.getLab_info().setText("完成："+--i+"/"+this.mf.getImgs().size()+ "张");	
		}
	}

}
