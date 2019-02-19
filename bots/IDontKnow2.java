package bots;

import gameengine.*;

public class IDontKnow2 implements BotAPI {

    // The public API of Bot must not change
    // This is ONLY class that you can edit in the program
    // Rename Bot to the name of your team. Use camel case.
    // Bot may not alter the state of the board or the player objects
    // It may only inspect the state of the board and the player objects

    private Player player;
    private PlayersInfo playersInfo;
    private Map map;
    private Dice dice;
    private Log log;
    private Deck deck;
    
    private String name = "IDontKnow2";
    private Room destination;
    private boolean turnOver = false;
    

    public IDontKnow2 (Player player, PlayersInfo playersInfo, Map map, Dice dice, Log log, Deck deck) {
        this.player = player;
        this.playersInfo = playersInfo;
        this.map = map;
        this.dice = dice;
        this.log = log;
        this.deck = deck;
    }

    public String getName() {
        return this.name; // must match the class name
    }

    public String getVersion () {
        return "0.1";   // change on a new release
    }

     public String getCommand() {
         
        
    	if(!turnOver) {
        	if(player.getToken().isInRoom()) {
        		return "question";
        	}
        	else {
        		turnOver = true;
        		return "roll";
        	}
    	}
    	
    	turnOver = false;
    	return "done";
    
    }

    public String getMove() {

    	int i = 1 + (int)(Math.random() * 8);
    	//Select a random room as the destination
        destination = BFS.getRoomWithName(Names.ROOM_CARD_NAMES[i]); 
        
        Coordinates start = player.getToken().getPosition();
    	Coordinates end = destination.getDoorCoordinates(0);

    	String direction = BFS.solve(start, end).get(0);
        return direction;
    }

   public String getSuspect() {
    	
    	
    	if(!player.hasCard("Plum")){
    		return Names.SUSPECT_NAMES[0];
    	}
    	else if(!player.hasCard("White")) {
    		return Names.SUSPECT_NAMES[1];
    	}
    	else if(!player.hasCard("Scarlett")) {
    		return Names.SUSPECT_NAMES[2];
    	}
    	else if(!player.hasCard("Green")) {
    		return Names.SUSPECT_NAMES[3];
    	}
    	else if(!player.hasCard("Mustard")) {
    		return Names.SUSPECT_NAMES[4];
    	}
    	else if(!player.hasCard("Peacock")) {
    		return Names.SUSPECT_NAMES[5];
    	}
    	else {
    		return Names.SUSPECT_NAMES[0];
    	}
   
    }

    public String getWeapon() {
        
    	if(!player.hasCard("Rope")) {
    		return Names.WEAPON_NAMES[0];
    	}
    	else if(!player.hasCard("Dagger")) {
    		return Names.WEAPON_NAMES[1];
    	}
    	else if(!player.hasCard("Wrench")) {
    		return Names.WEAPON_NAMES[2];
    	}
    	else if(!player.hasCard("Pistol")) {
    		return Names.WEAPON_NAMES[3];
    	}
    	else if(!player.hasCard("Candlestick")) {
    		return Names.WEAPON_NAMES[4];
    	}
    	else if(!player.hasCard("Lesad Pipe")) {
    		return Names.WEAPON_NAMES[5];
    	}
    	else {
        return Names.WEAPON_NAMES[0];
    	}
    }

    public String getRoom() {
    	
    	if(!player.hasCard("Kitchen")) {
    		return Names.ROOM_NAMES[0];
    	}
    	else if(!player.hasCard("Ballroom")) {
    		return Names.ROOM_NAMES[1];
    	}
    	else if(!player.hasCard("Conservatory")) {
    		return Names.ROOM_NAMES[2];
    	}
    	else if(!player.hasCard("Billiard Room")) {
    		return Names.ROOM_NAMES[3];
    	}
    	else if(!player.hasCard("Library")) {
    		return Names.ROOM_NAMES[4];
    	}
    	else if(!player.hasCard("Study")) {
    		return Names.ROOM_NAMES[5];
    	}
    	else if(!player.hasCard("Hall")) {
    		return Names.ROOM_NAMES[6];
    	}
    	else if(!player.hasCard("Lounge")) {
    		return Names.ROOM_NAMES[7];
    	}
    	else if(!player.hasCard("Dining Room")) {
    		return Names.ROOM_NAMES[8];
    	}
    	else if(!player.hasCard("Cellar")) {
    		return Names.ROOM_NAMES[9];
    	}
    	else {
     
        return Names.ROOM_NAMES[0];
    	}
    }

    public String getDoor() {
    	Room currRoom = player.getToken().getRoom();
    	int numExits = currRoom.getNumberOfDoors();
    	int exitDoor = 1 + (int)(Math.random() * numExits);
        return Integer.toString(exitDoor);
    }
    
    

    public String getCard(Cards matchingCards) {
        // Add your code here
        return matchingCards.get().toString();
    }

    public void notifyResponse(Log response) {
        // Add your code here
    }

    public void notifyPlayerName(String playerName) {
        // Add your code here
    }

    public void notifyTurnOver(String playerName, String position) {
        // Add your code here
    }

    public void notifyQuery(String playerName, String query) {
        // Add your code here
    }

    public void notifyReply(String playerName, boolean cardShown) {
        // Add your code here
    }

}
