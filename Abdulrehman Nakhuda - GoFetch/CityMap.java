
import java.util.Arrays;
import java.util.Scanner;

// The city consists of a grid of 9 X 9 City Blocks

// Streets are east-west (1st street to 9th street)
// Avenues are north-south (1st avenue to 9th avenue)

// Example 1 of Interpreting an address:  "34 4th Street"
// A valid address *always* has 3 parts.
// Part 1: Street/Avenue residence numbers are always 2 digits (e.g. 34).
// Part 2: Must be 'n'th or 1st or 2nd or 3rd (e.g. where n => 1...9)
// Part 3: Must be "Street" or "Avenue" (case insensitive)

// Use the first digit of the residence number (e.g. 3 of the number 34) to determine the avenue.
// For distance calculation you need to identify the the specific city block - in this example 
// it is city block (3, 4) (3rd avenue and 4th street)

// Example 2 of Interpreting an address:  "51 7th Avenue"
// Use the first digit of the residence number (i.e. 5 of the number 51) to determine street.
// For distance calculation you need to identify the the specific city block - 
// in this example it is city block (7, 5) (7th avenue and 5th street)
//
// Distance in city blocks between (3, 4) and (7, 5) is then == 5 city blocks
// i.e. (7 - 3) + (5 - 4) 

public class CityMap
{
  
  private static boolean allDigits(String s)
  {
    for (int i = 0; i < s.length(); i++)
      if (!Character.isDigit(s.charAt(i)))
        return false;
    return  true;
  }

  // Get all parts of address string
  
  private static String[] getParts(String address)
  {
    String parts[] = new String[3];
    
    if (address == null || address.length() == 0)
    {
      parts = new String[0];
      return parts;
    }
    int numParts = 0;
    Scanner sc = new Scanner(address);
    while (sc.hasNext())
    {
      if (numParts >= 3)
        parts = Arrays.copyOf(parts, parts.length+1);

      parts[numParts] = sc.next();
      numParts++;
    }
    if (numParts == 1)
      parts = Arrays.copyOf(parts, 1);
    else if (numParts == 2)
      parts = Arrays.copyOf(parts, 2);
    return parts;
  }

  // Checks for a valid address
  public static boolean validAddress(String address)
  {
    
    if (address==null){
      return false;
    }
    String [] p = getParts(address);// Turns the address given into 3 parts so its easier to check
    //Returns a boolean value that results from all possivle valid conditions
    return (p.length==3 && p[0].length()==2 && allDigits(p[0]) && Integer.parseInt(Character.toString(p[1].charAt(0)))>0 && Integer.parseInt(Character.toString(p[1].charAt(0)))<10 && ((Integer.parseInt(Character.toString(p[1].charAt(0)))==1 && p[1].substring(1,3).equalsIgnoreCase("st")) || (Integer.parseInt(Character.toString(p[1].charAt(0)))==2 && p[1].substring(1,3).equalsIgnoreCase("nd") || (Integer.parseInt(Character.toString(p[1].charAt(0)))==3 && p[1].substring(1,3).equalsIgnoreCase("rd"))|| (Integer.parseInt(Character.toString(p[1].charAt(0)))>3 && p[1].substring(1,3).equalsIgnoreCase("th"))))&& p[1].length()==3 && (p[2].equalsIgnoreCase("street")||p[2].equalsIgnoreCase("avenue")));
  }

  // Computes the city block coordinates from an address string
  // returns an int array of size 2. e.g. [3, 4] 
  // where 3 is the avenue and 4 the street
  // See comments at the top for a more detailed explanation
  public static int[] getCityBlock(String address) {
    int[] block = new int[2];//creates an empty array to hold cords

   
    if (validAddress(address)) {
        String[] p = getParts(address);
        if (p[2].equalsIgnoreCase("Street")) {//correctly deciphers information if the address ends in "street"
            block[0] = Integer.parseInt(Character.toString(p[0].charAt(0)));
            block[1] = Integer.parseInt(p[1].substring(0, p[1].length() - 2));
        } else if (p[2].equalsIgnoreCase("Avenue")) {//correctly deciphers information if the address ends in "avenue"
            block[0] = Integer.parseInt(p[1].substring(0, p[1].length() - 2));
            block[1] = Integer.parseInt(Character.toString(p[0].charAt(0)));
        }
        return block;
    }
    return null;
}

  
  // Calculates the distance in city blocks between the 'from' address and 'to' address
  
  public static int getDistance(String from, String to)
  {
    
    if (getCityBlock(from)==null || getCityBlock(to)==null){ //Invalid condtions
      return -1;
    }
    int a []=getCityBlock(from);
    int b []=getCityBlock(to);
    int distance=Math.abs(a[0]-b[0])+Math.abs(a[1]-b[1]);//Calculates the distance
    return distance;

  }
  public static void main(String[] args) {
    System.out.println();
  }
  
  public static int getCityZone(String address){
    int[] cords = getCityBlock(address);
    if((cords[0]>=1 && cords[0]<=5)&&(cords[1]>=6 && cords[1]<=9)){
      return 0;
    }
    else if((cords[0]>=6 && cords[0]<=9)&&(cords[1]>=6 && cords[1]<=9)){
      return 1;
    }
    else if((cords[0]>=6 && cords[0]<=9)&&(cords[1]>=1 && cords[1]<=5)){
      return 2;
    }
    else if((cords[0]>=1 && cords[0]<=5)&&(cords[1]>=1 && cords[1]<=5)){
      return 3;
    }
    return -1;
  }
}
