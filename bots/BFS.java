package bots;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import gameengine.Coordinates;
import gameengine.Map;
import gameengine.Names;
import gameengine.Room;

public class BFS {
	
	
	
	

	//private static String[] DIRECTIONS = { "up", "down", "left", "right" };
	private static int[][] DIRECTIONS = { { 0, 1 }, { 0,  -1}, { 1, 0 }, { -1, 0 } }; //right, down, left, up

    public final static int NUM_ROOMS = Names.ROOM_NAMES.length;
    public final static int D = 100, C=101, X=102;  // D = Door threshold, C = Corridor, X = Room or Prohibited
    public static int[][] MAP = Map.MAP;
    public final static int NUM_COLS = Map.NUM_COLS, NUM_ROWS = Map.NUM_ROWS;
    private static int maze[][] = new int[NUM_ROWS][NUM_COLS];
    public final static Room[] rooms = Map.rooms;
    public final static HashMap<String, Coordinates> keyToCoordinates = new HashMap<>();
    
//    public static void main(String[] args) {
//    	
//    	System.out.println(solve(new Coordinates(0,7),new Coordinates(1,8)));
//    }
    
    public static List<String> solve(Coordinates start, Coordinates end) {
    	List<Positions> sol =  getSteps(start, end);
    	Collections.reverse(sol);
    	return getDirections(sol);

    }
    
    public static List<String> getDirections(List<Positions> sol) {
    	
    	LinkedList<String> directions = new LinkedList<>(); 
    	if(sol.size()>1) {
	    	for (int i=0;i<sol.size()-1;i++) {
	    		Positions prev = sol.get(i);
	    		Positions next = sol.get(i+1);
	    		if(next.getCol() < prev.getCol())
	    			directions.add("l");
	    		else if(next.getCol() > prev.getCol())
	    			directions.add("r");
	    		else if(next.getRow() < prev.getCol())
	    			directions.add("d");
	    		else   
	    			directions.add("u");
	    	}
	    	
	    	//System.out.println(directions);
	    	return directions;
    	}
    	else {
    	return Collections.emptyList();
    	}
    }
    
    
    
	public static List<Positions> getSteps(Coordinates startCoordinates, Coordinates end) {
	    	LinkedList<Positions> nextToVisit = new LinkedList<>();
	    	//int maze[][] = new int[NUM_ROWS][NUM_COLS];
	   	
	   
	    	for(int c=0; c<NUM_COLS;c++) {
	    		for(int r=0; r<NUM_ROWS;r++) {
	    			Coordinates temp = new Coordinates(r, c);
	    			if(isCorridor(temp)) 
	    				maze[r][c] =  0;//passable
	    			else
	    				maze[r][c] = 1;//blocked
	    			//System.out.print(maze[r][c]);
	    		}
	    		//System.out.println("");
	    	} 
	    	
	    	maze[end.getRow()][end.getCol()]=5; //Destination
	       
	    	Positions start = new Positions(startCoordinates, null);//tree head
	    	nextToVisit.add(start); //Begin with the start node
	    	 
	        while (!nextToVisit.isEmpty()) {
	        	
	            Positions cur = nextToVisit.remove();//TODO 

	            if (!isValidLocation(cur) || isExplored(cur)) {
	            	//System.out.println("is explored "+cur);
	                continue;
	            }
	     
	            if (isWall(cur)) {
	            	//System.out.println("Is wall "+cur);
	                setVisited(cur);
	                continue;
	            }
	     
	            //If this is the desired location
	            if (isExit(cur)) {
	                return backtrackPath(cur);
	            }
	     
	            //System.out.println(cur + " is ok!");
	            for (int[] dir : DIRECTIONS) { //For each of the four directions
	            	//Coordinates nextCor = map.getNewPosition(cur.getCoordinates(), dir);
	            	
	            	Positions next = new Positions(cur.getRow() + dir[0], cur.getCol()+dir[1], cur);

	            	nextToVisit.add(next);
	            	setVisited(cur);
	            }
	        }
	        
	        //If no path was found
	        return Collections.emptyList();
	       
	        
	    }
	    
	    private static boolean isExit(Positions cur) {
	    	int row = cur.getRow();
	    	int col = cur.getCol();
	    	
	    	return maze[row][col] == 5;
	    }
	   
	    
	    private static boolean isValidLocation(Positions cur) {
	    	int row = cur.getRow();
	    	int col = cur.getCol();
	    	
	    	return(row>=0 && row<NUM_ROWS && col>=0 && col<NUM_COLS);
	    }
	    
	    private static boolean isExplored(Positions cur) {
	    	int row = cur.getRow();
	    	int col = cur.getCol();
	    	return maze[row][col]==2;
	    }
	    
	    private static void setVisited(Positions cur) {
	    	int row = cur.getRow();
	    	int col = cur.getCol();
	    	maze[row][col]=2;
	    	cur.visited();
	    }
	    
	    private static boolean isWall(Positions cur) {
	    	int row = cur.getRow();
	    	int col = cur.getCol();
	    	return (MAP[row][col]==X);
	    }
	    
	    private static List<Positions> backtrackPath(Positions cur) {
	    		    List<Positions> path = new ArrayList<>();
	    		    Positions iter = cur;
	    		    while (iter != null) {
	    		        path.add(iter);
	    		        iter = iter.getParent();
	    		    }
	    		 
	    		    //Reverse the path list so that the first co-ordinate, is the first step to take:
	    		    
	    		    return path;
	    		}
	    
	    public Room getRoom(String name) {
	    	for(Room room: rooms) {
	    		if(room.hasName(name))
	    			return room;
	    	}
	    	return null;
	    }
	        

	    public static boolean isCorridor(Coordinates position) {
	        return (position.getCol()>=0 && position.getCol()<NUM_COLS && position.getRow()>=0 && position.getRow()<NUM_ROWS &&
	                (MAP[position.getRow()][position.getCol()]==C || MAP[position.getRow()][position.getCol()]==D));
	    }

	    public boolean isDoor(Coordinates currentPosition, Coordinates nextPosition) {
	        return (currentPosition.getCol()>=0 && currentPosition.getCol()<NUM_COLS &&
	                currentPosition.getRow()>=0 && currentPosition.getRow()<NUM_ROWS &&
	                nextPosition.getCol()>=0 && nextPosition.getCol()<NUM_COLS &&
	                nextPosition.getRow()>=0 && nextPosition.getRow()<NUM_ROWS &&
	                MAP[currentPosition.getRow()][currentPosition.getCol()]==D &&
	                MAP[nextPosition.getRow()][nextPosition.getCol()]<NUM_ROOMS);
	    }

	    public Room getRoom(Coordinates position) {
	        return rooms[MAP[position.getRow()][position.getCol()]];
	    }

	    public static Room getRoomWithName(String name) {
	        for (Room room : rooms) {
	            if (room.hasName(name)) {
	                return room;
	            }
	        }
	        return null;
	    }

}
