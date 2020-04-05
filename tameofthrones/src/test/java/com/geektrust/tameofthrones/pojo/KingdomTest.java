package com.geektrust.tameofthrones.pojo;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.geektrust.tameofthrones.exceptions.InvalidKingdomNameException;
import com.geektrust.tameofthrones.exceptions.InvalidMessageFormatException;

public class KingdomTest {
	private Kingdom kingdom;
	
	@Before
	public void setUp() {
		kingdom = new Kingdom("LAND", "Panda");
	}
	
	/*
	 * POSITIVE TEST
	 * 
	 * kingdom: LAND
	 * message: FDIXXSOKKOFBBMU
	 * returns: AYDSSNJFFJAWWHP
	 * 
	 * Asserts true
	 */
	@Test
	public void decryptMessageTest() throws InvalidMessageFormatException {
		String message = "FDIXXSOKKOFBBMU";
		int key = kingdom.getEmblem().length();
		String result = MessageVerifier.decryptMessage(message, key);
		Assert.assertEquals("AYDSSNJFFJAWWHP", result);
	}
	
	/*
	 * POSITIVE TEST
	 * 
	 * kingdom is LAND and its emblem is Panda
	 * Input message: OFBBMUFDICCSO
	 * Output: true
	 * 
	 * Asserts true if message contains the emblem
	 * 
	 */
	@Test
	public void verifyMessageShouldReturnTrue() throws IOException, InvalidKingdomNameException, InvalidMessageFormatException {
		String msg = "OFBBMUFDICCSO";
		boolean result = kingdom.verifyMessage(msg);
		Assert.assertTrue(result);
	}
	
	
	/*
	 * NEGATIVE TEST
	 * 
	 * kingdom is LAND and its emblem is Panda
	 * Input message: OTBBMUFDICCSO
	 * Output: false
	 * 
	 */
	@Test
	public void verifyMessageShouldReturnFalse() throws IOException, InvalidKingdomNameException, InvalidMessageFormatException {
		String msg = "OTBBMUFDICCSO";
		boolean result = kingdom.verifyMessage(msg);
		Assert.assertFalse(result);
	}
}
