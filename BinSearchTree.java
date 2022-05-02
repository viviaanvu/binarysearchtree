/******************************************************************************************************

 Name - Vivian Vu
 Date - 4/30/20
 Course - CMSC 256

 Project 5

 File Name - BinSearchTree
 File Purpose - Implements methods from BinTreeInterface.

 ******************************************************************************************************/
package cmsc256;

import bridges.base.BinTreeElement;
import bridges.connect.Bridges;

public class BinSearchTree<E> implements BinTreeInterface<E> {

    private BinTreeElement<E> root = null;
    private int size = 0;

    //gets the root of the tree
    @Override
    public BinTreeElement<E> getRoot() {
        return root;
    }

    //adds node to tree
    @Override
    public boolean add(E element) {
        boolean a = true;

        BinTreeElement<E> node = new BinTreeElement<>(element);
        node.setLabel(node.getValue().toString());

        if(getRoot() == null) {
            root = node;
        }
        else {
            a = addToParent(root, node);
        }

        if(a) {
            size++;
        }

        return a;
    }

    private boolean addToParent(BinTreeElement<E> parent, BinTreeElement<E> node) {
        String parentNode = parent.getValue().toString();
        String newNode = node.getValue().toString();
        boolean a;

        int compare = parentNode.compareTo(newNode);

        if (compare > 0) {
            if(parent.getLeft() == null) {
                parent.setLeft(node);
                a = true;
            }
            else {
                a = addToParent(parent.getLeft(), node);
            }
        }
        else {
            if(parent.getRight() == null) {
                parent.setRight(node);
                a = true;
            }
            else {
                a = addToParent(parent.getRight(), node);
            }
        }

        return a;
    }

    //removes element from tree
    @Override
    public boolean remove(E element) {
        if (root == null) {
            return false;
        }
        if (root.getValue() == element) {
            if (root.getLeft() == null) {
                root = root.getRight();
            }
            else if (root.getRight() == null) {
                root = root.getLeft();
            }
            else {
                BinTreeElement<E> right = root.getRight();

                root = root.getLeft();

                addToParent(root, right);
            }

            size--;
            return true;
        }
        return removeNode(root, element);
    }

    //removes a node from parent
    public boolean removeNode(BinTreeElement<E> parent, E node) {
        String parentNode = parent.getValue().toString();
        String newNode = node.toString();
        BinTreeElement<E> tree;

        int compare = (parentNode.compareTo(newNode));

        if (compare > 0) {
            tree = parent.getLeft();
        }
        else {
            tree = parent.getRight();
        }
        if (tree == null) {
            return false;
        }
        if ((tree.getValue().toString().compareTo(node.toString())) == 0) {
            BinTreeElement<E> e;
            if (tree.getLeft() == null) {
                e = tree.getLeft();
            }
            else if (tree.getRight() == null) {
                e = tree.getRight();
            }
            else {
                BinTreeElement<E> right = tree.getRight();
                e = tree.getLeft();

                addToParent(e, right);
            }

            if (compare > 0) {
                parent.setRight(e);
            }
            else {
                parent.setLeft(e);
            }
            size--;
            return true;
        }
        return removeNode(tree, node);
    }

    //returns size of tree
    @Override
    public int size() {
        return size;
    }

    //checks of tree is empty
    @Override
    public boolean isEmpty() {
        return getRoot() == null;
    }

    //clears tree
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    //searches for node in tree
    @Override
    public boolean search(E e) {
        BinTreeElement<E> current = root;

        while (current != null) {
            if (e.toString().compareTo(current.getValue().toString()) < 0) {
                current = current.getLeft();
            }
            else if (e.toString().compareTo(current.getValue().toString()) > 0) {
                current = current.getRight();
            }
            else {
                return true;
            }
        }
        return false;
    }

    //calls inorder method and returns it
    @Override
    public String inorder() {
        return inorderTraversal(root);
    }

    //returns inorder traversal
    private String inorderTraversal(BinTreeElement<E> e) {
        String str = "";
        if (e != null) {
            inorderTraversal(e.getLeft());
            str = str + e.getValue().toString() + " ";
            inorderTraversal(e.getRight());
        }

        return str;
    }

    //calls postorder method and returns it
    @Override
    public String postorder() {
        return postorderTraversal(root);
    }

    //returns postorder traversal
    private String postorderTraversal(BinTreeElement<E> e) {
        String str = "";
        if (e != null) {
            postorderTraversal(e.getLeft());
            postorderTraversal(e.getRight());
            str = str + e.getValue().toString() + " ";
        }

        return str;
    }

    //calls preorder method and returns it
    @Override
    public String preorder() {
        return preorderTraversal(root);
    }

    //returns preorder traversal
    private String preorderTraversal(BinTreeElement<E> e) {
        String str = "";
        if (e != null) {
            str = str + e.getValue().toString() + " ";
            preorderTraversal(e.getLeft());
            preorderTraversal(e.getRight());
        }

        return str;
    }

    //calls calcHeight method and returns it
    public int height() {
        return getHeight(root);
    }

    //calculates the height of the tree
    private int getHeight(BinTreeElement<E> e) {
        if (e == null) {
            return -1;
        }

        int left = getHeight(e.getLeft());
        int right = getHeight(e.getRight());

        if (left > right) {
            return left + 1;
        }
        else {
            return right + 1;
        }
    }

    //calls the checkFullBST method and returns it
    public boolean isFullBST() {
        if (root == null) {
            return false;
        }
        return checkFullBST(getRoot());
    }

    //checks if the tree is full
    private boolean checkFullBST(BinTreeElement<E> e) {
        BinTreeElement<E> left = e.getLeft();
        BinTreeElement<E> right = e.getRight();
        if (left == null && right == null) {
            return true;
        }
        if ((left != null) && (right != null)) {
            return (checkFullBST(left) && checkFullBST(right));
        }
        return false;
    }

    //calls the leaves method and returns it
    public int getNumberOfLeaves() {
        return leaves(getRoot());
    }

    //finds the number of leaves in the tree
    private int leaves(BinTreeElement<E> e) {
        if (e == null) {
            return 0;
        }
        if (e.getLeft() == null && e.getRight() == null) {
            return 1;
        }
        else {
            return leaves(e.getLeft()) + leaves(e.getRight());
        }
    }

    //calls the nonleaves method and returns it
    public int getNumberOfNonLeaves() {
        return nonleaves(getRoot());
    }

    //returns int of the size of the tree subtracted by the number of leaves
    private int nonleaves(BinTreeElement<E> e) {
        return size() - leaves(e);
    }

    //main method
    public static void main(String[] args) {
        Bridges bridges = new Bridges(5, "vivitrinhvu", "145852909132");
        bridges.setTitle("Vivian Vu Project 5");
        bridges.setDescription("CMSC256: BinSearchTree");

        BinSearchTree <String> names = new BinSearchTree<>();
        names.add("Frodo");
        names.add("Dori");
        names.add("Bilbo");
        names.add("Kili");
        names.add("Gandalf");
        names.add("Fili");
        names.add("Thorin");
        names.add("Nori");

        try {
            bridges.setDataStructure(names.getRoot());
            bridges.visualize();
        }
        catch (Exception e){
            System.out.println("Cannot connect to Bridges");
        }
    }

}