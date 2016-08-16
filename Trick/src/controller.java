import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

import java.awt.event.*;

public class controller implements ActionListener {

	private Hand hand;

	private final int PLAYER = 0;

	private final int CPU = 1;

	private final int ENVIDO = 0;

	private final int REALENVIDO = 1;

	private final int FALTAENVIDO = 2;

	private int turn, roundTurn, playerScore, cpuScore, playerRoundScore,
			cpuRoundScore, roundPoints, firstRoundWinner, firstEnvido,
			trucoTurn;

	ArrayList<Integer> vidos;

	private String currentCard, nextTruco;

	private int cardNum = 0;

	private boolean envidoPlayed;

	private Timer cpuTimer = new Timer(3000, this);

	private Timer roundChecker = new Timer(33, this);

	private Timer newRound = new Timer(1500, this);

	private final Random rand = new Random();

	public void newGame() {
		cpuTimer.start();
		roundChecker.start();
		deal();
		roundTurn = rand.nextInt(2);
		turn = roundTurn;
		playerScore = 0;
		cpuScore = 0;
		cardNum = 0;
	}

	public void deal() {
		envidoPlayed = false;
		vidos = new ArrayList();
		vidos.add(1);
		firstRoundWinner = 2;
		roundTurn = (roundTurn == 0) ? roundTurn + 1 : roundTurn - 1;
		turn = roundTurn;
		hand = new Hand();
		roundChecker.start();
		playerRoundScore = 0;
		cpuRoundScore = 0;
		roundPoints = 1;
		cardNum = 0;
		trucoTurn = 2;
		currentCard = "";
		nextTruco = "Truco";
	}

	public void fold() {
		cpuScore += roundPoints;
		deal();
	}

	public Hand getHand() {
		return hand;
	}

	public int getPlayerScore() {
		return playerScore;
	}

	public int getcpuScore() {
		return cpuScore;
	}

	public String getTurn() {
		return turn == PLAYER ? "Player" : "CPU";
	}

	public String getCurrentCard() {
		return currentCard;
	}

	public String getNextTruco() {
		return nextTruco;
	}

	public void cpuPlay() {
		int envido = hand.envido(hand.cpu);
		int betterCards = hand.cardsBetter(currentCard);
		int handRank = hand.handRank();
		if (turn == CPU) {
			cpuTimer.stop();
			switch (cardNum) {
			case 0:
				if (handRank <= 23 ) {
					cpuCallTruco("Raise");
				}
				if (envido > 23 && envido < 28 && !envidoPlayed) {
					cpuCallEnvido(ENVIDO);
				} else if (envido > 27 && envido < 31 && !envidoPlayed) {
					cpuCallEnvido(REALENVIDO);
				} else if (envido > 30 && !envidoPlayed) {
					cpuCallEnvido(FALTAENVIDO);
				}
			case 2:
			case 4:
			case 5:
				cardPlayer(hand.getBest());
				hand.remove(hand.getBest());
				break;
			case 1:
				if (handRank <= 23) {
					cpuCallTruco("Raise");
				}
				if (envido > 23 && envido < 28 && !envidoPlayed) {
					cpuCallEnvido(ENVIDO);
				} else if (envido > 27 && !envidoPlayed) {
					cpuCallEnvido(REALENVIDO);
				} else if (envido > 30 && !envidoPlayed) {
					cpuCallEnvido(FALTAENVIDO);
				}
			case 3:
				if (betterCards == 0) {
					cardPlayer(hand.getWorst());
					hand.remove(hand.getWorst());
				} else if (betterCards == 1) {
					cardPlayer(hand.getBest());
					hand.remove(hand.getBest());
				} else if (betterCards == 2) {
					System.out.println(hand.getSecond());
					cardPlayer(hand.getSecond());
					hand.remove(hand.getSecond());
				} else if (betterCards == 3) {
					cardPlayer(hand.getWorst());
					hand.remove(hand.getWorst());
				}
			}
		}

	}

	public void checkRound() {
		if (playerRoundScore == 2) {
			playerScore += roundPoints;
			roundChecker.stop();
			newRound.start();
		}
		if (cpuRoundScore == 2) {
			cpuScore += roundPoints;
			roundChecker.stop();
			newRound.start();
		}
		if (cpuScore > 5 || playerScore > 5) {
			String winner = "";
			winner = (cpuScore > 5) ? "CPU" : "Player";
			JOptionPane.showMessageDialog(null, "WINNER WINNER CHICKEN "
					+ winner);
			newGame();
		}
	}

	public void cardPlayer(String play) {
		String card = play;
		if (!card.equals("null")) {
			cpuTimer.stop();
			cpuTimer.start();
			cardNum++;

			if (cardNum == 2 || cardNum == 4 || cardNum == 6) {
				if (truco.cardRank(card) == truco.cardRank(currentCard)) {
					tie();
				}
				if (truco.cardRank(card) < truco.cardRank(currentCard)) {
					if (turn == PLAYER) {
						playerRoundScore++;
						if (cardNum == 2) {
							firstRoundWinner = PLAYER;
						}
					} else if (turn == CPU) {
						cpuRoundScore++;
						if (cardNum == 2) {
							firstRoundWinner = CPU;
						}
					}
				} else if (truco.cardRank(card) > truco.cardRank(currentCard)) {
					if (turn == PLAYER) {
						cpuRoundScore++;
						turn = CPU;
						if (cardNum == 2) {
							firstRoundWinner = CPU;
						}
					} else if (turn == CPU) {
						playerRoundScore++;
						turn = PLAYER;
						if (cardNum == 2) {
							firstRoundWinner = PLAYER;
						}
					}
				}

			} else if (cardNum == 1 || cardNum == 3 || cardNum == 5) {
				if (turn == CPU) {
					turn = PLAYER;
				} else if (turn == PLAYER) {
					turn = CPU;
				}
			}

		}
		currentCard = card;
	}

	public void playerCallTruco(int call) {
		String raise = nextTruco;
		int handRank = hand.handRank();
		System.out.println(handRank);
		if (trucoTurn == PLAYER || trucoTurn == 2) {
			switch (call) {
			case 0:// raise
				switch (nextTruco) {
				case "Truco":
					nextTruco = "Retruco";
					break;
				case "Retruco":
					nextTruco = "Vale Cuatro";
					break;
				}
				roundPoints++;
				trucoTurn = CPU;
				switch (raise) {
				case "Truco":
					if (handRank > 27) {
						cpuCallTruco("Decline");
					} else if (handRank <= 27 && handRank > 17) {
						cpuCallTruco("Accept");
					} else if (handRank <= 17) {
						cpuCallTruco("Raise");
					}
					break;
				case "Retruco":
					if (handRank > 22) {
						cpuCallTruco("Decline");
					} else if (handRank <= 22 && handRank > 12) {
						cpuCallTruco("Accept");
					} else if (handRank <= 12) {
						cpuCallTruco("Raise");
					}
					break;
				case "Vale Cuatro":
					if (handRank < 12) {
						cpuCallTruco("Accept");
					} else if (handRank >= 12) {
						cpuCallTruco("Decline");
					}
					break;
				}
				break;
			case 2:// decline
				cpuScore += (roundPoints - 1);
				deal();
				break;
			}
		}
	}

	public void cpuCallTruco(String call) {
		if (trucoTurn == CPU || trucoTurn == 2) {
			switch (call) {
			case "Raise":
				String raise = nextTruco;
				switch (nextTruco) {
				case "Truco":
					nextTruco = "Retruco";
					break;
				case "Retruco":
					nextTruco = "Vale Cuatro";
					break;
				}
				roundPoints++;
				trucoTurn = PLAYER;
				String[] options = { nextTruco, "Accept", "Decline" };
				int response = JOptionPane.showOptionDialog(null, "CPU calls "
						+ raise, "Envido", JOptionPane.DEFAULT_OPTION,
						JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				if (response == JOptionPane.CLOSED_OPTION) {
					response = 2;
				}
				playerCallTruco(response);
				break;
			case "Accept":
				JOptionPane.showMessageDialog(null, "Quiero!");
				break;
			case "Decline":
				JOptionPane.showMessageDialog(null, "CPU Declines");
				playerScore += (roundPoints - 1);
				deal();
				break;
			}
		}
	}

	public void cpuCallEnvido(int choice) {
		if (vidos.size() == 1) {
			firstEnvido = CPU;
		}
		envidoPlayed = true;
		String call = "";
		switch (choice) {
		case ENVIDO:
			call = "envido";
			vidos.add(2);
			break;
		case REALENVIDO:
			call = "real envido";
			vidos.add(3);
			break;
		case FALTAENVIDO:
			call = "falta envido";
			vidos.add(15);
			break;
		case 4:// accept
			envidoWinner();
			break;
		case 5: // decline
			JOptionPane.showMessageDialog(null, "CPU has declined");
			int declineScore = 0;
			if (vidos.size() == 2) {
				declineScore = 1;
			} else
				for (int i = 1; i < vidos.size() - 1; i++) {
					declineScore += vidos.get(i);
				}
			playerScore += declineScore;
			break;
		}
		if (choice != 4 && choice != 5) {
			String[] options = new String[5];
			int response = 9;
			if (choice != 2) {
				options = new String[] { "Falta Envido", "Real Envido",
						"Envido", "Accept", "Decline" };
				response = JOptionPane.showOptionDialog(null, "CPU calls "
						+ call, "Envido", JOptionPane.DEFAULT_OPTION,
						JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

			} else {
				options = new String[] { "Accept", "Decline" };
				response = JOptionPane.showOptionDialog(null, "CPU calls "
						+ call, "Envido", JOptionPane.DEFAULT_OPTION,
						JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				if (response != JOptionPane.CLOSED_OPTION) {
					response += 3;
				}
				;
			}

			if (response == JOptionPane.CLOSED_OPTION) {
				response = 4;
			}

			switch (response) {
			case 0:
				playerCallEnvido(FALTAENVIDO);
				break;
			case 1:
				playerCallEnvido(REALENVIDO);
				break;
			case 2:
				playerCallEnvido(ENVIDO);
				break;
			case 3:
				envidoWinner();
				break;
			case 4:
				int declineScore = 0;
				if (vidos.size() == 2) {
					declineScore = 1;
				} else
					for (int i = 1; i < vidos.size() - 1; i++) {
						declineScore += vidos.get(i);
					}
				cpuScore += declineScore;
				break;
			}

		}

	}

	public void playerCallEnvido(int call) {
		if (vidos.size() == 1) {
			firstEnvido = PLAYER;
		}
		int cpuEnvido = hand.envido(hand.cpu);
		envidoPlayed = true;
		switch (call) {
		case ENVIDO:
			vidos.add(2);
			if (cpuEnvido > 24 && cpuEnvido < 29) {
				cpuCallEnvido(4);
			} else if (cpuEnvido > 28) {
				cpuCallEnvido(2);
			} else
				cpuCallEnvido(5);
			break;
		case REALENVIDO:
			vidos.add(3);
			if (cpuEnvido > 27 && cpuEnvido < 32) {
				cpuCallEnvido(4);
			} else if (cpuEnvido > 31) {
				cpuCallEnvido(3);
			} else
				cpuCallEnvido(5);
			break;
		case FALTAENVIDO:
			vidos.add(15);
			if (cpuEnvido > 30) {
				cpuCallEnvido(4);
			} else
				cpuCallEnvido(5);
			break;
		case 4:
			String[] options = new String[] { "Envido", "Real Envido",
					"Falta Envido" };
			int response = JOptionPane.showOptionDialog(null,
					"Choose Your Envido", "Envido", JOptionPane.DEFAULT_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			playerCallEnvido(response);
		}

	}

	public boolean getEnvidoPlayed() {
		return envidoPlayed;
	}

	public void envidoWinner() {
		int envidoPoints = 0;
		int pVido = hand.envido(hand.player);
		int cVido = hand.envido(hand.cpu);
		for (int i = 1; i < vidos.size(); i++) {
			envidoPoints += vidos.get(i);
		}
		if (cVido > pVido) {
			JOptionPane.showMessageDialog(null,
					"CPU wins: " + hand.envido(hand.cpu));
			if (vidos.get(vidos.size() - 1) == 15) {
				cpuScore += 15 - (cpuScore % 15);
			} else
				cpuScore += envidoPoints;
		} else if (cVido < pVido) {

			JOptionPane.showMessageDialog(null,
					"Player wins: " + hand.envido(hand.player));
			if (vidos.get(vidos.size() - 1) == 15) {
				playerScore += 15 - (playerScore % 15);
			} else
				playerScore += envidoPoints;
		} else if (cVido == pVido) {
			if (firstEnvido == PLAYER) {
				JOptionPane.showMessageDialog(null,
						"Player wins: " + hand.envido(hand.player));
				if (vidos.get(vidos.size() - 1) == 15) {
					playerScore += 15 - (playerScore % 15);
				} else
					playerScore += envidoPoints;
			} else if (firstEnvido == CPU) {
				JOptionPane.showMessageDialog(null,
						"CPU wins: " + hand.envido(hand.cpu));
				if (vidos.get(vidos.size() - 1) == 15) {
					cpuScore += 15 - (cpuScore % 15);
				} else
					cpuScore += envidoPoints;
			}
		}
	}

	public void tie() {
		if (cardNum == 2) {
			cpuRoundScore = 1;
			playerRoundScore = 1;
			turn = roundTurn;
		} else if (cardNum == 4 || cardNum == 6) {
			turn = roundTurn;
			if (firstRoundWinner == PLAYER) {
				playerRoundScore = 2;
			} else if (firstRoundWinner == CPU) {
				cpuRoundScore = 2;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cpuTimer && turn == CPU) {
			cpuPlay();
		}
		if (e.getSource() == roundChecker) {
			checkRound();
		}
		if (e.getSource() == newRound) {
			newRound.stop();
			deal();
		}

	}
}