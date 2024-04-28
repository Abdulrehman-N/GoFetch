
/*
 * 
 * This class simulates a food delivery service for a simple Uber app
 * 
 * A GoFetchDelivery is-a GoFetchService with some extra functionality
 */
public class GoFetchDelivery extends GoFetchService
{
  public static final String TYPENAME = "DELIVERY";
 
  private String restaurant; 
  private String foodOrderId;
   
   // Constructor to initialize all inherited and new instance variables 
  public GoFetchDelivery(String from, String to, User user, int distance, double cost,
                        String restaurant, String order)
  {
  
    super(from, to, user, distance, cost, TYPENAME);//calls super
    this.restaurant=restaurant;
    this.foodOrderId=order;
  }
 
  //setters and getters
  public String getServiceType()
  {
    return TYPENAME;
  }
  public String getRestaurant()
  {
    return restaurant;
  }
  public void setRestaurant(String restaurant)
  {
    this.restaurant = restaurant;
  }
  public String getFoodOrderId()
  {
    return foodOrderId;
  }
  public void setFoodOrderId(String foodOrderId)
  {
    this.foodOrderId = foodOrderId;
  }
  /*
   * Two Delivery Requests are equal if they are equal in terms of GoFetchServiceRequest
   * and the restaurant and food order id are the same  
   */
  public boolean equals(Object other)
  {
    
    if (this == other) {
      return true;
    }
    if (!(other instanceof GoFetchDelivery)) {
      return false;
    }
    GoFetchDelivery o = (GoFetchDelivery) other;//casting
    return super.equals(o) && this.restaurant.equals(o.restaurant) && this.foodOrderId.equals(o.foodOrderId);//returns corresponding boolean
  }
  /*
   * Print Information about a Delivery Request
   */
  public void printInfo()
  {
   
    super.printInfo();
  
    System.out.printf("\nRestaurant: %-9s Food Order #: %-3s", restaurant, foodOrderId); 
  }
}
