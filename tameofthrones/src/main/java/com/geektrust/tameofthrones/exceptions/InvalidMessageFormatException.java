package com.geektrust.tameofthrones.exceptions;

public class InvalidMessageFormatException extends Exception {
	private static final long serialVersionUID = 118165766431193541L;

	public InvalidMessageFormatException(String msg) {
		super(msg);
	}
}
