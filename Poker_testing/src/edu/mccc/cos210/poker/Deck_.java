package edu.mccc.cos210.poker;

//.
import java.util.ArrayList;
import java.util.Random;

public class Deck_ {

	private ArrayList<Card> faces;

	Deck_() {
		faces = new ArrayList<Card>();

		int Card1, Card2;

		Random rand = new Random();
		Card t;

		for (short a = 0; a <= 3; a++) {
			for (short b = 0; b <= 12; b++) {
				faces.add(new Card(a, b));
			}
		}

		int s = faces.size() - 1;
		for (short i = 0; i < 500; i++) {
			// generate a random card
			Card1 = rand.nextInt(s);
			Card2 = rand.nextInt(s);

			t = (Card) faces.get(Card2);
			faces.set(Card2, faces.get(Card1));
			faces.set(Card1, t);
		}
	}

	public Card DrawFromDeck() {
		return faces.remove(faces.size() - 1);
	}

}
