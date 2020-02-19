package controller;

import model.Card;

public class CardController {
	
	private int pinId = 1234;
	private int customerId = 1;
	private String cardNumber = "123456789123";

	/**
	 * This method use to validate the pin
	 * @param pin
	 * @return
	 */
	public Card pinValidation(int pin) {
		Card card = null;
		if (pin == pinId) {
			card = new Card();
			card.setCustomerId(customerId);
			card.setPinId(pinId);
			card.setCardNumber(cardNumber);
		}
		return card;
	}
}
