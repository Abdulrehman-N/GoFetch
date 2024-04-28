
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Queue;
import java.util.TreeMap;


/*
 * 
 * This class contains the main logic of the system.
 * 
 *  It keeps track of all users, drivers and service requests (RIDE or DELIVERY)
 * 
 */
public class GoFetchSystemManager
{
 //Variables used throughout the class 
  private ArrayList<User>   userList;
  private ArrayList<Driver> drivers;
  private Map<String, User> users1;

  private Queue<GoFetchService>[] serviceRequests;


  public double totalRevenue; // Total revenues accumulated via rides and deliveries
  
  // Rates per city block
  private static final double DELIVERYRATE = 1.2;
  private static final double RIDERATE = 1.5;
  // Portion of a ride/delivery cost paid to the driver
  private static final double PAYRATE = 0.1;

  //These variables are used to generate user account and driver ids
  int userAccountId = 900;
  int driverId = 700;

  public GoFetchSystemManager()
  {
    userList = new ArrayList<User>();
    users1 = new TreeMap<>();
    drivers = new ArrayList<Driver>();
    serviceRequests = (Queue<GoFetchService>[]) new Queue[4];
    for (int i = 0; i < 4; i++) {
      serviceRequests[i] = new LinkedList<>();
  }    
    totalRevenue = 0;
  }

  // General string variable used to store an error message when something is invalid 
  // (e.g. user does not exist, invalid address etc.)  
  // The methods below will set this errMsg string and then return false
  //Setter for users
  public void setUsers(ArrayList<User> userList){
    for(User u: userList){
      this.userList.add(u);
      users1.put(u.getAccountId(), u);
    }
  }
  //Getter for driver
  public ArrayList<Driver> getDrivers(){
    return drivers;
  }
  //Setter for drivers
  public void setDrivers(ArrayList<Driver> driverslist){
    for(Driver o : driverslist){
      drivers.add(o);
    }
  }
  //getter for users
  public Map<String, User> getUsers(){
    return users1;
  }
  
  // Given user account id, find user in list of users
  // Return null if not found
  public User getUser(String accountId)
  {
    
    //Loops through users using an enhanced for loop and checks accountId
    for(User o : users1.values()){
      if(o.getAccountId().equals(accountId)){
        return o;
      }
    }
    return null;
  }
  
  // Check for duplicate user
  private void userExists(User user)
  {
    
    //Loops through users and checks if it exists
    for(User o : users1.values()){
      if(o.equals(user)){
        throw new UserExistsException();
      }
    }
  }
  
 // Check for duplicate driver
 private void driverExists(Driver driver) 
 {
   //   the code
   //Loops through drivers to see if such driver exists
   for(Driver o : drivers){
    if(o.equals(driver)){
      throw new DriverExistsException();
    }
  }
 }
  
  // Given a user, check if user ride/delivery request already exists in service requests
  private void existingRequest(GoFetchService req, int zone)
  {
    //  
    //Loops through servicerequests to see if such request exists
    for(GoFetchService o : serviceRequests[zone]){
      if(o.equals(req)){
        throw new InvalidReqException();
      }
    }
  }

  // Calculate the cost of a ride or of a delivery based on distance 
  private double getDeliveryCost(int distance)
  {
    return distance * DELIVERYRATE;
  }

  private double getRideCost(int distance)
  {
    return distance * RIDERATE;
  }

  // Go through all drivers and see if one is available
  // Choose the first available driver
  // Return null if no available driver
  private Driver getAvailableDriver()
  {
    //  
    //Loops through drivers to check if one is available
    for(Driver o : drivers){
      if(o.getStatus()==Driver.Status.AVAILABLE){
        return o;
      }
    }
    return null;
  }

  //Formatted printing for users
  public void listAllUsers()
  {
    System.out.println();
    int count = 0;
    for (User u : userList) {
      count++;
      System.out.printf("%-2s. ", count);
      u.printInfo();
      System.out.println(); 

    }
  }

 
  // Print Information (printInfo()) about all registered drivers in the system
  public void listAllDrivers()
  {
    //  
    System.out.println();
    
    for (int i = 0; i < drivers.size(); i++)
    {
      System.out.println();
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      drivers.get(i).printInfo();
      System.out.println(); 
    }
  }

  // Print Information (printInfo()) about all current service requests
  public void listAllServiceRequests()
  {
    //  
    for (int i=0;i<serviceRequests.length;i++)
    {
      System.out.println();
      System.out.println("ZONE "+ i);
      System.out.print("======\n");
      int t=1;
      for(GoFetchService j : serviceRequests[i]){
        System.out.print("\n"); 
        System.out.print(t+". ------------------------------------------------------------");
        j.printInfo();
        System.out.println(); 
        t++;
      }
      
    }
  }

  // Add a new user to the system
  public void registerNewUser(String name, String address, double wallet) 
  {
    
    if(name==null || name.length()<=0){
      throw new InvalidUsernameException();
    }
    else if(!CityMap.validAddress(address)){
      throw new InvalidUAddressException();
    }
    else if(wallet<0){
      throw new InsufficentFundsException();
    }
    //creates a user
    User user1 =new User(GoFetchRegistered.generateUserAccountId(userList), name, address, wallet);
    try {userExists(user1);}//Check if they already exist
    catch(UserExistsException e){
      throw new UserExistsException();
    }
    userList.add(user1);
    users1.put(user1.getAccountId(),user1);//Adds user to users
  }

  // Add a new driver to the system
  public void registerNewDriver(String name, String carModel, String carLicencePlate,String address) 
  {
    
    if(name==null || name.length()<=0){
      throw new InvalidDrivernameException();
    }
    else if(carModel==null || carModel.length()<=0){
      throw new InvalidCarException();
    }
    else if(carLicencePlate==null || carLicencePlate.length()<=0){
      throw new InvalidCarLicenseException();
    }
    if(!CityMap.validAddress(address)){
      throw new InvalidDAddressException();
    }
    //Creates driver
    Driver driver1= new Driver(GoFetchRegistered.generateDriverId(drivers), name, carModel, carLicencePlate,address);
    try{driverExists(driver1);} catch(DriverExistsException e){//Checks if driver already exists
      throw new DriverExistsException();
    }
    drivers.add(driver1);//adds driver to drivers
  }

  // Request a ride. User wallet will be reduced when drop off happens
  public void requestRide(String accountId, String from, String to)
  {
    
    if (!CityMap.validAddress(from) || !CityMap.validAddress(to)){
      throw new InvalidAddressException();
    }
    if(getAvailableDriver()==null){
      throw new NoDriverException();
    }
    if(getUser(accountId)==null){
      throw new UserAccException();
    }
    //finds user
    User userl=getUser(accountId);
    int distance=CityMap.getDistance(from,to);//finds distance
    int czone=CityMap.getCityZone(from);
    if(distance<=1){
      throw new InvalidDistanceException();
    }
    if (distance>1){//check distance
      Driver driverl=getAvailableDriver();//finds driver
      double cost1=getRideCost(distance);//finds cost
      if(userl.getWallet()<cost1){//checks funds
        throw new InsufficentFundsException();
      }
      GoFetchRide ride1= new GoFetchRide(from, to,userl,distance,cost1);//creates a ride
      try{
        existingRequest(ride1,czone);
        serviceRequests[czone].add(ride1);
        userl.addRide();//increments addride variable
      
      }
      catch(InvalidReqException e){ throw new UserRideException();}//makes sure ride doesnt already exist
        
      
    }
    
  }

  // Request a food delivery. User wallet will be reduced when drop off happens
  public void requestDelivery(String accountId, String from, String to, String restaurant, String foodOrderId){
   
    if (!CityMap.validAddress(from) || !CityMap.validAddress(to)){
      throw new InvalidAddressException();
    }
    if(getAvailableDriver()==null){
      throw new NoDriverException();
    }
    if(getUser(accountId)==null){
      throw new UserAccException();
    }
    User userl=getUser(accountId);//finds user
    int distance=CityMap.getDistance(from,to);//gets distance
    int czone=CityMap.getCityZone(from);
    if(distance<=1){
      throw new InvalidDistanceException();
    }
    if (distance>1){//checks if distance is valid
      Driver driverl=getAvailableDriver();//finds driver
      double cost1=getDeliveryCost(distance);//gets cost
      if(userl.getWallet()<cost1){//checks funds
        throw new InsufficentFundsException();
      }
      GoFetchDelivery delivery1= new GoFetchDelivery(from, to, userl, distance, cost1, restaurant, foodOrderId);//creates a delivery
      try{existingRequest(delivery1,czone);
        serviceRequests[czone].add(delivery1);
        userl.addDelivery();}//increments number of delivers for user}
      catch(InvalidReqException e){//checks if delivery already exists
        throw new DelExistsException();
      }
    }
  }


  // Cancel an existing service request. 
  // parameter int request is the index in the serviceRequests array list
  public void cancelServiceRequest(int zone,int request) 
  {
    
    if (zone < 0 || zone >= serviceRequests.length) {
      throw new InvalidZoneException();
  }
    Queue<GoFetchService> requestQueue = serviceRequests[zone];
    if (request < 0 || request > requestQueue.size()) {
      System.out.println(requestQueue.size());
        throw new InvalidReqException();
    }
    request-=1;//decrements since ui is from 1, onwards but indexing starts at 0
    Queue<GoFetchService> temp = new LinkedList<>();

    int count = 0;
    boolean foundRequest = false;

    // Loop through the array of services
    for (GoFetchService service : requestQueue) {
      if (count != request) {
        temp.add(service); // Add service to temp queue if it's not the specified index
      } 
      else {
        // If this is the service to be removed, perform necessary actions
        foundRequest = true;
        if (service.getServiceType().equals("RIDE")) {
          service.getUser().removeRide(); // Remove ride associated with the service
        } 
        else if (service.getServiceType().equals("DELIVERY")) {
          service.getUser().removeDelivery(); // Remove delivery associated with the service
        }
      }
      count++;
    }
    // If the requested service was not found in the queue, throw an exception
    if (!foundRequest) {
      throw new InvalidReqException();
    }

    // Clear the original requestQueue and add all services from temp queue back to it
    requestQueue.clear();
    requestQueue.addAll(temp);
  }
  
  
  // Drop off a ride or a delivery. This completes a service.
  // parameter request is the index in the serviceRequests array list
  //getter for drivers
  public Driver getDriver(String driverid1){
    for(Driver o : drivers){
      if(o.getId().equals(driverid1)){
        return o;
      }
    }
    return null;
  }
  public void dropOff(String driverid)
  {
    
    Driver d = getDriver(driverid);
    if(d == null){
      throw new InvalidDriverIDException();
    }
    if(d.getStatus() != Driver.Status.DRIVING){
      throw new DriverNotDriveException();
    }
    GoFetchService serv = d.getService();
    if(serv == null){
      throw new ServiceNotExistsException();
    }
      double paid = serv.getCost();
      d.pay(PAYRATE*paid);
      totalRevenue+=paid-(d.getWallet());
      serv.getUser().payForService(paid);
      d.setService(null);
      d.setStatus(Driver.Status.AVAILABLE);
      d.setAddress(d.getTo());
      
  }
  public void driveTo(String driverId, String address) {
    Driver driver = getDriver(driverId);
    if(driver==null){
      throw new InvalidDriverIDException();
    }
    if(driver.getStatus() != Driver.Status.AVAILABLE){
      throw new DriverNotDriveException();
    }
      // Validate the new address
      if (!CityMap.validAddress(address)) {
          throw new InvalidDAddressException();
      }
      driver.setAddress(address);
    }
//Pickup method to pickup user's requests
//Works by finding the driving and changeing service, status and address(also zone)
  public void pickup(String driverId)
  {
    Driver d = getDriver(driverId);
    if(d == null){
      throw new InvalidDriverIDException();
    }
    if(serviceRequests[d.getZone()].peek() == null){
      throw new InvalidZoneException(d.getZone());
    }
    d.setService(serviceRequests[d.getZone()].remove());
    d.setStatus(Driver.Status.DRIVING);
    d.setAddress(d.getService().getFrom());
    d.setTo(d.getService().getTo());
  }
  // Sort users by name
  // Then list all users
  public void sortByUserName()
  {
    if (users1 == null || users1.isEmpty()) {
      throw new UserAccException();
  }
  Collections.sort(userList, new NameComparator());
  listAllUsers();
  }

  // Helper class for method sortByUserName
  private class NameComparator implements Comparator<User>
  {
    public int compare(User a, User b){
      return a.getName().compareTo(b.getName());//helper methods for name
    }
  }

  // Sort users by number amount in wallet
  // Then ist all users
  public void sortByWallet()
  {
    if (users1 == null || users1.isEmpty()) {
      throw new UserAccException();
  }
    Collections.sort(userList, new UserWalletComparator());
    listAllUsers();
  }
  // Helper class for use by sortByWallet
  private class UserWalletComparator implements Comparator<User>
  {
    public int compare(User a, User b){
      return (int) (a.getWallet() - b.getWallet());//helper method for wallet
    }
  }

  // Sort trips (rides or deliveries) by distance
  // Then list all current service requests
  public void sortByDistance()
  {
    for (Queue<GoFetchService> queue : serviceRequests) {
      
      Collections.sort((LinkedList<GoFetchService>) queue);
    }//sorts by distance
    listAllServiceRequests();
  }
 
}
// Custom exception class
class InvalidUsernameException extends RuntimeException {
  // Constructor with a custom error message  
  public InvalidUsernameException() {
      super("Invalid User Name");;
  }
}
class InvalidUAddressException extends RuntimeException {
  // Constructor with a custom error message 
  public InvalidUAddressException() {
      super("Invalid User Address");;
  }
}
class InvalidDAddressException extends RuntimeException {
  // Constructor with a custom error message  
  public InvalidDAddressException() {
      super("Invalid Driver Address");;
  }
}
class InvalidMoneyException extends RuntimeException {
  // Constructor with a custom error message  
  public InvalidMoneyException() {
      super("Invalid Money in Wallet");;
  }
}
class UserExistsException extends RuntimeException {
  // Constructor with a custom error message  
  public UserExistsException() {
      super("User Already Exists in System");
  }
}
class InvalidDrivernameException extends RuntimeException {
  // Constructor with a custom error message  
  public InvalidDrivernameException() {
      super("Invalid Driver Name");
  }
}
class InvalidCarException extends RuntimeException {
  // Constructor with a custom error message  
  public InvalidCarException() {
      super("Invalid Car Model");
  }
}
class InvalidCarLicenseException extends RuntimeException {
  // Constructor with a custom error message  
  public InvalidCarLicenseException() {
      super("Invalid Car License Plate ");
  }
}
class DriverExistsException extends RuntimeException {
  // Constructor with a custom error message  
  public DriverExistsException() {
      super("Driver Already Exists in System");
  }
}
class UserAccException extends RuntimeException {
  // Constructor with a custom error message  
  public UserAccException() {
      super("User Account Not Found");
  }
}
class UserRideException extends RuntimeException {
  // Constructor with a custom error message  
  public UserRideException() {
      super("User Already Has Ride Reqeust");
  }
}
class InvalidDistanceException extends RuntimeException {
  // Constructor with a custom error message  
  public InvalidDistanceException() {
      super("Insufficient Travel Distance");
  }
}
class InvalidAddressException extends RuntimeException {
  // Constructor with a custom error message  
  public InvalidAddressException() {
      super("Invalid Address");
  }
}
class InsufficentFundsException extends RuntimeException {
  // Constructor with a custom error message  
  public InsufficentFundsException() {
      super("Insufficient Funds");
  }
}
class NoDriverException extends RuntimeException {
  // Constructor with a custom error message  
  public NoDriverException() {
      super("No Drivers Available");
  }
}
class DelExistsException extends RuntimeException {
  // Constructor with a custom error message  
  public DelExistsException() {
      super("User Already Has Delivery Request at Restaurant with this Food Order");
  }
}
class InvalidReqException extends RuntimeException {
  // Constructor with a custom error message  
  public InvalidReqException() {
      super("Invalid Request #");
  }
}
class InvalidZoneException extends RuntimeException {
  // Constructor with a custom error message  
  public InvalidZoneException() {
      super("Invalid Zone");
  }
  public InvalidZoneException(int zone) {
      super("No Service Requests in Zone " + zone);
  }
}
class InvalidDriverIDException extends RuntimeException {
  // Constructor with a custom error message  
  public InvalidDriverIDException() {
      super("Invalid DriverID");
  }
}
class DriverNotDriveException extends RuntimeException {
  // Constructor with a custom error message  
  public DriverNotDriveException() {
      super("Driver is Not Driving");
  }
}
class ServiceNotExistsException extends RuntimeException {
  // Constructor with a custom error message  
  public ServiceNotExistsException() {
      super("Service Not Found");
  }
}

