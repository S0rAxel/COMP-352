package CleverSIDC;

import java.util.Random;

public class Main
{
    public static void main(String[] args) 
    {
        System.out.println("Hello World!\n");
        
        testKeyInsertionNDeletion();
        
        //testNextKeySmallTable();
        
        
        CleverHashTable testHashTable = new CleverHashTable(2445);
 
        CleverHashTable testHashTable1 = new CleverHashTable(2445);
        CleverHashTable testHashTable2 = new CleverHashTable(76642);

        //testHashTable.add(44352340, new Student());
        //testHashTable.add(44352378, new Student());
        //testHashTable.add(44352363, new Student());
        //testHashTable.add(44352347, new Student());
        //testHashTable.add(44352657, new Student());

        

        //System.out.println("Range values with same values: " + testHashTable.rangeKey(44352340, 44352340) + "\n");
        //System.out.println("Range values with wrong values: " + testHashTable.rangeKey(12345678, 23459456) + "\n");
        //System.out.println("\nRange values with correct values: " + testHashTable.rangeKey(44352340, 44352341) + "\n");

    }
    
    static void testNextKeySmallTable()
    {
    	CleverHashTable testHashTable0 = new CleverHashTable(643);
    	
    	
    	testHashTable0.add(12345678, new Student());
    	testHashTable0.add(generate(testHashTable0), new Student());
    	testHashTable0.add(generate(testHashTable0), new Student());
    	testHashTable0.add(generate(testHashTable0), new Student());
    	testHashTable0.add(87654321, new Student());
      
    	testHashTable0.add(generate(testHashTable0), new Student());
    	testHashTable0.add(generate(testHashTable0), new Student());
    	testHashTable0.add(generate(testHashTable0), new Student());
    	testHashTable0.add(generate(testHashTable0), new Student());
    	testHashTable0.add(generate(testHashTable0), new Student());
    	
    	testHashTable0.add(12345675, new Student());
    	testHashTable0.add(12345674, new Student());
    	testHashTable0.add(12345673, new Student());
    	testHashTable0.add(12345672, new Student());
    	testHashTable0.add(12345671, new Student());
    	
    	testHashTable0.add(23456000, new Student());
    	testHashTable0.add(12345001, new Student());
    	testHashTable0.add(12345002, new Student());
    	testHashTable0.add(12345003, new Student());
    	testHashTable0.add(12345030, new Student());
    	testHashTable0.add(56789000, new Student());
    	
        testHashTable0.printInfo();
        testHashTable0.printTraversals();
    }
    
    static void testKeyInsertionNDeletion()
    {
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
        testHashTable3.printTraversals();
        
        
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
        
        System.out.println("\nGenerated " + generate(testHashTable3));
        System.out.println("Generated " + generate(testHashTable3));
        System.out.println("Generated " + generate(testHashTable3));
        System.out.println("Generated " + generate(testHashTable3) + "\n");

        CleverHashTable testHashTable = new CleverHashTable(632);

        testHashTable.add(44352378, new Student());
        testHashTable.add(44352657, new Student());
        testHashTable.add(44352340, new Student());
        testHashTable.add(44352347, new Student()); 
        testHashTable.add(44352363, new Student());
        testHashTable.add(45357337, new Student());

        /*testHashTable0.add(generate(testHashTable0), new Student());
        testHashTable0.add(generate(testHashTable0), new Student());
        testHashTable0.add(generate(testHashTable0), new Student());
        testHashTable0.add(generate(testHashTable0), new Student());
        testHashTable0.add(generate(testHashTable0), new Student());
        testHashTable0.add(generate(testHashTable0), new Student());
        testHashTable0.add(generate(testHashTable0), new Student());*/

        System.out.println("Range values with same values: " + testHashTable.rangeKey(44352340, 44352340) + "\n");
        System.out.println("Range values with wrong values: " + testHashTable.rangeKey(12345678, 23459456) + "\n");
        System.out.println("\nRange values with correct values: " + testHashTable.rangeKey(44352340, 44352347) + "\n");

        Sequence sequence = testHashTable.allKeys();
        System.out.println("\nAll Keys: ");
        for (Key key : sequence.keys) {
            System.out.println("Student: " + key.key);
        }

        testHashTable3.printTraversals();
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