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
}

public class GenericsKbAVLApp {
    public static void main(String[] args) throws Exception {
        
    }
}
