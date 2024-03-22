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

        // Calculate the balance factor for the current node
        int balanceFactor = getBalanceFactor(node);

        // Left subtree is heavier
        if (balanceFactor > 1) {
            // Left-Left case: Perform right rotation on the current node
            if (getHeight(node.left.left) >= getHeight(node.left.right)) {
                return rightRotate(node);
            }
            // Left-Right case: Perform left rotation on the left child followed by right
            // rotation on the current node
            else {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }
        // Right subtree is heavier
        else if (balanceFactor < -1) {
            // Right-Right case: Perform left rotation on the current node
            if (getHeight(node.right.right) >= getHeight(node.right.left)) {
                return leftRotate(node);
            }
            // Right-Left case: Perform right rotation on the right child followed by left
            // rotation on the current node
            else {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }
        return node; // Node is already balanced
    }
}

public class GenericsKbAVLApp {
    public static void main(String[] args) throws Exception {
        
    }
}
