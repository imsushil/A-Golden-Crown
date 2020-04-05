package com.geektrust.tameofthrones.pojo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import com.geektrust.tameofthrones.exceptions.InvalidKingdomNameException;
import com.geektrust.tameofthrones.exceptions.InvalidMessageFormatException;

/**
 *	The King is the ruler who has at least 3 kingdoms as allies.
 *	King Shan wants to rule all the kingdoms.
 *	Shan sends messages to different kingdoms for the allegiance via sendMessages method.
 */
public class King {
	private static final Integer WINS_NEEDED = 3; // Wins needed to be the ruler of Southeros.
	private static final String RULER = "SPACE"; // King who wants to be the ruler of Southeros.
	private static final String NONE = "NONE";
	private static final String MESSAGE_REGEX = "[A-Za-z ]*";
	private Set<String> allies; // Allies of the ruler
	private List<String> messageList; // Messages that the ruler sends to other kingdoms
	
	private static King instance;
	
	/* PRIVATE CONSTRUCTOR */
	private King() {
		this.messageList = new ArrayList<String>();
	}
	
	/* ===============================
	 * PRIVATE METHODS 
	 * ===============================
	 */
	
	/**
	 * This method checks if kingdoms are allies of King, if yes then adds them to allies list
	 * @throws IOException
	 */
	private void findAllies() throws IOException {
		this.allies = new TreeSet<String>();
		Map<String, Kingdom> kingdoms = Southeros.getInstance().getKingdoms();
		for(Entry<String, Kingdom> item: kingdoms.entrySet()) {
			if(item.getValue().isAlly()) { // if kingdom is an ally, add its name to the allies list.
				this.allies.add(item.getKey());
			}
		}
	}
	
	/**
	 * Message should contain alphabets and/or spaces only
	 * 
	 * @param message
	 * @return
	 * @throws InvalidMessageFormatException 
	 */
	private boolean validateMessage(String message) throws InvalidMessageFormatException {
		if(!Pattern.matches(MESSAGE_REGEX, message)) {
			throw new InvalidMessageFormatException("Message contains characters other than alphabets & spaces.");
		}
		return true;
	}
	
	/**
	 * Splits the input message into kingdom and message and returns it.
	 * 
	 * @param msgInput
	 * @return kingdom and message in string array
	 * @throws InvalidMessageFormatException
	 */
	private String[] getKingdomAndMessage(String msgInput) throws InvalidMessageFormatException {
		final int SPLIT_SIZE = 2;
		String in[] = msgInput.split(" ", SPLIT_SIZE); 
		if(in.length != SPLIT_SIZE) {
			throw new InvalidMessageFormatException("Format of the message(" + msgInput + ") is not valid.");
		}
		return in;
	}

	/**
	 * It returns the kingdom object from the kingdoms map of Southeros for the input kingdomName. 
	 * 
	 * @param kingdomName
	 * @return kingdom object for the specified kingdomName
	 * @throws IOException
	 * @throws InvalidKingdomNameException
	 */
	private Kingdom getKingdom(String kingdomName) throws IOException, InvalidKingdomNameException {
		Map<String, Kingdom> kingdoms = Southeros.getInstance().getKingdoms();
		Kingdom kingdom = kingdoms.get(kingdomName.toUpperCase());
		if(kingdom == null) {
			throw new InvalidKingdomNameException("Kingdom name: " + kingdomName + " is invalid.");
		}
		return kingdom;
	}
	
	/* =================================
	 * PUBLIC METHODS 
	 * =================================
	 */
	
	/**
	 * This method sends the message to kingdoms by calling verify message of Kingdom
	 * 
	 * @throws IOException
	 * @throws InvalidMessageFormatException
	 * @throws InvalidKingdomNameException
	 */
	public void sendMessages() throws IOException, InvalidMessageFormatException, InvalidKingdomNameException {
		String kingdomName;
		String message;
		for (String msgInput: messageList) {
			String[] in = getKingdomAndMessage(msgInput);
			kingdomName = in[0].trim();
			message = in[1].trim();

			validateMessage(message);
			Kingdom kingdom = getKingdom(kingdomName);
			kingdom.verifyMessage(message);
		}
	}

	
	
	/**
	 * 
	 * @return the ruler if no. of allies are greater or equal than the required wins, otherwise returns NONE 
	 * @throws IOException
	 */
	public String getRuler() throws IOException {
		this.findAllies();
		if (this.allies.size() >= WINS_NEEDED) {
			return RULER;
		}
		return NONE;
	}
	
	/**
	 * 
	 * @return allies in comma separated string if there are any, otherwise returns None
	 * @throws IOException
	 */
	public String getAllies() throws IOException {
		this.findAllies();
		if(this.allies.isEmpty()) {
			return NONE;
		}
		return String.join(" ", this.allies);
	}
	
	public String getRulerWithAllies() throws IOException {
		if(NONE.equals(this.getRuler())) {
			return NONE;
		}
		return this.getRuler() + " " + this.getAllies();
	}
	
	/**
	 * Adds message to the messageList which in turn will be send to the corresponding kingdoms
	 * @param message
	 */
	public void addMessage(String message) {
		this.messageList.add(message);
	}
	
	public static King getInstance() {
		if(instance == null) {
			instance = new King();
		}
		return instance;
	}
}