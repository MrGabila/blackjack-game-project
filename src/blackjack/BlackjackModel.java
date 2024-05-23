package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import deckOfCards.*;

/**
 * Model for the Blackjack Simulation. The model consists of a List of dealer 
 * cards, a List of player cards, and a Deck of cards the game will be played with              
 * <p>                                                               
 * Each time the simulation is re-started a new Model object is created.        
 * <p>                                                                                                       
 *                                                                           
 * @author Mr. Gabila          
 */

public class BlackjackModel {
	
	/*an ArrayList of Cards that will be used to store the dealer's cards. */	
	private ArrayList<Card> dealerCards;
	/* an ArrayList of Cards that will be used to store the player's cards.*/
	private ArrayList<Card> playerCards;
	/* a Deck variable to represent the Deck of cards for the game.*/
	private Deck deck;
	
	/*-----------------------Getters-----------------------------*/
	public ArrayList<Card> getDealerCards(){
		return new ArrayList<Card>(dealerCards);
	}
	 
	public ArrayList<Card> getPlayerCards() {
		return new ArrayList<Card>(playerCards);
	}
	
	/*------------------------Setters------------------------------*/
	public void setDealerCards(ArrayList<Card> cards) {
		dealerCards = new ArrayList<>(cards);
	}
	
	public void setPlayerCards(ArrayList<Card> cards) {
		playerCards = new ArrayList<>(cards);
	}

	/*----------------------Instance Methods--------------------------*/
	/*This method will assign a new instance of the Deck class to the deck
	 * variable, and will then shuffle the deck, passing the @param (random)
	 * along to the deck's shuffle method.
	 */	
	public void createAndShuffleDeck(Random random) {
		deck = new Deck();
		deck.shuffle(random);
	}
	
	/* The next two methods will instantiate the respective list of cards
	 * (playerCards or dealerCards) and then will deal two cards from the deck 
	 * and add them to that list. 
	 * Assume createAndShuffleDeck has already been called
	 */
	public void initialDealerCards() {
		dealerCards = new ArrayList<>();
		dealerCards.add(deck.dealOneCard());
		dealerCards.add(deck.dealOneCard());
	}
	
	public void initialPlayerCards() {
		playerCards =new ArrayList<>();
		playerCards.add(deck.dealOneCard());
		playerCards.add(deck.dealOneCard());
	}
	
	/* The next two methods will simply deal a card from the deck and add it to
	 * either the playerCards list or the dealerCards list. 
	 * Assume initialPlayerCards and initialDealerCards has been called before.
	 */
	public void playerTakeCard() {
		playerCards.add(deck.dealOneCard());
	}
	public void dealerTakeCard() {
		dealerCards.add(deck.dealOneCard());
	}

	/* This method takes an ArrayList and returns a very short
	 * ArrayList that contains either one or two Integers(where Aces are present)
	 * representing the value(s) that could be assigned to that hand. 
	 * Method assumes that the hand consists of at least 2 cards. 
	 * @param ArryaList<Card>
	 */
	public static ArrayList<Integer> possibleHandValues(ArrayList<Card> hand) {
		ArrayList<Integer> possibleValues = new ArrayList<>();
		ArrayList<Rank> rank =  arrayOfRanks(hand);
		int nonAceValues = 0;
		int aceCounter = 0;
		
		for (Rank rankMember : rank) {
			if (rankMember == Rank.ACE) {
				aceCounter++; //count the number of Aces
			} else {
				nonAceValues += rankMember.getValue(); //add the non-Ace values
			}
		}
		
		if (aceCounter == 0) { //if there are no aces in the hand
			possibleValues.add(nonAceValues);
			return possibleValues;
		} else if (aceCounter == 1){
			possibleValues.add(1 + nonAceValues);
			possibleValues.add(11 + nonAceValues);
		} else if (aceCounter == 2) {
			possibleValues.add(2 + nonAceValues);
			possibleValues.add(12 + nonAceValues);
			possibleValues.add(22 + nonAceValues);
		} else if (aceCounter == 3) {
			possibleValues.add(3 + nonAceValues);
			possibleValues.add(13 + nonAceValues);
			possibleValues.add(23 + nonAceValues);
			possibleValues.add(33 + nonAceValues);
		} else if (aceCounter == 4) {
			possibleValues.add(4 + nonAceValues);
			possibleValues.add(14 + nonAceValues);
			possibleValues.add(24 + nonAceValues);
			possibleValues.add(34 + nonAceValues); 
			possibleValues.add(44 + nonAceValues);
		}
		
		for (int index = possibleValues.size() - 1; index >= 0; index--) {
			if (possibleValues.get(index) > 21 && index != 0) {
				possibleValues.remove(index);
			}
		}
		return possibleValues;
	}
	
	/*
	 * Takes an ArrayList of Cards and return an ArrayList of Ranks of each card
	 */
	private static ArrayList<Rank> arrayOfRanks(ArrayList<Card> array) {
		ArrayList<Rank> rank = new ArrayList<>();
		for (Card card : array) {
			rank.add(card.getRank());
		}
		Collections.sort(rank);
		return rank;
	}

	/* This method take an ArrayList of Cards and assess the hand and 
	 * return one of the four HandAssessment constants, see HandAssesment Enum.
	 * 
	 * @param AarrayList
	 * @return HandAssessment Symbolic Constants 
	 */
	public static HandAssessment assessHand(ArrayList<Card> hand) {
		if (hand.size() < 2 || hand == null) {
			return HandAssessment.INSUFFICIENT_CARDS;
		} else if (possibleHandValues(hand).get(0) > 21) {
			return HandAssessment.BUST;
		} else if (hand.size() == 2 && possibleHandValues(hand).contains(21)) {
			return HandAssessment.NATURAL_BLACKJACK;
		} else {
			return HandAssessment.NORMAL;
		}
	}
	
	/* This method will look at the playerCards and the dealerCards and determine
	 * the outcome of the game, returning one of the GameResult constants
	 * (PLAYER_WON, PLAYER_LOST, PUSH, or NATURAL_BLACKJACK). PUSH is a draw, 
	 * and NATURAL_BLACKJACK is when the player wins 1.5 times the bet
	 */
	public GameResult gameAssessment() {
		ArrayList<Integer> dealerHandValue = possibleHandValues(dealerCards);
		ArrayList<Integer> playerHandValue = possibleHandValues(playerCards);
		dealerHandValue.add(0);
		playerHandValue.add(0);
		
		int playerValue = Collections.max(playerHandValue);
		int dealerValue = Collections.max(dealerHandValue);
		
		if (assessHand(playerCards) == HandAssessment.NATURAL_BLACKJACK && 
				(assessHand(dealerCards) != HandAssessment.NATURAL_BLACKJACK)) {
			return GameResult.NATURAL_BLACKJACK;
		} else if (assessHand(playerCards) == HandAssessment.NATURAL_BLACKJACK && 
				(assessHand(dealerCards) == HandAssessment.NATURAL_BLACKJACK)) {
			return GameResult.PUSH;
		} else if (assessHand(playerCards) == HandAssessment.BUST) {
			return GameResult.PLAYER_LOST;
		} else if (assessHand(playerCards) == HandAssessment.NORMAL && 
				(assessHand(dealerCards) == HandAssessment.BUST)) {
			return GameResult.PLAYER_WON;
		} else {
			if (playerValue > dealerValue) {
				return GameResult.PLAYER_WON;
			} else if (playerValue < dealerValue) {
				return GameResult.PLAYER_LOST;
			} else {
				return GameResult.PUSH;
			}
		}
	}
	
	/* This method will look at the dealerCards and determine whether or not the
	 * dealer should take another card during her turn, returning a boolean;
	 *  true if the dealer should take another card and false otherwise.
	 */
	public boolean dealerShouldTakeCard() {
		ArrayList<Integer> dealerValues = possibleHandValues(dealerCards);
		dealerValues.add(0);
		
		int dealerMax = Collections.max(dealerValues);
		if (dealerMax <= 16) {
			return true;
		} else if (dealerMax >= 18) {
			return false;
		} else if (dealerMax == 17 && dealerValues.contains(7)) {
			return true;
		} else {
			return false;
		}
	}
}
