package edu.mccc.cos210.poker;

//.
import java.util.ArrayList;

public class PokerHand {
	private Card[] faces = new Card[5];
	private int[] Val = new int[6]; // value

	PokerHand(Deck_ d) // PokerHand class extends Card/Deck class
	{
		for (int x = 0; x < 5; x++) {
			faces[x] = d.DrawFromDeck();
		}

		initialize();

	}

	public PokerHand(ArrayList<edu.mccc.cos210.poker.UI.Card> cardList) {
		for (int x = 0; x < 5; x++) {
			// add all faces from the list into an array
			faces[x] = new Card(cardList.get(x));
		}

		initialize();
	}

	private void initialize() {

		int[] ranksOfCards = new int[14];
		for (int a = 0; a <= 13; a++) {
			ranksOfCards[a] = 0;
		}
		for (int a = 0; a <= 4; a++) {
			ranksOfCards[faces[a].getRank()]++;
		}

		int largeRankPairs = 0; // pairs that will have larger ranks
		int smallRankPairs = 0; // pairs that will have smaller ranks

		int Identical_Cards = 1; // there will always be a one card of any rank
		int Identical_Cards_1 = 1; // we're going to initialize both = 1

		for (int a = 13; a >= 1; a--) {
			// if rankofcards a is greater than Identical_Cards
			if (ranksOfCards[a] > Identical_Cards) {
				if (Identical_Cards != 1) {
					Identical_Cards_1 = Identical_Cards;
					smallRankPairs = a;
				}

				Identical_Cards = ranksOfCards[a];
				largeRankPairs = a;

			}
			// if rankofcards a is greater than the 2nd pair we found in the hand.
			else if (ranksOfCards[a] > Identical_Cards_1) {
				Identical_Cards_1 = ranksOfCards[a];
				smallRankPairs = a;
			}
		}

		// Flush
		boolean flush = true; // lets assume there is a flush

		for (int f = 0; f < 4; f++) {
			if (faces[f].getSuit() != faces[f + 1].getSuit())
				flush = false;
		}

		// Straight
		int straightPair = 0;
		boolean straight = false;

		for (int s = 1; s <= 9; s++) {
			if (ranksOfCards[s] == 1 && ranksOfCards[s + 1] == 1 && ranksOfCards[s + 2] == 1 && ranksOfCards[s + 3] == 1
					&& ranksOfCards[s + 4] == 1) {
				straight = true;
				straightPair = s + 4;
				break;
			}
		}
		int royalHand = 10;
		boolean royal = false;

		for (int r = 9; r <= 13; r++) {
			if (ranksOfCards[r] == 9 && ranksOfCards[r + 1] == 9 && ranksOfCards[r + 2] == 9 && ranksOfCards[r + 3] == 9
					&& ranksOfCards[r + 4] == 9) {
				royal = true;
				royalHand = r + 4; // so if the royal is 4 above bottom value, break it. or stop
				break;
			}
		}

		int[] rankComparator = new int[5];
		int index = 0;

		// since we have already implemented the code for scenarios where there are 2
		// cards of the same rank
		for (int a = 13; a >= 2; a--) {
			if (ranksOfCards[a] == 1) {
				rankComparator[index] = a;
				index++;
			}
		}

		// High Card
		if (Identical_Cards == 1) {
			Val[0] = 1;
			Val[1] = rankComparator[0];
			Val[2] = rankComparator[1];
			Val[3] = rankComparator[2];
			Val[4] = rankComparator[3];
			Val[5] = rankComparator[4];
		}

		// A Pair
		if (Identical_Cards == 2) {
			Val[0] = 2;
			Val[1] = largeRankPairs;
		}

		// Two Pair
		if (Identical_Cards == 2 && Identical_Cards_1 == 2) {
			Val[0] = 3;
			Val[1] = largeRankPairs > smallRankPairs ? largeRankPairs : largeRankPairs;

			Val[2] = largeRankPairs < smallRankPairs ? smallRankPairs : smallRankPairs;

		}

		// 3 of a Kind
		if (Identical_Cards == 3 && Identical_Cards_1 != 2) {
			Val[0] = 4;
			Val[1] = largeRankPairs;

		}

		if (straight) {
			Val[0] = 5;
			Val[1] = straightPair;

		}

		if (flush) {
			Val[0] = 6;
			Val[1] = rankComparator[0];
			Val[2] = rankComparator[1];
			Val[3] = rankComparator[2];
			Val[4] = rankComparator[3];
			Val[5] = rankComparator[4];
		}

		// Full House
		if (Identical_Cards == 3 && Identical_Cards_1 == 2) {
			Val[0] = 7; // rank 7
			Val[1] = largeRankPairs;
			Val[3] = smallRankPairs;
		}

		// Four of a Kind
		if (Identical_Cards == 4) {
			Val[0] = 8; // rank 8
			Val[1] = largeRankPairs;
		}

		// Straight Flush
		if (straight && flush) {
			Val[0] = 9; // the highest and the most rarest hand of cards.
			Val[1] = straightPair;
		}

		// Royal Flush
		if (royal && straight) {
			Val[0] = 10;
			Val[1] = royalHand;
		}
	}

	int compareTo(PokerHand to) {
		int a = 0;

		if (this.Val[a] > to.Val[a])

			return 1;

		else if (this.Val[a] < to.Val[a])

			return -1;

		return 0;
	}

	String Show_Hand() {
		String a = null;
		switch (Val[0]) {
		case 10:
			a = "This hand is Royal Flush " + Card.card_rank_string(Val[1]);
			break;

		case 9:
			a = "This hand is straight flush " + Card.card_rank_string(Val[1]);
			break;

		case 8:
			a = "This hand is four of a kind " + Card.card_rank_string(Val[1]);
			break;

		case 7:
			a = "This hand is full house " + Card.card_rank_string(Val[1]) + Card.card_rank_string(Val[3]);
			break;

		case 6:
			a = "This hand is flush ";
			break;

		case 5:
			a = "This hand is Straight" + Card.card_rank_string(Val[1]);
			break;

		case 4:
			a = "This hand is three of a kind " + Card.card_rank_string(Val[1]) + "\'s";
			break;

		case 3:
			a = "This hand is two pair of " + Card.card_rank_string(Val[1]) + " and " + Card.card_rank_string(Val[2]);
			break;

		case 2:
			a = "This hand is pair of " + Card.card_rank_string(Val[1]) + "\'s";
			break;
		case 1:
			a = "Both hands are tie";
			break;

		}
		return a;
	}
}
