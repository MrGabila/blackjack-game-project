package tests;

import deckOfCards.*;
import blackjack.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.*;
import org.junit.Test;

public class PublicTests {

	@Test
	public void testDeckConstructorAndDealOneCard() {
		Deck deck = new Deck();
		for (int suitCounter = 0; suitCounter < 4; suitCounter++) {
			for (int valueCounter = 0; valueCounter < 13; valueCounter++) {
				Card card = deck.dealOneCard();
				assertEquals(card.getSuit().ordinal(), suitCounter);
				assertEquals(card.getRank().ordinal(), valueCounter);
			}
		}
	}
	
	/* This test will pass only if an IndexOutOfBoundsException is thrown */
	@Test (expected = IndexOutOfBoundsException.class)
	public void testDeckSize() {
		Deck deck = new Deck();
		for (int i = 0; i < 53; i++) {  // one too many -- should throw exception
			deck.dealOneCard();
		}
	}

	@Test
	public void testDeckShuffle() {
		Deck deck = new Deck();
		Random random = new Random(1234);
		deck.shuffle(random);
		assertEquals(new Card(Rank.KING, Suit.CLUBS), deck.dealOneCard());
		assertEquals(new Card(Rank.TEN, Suit.CLUBS), deck.dealOneCard());
		assertEquals(new Card(Rank.JACK, Suit.SPADES), deck.dealOneCard());
		for (int i = 0; i < 20; i++) {
			deck.dealOneCard();
		}
		assertEquals(new Card(Rank.SIX, Suit.CLUBS), deck.dealOneCard());
		assertEquals(new Card(Rank.FIVE, Suit.CLUBS), deck.dealOneCard());
		for (int i = 0; i < 24; i++) {
			deck.dealOneCard();
		}
		assertEquals(new Card(Rank.EIGHT, Suit.CLUBS), deck.dealOneCard());
		assertEquals(new Card(Rank.JACK, Suit.HEARTS), deck.dealOneCard());
		assertEquals(new Card(Rank.JACK, Suit.CLUBS), deck.dealOneCard());
	}
	
	@Test
	public void testGameBasics() {
		Random random = new Random(3723);
		BlackjackModel game = new BlackjackModel();
		game.createAndShuffleDeck(random);
		game.initialPlayerCards();
		game.initialDealerCards();
		game.playerTakeCard();
		game.dealerTakeCard();
		ArrayList<Card> playerCards = game.getPlayerCards();
		ArrayList<Card> dealerCards = game.getDealerCards();
		assertTrue(playerCards.get(0).equals(new Card(Rank.QUEEN, Suit.HEARTS)));
		assertTrue(playerCards.get(1).equals(new Card(Rank.SIX, Suit.DIAMONDS)));
		assertTrue(playerCards.get(2).equals(new Card(Rank.EIGHT, Suit.HEARTS)));
		assertTrue(dealerCards.get(0).equals(new Card(Rank.THREE, Suit.CLUBS)));
		assertTrue(dealerCards.get(1).equals(new Card(Rank.NINE, Suit.SPADES)));
		assertTrue(dealerCards.get(2).equals(new Card(Rank.FIVE, Suit.CLUBS)));		
	}
	
	@Test
	public void testPossibleHandValues() {
		ArrayList<Card> hand = new ArrayList<>();
		ArrayList<Card> hand1 = new ArrayList<>();
		
		hand.add(new Card(Rank.TEN, Suit.HEARTS));
		//hand.add(new Card(Rank.ACE, Suit.SPADES));
		hand.add(new Card(Rank.SEVEN, Suit.SPADES));
		hand.add(new Card(Rank.FIVE, Suit.DIAMONDS));
		
		hand1.add(new Card(Rank.ACE, Suit.CLUBS));
		hand1.add(new Card(Rank.SIX, Suit.DIAMONDS));
		hand1.add(new Card(Rank.FIVE, Suit.DIAMONDS));
		
		ArrayList<Integer> test = new ArrayList<>();
		ArrayList<Integer> test1 = new ArrayList<>();
		
		
		test.add(22);
		test1.add(12);
		
		assertTrue(Collections.max(test).equals(22));
		assertTrue(BlackjackModel.possibleHandValues(hand).equals(test));
		assertTrue(BlackjackModel.possibleHandValues(hand1).equals(test1));
	}
	
	@Test
	public void testDealerHand() {
		BlackjackModel game = new BlackjackModel();
		
		ArrayList<Card> hand = new ArrayList<>();
		
		hand.add(new Card(Rank.QUEEN, Suit.HEARTS));
		hand.add(new Card(Rank.ACE, Suit.SPADES));
		hand.add(new Card(Rank.ACE, Suit.CLUBS));
		hand.add(new Card(Rank.FOUR, Suit.DIAMONDS));
		
		game.setDealerCards(hand);
		
		assertEquals(game.dealerShouldTakeCard(), true);
		
	}
	
	@Test
	public void testAssessHand() {
		BlackjackModel game = new BlackjackModel();
		
		ArrayList<Card> hand = new ArrayList<>();
		
		hand.add(new Card(Rank.TEN, Suit.HEARTS));
		hand.add(new Card(Rank.NINE, Suit.SPADES));
		hand.add(new Card(Rank.TWO, Suit.CLUBS));
		//hand.add(new Card(Rank.FOUR, Suit.DIAMONDS));
		
		game.setDealerCards(hand);
		
		assertEquals(BlackjackModel.assessHand(hand), HandAssessment.NORMAL);
		
	}
	
}
