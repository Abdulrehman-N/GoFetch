
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class GoFetchRegistered
{
    // These variables are used to generate user account and driver ids
    private static int firstUserAccountID = 900;
    private static int firstDriverId = 700;

    // Generate a new user account id
    public static String generateUserAccountId(ArrayList<User> current)
    {
        return "" + firstUserAccountID + current.size();
    }

    // Generate a new driver id
    public static String generateDriverId(ArrayList<Driver> current)
    {
        return "" + firstDriverId + current.size();
    }

    // Database of Preregistered users
    // Users loaded from a file
    // The test scripts and test outputs included with the skeleton code use these
    // users and drivers below. You may want to work with these to test your code (i.e. check your output with the
    // sample output provided). 
    public static ArrayList<User> loadPreregisteredUsers(String file) throws IOException
    {
        //Reads in all the user information
        ArrayList<User> userLists = new ArrayList<>();
        Scanner scan = new Scanner(new File(file));
        while(scan.hasNextLine()){
            String name = scan.nextLine();
            String address1 = scan.nextLine();
            double wallet = Double.parseDouble(scan.nextLine());
            userLists.add(new User(generateUserAccountId(userLists),name,address1,wallet));
        }
        scan.close();
        return userLists;
    }

    // Database of Preregistered users
    // In Assignment 2 these will be loaded from a file
    public static ArrayList<Driver> loadPreregisteredDrivers(String file) throws IOException 
    {
        //Reads in driver information
        ArrayList<Driver> driverLists = new ArrayList<>();
        Scanner scan = new Scanner(new File(file));
        while(scan.hasNextLine()){
            String name = scan.nextLine();
            String model = scan.nextLine();
            String license = scan.nextLine();
            String address1 = scan.nextLine();
            driverLists.add(new Driver(generateDriverId(driverLists),name,model,license,address1));
        }
        scan.close();
        return driverLists;
    }
}

