package project;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class win_time {

	/**
	 * 执行CMD命令
	 * @param arstringCommand
	 */
	private int group=0;
	private int id=0;
	private boolean connected=false;
	public void execCommand(String arstringCommand) {
		try {
			MainPage.runtime.exec(arstringCommand);
			
		} catch (Exception e) {
			execCommand(arstringCommand);
			System.out.println("test");
			System.out.println(e.getMessage());
		}
	}
	
	
	
	public void connect_machine(String[] info){
		String command1="net use \\\\"+info[0]+" /user:"+info[1]+" "+info[2];
		//execCommand(command1);
		String line=null;
		try 
		{
			Process pr=MainPage.runtime.exec(command1);
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream(), "GBK"));
			if((line = input.readLine())!=null)
			{
				String result=null;
				result=line.substring(2,line.length());
				if(result.equals("成功完成。"))
					connected=true;
			}
		
		} catch (Exception e) {System.out.println(e.getMessage());}
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
			Process pr=MainPage.runtime.exec(command2);
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
		connect_machine(info);
		if(connected)
		{
		result=get_time(info);
		connect_disconnect(info);
		connected=false;
		int start=result.indexOf("是");
		String tmp1=result.substring(start+2, result.length());
		start=tmp1.indexOf(" ");
		String tmp2=tmp1.substring(0,start);
		String tmp3=tmp1.substring(start+1,tmp1.length());
		String tmp=tmp2+"\n"+tmp3;
		return tmp;
		}
		else
			return null;
	}
	
	
	public void setgroup(int a,int b){
		group=a;
		id=b;
	}
	
	public static void main(String[] args){
		//String[] info={"192.168.19.52","administrator","jinying"};
		String[] info={"192.168.1.107","administrator","jinying"};
		win_time win_t=new win_time();
		System.out.println(win_t.get_win_time(info));
	}
	
	
	
}
