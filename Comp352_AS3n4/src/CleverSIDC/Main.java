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

        testHashTable3.add(83502410, new Student());
        testHashTable3.printInfo();
        testHashTable3.add(83502412, new Student());
        testHashTable3.printInfo();
        testHashTable3.add(83502411, new Student());
        testHashTable3.add(13571232, new Student());
        
        testHashTable3.add(83502421, new Student());
        testHashTable3.add(83502142, new Student());
        testHashTable3.add(83502400, new Student());
       
        
        testHashTable3.printInfo();

        testHashTable3.getValue(83502410);
        testHashTable3.getValue(83502411);
        testHashTable3.getValue(83502412);
        
        testHashTable3.getValue(83502421);
        testHashTable3.getValue(83502142);
        
        testHashTable3.getValues(83502421);
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