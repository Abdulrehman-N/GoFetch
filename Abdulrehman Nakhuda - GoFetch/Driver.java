
/*
 * 
 * This class simulates a car driver in a simple uber app 
 */
public class Driver
{
  private String id;
  private String name;
  private String carModel;
  private String licensePlate;
  private double wallet;
  private String type;
  private GoFetchService service;
  private String address;
  private int zone;
  private String to;
  
  public static enum Status {AVAILABLE, DRIVING};
  private Status status;
    
  
  public Driver(String id, String name, String carModel, String licensePlate, String address)
  {
    this.id = id;
    this.name = name;
    this.carModel = carModel;
    this.licensePlate = licensePlate;
    this.status = Status.AVAILABLE;
    this.wallet = 0;
    this.type = "";
    this.service=null;
    this.address=address;
    this.zone=CityMap.getCityZone(address);
  }
  public GoFetchService getService() {
    return service;
  }

public void setService(GoFetchService service) {
    this.service = service;
  }
  // Print Information about a driver
  public void printInfo()
  {
    //If the user is driving, show the from and to destination of the service
    if(this.getStatus().equals(Driver.Status.DRIVING)){
      System.out.printf("Id: %-3s Name: %-15s Car Model: %-15s License Plate: %-10s Wallet: %2.2f \nStatus: %-10s Address: %-15s Zone: %d \nFrom: %-15s To: %-15s", 
        id, name, carModel, licensePlate, wallet, status, address, getZone(), address, to);
    }
    else{
      System.out.printf("Id: %-3s Name: %-15s Car Model: %-15s License Plate: %-10s Wallet: %2.2f \nStatus: %-10s Address: %-15s Zone: %d" , 
          id, name, carModel, licensePlate, wallet, status, address, getZone());
    }
  }
  // Getters and Setters
  public void setTo(String to2){
    this.to=to2;
  }
  public String getTo(){
    return this.to;
  }
  public String getType()
  {
    return type;
  }
  public void setType(String type)
  {
    this.type = type;
  }
  public String getId()
  {
    return id;
  }
  public void setId(String id)
  {
    this.id = id;
  }
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  public String getCarModel()
  {
    return carModel;
  }
  public void setCarModel(String carModel)
  {
    this.carModel = carModel;
  }
  public String getLicensePlate()
  {
    return licensePlate;
  }
  public void setLicensePlate(String licensePlate)
  {
    this.licensePlate = licensePlate;
  }
  public Status getStatus()
  {
    return status;
  }
  public void setStatus(Status status)
  {
    this.status = status;
  }
  public double getWallet()
  {
    return wallet;
  }
  public void setWallet(double wallet)
  {
    this.wallet = wallet;
  }
  /*
   * Two drivers are equal if they have the same name and license plates.
   * This method is overriding the inherited method in superclass Object
   * 
   *
   */
  public boolean equals(Object other)
  {
    if(other instanceof Driver && other !=null){
      Driver o = (Driver) other;//Casting 
      return (this.name.equals(o.getName()) && this.licensePlate.equals(o.getLicensePlate()));//Returns corresponding boolean
    }
    return false;
  }
  
  // A driver earns a fee for every ride or delivery
  public void pay(double fee)
  {
    wallet += fee;
  }
  //More getters and setters
  public int getZone(){
    return this.zone;
  }
  public String getAddress(){
    return this.address;
  }
  public void setAddress(String in){
    this.address=in;
    this.zone=CityMap.getCityZone(in);
  }
  
}
