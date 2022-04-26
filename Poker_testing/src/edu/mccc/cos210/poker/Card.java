package edu.mccc.cos210.poker;

//.
public class Card {
	private short rank, suit;

	// static methods are used here to be allied to the class as a whole
	private String[] suits = { "Hearts", "Clubs", "Diamonds", "Spades" };
	private static String[] ranks = { "0", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };

	Card(short suit, short rank) {
		this.suit = suit;
		this.rank = rank;
	}

	public Card(edu.mccc.cos210.poker.UI.Card card) {
		this.rank = convertRank(card);
		this.suit = convertSuit(card);
	}

	/*
	 * convertSuit will take in as object from UI class and convert its value of
	 * Suit to a type short.
	 */
	public short convertSuit(edu.mccc.cos210.poker.UI.Card card) {
		String suit = card.getSuit().getSuitId();

		if (suit.equals("H")) {
			return 0;
		} else if (suit.equals("C")) {
			return 1;
		} else if (suit.equals("D")) {
			return 2;
		} else {
			return 3;
		}

	}

	public static String card_rank_string(int rank) {
		return ranks[rank];
	}

	/*
	 * converRank method will take in an object from UI class and convert its value
	 * of Rank to a type short.
	 */
	public short convertRank(edu.mccc.cos210.poker.UI.Card card) {
		String rank = card.getRank().getRankId();

		for (int i = 0; i < ranks.length; i++) {
			if (ranks[i].equals(rank)) {
				return (short) i;
			}
		}

		return -1;
	}

	public short getRank() {
		return rank;
	}

	public short getSuit() {
		return suit;
	}

	public String toString() {
		return ranks[rank] + " of " + suits[suit];
	}

}
