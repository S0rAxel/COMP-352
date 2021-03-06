package CleverSIDC;

class Key 
{
	int key;
	int index;
	
	Student value;
	
	// Sequential information.	
	Boolean isChained = false;
	Key nextKey = null;
	Key prevKey = null;
	
	
	// Constructor
	
	public Key (int SIDC, Student value, int hash)
	{
		this.key = SIDC;
		this.value = value;
		
		this.index = hash;
		
		System.out.println(String.format("Student #%d inserted at index %d.", SIDC, hash));
	}
	
	public Key (int SIDC, Student value, int hash, boolean isChained)
	{
		this.key = SIDC;
		this.value = value;
		
		this.index = hash;
		this.isChained = true;
		
		System.out.println(String.format("Student #%d inserted at index %d, chained.", SIDC, hash));
	}
	
	
	// Setters
	
	void setNext(Key newNext)
	{
		if (String.valueOf(key).replace("-", "").length() == 8)
		{
			Key oldNextKey = this.nextKey;
			
			this.nextKey = newNext;
			newNext.prevKey = this;

			if (newNext.nextKey == null)
			{
				newNext.nextKey = oldNextKey;
			}
		}
		else
		{
			System.out.println(String.format("The key in reference (%d) is not valid.", newNext.toString()));
		}
	}
	
	void setPrev(Key newPrev)
	{
		if (String.valueOf(key).replace("-", "").length() == 8)
		{
			Key oldPrevKey = this.prevKey;
			
			this.prevKey = newPrev;
			newPrev.nextKey = this;
			
			if (newPrev.prevKey == null)
			{
				newPrev.prevKey = oldPrevKey;
			}
		}
		else
		{
			System.out.println(String.format("The key in reference (%d) is not valid.", newPrev.toString()));
		}
	}

	
	// Misc
	
	void printPrevNext()
	{
		System.out.println(String.format("Student #%s (index %d) is preceded by student #%s (index %d), and followed by student #%s (index %d).\n", 
				this.toString(), this.index, 
				this.prevKey, (this.prevKey != null ? this.prevKey.index : -1),
				this.nextKey, (this.nextKey != null ? this.nextKey.index : -1)));
	}
	
	// Overrides
	
	public String toString()
	{
		return String.valueOf(key);
	}
	
	public Boolean equals(Key otherKey)
	{
		return (key == otherKey.key);
	}
	
	public Boolean equals(int otherKey)
	{
		return (key == otherKey);
	}
	
	public int compareTo(Key otherKey)
	{
		return Integer.compare(key, otherKey.key);
	}
}