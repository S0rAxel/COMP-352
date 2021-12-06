
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
		else if (table[hash].key < 0) // We have encountered a previously deleted key.
		{
			newKey = new Key(SIDC, value, hash);
			newKey.setPrev(table[hash].prevKey); // These functions handle both regular neighbors and chained keys.
			newKey.setNext(table[hash].nextKey);
			table[hash] = newKey;
		}
		else // We have a collision.
		{
			if (ceiledSize <= 1000) // If the table only has a capacity of a thousand or less, 
			{
				while((hash++ < ceiledSize) && (table[hash] != null)) // resolve the collision through linear probing.
				{
					hash++;
				}
				if (hash < ceiledSize) // If the current hash exceeds the table's capacity, continue on to the separate chaining section.
				{
					table[hash] = newKey = new Key(SIDC, value, hash);
					this.setUpPrevNext(newKey);
					load++;
					return;
				}
			}
			
			// If the array is larger, resolve the collision through separate chaining.
			Key parentKey = table[hash];
			while ((parentKey.nextKey != null) && (parentKey.index == parentKey.nextKey.index)) // Find the last chained element.
			{
				parentKey = parentKey.nextKey;
			}
			
			newKey = new Key(SIDC, value, hash, true); // Create a chained key.
			if (parentKey.equals(this.last)) // If the parent key was the last element in the array, update it.
			{
				this.last = newKey;
			}
			if (parentKey.nextKey != null) // If the parent key has a following key, update the relevant parameters.
			{
				newKey.setNext(parentKey.nextKey);
				parentKey.setNext(newKey);

				newKey.printPrevNext();
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
	
	void setUpPrevNext(Key key) // Allows for negative/deleted keys to keep existing in the chain.
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
		if (key.prevKey == null) // If no item was found, the key�s prev variable will have remained null.
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
		if (key.nextKey == null) // If no item was found, the key�s next variable will have remained null.
		{
			this.last = key; // This means the key is the last item in the array.
		}
		
		System.out.println(String.format("Student #%s (index %d) is preceded by student #%s (index %d), and followed by student #%s (index %d).\n", 
				key.toString(), key.index, 
				key.prevKey, (key.prevKey != null ? key.prevKey.index : -1),
				key.nextKey, (key.nextKey != null ? key.nextKey.index : -1)));
	}
	
	void setUpNext(Key key)  // Allows for negative/deleted keys to keep existing in the chain.
	{
		// Traverse the array in search of a subsequent item.
		for (int currentIndex = key.index + 1; currentIndex < ceiledSize; currentIndex++)
		{
			if (table[currentIndex] != null) // When a first item is found, set and move on.
			{
				key.setNext(table[currentIndex]);
				break;
			}
		}
		if (key.nextKey == null) // If no item was found, the key�s next variable will have remained null.
		{
			this.last = key; // This means the key is the last item in the array.
		}
		
		System.out.println(String.format("Student #%s (index %d) is preceded by student #%s (index %d), and followed by student #%s (index %d).\n", 
				key.toString(), key.index, 
				key.prevKey, (key.prevKey != null ? key.prevKey.index : -1),
				key.nextKey, (key.nextKey != null ? key.nextKey.index : -1)));
	}
	
	
	// Return the specific item associated with the inputed SIDC key.
	Student getValue(int SIDC)
	{
		int hash = hash(SIDC);
		
		if ((table[hash] == null) || table[hash].key == -SIDC) // If the key corresponds to the negative value of the inputed SIDC, it has been deleted.
		{
			//System.out.println(String.format("No entry found for SIDC key '%d', expected to be found at index %d.\n", SIDC, hash));
			return null;
		}
		else
		{
			Key targetKey = table[hash]; // Find the first item at index.
			while(!targetKey.equals(SIDC) && (targetKey.nextKey != null)) // If it's not the right key, move on to the next item.
			{
				if (targetKey.equals(SIDC))
				{
					System.out.println("Found target key!.");
					break;
				} // If the deleted key is encountered, there is no need to continue on.
				else if (targetKey.equals(-SIDC) || (!targetKey.equals(this.last))) 
				{
					System.out.println(String.format("No entry found for SIDC key '%d', expected to be found at index %d.\n", SIDC, hash));
					return null;
				}
				targetKey = targetKey.nextKey;
			}
			
			System.out.println(String.format("Found taget key #%d. Returning associated Student.\n", SIDC));
			//targetKey.printPrevNext();
			return targetKey.value;
		}
	}
	
	// Return all values chained to the hash generated from the SIDC key.
	ArrayList<Student> getValues(int SIDC) // ADD HANDLING OF NEGATIVE/DELETED KEYS
	{
		int hash = hash(SIDC);
		
		if (table[hash] == null)
		{
			System.out.println(String.format("No entry found for SIDC key '%d', expected to be found at index %d.\n", SIDC, hash));
			return null;
		}
		
		ArrayList<Student> studentsFound = new ArrayList<Student>();
		
		if (ceiledSize <= 1000) // If the table's small size means linear probing was used instead of chaining,
		{
			studentsFound.add(this.getValue(SIDC)); // seek and return only the specific student.
			return studentsFound;
		}
		else
		{
			Key targetKey = table[hash]; // Find the first item at index.
			studentsFound.add(targetKey.value); // Beware, it will not be marked as 'chained'.
			
			while ((!targetKey.equals(this.last)) && (targetKey.nextKey != null) && (targetKey.nextKey.isChained = true) && (targetKey.nextKey.index == hash)) // Collect chained elements.
			{
				if (targetKey.key >= 0) // Only collect 'living'/nonegative keys.
				{
					studentsFound.add(targetKey.value);
				}
				targetKey = targetKey.nextKey;
			}
		}
		System.out.println(String.format("%d chained key(s) were/was found at index %d. Returning associated Student(s).\n", studentsFound.size(), hash));
		return studentsFound;
	}
	
	// 'Removes' the key corresponding to the given SIDC code by negating it. It will no longer be accessible to getValue(s) operations, but might remain part of the prev/next chain.
	Key remove(int SIDC)
	{
		int hash = hash(SIDC);
		Key returnKey;
		
		if (table[hash] == null) // Nothing was found at the hash index generated from the SIDC key.
		{
			System.out.println(String.format("No key was found for SIDC code #%s. It may already have been deleted.", SIDC));
			return null;
		}
		
		// Traverse the array in search of the relevant key.
		returnKey = table[hash];
		while (!returnKey.equals(SIDC))
		{
			if ((returnKey.equals(-SIDC)) || (returnKey.equals(this.last)) || (returnKey.nextKey == null)) // If the key has already been deleted, or was never found.
			{
				System.out.println(String.format("No key was found for SIDC code #%s. It may already have been deleted.", SIDC));
				return null;
			}
			returnKey = returnKey.nextKey;
		}
		
		// If the key to be deleted is the root of a chain, replaced it with the next in line.
		if ((!returnKey.isChained) && (returnKey.nextKey.isChained) && (returnKey.index == returnKey.nextKey.index))
		{
			table[returnKey.index] = returnKey.nextKey;
			if (returnKey.prevKey != null)
			{
				table[returnKey.index].setPrev(returnKey.prevKey);
			}
			table[returnKey.index].isChained = false;
			if (returnKey.equals(this.first)) // If the key was the first in the chain, update the hashTable�s first key parameter.
			{
				this.first = table[returnKey.index];
			}
		}
		else // Else, simply �negate� the key to mark it as deleted.
		{
			returnKey.key = -1 * returnKey.key;
			if (returnKey.equals(this.first)) // If the key was the first in the chain, update the hashTable�s first key parameter.
			{
				this.first = returnKey.nextKey;
			}
			if (returnKey.equals(this.last)) // If the key was the last in the chain, update the hashTable�s last key parameter.
			{
				this.last = returnKey.prevKey;
			}
		}
		
		System.out.println(String.format("Key #%d has been removed from index %d.\n", Math.abs(returnKey.key), returnKey.index));
		this.load --;
		return returnKey;
	}
	
	/*List<key> allKeys()
	{
		List<Keys> keyList = new ArrayList();
		Key key = first;

		while (key.nextKey != last)
		{

		}
	}*/

	int rangeKey(int key1, int key2)
	{
		if (load < 0)
		{
			System.out.println(String.format("The table has no entries.\n"));
			return 0;
		} 
		else if (key1 == key2) 
		{
			System.out.println(String.format("Both keys are the same."));
			return 0;
		}

		int range = 0;
		int hash1 = hash(key1);
		int hash2 = hash(key2);
		
		if (table[hash1] == null || table[hash2] == null)
		{
			System.out.println(String.format("No entry found for one or both of the SIDC keys '%d' and '%d'.\n", key1, key2));
			return 0;
		}

		Key k1 = table[hash1];
		Key k2 = table[hash2];

		Key kInBetween = k1.nextKey;

		while (kInBetween != k2)
		{
			if (kInBetween.key > 0)
			{
				range++;
			}
			
			System.out.println("key : " + kInBetween);
			System.out.println("next key : " + kInBetween.nextKey);

			if	( kInBetween.nextKey != null)
			{
				kInBetween = kInBetween.nextKey;
			}
			else 
			{
				return 0;
			}
		}

		return range;
	}
}
