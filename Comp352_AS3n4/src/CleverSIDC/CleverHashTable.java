package CleverSIDC;

import java.util.ArrayList; // Will not be used to construct the data structure itself.

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
			table = new Key[ceiledSize]; // Start with a table of 100,000+ elements.
			
			mappedDigits = 4;
		}
	}
	
	
	void printInfo()
	{
		System.out.println(String.format("The hash table has %d buckets in total and currently contains %d items.", ceiledSize, load));
		System.out.println(String.format("Its first key is #%s at index %d.", first.toString(), first.index));
		System.out.println(String.format("Its last key is #%s at index %d.\n", last.toString(), last.index));
	}
	
	void add(int SIDC, Student value)
	{
		int hash = hash(SIDC);
		Key newKey = null;
		
		if (table[hash] == null)
		{
			table[hash] = newKey = new Key(SIDC, value, hash);
			this.setUpPrevNext(newKey);
		}
		else if (table[hash] != null) // We have a collision.
		{
			if (ceiledSize <= 1000) // If the table only has a capacity of a thousand or less, 
			{
				while(table[hash] != null) // resolve the collision through linear probing.
				{
					hash++;
				}
				table[hash] = newKey = new Key(SIDC, value, hash); // The constructor will scan for the previous and next keys.
				this.setUpPrevNext(newKey);
			}
			else // If the array is larger, resolve the collision through separate chaining.
			{
				Key parentKey = table[hash];
				//System.out.println(String.format("Parent key #%s at index %d.", parentKey.toString(), hash));
				
				while ((parentKey.nextKey != null) && (parentKey.nextKey.isChained = true)) // Find the last chained element.
				{
					parentKey = parentKey.nextKey;
					//System.out.println(String.format("Moving on to key #%d.\n", parentKey.key));
				}
				parentKey.setNext(newKey = new Key(SIDC, value, hash, true)); // Create a chained key.
				if ((parentKey.nextKey != this.last) && (newKey.nextKey == null)) // 
				{
					this.setUpNext(newKey);
				}
			}
		}
		load++;
		return;
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
	
	void setUpPrevNext(Key key)
	{
		// Traverse the array in search of a previous item.
		for (int currentIndex = key.index - 1; currentIndex >= 0; currentIndex--)
		{
			if (table[currentIndex] != null) // When a first item is found, set and move on.
			{
				Key currentKey = table[currentIndex];
				while ((currentKey.nextKey != null) && (currentKey.nextKey.isChained = true) && (currentKey.index == currentKey.nextKey.index)) // If needed, handle chained keys.
				{
					currentKey = currentKey.nextKey; 
				}
				key.setPrev(currentKey);
				break;
			}
		}
		if (key.prevKey == null) // If no item was found, the key’s prev variable will have remained null.
		{
			this.first = key; // This means the key is the first item in the array.
		}
		
		// Traverse the array in search of a subsequent item.
		for (int currentIndex = key.index + 1; currentIndex < ceiledSize; currentIndex++)
		{
			if (table[currentIndex] != null) // When a first item is found, set and move on.
			{
				key.setNext(table[currentIndex]);
				break;
			}
		}
		if (key.nextKey == null) // If no item was found, the key’s next variable will have remained null.
		{
			this.last = key; // This means the key is the last item in the array.
		}
		
		System.out.println(String.format("Student #%s (index %d) is preceded by student #%s (index %d), and followed by student #%s (index %d).\n", 
				key.toString(), key.index, 
				key.prevKey, (key.prevKey != null ? key.prevKey.index : -1),
				key.nextKey, (key.nextKey != null ? key.nextKey.index : -1)));
	}
	
	void setUpNext(Key key)
	{
		// Traverse the array in search of a subsequent item.
		for (int currentIndex = key.index + 1; currentIndex < ceiledSize; currentIndex++)
		{
			if (table[currentIndex] != null) // When a first item is found, set and move on.
			{
				key.setNext(table[currentIndex]);
				break;
			}
			if (key.nextKey == null) // If no item was found, the key’s next variable will have remained null.
			{
				this.last = key; // This means the key is the last item in the array.
			}
		}
		
		System.out.println(String.format("Student #%s (index %d) is preceded by student #%s (index %d), and followed by student #%s (index %d).\n", 
				key.toString(), key.index, 
				key.prevKey, (key.prevKey != null ? key.prevKey.index : -1),
				key.nextKey, (key.nextKey != null ? key.nextKey.index : -1)));
	}
	
	
	// Return the specific item associated with the inputed SIDC key.
	Student getValue(int key)
	{
		int hash = hash(key);
		
		if (table[hash] == null)
		{
			System.out.println(String.format("No entry found for SIDC key '%d', expected to be found at index %d.\n", key, hash));
			return null;
		}
		else
		{
			Key targetKey = table[hash]; // Find the first item at index.
			
			while(!targetKey.equals(key)) // If it's not the right key, move on to the next item.
			{
				if (targetKey.nextKey != null)
				{
					targetKey = targetKey.nextKey;
					if (targetKey.equals(key))
						break;
				}
				else
				{
					System.out.println(String.format("No entry found for SIDC key '%d', expected to be found at index %d.\n", key, hash));
					return null;
				}
			}
			
			System.out.println(String.format("Found taget key #%d. Returning associated Student.\n", key));
			//targetKey.printPrevNext();
			return targetKey.value;
		}
	}
	
	// Return all values chained to the hash generated from the SIDC key.
	ArrayList<Student> getValues(int key)
	{
		int hash = hash(key);
		
		if (table[hash] == null)
		{
			System.out.println(String.format("No entry found for SIDC key '%d', expected to be found at index %d.\n", key, hash));
			return null;
		}
		
		ArrayList<Student> studentsFound = new ArrayList<Student>();
		
		if (ceiledSize <= 1000) // If the table's small size means linear probing was used instead of chaining,
		{
			studentsFound.add(this.getValue(key)); // seek and return only the specific student.
			return studentsFound;
		}
		else
		{
			Key targetKey = table[hash]; // Find the first item at index.
			studentsFound.add(targetKey.value); // Beware, it will not be marked as 'chained'.
			
			while ((targetKey.nextKey != null) && (targetKey.nextKey.isChained = true) && (targetKey.nextKey.index == hash)) // Collect chained elements.
			{
				studentsFound.add(targetKey.value);
				targetKey = targetKey.nextKey;
			}
		}
		System.out.println(String.format("%d chained key(s) were/was found at index %d. Returning associated Student(s).\n", studentsFound.size(), hash));
		return studentsFound;
	}

}
