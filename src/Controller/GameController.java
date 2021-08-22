package Controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import engine.Game;
import engine.GameListener;
import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import model.cards.Card;
import model.cards.minions.Minion;
import model.cards.spells.AOESpell;
import model.cards.spells.FieldSpell;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.LeechingSpell;
import model.cards.spells.MinionTargetSpell;
import model.cards.spells.Spell;
import model.heroes.Hero;
import model.heroes.Hunter;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Priest;
import model.heroes.Warlock;
import view.FirstWindow;
import view.SecondWindow;

public class GameController implements GameListener {
	private Game model;
	private FirstWindow view1;
	private SecondWindow view2;
	private Hero Hero1;
	private Hero Hero2;
	private ArrayList<JButton> CurrentHand;
	private ArrayList<JButton> OpponentHand;
	private ArrayList<JButton> CurrentField;
	private ArrayList<JButton> OpponentField;
	private Minion Attacker;
	private Minion ToBeAttacked;
	private Spell toBeCasted;
	private boolean heroPowerEnabled;

	public GameController() {
		CurrentHand = new ArrayList<JButton>();
		OpponentHand = new ArrayList<JButton>();
		CurrentField = new ArrayList<JButton>();
		OpponentField = new ArrayList<JButton>();
		heroPowerEnabled=false;
		Attacker = null;
		Hero1 = null;
		Hero2 = null;
		view1 = new FirstWindow();
		view2 = new SecondWindow();
		JOptionPane.showMessageDialog(view1, "1 - To start the game , select Hero 1 & Hero 2 , then press next \r\n" + 
				"2-The lower half is fixed for the current hero \n"+
				"3-The screen is divided vertically into three parts ,the lower part is current player`s Hand,the middle part is current player`s fields,the upper part is opponent`s field \n"+
				"4-The right part contains the information of current hero in the lower half and and opponents hero in upper half ,\n there is information about remaining cards in each hero`s deck \n ,number of cards in opponents hand,and the information of the last burned card which is updated everytime a card is burned \n ,and buttons to use current heropower and end current player`s turn \n"+
				"5-The hero buttons are the buttons with green background where current hero`s button is on lwer right corner while opponent`s button is on upper right corner \n "+
				"6-For current player to play a minion click on the minion he wants from his hand,where he must have enough mana crystals to play this minion  \n"+
				"7- In order for current player to attack with a minion , First click on the minion you want to attack with from current field \n Then click on The Minion  you want to be attacked From  opponent’s field or click on opponent`s button to attack opponent`s hero\r\n" + 
				"8- If you want to cast a AoeSpell or FieldSpell click on it from current hand and it will be casted immediatly if you have enough mana crystals\n" + 
				"9-if you want to cast a spell MinonTargetSpell,LeechingSpell,HeroTargetSpell first click on the spell from current hand ,\n then click on the minion or hero you want to cast it on from either fields or from either hero buttons \n"+
				"10- To use the power of the current hero , Press UseCurrentHeroPower ,\n if the current hero is mage or priest then after you click use current hero power click on the target minion or hero, \n if the hero is Hunter,Warlock or paladin then the hero power will be used immediatly after you click useCurrenheroPower\r\n" + 
				"11- To end current player`s turn , Press EndCurrentHeroTurn to exchange the turn with your opponent");
		view1.setButtonsListener(new FirstWindowListener());
		//model.setListener(this);
		view1.revalidate();
		view1.repaint();
	}

	public void onGameOver() {
				
		JOptionPane.showMessageDialog(view2, "GameOver! Congratulation the winner is "+model.getCurrentHero().getName() );
		view2.getFrame2().setVisible(false);

	}

	public static void main(String[] args) {
		new GameController();
	}

	public class FirstWindowListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {
				if (e.getSource() == view1.getHero1Hunter())
					Hero1 = new Hunter();
				else if (e.getSource() == view1.getHero1Mage())
					Hero1 = new Mage();
				else if (e.getSource() == view1.getHero1Paladin())
					Hero1 = new Paladin();
				else if (e.getSource() == view1.getHero1Priest())
					Hero1 = new Priest();
				else if (e.getSource() == view1.getHero1Warlock())
					Hero1 = new Warlock();
				else if (e.getSource() == view1.getHero2Hunter())
					Hero2 = new Hunter();
				else if (e.getSource() == view1.getHero2Mage())
					Hero2 = new Mage();
				else if (e.getSource() == view1.getHero2Paladin())
					Hero2 = new Paladin();
				else if (e.getSource() == view1.getHero2Priest())
					Hero2 = new Priest();
				else if (e.getSource() == view1.getHero2Warlock())
					Hero2 = new Warlock();
				else if (Hero1 != null && Hero2 != null && e.getSource() == view1.getNextButton()) {
					model = new Game(Hero1, Hero2);
					view2.getOpponentHero().addActionListener(new OpponentHeroListener());
					view2.getEndPlayer1Turn().addActionListener(new endTurnListener());
					view2.getUseHero1Power().addActionListener(new useHeroPowerListener());
					view2.getCurrentHero().addActionListener(new CurrentHeroListener());
					update();
					view1.getFrame1().setVisible(false);
					view2.getFrame2().setVisible(true);
				}
			} catch (IOException e1) {
			} catch (CloneNotSupportedException e2) {
			} catch (FullHandException e3) {
				Card c = e3.getBurned();
				view2.getBurnedCard().setText("B.C: "+c.toString());
			}

		}
	}

	public class CurrentHandListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean enough = true;
			int index = CurrentHand.indexOf(e.getSource());
			Card c = model.getCurrentHero().getHand().get(index);
			try {
				model.validateManaCost(c);
			} catch (NotEnoughManaException e1) {
				enough = false;
				JOptionPane.showMessageDialog(view2, e1.getMessage());
			}
			if (c instanceof Minion && enough) {
				boolean thereIsSpace=true;
				try {
					model.validatePlayingMinion((Minion) c);
					model.getCurrentHero().playMinion((Minion) c);
				} catch (FullFieldException e1) {
					JOptionPane.showMessageDialog(view2, e1.getMessage());
				} catch (NotYourTurnException e2) {
					JOptionPane.showMessageDialog(view2, e2.getMessage());
				} catch (NotEnoughManaException e3) {
					JOptionPane.showMessageDialog(view2, e3.getMessage());
				}
			
				update();

			} else if (c instanceof Spell && enough) {
				toBeCasted = (Spell) c;
				Attacker = null;
				heroPowerEnabled=false;
				if (c instanceof FieldSpell) {
					boolean valid1 = true;
					try {

						model.getCurrentHero().castSpell((FieldSpell) toBeCasted);
					} catch (NotYourTurnException e1) {
						valid1 = false;
						JOptionPane.showMessageDialog(view2, e1.getMessage());
					} catch (NotEnoughManaException e2) {
						valid1 = false;
						JOptionPane.showMessageDialog(view2, e2.getMessage());
					}
					if (valid1) {
						JOptionPane.showMessageDialog(view2, toBeCasted.getName() + " Was Casted");
					}
				} else if (c instanceof AOESpell) {
					boolean valid2 = true;
					try {
						model.getCurrentHero().castSpell((AOESpell) toBeCasted, model.getOpponent().getField());
					} catch (NotYourTurnException e1) {
						valid2 = false;
						JOptionPane.showMessageDialog(view2, e1.getMessage());
					} catch (NotEnoughManaException e2) {
						valid2 = false;
						JOptionPane.showMessageDialog(view2, e2.getMessage());
					}
					if (valid2) {
						JOptionPane.showMessageDialog(view2, toBeCasted.getName() + " Was Casted");
					}
				}
				update();
			}
			update();
		}

	}

	public class OpponentHandListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	public class CurrentFieldListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int index = CurrentField.indexOf(e.getSource());
			Attacker = model.getCurrentHero().getField().get(index);
			
			 if(heroPowerEnabled && model.getCurrentHero() instanceof Priest) {
				boolean valid=true;
				try {
					((Priest)(model.getCurrentHero())).useHeroPower(Attacker);
				} catch (NotEnoughManaException e1) {
					valid=false;
					JOptionPane.showMessageDialog(view2, e1.getMessage());
				} catch (HeroPowerAlreadyUsedException e2) {
					valid=false;
					JOptionPane.showMessageDialog(view2, e2.getMessage());
				} catch (NotYourTurnException e3) {
					valid=false;
					JOptionPane.showMessageDialog(view2, e3.getMessage());
				} catch (FullHandException e4) {
					valid=false;
					JOptionPane.showMessageDialog(view2, e4.getMessage());
				} catch (FullFieldException e5) {
					valid=false;
					JOptionPane.showMessageDialog(view2, e5.getMessage());
				} catch (CloneNotSupportedException e6) {
					valid=false;
					JOptionPane.showMessageDialog(view2, e6.getMessage());
				}
				if(valid) {
					JOptionPane.showMessageDialog(view2, model.getCurrentHero().getName()+" used his hero power on "+Attacker.getName());
					update();
				}
				Attacker=null;
				heroPowerEnabled=false;
			}
			else if (toBeCasted != null) {
				boolean valid1 = true;
				if (toBeCasted instanceof LeechingSpell) {
					try {
						model.getCurrentHero().castSpell((LeechingSpell) toBeCasted, Attacker);
						
					} catch (NotYourTurnException e1) {
						valid1 = false;
						JOptionPane.showMessageDialog(view2, e1.getMessage());
					} catch (NotEnoughManaException e2) {
						valid1 = false;
						JOptionPane.showMessageDialog(view2, e2.getMessage());
					}
					if (valid1) {
						JOptionPane.showMessageDialog(view2,
								toBeCasted.getName() + " Was Casted on " + Attacker.getName());
					}

				} else if (toBeCasted instanceof MinionTargetSpell) {
					boolean valid2 = true;
					try {
						model.getCurrentHero().castSpell((MinionTargetSpell) toBeCasted, Attacker);
					} catch (NotYourTurnException e1) {
						valid2 = false;
						JOptionPane.showMessageDialog(view2, e1.getMessage());
					} catch (NotEnoughManaException e2) {
						valid2 = false;
						JOptionPane.showMessageDialog(view2, e2.getMessage());
					} catch (InvalidTargetException e3) {
						valid2 = false;
						JOptionPane.showMessageDialog(view2, e3.getMessage());
					}
					if (valid2) {
						JOptionPane.showMessageDialog(view2,
								toBeCasted.getName() + " Was Casted on " + Attacker.getName());
					}

				}

			
		}
			update();
			
		}
	}

	public class OpponentFieldListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int index = OpponentField.indexOf(e.getSource());
			ToBeAttacked = model.getOpponent().getField().get(index);
			boolean valid = true;

			if (Attacker != null) {

				try {
					model.getCurrentHero().attackWithMinion(Attacker, ToBeAttacked);
				} catch (CannotAttackException e1) {
					valid = false;
					JOptionPane.showMessageDialog(view2, e1.getMessage());
				} catch (NotYourTurnException e2) {
					valid = false;
					JOptionPane.showMessageDialog(view2, e2.getMessage());
				} catch (TauntBypassException e3) {
					valid = false;
					JOptionPane.showMessageDialog(view2, e3.getMessage());
				} catch (InvalidTargetException e4) {
					valid = false;
					JOptionPane.showMessageDialog(view2, e4.getMessage());
				} catch (NotSummonedException e5) {
					valid = false;
					JOptionPane.showMessageDialog(view2, e5.getMessage());
				}

				if (valid) {
					JOptionPane.showMessageDialog(view2, Attacker.getName() + " Attacked " + ToBeAttacked.getName());
				}
				Attacker=null;
				ToBeAttacked=null;
				update();
			} 
			else if (toBeCasted != null) {
				boolean valid1 = true;
				if (toBeCasted instanceof LeechingSpell) {
					try {
						model.getCurrentHero().castSpell((LeechingSpell) toBeCasted, ToBeAttacked);
						
					} catch (NotYourTurnException e1) {
						valid1 = false;
						JOptionPane.showMessageDialog(view2, e1.getMessage());
					} catch (NotEnoughManaException e2) {
						valid1 = false;
						JOptionPane.showMessageDialog(view2, e2.getMessage());
					}
					if (valid1) {
						JOptionPane.showMessageDialog(view2,
								toBeCasted.getName() + " Was Casted on " + ToBeAttacked.getName());
					}

				} else if (toBeCasted instanceof MinionTargetSpell) {
					boolean valid2 = true;
					try {
						model.getCurrentHero().castSpell((MinionTargetSpell) toBeCasted, ToBeAttacked);
					} catch (NotYourTurnException e1) {
						valid2 = false;
						JOptionPane.showMessageDialog(view2, e1.getMessage());
					} catch (NotEnoughManaException e2) {
						valid2 = false;
						JOptionPane.showMessageDialog(view2, e2.getMessage());
					} catch (InvalidTargetException e3) {
						valid2 = false;
						JOptionPane.showMessageDialog(view2, e3.getMessage());
					}
					if (valid2) {
						JOptionPane.showMessageDialog(view2,
								toBeCasted.getName() + " Was Casted on " + ToBeAttacked.getName());
					}

				}
				toBeCasted=null;
				
			}else if(heroPowerEnabled && model.getCurrentHero() instanceof Mage) {
				boolean valid3=true;
				try {
					((Mage)(model.getCurrentHero())).useHeroPower(ToBeAttacked);
				} catch (NotEnoughManaException e1) {
					valid3=false;
					JOptionPane.showMessageDialog(view2, e1.getMessage());
				} catch (HeroPowerAlreadyUsedException e2) {
					valid3=false;
					JOptionPane.showMessageDialog(view2, e2.getMessage());
				} catch (NotYourTurnException e3) {
					valid3=false;
					JOptionPane.showMessageDialog(view2, e3.getMessage());
				} catch (FullHandException e4) {
					valid3=false;
					JOptionPane.showMessageDialog(view2, e4.getMessage());
				} catch (FullFieldException e5) {
					valid3=false;
					JOptionPane.showMessageDialog(view2, e5.getMessage());
				} catch (CloneNotSupportedException e6) {
					valid3=false;
					JOptionPane.showMessageDialog(view2, e6.getMessage());
				}
				if(valid3) {
					JOptionPane.showMessageDialog(view2, model.getCurrentHero().getName()+" used his hero power on "+model.getOpponent().getName());
					update();
				}
				heroPowerEnabled=false;;
			}
			
			update();
		}
		
	}

	public class OpponentHeroListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			boolean valid = true;
			if (Attacker != null) {

				try {
					model.getCurrentHero().attackWithMinion(Attacker, model.getOpponent());
				} catch (CannotAttackException e1) {
					valid = false;
					JOptionPane.showMessageDialog(view2, e1.getMessage());
				} catch (NotYourTurnException e2) {
					valid = false;
					JOptionPane.showMessageDialog(view2, e2.getMessage());
				} catch (TauntBypassException e3) {
					valid = false;
					JOptionPane.showMessageDialog(view2, e3.getMessage());
				} catch (InvalidTargetException e4) {
					valid = false;
					JOptionPane.showMessageDialog(view2, e4.getMessage());
				} catch (NotSummonedException e5) {
					valid = false;
					JOptionPane.showMessageDialog(view2, e5.getMessage());
				}

				if (valid) {
					JOptionPane.showMessageDialog(view2,
							Attacker.getName() + " Attacked " + model.getOpponent().getName());
				}
				update();
			} else if (toBeCasted != null) {
				if (toBeCasted instanceof HeroTargetSpell) {
					boolean valid1 = true;
					try {
						model.getCurrentHero().castSpell((HeroTargetSpell) toBeCasted, model.getOpponent());
					} catch (NotYourTurnException e1) {
						valid1 = false;
						JOptionPane.showMessageDialog(view2, e1.getMessage());
					} catch (NotEnoughManaException e2) {
						valid1 = false;
						JOptionPane.showMessageDialog(view2, e2.getMessage());
					}
					if (valid1) {
						JOptionPane.showMessageDialog(view2,
								toBeCasted.getName() + " was Casted on " + model.getOpponent().getName());
						update();
					}
				}
				

			}
			else if(heroPowerEnabled && model.getCurrentHero() instanceof Mage) {
				boolean valid2=true;
				try {
					((Mage)(model.getCurrentHero())).useHeroPower(model.getOpponent());
				} catch (NotEnoughManaException e1) {
					valid2=false;
					JOptionPane.showMessageDialog(view2, e1.getMessage());
				} catch (HeroPowerAlreadyUsedException e2) {
					valid2=false;
					JOptionPane.showMessageDialog(view2, e2.getMessage());
				} catch (NotYourTurnException e3) {
					valid2=false;
					JOptionPane.showMessageDialog(view2, e3.getMessage());
				} catch (FullHandException e4) {
					valid2=false;
					JOptionPane.showMessageDialog(view2, e4.getMessage());
				} catch (FullFieldException e5) {
					valid2=false;
					JOptionPane.showMessageDialog(view2, e5.getMessage());
				} catch (CloneNotSupportedException e6) {
					valid2=false;
					JOptionPane.showMessageDialog(view2, e6.getMessage());
				}
				if(valid2) {
					JOptionPane.showMessageDialog(view2, model.getCurrentHero().getName()+" used his hero power on "+model.getOpponent().getName());
					update();
				}
				Attacker=null;
				heroPowerEnabled=false;
			}
			update();
		}

	}

	public class CurrentHeroListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(heroPowerEnabled && model.getCurrentHero() instanceof Priest) {
				boolean valid=true;
				try {
					((Priest)(model.getCurrentHero())).useHeroPower(model.getCurrentHero());
				} catch (NotEnoughManaException e1) {
					valid=false;
					JOptionPane.showMessageDialog(view2, e1.getMessage());
				} catch (HeroPowerAlreadyUsedException e2) {
					valid=false;
					JOptionPane.showMessageDialog(view2, e2.getMessage());
				} catch (NotYourTurnException e3) {
					valid=false;
					JOptionPane.showMessageDialog(view2, e3.getMessage());
				} catch (FullHandException e4) {
					valid=false;
					JOptionPane.showMessageDialog(view2, e4.getMessage());
				} catch (FullFieldException e5) {
					valid=false;
					JOptionPane.showMessageDialog(view2, e5.getMessage());
				} catch (CloneNotSupportedException e6) {
					valid=false;
					JOptionPane.showMessageDialog(view2, e6.getMessage());
				}
				if(valid) {
					JOptionPane.showMessageDialog(view2, model.getCurrentHero().getName()+" used his hero power on "+model.getCurrentHero().getName());
				}
				Attacker=null;
			}
		update();
		}
		
	}
	
	public class useHeroPowerListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(model.getCurrentHero() instanceof Hunter || model.getCurrentHero() instanceof Paladin || model.getCurrentHero() instanceof Warlock) {
				boolean valid=true;
				try {
					model.validateUsingHeroPower(model.getCurrentHero());
					model.getCurrentHero().useHeroPower();
				} catch (NotEnoughManaException e1) {
					valid=false;
					JOptionPane.showMessageDialog(view2, e1.getMessage());
				} catch (HeroPowerAlreadyUsedException e2) {
					valid=false;
					JOptionPane.showMessageDialog(view2, e2.getMessage());
				} catch (NotYourTurnException e3) {
					valid=false;
					JOptionPane.showMessageDialog(view2, e3.getMessage());
				} catch (FullHandException e4) {
					Card c = e4.getBurned();
					view2.getBurnedCard().setText("B.C: "+c.toString());
					valid=false;
					JOptionPane.showMessageDialog(view2, e4.getMessage());
				} catch (FullFieldException e5) {
					valid=false;
					JOptionPane.showMessageDialog(view2, e5.getMessage());
				} catch (CloneNotSupportedException e6) {
					valid=false;
					JOptionPane.showMessageDialog(view2, e6.getMessage());
				}
				if(valid)
					JOptionPane.showMessageDialog(view2, model.getCurrentHero().getName()+" used his hero power");
				update();
			}
			else if(model.getCurrentHero() instanceof Mage || model.getCurrentHero() instanceof Priest) {
				heroPowerEnabled = true;
				toBeCasted=null;
				Attacker=null;
			}
		}
		
	}
	public void setButton(Hero h, JButton p) {
		if (h == model.getCurrentHero()) {
			p.setText("Current Hero " + '\n' + h.toString());
		} else {
			p.setText("Opponent Hero " + '\n' + h.toString());
		}
	}

	public void setHandCards(Hero p, ArrayList<JButton> x, ActionListener z) {
		while (!x.isEmpty()) {
			x.remove(0);
		}
		for (int i = 0; i < p.getHand().size(); i++) {
			JButton b = new JButton();
			b.setText(p.getHand().get(i).toString());
			b.addActionListener(z);
			x.add(b);
		}

	}

	public void setFieldCards(Hero p, ArrayList<JButton> x, ActionListener z) {
		while (!x.isEmpty()) {
			x.remove(0);
		}
		for (int i = 0; i < p.getField().size(); i++) {
			JButton b = new JButton();
			b.setText(p.getField().get(i).toString());
			b.addActionListener(z);
			x.add(b);
		}

	}

	public void addCards(ArrayList<JButton> x, JPanel y) {
		y.removeAll();
		for (int i = 0; i < x.size(); i++) {
			y.add(x.get(i));
		}
		y.revalidate();
		y.repaint();
		view2.revalidate();
		view2.repaint();
		view2.getFrame2().revalidate();
		view2.getFrame2().repaint();
	}

	public void update() {
		if(model.getCurrentHero().getCurrentHP()!=0 && model.getOpponent().getCurrentHP()!=0) {
		setHandCards(model.getCurrentHero(), CurrentHand, new CurrentHandListener());
		setHandCards(model.getOpponent(), OpponentHand, new OpponentHandListener());
		setFieldCards(model.getCurrentHero(), CurrentField, new CurrentFieldListener());
		setFieldCards(model.getOpponent(), OpponentField, new OpponentFieldListener());
		addCards(CurrentHand, view2.getCurrentHand());
		addCards(OpponentHand, view2.getOpponentHand());
		addCards(CurrentField, view2.getCurrentField());
		addCards(OpponentField, view2.getOpponentField());
		view2.getCurrentHero().setText(model.getCurrentHero().getName()+" \n"+ model.getCurrentHero().getCurrentHP()+"HP \n"+ model.getCurrentHero().getCurrentManaCrystals()+"MC"+model.getCurrentHero().getTotalManaCrystals()+"TMC");
		view2.getOpponentHero().setText(model.getOpponent().getName()+" \n"+ model.getOpponent().getCurrentHP()+"HP \n"+ model.getOpponent().getCurrentManaCrystals()+"MC"+model.getOpponent().getTotalManaCrystals()+"TMC");
		view2.getDeck1().setText("Cur Deck Rem: "+ model.getCurrentHero().getDeck().size()+" Card" );
		view2.getDeck2().setText("OPP Deck Rem: "+ model.getOpponent().getDeck().size()+"/ Hand count: "+model.getOpponent().getHand().size() );}
		else if (model.getCurrentHero().getCurrentHP()==0 || model.getOpponent().getCurrentHP()==0) {
			onGameOver();
		}
		view2.revalidate();
		view2.repaint();
		view2.getFrame2().revalidate();
		view2.getFrame2().repaint();
	}

	public class endTurnListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
				try {
					model.endTurn();
				} catch (FullHandException e1) {
					Card c = e1.getBurned();
					view2.getBurnedCard().setText(c.toString());
					JOptionPane.showMessageDialog(view2, "B.C: "+e1.getMessage());
				} catch (CloneNotSupportedException e2) {
					JOptionPane.showMessageDialog(view2, e2.getMessage());
				}
				heroPowerEnabled = false;
				Attacker=null;
				toBeCasted=null;
				ToBeAttacked=null;
			
			update();

		}

	}

}
