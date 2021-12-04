package CleverSIDC;

class Key 
{
	int key;
	int index;
	
	Student value;
	
	// Sequential information.	
	Key nextKey;
	Key prevKey;
	
	
	// Constructor
	
	public Key (int key, Student value, int hash)
	{
		this.key = key;
		this.value = value;
		
		this.index = hash;
	}
	
	
	// Getters & Setters
	
	void setNext(Key newNext)
	{
		if (String.valueOf(key).length() == 8)
		{
			this.nextKey = newNext;
			newNext.prevKey = this;
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
			this.prevKey = newPrev;
			newPrev.nextKey = this;
		}
		else
		{
			System.out.println(String.format("The key in reference (%d) is not valid.", newPrev.toString()));
		}
	}
	
	Key getNext()
	{
		if (nextKey != null)
		{
			return nextKey;
		}
		else
		{
			System.out.println("No subsequent key exists.");
			return null;
		}
	}
	
	Key getPrev()
	{
		if (prevKey != null)
		{
			return prevKey;
		}
		else
		{
			System.out.println("No previous key exists.");
			return null;
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
	
	public int compareTo(Key otherKey)
	{
		return Integer.compare(key, otherKey.key);
	}
}
