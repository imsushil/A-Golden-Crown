package com.geektrust.tameofthrones;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.geektrust.tameofthrones.exceptions.InvalidKingdomNameException;
import com.geektrust.tameofthrones.exceptions.InvalidMessageFormatException;
import com.geektrust.tameofthrones.pojo.King;

public class MainApplication {
	private static final Logger LOGGER = Logger.getLogger(MainApplication.class.getName());
	private static King king = King.getInstance();

	public static void main(String[] args) throws IOException {
		String filePath = args[0];
		String message;
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			while((message = br.readLine()) != null) { 
				king.addMessage(message);
			}
			king.sendMessages();
			System.out.println(king.getRulerWithAllies());
		} catch (IOException | InvalidMessageFormatException | InvalidKingdomNameException e) {
			LOGGER.log(Level.INFO, e.getMessage(), e);
		}
	}
}
