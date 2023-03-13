//Fatima Khalid
//fxk200007
#include <iostream>
#include <fstream>
#include <string>
#include <cctype>
#include <iomanip>
#include <cmath>

using namespace std;

string GetName(string, string[], int);//Extracts player name
void ParseBaseStats(string, int[],int[],int[],int[],int[],int[], double[], double[], int); //Extracts player stats
void DisplayPlayers(string[], int[],int[],int[],int[], double[], double[], int, int); //Displays individual stats
void Leaderboard(string[], int[],int[],int[],int[], double[], double[], int); //Displays leaderboard

int main()
{
    ifstream inFile; //creates file stream object for input file
    string fileName; //to hold input file name
    getline(cin, fileName); //get user input for input file name
    inFile.open(fileName); //open file
    
    //Prints errors untill user enters a valid file
    while(!inFile)
    {
        cout << "Error opening input file.\nPlease enter input file name again: ";
        getline(cin, fileName);
        inFile.open(fileName);
    }
    
    string line; //to hold one line from file at a time
    int playerCount = 0; //holds number of players in file
    
    //loop to get number of players
    while(getline(inFile, line))
    {
        
        if (line == "") //if file is empty, stop reading
        {
            break;
        }
        playerCount++; //keeps track of number of players
        
    }

    //create arrays to hold data
    string *names = new string[playerCount];
    
    int *hit{ new int[playerCount]{} };
    int *out{ new int[playerCount]{} };
    int *strikeout{ new int[playerCount]{} };
    int *walk{ new int[playerCount]{} };
    int *hitByPitch{ new int[playerCount]{} };
    int *sacrifice{ new int[playerCount]{} };

    double *batAvg{ new double[playerCount]{} };
    double *onBase{ new double[playerCount]{} };

    //Close and reopen input file to reset flags/read position
    inFile.close();
    inFile.open(fileName);
    
    int playerIndex = 0; //to hold current player's index in all the arrays
    
    //loop to store and display each player data, one line at a time
    while(getline(inFile, line))
    {
        if (line == "") //if file is empty, stop reading
        {
            break;
        }
        
        line = GetName(line, names, playerIndex); //update line so that name is removed and stored in array
        
        //extract player stats
        ParseBaseStats(line, hit, out, strikeout, walk, hitByPitch, sacrifice, batAvg, onBase, playerIndex); 
        
        playerIndex++; //move on to next player
    }
    
    playerIndex = 0; //reset to first player so we can display all data 
    DisplayPlayers(names, hit, strikeout, walk, hitByPitch, batAvg, onBase, playerCount, playerIndex); //Display individual stats
    
    
    Leaderboard(names, hit, strikeout, walk, hitByPitch, batAvg, onBase, playerCount); //Display leaderboard
    
    inFile.close(); //Close input file
    
    //Delete dynamically allocated memory
    delete[] names;
    delete[] hit;
    delete[] strikeout;
    delete[] walk;
    delete[] hitByPitch;
    delete[] sacrifice;
    delete[] batAvg;
    delete[] onBase;
    
    return 0;
}


string GetName(string line, string names[], int curIndex)
{
    int spacePos = 0; //Marks index of first space found
    
    while(!(isspace(line[spacePos]))) //untill first space found
    {
        spacePos++; //update index of first space
    }
    
    string name = line.substr(0,spacePos); //store name in a string
    names[curIndex] = name; //add name to name array 

    line = line.substr(spacePos+1); // update string so its just stats
    return line; //send updated string back
    
}

void ParseBaseStats(string stats, int h[],int o[],int k[],int w[],int p[],int s[], double ba[], double obp[], int curIndex) //gather stats
{
    double atBats = 0, plateApps = 0; //to hold at-bats and plate appearances
    
    for(int i = 0; i < static_cast<int>(stats.size()); i++) //parses string character by character and updates stat arrays
    {
        //hits
        if(stats.at(i) == 'H')
        {
            h[curIndex]++;
            plateApps++;
            atBats++;
        }
        //outs
        else if(stats.at(i) == 'O')
        {
            o[curIndex]++;
            plateApps++;
            atBats++;
        }
        //strikeouts
        else if(stats.at(i) == 'K')
        {
            k[curIndex]++;
            plateApps++;
            atBats++;
        }
        //walks
        else if(stats.at(i) == 'W')
        {
            w[curIndex]++;
            plateApps++;
        }
        //hit by pitches
        else if(stats.at(i) == 'P')
        {
            p[curIndex]++;
            plateApps++;
        }
        //sacrifices
        else if(stats.at(i) == 'S')
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
    obp[curIndex] = round(1000 * obp[curIndex]) / 1000;
    
}

void DisplayPlayers(string names[], int h[], int k[],int w[],int p[], double ba[], double obp[], int size, int curIndex) //display individual stats
{
    while(curIndex < size) //While players are still being read
    {
        cout << setprecision(3) << fixed << showpoint; //Set output to 3 decimal places
        
        cout << names[curIndex] << endl; //Print name
        cout << "BA: " << ba[curIndex] << endl; //print batting average
        cout << "OB%: " << obp[curIndex] << endl; //print on base percentage
        cout << "H: " << h[curIndex] << endl; //print Hits
        cout << "BB: " << w[curIndex] << endl;//print Walks
        cout << "K: " << k[curIndex] << endl; //print strikeouts
        cout << "HBP: " << p[curIndex] << endl << endl; //print hits by pitch
        
        curIndex++; //move on to next player
    }

}

void Leaderboard(string names[], int h[],int k[],int w[],int p[], double ba[], double obp[], int size) //create leaderboard
{
    //to hold the highest scores (seperate data types for accurate output rounding)
    double highest; 
    int high;
    
    int winIndex = 0; //to hold the player number so we can get their name

    cout << setprecision(3) << fixed << showpoint; //format output
    
    cout << "LEAGUE LEADERS\n"; //display heading
    
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
    cout << "BA: " << names[winIndex];
    
    //once highest value is found, iterate through rest of array to catch any ties
    for(int i = 0; i < size; i++)
    {
        if(ba[i] == highest && i != winIndex)
        {
            //display remaining winners
            cout << ", " << names[i];
        }
    }
    //print score of winners
    cout << " " << highest << endl;
    
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
    cout << "OB%: " << names[winIndex];
    
    //once highest value is found, iterate through rest of array to catch any ties
    for(int i = 0; i < size; i++)
    {
        if(obp[i] == highest && i != winIndex)
        {
            //display remaining winners
            cout << ", " << names[i];
        }
    }
    //print score of winners
    cout << " " << highest << endl;

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
    cout << "H: " << names[winIndex];
    //once highest value is found, iterate through rest of array to catch any ties
    for(int i = 0; i < size; i++)
    {
        if(h[i] == high && i != winIndex)
        {
            cout << ", " << names[i];
        }
    }
    //display remaining winners
    cout << " " << high << endl;

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
    cout << "BB: " << names[winIndex];
    
    //once highest value is found, iterate through rest of array to catch any ties
    for(int i = 0; i < size; i++)
    {
        if(w[i] == high && i != winIndex) 
        {
            cout << ", " << names[i];
        }
    }
    //display remaining winners
    cout << " " << high << endl;
    


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
    cout << "K: " << names[winIndex];
    //once highest value is found, iterate through rest of array to catch any ties
    for(int i = 0; i < size; i++)
    {
        if(k[i] == lowest && i != winIndex) 
        {
            cout << ", " << names[i];
        }
    }
    //display remaining winners
    cout << " " << lowest << endl;
    
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
    cout << "HBP: " << names[winIndex];
    //once highest value is found, iterate through rest of array to catch any ties
    for(int i = 0; i < size; i++)
    {
        if(p[i] == high && i != winIndex)
        {
            cout << ", " << names[i];
        }
    }
    //display remaining winners
    cout << " " << high << endl;
    
}

    
