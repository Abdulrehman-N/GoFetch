

import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple Command-line based Uber App 

// This system supports "ride sharing" service and a delivery service

public class GoFetchUI
{
  public static void main(String[] args)
  {
    // Create the System Manager - the main system code is in here 

    GoFetchSystemManager GoFetch = new GoFetchSystemManager();
    
    Scanner scanner = new Scanner(System.in);
    System.out.print(">");

    // Process keyboard actions
    while (scanner.hasNextLine())
    try{
      {String action = scanner.nextLine();

      if (action == null || action.equals("")) 
      {
        System.out.print("\n>");
        continue;
      }
      // Quit the App
      else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
        return;
      // Print all the registered drivers
      else if (action.equalsIgnoreCase("DRIVERS"))  // List all drivers
      {
        GoFetch.listAllDrivers(); 
      }
      // Print all the registered users
      else if (action.equalsIgnoreCase("USERS"))  // List all users
      {
        GoFetch.listAllUsers(); 
      }
      // Print all current ride requests or delivery requests
      else if (action.equalsIgnoreCase("REQUESTS"))  // List all requests
      {
        GoFetch.listAllServiceRequests(); 
      }
      //Load users
      else if (action.equalsIgnoreCase("LOADUSERS")) 
      {
        String fname = "";
        System.out.print("User File: ");
        if (scanner.hasNextLine())
        {
          fname = scanner.nextLine();
        }
        try{
          ArrayList<User>o=GoFetchRegistered.loadPreregisteredUsers(fname);
          GoFetch.setUsers(o);
          
          System.out.println("Users Loaded");
        }
        catch(FileNotFoundException e){
          System.out.println("User File: "+fname+" Not Found");
        }
      }
      //Calls the pickup method
      else if (action.equalsIgnoreCase("PICKUP")) 
      {
        String driverid = "";
        System.out.print("Driver ID: ");
        if (scanner.hasNextLine())
        {
          driverid = scanner.nextLine();
        }
        GoFetch.pickup(driverid);
        System.out.println("\nDriver "+driverid+" Picking Up in Zone "+GoFetch.getDriver(driverid).getZone());
      }
      //Calls the driveTo method
      else if (action.equalsIgnoreCase("DRIVETO")) 
      {
        String driverid = "";
        System.out.print("Driver ID: ");
        if (scanner.hasNextLine())
        {
          driverid = scanner.nextLine();
        }
        String address2 = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address2 = scanner.nextLine();
        }
        GoFetch.driveTo(driverid, address2);
        System.out.println("Driver "+driverid+" Now in Zone "+GoFetch.getDriver(driverid).getZone());
      }
      //Loads all the users into the program
      else if (action.equalsIgnoreCase("LOADDRIVERS")) 
      {
        String fname = "";
        System.out.print("Driver File: ");
        if (scanner.hasNextLine())
        {
          fname = scanner.nextLine();
        }
        try{
          ArrayList<Driver>o=GoFetchRegistered.loadPreregisteredDrivers(fname);
          GoFetch.setDrivers(o);
          System.out.println("Drivers Loaded");
        }
        catch(FileNotFoundException e){
          System.out.println("Driver File: "+fname+" Not Found");
        }
        
      }
      // Register a new driver
      else if (action.equalsIgnoreCase("REGDRIVER")) 
      {
        String name = "";
        System.out.print("Name: ");
        if (scanner.hasNextLine())
        {
          name = scanner.nextLine();
        }
        String carModel = "";
        System.out.print("Car Model: ");
        if (scanner.hasNextLine())
        {
          carModel = scanner.nextLine();
        }
        String license = "";
        System.out.print("Car License: ");
        if (scanner.hasNextLine())
        {
          license = scanner.nextLine();
        }
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
        
        GoFetch.registerNewDriver(name, carModel, license,address);
        System.out.printf("Driver: %-15s Car Model: %-15s License Plate: %-10s", name, carModel, license);
          
      }
      // Register a new user
      else if (action.equalsIgnoreCase("REGUSER")) 
      {
        String name = "";
        System.out.print("Name: ");
        if (scanner.hasNextLine())
        {
          name = scanner.nextLine();
        }
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
        double wallet = 0.0;
        System.out.print("Wallet: ");
        if (scanner.hasNextDouble())
        {
          wallet = scanner.nextDouble();
          scanner.nextLine(); // consume nl!! Only needed when mixing strings and int/double
        }
        GoFetch.registerNewUser(name, address, wallet);
        System.out.printf("User: %-15s Address: %-15s Wallet: %2.2f", name, address, wallet);
      }
      // Request a ride
      else if (action.equalsIgnoreCase("REQRIDE")) 
      {

        String userAccountId = "";
        System.out.print("User Account Id: ");
        if (scanner.hasNextLine())
        {
          userAccountId = scanner.nextLine();
        }

        String fromAddress = "";
        System.out.print("From Address: ");
        if (scanner.hasNextLine())
        {
          fromAddress = scanner.nextLine();
        }

        String toAddress = "";
        System.out.print("To Address: ");
        if (scanner.hasNextLine())
        {
          toAddress = scanner.nextLine();
        }

        GoFetch.requestRide(userAccountId, fromAddress, toAddress);
        System.out.printf("\nRIDE for: %-15s From: %-15s To: %-15s", GoFetch.getUser(userAccountId).getName(), fromAddress, toAddress);
        
        
      }
      // Request a food delivery
      else if (action.equalsIgnoreCase("REQDLVY")) 
      {

        String userAccountId = "";
        System.out.print("User Account Id: ");
        if (scanner.hasNextLine())
        {
          userAccountId = scanner.nextLine();
        }

        String fromAddress = "";
        System.out.print("From Address: ");
        if (scanner.hasNextLine())
        {
          fromAddress = scanner.nextLine();
        }
        String toAddress = "";
        System.out.print("To Address: ");
        if (scanner.hasNextLine())
        {
          toAddress = scanner.nextLine();
        }
        String restaurant = "";
        System.out.print("Restaurant: ");
        if (scanner.hasNextLine())
        {
          restaurant = scanner.nextLine();
        }

        String foodOrder = "";
        System.out.print("Food Order #: ");
        if (scanner.hasNextLine())
        {
          foodOrder = scanner.nextLine();
        }

        GoFetch.requestDelivery(userAccountId, fromAddress, toAddress, restaurant, foodOrder);
        System.out.printf("\nDELIVERY for: %-15s From: %-15s To: %-15s", GoFetch.getUser(userAccountId).getName(), fromAddress, toAddress);
      }
      // Sort users by name
      else if (action.equalsIgnoreCase("SORTBYNAME")) 
      {
        GoFetch.sortByUserName();
      }
      // Sort users by number of ride they have had
      else if (action.equalsIgnoreCase("SORTBYWALLET")) 
      {
        GoFetch.sortByWallet();
      }
      // Sort current service requests (ride or delivery) by distance
      else if (action.equalsIgnoreCase("SORTBYDIST")) 
      {
        GoFetch.sortByDistance();
      }
      // Cancel a current service (ride or delivery) request
      else if (action.equalsIgnoreCase("CANCELREQ")) 
      {
        int zone = -1;
        System.out.print("Zone #: ");
        if (scanner.hasNextInt())
        {
          zone = scanner.nextInt();
          scanner.nextLine(); // consume nl character
        }
        int request = -1;
        System.out.print("Request #: ");
        if (scanner.hasNextInt())
        {
          request = scanner.nextInt();
          scanner.nextLine(); // consume nl character
        }
        
        GoFetch.cancelServiceRequest(zone,request);
        System.out.print("Service request #" + request + " cancelled\n");
      }
      // Drop-off the user or the food delivery to the destination address
      else if (action.equalsIgnoreCase("DROPOFF")) 
      {
        String driverid="";
        System.out.print("Driver ID: ");
        if (scanner.hasNext())
        {
          driverid = scanner.nextLine();
          //scanner.nextLine(); // consume nl
        }
        GoFetch.dropOff(driverid);
        System.out.println("Driver " + driverid + " Dropping Off");
      }
      // Get the Current Total Revenues
      else if (action.equalsIgnoreCase("REVENUES")) 
      {
        System.out.println("Total Revenue: " + GoFetch.totalRevenue);
      }
      // Unit Test of Valid City Address 
      else if (action.equalsIgnoreCase("ADDR")) 
      {
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
        System.out.print(address);
        if (CityMap.validAddress(address))
          System.out.println("\nValid Address"); 
        else
          System.out.println("\nBad Address"); 
      }
      // Unit Test of CityMap Distance Method
      else if (action.equalsIgnoreCase("DIST")) 
      {
        String from = "";
        System.out.print("From: ");
        if (scanner.hasNextLine())
        {
          from = scanner.nextLine();
        }
        String to = "";
        System.out.print("To: ");
        if (scanner.hasNextLine())
        {
          to = scanner.nextLine();
        }
        if(!CityMap.validAddress(from) || !CityMap.validAddress(to)){
          System.out.println("Invalid Address");
        }else{
        System.out.print("\nFrom: " + from + " To: " + to);
        System.out.println("\nDistance: " + CityMap.getDistance(from, to) + " City Blocks");
        }
      }
      
      
    }
    System.out.print("\n>");
    }
    //Catches the custom errors and any other runtime error
    catch (RuntimeException e) {
          System.out.print("\n "+e.getMessage()+"\n\n>");
    }
    //catches IOExceptions other than FileNotFound and exits the program
    catch(IOException e){
      break;
    }

  }
}