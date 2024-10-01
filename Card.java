//Henry Lam, Jacob Flores-Zepeda, Marisol Guerrero-chan, Jesus Rosalez

public class Card {
  private int value; //value of the card
  private String suit; //suit of the card
  private String rank; //rank of the card

  //constructor to initialize the card
  public Card(int value, String suit, String rank) {
    this.value = value;
    this.suit = suit;
    this.rank = rank;
  }

  //getters
  public int getValue() {
    return value;
  }

  public String getSuit() {
    return suit;
  }

  public String getRank() {
    return rank;
  }

  //setters
  public void setValue(int value) {
    this.value = value;
  }

  public void setSuit(String suit) {
    this.suit = suit;
  }

  public void setRank(String rank) {
    this.rank = rank;
  }

  //method to display card information
  @Override
  public String toString() {
    return rank + " of " + suit;
  }
}
