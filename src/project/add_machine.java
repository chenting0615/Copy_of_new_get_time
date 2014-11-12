package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;



public class add_machine extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JPasswordField passwordField;
	private JTextField yuzhi;

	
	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		try {
			add_machine dialog = new add_machine();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/

	/**
	 * Create the dialog.
	 */
	public add_machine(int i) {
		
		setBounds(100, 100, 435, 352);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblIp = new JLabel("ip");
		lblIp.setBounds(100, 25, 30, 26);
		contentPanel.add(lblIp);
		
		textField = new JTextField();
		textField.setBounds(152, 26, 143, 26);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("\u673A\u5668\u540D");
		label.setBounds(76, 80, 54, 23);
		contentPanel.add(label);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(152, 77, 143, 26);
		contentPanel.add(textField_1);
		
		JLabel label_1 = new JLabel("\u7C7B\u522B");
		label_1.setBounds(47, 124, 43, 23);
		contentPanel.add(label_1);
		
		String type[]={"windows","linux"};
		JComboBox comboBox = new JComboBox(type);
		comboBox.setBounds(86, 122, 82, 26);
		contentPanel.add(comboBox);
		
		textField_2 = new JTextField();
		textField_2.setBounds(100, 218, 89, 26);
		contentPanel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel label_2 = new JLabel("\u7CFB\u7EDF\u767B\u5F55\u4FE1\u606F");
		label_2.setBounds(190, 174, 133, 26);
		contentPanel.add(label_2);
		
		JLabel label_3 = new JLabel("\u7528\u6237\u540D");
		label_3.setBounds(47, 220, 43, 21);
		contentPanel.add(label_3);
		
		JLabel label_4 = new JLabel("\u5BC6\u7801");
		label_4.setBounds(236, 223, 43, 21);
		contentPanel.add(label_4);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(276, 218, 95, 26);
		contentPanel.add(passwordField);
		
		JLabel label_5 = new JLabel("\u62A5\u8B66\u9608\u503C");
		label_5.setBounds(207, 120, 59, 30);
		contentPanel.add(label_5);
		
		yuzhi = new JTextField();
		yuzhi.setBounds(286, 121, 66, 26);
		contentPanel.add(yuzhi);
		yuzhi.setColumns(10);
		
		JLabel label_6 = new JLabel("\u79D2");
		label_6.setBounds(362, 124, 47, 19);
		contentPanel.add(label_6);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						String[] info={textField.getText(),textField_2.getText(),passwordField.getText(),comboBox.getSelectedItem().toString(),textField_1.getText(),yuzhi.getText().trim()};
						//JTextArea jta=new JTextArea(){{this.setOpaque(false);}};
						//JLabel time=new JLabel(){{this.setOpaque(false);}};
						MainPage.divGap[i][MainPage.used[i]]=Integer.parseInt(info[5]);
						MainPage.time_lable[i][MainPage.used[i]]=new JLabel();
						//jta.setBackground(Color.);
						MainPage.time_lable[i][MainPage.used[i]].setBounds(12,15, 91, 59);
						MainPage.time_lable[i][MainPage.used[i]].setForeground(Color.DARK_GRAY);
						MainPage.machine[i][MainPage.used[i]].setVisible(true);
						MainPage.machine[i][MainPage.used[i]].add(MainPage.time_lable[i][MainPage.used[i]]);
						gettime_thread gtt=new gettime_thread(MainPage.time_lable[i][MainPage.used[i]],info);
						//添加列表行
						DefaultTableModel tableModel=null;
						if(MainPage.table[i]!=null)
							tableModel = (DefaultTableModel) MainPage.table[i].getModel();
						if(tableModel!=null)
							tableModel.addRow(new Object[]{info[4], info[0], "0",info[5]});
						
						
						gtt.setid(i, MainPage.used[i]);
						gtt.start();
						MainPage.used[i]++;
						
						
						//写入配置文件
						write_file(MainPage.path[i],info);
						
						/*
						//jta.setOpaque(false);
						main_page_1.machine_group[i].add(jta);
						//panel.add(jta);
						gettime_thread gtt=new gettime_thread(jta,info);
						gtt.start();
						//jiankong jk=new jiankong(jta,info);
						//jk.start();
						//add_machine_thread amt=new add_machine_thread(panel,info);
						//amt.start();
						 * */
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	
	
	public void write_file(String file,String[] ss)
	{
		String content="";
		for(int i=0;i<ss.length;i++)
			content+=ss[i]+" ";
		content+="\n";
		
		BufferedWriter out = null;
		   try {
		    out = new BufferedWriter(new OutputStreamWriter(
		      new FileOutputStream(file, true)));
		    out.write(content);
		   } catch (Exception e) {
		    e.printStackTrace();
		   } finally {
		    try {
		     out.close();
		    } catch (IOException e) {
		     e.printStackTrace();
		    }
		   }
	}
	
	
}
