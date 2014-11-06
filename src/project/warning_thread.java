package project;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JLabel;

public class warning_thread implements Runnable{

	JLabel jta=null;
	
	public warning_thread(JLabel jt)
	{
		jta=jt;
	}
	
	public void run(){
		try{
		while(true){
		//System.out.println("\007");//系统报警声
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		toolkit.beep();
		jta.setBackground(Color.RED);
		Thread.sleep(500);
		jta.setBackground(Color.WHITE);
		Thread.sleep(500);
		}
		}catch(InterruptedException e){}
		
		//System.out.println("\007");//系统报警声
	}
}
