//Fatima Khalid
//fxk200007
import java.io.*;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws IOException //Main functions 
    {

        String fileName; //to hold file name
        Scanner consoleIn = new Scanner(System.in); //to get user input
        fileName = consoleIn.next(); //set file name to what user enters
        
        File inFile = new File(fileName); //to open file
        Scanner in = new Scanner(inFile); //to read from file
        
        LinkList list = new LinkList();

        String line; //to hold one line from file at a time
        String playerName;
        int playerCount = 0; //holds number of players in file
        
        
        if (inFile.canRead()) //if file does not open properly
        {
            
            int playerIndex = 0; //to hold current player's index in all the arrays
            while (in.hasNext()) //while the file still has player data in it
            {
                
                line = in.nextLine(); //store the data for one player
                playerName = line.substring(0, line.indexOf(' ')); //stores player name
                line = line.substring(line.indexOf(' ') + 1); //line now only holds player stats
                if(list.SearchPlayer(playerName) == null) //if player is not already existing in the list (always executes for now)
                {
                    Player p = new Player(playerName); //creates player with that player's name
                    ParseBaseStats(line, p); //extract/store player stats
                    Node n = new Node(p);
                    
                    list.Append(n);
                    
                }
                else
                {
                   ParseBaseStats(line, list.SearchPlayer(playerName)); //add to existin player's stats
                }

            }
            list.Sort();
            Node cur = list.getHead();
            list.toString(cur); //Display individual stats
            list.ReverseSort();
            list.Leaderboard();
        }
        in.close(); //close file
  
    
    }
    
public static void ParseBaseStats(String stats, Player p) //gather stats
{
    for(int i = 0; i < stats.length(); i++) //parses string character by character and updates stat arrays
    {
        //hits
        if(stats.charAt(i) == 'H') 
        {
            p.setHits(p.getHits()+1);
        }
        //outs
        else if(stats.charAt(i) == 'O')
        {
            p.setOuts(p.getOuts()+1);

        }
        //strikeouts
        else if(stats.charAt(i) == 'K')
        {
            p.setStrikeouts(p.getStrikeouts()+1);
        }
        //walks
        else if(stats.charAt(i) == 'W')
        {
            p.setWalks(p.getWalks()+1);
        }
        //hit by pitches
        else if(stats.charAt(i) == 'P')
        {
            p.setHitByPitches(p.getHitByPitches()+1);
        }
        //sacrifices
        else if(stats.charAt(i) == 'S')
        {
            p.setSacrifices(p.getSacrifices()+1);
        }
        //invalid character
        else
        {
            continue;
        }
        
    }
    

}

public static void DisplayPlayers(String names[], int[] h, int[] k,int[] w,int[] p, double[] ba, double[] obp, int size, int curIndex) //display individual stats
{
    while(curIndex < size) //While players are still being read
    {
        System.out.println(names[curIndex]);//Print name
        
        System.out.printf("BA: %.3f\n", ba[curIndex]); //print batting average
        System.out.printf("OB%%: %.3f\n", obp[curIndex]);//print on base percentage

        System.out.printf("H: %d\n", h[curIndex]); //print Hits
        System.out.printf("BB: %d\n", w[curIndex]);//print Walks
        System.out.printf("K: %d\n", k[curIndex]); //print strikeouts
        System.out.printf("HBP: %d\n\n", p[curIndex]); //print hits by pitch
        
        curIndex++; //move on to next player
    }

}

}