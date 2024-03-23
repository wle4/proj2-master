import java.util.ArrayList;
import java.util.Collections;

public class BlackjackDealer{
    public ArrayList<Card> deck; //where all the cards will be, index 0 being the top of the deck, and the last index being the bottom of the deck

    public void generateDeck() {
        if(deck != null) { //resets the current deck and regenerates it
            deck.clear();
        }
        deck = new ArrayList<>();

        String[] suits = {"hearts", "diamonds", "clubs", "spades"};
        int[] values = {1,2,3,4,5,6,7,8,9,10,11,12,13}; //1 = ace, 11 = jack, 12 = queen, 13 = king, rest are normal values

        for(String suit:suits){ //double for loop that will combine each value with each suit
            for(int value: values){
                deck.add(new Card(suit,value));
            }
        }
    }

    public ArrayList<Card> dealHand(){
        if(deck == null || deck.isEmpty()){ //making sure that the deck was generated correctly before dealing hands
            generateDeck();
        }

        ArrayList<Card> dealerCards = new ArrayList<>(); //the arraylist that will hold on to the two cards given to the player/dealer
        dealerCards.add(deck.get(0)); //grabs the first two cards from the deck and stores it in the new arraylist
        dealerCards.add(deck.get(1));
        //removes the first two cards from the deck
        deck.remove(0);
        deck.remove(0);
        //returns the two cards grabbed from the deck
        return dealerCards;
    }

    public Card drawOne(){
        if(deck == null || deck.isEmpty()){ //making sure that the deck was generated correctly before drawing one card
            generateDeck();
        }

        Card topcard = deck.get(0); //grabs a card from the top of the deck (given that the deck is already shuffled)
        deck.remove(0); //removes that card from the deck (removes first index)
        return topcard; //returns the card
    }

    public void shuffleDeck(){
        //checks if deck is not initialized before shuffling
        if(deck == null){
            generateDeck();
        }
        //checks if deck does not have the correct number of cards before shuffling
        else if(deck.size() != 52) {
            deck.clear();
            generateDeck();
        }
        Collections.shuffle(deck); //uses Collections.shuffle to randomize the deck
    }

    public int deckSize(){
        return deck.size(); //returns the size of the deck
    }
}

