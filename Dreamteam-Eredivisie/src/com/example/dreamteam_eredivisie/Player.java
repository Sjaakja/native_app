package com.example.dreamteam_eredivisie;

public class Player {

	    private String name;
	    private String club;
	    private String position;
	    private String side;
	    private int market_value;
	    
	    public Player(String name, String club, String position, String side, int market_value) {
	        this.name = name;
	        this.club = club;
	        this.position = position;
	        this.side = side;
	        this.market_value = market_value;
	    }
	 
	    public String getName() {
	    	return name;	    	
	    }
	    
	    public String getClub() {
	    	return club;	    	
	    }
	    
	    public String getPosition() {
	    	return position;	    	
	    }
	    
	    public String getSide() {
	    	return side;	    	
	    }
	    
	    public int getMarketValue() {
	    	return market_value;    	
	    }
	    
	
}
