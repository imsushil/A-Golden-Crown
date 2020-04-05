package com.geektrust.tameofthrones.pojo;

import java.io.IOException;

import com.geektrust.tameofthrones.exceptions.InvalidKingdomNameException;
import com.geektrust.tameofthrones.exceptions.InvalidMessageFormatException;

/**
 * Each Kingdom is part of Southeros universe.
 * Kingdoms have the following data members.
 * name - kingdom's name
 * emblem - kingdom's emblem
 * ally - true if the kingdom is an ally of ruler otherwise false 
 * 
 * @author sushil
 */
public class Kingdom {
	private String name;
	private String emblem;
	private boolean ally;
	
	/* PUBLIC CONSTRUCTORS */
	public Kingdom() {}
	
	public Kingdom(String name, String emblem) {
		super();
		this.name = name;
		this.emblem = emblem;
	}

	
	
	/* ================================== 
	 * PUBLIC METHODS 
	 * ==================================
	 */
	
	/**
	 * This method takes the message as input and verifies it using MessageVerifier
	 * 
	 * @param message
	 * @return
	 * @throws IOException
	 * @throws InvalidKingdomNameException
	 * @throws InvalidMessageFormatException 
	 */
	public boolean verifyMessage(String message) throws IOException, InvalidKingdomNameException, InvalidMessageFormatException {
		String decryptedMessage = MessageVerifier.decryptMessage(message, this.emblem.length());
		this.ally = MessageVerifier.verify(this.emblem, decryptedMessage);
		return this.ally;
	}

	/**
	 * 
	 * @return true if the kingdom is an ally of the ruler, otherwise false
	 */
	public boolean isAlly() {
		return ally;
	}
	
	public String getEmblem() {
		return this.emblem;
	}
	
}
