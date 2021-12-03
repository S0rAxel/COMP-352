package CleverSIDC;

class CleverHashTable 
{
	int size;
	Key[] table;
	
	int digitCutoff;
	
	
	
	
	
	
	
	
	
	void setSIDCThreshold(int size)
	{
		if (size < 10000) // Hashes of four digits long.
		{
			table = new Key[1000]; // Start with a table of 1,000 elements.
			digitCutoff = 2;
			return;
		}
		if (size < 100000) // Hashes of five digits long.
		{
			table = new Key[10000]; // Start with a table of 10,000 elements.
			digitCutoff = 3;
			return;
		}
		else
		{
			table = new Key[100000]; // Start with a table of 100,000 elements.
			digitCutoff = 4;
		}
	}
	
	
	
	
	
	int hash(int SIDC, int digitCutoff)
	{
		String SIDCAsString = String.valueOf(SIDC);
		
		// Harvest first two numbers 
		String hashAsString = SIDCAsString.substring(0, digitCutoff);
		
		// Compute the total of the numbers in the SIDCKey.
		int SIDCSum = 0;
		for (char digit : SIDCAsString.toCharArray())
		{
			// The maximum value possible is 8 * 9 = 72.
			SIDCSum += Character.getNumericValue(digit);
		}
		
		hashAsString.concat(String.format("%01d", SIDCSum));
		
		return Integer.valueOf(hashAsString);
	}

}
