package com.geektrust.tameofthrones.pojo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


 /**
  * Southeros is place which has several kingdoms like Land, Space, Air, Water, Ice, Fire
  * Each Kingdom has an emblem associated with it.
  * The Kingdoms are loaded into kingdoms map from kingdoms.txt file.
  * 
  */
public class Southeros {
	private final String KINGDOMS_PATH = "/kingdoms.txt";
	private static Southeros instance;
	private Map<String, Kingdom> kingdoms;
	
	
	/* PRIVATE CONSTRUCTOR */
	private Southeros() throws IOException {
		this.kingdoms = new HashMap<String, Kingdom>();
		this.init();
	}
	
	/* ===============================
	 * PRIVATE METHODS 
	 * ===============================
	 */
	
	/**
	 * Reads kingdom.txt file and populates the kingdoms map.
	 * @throws IOException
	 */
	private void init() throws IOException {
		try(InputStream input = getClass().getResourceAsStream(KINGDOMS_PATH);
			BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
			String line, in[];
			
            while ((line = br.readLine()) != null) {
            	in = line.split(" ");
                String kingdomName = in[0];
                String emblem = in[1];
                
                Kingdom kingdom = new Kingdom(kingdomName, emblem);
                this.kingdoms.put(kingdomName.toUpperCase(), kingdom);
            }
		}
	}
	
	
	/* ================================
	 * PUBLIC METHODS 
	 * ================================
	 */

	public Map<String, Kingdom> getKingdoms() {
		return new HashMap<>(this.kingdoms);
	}
	
	public static Southeros getInstance() throws IOException {
		if(instance == null) {
			instance = new Southeros();
		}
		return instance;
	}
}
