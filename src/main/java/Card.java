public class Card {
    String suit;
    int value;

    Card(String theSuit, int theValue) {
        suit = theSuit;
        value = theValue;
    }
    //returns the name of this specific card's image
    public String cardName(){
        return this.value + this.suit + ".jpg";
    }
}
