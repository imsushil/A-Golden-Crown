package com.geektrust.tameofthrones.pojo;

import java.util.HashMap;
import java.util.Map;

import com.geektrust.tameofthrones.exceptions.InvalidMessageFormatException;

/**
 * This class does the job of verifying if the emblem of kingdom is present in
 * the message.
 * 
 * @author sushil
 */
public class MessageVerifier {
	private static final Character UPPERCASE_A = 'A';
	private static final Character LOWERCASE_A = 'a';
	private static final int TOTAL_ALPHABETS = 26;
	private static final char SPACE = ' ';

	/* ==================================
	 * PRIVATE METHODS 
	 * ==================================
	 */

	/**
	 * Utility method to count the number of characters in a string and stores
	 * it in hashmap
	 * 
	 * @param string
	 * @return
	 */
	private static Map<Character, Integer> getCharCount(String string) {
		Character ch;
		Map<Character, Integer> count = new HashMap<Character, Integer>();
		int lengthOfStr = string.length();
		
		for (int i = 0; i < lengthOfStr; ++i) {
			ch = Character.toLowerCase(string.charAt(i));
			if (count.containsKey(ch)) { // If character is already present, increment the count
				count.put(ch, count.get(ch) + 1);
			} else {
				count.put(ch, 1);
			}
		}
		return count;
	}

	/**
	 * For each character in emblem map, this method compares its count with the
	 * count of the same character in message map returns true if the message
	 * has at least same count of characters as of emblem otherwise false
	 * 
	 * @param emblemMap
	 * @param messageMap
	 * @return
	 */
	private static boolean compareCharacters(Map<Character, Integer> emblemMap, Map<Character, Integer> messageMap) {
		for (Character key : emblemMap.keySet()) {
			if (!messageMap.containsKey(key)) return false;
			
			// return false if count of the character in emblem is greater
			// than the count of the same character in message
			if (emblemMap.get(key) > messageMap.get(key)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * It returns equivalent upper case of the alphabet index[0-25]
	 * e.g. 3 will be converted to 'C', 25 to 'Z'
	 * 
	 * @param rotatedCharIndex
	 * @return
	 */
	private static char getLowerCase(int rotatedCharIndex) {
		return (char) (LOWERCASE_A + rotatedCharIndex);
	}

	/**
	 * It returns equivalent lower case of the alphabet index[0-25]
	 * e.g. 2 will be converted to 'b', 5 to 'e'
	 * 
	 * @param rotatedCharIndex
	 * @return
	 */
	private static char getUpperCase(int rotatedCharIndex) {
		return (char) (UPPERCASE_A + rotatedCharIndex);
	}
	
	/**
	 * This method returns the decrypted letter by moving "key" letters anti-clockwise on the message wheel. 
	 * 
	 * @param msgChar
	 * @param key
	 * @return
	 */
	private static char rotate(char msgChar, int key) {
		int rotatedCharIndex;
		if (Character.isUpperCase(msgChar)) {
			rotatedCharIndex = (TOTAL_ALPHABETS + (msgChar - UPPERCASE_A - key)) % TOTAL_ALPHABETS;
			return getUpperCase(rotatedCharIndex);
		}
		
		rotatedCharIndex = (TOTAL_ALPHABETS + (msgChar - UPPERCASE_A - key)) % TOTAL_ALPHABETS;
		return getLowerCase(rotatedCharIndex);
	}

	/* ========================================
	 * PUBLIC METHODS 
	 * ========================================
	 */
	
	/**
	 * This method decrypts the message by doing reverse of ceasar cipher.
	 * 
	 * @param message
	 * @param key
	 * @return
	 * @throws InvalidMessageFormatException
	 */
	public static String decryptMessage(String message, int key) throws InvalidMessageFormatException {
		StringBuilder decryptedMsg = new StringBuilder();
		for (int i = 0; i < message.length(); i++) {
			Character msgChar = message.charAt(i);
			if(msgChar == SPACE) continue;
			decryptedMsg.append(rotate(msgChar, key));
		}
		return decryptedMsg.toString();
	}

	/**
	 * This method verifies the message by comparing counts of characters stored
	 * in both the maps
	 * 
	 * @param emblem
	 * @param message
	 * @return true if message contains all characters of emblem, otherwise
	 *         false
	 */
	public static boolean verify(String emblem, String message) {
		// store the count of all the characters of message
		Map<Character, Integer> charCountOfMsg = getCharCount(message);

		// store the count of all the characters of the emblem of kingdom
		Map<Character, Integer> charCountOfEmblem = getCharCount(emblem);

		return compareCharacters(charCountOfEmblem, charCountOfMsg);
	}
}
