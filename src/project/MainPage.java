package project;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.plaf.*;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainPage {

	private JFrame frame;
	public static JPanel[][] machine=new JPanel[4][10];
	public static TimeThread[][] thread=new TimeThread[4][10];
	public static int[] used={0,0,0,0};
	public static JTable[] table=new JTable[4];
	public static String[] path={"d://clock//cfg//cfg_0.txt","d://clock//cfg//cfg_1.txt","d://clock//cfg//cfg_2.txt","d://clock//cfg//cfg_3.txt"};
	public static String stand_path="d://clock//cfg//stand.txt";
	public static String [][] info=new String[4][10];
	public static JLabel[][] time_lable=new JLabel[4][10];
	public static JTextArea textArea=null;
	public static JTextArea global_status_pane;
	public static int max=0;
	public static int min=3600;
	public static int error=0;
	public static boolean[][] has_connect=new boolean[4][10];
	public static String found_time=null;
	public static int secondsGap=5*1000;
	public static Runtime runtime=Runtime.getRuntime();
	public static int[][] divGap=new int[4][10];
	private JTextField setText;
	private JPanel setPanel;
	//private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPage window = new MainPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainPage() {
		for(int i=0;i<4;i++){
			for(int j=0;j<10;j++){
				has_connect[i][j]=false;
				divGap[i][j]=300;
			}
		}
		for(int i=0;i<4;i++)
			readTxtFile(path[i],i);
		for(int i=0;i<4;i++)
			used[i]=info[i].length;
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}catch(Exception e){System.out.println(e.getMessage());}
		initialize();
		//readTxtFile(path);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("时钟监控平台");
		frame.setBounds(100, 100, 1197, 743);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){ 
				try {
					Runtime.getRuntime().exec("net use * /del /y");
				} catch (IOException e1) {e1.printStackTrace();}
				System.exit(0); 
				} 
		});
		frame.getContentPane().setLayout(null);
		
		
		JTabbedPane stand_pane = new JTabbedPane(JTabbedPane.TOP);
		stand_pane.setBounds(39, 22, 207, 148);
		frame.getContentPane().add(stand_pane);
		
		JPanel standtime_panel = new JPanel();
		stand_pane.addTab("标准时间", null, standtime_panel, null);
		standtime_panel.setLayout(null);
		
		textArea = new JTextArea(){{this.setOpaque(false);this.setBackground(Color.cyan);}};
		textArea.setLineWrap(true);
		textArea.setBounds(0, 0, 202, 119);
		
		
		JTabbedPane global_info = new JTabbedPane(JTabbedPane.TOP);
		global_info.setBounds(298, 22, 281, 148);
		frame.getContentPane().add(global_info);
		
		JPanel gloabl_status = new JPanel();
		global_info.addTab("全局统计信息", null, gloabl_status, null);
		gloabl_status.setLayout(null);
		
		global_status_pane = new JTextArea();
		global_status_pane.setBounds(0, 0, 276, 119);
		//global_status_pane.setText("abcd");
		gloabl_status.add(global_status_pane);
		
		
		standard_time st=new standard_time(textArea);
		standtime_panel.add(textArea);
		st.start();

		
		JTabbedPane local_status = new JTabbedPane(JTabbedPane.TOP);
		local_status.setBounds(657, 22, 321, 148);
		frame.getContentPane().add(local_status);
		
		
		String[] fenlei={"消息","文件","农行","其他"};
		JScrollPane[] scrollPane=new JScrollPane[4];
		for(int i=0;i<4;i++)
		{
			scrollPane[i]=new JScrollPane();
			String[] headers = { "主机名", "ip", "时间差","阀值" };
			Object[][] cellData =  null;
			DefaultTableModel model = new DefaultTableModel(cellData, headers) {
				  public boolean isCellEditable(int row, int column) {
				    return false;
				  }
				};
			table[i]=new JTable(model);
			//JTable.fitTableColumns(table[i]);
			scrollPane[i].setViewportView(table[i]);
			local_status.addTab(fenlei[i], scrollPane[i]);
		}
		
		
		
		
		panel_info p1=new panel_info(0,info[0]);
		p1.multi_mac.setBounds(10, 57, 520, 190);
		p1.setBounds(39, 180, 540, 257);
		p1.setname("FDEP-消息");
		p1.setBorder(BorderFactory.createLineBorder(Color.gray));
		frame.getContentPane().add(p1);
		
		
		panel_info p2=new panel_info(1,info[1]);
		p2.multi_mac.setBounds(10, 57, 520, 181);
		p2.setBounds(39, 447, 540, 248);
		p2.setname("FDEP-文件");
		p2.setBorder(BorderFactory.createLineBorder(Color.gray));
		frame.getContentPane().add(p2);
		
		panel_info p3 = new panel_info(2, info[2]);
		p3.multi_mac.setBounds(10, 57, 520, 190);
		p3.setname("农行前置");
		p3.setBorder(BorderFactory.createLineBorder(Color.gray));
		p3.setBounds(586, 180, 540, 257);
		frame.getContentPane().add(p3);
		
		panel_info p4 = new panel_info(3, info[3]);
		p4.multi_mac.setBounds(10, 57, 520, 181);
		p4.setname("其他");
		p4.setBorder(BorderFactory.createLineBorder(Color.gray));
		p4.setBounds(586, 447, 543, 248);
		frame.getContentPane().add(p4);
		
		JButton button = new JButton("\u91CD\u7F6E");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
		});
		button.setBounds(988, 59, 57, 29);
		frame.getContentPane().add(button);
		
		JButton button_1 = new JButton("设置");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setPanel.setVisible(true);
			}
		});
		button_1.setBounds(1089, 59, 57, 29);
		frame.getContentPane().add(button_1);
		
		setPanel = new JPanel();
		setPanel.setBounds(981, 94, 176, 61);
		frame.getContentPane().add(setPanel);
		setPanel.setVisible(false);
		setPanel.setLayout(null);
		
		setText = new JTextField();
		setText.setToolTipText("\u8BBE\u7F6E\u53D6\u65F6\u95F4\u95F4\u9694");
		setText.setBounds(10, 22, 70, 29);
		setPanel.add(setText);
		setText.setColumns(10);
		
		JLabel label = new JLabel("\u79D2");
		label.setBounds(90, 25, 26, 22);
		setPanel.add(label);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setGap();
			}
		});
		btnOk.setBounds(114, 23, 53, 26);
		setPanel.add(btnOk);
		
		
		
		
		
		

	}
	
	public static void readTxtFile(String filePath,int type){
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
                       //System.out.println(lineTxt);
                       data.add(lineTxt);
                    }
                    read.close();
                    //String[] result=data.toArray(new String[]{});
                    info[type]=data.toArray(new String[]{});
                    for(String str:info[type])
                    	System.out.println(str);
                    //System.out.println(info[type].length);
        }else{
        	File d = new File(filePath);
            d.createNewFile();
            readTxtFile(filePath,type);
            //System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
     
    }
	
	
	public void windowClosing(WindowEvent e){ 
		try {
			Runtime.getRuntime().exec("net use * /del /y");
		} catch (IOException e1) {e1.printStackTrace();}
		System.exit(0); 
		}
	
	public void reset(){
		secondsGap=300*1000;
		for(int i=0;i<4;i++){
			for(int j=0;j<10;j++)
				divGap[i][j]=300;
		}
	}
	
	public void setGap(){
		String set=setText.getText().trim();
		int newGap=Integer.parseInt(set);
		secondsGap=newGap*1000;
		setPanel.setVisible(false);
	}
	
	
}
