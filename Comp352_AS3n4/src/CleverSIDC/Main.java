package CleverSIDC;

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
}