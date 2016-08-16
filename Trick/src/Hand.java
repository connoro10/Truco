import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;


public class Hand {
	ArrayList<String> player = new ArrayList();
	ArrayList<String> cpu = new ArrayList();
	String [] suits = {"Sword", "Club", "Gold", "Cup"};
	
	public Hand(){
		
		ArrayList<String> deck = new ArrayList();
		for(String suit: suits){
			for(int i = 1; i<11; i++){
				deck.add(suit+" "+i);
			}
		}
		for(int i = 0; i<3; i++){
			Random index = new Random();
			String pCard = deck.get(index.nextInt(deck.size()));
			player.add(pCard);
			deck.remove(pCard);
			String cCard = deck.get(index.nextInt(deck.size()));
			cpu.add(cCard);
			deck.remove(cCard);
		}

	}
	
	public String getBest(){
		String best = "";
		int bestSoFar = Integer.MAX_VALUE;
		for(int i =0; i<cpu.size(); i++){
			if(truco.cardRank(cpu.get(i))<bestSoFar){
				bestSoFar = truco.cardRank(cpu.get(i));
				best = cpu.get(i);
			}
		}
		return best;
	}
	
	public String getWorst(){
		String worst = "";
		int worstSoFar = Integer.MIN_VALUE;
		for(int i =0; i<cpu.size(); i++){
			if(truco.cardRank(cpu.get(i))>worstSoFar){
				worstSoFar = truco.cardRank(cpu.get(i));
				worst = cpu.get(i);
			}
		}
		return worst;
	}
	
	public String getSecond(){
		String second = "";
		if(cpu.size()==2){
			return getWorst();
		}
		else
		for(int i =0; i<cpu.size(); i++){
			if(!cpu.get(i).equals(getBest()) && !cpu.get(i).equals(getWorst())){
				second = cpu.get(i);
			}
		}
		return second;
	}
	
	public int cardsBetter(String currentCard){
		int betterCards = 0;
		for(int i = 0; i<cpu.size(); i++){
			if(truco.cardRank(cpu.get(i))< truco.cardRank(currentCard)){
				betterCards++;
			}
		}
		return betterCards;
	}
	
	public String getCard1(){
		return player.get(0);
	}
	
	public String getCard2(){
		return player.get(1);
	}
	
	public String getCard3(){
		return player.get(2);
	}
	
	public void remove(String card){
		cpu.remove(card);
	}
	
	public void makeNull(String card){
		int index = player.indexOf(card);
		player.set(index, "null");
	}
	
	public int handRank(){
		int rank = 0;
		for(int i = 0; i<cpu.size(); i++){
			rank+=truco.cardRank(cpu.get(i));
		}
		return rank;
	}
	
	public int envido(ArrayList<String> cpu){
		int envido = 0;
		ArrayList<String> sameSuit = new ArrayList();
		for(int i = 0; i<cpu.size()-1; i++){
			Scanner s = new Scanner(cpu.get(i));
			String suit = s.next();
			for(int n = i+1; n<cpu.size(); n++){
				Scanner sc = new Scanner(cpu.get(n));
				String suit2 = sc.next();
				if(suit.equals(suit2)){
					sameSuit.add(cpu.get(n));
					sameSuit.add(cpu.get(i));
				}
				
			}
			Set<String> dubz = new HashSet(sameSuit);
			sameSuit = new ArrayList(dubz);
		}
		if(sameSuit.size()==0){
			int bestInv = 0;
			for(String card: cpu){
				int value = truco.invConvert(Character.getNumericValue(card.charAt(card.length()-1)));
				if(value >bestInv){
					bestInv = value;
				}
				
			}
			envido = bestInv;
		}
		
		else if (sameSuit.size()==2){
			String card1 = sameSuit.get(0);
			String card2 = sameSuit.get(1);
			int value1 = truco.invConvert(Character.getNumericValue(card1.charAt(card1.length()-1)));
			int value2 = truco.invConvert(Character.getNumericValue(card2.charAt(card2.length()-1)));
			envido = value1+value2+20;
		}
		
		else if(sameSuit.size()==3){
			int smallest = 8;
			for(String card: sameSuit){
				if(truco.invConvert(Character.getNumericValue(card.charAt(card.length()-1)))<smallest){
					smallest = truco.invConvert(Character.getNumericValue(card.charAt(card.length()-1)));
				}
			}
			for(int i = 0; i<sameSuit.size(); i++){
				String card = sameSuit.get(i);
				if(truco.invConvert(Character.getNumericValue(card.charAt(card.length()-1)))==smallest){
					sameSuit.remove(card);
					break;
				}
			}
			String card1 = sameSuit.get(0);
			String card2 = sameSuit.get(1);
			int value1 = truco.invConvert(Character.getNumericValue(card1.charAt(card1.length()-1)));
			int value2 = truco.invConvert(Character.getNumericValue(card2.charAt(card2.length()-1)));
			envido = value1+value2+20;
		}
		return envido;
	}

	public static void main(String[] args){
		Hand deal = new Hand();
	}
}
