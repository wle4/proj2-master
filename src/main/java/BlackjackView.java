import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BlackjackView extends Application {
	String css = getClass().getResource("styles.css").toExternalForm(); //since multiple scenes will be referencing from the .css file, this is left here

	double money;
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("BLACKJACK");
		//initialize features of the window
		ImageView icon = new ImageView(new Image("titleicon.png"));
		Button start = new Button("START");
		Button quit = new Button("QUIT");

		start.getStyleClass().add("titleButtons"); //begins the game when clicked
		quit.getStyleClass().add("titleButtons"); //quits the game when clicked
		icon.setFitWidth(150);
		icon.setFitHeight(150);

		TextField title = new TextField("BLACKJACK");
		title.setEditable(false);
		title.getStyleClass().add("title");
		title.setFocusTraversable(false);

		StackPane iconPane = new StackPane(icon);
		StackPane.setAlignment(icon, Pos.BOTTOM_RIGHT);
		StackPane.setMargin(icon, new Insets(30));

		VBox v1 = new VBox(30, title, start, quit);
		HBox h1 = new HBox(20, v1);

		v1.setAlignment(Pos.CENTER);
		h1.setAlignment(Pos.CENTER);
		//setting font
		h1.setStyle("-fx-font-family: 'sans-serif'");

		BorderPane pane = new BorderPane();
		pane.setCenter(h1);
		pane.setBottom(iconPane);

		Scene titlescene = new Scene(pane, 800,600);
		titlescene.getRoot().getStyleClass().add("background");
		titlescene.getStylesheets().add(css);

		primaryStage.setScene(titlescene);
		primaryStage.show();

		start.setOnAction(e -> moneyScene(primaryStage));
		quit.setOnAction(e->{
			primaryStage.close();
		});
	}

	public void moneyScene(Stage primaryStage) {
		//setting up for the game starts here
		BlackjackGame game = new BlackjackGame();
		Button entermoney = new Button("ENTER MONEY");
		TextField mymoney = new TextField(); // current money input

		mymoney.setMaxWidth(300);
		mymoney.setPadding(new Insets(20));

		VBox moneySetup = new VBox(10, mymoney, entermoney);
		mymoney.setPromptText("Enter starting money");

		mymoney.setAlignment(Pos.CENTER);
		entermoney.setAlignment(Pos.CENTER);
		moneySetup.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setCenter(moneySetup);

		entermoney.setOnAction(e -> {
			try {
				money = Double.parseDouble(mymoney.getText());
				game.money = money;
				betScene(primaryStage, game);
			} catch (NumberFormatException ex) {
				mymoney.clear();
				mymoney.setPromptText("Error: enter valid starting money");
			}
		});

		Scene newScene = new Scene(pane, 800,600);
		newScene.getRoot().getStyleClass().add("background");
		entermoney.getStyleClass().add("custombutton");
		newScene.getStylesheets().add(css);

		// Set the new scene to the stage
		primaryStage.setScene(newScene);
	}

	public void betScene(Stage primaryStage, BlackjackGame game) {
		// Create an empty layout for the new scene
		BorderPane pane = new BorderPane();

		TextField textmoney = new TextField("Current money: " + String.format("$%.2f", game.money));
		TextField betinput = new TextField(); // bet money input

		textmoney.getStyleClass().add("moneybox");
		textmoney.setEditable(false);
		textmoney.setAlignment(Pos.TOP_CENTER);

		betinput.setPromptText("Enter a bet");
		betinput.setMaxWidth(100);

		Button bet = new Button("BET");

		bet.setOnAction(event -> { // bet button clicked, set bet
			try {
				double user = Double.parseDouble(betinput.getText());
				if(user <= game.money && user > 0) {
					game.setBet(user); // convert bet money input to double
					betinput.setEditable(false);
					bet.setDisable(true);
					betinput.clear();
					betinput.setPromptText("");
					gameScene(primaryStage, game);
				}
				else{
					betinput.clear();
					betinput.setPromptText("Error: enter valid bet");
				}
			}
			catch (NumberFormatException e){
				betinput.clear();
				betinput.setPromptText("Error: enter valid bet");
			}
		});

		VBox directions = new VBox(10, betinput, bet);
		directions.setAlignment(Pos.CENTER_RIGHT);

		VBox directionsWrapper = new VBox(textmoney, directions);
		directionsWrapper.setPadding(new Insets(10, 20, 0, 0));

		pane.setCenter(directionsWrapper);

		Scene newScene = new Scene(pane, 800,600);
		newScene.getRoot().getStyleClass().add("background");
		bet.getStyleClass().add("custombutton");
		textmoney.getStyleClass().add("moneybox");
		newScene.getStylesheets().add(css);

		// Set the new scene to the stage
		primaryStage.setScene(newScene);
	}

	public void gameScene(Stage primaryStage, BlackjackGame game) {
		game.newRound(); //resets the deck and hands when previous rounds are played

		BorderPane pane = new BorderPane();
		//initializes player's and dealer's deck
		HBox dealerDeck = dealerCards(game);
		HBox playerDeck = playersCards(game);
		HBox revealedDealerDeck = revealDealer(game);
		//player's current money text field
		TextField textmoney = new TextField(String.format("$%.2f", game.money));
		// buttons
		Button hit = new Button("HIT");
		Button stand = new Button("STAND");
		Button next = new Button("NEXT");
		Button quit = new Button("QUIT");
		// make buttons to vbox
		VBox buttons = new VBox(10,hit,stand);
		VBox nextquitbuttons = new VBox(10, next, quit);

		// set styles to text fields and HBoxes/VBoxes
		dealerDeck.setAlignment(Pos.CENTER_LEFT);
		playerDeck.setAlignment(Pos.CENTER_LEFT);
		revealedDealerDeck.setAlignment(Pos.CENTER_LEFT);
		textmoney.setEditable(false);
		textmoney.getStyleClass().add("moneybox"); //begins the game when clicked

		// set styles to buttons
		hit.getStyleClass().add("custombutton");
		stand.getStyleClass().add("custombutton");
		next.getStyleClass().add("custombutton");
		quit.getStyleClass().add("custombutton");
		buttons.setAlignment(Pos.CENTER_RIGHT);
		buttons.setPadding(new Insets(30));
		nextquitbuttons.setPadding(new Insets(30));
		nextquitbuttons.setAlignment(Pos.BOTTOM_LEFT);

		pane.setRight(buttons);
		//set up the buttons to their respective actions
		next.setOnAction(e->{
			try {
				//if the player has run out of money, automatically go back to the money setup scene
				if(game.money <= 0){
					moneyScene(primaryStage);
				}
				else {
					betScene(primaryStage, game);
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		});
		quit.setOnAction(e->{
			try {
				start(primaryStage);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		});
		hit.setOnAction(eventhit -> {
			Card newCard = game.hitCard();
			// new game card
			String newCardName = newCard.cardName();
			ImageView newCardImage = new ImageView(new Image(newCardName));
			newCardImage.setPreserveRatio(true);
			newCardImage.setFitWidth(80);

			playerDeck.getChildren().add(newCardImage);
			//handles player bust
			if (game.checkPlayerBust()) {
				pane.setCenter(new VBox(100, revealedDealerDeck, playerDeck));
				hit.setDisable(true);
				stand.setDisable(true);
				// player bust display
				TextField lost = new TextField("PLAYER BUST");
				TextField lostMoney = new TextField("You lost $" + String.format("%.2f", Math.abs(game.evaluateWinnings())));
				lost.setEditable(false);
				lost.getStyleClass().add("winloss");
				lost.setPrefWidth(400);
				lostMoney.setEditable(false);
				lostMoney.getStyleClass().add("winloss");
				lostMoney.setPrefWidth(400);
				VBox thebox = new VBox(10, lostMoney, lost, pane.getRight());
				pane.setRight(thebox);
				pane.setBottom(nextquitbuttons);
			}
		});
		stand.setOnAction(eventhit -> {
			pane.setCenter(new VBox(100, revealedDealerDeck, playerDeck));
			hit.setDisable(true);
			stand.setDisable(true);
			//handles adding cards to the dealer's deck while under 17
			while(game.gameLogic.evaluateBankerDraw(game.bankerHand)) {
				Card newCard = game.dealerHitCard();
				String newCardName = newCard.cardName();
				ImageView newCardImage = new ImageView(new Image(newCardName));
				newCardImage.setPreserveRatio(true);
				newCardImage.setFitWidth(80);

				revealedDealerDeck.getChildren().add(newCardImage);
			}
			//handles dealer bust
			if(game.checkDealerBust() && !game.playerHasBlackjack() && !game.dealerHasBlackjack()) {
				TextField lost = new TextField("DEALER BUST");
				lost.setEditable(false);
				lost.getStyleClass().add("winloss");
				VBox thebox = new VBox(lost, pane.getRight());
				pane.setRight(thebox);
				pane.setBottom(nextquitbuttons);
			}
			//prints out who won
			TextField whoWon = new TextField();

			double earned = game.evaluateWinnings();
			String temp;

			if (earned < 0) {
				temp = "You lost $" + String.format("%.2f", Math.abs(earned));
			}
			else if (earned > 0){
				temp = "You won $" + String.format("%.2f", earned);
			}
			else{
				temp = "Draw";
			}
			if(game.playerHasBlackjack() && !game.dealerHasBlackjack()){
				temp = "PLAYER BLACKJACK: " + temp;
			}
			if(game.dealerHasBlackjack() && !game.playerHasBlackjack()){
				temp = "DEALER BLACKJACK: " + temp;
			}

			whoWon.setText(temp);
			whoWon.setEditable(false);
			whoWon.getStyleClass().add("winloss");

			VBox thebox = new VBox(10, whoWon, pane.getRight());
			pane.setRight(thebox);
			pane.setBottom(nextquitbuttons);
		});

		// finish up scene
		VBox displayTable = new VBox(150,dealerDeck,playerDeck);
		displayTable.setPadding(new Insets(20));
		displayTable.setAlignment(Pos.CENTER_LEFT);

		pane.setCenter(displayTable);
		pane.setTop(textmoney);
		Scene newScene = new Scene(pane, 800,600);
		// add css
		newScene.getRoot().getStyleClass().add("background");
		newScene.getStylesheets().add(css);
		primaryStage.setScene(newScene);
		//handles when the first turn ends up with either player or dealer having a Blackjack
		if(game.playerHasBlackjack() || game.dealerHasBlackjack()){
			stand.fire();
		}
	}

	//shows the default dealer cards (one card shows and the other is hidden)
	public HBox dealerCards(BlackjackGame game) {
		String firstbankercard = game.bankerHand.get(0).cardName(); // banker card

		// images of banker cards
		ImageView show1bankercard = new ImageView(new Image(firstbankercard));
		ImageView dealercardcover = new ImageView(new Image("cardcover.jpg"));

		dealercardcover.setPreserveRatio(true);
		show1bankercard.setPreserveRatio(true);

		dealercardcover.setFitWidth(80);
		show1bankercard.setFitWidth(80);

        return new HBox(5, show1bankercard, dealercardcover);

	}
	//shows the player's original cards
	public HBox playersCards(BlackjackGame game) {
		String firstplayercard = game.playerHand.get(0).cardName();
		String secondplayercard = game.playerHand.get(1).cardName();

		ImageView show1playercard = new ImageView(new Image(firstplayercard));
		ImageView show2playercard = new ImageView(new Image(secondplayercard));

		show1playercard.setPreserveRatio(true);
		show2playercard.setPreserveRatio(true);

		show1playercard.setFitWidth(80);
		show2playercard.setFitWidth(80);

        return new HBox(5, show1playercard, show2playercard);
	}
	//reveals the dealer's cards once the player busts or presses "Hit"
	public HBox revealDealer(BlackjackGame game) {
		String newfirstdealercard = game.bankerHand.get(0).cardName();
		String newseconddealercard = game.bankerHand.get(1).cardName();

		ImageView show1dealercard = new ImageView(new Image(newfirstdealercard));
		ImageView show2dealercard = new ImageView(new Image(newseconddealercard));

		show1dealercard.setPreserveRatio(true);
		show2dealercard.setPreserveRatio(true);

		show1dealercard.setFitWidth(80);
		show2dealercard.setFitWidth(80);

        return new HBox(5, show1dealercard, show2dealercard);
	}

}
