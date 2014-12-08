package project;

import java.awt.Font;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.swing.JTextArea;

public class standard_time extends Thread{

	public JTextArea jta=null;
	public String time;
	public String[] information=null;
	
	public standard_time(JTextArea jt){
		jta=jt;
	}
	
	
	 public void run()
	 {
		 get_info(MainPage.stand_path);
		 //gettime_thread gtt=new gettime_thread()
		 get_stand_time(information);
	 }
	 
	 public void test(){
		 while(true){
				try {
					TimeZone.setDefault(TimeZone.getTimeZone("GMT+8")); // 时区设置  
				       URL url;
					url = new URL("http://www.bjtime.cn");
					URLConnection uc;
					try {
						uc = url.openConnection();
						uc.connect(); //发出连接  
						long ld=uc.getDate(); //取得网站日期时间（时间戳）  
					    Date date=new Date(ld); //转换为标准时间对象  
					    //System.out.println(date.getHours()+"时"+date.getMinutes()+"分"+date.getSeconds()+"秒"); 
					    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					    String tmp1=formatter.format(date);
					    String tmp=date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
					    time="北京时间\n"+tmp1+"\n"+tmp;
					    Font x = new Font("Serif",0,20);
					    jta.setFont(x);
					    jta.setText(time);
					    //jta.paintImmediately(jta.getBounds());
					    
					    
					    int total=0;
						for(int i=0;i<4;i++)
							total+=MainPage.used[i];
						int error=MainPage.error;
						int normal=total-error;
						String global_status="全局状态：\n"+"共监控"+total+"台机器："+normal+"台正常;"+error+"台异常\n"+"最大偏差:"+MainPage.max+"秒;最小偏差:"+MainPage.min+"秒";
						//System.out.println(global_status);
						Font x1 = new Font("Serif",0,18);
						MainPage.global_status_pane.setFont(x1);
						MainPage.global_status_pane.setText(global_status);
						if(error>0&&MainPage.sound)
							Toolkit.getDefaultToolkit().beep();
						//MainPage.global_status_pane.paintImmediately(MainPage.global_status_pane.getBounds());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();		
					}//生成连接对象  
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//取得资源对象  
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
	 }
	 
	 
	 public void get_info(String filePath)
	 {
		 try {
        	 List<String> data = new ArrayList<>();
                String encoding="GBK";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
                       data.add(lineTxt);
                    }
                    read.close();
                    String[] temp=data.toArray(new String[]{});
                    information=temp[0].split(" ");
                    MainPage.secondsGap=Integer.parseInt(information[5].trim())*1000;
        }else{
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {System.out.println("读取文件内容出错");e.printStackTrace();}
	 }
	 
	 
	 public void get_stand_time(String[] info){
		 
		 Font x = new Font("Serif",0,20);
		 jta.setFont(x);
		 win_time wt=new win_time();
		 linux_time lt=new linux_time();
		 if(info[3].equals("windows")){
				while(true){
				String tmp=wt.get_win_time(info);
				int n1=tmp.indexOf("\n");
				String tmp_1=tmp.substring(0,n1);
				String tmp_2=tmp.substring(n1+1, tmp.length());
				//String result="<html><body>"+info[0]+"<br>"+tmp_1+"<br>"+tmp_2+"</body></html>";
				String result="基准时间\n"+tmp_1+"\n"+tmp_2;
				result=result.replaceAll("/", "-");
				MainPage.found_time=result;
				jta.setText(result);
				another();
				try {sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
				}
			}
			else{
				while(true){
				try{
				String tmp=lt.get_linux_time(info);
				int n1=tmp.indexOf("\n");
				String tmp_1=tmp.substring(0,n1);
				String tmp_2=tmp.substring(n1+1, tmp.length());
				//String result="<html><body>"+info[0]+"<br>"+tmp_1+"<br>"+tmp_2+"</body></html>";
				String result="基准时间\n"+tmp_1+"\n"+tmp_2;
				result=result.replaceAll("/", "-");
				MainPage.found_time=result;
				jta.setText(result);
				another();
				try {sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
				}catch(IOException e){System.out.println("abc");System.out.println(e.getMessage());}
				}
			}
	 }
	 
	 public void another(){
		 int total=0;
			for(int i=0;i<4;i++)
				total+=MainPage.used[i];
			int error=MainPage.error;
			if(error>0&&MainPage.sound)
				Toolkit.getDefaultToolkit().beep();
			int normal=total-error;
			String global_status="全局状态：\n"+"共监控"+total+"台机器："+normal+"台正常;"+error+"台异常\n"+"最大偏差:"+MainPage.max+"秒;最小偏差:"+MainPage.min+"秒"+"\n默认"+MainPage.secondsGap/1000+"秒读取一次时间";
			//System.out.println(global_status);
			Font x1 = new Font("Serif",0,18);
			MainPage.global_status_pane.setFont(x1);
			MainPage.global_status_pane.setText(global_status);
	 }
}
