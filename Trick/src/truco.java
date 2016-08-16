import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.ImageIcon;

public class truco {

	public static ImageIcon setCard(String a) {
		ImageIcon icon;
		String file = "";
		for (int i = 1; i < 11; i++) {
			if (a.equals("Sword " + i)) {
				file = i + "sword.png";
			}
		}
		for (int i = 1; i < 11; i++) {
			if (a.equals("Club " + i)) {
				file = i + "club.png";
			}
		}
		for (int i = 1; i < 11; i++) {
			if (a.equals("Gold " + i)) {
				file = i + "gold.png";
			}
		}
		for (int i = 1; i < 11; i++) {
			if (a.equals("Cup " + i)) {
				file = i + "cup.png";
			}
		}
		return createImageIcon("naipes/" + file);

	}

	private static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = truco.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public static int cardRank(String a) {
		HashMap power = new HashMap();
		power.put("Sword 1", 1);
		power.put("Club 1", 2);
		power.put("Sword 7", 3);
		power.put("Gold 7", 4);
		power.put("Sword 3", 5);
		power.put("Club 3", 5);
		power.put("Gold 3", 5);
		power.put("Cup 3", 5);
		power.put("Sword 2", 6);
		power.put("Club 2", 6);
		power.put("Gold 2", 6);
		power.put("Cup 2", 6);
		power.put("Gold 1", 7);
		power.put("Cup 1", 7);
		power.put("Sword 10", 8);
		power.put("Club 10", 8);
		power.put("Gold 10", 8);
		power.put("Cup 10", 8);
		power.put("Sword 9", 9);
		power.put("Club 9", 9);
		power.put("Gold 9", 9);
		power.put("Cup 9", 9);
		power.put("Sword 8", 10);
		power.put("Club 8", 10);
		power.put("Gold 8", 10);
		power.put("Cup 8", 10);
		power.put("Club 7", 11);
		power.put("Cup 7", 11);
		power.put("Sword 6", 12);
		power.put("Club 6", 12);
		power.put("Gold 6", 12);
		power.put("Cup 6", 12);
		power.put("Sword 5", 13);
		power.put("Club 5", 13);
		power.put("Gold 5", 13);
		power.put("Cup 5", 13);
		power.put("Sword 4", 14);
		power.put("Club 4", 14);
		power.put("Gold 4", 14);
		power.put("Cup 4", 14);
		power.put("", 6);

		return (int) power.get(a);
	}
	
	public static int invConvert(int a){
		int value = a;
		if(a==8||a==9||a==0){
			value = 0;
		}
		return value;
	}
}