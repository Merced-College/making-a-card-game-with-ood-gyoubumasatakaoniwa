//Henry Lam, Jacob Flores-Zepeda, Marisol Guerrero-chan, Jesus Rosalez

import java.util.Random;
import java.util.Scanner;

public class BlackJackNEW {
  private static Card[] cards = new Card[52]; //array to hold the deck of cards
  private static int currentCardIndex = 0; //index to keep track of the current card in the deck

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    boolean playAgain = true;
    
    while (playAgain) {
      initializeDeck(); //initialize the deck of cards
      shuffleDeck(); //shuffle the deck of cards
      
      int playerTotal = 0;
      int dealerTotal = 0;
      
      playerTotal = dealInitialPlayerCards(); //deal initial cards to the player
      dealerTotal = dealInitialDealerCards(); //deal initial cards to the dealer
      
      playerTotal = playerTurn(scanner, playerTotal); //player's turn to hit or stand
      
      if (playerTotal > 21) { //check if the player busted
        System.out.println("You busted! Dealer wins.");
        continue; //start a new game
      }
      
      dealerTotal = dealerTurn(dealerTotal); //dealer's turn to hit or stand
      
      determineWinner(playerTotal, dealerTotal); //determine the winner of the game
      
      System.out.println("Would you like to play another hand? (yes/no)");
      String playerDecision = scanner.nextLine().toLowerCase();
      
      while (!(playerDecision.equals("no") || playerDecision.equals("yes"))) {
        System.out.println("Invalid action. Please type 'yes' or 'no'.");
        playerDecision = scanner.nextLine().toLowerCase();
      }
      
      if (playerDecision.equals("no")) {
        playAgain = false; //exit the loop to end the game
      }
    }
    System.out.println("Thanks for playing!");
  }

  //initializes the deck with 52 cards
  private static void initializeDeck() {
    String[] SUITS = { "Hearts", "Diamonds", "Clubs", "Spades" };
    String[] RANKS = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace" };
    int suitsIndex = 0, rankIndex = 0;
    
    for (int i = 0; i < cards.length; i++) {
      cards[i] = new Card(Integer.parseInt(RANKS[rankIndex]), SUITS[suitsIndex], RANKS[rankIndex]);
      suitsIndex++;
      if (suitsIndex == SUITS.length) {
        suitsIndex = 0;
        rankIndex++;
      }
    }
  }

  //shuffles the deck of cards
  private static void shuffleDeck() {
    Random random = new Random();
    for (int i = 0; i < cards.length; i++) {
      int index = random.nextInt(cards.length);
      Card temp = cards[i];
      cards[i] = cards[index];
      cards[index] = temp;
    }
  }

  //deals initial cards to the player
  private static int dealInitialPlayerCards() {
    Card card1 = dealCard();
    Card card2 = dealCard();
    
    System.out.println("Your cards: " + card1.getRank() + " of " + card1.getSuit() + " and " + card2.getRank() + " of " + card2.getSuit());
    return card1.getValue() + card2.getValue();
  }

  //deals initial cards to the dealer
  private static int dealInitialDealerCards() {
    Card card1 = dealCard();
    System.out.println("Dealer's card: " + card1.getRank() + " of " + card1.getSuit());
    return card1.getValue();
  }

  //manages the player's turn
  private static int playerTurn(Scanner scanner, int playerTotal) {
    while (true) {
      System.out.println("Your total is " + playerTotal + ". Do you want to hit or stand?");
      String action = scanner.nextLine().toLowerCase();
      
      if (action.equals("hit")) {
        Card newCard = dealCard();
        playerTotal += newCard.getValue();
        System.out.println("You drew a " + newCard.getRank() + " of " + newCard.getSuit());
        
        if (playerTotal > 21) {
          System.out.println("You busted! Dealer wins.");
          return 0; //reset playerTotal for the next game
        }
      } else if (action.equals("stand")) {
        break; //exit the loop if the player stands
      } else {
        System.out.println("Invalid action. Please type 'hit' or 'stand'.");
      }
    }
    return playerTotal;
  }

  //manages the dealer's turn
  private static int dealerTurn(int dealerTotal) {
    while (dealerTotal < 17) {
      Card newCard = dealCard();
      dealerTotal += newCard.getValue();
    }
    System.out.println("Dealer's total is " + dealerTotal);
    return dealerTotal;
  }

  //determines the winner of the game
  private static void determineWinner(int playerTotal, int dealerTotal) {
    if (dealerTotal > 21 || playerTotal > dealerTotal) {
      System.out.println("You win!");
    } else if (dealerTotal == playerTotal) {
      System.out.println("It's a tie!");
    } else {
      System.out.println("Dealer wins!");
    }
  }

  //deals a card from the deck
  private static Card dealCard() {
    return cards[currentCardIndex++];
  }

//algorithm to calculate the best card value, considering Ace can be 1 or 11
private static int calculateBestValue(int total, Card card) {
  int cardValue = card.getValue();
  //if card is Ace, consider both values (1 and 11) to find the best total
  if (cardValue == 11) {
    //check if adding 11 keeps the total <= 21, otherwise use 1
    if (total + 11 > 21) {
      cardValue = 1;
    }
  }
  return cardValue;
}

//update playerTurn and dealerTurn methods to use the new calculateBestValue method
private static int playerTurn(Scanner scanner, int playerTotal) {
  while (true) {
    System.out.println("Your total is " + playerTotal + ". Do you want to hit or stand?");
    String action = scanner.nextLine().toLowerCase();
    
    if (action.equals("hit")) {
      Card newCard = dealCard();
      int cardValue = calculateBestValue(playerTotal, newCard);
      playerTotal += cardValue;
      System.out.println("You drew a " + newCard.getRank() + " of " + newCard.getSuit());
      
      if (playerTotal > 21) {
        System.out.println("You busted! Dealer wins.");
        return 0; //reset playerTotal for the next game
      }
    } else if (action.equals("stand")) {
      break; //exit the loop if the player stands
    } else {
      System.out.println("Invalid action. Please type 'hit' or 'stand'.");
    }
  }
  return playerTotal;
}

  private static int dealerTurn(int dealerTotal) {
    while (dealerTotal < 17) {
      Card newCard = dealCard();
      int cardValue = calculateBestValue(dealerTotal, newCard);
      dealerTotal += cardValue;
    }
    System.out.println("Dealer's total is " + dealerTotal);
    return dealerTotal;
  }

}
