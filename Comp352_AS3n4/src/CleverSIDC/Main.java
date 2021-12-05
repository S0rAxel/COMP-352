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
}