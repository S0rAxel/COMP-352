package CleverSIDC;

import java.lang.Math;

public class AVLTree 
{
    Node root;

    int height(Node node)
    {
        if (node == null)
        {
            return -1;
        }

        return node.height;
    }
    
    Node rotateRight(Node y)
    {
        Node x = y.left;
        Node z = y.right;

        x.right = y;
        y.left = z;

        updateHeight(y);
        updateHeight(y);

        return x;
    }

    Node rotateLeft(Node y) 
    {
        Node x = y.right;
        Node z = x.left;

        x.left = y;
        y.right = z;

        updateHeight(x);
        updateHeight(y);

        return x;
    }

    int getBalance(Node node)
    {
        if (node == null)
        {
            return 0;
        }

        return height(node.left) - height(node.right);
    }

    Node rebalance(Node node)
    {
        updateHeight(node);
        int balance = getBalance(node);

        if (balance > 1)
        {
            if (height(node.right.right) > height(node.right.left))
            {
                node = rotateLeft(node);
            }
            else 
            {
                node.right = rotateRight(node.right);
                node = rotateLeft(node);
            }
        } 
        else if (balance < -1)
        {
            if (height(node.left.left) > height(node.left.right))
            {
                node = rotateRight(node);
            }
            else 
            {
                node.left = rotateLeft(node.left);
                node = rotateRight(node);
            }
        }
 
        return node;
    }

    Node rebalance(Node node, int key) 
    {
         
        updateHeight(node);
        int balance = getBalance(node);
 
        if (balance > 1 && key < node.left.key)
        {
            return rotateRight(node);

        }
        else if (balance < -1 && key > node.right.key)
        {
            return rotateLeft(node);
        }
        else if (balance > 1 && key > node.left.key) 
        {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        else if (balance < -1 && key < node.right.key) 
        {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    Node insert(Node node, int key)
    {
        if (node == null)
        {
            return new Node(key);
        }

        if (key < node.key)
        {
            node.left = insert(node.left, key);
            return rebalance(node, key);
        }
        else if (key > node.key)
        {
            node.right = insert(node.right, key);
            return rebalance(node, key);
        }
        else 
        {
            return node;
        }
    }

    void inOrder(Node node)
    {
        if (node == null)
        {
            return;
        }

        inOrder(node.left);
        System.out.print(node.key + " ");
        inOrder(node.right);
    }

    void preOrder(Node node) {
        if (node == null) 
        {   
            return;
        }
 
        System.out.print(node.key + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    // Quick function to update the height of a node
    void updateHeight(Node node)
    {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }
}