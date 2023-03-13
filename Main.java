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
        
        String line; //to hold one line from file at a time
        int playerCount = 0; //holds number of players in file
        
        
        if (inFile.canRead()) //if file does not open properly
        {
            while (in.hasNext()) //gets number of players
            {
                line = in.nextLine();
                playerCount++;
            }
            
            //create all arrays to hold player data
            
            String[] names = new String[playerCount];
         
            int [] hit = new int[playerCount];
            int [] out = new int[playerCount];
            int [] strikeout = new int[playerCount];
            int [] walk = new int[playerCount];
            int [] hitByPitch = new int[playerCount];
            int [] sacrifice = new int[playerCount];
        
            double [] batAvg = new double[playerCount];
            double [] onBase = new double[playerCount];
            
            int playerIndex = 0; //to hold current player's index in all the arrays
            
            //close and reopen file to reset file read position so we can start from the beginning
            in.close();
            in = new Scanner(inFile); 

            while (in.hasNext()) //while the file still has player data in it
            {
                line = in.nextLine(); //store the data for one player
                line = GetName(line, names, playerIndex); //store the name of the player and update the line to hold only stats
                
                ParseBaseStats(line, hit, out, strikeout, walk, hitByPitch, sacrifice, batAvg, onBase, playerIndex); //extract/store player stats
                
                playerIndex++; //move on to the next player
               
            }
            playerIndex = 0; //reset to first player so we can display all data 
            DisplayPlayers(names, hit, strikeout, walk, hitByPitch, batAvg, onBase, playerCount, playerIndex); //Display individual stats
            Leaderboard(names, hit, strikeout, walk, hitByPitch, batAvg, onBase, playerCount); //Display leaderboard


        }
        in.close(); //close file
  
    
    }
    


public static String GetName(String line, String []names, int curIndex)
{ 
    names[curIndex] = line.substring(0, line.indexOf(" ")); //store everything before the first space in the line as the player's name
    line = line.substring(line.indexOf(" ")+1); //update line so it holds only play stats
    return line; //send updated line back

}

public static void ParseBaseStats(String stats, int[] h,int[] o,int[] k,int[] w,int[] p,int[] s, double[] ba, double[] obp, int curIndex) //gather stats
{
    double atBats = 0, plateApps = 0; //to hold at-bats and plate appearances
    
    for(int i = 0; i < stats.length(); i++) //parses string character by character and updates stat arrays
    {
        //hits
        if(stats.charAt(i) == 'H')
        {
            h[curIndex]++;
            plateApps++;
            atBats++;
        }
        //outs
        else if(stats.charAt(i) == 'O')
        {
            o[curIndex]++;
            plateApps++;
            atBats++;
        }
        //strikeouts
        else if(stats.charAt(i) == 'K')
        {
            k[curIndex]++;
            plateApps++;
            atBats++;
        }
        //walks
        else if(stats.charAt(i) == 'W')
        {
            w[curIndex]++;
            plateApps++;
        }
        //hit by pitches
        else if(stats.charAt(i) == 'P')
        {
            p[curIndex]++;
            plateApps++;
        }
        //sacrifices
        else if(stats.charAt(i) == 'S')
        {
            s[curIndex]++;
            plateApps++;
        }
        //invalid character
        else
        {
            continue;
        }
        
    }
    
    //calculate and store batting average if calculation is mathematically valid
    if(atBats!=0)
    ba[curIndex] = h[curIndex] / atBats; 
    
    //calculate and store on base percentage if calculation is mathematically valid
    if(plateApps!=0)
    obp[curIndex] = (h[curIndex] + w[curIndex] + p[curIndex]) / plateApps;

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

public static void Leaderboard(String [] names, int[] h,int[] k,int[] w,int[] p, double[] ba, double[] obp, int size) //create leaderboard
{
    //to hold the highest scores (seperate data types for accurate output rounding)
    double highest; 
    int high;
    
    int winIndex = 0; //to hold the player number so we can get their name


    System.out.println("LEAGUE LEADERS"); //display heading
    
    //CALCULATE AND PRINT BATTING AVERAGE WINNER
    highest = ba[0];
    for(int i = 0; i < size; i++)
    {
        if(ba[i] > highest)
        {
            highest = ba[i];
            winIndex = i;
        }
    }
    System.out.printf("BA: %s", names[winIndex]); //print first winner name
    
    //once highest value is found, iterate through rest of array to catch any ties
    for(int i = 0; i < size; i++)
    {
        if(ba[i] == highest && i != winIndex)
        {
            //display remaining winners
            System.out.printf(", %s", names[i]);
            
        }
    }
    //print score of winners
    System.out.printf(" %.3f\n", highest);
    
    //CALCULATE AND PRINT ON BASE PERENTAGE WINNER
    //reset tracking variables
    highest = obp[0];
    winIndex = 0;
    
    for(int i = 0; i < size; i++)
    {
        if(obp[i] > highest)
        {
            highest = obp[i];
            winIndex = i;
        }
    }
    System.out.printf("OB%%: %s", names[winIndex]); //print first winner name

    
    //once highest value is found, iterate through rest of array to catch any ties
    for(int i = 0; i < size; i++)
    {
        if(obp[i] == highest && i != winIndex)
        {
            //display remaining winners
            System.out.printf(", %s", names[i]);
        }
    }
    //print score of winners
    System.out.printf(" %.3f\n", highest);

    //CALCULATE AND PRINT HITS
    //reset tracking variables
    high = h[0];
    winIndex = 0;
    for(int i = 0; i < size; i++)
    {
        if(h[i] > high)
        {
            high = h[i];
            winIndex = i;
        }
    }
    System.out.printf("H: %s", names[winIndex]); //print first winner name

    //once highest value is found, iterate through rest of array to catch any ties
    for(int i = 0; i < size; i++)
    {
        if(h[i] == high && i != winIndex)
        {
            //display remaining winners
            System.out.printf(", %s", names[i]);
        }
    }
    //print score of winners
    System.out.printf(" %d\n", high); 

    //CALCULATE AND PRINT WALKS
    //reset tracking variables
    high = w[0];
    winIndex = 0;
    for(int i = 0; i < size; i++)
    {
        if(w[i] > high)
        {
            high = w[i];
            winIndex = i;
        }
    }
    System.out.printf("BB: %s", names[winIndex]); //print first winner name
    
    //once highest value is found, iterate through rest of array to catch any ties
    for(int i = 0; i < size; i++)
    {
        if(w[i] == high && i != winIndex) 
        {
            //display remaining winners
            System.out.printf(", %s", names[i]);
        }
    }
    //print score of winners
    System.out.printf(" %d\n", high);
    


    //CALCULATE AND PRINT STRIKEOUTS
    //reset tracking variables
    int lowest = k[0];
    winIndex = 0;
    for(int i = 0; i < size; i++)
    {
        if(k[i] < lowest)
        {
            lowest = k[i];
            winIndex = i;
        }
    }
        System.out.printf("K: %s", names[winIndex]); //print first winner name

    //once highest value is found, iterate through rest of array to catch any ties
    for(int i = 0; i < size; i++)
    {
        if(k[i] == lowest && i != winIndex) 
        {
            //display remaining winners
            System.out.printf(", %s", names[i]);
        }
    }
    //print score of winners
    System.out.printf(" %d\n", lowest);
    
    //CALCULATE AND PRINT HIT BY PITCH
    //reset tracking variables
    high = p[0];
    winIndex = 0;
    for(int i = 0; i < size; i++)
    {
        if(p[i] > high)
        {
            high = p[i];
            winIndex = i;
        }
    }
    System.out.printf("HBP: %s", names[winIndex]); //print first winner name

    //once highest value is found, iterate through rest of array to catch any ties
    for(int i = 0; i < size; i++)
    {
        if(p[i] == high && i != winIndex)
        {
            //display remaining winners
            System.out.printf(", %s", names[i]);
        }
    }
    //print score of winners
    System.out.printf(" %d\n", high);
    
}

}