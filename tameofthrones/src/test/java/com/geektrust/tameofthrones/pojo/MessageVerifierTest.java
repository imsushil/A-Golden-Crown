package com.geektrust.tameofthrones.pojo;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.geektrust.tameofthrones.exceptions.InvalidKingdomNameException;

public class MessageVerifierTest {

	/*
	 * emblem: Dragon
	 * message: AJXGAMUTA
	 * returns true
	 * 
	 */
	@Test 
	public void givenCorrectMessageWhenVerifyThenTrue() throws InvalidKingdomNameException, IOException {
		String emblem = "Dragon";
		String message = "UDRAUGON";
		boolean result = MessageVerifier.verify(emblem, message);
		Assert.assertTrue(result); 
	}

	/*
	 * emblem: Dragon
	 * message: AMGGAMUTA
	 * returns false
	 * 
	 */
	@Test
	public void givenIncorrectMessageWhenVerifyThenFalse() throws InvalidKingdomNameException {
		String emblem = "Dragon";
		String message = "AMGGAMUTA";
		boolean result = MessageVerifier.verify(emblem, message);
		Assert.assertFalse(result);
	}

}
