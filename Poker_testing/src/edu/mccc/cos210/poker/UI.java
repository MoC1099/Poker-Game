package edu.mccc.cos210.poker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import edu.mccc.cos210.poker.UI.Card.Rank;
import edu.mccc.cos210.poker.UI.Card.Suit;

public class UI {
	private static final double SCALE = 0.20;
	private static int CARDS_DEALT = 5;
	private static int ROWS = 1;
	private static int COLUMNS = 5;
	private static final boolean FACE_UP = true;

	private Deck deck = new Deck();
	private static JPanel jp4;
	private static JPanel jp5;

	public UI() {
		String[] names = new String[] { "Huang, Xiaoguang", "Gavva, Nihar", "Bacskocky, Joseph", "Butler, Carson",
				"Shah, Mariam", "Mancuso, Michael", "Dos Santos, Rodrigo", "Sidgiddi, Sanjana", "Gaspari, Madison",
				"Bostain, David", "Jimenez, Katherin", "Smith, Theodore", "Martinez, Vanessa", "Zottman, William",
				"Siamashka, Aliaksandr", "Arana Leon, Noam", "belay, huruy", "Morales, Michelle", "Perez, Christian",
				"Endreddy, Sumana", "Subedi, Basu", "Miralles-Cordal, Alexander", "Ahmed, Manadi", "Sontupe, Ryan",
				"Osias, Brittany", "Bindra, Sargun" };
		Random r = new Random();

		int idx = r.nextInt(names.length);
		int idx2 = r.nextInt(names.length);

		JFrame jf = new JFrame("Poker Game");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(true);
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		jf.add(jp, BorderLayout.CENTER);

		JPanel jp3 = new JPanel();
		jp.add(jp3);
		JButton jbDeal = new JButton("DEAL");
		JButton jbTheWinner = new JButton("THE WINNER");
		jp3.setLayout(new FlowLayout());
		jp3.add(jbTheWinner, FlowLayout.LEFT);
		jp3.add(jbDeal, FlowLayout.LEFT);
		jp3.setBackground(new Color(0 - 0 - 0));

//		jp3.add (new JButton("SHUFFLE"), FlowLayout.LEFT);

		jp4 = shuffleDeckAndDealCards(deck);
		JLabel jl4 = new JLabel(names[idx]);
		jp4.add(jl4, FlowLayout.LEFT);
		jp4.setVisible(true);
		jp.add(jp4, BorderLayout.CENTER);
		jp4.setBackground(Color.GREEN);

		jp5 = shuffleDeckAndDealCards(deck);
		JLabel jl5 = new JLabel(names[idx2]);
		jp5.add(jl5, FlowLayout.LEFT);
		jp5.setVisible(true);
		jp.add(jp5, BorderLayout.CENTER);
		jp5.setBackground(Color.GREEN);

//DEAL BUTTON
		jbDeal.addActionListener(

				ae -> {

					jp.remove(jp4);
					jp.remove(jp5);
					deck = new Deck();
					jp4 = null;

					Random r2 = new Random();

					int idNewx = r2.nextInt(names.length);
					int idNewx2 = r2.nextInt(names.length);

					jp4 = shuffleDeckAndDealCards(deck);
					jp4.add(new JLabel(names[idNewx]), FlowLayout.LEFT);
					jp4.setVisible(true);
					jp.add(jp4, BorderLayout.CENTER);
					jp4.setBackground(Color.GREEN);

					jp5 = null;
					jp5 = shuffleDeckAndDealCards(deck);
					jp5.add(new JLabel(names[idNewx2]), FlowLayout.LEFT);
					jp5.setVisible(true);
					jp.add(jp5, BorderLayout.CENTER);
					jf.validate();
					jp5.setBackground(Color.GREEN);

				}

		);

//THE WINNER BUTTON
		jbTheWinner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Card> firstPlayer = new ArrayList<>();
				ArrayList<Card> secondPlayer = new ArrayList<>();

				for (int x = 1; x < 6; x++) {
					Component compOne = jp4.getComponent(x);
					Component compTwo = jp5.getComponent(x);
					JLabel jlTempOne = (JLabel) compOne;
					JLabel jlTempTwo = (JLabel) compTwo;

					Card cardOne = (Card) jlTempOne.getClientProperty("CARD");
					Card cardTwo = (Card) jlTempTwo.getClientProperty("CARD");

					firstPlayer.add(cardOne);
					secondPlayer.add(cardTwo);
				}
				PokerHand pHandOne = new PokerHand(firstPlayer);
				PokerHand pHandTwo = new PokerHand(secondPlayer);
				// COMPARE TWO HANDS
				int value = pHandOne.compareTo(pHandTwo);
				if (e.getSource() == jbTheWinner) { // WHEN WE CLICK ON THE WINNER BUTTON
					// OPEN THE WINNER SCREEN SHOWING THE WINNING HAND
					// pokerBackGreen = new ImageIcon(this.getClass().getResource("/pGreen.jpg"));
					JFrame frame = new JFrame("!!! *** THE WINNER *** !!!");
					frame.setSize(1000, 300);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					JPanel jp = new JPanel();
					frame.add(jp, BorderLayout.CENTER);

					if (value > 0) {
						jp.add(jp4);
						jp.add(new JLabel(pHandOne.Show_Hand()));
						System.out.println(names[idx]);
						jp4.setBackground(Color.RED);

					} else if (value < 0) {
						jp.add(jp5);
						jp.add(new JLabel(pHandTwo.Show_Hand()));
						System.out.println(names[idx2]);
						jp5.setBackground(Color.RED);

					} else if (value == 0) {
						jp.add(new JLabel("It is a tie"));
					}
				}
			}
		});

// Creaate a Jlabel set the text in the Jlable
		jf.pack();
		jf.setResizable(true);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(UI::new);
	}

	private JPanel shuffleDeckAndDealCards(Deck deck) {
		Collections.shuffle(deck);
		deck.stream().forEach(System.out::println);
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(ROWS, COLUMNS));
		for (int i = 0; i < CARDS_DEALT; i++) {
			Card card = deck.dealCard();
			JLabel jl = new JLabel(new ImageIcon(card.getImage()));
			jl.putClientProperty("CARD", card);
			jl.setBorder(new BevelBorder(BevelBorder.LOWERED));
			jl.setEnabled(true);
			jp.add(jl);
		}
		return jp;
	}

	private static BufferedImage loadImage(String s) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new FileInputStream(s));
			BufferedImage bix = new BufferedImage((int) (bi.getWidth() * SCALE), (int) (bi.getHeight() * SCALE),
					bi.getType());
			Graphics2D g2d = bix.createGraphics();
			g2d.drawRenderedImage(bi, AffineTransform.getScaleInstance(SCALE, SCALE));
			g2d.dispose();
			bi = bix;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
		return bi;
	}

	static class Deck extends LinkedList<Card> {
		private static final long serialVersionUID = 1L;

		public Deck() {
			loadDeck();
		}

		protected void loadDeck() {
			BufferedImage biBack = loadImage("./images/gray_back.png");
			for (Suit suit : Suit.values()) {
				for (Rank rank : Rank.values()) {
					this.add(new Card(suit, rank, biBack, FACE_UP));
				}
			}
		}

		protected Card dealCard() {
			Card card = this.removeFirst();
			card.setImage(loadImage("./images/" + card.getRank().getRankId() + card.getSuit().getSuitId() + ".png"));
			return card;
		}
	}

	static class Card {
		private Suit suit;
		private Rank rank;

		private boolean faceUp;

		private BufferedImage biFront = null;
		private BufferedImage biBack;

		public Card(Suit suit, Rank rank, BufferedImage biBack, boolean faceUp) {
			this.suit = suit;
			this.rank = rank;
			this.biBack = biBack;
			this.faceUp = faceUp;
		}

		public Suit getSuit() {
			return this.suit;
		}

		public Rank getRank() {
			return this.rank;
		}

		public BufferedImage getImage() {
			BufferedImage bi = this.biBack;
			if (faceUp) {
				bi = this.biFront;
			}
			return bi;
		}

		public void setImage(BufferedImage biFront) {
			this.biFront = biFront;
		}

		public void toggle() {
			this.faceUp = !faceUp;
		}

		@Override
		public String toString() {
			return rank + " of " + suit;
		}

		public enum Suit {
			Clubs("C"), Diamonds("D"), Hearts("H"), Spades("S");

			private final String suitId;

			private Suit(String suitId) {
				this.suitId = suitId;
			}

			public String getSuitId() {
				return this.suitId;
			}
		}

		public enum Rank {
			Ace("A"), Deuce("2"), Three("3"), Four("4"), Five("5"), Six("6"), Seven("7"), Eight("8"), Nine("9"),
			Ten("10"), Jack("J"), Queen("Q"), King("K");

			private final String rankId;

			private Rank(String rankId) {
				this.rankId = rankId;
			}

			public String getRankId() {
				return this.rankId;
			}
		}
	}

}
