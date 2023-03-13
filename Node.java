public class Node{
    Player player;
    Node next;
    
    //constructor that creates a player node pointing to null
    public Node(Player p){player = p; next= null;}
    
    //accessors
    public Player getPlayer(){return player;}
    public Node getNext(){return next;}
    
    //mutators
    public void setPlayer(Player p){player = p;}
    public void setNext(Node n){next = n;}
}