
/*
 * 
 * This class simulates an ride service for a simple Uber app
 * 
 * A GoFetchRide is-a GoFetchService with some extra functionality
 */
public class GoFetchRide extends GoFetchService
{
  private int numPassengers;
  private boolean requestedXL;
  
  public static final String TYPENAME = "RIDE";
  
  // Constructor to initialize all inherited and new instance variables 
  public GoFetchRide(String from, String to, User user, int distance, double cost)
  {
    
    super(from, to, user, distance, cost,TYPENAME);//calls super
    this.numPassengers=0;
    this.requestedXL=false;
  }
  //Setter and getters
  public String getServiceType()
  {
    return TYPENAME;
  }

  public int getNumPassengers()
  {
    return numPassengers;
  }

  public void setNumPassengers(int numPassengers)
  {
    this.numPassengers = numPassengers;
  }

  public boolean isRequestedXL()
  {
    return requestedXL;
  }

  public void setRequestedXL(boolean requestedXL)
  {
    this.requestedXL = requestedXL;
  }
}
