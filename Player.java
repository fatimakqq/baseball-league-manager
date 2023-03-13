public class Player{
    //variables
    private String name = "";
    private int hits = 0;
    private int outs = 0;
    private int strikeouts = 0;
    private int walks = 0;
    private int hitByPitches = 0;
    private int sacrifices = 0;
    
    //overloaded constructor with name
    public Player(String n){name = n;}
    //overloaded constructor with all player info 
    public Player(String n, int h, int o, int k, int w, int p, int s){
        name = n;
        hits = h;
        outs=o;
        strikeouts=k;
        walks=w;
        hitByPitches=p;
        sacrifices = s;
        
    }
    
    //accessors
    public String getName(){return name;}
    public int getHits(){return hits;}
    public int getOuts(){return outs;}
    public int getStrikeouts(){return strikeouts;}
    public int getWalks(){return walks;}
    public int getHitByPitches(){return hitByPitches;}
    public int getSacrifices(){return sacrifices;}
    
    //mutators
    public void setName(String n ){name = n;}
    public void setHits(int h){hits = h;}
    public void setOuts(int o){outs = o;}
    public void setStrikeouts(int k){strikeouts = k;}
    public void setWalks(int w){walks = w;}
    public void setHitByPitches(int p){hitByPitches = p;}
    public void setSacrifices(int s){sacrifices = s;}
    
    //Calculate at bats
    public int getAtBats(){return hits + outs + strikeouts;}
    //Calculate plate appearances
    public int getPlateApps(){return hits + outs + strikeouts + walks + hitByPitches + sacrifices;}
    //Calculate batting average
    public double getBattingAvg()
    {
        if((hits+outs+strikeouts)!= 0)
            return (double)(hits)/(hits+outs+strikeouts);
        else 
            return 0.0;
        
    }
    //Calculate on base percentage 
    public double getOnBasePercent()
    {
        if((hits + outs + strikeouts + walks + hitByPitches + sacrifices)!=0)
            return (double)(hits + walks + hitByPitches)/(hits + outs + strikeouts + walks + hitByPitches + sacrifices);
        else
            return 0.0;
        
    }
    
    
}
