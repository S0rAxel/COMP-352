package CleverSIDC;

import java.util.Hashtable;
import java.util.Random;

public class Main
{
    public static void main(String[] args) 
    {
        System.out.println("Hello World!\n");

        CleverHashTable testHashTable0 = new CleverHashTable(643);
        CleverHashTable testHashTable1 = new CleverHashTable(2445);
        CleverHashTable testHashTable2 = new CleverHashTable(76642);
        CleverHashTable testHashTable3 = new CleverHashTable(526153);

        testHashTable3.add(87654321, new Student());
        testHashTable3.add(87654323, new Student());
        testHashTable3.add(87654322, new Student());
        
        testHashTable3.printInfo();
        
        testHashTable3.add(24352340, new Student());
        testHashTable3.add(24352430, new Student());
        testHashTable3.add(24352034, new Student());
        
        testHashTable3.add(24352341, new Student());
        
        
        testHashTable3.printInfo();
        
        testHashTable3.getValue(87654322);
        testHashTable3.getValue(24352430);
        
        testHashTable3.getValues(24352430);
        
        
        testHashTable3.remove(87654323);
        testHashTable3.getValue(87654323);
        testHashTable3.printInfo();
        
        
        testHashTable3.remove(24352340);
        testHashTable3.getValue(24352340);
        testHashTable3.getValue(24352430);
        testHashTable3.getValues(24352340);
        
        testHashTable3.printInfo();
    }

    // Creates the a new random 8 digit number that does not exist in the specified hashTable 
    public static int generate(CleverHashTable hashTable) 
    {
        int randomID = 40000000;
        Random random = new Random();
        randomID += random.nextInt(10000000);
  
        do
        {
            if (hashTable.getValue(randomID) == null)
            {
                break;
            }   
            else
            {
                randomID = 40000000 + random.nextInt(10000000);
            }
            
        } while(true);
        
        return randomID;
    }
}