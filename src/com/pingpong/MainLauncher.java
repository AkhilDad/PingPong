package com.pingpong;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akhil on 31/10/15.
 */
public class MainLauncher {

    public static void main(String[] args) {
       //start game from here
    	List<Player> players = new ArrayList<Player>();
    	players.add(new Player(1, "Joey", 8));
    	players.add(new Player(2, "Nick", 7));
    	players.add(new Player(3, "Russel", 6));
    	players.add(new Player(4, "Vivek", 8));
    	players.add(new Player(5, "Pritam", 5));
    	players.add(new Player(6, "Amit", 6));
    	players.add(new Player(7, "Chandler", 6));
    	players.add(new Player(8, "Calwin", 8));
    	Referee referee;
		try {
			referee = new Referee(players);
			Thread thread = new Thread(referee);
	    	thread.start();
	    	System.out.print("Thread started");
	    	try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	System.out.print("Thread joined");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    }
}
