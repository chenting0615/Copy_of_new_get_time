package project;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class win_time {

	/**
	 * ÷¥––CMD√¸¡Ó
	 * @param arstringCommand
	 */
	
	private int group=0;
	private int id=0;
	public void execCommand(String arstringCommand) {
		try {
			Runtime.getRuntime().exec(arstringCommand);
			
		} catch (Exception e) {
			execCommand(arstringCommand);
			System.out.println("test");
			System.out.println(e.getMessage());
		}
	}
	
	
	
	public void connect_machine(String[] info){
		String command1="net use \\\\"+info[0]+" /user:"+info[1]+" "+info[2];
		execCommand(command1);
	}
	
	public void connect_disconnect(String[] info){
		String command1="net use \\\\"+info[0]+" /del";
		execCommand(command1);
	}
	
	public String get_time(String[] info){
		String line=null;
		String command2="net time \\\\"+info[0];
		try 
		{
			Process pr=Runtime.getRuntime().exec(command2);
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream(), "GBK"));
			if((line = input.readLine())!=null)
			{
				String result=null;
				result=line.substring(2,line.length());
			}
		
		} catch (Exception e) {System.out.println(e.getMessage());}
		return line;
	}
	
	public String get_win_time(String[] info){
		String result=null;
		if(!MainPage.has_connect[group][id])
			connect_machine(info);
		result=get_time(info);
		//connect_disconnect(info);
		int start=result.indexOf(" «");
		String tmp1=result.substring(start+2, result.length());
		start=tmp1.indexOf(" ");
		String tmp2=tmp1.substring(0,start);
		String tmp3=tmp1.substring(start+1,tmp1.length());
		String tmp=tmp2+"\n"+tmp3;
		return tmp;
	}
	
	
	public void setgroup(int a,int b){
		group=a;
		id=b;
	}
	
	
	public static void main(String[] args){
		//String[] info={"192.168.19.52","administrator","jinying"};
		String[] info={"192.168.19.10","administrator","jason11420"};
		win_time win_t=new win_time();
		while(true)
		System.out.println(win_t.get_win_time(info));
	}
	
	
	
}
