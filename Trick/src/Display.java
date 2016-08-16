import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.Timer;


public class Display extends JFrame implements ActionListener {
	controller controller;
	JLabel card1;
	JLabel card2;
	JLabel card3;
	JLabel score;
	JLabel currentCard;
	JButton fold;
	JButton newGame;
	JButton card1Button;
	JButton card2Button;
	JButton card3Button;
	JButton envido;
	JButton trucoz;
	Timer updateTimer;
	
	
	public static void main(String[] args){
		Display screen = new Display();
		screen.setVisible(true);
		
	}
	public Display() {
		
		controller = new controller();
		
		updateTimer = new Timer(500, this);
		
		setTitle("Truco");
		
		getContentPane().setBackground(Color.BLACK);
		
		setSize(800, 450);
		
		setResizable(false);
		
		 card1 = new JLabel();
		
		card1Button = new JButton("Card 1");
		card1Button.addActionListener(this);
		
		 card2 = new JLabel();
		
		 card3 = new JLabel();
		
		card2Button = new JButton("Card 2");
		card2Button.addActionListener(this);
		
		card3Button = new JButton("Card 3");
		card3Button.addActionListener(this);
		
		 currentCard = new JLabel();
		
		trucoz = new JButton("Truco");
		trucoz.addActionListener(this);
		
		envido = new JButton("Envido");
		envido.addActionListener(this);
		
		 fold = new JButton("Fold");
		 fold.addActionListener(this);
		
		 score = new JLabel("Score");
		 score.setForeground(Color.WHITE);
		
		newGame = new JButton("New Game");
		newGame.addActionListener(this);
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(22)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(card1Button)
									.addGap(96))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(card1, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
									.addGap(18)))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(card2, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
								.addComponent(card2Button, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(95)
							.addComponent(trucoz)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(envido)
							.addGap(14)
							.addComponent(fold, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(card3, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
							.addGap(75)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(score, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
								.addComponent(currentCard, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(card3Button, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(newGame)
							.addGap(40)))
					.addContainerGap(65, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(trucoz)
						.addComponent(envido)
						.addComponent(score, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
						.addComponent(fold))
					.addPreferredGap(ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(card2, GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE)
								.addComponent(card3, GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE)
								.addComponent(currentCard, GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(card2Button)
								.addComponent(card3Button)
								.addComponent(newGame)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(card1, GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(card1Button)))
					.addGap(39))
		);
		getContentPane().setLayout(groupLayout);
	}
	
	public void update(){
		card1.setIcon(truco.setCard(controller.getHand().getCard1()));
		card2.setIcon(truco.setCard(controller.getHand().getCard2()));
		card3.setIcon(truco.setCard(controller.getHand().getCard3()));
		score.setText("<html>Player: "+controller.getPlayerScore()+"<br>CPU: "+controller.getcpuScore()+"<br>"+"Turn: "+controller.getTurn()+"</html>");
		currentCard.setIcon(truco.setCard(controller.getCurrentCard()));
		trucoz.setText(controller.getNextTruco());
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==newGame){
			updateTimer.start();
			controller.newGame();
			update();
		}
		if(e.getSource()==card1Button && controller.getTurn().equals("Player")){
			controller.cardPlayer(controller.getHand().getCard1());
			controller.getHand().makeNull(controller.getHand().getCard1());
			update();
		}
		if(e.getSource()==card2Button && controller.getTurn().equals("Player")){
			controller.cardPlayer(controller.getHand().getCard2());
			controller.getHand().makeNull(controller.getHand().getCard2());
			update();
		}
		if(e.getSource()==fold){
			controller.fold();
			update();
		}
		if(e.getSource()==card3Button && controller.getTurn().equals("Player")){
			controller.cardPlayer(controller.getHand().getCard3());
			controller.getHand().makeNull(controller.getHand().getCard3());
			update();
		}
		if(e.getSource()==envido && !controller.getEnvidoPlayed() && controller.getTurn().equals("Player")){
			controller.playerCallEnvido(4);
		}
		if(e.getSource()==trucoz && controller.getTurn().equals("Player")){
			controller.playerCallTruco(0);
		}
	 if(e.getSource()==updateTimer){
			update();
		}
		
		
	}
	
}