//Fatima Khalid
//fxk200007
import java.io.*;
import java.util.*;

public class Main
{
    public static void main(String[] args) throws IOException //Main functions 
    {
        //Open keyfile
        File keyFile = new File("keyfile.txt");
        Scanner in = new Scanner(keyFile); //to read from keyfile
         //Let user know if file cannot be read
         if(!(keyFile.canRead()))
         {
             System.out.println("Cannot open file.");
         }
         //Create statsMap (maps stat code to the stat it represents)
        HashMap <String, Character> statsMap = CreateStatsHashmap(in);
            
        // -	Instantiate empty playerMap Hashmap (maps String to Player)
        HashMap<String, Player> playerMap = new HashMap<>();
       
        // -	Prompt user for play-by-play filename
        String fileName; //to hold file name
        Scanner consoleIn = new Scanner(System.in); //to get user input
        fileName = consoleIn.next(); //set file name to what user enters
        
        // -	Open play-by-play file
        File playFile = new File(fileName);
        in = new Scanner(playFile);
        
        String line, playerName, fileStatKey;
        // -	While play by play file has lines to read
        while(in.hasNext())
        {
            // o	Store a line from the file
            line = in.nextLine();
            // o	Extract current player name
            int firstSpace = line.indexOf(" ");
            int secondSpace = line.indexOf(" ", firstSpace + 1);
            playerName = line.substring(firstSpace+1, secondSpace);
            // o	Extract the plate appearance type
            fileStatKey = line.substring(secondSpace+1);
            // o	If current player name does not exist in playerMap
            if(! (playerMap.containsKey(playerName)) )
            {
                Player p = new Player(playerName, line.charAt(0)); // 	Create Player object for that player and store name and team
                playerMap.put(playerName, p); //store player in playerMap, findable by name
            }
            Character stat = statsMap.get(fileStatKey); //get type of stat for that turn
            Player player = playerMap.get(playerName); //find player object associated with current player, regardless if they exist or not
            UpdateStat(player, stat); //update respective info in player object
        }
        // -	At this point, all file data has been extracted and added into playerMap
        PrintPlayers(playerMap, 'A'); //print away team's stats
        PrintPlayers(playerMap, 'H'); //print home team's stats
        
        // Print league leaders
        Leaderboard(playerMap);
        
        // -	Close file
        in.close();
        
        
    }
    
     public static HashMap<String, Character> CreateStatsHashmap(Scanner in)
    {
        // o    to hold line from file and the key
        String line, keyVal;
        // o	to hold the value in key/value pair
        Character mapData;
        // o	Create empty hashMap statMap
        HashMap<String, Character> statMap = new HashMap<>();
        // o	While file still has lines to read
        while(in.hasNext())
        {
            line = in.nextLine(); //store line from file
            // If line’s first char is not # (a valid stat)
            if(line.length() != 0 && line.charAt(0) != '#')
            {
                // 	Store the key
                keyVal = line;
                //fill up stats map with keys corresponding to one stat type
                // 	strikeout
                if(keyVal.equals("K"))
                {
                    mapData = 'K';
                }
                // 	error
                else if(keyVal.charAt(0) == 'E')
                {
                    mapData = 'E';
                }
                // 	sacrifice
                else if(keyVal.charAt(0) == 'S')
                {
                    mapData = 'S';
                }
                // 	walk
                else if(keyVal.equals("BB"))
                {
                    mapData = 'W';
                }
                // 	hit by pitch
                else if(keyVal.equals("HBP"))
                {
                    mapData = 'P';
                }
                // 	hit
                else if(keyVal.equals("1B") || keyVal.equals("2B") ||keyVal.equals("3B") ||keyVal.equals("HR") )
                {
                    mapData = 'H';
                }
                else //out
                {
                    mapData = 'O';
                }
                // 	Add element to statMap
                statMap.put(keyVal, mapData);
            }
        }
        // o	Return statsMap
        return statMap;
    }
    public static void UpdateStat(Player p, Character stat)
    {
        if(stat == 'H') //hit
        {
            p.setHits( (p.getHits()+1) ); //update player
        }
        else if(stat == 'O') //out
        {
            p.setOuts( (p.getOuts()+1) ); //update player
        }
        else if(stat == 'K') //strikeout
        {
            p.setStrikeouts( (p.getStrikeouts()+1) ); //update player
        }
        else if(stat == 'W') //walk
        {
            p.setWalks( (p.getWalks()+1) ); //update player
        }
        else if(stat == 'P') //hit by pitch
        {
            p.setHitByPitches( (p.getHitByPitches()+1) ); //update player
        }
        else if(stat == 'S') //sacrifice
        {
            p.setSacrifices( (p.getSacrifices()+1) ); //update player
        }
        else if(stat == 'E') //error
        {
            p.setErrors( (p.getErrors()+1) ); //update player
        }

    }
    public static void PrintPlayers(HashMap<String,Player> pMap, char team)
    {
        // Sort players HashMap
        TreeMap<String, Player> sortedMap = new TreeMap<>();
        sortedMap.putAll(pMap);
        
        // o	Print Away Team
        if(team == 'A')
        {
            System.out.println("AWAY"); //print AWAY
            // 	Use modified for loop from Map class to iterate through entire sortedMap
            for (Map.Entry<String, Player> m : sortedMap.entrySet())
            {
                if( (m.getValue()).getTeam() == 'A' ) //if player is on away team
                {
                    //print player with their stats
                    System.out.printf("%s\t%d\t%d\t%d\t%d\t%d\t%d\t%.3f\t%.3f\n", m.getKey(), (m.getValue()).getAtBats(), (m.getValue()).getHits(),
                                    (m.getValue()).getWalks(),(m.getValue()).getStrikeouts(),(m.getValue()).getHitByPitches(),
                                    (m.getValue()).getSacrifices(), (m.getValue()).getBattingAvg(),(m.getValue()).getOnBasePercent());
                }
            }
            System.out.println();
                
        }
        else //print home team
        {
            System.out.println("HOME"); //print HOME
            for (Map.Entry<String, Player> m : sortedMap.entrySet())
            {
                if( (m.getValue()).getTeam() == 'H' ) //if player is on home team
                {
                    //print name and stats
                    System.out.printf("%s\t%d\t%d\t%d\t%d\t%d\t%d\t%.3f\t%.3f\n", m.getKey(), (m.getValue()).getAtBats(), (m.getValue()).getHits(),
                                    (m.getValue()).getWalks(),(m.getValue()).getStrikeouts(),(m.getValue()).getHitByPitches(),
                                    (m.getValue()).getSacrifices(), (m.getValue()).getBattingAvg(),(m.getValue()).getOnBasePercent());
                }
            }
        }
        
    }
    public static void Leaderboard(HashMap<String, Player> pMap)
    {
        System.out.println("\nLEAGUE LEADERS"); //print heading 
        
        // Sort player map
        TreeMap<String, Player> sortedMap = new TreeMap<>();
        sortedMap.putAll(pMap);
        
        
        // Create a new sorted map for AWAY TEAM
        TreeMap<String, Player> sortedMapA = new TreeMap<>(); //
        sortedMapA.putAll(pMap); //copy map of all players
        Set<String> homeSet = new HashSet<> ();
        for (Map.Entry<String, Player> m : sortedMapA.entrySet()) //find all home players
        {
            if(m.getValue().getTeam() == 'H')
            {
                homeSet.add(m.getKey());
            }
        }
        sortedMapA.keySet().removeAll(homeSet); //remove home players, leaving only away players in map

        // Create a new sorted map for HOME TEAM
        TreeMap<String, Player> sortedMapH = new TreeMap<>();
        sortedMapH.putAll(pMap); //copy original map
        Set<String> awaySet = new HashSet<> (); 
        for (Map.Entry<String, Player> m : sortedMapH.entrySet()) //find all away team members
        {
            if(m.getValue().getTeam() == 'A')
            {
                awaySet.add(m.getKey());
            }
        }
        sortedMapH.keySet().removeAll(awaySet); //remove away team players, leaving only home players

        //batting average
        System.out.println("BATTING AVERAGE");
        
        //to hold first, second, and third highest scores: set to an impossibly low value
        double first = Double.NEGATIVE_INFINITY;
        double second = Double.NEGATIVE_INFINITY;
        double third = Double.NEGATIVE_INFINITY;
        int numTies1 = 0, numTies2=0, numTies3 = 0; //hold number of ties for each place
        // o	Use modified for loop to iterate through hashmap value batting averages
        for (Map.Entry<String, Player> m : sortedMap.entrySet())
        {
            // 	If current element larger than first element
            if(first < m.getValue().getBattingAvg())
            {
                third = second; //second place becomes third
                second = first; //first place becomes second
                first = m.getValue().getBattingAvg(); //first becomes current element
            }
            // 	Else if current element between 1st and 2nd 
            else if(second < m.getValue().getBattingAvg() && m.getValue().getBattingAvg()!= first) 
            {
                third = second; //second becomes third
                second = m.getValue().getBattingAvg(); //current becomes second 
            }
            // 	Else if current element between 2nd and 3rd 
            else if (m.getValue().getBattingAvg() > third && m.getValue().getBattingAvg() != second) //if between second and third
            {
                third = m.getValue().getBattingAvg(); //current becomes third
            }
        }
        if(first!=Double.NEGATIVE_INFINITY) //print first place
        {
            System.out.printf("%.3f\t", first);//first place value
            for (Map.Entry<String, Player> m : sortedMapA.entrySet())
            {
                if(m.getValue().getBattingAvg() == first )
                {
                    if(numTies1 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies1++;
                    System.out.print(m.getKey());
                }
            }
            for (Map.Entry<String, Player> m : sortedMapH.entrySet())
            {
                if(m.getValue().getBattingAvg() == first )
                {
                    if(numTies1 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies1++;
                    System.out.print(m.getKey());
                }
            }
        }
        if(second!=Double.NEGATIVE_INFINITY && numTies1 < 3) //print second place
        {
            System.out.printf("\n%.3f\t", second);
            for (Map.Entry<String, Player> m : sortedMapA.entrySet())
            {
                if(m.getValue().getBattingAvg() == second )
                {
                    if(numTies2 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies2++;
                    System.out.print(m.getKey());
                }
            }
            for (Map.Entry<String, Player> m : sortedMapH.entrySet())
            {
                if(m.getValue().getBattingAvg() == second )
                {
                    if(numTies2 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies2++;
                    System.out.print(m.getKey());
                }
            }
        }
        if(third!=Double.NEGATIVE_INFINITY && numTies1 == 1 && numTies2 == 1) //print third place
        {
            System.out.printf("\n%.3f\t", third);
            for (Map.Entry<String, Player> m : sortedMapA.entrySet())
            {
                if(m.getValue().getBattingAvg() == third )
                {
                    if(numTies3 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies3++;
                    System.out.print(m.getKey());
                }
            }
            for (Map.Entry<String, Player> m : sortedMapH.entrySet())
            {
                if(m.getValue().getBattingAvg() == third )
                {
                    if(numTies3 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies3++;
                    System.out.print(m.getKey());
                }
            }
        }
        //reset variables
        first = second = third = Double.NEGATIVE_INFINITY;
        numTies1 = numTies2 = numTies3 = 0;
        
        //on base percent
        System.out.println("\n\nON-BASE PERCENTAGE");
        for (Map.Entry<String, Player> m : sortedMap.entrySet())
        {
            
            // 	If current element larger than first element
            if(first < m.getValue().getOnBasePercent())
            {
                third = second; //second place becomes third
                second = first; //first place becomes second
                first = m.getValue().getOnBasePercent(); //first becomes current element
            }
            // 	Else if current element between 1st and 2nd 
            else if(second < m.getValue().getOnBasePercent() && m.getValue().getOnBasePercent()!= first) 
            {
                third = second; //second becomes third
                second = m.getValue().getOnBasePercent(); //current becomes second 
            }
            // 	Else if current element between 2nd and 3rd 
            else if (m.getValue().getOnBasePercent() > third && m.getValue().getOnBasePercent() != second) //if between second and third
            {
                third = m.getValue().getOnBasePercent(); //current becomes third
            }
        }
        if(first!=Double.NEGATIVE_INFINITY) //print first place
        {
            System.out.printf("%.3f\t", first);//first place value
            for (Map.Entry<String, Player> m : sortedMapA.entrySet())
            {
                if(m.getValue().getOnBasePercent() == first )
                {
                    if(numTies1 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies1++;
                    System.out.print(m.getKey());
                }
            }
            for (Map.Entry<String, Player> m : sortedMapH.entrySet())
            {
                if(m.getValue().getOnBasePercent() == first )
                {
                    if(numTies1 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies1++;
                    System.out.print(m.getKey());
                }
            }
        }
        if(second!=Double.NEGATIVE_INFINITY && numTies1 < 3) //print second place
        {
            System.out.printf("\n%.3f\t", second);
            for (Map.Entry<String, Player> m : sortedMapA.entrySet())
            {
                if(m.getValue().getOnBasePercent() == second )
                {
                    if(numTies2 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies2++;
                    System.out.print(m.getKey());
                }
            }
            for (Map.Entry<String, Player> m : sortedMapH.entrySet())
            {
                if(m.getValue().getOnBasePercent() == second )
                {
                    if(numTies2 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies2++;
                    System.out.print(m.getKey());
                }
            }
        }
        if(third!=Double.NEGATIVE_INFINITY && numTies1 == 1 && numTies2 == 1) //print third place
        {
            System.out.printf("\n%.3f\t", third);
            for (Map.Entry<String, Player> m : sortedMapA.entrySet())
            {
                if(m.getValue().getOnBasePercent() == third )
                {
                    if(numTies3 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies3++;
                    System.out.print(m.getKey());
                }
            }
            for (Map.Entry<String, Player> m : sortedMapH.entrySet())
            {
                if(m.getValue().getOnBasePercent() == third )
                {
                    if(numTies3 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies3++;
                    System.out.print(m.getKey());
                }
            }
        }
        //reset variables
        first = second = third = Double.NEGATIVE_INFINITY;
        numTies1 = numTies2 = numTies3 = 0;
        
        //hits
        System.out.println("\n\nHITS");
        for (Map.Entry<String, Player> m : sortedMap.entrySet())
        {
            
            // 	If current element larger than first element
            if(first < m.getValue().getHits())
            {
                third = second; //second place becomes third
                second = first; //first place becomes second
                first = m.getValue().getHits(); //first becomes current element
            }
            // 	Else if current element between 1st and 2nd 
            else if(second < m.getValue().getHits() && m.getValue().getHits()!= first) 
            {
                third = second; //second becomes third
                second = m.getValue().getHits(); //current becomes second 
            }
            // 	Else if current element between 2nd and 3rd 
            else if (m.getValue().getHits() > third && m.getValue().getHits() != second) //if between second and third
            {
                third = m.getValue().getHits(); //current becomes third
            }
        }
        if(first!=Double.NEGATIVE_INFINITY) //print first place
        {
            System.out.print((int)first + "\t");//first place value
            for (Map.Entry<String, Player> m : sortedMapA.entrySet())
            {
                if(m.getValue().getHits() == first )
                {
                    if(numTies1 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies1++;
                    System.out.print(m.getKey());
                }
            }
            for (Map.Entry<String, Player> m : sortedMapH.entrySet())
            {
                if(m.getValue().getHits() == first )
                {
                    if(numTies1 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies1++;
                    System.out.print(m.getKey());
                }
            }
        }
        if(second!=Double.NEGATIVE_INFINITY && numTies1 < 3) //print second place
        {
            System.out.print((int)second + "\t");
            for (Map.Entry<String, Player> m : sortedMapA.entrySet())
            {
                if(m.getValue().getHits() == second )
                {
                    if(numTies2 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies2++;
                    System.out.print(m.getKey());
                }
            }
            for (Map.Entry<String, Player> m : sortedMapH.entrySet())
            {
                if(m.getValue().getHits() == second )
                {
                    if(numTies2 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies2++;
                    System.out.print(m.getKey());
                }
            }
        }
        if(third!=Double.NEGATIVE_INFINITY && numTies1 == 1 && numTies2 == 1) //print third place
        {
            System.out.print((int)third + "\t");
            for (Map.Entry<String, Player> m : sortedMapA.entrySet())
            {
                if(m.getValue().getHits() == third )
                {
                    if(numTies3 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies3++;
                    System.out.print(m.getKey());
                }
            }
            for (Map.Entry<String, Player> m : sortedMapH.entrySet())
            {
                if(m.getValue().getHits() == third )
                {
                    if(numTies3 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies3++;
                    System.out.print(m.getKey());
                }
            }
        }
        //reset variables
        first = second = third = Double.NEGATIVE_INFINITY;
        numTies1 = numTies2 = numTies3 = 0;
        
        //walks
        System.out.println("\n\nWALKS");
        for (Map.Entry<String, Player> m : sortedMap.entrySet())
        {
            // 	If current element larger than first element
            if(first < m.getValue().getWalks())
            {
                third = second; //second place becomes third
                second = first; //first place becomes second
                first = m.getValue().getWalks(); //first becomes current element
            }
            // 	Else if current element between 1st and 2nd 
            else if(second < m.getValue().getWalks() && m.getValue().getWalks()!= first) 
            {
                third = second; //second becomes third
                second = m.getValue().getWalks(); //current becomes second 
            }
            // 	Else if current element between 2nd and 3rd 
            else if (m.getValue().getWalks() > third && m.getValue().getWalks() != second) //if between second and third
            {
                third = m.getValue().getWalks(); //current becomes third
            }
        }
        if(first!=Double.NEGATIVE_INFINITY) //print first place
        {
            System.out.print((int)first + "\t");//first place value
            
            for (Map.Entry<String, Player> m : sortedMapA.entrySet())
            {
                if(m.getValue().getWalks() == first )
                {
                    if(numTies1 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies1++;
                    System.out.print(m.getKey());
                }
            }
            for (Map.Entry<String, Player> m : sortedMapH.entrySet())
            {
                if(m.getValue().getWalks() == first )
                {
                    if(numTies1 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies1++;
                    System.out.print(m.getKey());
                }
            }
        }
        if(second!=Double.NEGATIVE_INFINITY && numTies1 < 3) //print second place
        {
            System.out.print((int)second + "\t");
            for (Map.Entry<String, Player> m : sortedMapA.entrySet())
            {
                if(m.getValue().getWalks() == second )
                {
                    if(numTies2 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies2++;
                    System.out.print(m.getKey());
                }
            }
            for (Map.Entry<String, Player> m : sortedMapH.entrySet())
            {
                if(m.getValue().getWalks() == second )
                {
                    if(numTies2 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies2++;
                    System.out.print(m.getKey());
                }
            }
        }
        if(third!=Double.NEGATIVE_INFINITY && numTies1 == 1 && numTies2 == 1) //print third place
        {
            System.out.print((int)third + "\t");
            for (Map.Entry<String, Player> m : sortedMapA.entrySet())
            {
                if(m.getValue().getWalks() == third )
                {
                    if(numTies3 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies3++;
                    System.out.print(m.getKey());
                }
            }
            for (Map.Entry<String, Player> m : sortedMapH.entrySet())
            {
                if(m.getValue().getWalks() == third )
                {
                    if(numTies3 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies3++;
                    System.out.print(m.getKey());
                }
            }
        }
        //reset variables
        first = second = third = Double.POSITIVE_INFINITY;
        numTies1 = numTies2 = numTies3 = 0;
        
        
        //strikeouts
        System.out.println("\n\nSTRIKEOUTS");
        for (Map.Entry<String, Player> m : sortedMap.entrySet())
        {
            
            // 	If current element larger than first element
            if(first > m.getValue().getStrikeouts())
            {
                third = second; //second place becomes third
                second = first; //first place becomes second
                first = m.getValue().getStrikeouts(); //first becomes current element
            }
            // 	Else if current element between 1st and 2nd 
            else if(second > m.getValue().getStrikeouts() && m.getValue().getStrikeouts()!= first) 
            {
                third = second; //second becomes third
                second = m.getValue().getStrikeouts(); //current becomes second 
            }
            // 	Else if current element between 2nd and 3rd 
            else if (m.getValue().getStrikeouts() < third && m.getValue().getStrikeouts() != second) //if between second and third
            {
                third = m.getValue().getStrikeouts(); //current becomes third
            }
        }
        if(first!=Double.POSITIVE_INFINITY) //print first place
        {
            System.out.print((int)first + "\t");
            for (Map.Entry<String, Player> m : sortedMapA.entrySet())
            {
                if(m.getValue().getStrikeouts() == first )
                {
                    if(numTies1 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies1++;
                    System.out.print(m.getKey());
                }
            }
            for (Map.Entry<String, Player> m : sortedMapH.entrySet())
            {
                if(m.getValue().getStrikeouts() == first )
                {
                    if(numTies1 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies1++;
                    System.out.print(m.getKey());
                }
            }
        }
        if(second!=Double.POSITIVE_INFINITY && numTies1 < 3) //print second place
        {
            System.out.print((int)second + "\t");
            for (Map.Entry<String, Player> m : sortedMapA.entrySet())
            {
                if(m.getValue().getStrikeouts() == second )
                {
                    if(numTies2 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies2++;
                    System.out.print(m.getKey());
                }
            }
            for (Map.Entry<String, Player> m : sortedMapH.entrySet())
            {
                if(m.getValue().getStrikeouts() == second )
                {
                    if(numTies2 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies2++;
                    System.out.print(m.getKey());
                }
            }
        }
        if(third!=Double.POSITIVE_INFINITY && numTies1 == 1 && numTies2 == 1) //print third place
        {
            System.out.print((int)third + "\t");
            for (Map.Entry<String, Player> m : sortedMapA.entrySet())
            {
                if(m.getValue().getStrikeouts() == third )
                {
                    if(numTies3 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies3++;
                    System.out.print(m.getKey());
                }
            }
            for (Map.Entry<String, Player> m : sortedMapH.entrySet())
            {
                if(m.getValue().getStrikeouts() == third )
                {
                    if(numTies3 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies3++;
                    System.out.print(m.getKey());
                }
            }
        }
        //reset variables
        first = second = third = Double.NEGATIVE_INFINITY;
        numTies1 = numTies2 = numTies3 = 0;
        
        
        //hit by pitch
        System.out.println("\n\nHIT BY PITCH");
        for (Map.Entry<String, Player> m : sortedMap.entrySet())
        {
            
            // 	If current element larger than first element
            if(first < m.getValue().getHitByPitches())
            {
                third = second; //second place becomes third
                second = first; //first place becomes second
                first = m.getValue().getHitByPitches(); //first becomes current element
            }
            // 	Else if current element between 1st and 2nd 
            else if(second < m.getValue().getHitByPitches() && m.getValue().getHitByPitches()!= first) 
            {
                third = second; //second becomes third
                second = m.getValue().getHitByPitches(); //current becomes second 
            }
            // 	Else if current element between 2nd and 3rd 
            else if (m.getValue().getHitByPitches() > third && m.getValue().getHitByPitches() != second) //if between second and third
            {
                third = m.getValue().getHitByPitches(); //current becomes third
            }
        }
        if(first!=Double.NEGATIVE_INFINITY) //print first place
        {
            System.out.print((int)first + "\t");
            for (Map.Entry<String, Player> m : sortedMapA.entrySet())
            {
                if(m.getValue().getHitByPitches() == first )
                {
                    if(numTies1 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies1++;
                    System.out.print(m.getKey());
                }
            }
            for (Map.Entry<String, Player> m : sortedMapH.entrySet())
            {
                if(m.getValue().getHitByPitches() == first )
                {
                    if(numTies1 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies1++;
                    System.out.print(m.getKey());
                }
            }
        }
        if(second!=Double.NEGATIVE_INFINITY && numTies1 < 3) //print second place
        {
            System.out.print((int)second + "\t");
            for (Map.Entry<String, Player> m : sortedMapA.entrySet())
            {
                if(m.getValue().getHitByPitches() == second )
                {
                    if(numTies2 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies2++;
                    System.out.print(m.getKey());
                }
            }
            for (Map.Entry<String, Player> m : sortedMapH.entrySet())
            {
                if(m.getValue().getHitByPitches() == second )
                {
                    if(numTies2 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies2++;
                    System.out.print(m.getKey());
                }
            }
        }
        if(third!=Double.NEGATIVE_INFINITY && numTies1 == 1 && numTies2 == 1) //print third place
        {
            System.out.print((int)third + "\t");
            for (Map.Entry<String, Player> m : sortedMapA.entrySet())
            {
                if(m.getValue().getHitByPitches() == third )
                {
                    if(numTies3 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies3++;
                    System.out.print(m.getKey());
                }
            }
            for (Map.Entry<String, Player> m : sortedMapH.entrySet())
            {
                if(m.getValue().getHitByPitches() == third )
                {
                    if(numTies3 > 0) //if names are already listed
                    {
                        System.out.print(", ");
                    }
                    numTies3++;
                    System.out.print(m.getKey());
                }
            }
        }
        //reset variables
        first = second = third = Double.NEGATIVE_INFINITY;
        numTies1 = numTies2 = numTies3 = 0;
        System.out.println();
    }
}