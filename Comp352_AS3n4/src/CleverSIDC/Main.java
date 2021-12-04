package CleverSIDC;

import java.util.Hashtable;
import java.util.Random;

public class Main
{
    public static void main(String[] args) 
    {
        System.out.println("Hello World!");
        
        CleverHashTable testHashTable1 = new CleverHashTable(2445);
        CleverHashTable testHashTable2 = new CleverHashTable(76642);
        CleverHashTable testHashTable3 = new CleverHashTable(526153);
        
        CleverHashTable testHashTable4 = new CleverHashTable(9999);
        CleverHashTable testHashTable5 = new CleverHashTable(99999);
        
        CleverHashTable testHashTable6 = new CleverHashTable(785);
        testHashTable6.add(55555555, new Student());
        testHashTable6.add(55555556, new Student());
        
        testHashTable3.add(83502412, new Student());
        testHashTable3.add(83502413, new Student());
        testHashTable3.add(83502431, new Student());
        testHashTable3.add(83502341, new Student());
        testHashTable3.add(83502342, new Student());

        System.out.println(generate(testHashTable3));
        System.out.println(generate(testHashTable3));
        System.out.println(generate(testHashTable3));
        System.out.println(generate(testHashTable3));

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