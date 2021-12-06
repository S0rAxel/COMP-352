package CleverSIDC;

import java.util.Arrays;

public class Sequence 
{
    public Key[] keys;
    private int size = 0;

    
    public void addAtRank(int i, Key key)
    {
        if (size == 0)
        {
            size++;
            keys = new Key[1];
            keys[0] = key;
            return;
        }
        if (size == keys.length)
        {
            increaseCapacity();
        }

        size++;
        keys[i] = key;
    }

    public Key removeAtRack(int i)
    {
        if (isEmpty())
        {
            return null;
        }

        Key keyToReturn = keys[i];
        keys[i] = null;
        size--;

        return keyToReturn;
    }

    public int size()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return keys.length > 0 ? false : true;
    }

    private void increaseCapacity()
    {
        int newCapacity = keys.length * 2; // doubling it
        keys = Arrays.copyOf(keys, newCapacity);
    }
}
