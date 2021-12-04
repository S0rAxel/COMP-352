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
	
	public Key (int key, Student value, int hash)
	{
		this.key = key;
		this.value = value;
		
		this.index = hash;
		
		System.out.println(String.format("Student #%d inserted at index %d.\n", key, hash));
	}
	
	public Key (int key, Student value, int hash, boolean isChained)
	{
		this.key = key;
		this.value = value;
		
		this.index = hash;
		this.isChained = true;
		
		System.out.println(String.format("Student #%d inserted at index %d, chained.\n", key, hash));
	}
	
	// Setters
	
	void setNext(Key newNext)
	{
		if (String.valueOf(key).length() == 8)
		{
			newNext.prevKey = this;
			if (this.nextKey != null)
			{
				System.out.println(this.nextKey.nextKey);
				newNext.nextKey = this.nextKey.nextKey;
			}
			this.nextKey = newNext;
		}
		else
		{
			System.out.println(String.format("The key in reference (%d) is not valid.", newNext.toString()));
		}
	}
	
	void setPrev(Key newPrev)
	{
		if (String.valueOf(key).length() == 8)
		{
			newPrev.prevKey = this.prevKey;
			newPrev.nextKey = this;
			this.prevKey = newPrev;
		}
		else
		{
			System.out.println(String.format("The key in reference (%d) is not valid.", newPrev.toString()));
		}
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
