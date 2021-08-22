package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public class FirstWindow extends JFrame {
		public JFrame getFrame1() {
		return frame1;
	}

	public JButton getHero1Hunter() {
		return Hero1Hunter;
	}
	public JButton getNextButton() {
		return NextButton;
	}

	public JButton getHero1Mage() {
		return Hero1Mage;
	}

	public JButton getHero1Priest() {
		return Hero1Priest;
	}

	public JButton getHero1Paladin() {
		return Hero1Paladin;
	}

	public JButton getHero1Warlock() {
		return Hero1Warlock;
	}

	public JButton getHero2Hunter() {
		return Hero2Hunter;
	}

	public JButton getHero2Mage() {
		return Hero2Mage;
	}

	public JButton getHero2Priest() {
		return Hero2Priest;
	}

	public JButton getHero2Paladin() {
		return Hero2Paladin;
	}

	public JButton getHero2Warlock() {
		return Hero2Warlock;
	}

		private JFrame frame1;
		private JButton Hero1Hunter;
		private JButton Hero1Mage;
		private JButton Hero1Priest;
		private JButton Hero1Paladin;
		private JButton Hero1Warlock;
		private JButton Hero2Hunter;
		private JButton Hero2Mage;
		private JButton Hero2Priest;
		private JButton Hero2Paladin;
		private JButton Hero2Warlock;
		private JButton NextButton;
	public FirstWindow() {
		frame1 = new JFrame("HearthStone");
		frame1.getContentPane().setLayout(null);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setSize(1366, 720);
		frame1.setVisible(true);
		
		JPanel  Hero1Panel = new JPanel();
		
		//Hero1Panel.setBackground(Color.BLUE);
		
		JLabel Hero1Label = new JLabel();
		Hero1Label.setText("Select Hero 1");
		Hero1Panel.add(Hero1Label);
		Hero1Warlock = new JButton("Warlock");
		Hero1Panel.add(Hero1Warlock);
		Hero1Hunter = new JButton("Hunter");
		Hero1Panel.add(Hero1Hunter);
		Hero1Priest = new JButton("Priest");
		Hero1Panel.add(Hero1Priest);
		Hero1Mage = new JButton("Mage");
		Hero1Panel.add(Hero1Mage);
		Hero1Paladin = new JButton("Paladin");
		Hero1Panel.add(Hero1Paladin);
		
		JPanel  Hero2Panel = new JPanel();		
		//Hero2Panel.setBackground(Color.RED);
		
		JLabel Hero2Label = new JLabel();
		Hero2Label.setText("Select Hero 2");
		Hero2Panel.add(Hero2Label);
		Hero2Warlock = new JButton("Warlock");
		Hero2Panel.add(Hero2Warlock);
		Hero2Hunter = new JButton("Hunter");
		Hero2Panel.add(Hero2Hunter);
		Hero2Priest = new JButton("Priest");
		Hero2Panel.add(Hero2Priest);
		Hero2Mage = new JButton("Mage");
		Hero2Panel.add(Hero2Mage);
		Hero2Paladin = new JButton("Paladin");
		Hero2Panel.add(Hero2Paladin);
		Hero2Panel.setLocation(0, 0);
		Hero1Panel.setSize(1366, 300);
		Hero2Panel.setSize(1366, 300);
		Hero1Panel.setLocation(0, 300);
		
		
		JPanel NextPanel = new JPanel();
		
		NextButton = new JButton("Next");
		NextPanel.setLocation(0, 600);
		NextPanel.setSize(1366,180);
		NextPanel.add(NextButton);
		frame1.add(Hero2Panel);
		frame1.add(NextPanel);
		frame1.add(Hero1Panel);
		
		
		frame1.revalidate();
		frame1.repaint();
		
	}
	
	
	public void setButtonsListener(ActionListener x) {
		Hero1Hunter.addActionListener(x);
		Hero1Mage.addActionListener(x);
		Hero1Priest.addActionListener(x);
		Hero1Paladin.addActionListener(x);
		Hero1Warlock.addActionListener(x);
		Hero2Hunter.addActionListener(x);
		Hero2Mage.addActionListener(x);
		Hero2Priest.addActionListener(x);
		Hero2Paladin.addActionListener(x);
		Hero2Warlock.addActionListener(x);
		NextButton.addActionListener(x);
	}
}
