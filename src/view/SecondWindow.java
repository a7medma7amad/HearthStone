package view;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class SecondWindow extends JFrame {
	   
    private JFrame frame2;
    private JButton CurrentHero;
    private JButton OpponentHero;
    private JPanel CurrentHand;
    private JPanel OpponentHand;
    private JPanel CurrentField;
    private JPanel OpponentField;
    private JPanel WestPanel;
    private JPanel EastPanel;
    private JButton CurrentDeck;  
    private JButton OpponentDeck;
    private JButton BurnedCard;
    private JButton BurnedCard2;
    private JButton useHero1Power;
    private JButton useHero2Power;
    private JButton endPlayer1Turn;
    private JButton endPlayer2Turn;
 
    
    public SecondWindow() {
		frame2 = new JFrame("HearthStone");
		frame2.getContentPane().setLayout(null);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setSize(1366, 720);
		frame2.setVisible(false);
		
		CurrentHand = new JPanel();
		//CardsPlayer1.setBounds(400,520,800,200);
		//CardsPlayer1.setPreferredSize(new Dimension(800, 200));
		CurrentHand.setLayout(new GridLayout(1, 10));
		CurrentHand.setBackground(Color.BLUE);
		
		OpponentHand = new JPanel();
		//CardsPlayer2.setBounds(400,0,800,200);
		//CardsPlayer2.setPreferredSize(new Dimension(800, 200));
		OpponentHand.setLayout(new GridLayout(1, 10));
		OpponentHand.setBackground(Color.black);
		
		CurrentField = new JPanel();
		//Field.setPreferredSize(new Dimension(800, 200));
		CurrentField.setLayout(new GridLayout(1, 7));
		CurrentField.setBackground(Color.CYAN);
		
		OpponentField = new JPanel();
		//Field.setPreferredSize(new Dimension(800, 200));
		OpponentField.setLayout(new GridLayout(1, 7));
		OpponentField.setBackground(Color.PINK);
		
		WestPanel = new JPanel();
		WestPanel.setLayout(new GridLayout(0, 1));
		//WestPanel.setBounds(0,0,400,frame.getHeight());
		//WestPanel.setPreferredSize(new Dimension(200, frame.getHeight()));
		WestPanel.setBackground(Color.magenta);
		
		EastPanel = new JPanel();
		EastPanel.setLayout(null);
		//WestPanel.setBounds(1400,0,400,frame.getHeight());
		//EastPanel.setPreferredSize(new Dimension(200, frame.getHeight()));
		EastPanel.setBackground(Color.green);
		
		
		
		
		//WestPanel.setLocation(0,0);
		//WestPanel.setSize(233, 720);
		EastPanel.setLocation(1133,0);
		EastPanel.setSize(233, 720);
		CurrentHand.setLocation(0, 480);
		CurrentHand.setSize(1133,240);
		OpponentField.setLocation(0,0);
		OpponentField.setSize(1333, 240);
		CurrentField.setLocation(0,240);
		CurrentField.setSize(1133, 240);
		
		CurrentHero = new JButton();
		CurrentHero.setBackground(Color.GREEN);
		CurrentHero.setText("Current Hero");
		
		BurnedCard = new JButton();
		BurnedCard.setText("Burned Card");
		CurrentDeck = new JButton();
		CurrentDeck.setText("Current Deck");
		endPlayer1Turn = new JButton("End Player1 Turn");
		useHero1Power = new JButton("use current hero Power");
		
		OpponentHero = new JButton();
		OpponentHero.setBackground(Color.GREEN);
		OpponentHero.setText("Opponent Hero");
		BurnedCard2 = new JButton();
		BurnedCard2.setText("Burned Card");
		OpponentDeck = new JButton();
		OpponentDeck.setText("OpponentDeck");
		endPlayer2Turn = new JButton("End Player2 Turn");
		useHero2Power = new JButton("use hero2 Power");
		
		/*WestPanel.add(endPlayer2Turn);
		WestPanel.add(useHero2Power);
		WestPanel.add(Deck2);
		WestPanel.add(BurnedCard1);
		WestPanel.add(CurrentHero);*/
		
		OpponentHero.setSize(233, 240);
		OpponentHero.setLocation(0, 0);
		//OpponentHero.setBackground(Color.BLUE);
		OpponentDeck.setSize(233,40);
		OpponentDeck.setLocation(0,240 );
		BurnedCard.setSize(233,80);
		BurnedCard.setLocation(0,280 );
		CurrentDeck.setSize(233,40);
		CurrentDeck.setLocation(0,360 );
		useHero1Power.setSize(233,40);
		useHero1Power.setLocation(0, 400);
		endPlayer1Turn.setSize(233,40);
		endPlayer1Turn.setLocation(0, 440);
		CurrentHero.setSize(233, 240);
		CurrentHero.setLocation(0, 480);
		EastPanel.add(OpponentHero);
		EastPanel.add(OpponentDeck);
		EastPanel.add(BurnedCard);	
		EastPanel.add(useHero1Power);	
		EastPanel.add(CurrentHero);
		EastPanel.add(CurrentDeck);
		EastPanel.add(endPlayer1Turn);
		frame2.add(EastPanel);
		frame2.add(OpponentField);
		
		//frame2.add(WestPanel);
		frame2.add(CurrentHand);
		//frame2.add(OpponentHand);
		frame2.add(CurrentField);
		
		
		

		frame2.revalidate();
		frame2.repaint();

	}
        

    
    public JButton getCurrentHero() {
		return CurrentHero;
	}



	public JButton getOpponentHero() {
		return OpponentHero;
	}



	public JPanel getWestPanel() {
		return WestPanel;
	}



	public JPanel getEastPanel() {
		return EastPanel;
	}



	public JButton getUseHero1Power() {
		return useHero1Power;
	}



	public JButton getUseHero2Power() {
		return useHero2Power;
	}



	public JButton getEndPlayer2Turn() {
		return endPlayer2Turn;
	}



	public JButton getEndPlayer1Turn() {
		return endPlayer1Turn;
	}






	public JFrame getFrame2() {
		return frame2;
	}



	public JButton getHero1() {
		return CurrentHero;
	}



	public JButton getHero2() {
		return OpponentHero;
	}



	public JPanel getCardsPlayer1() {
		return CurrentHand;
	}



	public JPanel getCardsPlayer2() {
		return OpponentHand;
	}



	public JPanel getField1() {
		return CurrentField;
	}



	public JPanel getField2() {
		return OpponentField;
	}



	public JButton getDeck1() {
		return CurrentDeck;
	}



	public JButton getDeck2() {
		return OpponentDeck;
	}



	public JButton getBurnedCard() {
		return BurnedCard;
	}



	



	public void setText(String text){
        
    }

    public JPanel getCurrentHand(){
    	return this.CurrentHand;
    }
    public JPanel getOpponentHand(){
    	return this.OpponentHand;
    }
    public JPanel getCurrentField(){
    	return this.CurrentField;
    }
    public JPanel getOpponentField(){
    	return this.OpponentField;
    }
}
