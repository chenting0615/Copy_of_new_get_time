package project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class panel_info extends JPanel {
	
	public int group_id;
	public String[] machine_info;
	public final int rows=2;
	public final int cols=5;
	public JPanel multi_mac = new JPanel();
	public ImageIcon imageIcon=new ImageIcon("D://clock//image//used.jpg");
	public JLabel label=null;

	/**
	 * Create the panel.
	 */
	public panel_info() {
		
		group_id=0;
		machine_info=null;
		init();
	}
	
	public panel_info(int id,String[] info)
	{
		group_id=id;
		machine_info=info;
		init();
	}
	
	
	//面板初始化
	public void init()
	{
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 443, 37);
		add(panel);
		panel.setLayout(null);
		
		label = new JLabel("群组信息");
		label.setBounds(55, 6, 127, 23);
		panel.add(label);
		
		JButton add_button = new JButton("\u65B0\u589E");
		//add_button.setActionCommand(Integer.toString(group_id));
		add_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//int num=MainPage.used[group_id];
				add_machine addm=new add_machine(group_id);
				addm.setVisible(true);
			}
		});
		add_button.setBounds(222, 6, 93, 23);
		panel.add(add_button);
		
		JButton del_button = new JButton("\u5220\u9664");
		del_button.setBounds(340, 6, 93, 23);
		panel.add(del_button);
		
		
		multi_mac.setBounds(10, 57, 547, 280);
		GridLayout layout=new GridLayout(rows,cols);
		layout.setHgap(10);
		layout.setVgap(10);
		multi_mac.setLayout(layout);
		add(multi_mac);
		init_panel();

	}
	//返回面板的ID
	public int get_id()
	{
		return group_id;
	}
	
	//返回机器群组信息
	public String[] get_info()
	{
		return machine_info;
	}

	//添加监控机器
	public void add_machine(String[] new_info)
	{
		
	}
	
	//面板初始化
	public void init_panel()
	{
		int num=rows*cols;
		int total=machine_info.length;
		for(int i=0;i<num;i++){
			MainPage.machine[group_id][i]=new JPanel(){{this.setOpaque(false);}
			   public void paintComponent(Graphics g) {
				    g.drawImage(imageIcon.getImage(), 0, 0, this);
				    super.paintComponents(g);
				   }};
			multi_mac.add(MainPage.machine[group_id][i]);
			MainPage.machine[group_id][i].setLayout(null);
			//if(i<MainPage.used[group_id])
			if(i<machine_info.length){
				try{
				MainPage.machine[group_id][i].setVisible(true);
				MainPage.time_lable[group_id][i]=new JLabel();
				//jta.setBackground(Color.);
				MainPage.time_lable[group_id][i].setBounds(12,15, 91, 59);
				MainPage.time_lable[group_id][i].setForeground(Color.DARK_GRAY);
				//MainPage.machine[i][MainPage.used[i]].setVisible(true);
				MainPage.machine[group_id][i].add(MainPage.time_lable[group_id][i]);
				String[] information=MainPage.info[group_id][i].split(" ");
				gettime_thread gtt=new gettime_thread(MainPage.time_lable[group_id][i],information);
				//System.out.println("groupid="+group_id+" id="+i);
				gtt.setid(group_id, i);
				gtt.start();}catch(Exception e){}
				}
			else
				MainPage.machine[group_id][i].setVisible(false);
			
			
			
			
			
			if(i<machine_info.length)
				addtable(machine_info[i]);
		}
		//table_content();
	}
	
	public void setname(String name)
	{
		label.setText(name+"群组信息");
	}
	
	public void addtable(String s)
	{
		DefaultTableModel tableModel=null;
		if(MainPage.table[group_id]!=null)
			tableModel = (DefaultTableModel) MainPage.table[group_id].getModel();
		//System.out.println(s);
		String[] ss=s.split(" ");
		if(tableModel!=null)
			tableModel.addRow(new Object[]{ss[4], ss[0], "0"});
	}
	
	/*
	public void table_content()
	{
		try{
		DefaultTableModel tableModel=null;
		if(MainPage.table[group_id]!=null)
			tableModel = (DefaultTableModel) MainPage.table[group_id].getModel();

		
		//tableModel.
		int lenth=machine_info.length;
		String[] ss=new String[lenth];//监控时间
		int[] chazhi=new int[lenth];
		String s=gettime_thread.get_standtime();
		System.out.println(s);
			
			for(int i=0;i<lenth;i++){
				ss[i]=new String();
				ss[i]=MainPage.time_lable[group_id][i].getText();
				chazhi[i]=gettime_thread.get_seconds(ss[i], s);
			}
			for(int i=0;i<lenth;i++){
				String tmp=MainPage.info[group_id][i];
				String[] t=tmp.split(" ");
				tableModel.addRow(new Object[]{t[4],t[0],Integer.toString(chazhi[i])});
			}
		}catch(Exception e){}
	}
	*/
	
}
