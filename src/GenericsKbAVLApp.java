import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class AVLNode {
    String data;
    AVLNode left;
    AVLNode right;
    int height;

    /**
     * Constructs an AVLNode with the given data.
     *
     * @param data The data to be stored in the node.
     */
    public AVLNode(String data) {
        this.data = data;
        left = right = null;
        height = 1;
    }
}

class AVLTree {
    AVLNode root;
    private int searchOpCount = 0;
    private int insertOpCount = 0;

    /**
     * Constructs an empty AVL tree.
     */
    public AVLTree() {
        root = null;
    }

    public int getSearchOpCount() {
        return searchOpCount;
    }

    public int getInsertOpCount() {
        return insertOpCount;
    }

    public void resetSearchOpCount() {
        searchOpCount = 0;
    }

    public void insert(String data) {
        root = insertRec(root, data);
        if (!isBalanced(root)) {
            root = balance(root);
        }
    }

    private AVLNode insertRec(AVLNode node, String data) {
        if (node == null) {
            insertOpCount++;
            return new AVLNode(data);
        }

        if (data.compareTo(node.data) < 0) {
            node.left = insertRec(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            node.right = insertRec(node.right, data);
        } else {
            return node;
        }

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        int balance = getBalanceFactor(node);

        // If node becomes unbalanced after new data is inserted, then there are 4 cases

        // Left Left Case
        if (balance > 1 && data.compareTo(node.left.data) < 0) {
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && data.compareTo(node.right.data) > 0) {
            return leftRotate(node);
        }

        // Left Right Case
        if (balance > 1 && data.compareTo(node.left.data) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && data.compareTo(node.right.data) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public boolean search(String searchTerm) {
        return searchRec(root, searchTerm);
    }

    private boolean searchRec(AVLNode node, String searchTerm) {
        if (node == null) {
            return false;
        }

        String[] parts = node.data.split("\t");
        String term = parts[0];
        String statement = parts[1];
        String score = parts[2];

        int comparisonResult = searchTerm.compareTo(term);
        if (comparisonResult == 0) {
            searchOpCount++;
            System.out.println(term + ": " + statement + " (" + score + ")");
            return true;
        } else if (comparisonResult < 0) {
            searchOpCount++;
            return searchRec(node.left, searchTerm);
        } else {
            searchOpCount++;
            return searchRec(node.right, searchTerm);
        }
    }

    /**
     * Calculates the height of a node.
     *
     * @param node The node to calculate the height for.
     * @return The height of the node.
     */
    private int getHeight(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    /**
     * Calculates the balance factor for a given node.
     *
     * @param node The node to calculate the balance factor for.
     * @return The balance factor of the node.
     */
    private int getBalanceFactor(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    /**
     * Rotates a node on the AVL tree left
     *
     * @param y The node to perform the rotation on.
     * @return The new root node after rotation.
     */
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode swapVariable = x.right;

        // Perform rotation
        x.right = y;
        y.left = swapVariable;

        // Update heights
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        // Return new root
        return x;
    }

    /**
     * Rotates a node on the AVL tree left.
     *
     * @param x The node to perform the rotation on.
     * @return The new root node after rotation.
     */
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode swapVariable = y.left;

        // Perform rotation
        y.left = x;
        x.right = swapVariable;

        // Update heights
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;

        // Return new root
        return y;
    }

    private boolean isBalanced(AVLNode node) {
        int balanceFactor = getBalanceFactor(node);
        return balanceFactor >= -1 && balanceFactor <= 1;
    }

    private AVLNode balance(AVLNode node) {
        if (node == null) {
            return null;
        }

        int balanceFactor = getBalanceFactor(node);

        // Left subtree is heavier
        if (balanceFactor > 1) {
            // Left-Left case
            if (getHeight(node.left.left) >= getHeight(node.left.right)) {
                return rightRotate(node);
            }
            // Left-Right case
            else {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }
        // Right subtree is heavier
        else if (balanceFactor < -1) {
            // Right-Right case
            if (getHeight(node.right.right) >= getHeight(node.right.left)) {
                return leftRotate(node);
            }
            // Right-Left case
            else {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }
        return node;
    }
}

public class GenericsKbAVLApp {
    private static AVLTree avlTree;

    /**
     * Main method to run the AVL tree application.
     * 
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        avlTree = new AVLTree();

        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter the name of the knowledge base file: ");
        String kbFile = keyboard.nextLine();

        System.out.print("Enter the name of the query file: ");
        String queryFile = keyboard.nextLine();

        keyboard.close();

        readKB(kbFile);

        searchQueries(queryFile);

        System.out.println("Search Operations: " + avlTree.getSearchOpCount());
        System.out.println("Insert Operations: " + avlTree.getInsertOpCount());
    }

    /**
     * Loads the knowledge base from the specified file.
     * 
     * @param fileName The name of the file containing the knowledge base.
     */
    private static void readKB(String fileName) {
        try {
            Scanner file = new Scanner(new File(fileName));
            while (file.hasNextLine()) {
                String line = file.nextLine();

                String[] parts = line.split("\t");
                if (parts.length == 3) {
                    avlTree.insert(line);
                }
            }
            System.out.println("\nKnowledge base loaded successfully.\n");
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("\nError - File not found: " + fileName + "\n");
        }
    }

    /**
     * Perform searches based on queries from the specified file.
     * 
     * @param fileName The name of the file containing the queries.
     */
    private static void searchQueries(String fileName) {
        try {
            Scanner file = new Scanner(new File(fileName));
            while (file.hasNextLine()) {
                String searchTerm = file.nextLine().trim();
                boolean found = avlTree.search(searchTerm);
                if (!found) {
                    System.out.println("Term not found: \"" + searchTerm +"\"");
                }
                System.out.println();
            }
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("\nError - File not found: " + fileName + "\n");
        }
    }
}
