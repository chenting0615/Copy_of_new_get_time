package project;

import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.metal.MetalIconFactory;

public class sigle_mac extends JPanel {

	public JLabel add_label;
	public ImageIcon imageIcon=new ImageIcon("D://used.jpg");
	/**
	 * Create the panel.
	 */
	public sigle_mac() {

		this.setOpaque(false);
		this.paintComponent(getGraphics());
		Icon warnIcon = MetalIconFactory.getTreeComputerIcon();
		add_label=new JLabel(warnIcon);
		add_label.setBounds(3,0,90,70);
	}

	//public void init()
	
	public void paintComponent(Graphics g) {
	    g.drawImage(imageIcon.getImage(), 0, 0, this);
	    super.paintComponents(g);
	   }
	
	public static void main(String[] args){
		sigle_mac sgm=new sigle_mac();
		sgm.setVisible(true);
	}
}
