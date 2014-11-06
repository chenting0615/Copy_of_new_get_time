package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class linux_time {
	
	
	private int group=0;
	private int id=0;
	
	
	
	/**
	 * 添加新的linux监控机器
	 * @param ip
	 * @param user
	 * @param passwd
	 * @return
	 */
	public Connection addhost(String ip,String user,String passwd) throws IOException
	{
		Connection conn = new Connection(ip);
		//try {
			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(user, passwd);
		//} catch (IOException e) {e.printStackTrace();}
		return conn;
	}
	
	//执行获取时间
	public String exectime(Connection conn)
	{
		String line=null;
		try
		{
			Session sess = conn.openSession();
			sess.execCommand("date '+%Y/%m/%d %H:%M:%S'");
			InputStream stdout = new StreamGobbler(sess.getStdout());
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
			line = br.readLine();
			sess.close();
		}
		catch (IOException e) {e.printStackTrace();}
		conn.close();
		return line;
	}
	
	
	public String getmonth(String i){
		String month=null;
		switch(i){
		case "Jan":
			month="1";
			break;
		case "Feb":
			month="2";
			break;
		case "Mar":
			month="3";
			break;
		case "Apr":
			month="4";
			break;
		case "May":
			month="5";
			break;
		case "Jun":
			month="6";
			break;
		case "Jul":
			month="7";
			break;
		case "Aug":
			month="8";
			break;
		case "Sep":
			month="9";
			break;
		case "Oct":
			month="10";
			break;
		case "Nov":
			month="11";
			break;
		case "Dec":
			month="12";
		}
		return month;
	}
	
	
	public String get_linux_time(String[] info) throws IOException{
		String result=null;
		Connection conn=addhost(info[0],info[1],info[2]);
		result=exectime(conn);
		int i1=result.indexOf(" ");
		String tmp1=result.substring(0,i1);
		String tmp2=result.substring(i1+1,result.length());
		result=tmp1+"\n"+tmp2;
		return result;
	}
	
	
	public void setgroup(int a,int b){
		group=a;
		id=b;
	}
	
	
	
	/*
	public static void main(String[] args){
		String info[]={"192.168.60.145","root","jinying"};
		linux_time linux_t=new linux_time();
		while(true){
		String result=linux_t.get_linux_time(info);
		//System.out.println("test");
		System.out.println(result);
		}
	}
*/
}
