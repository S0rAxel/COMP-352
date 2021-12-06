package CleverSIDC;

public class Node 
{
    int key;
    int height;
    Node left;
    Node right;

    Node(int key)
    {
        this.key = key;
        height = 1;
    }
}
