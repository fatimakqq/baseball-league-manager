public class LinkList{
    //hold head of linked list
    private Node head;
    //constructors
    public LinkList(){head = null;}
    public LinkList(Node h){head = h;}
    //accessors
    public Node getHead(){return head;}
    //mutators
    public void setHead(Node h ){head = h;}
    
    public Player SearchPlayer(String nameToFind) //check if a player already exists in linked list
    {
        //if list empty, return null (player not found)
        if(head==null)
        {
            return null;
        }
        Node cur = head; //for traversal 
        
        while(cur!= null) //traverse through entire list
        {
            if( (cur.getPlayer().getName()).equals(nameToFind) ) //if a match is found
            {
                return cur.getPlayer(); //return that player so we can modify their data
            }
            cur = cur.getNext();
            
        }
        return null; //player not found
    }
    public void Append(Node n) //add node to linked list
    {
        //if linked list is empty, make new node the head
        if (head == null)
        {
            head = n;
            return;
        }
        //else, traverse till the last node
        Node cur = head;
        while(cur.getNext() != null)
            cur = cur.next;
        
        //set node to last node 
        cur.next = n;
        return;
    }
    public void toString(Node cur) //prints all player data in linked list
    {
        if(cur.getNext() != null) //base case: there is no more players in list
        {
            toString(cur.getNext()); // recursively call function again
        }
        //print player's name, at-bats, hits, walks, strikeouts, hitbypitches, sacrifices, batting average, and on base percentage
        System.out.print(cur.getPlayer().getName() + "\t" + cur.getPlayer().getAtBats() + "\t" + cur.getPlayer().getHits() + "\t" +
        cur.getPlayer().getWalks() + "\t" + cur.getPlayer().getStrikeouts() + "\t" + cur.getPlayer().getHitByPitches() + "\t" + 
        cur.getPlayer().getSacrifices() + "\t");
        System.out.printf("%.3f\t", cur.getPlayer().getBattingAvg());
        System.out.printf("%.3f\n", cur.getPlayer().getOnBasePercent());
        
    }
    public void Sort() //alphabetically sort linked list
    {
        Node cur = head; //Traversal pointer
    
        int size = 0; //Hold size of list
    
        //Traverse list one time, calculating size of current list
        while(cur!=null) //While end of list is not reached
        {
            size++; //Update size
            cur = cur.getNext(); //Move traversal pointer
        }
        
        cur = head;
    
        if(size == 2) //If list only has two nodes,
        {
        
            //if current nameis alphabetically before the name that comes after 
            if( ((cur.getPlayer().getName()).compareTo((cur.getNext().getPlayer().getName()))) < 0 ) //Check if nodes need to be swapped
            {
                //swap both nodes
                cur = head.getNext(); 
                cur.setNext(head); 
                head.setNext(null);
                head = cur;
            }
            
        }
        else //Perform bubble sort on longer lists
        {
            cur = head; //Reset traversal pointer
            Node before = null; //To hold previous node while performing sort
            boolean flag = false; //To run bubble sort
            
            do
            {
                
                flag = false; //Reset flag
                cur = head; //Reset pointer position for next pass
                
                while(cur.getNext()!=null) //While current node is not the last one
                {
                    if(cur!= head) //If not at starting position, set previous pointer location
                    {
                        before = head;
                        //Traverse to node before current node
                        while(before.getNext()!=cur)
                        {
                            before = before.getNext();
                        }
                    }
                    
                    if(((cur.getPlayer().getName()).compareTo((cur.getNext().getPlayer().getName()))) < 0) //If a swap needs to occur
                    {
                        if(cur == head) //If swapping first two nodes
                        {
                            head = cur.getNext(); //Set new head to second element
                            cur.setNext(cur.getNext().getNext()); //point original first element to third element
                            head.setNext(cur); //point first element to original first element
                        }
                        else //Swapping any other two nodes
                        {
                            
                            before.setNext(cur.getNext());//Point previous node to node after current
                            cur.setNext(cur.getNext().getNext()); //Point current node to node after node after current
                            before.getNext().setNext(cur); //Point node after previous to current node
                            cur = before.getNext(); //Set new current node
                        }
                        flag = true; 
                    }
                    cur = cur.getNext(); //Move traversal
                } 
                //one pass completed
                
            }while(flag); 
        }
    }
    public void ReverseSort() //reverse alphabetically sort linked list
    {
        Node cur = head; //Traversal pointer
    
        int size = 0; //Hold size of list
    
        //Traverse list one time, calculating size of current list
        while(cur!=null) //While end of list is not reached
        {
            size++; //Update size
            cur = cur.getNext(); //Move traversal pointer
        }
        
        cur = head;
    
        if(size == 2) //If list only has two nodes,
        {
        
            //if current nameis alphabetically before the name that comes after 
            if( ((cur.getPlayer().getName()).compareTo((cur.getNext().getPlayer().getName()))) > 0 ) //Check if nodes need to be swapped
            {
                //System.out.println("swapping " + cur.getPlayer().getName() + "(alphabetically first) with " + cur.getNext().getPlayer().getName());
                //swap both nodes
                cur = head.getNext(); //point cur to second node
                cur.setNext(head);
                head.setNext(null);
                head = cur;
            }
            
        }
        else //Perform bubble sort on longer lists
        {
            cur = head; //Reset traversal pointer
            Node before = null; //To hold previous node while performing sort
            boolean flag = false; //To run bubble sort
            
            do
            {
                
                flag = false; //Reset flag
                cur = head; //Reset pointer position for next pass
                
                while(cur.getNext()!=null) //While current node is not the last one
                {
                    if(cur!= head) //If not at starting position, set previous pointer location
                    {
                        before = head;
                        //Traverse to node before current node
                        while(before.getNext()!=cur)
                        {
                            before = before.getNext();
                        }
                    }
                    
                    if(((cur.getPlayer().getName()).compareTo((cur.getNext().getPlayer().getName()))) > 0) //If a swap needs to occur
                    {
                        if(cur == head) //If swapping first two nodes
                        {
                            head = cur.getNext(); //Set new head to second element
                            cur.setNext(cur.getNext().getNext()); //point original first element to third element
                            head.setNext(cur); //point first element to original first element
                        }
                        else //Swapping any other two nodes
                        {
                            
                            before.setNext(cur.getNext());//Point previous node to node after current
                            cur.setNext(cur.getNext().getNext()); //Point current node to node after node after current
                            before.getNext().setNext(cur); //Point node after previous to current node
                            cur = before.getNext(); //Set new current node
                        }
                        flag = true; 
                    }
                    cur = cur.getNext(); //Move traversal
                } 
                //one pass completed
                
            }while(flag); 
        }
    }
    public void Leaderboard() //calculate league leaders
    {
        System.out.println("\nLEAGUE LEADERS");
        //batting average
        System.out.println("BATTING AVERAGE");
        
        //to hold first, second, and third highest scores: set to an impossibly low value
        double first = Double.NEGATIVE_INFINITY;
        double second = Double.NEGATIVE_INFINITY;
        double third = Double.NEGATIVE_INFINITY;
        Node cur = head; //traversal pointer
        
        //to hold positions of top 3 winners in list to help find ties later
        Node hold1 = head;
        Node hold2 = head;
        Node hold3 = head;
        
        while(cur != null) //traverse linked list
        {
            if(first < cur.getPlayer().getBattingAvg()) //if larger than first element
            {
                third = second; //second place becomes third
                hold3 = hold2;

                second = first; //first place becomes second
                hold2 = hold1;

                first = cur.getPlayer().getBattingAvg(); //first becomes current element
                hold1 = cur;
            }
            
            else if(second < cur.getPlayer().getBattingAvg() && cur.getPlayer().getBattingAvg()!= first) //if between first and second
            {
                third = second; //second becomes third
                hold3 = hold2;

                second = cur.getPlayer().getBattingAvg(); //current becomes second 
                hold2 = cur;
            }
            else if (cur.getPlayer().getBattingAvg() > third && cur.getPlayer().getBattingAvg() != second) //if between second and third
            {
                third = cur.getPlayer().getBattingAvg(); //current becomes third
                hold3 = cur;
            }
            
            cur = cur.getNext();
        }
        //we now have first, second, third place
        int numTies1 = 0, numTies2=0, numTies3 = 0;
        //loop through for ties, using hold pointers to identify
        if(first!=Double.NEGATIVE_INFINITY) //print first place only if it exists
        {
            System.out.printf("%.3f\t%s", first, hold1.getPlayer().getName());
            cur = head;
            while(cur!= null) //find first place ties
            {
                if(cur.getPlayer().getBattingAvg() == first && cur!= hold1) //tie for first place found
                {
                    System.out.print(", " + cur.getPlayer().getName()); //print tie
                    numTies1++;
                }
                
                cur = cur.getNext();
                
            }
            System.out.println();
        }
        
        
        if(second!=Double.NEGATIVE_INFINITY && numTies1 < 2) //print second place only if it exists and if less than 3 winners for 1st place
        {
            System.out.printf("%.3f\t%s", second, hold2.getPlayer().getName());
            cur = head;
            while(cur!= null) //find second place ties
            {
                if(cur.getPlayer().getBattingAvg() == second && cur!= hold2) //tie for second place found
                {
                    System.out.print(", " + cur.getPlayer().getName());
                    numTies2++;
                }
                
                cur = cur.getNext();
                
            }
            System.out.println();
        }
        
        
        if(third!=Double.NEGATIVE_INFINITY && numTies1 == 0 && numTies2 == 0) //print third place only if it exists
        {
            System.out.printf("%.3f\t%s", third, hold3.getPlayer().getName());
            cur = head;
            while(cur!= null) //find third place ties
            {
                if(cur.getPlayer().getBattingAvg() == third && cur!= hold3) //tie for third place found
                {
                    System.out.print(", " + cur.getPlayer().getName());
                    numTies3++;
                }
                
                cur = cur.getNext();
                
            }
            System.out.println();
        }
    
        
        //reset variables and pointers
        first = second = third = Double.NEGATIVE_INFINITY;
        cur = hold1 = hold2= hold3= head;
        
        System.out.println("\nON-BASE PERCENTAGE");
        while(cur != null)
        {
            if(first < cur.getPlayer().getOnBasePercent()) //if larger than first element
            {
                third = second;
                hold3 = hold2;

                second = first;
                hold2 = hold1;

                first = cur.getPlayer().getOnBasePercent();
                hold1 = cur;
            }
            
            else if(second < cur.getPlayer().getOnBasePercent() && cur.getPlayer().getOnBasePercent()!= first) //if between first and second
            {
                third = second;
                hold3 = hold2;

                second = cur.getPlayer().getOnBasePercent();
                hold2 = cur;
            }
            else if (cur.getPlayer().getOnBasePercent() > third && cur.getPlayer().getOnBasePercent() != second) //if between second and third 
            {
                third = cur.getPlayer().getOnBasePercent();
                hold3 = cur;
            }
            
            cur = cur.getNext();
        }
        //we now have first, second, third place
        numTies1 = numTies2 =  numTies3 = 0;//reset tie counters
        //loop through for ties, using hold pointers to identify
        if(first!=Double.NEGATIVE_INFINITY) //print first place only if it exists
        {
            System.out.printf("%.3f\t%s", first, hold1.getPlayer().getName());
            cur = head;
            while(cur!= null) //find first place ties
            {
                if(cur.getPlayer().getOnBasePercent() == first && cur!= hold1) //tie for first place found
                {
                    System.out.print(", " + cur.getPlayer().getName());
                    numTies1++;
                }
                
                cur = cur.getNext();
                
            }
            System.out.println();
        }

        if(second!=Double.NEGATIVE_INFINITY && numTies1 < 2) //print second place only if it exists
        {
            System.out.printf("%.3f\t%s", second, hold2.getPlayer().getName());
            cur = head;
            while(cur!= null) //find second place ties
            {
                if(cur.getPlayer().getOnBasePercent() == second && cur!= hold2) //tie for second place found
                {
                    System.out.print(", " + cur.getPlayer().getName());
                    numTies2++;
                }
                
                cur = cur.getNext();
                
            }
            System.out.println();
        }

        if(third!=Double.NEGATIVE_INFINITY && numTies1 == 0 && numTies2 == 0) //print third place only if it exists
        {
            System.out.printf("%.3f\t%s", third, hold3.getPlayer().getName());
            cur = head;
            while(cur!= null) //find third place ties
            {
                if(cur.getPlayer().getOnBasePercent() == third && cur!= hold3) //tie for third place found
                {
                    System.out.print(", " + cur.getPlayer().getName());
                    numTies3++;
                }
                
                cur = cur.getNext();
                
            }
            System.out.println();
        }

        //reset variables and pointers
        first = second = third = Double.NEGATIVE_INFINITY;
        cur = hold1 = hold2= hold3= head;
        
        System.out.println("\nHITS");
        while(cur != null)
        {
            if(first < cur.getPlayer().getHits()) //if larger than first element
            {
                third = second;
                hold3 = hold2;

                second = first;
                hold2 = hold1;

                first = cur.getPlayer().getHits();
                hold1 = cur;
            }
            
            else if(second < cur.getPlayer().getHits() && cur.getPlayer().getHits()!= first) //if between first and second
            {
                third = second;
                hold3 = hold2;

                second = cur.getPlayer().getHits();
                hold2 = cur;
            }
            else if (cur.getPlayer().getHits() > third && cur.getPlayer().getHits() != second) //if between second and third
            {
                third = cur.getPlayer().getHits();
                hold3 = cur;
            }
            
            cur = cur.getNext();
        }
        //we now have first, second, third place
        numTies1 = numTies2 =  numTies3 = 0;
        //loop through for ties, using hold pointers to identify
        if(first!=Double.NEGATIVE_INFINITY) //print first place only if it exists
        {
            System.out.printf("%d\t%s", (int)first, hold1.getPlayer().getName());
            cur = head;
            while(cur!= null) //find first place ties
            {
                if(cur.getPlayer().getHits() == first && cur!= hold1) //tie for first place found
                {
                    System.out.print(", " + cur.getPlayer().getName());
                    numTies1++;
                }
                
                cur = cur.getNext();
                
            }
            System.out.println();
        }

        if(second!=Double.NEGATIVE_INFINITY && numTies1 < 2) //print second place only if it exists
        {
            System.out.printf("%d\t%s", (int)second, hold2.getPlayer().getName());
            cur = head;
            while(cur!= null) //find second place ties
            {
                if(cur.getPlayer().getHits() == second && cur!= hold2) //tie for second place found
                {
                    System.out.print(", " + cur.getPlayer().getName());
                    numTies2++;
                }
                
                cur = cur.getNext();
                
            }
            System.out.println();
        }

        if(third!=Double.NEGATIVE_INFINITY && numTies1 == 0 && numTies2 == 0) //print third place only if it exists
        {
            System.out.printf("%d\t%s", (int)third, hold3.getPlayer().getName());
            cur = head;
            while(cur!= null) //find 3rd place ties
            {
                if(cur.getPlayer().getHits() == third && cur!= hold3) //tie for 3rd place found
                {
                    System.out.print(", " + cur.getPlayer().getName());
                    numTies3++;
                }
                
                cur = cur.getNext();
                
            }
            System.out.println();
        }

        //reset variables and pointers
        first = second = third = Double.NEGATIVE_INFINITY;
        cur = hold1 = hold2= hold3= head;
        
        System.out.println("\nWALKS");
        while(cur != null)
        {
            if(first < cur.getPlayer().getWalks()) //if larger than first element
            {
                third = second;
                hold3 = hold2;

                second = first;
                hold2 = hold1;

                first = cur.getPlayer().getWalks();
                hold1 = cur;
            }
            
            else if(second < cur.getPlayer().getWalks() && cur.getPlayer().getWalks()!= first) //if between first and second
            {
                third = second;
                hold3 = hold2;

                second = cur.getPlayer().getWalks();
                hold2 = cur;
            }
            else if (cur.getPlayer().getWalks() > third && cur.getPlayer().getWalks() != second) //if between second and third
            {
                third = cur.getPlayer().getWalks();
                hold3 = cur;
            }
            
            cur = cur.getNext();
        }
        //we now have first, second, third place
        numTies1 = numTies2 =  numTies3 = 0;
        //loop through for ties, using hold pointers to identify
        if(first!=Double.NEGATIVE_INFINITY) //print first place only if it exists
        {
            System.out.printf("%d\t%s", (int)first, hold1.getPlayer().getName());
            cur = head;
            while(cur!= null) //find first place ties
            {
                if(cur.getPlayer().getWalks() == first && cur!= hold1) //tie for first place found
                {
                    System.out.print(", " + cur.getPlayer().getName());
                    numTies1++;
                }
                
                cur = cur.getNext();
                
            }
            System.out.println();
        }

        if(second!=Double.NEGATIVE_INFINITY && numTies1 < 2) //print second place only if it exists
        {
            System.out.printf("%d\t%s", (int)second, hold2.getPlayer().getName());
            cur = head;
            while(cur!= null) //find second place ties
            {
                if(cur.getPlayer().getWalks() == second && cur!= hold2) //tie for second place found
                {
                    System.out.print(", " + cur.getPlayer().getName());
                    numTies2++;
                }
                
                cur = cur.getNext();
                
            }
            System.out.println();
        }

        if(third!=Double.NEGATIVE_INFINITY && numTies1 == 0 && numTies2 == 0) //print third place only if it exists
        {
            System.out.printf("%d\t%s", (int)third, hold3.getPlayer().getName());
            cur = head;
            while(cur!= null) //find third place ties
            {
                if(cur.getPlayer().getWalks() == third && cur!= hold3) //tie for third place found
                {
                    System.out.print(", " + cur.getPlayer().getName());
                    numTies3++;
                }
                
                cur = cur.getNext();
                
            }
            System.out.println();
        }

        //reset variables and pointers
        first = second = third = Double.POSITIVE_INFINITY;
        cur = hold1 = hold2 = hold3 = head;
        
        System.out.println("\nSTRIKEOUTS");
        while(cur != null)
        {
            if(first > cur.getPlayer().getStrikeouts()) //if larger than first element
            {
                third = second;
                hold3 = hold2;

                second = first;
                hold2 = hold1;

                first = cur.getPlayer().getStrikeouts();
                hold1 = cur;
            }
            
            else if(second > cur.getPlayer().getStrikeouts() && cur.getPlayer().getStrikeouts()!= first) //if between first and second
            {
                third = second;
                hold3 = hold2;

                second = cur.getPlayer().getStrikeouts();
                hold2 = cur;
            }
            else if (cur.getPlayer().getStrikeouts() < third && cur.getPlayer().getStrikeouts() != second) //if between second and third
            {
                third = cur.getPlayer().getStrikeouts();
                hold3 = cur;
            }
            
            cur = cur.getNext();
        }
        //we now have first, second, third place
        numTies1 = numTies2 =  numTies3 = 0;
        //loop through for ties, using hold pointers to identify
        if(first!=Double.POSITIVE_INFINITY) //print first place only if it exists
        {
            System.out.printf("%d\t%s", (int)first, hold1.getPlayer().getName());
            cur = head;
            while(cur!= null) //find first place ties
            {
                if(cur.getPlayer().getStrikeouts() == first && cur!= hold1) //tie for first place found
                {
                    System.out.print(", " + cur.getPlayer().getName());
                    numTies1++;
                }
                
                cur = cur.getNext();
                
            }
            System.out.println();
        }

        if(second!=Double.POSITIVE_INFINITY && numTies1 < 2) //print second place only if it exists
        {
            System.out.printf("%d\t%s", (int)second, hold2.getPlayer().getName());
            cur = head;
            while(cur!= null) //find second place ties
            {
                if(cur.getPlayer().getStrikeouts() == second && cur!= hold2) //tie for second place found
                {
                    System.out.print(", " + cur.getPlayer().getName());
                    numTies2++;
                }
                
                cur = cur.getNext();
                
            }
            System.out.println();
        }

        if(third!=Double.POSITIVE_INFINITY && numTies1 == 0 && numTies2 == 0) //print third place only if it exists
        {
            System.out.printf("%d\t%s", (int)third, hold3.getPlayer().getName());
            cur = head;
            while(cur!= null) //find third place ties
            {
                if(cur.getPlayer().getStrikeouts() == third && cur!= hold3) //tie for third place found
                {
                    System.out.print(", " + cur.getPlayer().getName());
                    numTies3++;
                }
                
                cur = cur.getNext();
                
            }
            System.out.println();
        }

        //reset variables and pointers
        first = second = third = Double.NEGATIVE_INFINITY;
        cur = hold1 = hold2= hold3= head;
        
        System.out.println("\nHIT BY PITCH");
        while(cur != null)
        {
            if(first < cur.getPlayer().getHitByPitches()) //if larger than first element
            {
                third = second;
                hold3 = hold2;

                second = first;
                hold2 = hold1;

                first = cur.getPlayer().getHitByPitches();
                hold1 = cur;
            }
            
            else if(second < cur.getPlayer().getHitByPitches() && cur.getPlayer().getHitByPitches()!= first) //if between first and second
            {
                third = second;
                hold3 = hold2;

                second = cur.getPlayer().getHitByPitches();
                hold2 = cur;
            }
            else if (cur.getPlayer().getHitByPitches() > third && cur.getPlayer().getHitByPitches() != second) //if between second and third
            {
                third = cur.getPlayer().getHitByPitches();
                hold3 = cur;
            }
            
            cur = cur.getNext();
        }
        //we now have first, second, third place
        numTies1 = numTies2 =  numTies3 = 0;
        //loop through for ties, using hold pointers to identify
        if(first!=Double.NEGATIVE_INFINITY) //print first place only if it exists
        {
            System.out.printf("%d\t%s", (int)first, hold1.getPlayer().getName());
            cur = head;
            while(cur!= null) //find first place ties
            {
                if(cur.getPlayer().getHitByPitches() == first && cur!= hold1) //tie for first place found
                {
                    System.out.print(", " + cur.getPlayer().getName());
                    numTies1++;
                }
                
                cur = cur.getNext();
                
            }
            System.out.println();
        }

        if(second!=Double.NEGATIVE_INFINITY && numTies1 < 2) //print second place only if it exists
        {
            System.out.printf("%d\t%s", (int)second, hold2.getPlayer().getName());
            cur = head;
            while(cur!= null) //find second place ties
            {
                if(cur.getPlayer().getHitByPitches() == second && cur!= hold2) //tie for second place found
                {
                    System.out.print(", " + cur.getPlayer().getName());
                    numTies2++;
                }
                
                cur = cur.getNext();
                
            }
            System.out.println();
        }

        if(third!=Double.NEGATIVE_INFINITY && numTies1 == 0 && numTies2 == 0) //print third place only if it exists
        {
            System.out.printf("%d\t%s", (int)third, hold3.getPlayer().getName());
            cur = head;
            while(cur!= null) //find third place ties
            {
                if(cur.getPlayer().getHitByPitches() == third && cur!= hold3) //tie for third place found
                {
                    System.out.print(", " + cur.getPlayer().getName());
                    numTies3++;
                }
                
                cur = cur.getNext();
                
            }
            System.out.println();
        }
        System.out.println();

        //reset variables and pointers
        first = second = third = Double.NEGATIVE_INFINITY;
        cur = hold1 = hold2= hold3= head;

    }
}
