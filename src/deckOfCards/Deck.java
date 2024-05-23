package deckOfCards;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
/**
 * The Deck class initializes 52 cards, with ranks for each suit. 
 * The Deck can deal one card at a time and shuffle the cards.
 * 
 * @author Mr. Gabila
 */
public class Deck {

	private ArrayList<Card> cards;

	/* ----------------Standard Constructor------------------------ */
	public Deck() {
		cards = new ArrayList<Card>(52);
		
		for (int suitCounter = 0; suitCounter < 4; suitCounter++) {
			for (int rankCounter = 0; rankCounter < 13; rankCounter++) {
				cards.add(new Card(Rank.values()[rankCounter], 
									Suit.values()[suitCounter]));
			}
	
		}
	}
	
	/*
	 * This method takes one @param randomNumberGenerator, and uses it to 
	 * shuffle the deck of cards
	 */
	public void shuffle(Random randomNumberGenerator) {
		Collections.shuffle(cards, randomNumberGenerator);
	}
	
	/*
	 * This method will remove one card from the front of the list (index 0) 
	 * and @return the card removed.
	 */
	public Card dealOneCard() {
		Card topcard = cards.get(0);
		cards.remove(0);
		return topcard;
	}
}
