package com.geektrust.tameofthrones.exceptions;

public class InvalidKingdomNameException extends Exception {
	private static final long serialVersionUID = 3670030000386740436L;

	public InvalidKingdomNameException(String msg) {
		super(msg);
	}
}
