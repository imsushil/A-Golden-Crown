package com.geektrust.tameofthrones.pojo;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.geektrust.tameofthrones.exceptions.InvalidKingdomNameException;
import com.geektrust.tameofthrones.exceptions.InvalidMessageFormatException;


public class KingTest {
	private King king;
	
	@Before
	public void setUp() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, IOException {
		Field instance = King.class.getDeclaredField("instance");
		instance.setAccessible(true);
		instance.set(null, null);
		king = King.getInstance();
		// resetting ally field to false before start of the test
		Map<String, Kingdom> kingdoms = Southeros.getInstance().getKingdoms();
		for(Entry<String, Kingdom> item: kingdoms.entrySet()) {
			Kingdom kingdom = item.getValue();
			Field kingdomsField = Kingdom.class.getDeclaredField("ally");
			kingdomsField.setAccessible(true);
			kingdomsField.set(kingdom, false);
		}
	}
	
	/*
	 * Setting up of the messages
	 * King Shan sends the messages to respective kingdoms
	 * */
	private void sendMessages() throws IOException, InvalidMessageFormatException, InvalidKingdomNameException {
		king.addMessage("AIR OWLAOWLBOWLC");
		king.addMessage("LAND OFBBMUFDICCSO");
		king.addMessage("ICE MOMAMVTMTMHTM");
		king.addMessage("WATER SUMMER IS COMING");
		king.addMessage("FIRE AJXGAMUTA");
		king.sendMessages();
	}
	
	/*
	 * No messages are sent to the kingdoms
	 * Returns NONE
	 * */
	@Test
	public void rulerShouldBeNone() throws IOException, InvalidMessageFormatException, InvalidKingdomNameException {
		String ruler = this.king.getRuler();
		Assert.assertEquals("NONE", ruler);
	}
	
	/*
	 * Messages are sent via sendMessages method
	 * Returns SPACE as ruler kingdom
	 * */
	@Test
	public void rulerShouldBeSpace() throws IOException, InvalidMessageFormatException, InvalidKingdomNameException {
		this.sendMessages();
		String ruler = this.king.getRuler();
		Assert.assertEquals("SPACE", ruler);
	}
	
	/*
	 * The ruler should win 3 allies namely FIRE, ICE, LAND
	 */
	@Test
	public void rulerShouldBeSpaceWithAlliesFireIceLand() throws IOException, InvalidMessageFormatException, InvalidKingdomNameException {
		this.sendMessages();
		Assert.assertThat(Arrays.asList(this.king.getRulerWithAllies().split(" ")), CoreMatchers.hasItems("SPACE", "FIRE", "ICE", "LAND"));
	}
	
	/*
	 * There is no ruler as King Shan is unable to win over minimum required kingdoms.
	 * So, the output is NONE 
	 */
	@Test
	public void ruleShouldBeNoneWithNoAllies() throws IOException, InvalidMessageFormatException, InvalidKingdomNameException {
		king.addMessage("AIR OWLAOWLBOWLC");
		king.addMessage("LAND OFBBMUFDICCSO");
		king.addMessage("ICE MOPTHQKIPADPET");
		king.addMessage("WATER SUMMER IS COMING");
		king.sendMessages();
		Assert.assertEquals("NONE", king.getRulerWithAllies());
	}
	
	/*
	 * Kingdom name is AER which is an invalid kingdom name so it throws InvalidKingdomNameException
	 * 
	 */
	@Test(expected=InvalidKingdomNameException.class)
	public void shouldThrowInvalidKingdomNameException() throws IOException, InvalidMessageFormatException, InvalidKingdomNameException {
		king.addMessage("AER OWLAOWLBOWLC");
		king.addMessage("LAND OFBBMUFDICCSO");
		king.addMessage("ICE MOPTHQKIPADPET");
		king.addMessage("WATER SUMMER IS COMING");
		king.sendMessages();
	}
	
	/*
	 * Message format should be: KINGDOM_NAME Message
	 * The message format is invalid so it throws InvalidMessageFormatException   
	 * 
	 */
	@Test(expected = InvalidMessageFormatException.class)
	public void shouldThrowInvalidMessageFormatException() throws IOException, InvalidMessageFormatException, InvalidKingdomNameException {
		king.addMessage("AIROWLAOWLBOWLC");
		king.sendMessages();
	}
}
