package CleverSIDC;

public class Node 
{
    Key key;
    int height;
    Node left;
    Node right;

    Node(Key key)
    {
        this.key = key;
        height = 1;
    }
}
