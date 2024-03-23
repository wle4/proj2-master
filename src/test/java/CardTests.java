import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
/*
class Card
[x] String suit
[x] int value
[x] Card(String theSuit, int theValue);
*/
public class CardTests {

    //------TESTS FOR CREATING CARDS------
    //checking if the card is correctly created
    @Test
    void createValidCardTest() {
        Card card = new Card("hearts", 10);
        assertNotNull(card, "Card: Failed to create a valid card");
        assertEquals("hearts", card.suit, "Card: Incorrect suit for the card");
        assertEquals(10, card.value, "Card: Incorrect value for the card");
    }

    //checking if the correct name is returned (this function returns the name of the image)
    @Test
    void createCardInvalidSuitTest() {
        Card card = new Card("hearts", 1);

        assertEquals("1hearts.jpg", card.cardName(), "Card: Incorrect image file name for the card");
    }
    //------END OF TESTS FOR CREATING CARDS------
}