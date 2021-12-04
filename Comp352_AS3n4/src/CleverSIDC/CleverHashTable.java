package CleverSIDC;

class CleverHashTable 
{
	int expectedSize; // The total number of elements the table expects to receive.
	int ceiledSize; // The above number rounded up to the nearest thousand/10,000/100,000, as needed.
	
	int mappedDigits; // The number of digits onto which to remap the SIDC during hashing. 
	
	Key[] table; // The base array's length will be determined by the tableSize variable.
	
	int load; // The number of elements currently in the table.
	
	Key first; // The first/leftmost key in the table.
	Key last; // The last/rightmost key in the table.
	
	
	
	
	
	public CleverHashTable(int tableSize)
	{
		setSIDCThreshold(tableSize);
		
		System.out.println(String.format("\n\nTotal size of table: %d.\n", ceiledSize));
		
		
		hash(99999999);
		hash(11111111);
		hash(55555555);
		hash(55555556);
		hash(55555565);
		hash(55555655);
	}
	
	
	
	void setSIDCThreshold(int size)
	{
		this.expectedSize = size;
		if (size < 9000) // Hashes of four digits long.
		{
			ceiledSize = (int) (Math.ceil((size+1000)/1000) * 1000);		
			table = new Key[ceiledSize]; // Start with a table of 1,000+ elements.
			
			mappedDigits = 2;
			return;
		}
		if (size < 90000) // Hashes of five digits long.
		{
			ceiledSize = (int) (Math.ceil((size+10000)/10000) * 10000);	
			table = new Key[ceiledSize]; // Start with a table of 10,000+ elements.
			
			mappedDigits = 3;
			return;
		}
		else
		{
			ceiledSize = (int) (Math.ceil((size+100000)/100000) * 100000);	
			table = new Key[100000]; // Start with a table of 100,000+ elements.
			
			mappedDigits = 4;
		}
	}
	
	
	
	
	void add(int key, Student value)
	{
		
	}
	
	

	
	int hash(int SIDC)
	{
		String SIDCAsString = String.valueOf(SIDC);
		
		System.out.println(String.format("SIDC: %d", SIDC));
		
		// Step 1: remap the SIDC down to a range determined by the array's size.
		int base = (int) Math.pow(10, mappedDigits-1);
		int newMax = base * Integer.parseInt(String.valueOf(ceiledSize).substring(0, 1));
		
		System.out.println(String.format("Using %d mapped digits.", mappedDigits));
		System.out.println(String.format("Base: %d", base));
		System.out.println(String.format("New max: %d", newMax));
		
		double ratio = Double.valueOf(newMax)/99999999.0;
		int remapped = (int) (ratio * SIDC);
		
		System.out.println(String.format("Ratio: %f", ratio));
		System.out.println(String.format("Remapped & rounded number: %d", remapped));
		
		
		// Step 2: compute the sum of all digits in the SIDC.
		if ((remapped * 100 + 72) < ceiledSize) // Only perform if the result would remain within range.
		{
			int SIDCSum = 0;
			for (char digit : SIDCAsString.toCharArray())
			{
				// The maximum value possible is 8 * 9 = 72.
				SIDCSum += Character.getNumericValue(digit);
			}
			System.out.println(String.format("Sum of all digits in SIDC: %d", SIDCSum));
			
			// Append and return the two computed values.
			System.out.println(String.format("Final hash: %d\n", (remapped * 100 + SIDCSum - 1)));
			return remapped * 100 + SIDCSum -1;
		}
		
		// Else, return the remapped value only.
		System.out.println(String.format("Final hash: %d\n", (remapped * 100 - 1)));
		return remapped * 100 -1;
	}
	

}
