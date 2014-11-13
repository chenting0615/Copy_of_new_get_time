package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JLabel;

public class gettime_thread extends Thread{

	public JLabel jta=null;
	public String[] info=null;
	public boolean started=false;
	Font x = new Font("Serif",0,12);
	
	public  int group;
	public  int id;
	
	
	public gettime_thread(JLabel jt,String[] inf)
	{
		jta=jt;info=inf;
	}
	public void run(){
		jta.setFont(x);
		win_time wt=new win_time();
		linux_time lt=new linux_time();
		//warning_thread wth=new warning_thread(jta);
		//Thread warn=new Thread(wth);
		//boolean started=false;
		if(info[3].equals("windows")){

			while(true){
				try{
			String tmp=wt.get_win_time(info);
			//System.out.println("测试获取的数据"+tmp);
			if(tmp==null){
				jta.setOpaque(true);
				jta.setBackground(Color.RED);
				Thread.sleep(10000);
				this.resume();
			}
			else
				jta.setOpaque(false);
			wt.setgroup(group, id);
			if(tmp!=null){
				jta.setOpaque(false);
				int n1=tmp.indexOf("\n");
				String tmp_1=tmp.substring(0,n1);
				String tmp_2=tmp.substring(n1+1, tmp.length());
				//String result=info[0]+"\n"+tmp;
				String result="<html><body>"+info[0]+"<br>"+tmp_1+"<br>"+tmp_2+"</body></html>";
				//System.out.println(result);
				if(result!=null)
				jta.setText(result);
				MainPage.has_connect[group][id]=true;
				//jta.paintImmediately(jta.getBounds());
				//jta.updateUI();
				if(!deal_string_1(tmp,get_standtime()))
				{
					jta.setOpaque(true);
					warn();
					if(!started){
						MainPage.error++;
						started=true;
					}
				}
				else
				{
					jta.setOpaque(false);
					if(started){
						MainPage.error--;
						started=false;
						MainPage.max=MainPage.min;
					}
					try{
						Thread.sleep(MainPage.secondsGap);
					}catch (InterruptedException e) {e.printStackTrace();}
				}
			}
				}catch(Exception e){
					System.out.println(e.getMessage());
					//System.out.println("已断开");
					jta.setOpaque(true);
					jta.setText("连接异常");
					warn();
					this.resume();
					
					}
			}
		}
		else{
			try{
			while(true){
			String tmp=lt.get_linux_time(info);
			lt.setgroup(group, id);
			//String result=info[0]+"\n"+tmp;
			if(tmp!=null)
			{
				jta.setOpaque(false);
				int n1=tmp.indexOf("\n");
				String tmp_1=tmp.substring(0,n1);
				String tmp_2=tmp.substring(n1+1, tmp.length());
				//String result=info[0]+"\n"+tmp;
				String result="<html><body>"+info[0]+"<br>"+tmp_1+"<br>"+tmp_2+"</body></html>";
				jta.setText(result);
				MainPage.has_connect[group][id]=true;
				//jta.paintImmediately(jta.getBounds());
				//jta.updateUI();
				if(!deal_string_1(tmp,get_standtime()))
				{
					jta.setOpaque(true);
					warn();
					if(!started){
						MainPage.error++;
						started=true;
					}
				}
				else
				{
					jta.setOpaque(false);
					if(started){
						MainPage.error--;
						started=false;
						MainPage.max=MainPage.min;
					}
					try{
						Thread.sleep(MainPage.secondsGap);
					}catch (InterruptedException e) {e.printStackTrace();}
				}
			}
			}
		}catch(IOException e){
			System.out.println(e.getMessage());
			jta.setOpaque(true);
			jta.setText("连接异常");
			System.out.println("linux连接异常");
			warn();
			gettime_thread new_thread=new gettime_thread(jta,info);
			new_thread.setid(group, id);
			new_thread.start();
			this.stop();
		}
		}
	}
	
	public static boolean deal_string(String s1,String s2)
	{
		boolean result=true;
		if(s1!=null&&s2!=null)
		{
			int t1=s1.indexOf("\n");
			String s1_1=s1.substring(0,t1);
			String s1_2=s1.substring(t1+1,s1.length());
			
			int t2=s2.indexOf("\n");
			String s2_1=s2.substring(0,t2);
			String s2_2=s2.substring(t2+1,s2.length());
			
			String [] kk=s1_1.split("/");
			String [] jj=s2_1.split("-");
			for(int i=0;i<kk.length;i++){
				if(Integer.parseInt(kk[i])!=Integer.parseInt(jj[i]))
					return false;
			}
			
			String [] kk_1=s1_2.split(":");
			String [] jj_1=s2_2.split(":");
			
			int tmp1=Integer.parseInt(kk_1[0])*3600+Integer.parseInt(kk_1[1])*60+Integer.parseInt(kk_1[2]);
			int tmp2=Integer.parseInt(jj_1[0])*3600+Integer.parseInt(jj_1[1])*60+Integer.parseInt(jj_1[2]);
			if(Math.abs(tmp1-tmp2)>=300)
				return false;
		}
		
		
		return result;
	}
	
	
	public  boolean deal_string_1(String s1,String s2)
	{
		try{
		if(s1!=null&&s2!=null)
		{
			int[] a=new int[6];
			int[] b=new int[6];
			int t1=s1.indexOf("\n");
			String s1_1=s1.substring(0,t1);
			String s1_2=s1.substring(t1+1,s1.length());
			
			int t2=s2.indexOf("\n");
			String s2_1=s2.substring(0,t2);
			String s2_2=s2.substring(t2+1,s2.length());
			
			String [] kk=s1_1.split("/");
			String [] jj=s2_1.split("-");
			for(int i=0;i<kk.length;i++){
				a[i]=Integer.parseInt(kk[i]);
				b[i]=Integer.parseInt(jj[i]);
			}
			
			//int temp1=Integer.parseInt(kk[0])*365+Integer.parseInt(kk[1])
			
			
			String [] kk_1=s1_2.split(":");
			String [] jj_1=s2_2.split(":");
			
			for(int i=0;i<kk_1.length;i++){
				a[3+i]=Integer.parseInt(kk_1[i]);
				b[3+i]=Integer.parseInt(jj_1[i]);
			}
			
			
			

			int cha=seconds(a,b);
			
			if(cha>=MainPage.divGap[group][id])
				return false;
			else
				return true;
		}
		else
			return true;
		}catch(Exception e){this.resume();}
		
		return true;
		
	}
	
	public  int  get_seconds(String s1,String s2){
		int[] a=new int[6];
		int[] b=new int[6];
		int t1=s1.indexOf("\n");
		String s1_1=s1.substring(0,t1);
		String s1_2=s1.substring(t1+1,s1.length());
		
		int t2=s2.indexOf("\n");
		String s2_1=s2.substring(0,t2);
		String s2_2=s2.substring(t2+1,s2.length());
		
		String [] kk=s1_1.split("/");
		String [] jj=s2_1.split("-");
		for(int i=0;i<kk.length;i++){
			a[i]=Integer.parseInt(kk[i]);
			b[i]=Integer.parseInt(jj[i]);
		}
		
		//int temp1=Integer.parseInt(kk[0])*365+Integer.parseInt(kk[1])
		
		
		String [] kk_1=s1_2.split(":");
		String [] jj_1=s2_2.split(":");
		
		for(int i=0;i<kk_1.length;i++){
			a[3+i]=Integer.parseInt(kk_1[i]);
			b[3+i]=Integer.parseInt(jj_1[i]);
		}
		
		
		

		int cha=seconds(a,b);
		return cha;
	}
	
	
	
	public  int seconds(int[] a,int[] b){
		int result=0;
		
		
		//闰年或非闰年
		int[] t_1={31,28 ,31 ,30, 31 ,30 ,31 ,31 ,30, 31, 30 ,31};
		int[] t_2={31,29 ,31 ,30, 31 ,30 ,31 ,31 ,30, 31, 30 ,31};
		int[] temp_1=new int[12];temp_1[0]=0;
		int[] temp_2=new int[12];temp_2[0]=0;
		for(int i=1;i<12;i++)
			temp_1[i]=temp_1[i-1]+t_1[i-1];
		for(int i=1;i<12;i++)
			temp_2[i]=temp_2[i-1]+t_2[i-1];
		
		int days_1=0;
		int days_2=0;
		//年份从1980开始，闰年366天，非闰年365天
		//a的天数
		for(int i=1980;i<a[0];i++)
		{
			if(i%4==0)
				days_1+=366;
			else
				days_1+=365;
		}
		if(a[0]%4==0)
			days_1+=temp_2[a[1]];
		else
			days_1+=temp_1[a[1]];
		days_1+=a[2];
		
		
		//b的天数
		for(int i=1980;i<b[0];i++)
		{
			if(i%4==0)
				days_2+=366;
			else
				days_2+=365;
		}
		if(b[0]%4==0)
			days_2+=temp_2[b[1]];
		else
			days_2+=temp_1[b[1]];
		days_2+=b[2];
		
		int sec_1=3600*a[3]+60*a[4]+a[5];
		int sec_2=3600*b[3]+60*b[4]+b[5];
		
		sec_1+=days_1*3600*24;
		sec_2+=days_2*3600*24;
		
		int really=sec_2-sec_1;
		result=Math.abs(sec_2-sec_1);
		
		if(MainPage.max<result)
			MainPage.max=result;
		if(MainPage.min>result)
			MainPage.min=result;
		//System.out.println("相差"+result);
		String temp=null;
		if(really>0)
			temp="-"+result;
		else if(really==0)
			temp=""+result;
		else
			temp="+"+result;
		try{
		MainPage.table[group].setValueAt(temp,id, 2);
		//MainPage.table[group].updateUI();
		}catch(Exception e){System.out.println("表单错误："+e.getMessage());}
		//System.out.println("groupid="+group+" id="+id);
		return result;
	}
	
	
	
	public  String get_standtime()
	{

		String test=MainPage.found_time;
		while(test==null)
		{
			try {sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
			test=MainPage.found_time;
		}
		
		String standtime=null;
		String tmp=null;
		do{
		tmp=MainPage.found_time;
		String[] temp;
		if(tmp!=null)
		{
			temp=tmp.split("\n");
			if(temp.length==3)
				standtime=temp[1]+"\n"+temp[2];
			else{
				get_standtime();
				}
		}
		}while(standtime==null);
		
		//System.out.println("北京时间"+standtime);
		
		/*
		try{
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8")); // 时区设置  
	       URL url;
		url = new URL("http://www.bjtime.cn");
		URLConnection uc;
		uc = url.openConnection();
		uc.connect(); //发出连接  
		long ld=uc.getDate(); //取得网站日期时间（时间戳）  
	    Date date=new Date(ld); //转换为标准时间对象  
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    String tmp1=formatter.format(date);
	    String tmp2=date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
	    standtime=tmp1+"\n"+tmp2;}catch (Exception e) {e.printStackTrace();}
	    */
		//System.out.println("北京时间"+standtime);
	    return standtime;
	}
	
	
	public void setid(int a,int b){
		group=a;
		id=b;
		System.out.println("groupid="+group+" id="+id);
	}
	
	public int get_group(){
		return group;
	}
	public int get_id(){
		return id;
	}
	
	
	public void warn(){
		try{
		for(int i=0;i<MainPage.secondsGap/2000;i++){
			jta.setBackground(Color.WHITE);
			Thread.sleep(500);
			jta.setBackground(Color.RED);
			Thread.sleep(500);}
	}catch (InterruptedException e) {e.printStackTrace();}
		}
}
