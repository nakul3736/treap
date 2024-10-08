public class AbstractTree {

    /*
        @Params :-
        newNode :- Node which is to be rotated until parent value is less than current.

        Like heapify treapify make the rotations maintaining the Treap property
    */
    protected void treapify(Node newNode) {

        //move from the newNode to the parents until the parent value is less than child
        while(newNode.getParent() != null && newNode.getValue() > newNode.getParent().getValue()) {

            Node newNodeParent = newNode.getParent();
            Node newNodeGrandParent = newNodeParent.getParent();

            //if grandParent exist
            if(newNodeGrandParent != null) {

                //if newNodeParent is left child , set grandParent left to newNode
                if(newNodeGrandParent.getLeft() != null && newNodeGrandParent.getLeft().equals(newNodeParent)) {
                    newNodeGrandParent.setLeft(newNode);
                }else if(newNodeGrandParent.getRight() != null && newNodeGrandParent.getRight().equals(newNodeParent)){
                    //if newNodeParent is right child, set grandParent right to newNode

                    newNodeGrandParent.setRight(newNode);
                }
            }


            //if newNode is right child of parent then do left rotation
            if(newNodeParent.getRight() != null && newNodeParent.getRight().equals(newNode)) {

                Node newNodeLeft = newNode.getLeft();

                //if left child of newNode exist , then put it on right of parent
                if(newNodeLeft != null) {
                    newNodeParent.setRight(newNodeLeft);
                    newNodeLeft.setParent(newNodeParent);
                }else {
                    //if left child does not exist then set right to NULL
                    newNodeParent.setRight(null);
                }

                //rotate parent to left , set newNode left to newNodeParent
                newNode.setLeft(newNodeParent);
                newNodeParent.setParent(newNode);

            }else if(newNodeParent.getLeft() != null && newNodeParent.getLeft().equals(newNode)){
                //right rotate

                Node newNodeRight = newNode.getRight();

                //if right child of newNode exist , then put it on left of parent
                if(newNodeRight != null) {
                    newNodeParent.setLeft(newNodeRight);
                    newNodeRight.setParent(newNodeParent);
                }else {
                    newNodeParent.setLeft(null);

                }

                //rotate parent to right , set newNode right to newNodeParent
                newNode.setRight(newNodeParent);
                newNodeParent.setParent(newNode);
            }

            //if grandParent exist change parent of newNode
            if(newNodeParent.getParent() != null) {
                newNode.setParent(newNodeGrandParent);
            }else {
                newNode.setParent(null);
            }
        }
    }

    /*
        @Params :-
        root :- It can be root of Treap or searchTree
        key :- key to be inserted
        value :- value for given key

        generic add for Treap as well as for searchTree
     */
    protected Node addNode(Node root, String key, int heapValue) {

        Node node = root;
        Node newNode = new Node(key, heapValue);

        //if no node is present in tree
        if(node == null) {
            root = addNodeInBST(null, newNode);
            return root;
        }

        //first insert the element in tree according to Binary search
        root = addNodeInBST(node, newNode);

        //now rotate nodes until newNode reach to its correct place
        if(node.getValue() < heapValue) {

            //handling case when heapValue is greater than current root value
            treapify(newNode);
            root = newNode;
        }else {
            treapify(newNode);
        }

        return root;
    }

    /*
       @Params :-
       root :- It can be root of Treap or searchTree
       key :- key to be removed

       generic remove for Treap as well as for searchTree
    */
    protected Node removeNode(Node root,String key) {

        //find the key which is to be removed
        Node nodeToBeRemoved = searchInBST(root, key);

        //if key is itself the root node
        if(nodeToBeRemoved.getParent() == null) {

            //is left and right child doesn't exist simply make root null
            if(nodeToBeRemoved.getLeft() == null && nodeToBeRemoved.getRight() == null) {
                root = null;
            }else if(nodeToBeRemoved.getLeft() == null || nodeToBeRemoved.getRight() == null) {

                //if either of the child exist
                Node nextRoot = nodeToBeRemoved.getLeft() != null ? nodeToBeRemoved.getLeft() : nodeToBeRemoved.getRight();

                //if right child exist make it root
                if(nodeToBeRemoved.getRight() != null) {
                    Node nodeToBeRemovedRight = nodeToBeRemoved.getRight();

                    nodeToBeRemoved.setRight(null);
                    nodeToBeRemovedRight.setParent(null);
                }else {
                    //if left child exist make it root
                    Node nodeToBeRemovedLeft = nodeToBeRemoved.getLeft();

                    nodeToBeRemoved.setLeft(null);
                    nodeToBeRemovedLeft.setParent(null);
                }

                root = nextRoot;
                root.setParent(null);
            }else {
                //if both left and right child exist , pick the node with the greater heapValue
                Node nextRoot = nodeToBeRemoved.getLeft().getValue() > nodeToBeRemoved.getRight().getValue() ? nodeToBeRemoved.getLeft() : nodeToBeRemoved.getRight();

                //if right child value is greater than left child than make right child the root
                if(nodeToBeRemoved.getRight().getValue() > nodeToBeRemoved.getLeft().getValue()) {
                    Node nodeToBeRemovedRight = nodeToBeRemoved.getRight();

                    //if left child of the right child of the root exist then remove it and place it in left subtree of root
                    if(nodeToBeRemovedRight.getLeft() != null) {
                        Node nodeToBeRemovedRightLeft = nodeToBeRemovedRight.getLeft();
                        Node iterator = nodeToBeRemoved.getLeft();

                        nodeToBeRemovedRight.setLeft(null);

                        //will take the left child of the root and move to its right until it not become null
                        while(iterator.getRight() != null) {
                            iterator = iterator.getRight();
                        }

                        //now place left child of the right child of the root to right of iterator
                        iterator.setRight(nodeToBeRemovedRightLeft);
                        nodeToBeRemovedRightLeft.setParent(iterator);

                        //now call treapify to rotate the nodes until left child of right child of root reach it correct position
                        treapify(nodeToBeRemovedRightLeft);
                    }

                    //change pointer left,right and parent pointer of current node
                    nodeToBeRemovedRight.setLeft(nodeToBeRemoved.getLeft());
                    nodeToBeRemoved.getLeft().setParent(nodeToBeRemovedRight);
                    nodeToBeRemoved.setRight(null);
                    nodeToBeRemoved.setLeft(null);
                }else {
                    Node nodeToBeRemovedLeft = nodeToBeRemoved.getLeft();

                    //if right child of the left child of the root exist then remove it and place it in right subtree of root

                    if(nodeToBeRemovedLeft.getRight() != null) {
                        Node nodeToBeRemovedLeftRight = nodeToBeRemovedLeft.getRight();
                        Node iterator = nodeToBeRemoved.getRight();

                        nodeToBeRemovedLeft.setRight(null);

                        //will take the right child of left child of the root and move to its left until it not become null
                        while(iterator.getLeft() != null) {
                            iterator = iterator.getLeft();
                        }

                        //now place right child of the left child of the root to left of iterator
                        iterator.setLeft(nodeToBeRemovedLeftRight);
                        nodeToBeRemovedLeftRight.setParent(iterator);

                            //now call treapify to rotate the nodes until right child of left child of root reach it correct position
                        treapify(nodeToBeRemovedLeftRight);
                    }

                    //change pointer left,right and parent pointer of current node
                    nodeToBeRemovedLeft.setRight(nodeToBeRemoved.getRight());
                    nodeToBeRemoved.getRight().setParent(nodeToBeRemovedLeft);
                    nodeToBeRemoved.setLeft(null);
                    nodeToBeRemoved.setRight(null);
                }

                // change the root to nextRoot
                root = nextRoot;
                root.setParent(null);
            }
        }else {
            //case when node to be removed is not the root node of the treap

            //if both left and right child of the node exist
            if(nodeToBeRemoved.getLeft() != null && nodeToBeRemoved.getRight() != null) {       // if both exist

                Node nodeToBeRemovedParent = nodeToBeRemoved.getParent();

                //pick the child which has greater heapValue
                if(nodeToBeRemoved.getRight().getValue() > nodeToBeRemoved.getLeft().getValue()) {
                    Node nodeToBeRemovedRight = nodeToBeRemoved.getRight();

                    //if left child of the right child of the node exist then remove it and place it in left subtree of node
                    if(nodeToBeRemovedRight.getLeft() != null) {
                        Node nodeToBeRemovedRightLeft = nodeToBeRemovedRight.getLeft();
                        Node iterator = nodeToBeRemoved.getLeft();

                        nodeToBeRemovedRight.setLeft(null);

                        //will take the left child of the node and move to its right until it not become null
                        while(iterator.getRight() != null) {
                            iterator = iterator.getRight();
                        }

                        //now place left child of the right child of the node to right of iterator
                        iterator.setRight(nodeToBeRemovedRightLeft);
                        nodeToBeRemovedRightLeft.setParent(iterator);

                        //now call treapify to rotate the nodes until left child of right child of root reach it correct position
                        treapify(nodeToBeRemovedRightLeft);
                    }

                    //if the node is left child of parent , change left of grandParent to right child of node
                    if(nodeToBeRemovedParent.getLeft() != null && nodeToBeRemovedParent.getLeft().equals(nodeToBeRemoved)) {
                        nodeToBeRemovedParent.setLeft(nodeToBeRemovedRight);
                    }else if(nodeToBeRemovedParent.getRight() != null && nodeToBeRemovedParent.getRight().equals(nodeToBeRemoved)) {
                        //if the node is right child of parent , change right of grandParent to right child of node
                        nodeToBeRemovedParent.setRight(nodeToBeRemovedRight);
                    }

                    //change pointer left,right and parent pointer of current node
                    nodeToBeRemovedRight.setParent(nodeToBeRemovedParent);
                    nodeToBeRemoved.setParent(null);
                    nodeToBeRemovedRight.setLeft(nodeToBeRemoved.getLeft());
                    nodeToBeRemoved.getLeft().setParent(nodeToBeRemovedRight);
                    nodeToBeRemoved.setLeft(null);
                }else {
                    Node nodeToBeRemovedLeft = nodeToBeRemoved.getLeft();

                    //if right child of the left child of the node exist then remove it and place it in right subtree of node
                    if(nodeToBeRemovedLeft.getRight() != null) {
                        Node nodeToBeRemovedLeftRight = nodeToBeRemovedLeft.getRight();
                        Node iterator = nodeToBeRemoved.getRight();

                        nodeToBeRemovedLeft.setRight(null);

                        //will take the right child of left child of the node and move to its left until it not become null
                        while(iterator.getLeft() != null) {
                            iterator = iterator.getLeft();
                        }

                        //now place right child of the left child of the root to left of iterator
                        iterator.setLeft(nodeToBeRemovedLeftRight);
                        nodeToBeRemovedLeftRight.setParent(iterator);

                        //now call treapify to rotate the nodes until right child of left child of root reach it correct position
                        treapify(nodeToBeRemovedLeftRight);
                    }

                    //if the node is left child of parent , change left of grandParent to left child of node
                    if(nodeToBeRemovedParent.getLeft() != null && nodeToBeRemovedParent.getLeft().equals(nodeToBeRemoved)) {
                        nodeToBeRemovedParent.setLeft(nodeToBeRemovedLeft);
                    }else if(nodeToBeRemovedParent.getRight() != null && nodeToBeRemovedParent.getRight().equals(nodeToBeRemoved)){
                        //if the node is right child of parent , change left of grandParent to left child of node
                        nodeToBeRemovedParent.setRight(nodeToBeRemovedLeft);
                    }

                    //change pointer left,right and parent pointer of current node
                    nodeToBeRemovedLeft.setParent(nodeToBeRemovedParent);
                    nodeToBeRemoved.setParent(null);
                    nodeToBeRemovedLeft.setRight(nodeToBeRemoved.getRight());
                    nodeToBeRemoved.getRight().setParent(nodeToBeRemovedLeft);
                    nodeToBeRemoved.setRight(null);
                }

            }else if(nodeToBeRemoved.getRight() != null || nodeToBeRemoved.getLeft() != null) {
                //if either of the left or right child exist
                Node nodeToBeRemovedParent = nodeToBeRemoved.getParent();

                //if right child exist, move it in place of current node
                if(nodeToBeRemoved.getRight() != null) {
                    Node nodeToBeRemovedRight = nodeToBeRemoved.getRight();

                    //if current node is left child of its parent, change left pointer of it parent
                    if(nodeToBeRemovedParent.getLeft() != null && nodeToBeRemovedParent.getLeft().equals(nodeToBeRemoved)) {
                        nodeToBeRemovedParent.setLeft(nodeToBeRemovedRight);
                    }else if(nodeToBeRemovedParent.getRight() != null && nodeToBeRemovedParent.getRight().equals(nodeToBeRemoved)){
                        //if current node is right child of its parent, change right pointer of it parent
                        nodeToBeRemovedParent.setRight(nodeToBeRemovedRight);
                    }

                    nodeToBeRemovedRight.setParent(nodeToBeRemovedParent);
                    nodeToBeRemoved.setParent(null);
                    nodeToBeRemoved.setRight(null);
                }else {
                    //if left child exist, move it in place of current node
                    Node nodeToBeRemovedLeft = nodeToBeRemoved.getLeft();

                    //if current node is left child of its parent, change left pointer of it parent
                    if(nodeToBeRemovedParent.getLeft() != null && nodeToBeRemovedParent.getLeft().equals(nodeToBeRemoved)) {
                        nodeToBeRemovedParent.setLeft(nodeToBeRemovedLeft);
                    }else if(nodeToBeRemovedParent.getRight() != null && nodeToBeRemovedParent.getRight().equals(nodeToBeRemoved)){
                        //if current node is right child of its parent, change right pointer of it parent
                        nodeToBeRemovedParent.setRight(nodeToBeRemovedLeft);
                    }

                    nodeToBeRemovedLeft.setParent(nodeToBeRemovedParent);
                    nodeToBeRemoved.setParent(null);
                    nodeToBeRemoved.setLeft(null);
                }

            }else {
                //if node is the leaf node
                Node nodeToBeRemovedParent = nodeToBeRemoved.getParent();

                //change the parent left or right pointer by pointing it to null
                if(nodeToBeRemovedParent.getLeft() != null && nodeToBeRemovedParent.getLeft().equals(nodeToBeRemoved)) {
                    nodeToBeRemovedParent.setLeft(null);
                }else if(nodeToBeRemovedParent.getRight() != null && nodeToBeRemovedParent.getRight().equals(nodeToBeRemoved)){
                    nodeToBeRemovedParent.setRight(null);
                }

                //make parent of the node null
                nodeToBeRemoved.setParent(null);
            }
        }

        return root;
    }

    /*
       @Params :-
       node :- Root of Treap or searchTree
       newNode :- Node which is to be added

       recursive function to add element in binary search tree
    */
    protected Node addNodeInBST(Node node, Node newNode) {
        //reaches to leaf then return newNode
        if(node == null) {
            return newNode;
        }

        //if node key is greater than newNode key then move to left child of node
        if(customCompare(node.getKey(), newNode.getKey()) > 0) {
            node.setLeft(addNodeInBST(node.getLeft(), newNode));
            node.getLeft().setParent(node);
        }else {
            //if node key is lesser than newNode key then move to right child of node
            node.setRight(addNodeInBST(node.getRight(), newNode));
            node.getRight().setParent(node);
        }

        return node;
    }

    /*
       @Params :-
       node :- Root of Treap or searchTree
       ele :- Key which is to be searched

       recursive function to search element in binary search tree
    */
    protected Node searchInBST(Node node, String ele) {
        if(node == null) {
            return null;
        }

        //if node key equals passed key return node
        if(customCompare(node.getKey(), ele) == 0) {
            return node;
        }else if(customCompare(node.getKey(), ele) > 0) {       // if node key is greater than passed key , move to left child
            return searchInBST(node.getLeft(), ele);
        }else {                                                 // if node key is smaller than passed key , move to right child
            return searchInBST(node.getRight(), ele);
        }
    }

    /*
         compare string after converting it to lowercase
    **/
    protected int customCompare(String s1,String s2) {
        return s1.toLowerCase().compareTo(s2.toLowerCase());
    }
}
