import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Node {
    int data, height;
    Node left, right;

    Node(int data) {
        this.data = data;
        this.height = 1;
    }
}

public class AVLTree {
    private Node root;

    private int height(Node node) {
        if (node == null) return 0;
        return node.height;
    }

    private int getBalanceFactor(Node node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    public void insert(int data) {
        root = insertRec(root, data);
    }

    private Node insertRec(Node node, int data) {
        if (node == null) return new Node(data);

        if (data < node.data) {
            node.left = insertRec(node.left, data);
        } else if (data > node.data) {
            node.right = insertRec(node.right, data);
        } else {
            return node; // Duplicate data not allowed
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalanceFactor(node);

        if (balance > 1) {
            if (data < node.left.data) {
                return rotateRight(node);
            } else {
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
        }

        if (balance < -1) {
            if (data > node.right.data) {
                return rotateLeft(node);
            } else {
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
        }

        return node;
    }

    public void delete(int data) {
        root = deleteRec(root, data);
    }

    public void display() {
        display(this.root, "Root Node: ");
    }

    private void display(Node node, String details) {
        if (node == null) {
            return;
        }
        System.out.println(details + node.data);
        display(node.left, "Left child of " + node.data + " : ");
        display(node.right, "Right child of " + node.data + " : ");
    }

    private Node deleteRec(Node root, int data) {
        // TODO: Implement deletion logic
        return root;
    }

    public void inOrderTraversal() {
        inOrderTraversalRec(root);
    }

    private void inOrderTraversalRec(Node root) {
        if (root != null) {
            inOrderTraversalRec(root.left);
            System.out.print(root.data + " ");
            inOrderTraversalRec(root.right);
        }
    }

    public List<List<Integer>> bfs(Node node)
    {
        List<List<Integer>> result = new ArrayList<>();

        Queue<Node> xd = new LinkedList<>();

        xd.offer(node);
        while(!xd.isEmpty())
        {
            int currentLevelSize = xd.size();
            List<Integer> currentLevel = new ArrayList<>();

            for(int i = 0 ; i< currentLevelSize ; i++)
            {
                Node curr = xd.poll();
                currentLevel.add(curr.data);
                if(curr.left != null)
                {
                    xd.offer(curr.left);
                }
                if(curr.right != null)
                {
                    xd.offer(curr.right);
                }
            }
            result.add(currentLevel);


        }

        return result;
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        for(int i = 0; i < 20 ; i++)
        {
            tree.insert(i);
        }

        System.out.println(tree.height(tree.root));
        tree.display();
        List<List<Integer>> level = new ArrayList<>();


        System.out.println(tree.bfs(tree.root));

    }
}
