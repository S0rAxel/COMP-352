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
		//System.out.println(String.format("Table initialized with length: %d.\n\n", table.length));
	}
	
	void setSIDCThreshold(int size)
	{
		this.expectedSize = size;
		
		if (size < 900) // Hashes of three digits long.
		{
			ceiledSize = (int) (Math.ceil((size+100)/100) * 100);	
			table = new Key[ceiledSize]; // Start with a table of 100+ elements.
			
			mappedDigits = 3;
			return;
		}
		else if (size < 9000) // Hashes of four digits long.
		{
			ceiledSize = (int) (Math.ceil((size+1000)/1000) * 1000);		
			table = new Key[ceiledSize]; // Start with a table of 1,000+ elements.
			
			mappedDigits = 2;
			return;
		}
		else if (size < 90000) // Hashes of five digits long.
		{
			ceiledSize = (int) (Math.ceil((size+10000)/10000) * 10000);	
			table = new Key[ceiledSize]; // Start with a table of 10,000+ elements.
			
			mappedDigits = 3;
			return;
		}
		else
		{
			ceiledSize = (int) (Math.ceil((size+100000)/100000) * 100000);	
			table = new Key[ceiledSize]; // Start with a table of 100,000+ elements.
			
			mappedDigits = 4;
		}
	}
	
	void add(int SIDC, Student value)
	{
		int hash = hash(SIDC);
		
		if (table[hash] == null)
		{
			table[hash] = new Key(SIDC, value, hash);
			load++;
			return;
		}
		else // We have a collision.
		{
			if (ceiledSize <= 1000) // If the table only has a capacity of a thousand or less, 
			{
				while(table[hash] != null) // resolve the collision through linear probing.
				{
					hash++;
				}
				table[hash] = new Key(SIDC, value, hash);
				load++;
				return;
			}
			else // If the array is larger, resolve the collision through separate chaining.
			{
				Key parentKey = table[hash];
				//System.out.println(String.format("Parent key #%s at index %d.", parentKey.toString(), hash));
				
				while ((parentKey.nextKey != null) && (parentKey.nextKey.isChained = true)) // Find the last chained element.
				{
					parentKey = parentKey.nextKey;
				}
				parentKey.setNext(new Key(SIDC, value, hash, true)); // Create a chained key.
				load++;
				return;
			}
		}
	}
	
	int hash(int SIDC)
	{
		int hash;
		String SIDCAsString = String.valueOf(SIDC);
		
		//System.out.println(String.format("SIDC: %d", SIDC));
		//System.out.println(String.format("Table size: %d", table.length));
		
		// Step 1: remap the SIDC down to a range determined by the array's size.
		int base = (int) Math.pow(10, mappedDigits-1);
		int newMax = base * Integer.parseInt(String.valueOf(ceiledSize).substring(0, 1));

		double ratio = Double.valueOf(newMax)/99999999.0;
		int remapped = (int) (ratio * SIDC);
		//System.out.println(String.format("Remapped key: %d\n", (remapped)));
		
		// Step 2: compute the sum of all digits in the SIDC.
		if ((ceiledSize > 900) & ((remapped * 100 + 72) < ceiledSize)) // Only perform if the result would remain within range.
		{
			int SIDCSum = 0;
			for (char digit : SIDCAsString.toCharArray())
			{
				// The maximum value possible is 8 * 9 = 72.
				SIDCSum += Character.getNumericValue(digit);
			}
			//System.out.println(String.format("SIDC sum: %d\n", (SIDCSum)));
			
			// Append the two computed values.
			hash = remapped * 100 + SIDCSum;
		}
		else if (ceiledSize > 900) // Else, return the remapped value only.
		{
			hash = remapped * 100;
		}
		else // No need to multiply it if the array is less that a thousand entries long.
		{
			hash = remapped;
		}
		
		if (hash > 0) // If the result value is above zero,
			hash--; // subtract one to fit it to the array's [0, ceiledSize-1] indexing range.
		
		//System.out.println(String.format("Final hash: %d\n", (hash)));
		return hash;
	}
	
	
	// Return the specific item associated with the inputed SIDC key.
	Student getValue(int key)
	{
		int hash = hash(key);
		
		if (table[hash] == null)
		{
			System.out.println(String.format("No entry found for SIDC key '%d', hashed at index %d.", key, hash));
			return null;
		}
		else
		{
			//Key targetKey = 
		}

		return new Student();
	}
	
	//
	Student getValues(int key)
	{
		return new Student();
	}

}
