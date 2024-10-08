import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TreapTest {

    @Test
    void testAdd() {
        Treap treap = new Treap();

        assertTrue(treap.add("apple", 10));
        assertTrue(treap.add("banana", 5));
        assertTrue(treap.add("cherry", 15));
        assertTrue(treap.add("", 15));
        assertFalse(treap.add(null, 15));
        assertTrue(treap.audit());
    }

    @Test
    void testBuild() {
        String[] values = {"apple", "banana", "cherry", "date", "elderberry", "fig", "grape"};
        int[] priorities = {10, 5, 15, 3, 8, 12, 17};
        Treap treap = new Treap();
        assertTrue(treap.build(values, priorities));

        for (String value : values) {
            assertTrue(treap.find(value));
        }
        assertTrue(treap.audit());
    }

    @Test
    void testBuildWithDuplicate() {
        String[] values = {"apple", "banana", "apple", "date", "elderberry", "fig", "grape"};
        int[] priorities = {10, 5, 15, 3, 8, 12, 17};
        Treap treap = new Treap();
        assertFalse(treap.build(values, priorities));
    }

    @Test
    void callBuildOnAlreadyExistingTree() {
        Treap treap = new Treap();
        assertTrue(treap.add("apple", 10));
        assertTrue(treap.add("banana", 5));
        assertTrue(treap.add("cherry", 15));
        assertEquals(3, treap.size());
        assertTrue(treap.find("apple"));
        assertTrue(treap.build(new String[]{"a", "b", "c", "d", "e", "f", "i"}, new int[]{10, 5, 15, 3, 8, 12, 17}));
        assertEquals(7, treap.size());
        assertFalse(treap.find("apple"));
        assertTrue(treap.find("d"));
        assertEquals(7, treap.size());
    }

    @Test
    void testCaseSensitive() {
        Treap treap = new Treap();
        treap.add("a",10);
        treap.add("b", 15);
        assertTrue(treap.find("A"));
        assertFalse(treap.add("A", 100));
        assertTrue(treap.remove("A"));
        assertFalse(treap.find("a"));
    }

    @Test
    void callBuildTwiceInRow() {
        Treap treap = new Treap();
        assertTrue(treap.build(new String[]{"a", "b", "c", "d", "e", "f", "i"}, new int[]{10, 5, 15, 3, 8, 12, 17}));
        assertEquals(7, treap.size());
        assertTrue(treap.build(new String[]{"p","q","r","s","t","u"}, new int[]{10,5,15,3,8,12}));
        assertFalse(treap.find("a"));
        assertTrue(treap.find("u"));
        assertEquals(6, treap.size());
    }

    @Test
    void testRemove() {
        String[] values = {"Hot", "Bake", "Good", "Jake", "Bed", "True", "Fox", "Cat", "Lime", "Wake", "Red", "U", "By", "Dog"};
        int[] priorities = {86, 12, 8, 60, 72, 32, 50, 30, 77, 41, 33, 31, 10, 9};

        Treap treap = new Treap();
        treap.build(values, priorities);

        assertTrue(treap.remove("Bed"));
        assertTrue(treap.audit());
        assertTrue(treap.remove("Bake"));
        assertTrue(treap.audit());
        assertTrue(treap.remove("Lime"));
        assertTrue(treap.audit());
        assertTrue(treap.remove("Cat"));
        assertTrue(treap.audit());
        assertTrue(treap.remove("Good"));
        assertTrue(treap.audit());
        assertTrue(treap.remove("Hot"));
        assertTrue(treap.audit());
        assertTrue(treap.remove("Wake"));
        assertTrue(treap.audit());
        assertTrue(treap.remove("Jake"));
        assertTrue(treap.audit());
        assertTrue(treap.remove("True"));
        assertTrue(treap.audit());
        assertTrue(treap.remove("Fox"));
        assertTrue(treap.audit());
        assertFalse(treap.remove("Cat"));
        assertTrue(treap.audit());
        assertFalse(treap.remove("Good"));
        assertTrue(treap.audit());
        assertTrue(treap.remove("Red"));
        assertTrue(treap.audit());
        assertTrue(treap.remove("U"));
        assertTrue(treap.audit());
        assertTrue(treap.remove("By"));
        assertTrue(treap.audit());
        assertTrue(treap.remove("Dog"));
        assertTrue(treap.audit());

        assertFalse(treap.remove("Cat"));
        assertTrue(treap.audit());
        assertFalse(treap.remove("Good"));
        assertTrue(treap.audit());
    }

    @Test
    void testFind() {
        Treap treap = new Treap();
        treap.add("apple", 10);
        treap.add("banana", 5);
        treap.add("cherry", 15);

        assertTrue(treap.audit());
        assertTrue(treap.find("apple"));
        assertTrue(treap.find("banana"));
        assertTrue(treap.find("cherry"));
        assertFalse(treap.find("kiwi"));
    }

    @Test
    void testFindPath() {
        Treap treap = new Treap();
        treap.add("apple", 10);
        treap.add("banana", 5);
        treap.add("cherry", 15);

        assertTrue(treap.audit());
        List<String> path = treap.findPath("apple");
        assertEquals(List.of("apple", "cherry"), path);

        path = treap.findPath("banana");
        assertEquals(List.of("banana", "apple", "cherry"), path);

        path = treap.findPath("cherry");
        assertEquals(List.of("cherry"), path);
    }

    @Test
    void testSize() {
        Treap treap = new Treap();
        assertEquals(0, treap.size());

        assertTrue(treap.audit());
        treap.add("apple", 10);
        assertEquals(1, treap.size());

        treap.add("banana", 5);
        assertEquals(2, treap.size());

        treap.add("cherry", 15);
        assertEquals(3, treap.size());

        assertTrue(treap.audit());
        treap.remove("banana");
        assertEquals(2, treap.size());

        treap.remove("cherry1");
        assertEquals(2, treap.size());
    }

    @Test
    public void testFindWithNull() {
        // Input validation: Provide null as a parameter
        Treap treap = new Treap();

        assertFalse(treap.find(null));
    }

    @Test
    public void testFindEmptyStringWhenInTree() {
        // Boundary test: Search for an empty string when it is in the tree
        Treap treap = new Treap();

        treap.add("", 10);
        assertTrue(treap.find(""));
    }

    @Test
    public void testFindEmptyStringWhenNotInTree() {
        // Boundary test: Search for an empty string when it is not in the tree
        Treap treap = new Treap();

        assertFalse(treap.find(""));
    }

    @Test
    public void testFindSmallestElement() {
        // Boundary test: Search for the smallest element
        Treap treap = new Treap();

        treap.add("A", 10);
        treap.add("B", 20);
        assertTrue(treap.find("A"));
    }

    @Test
    public void testFindLargestElement() {
        // Boundary test: Search for the largest element
        Treap treap = new Treap();

        treap.add("A", 10);
        treap.add("B", 20);
        assertTrue(treap.find("B"));
    }

    @Test
    public void testFindInEmptyTree() {
        // Boundary test: Search in a tree with no data in it
        Treap treap = new Treap();

        assertFalse(treap.find("A"));
    }

    @Test
    public void testFindInTreeWithJustRoot() {
        // Control flow: Search in a tree with just the root
        Treap treap = new Treap();

        treap.add("root", 50);

        // Search for the root
        assertTrue(treap.find("root"));

        // Search for something that is not the root
        assertFalse(treap.find("non-root"));
    }

    @Test
    public void testFindInLeafNode() {
        // Control flow: Search for an element that is in a leaf node
        Treap treap = new Treap();

        treap.add("root", 50);
        treap.add("leaf", 30);
        assertTrue(treap.find("leaf"));
    }

    @Test
    public void testFindInMiddleNode() {
        // Control flow: Search for an element that is in a middle node
        Treap treap = new Treap();

        treap.add("root", 50);
        treap.add("middle", 40);
        treap.add("leaf", 30);
        assertTrue(treap.find("middle"));
    }

    @Test
    public void testFindAfterDeleting() {
        // Data flow: Find a value after deleting it
        Treap treap = new Treap();

        treap.add("deleteMe", 20);
        treap.remove("deleteMe");
        assertFalse(treap.find("deleteMe"));
    }

    @Test
    public void testFindAfterUsingAdd() {
        // Data flow: Find in a tree built with add()
        Treap treap = new Treap();

        treap.add("A", 20);
        treap.add("B", 30);
        assertTrue(treap.find("A"));
        assertFalse(treap.find("C"));
    }

    @Test
    public void testFindAfterUsingBuild() {
        // Data flow: Find in a tree built with build()
        Treap treap = new Treap();

        String[] keys = {"A", "B", "C"};
        int[] heapValues = {10, 20, 30};
        treap.build(keys, heapValues);
        assertTrue(treap.find("A"));
        assertTrue(treap.find("B"));
        assertFalse(treap.find("D"));
    }

    @Test
    public void testFindAfterFinding() {
        // Data flow: Find a value after just finding the value
        Treap treap = new Treap();

        treap.add("A", 20);
        assertTrue(treap.find("A"));  // Find first time
        assertTrue(treap.find("A"));  // Find second time
    }

    @Test
    public void testFindPath_NullParameter() {
        Treap treap = new Treap();
        assertNull(treap.findPath(null));  // Input validation for null
    }

    @Test
    public void testFindPath_EmptyStringInTree() {
        Treap treap = new Treap();
        treap.add("", 50);
        List<String> expected = Arrays.asList("");  // Path is just the root
        assertEquals(expected, treap.findPath(""));  // Boundary test: empty string in tree
    }

    @Test
    public void testFindPath_EmptyStringNotInTree() {
        Treap treap = new Treap();
        treap.add("A", 50);
        assertNull(treap.findPath(""));  // Boundary test: empty string not in tree
    }

    @Test
    public void testFindPath_AfterDeletion() {
        Treap treap = new Treap();
        treap.add("A", 50);
        treap.add("B", 40);
        treap.remove("B");
        assertNull(treap.findPath("B"));  // Find after deletion
    }

    @Test
    public void testFindPath_TreeBuiltWithAdd() {
        Treap treap = new Treap();
        treap.add("a", 50);
        treap.add("b", 40);
        treap.add("c", 30);
        List<String> expected = Arrays.asList("c", "b", "a");
        List<String> actual = treap.findPath("c");
        assertEquals(expected, actual);  // Path in tree built with add()
    }

    @Test
    public void testFindPath_TreeBuiltWithBuild() {
        Treap treap = new Treap();
        String[] keys = {"a", "b", "c"};
        int[] heapValues = {50, 40, 30};
        treap.build(keys, heapValues);
        List<String> expected = Arrays.asList("c", "b", "a");
        List<String> actual = treap.findPath("c");
        assertEquals(expected, actual);  // Path in tree built with build()
    }

    @Test
    public void testFindPath_AfterJustFinding() {
        Treap treap = new Treap();
        treap.add("a", 50);
        treap.add("b", 40);
        treap.add("c", 30);
        treap.find("c");
        List<String> expected = Arrays.asList("c", "b", "a");
        List<String> actual = treap.findPath("c");
        assertEquals(expected, actual);  // Path after finding
    }

//    -----------------------------------------------------------------------------------------------------------------

    @Test
    public void testFindPathForRoot() {
        // Test for root node (A)
        Treap treap = new Treap();
        treap.add("b", 20);
        treap.add("a", 30); // Higher heap value, becomes root due to heap property
        treap.add("c", 10);
        treap.add("d", 5);
        treap.add("e", 25);
        treap.add("f", 15);
        List<String> path = treap.findPath("a");
        assertEquals(List.of("a"), path);
    }

    @Test
    public void testFindPathForLeftChild() {
        // Test for left child of the root (B)
        Treap treap = new Treap();
        treap.add("b", 20);
        treap.add("a", 30); // Higher heap value, becomes root due to heap property
        treap.add("c", 10);
        treap.add("d", 5);
        treap.add("e", 25);
        treap.add("f", 15);
        List<String> path = treap.findPath("b");
        assertEquals(List.of("b","e", "a"), path);  // B -> A
    }

    @Test
    public void testFindPathForRightChild() {
        // Test for right child of the root (E)
        Treap treap = new Treap();
        treap.add("b", 20);
        treap.add("a", 30); // Higher heap value, becomes root due to heap property
        treap.add("c", 10);
        treap.add("d", 5);
        treap.add("e", 25);
        treap.add("f", 15);
        List<String> path = treap.findPath("e");
        assertEquals(List.of("e", "a"), path);  // E -> A
    }

    @Test
    public void testFindPathForLeafNodeD() {
        // Test for leaf node (D)
        Treap treap = new Treap();
        treap.add("b", 20);
        treap.add("a", 30); // Higher heap value, becomes root due to heap property
        treap.add("c", 10);
        treap.add("d", 5);
        treap.add("e", 25);
        treap.add("f", 15);
        List<String> path = treap.findPath("d");
        assertEquals(List.of("d","c", "b","e", "a"), path);  // D -> B -> A
    }

    @Test
    public void testFindPathForLeafNodeC() {
        // Test for another leaf node (C)
        Treap treap = new Treap();
        treap.add("b", 20);
        treap.add("a", 30); // Higher heap value, becomes root due to heap property
        treap.add("c", 10);
        treap.add("d", 5);
        treap.add("e", 25);
        treap.add("f", 15);
        List<String> path = treap.findPath("c");
        assertEquals(List.of("c", "b", "e","a"), path);  // C -> B -> A
    }

    @Test
    public void testFindPathForNonExistentNode() {
        // Test for non-existent node (Z)
        Treap treap = new Treap();
        treap.add("b", 20);
        treap.add("a", 30); // Higher heap value, becomes root due to heap property
        treap.add("c", 10);
        treap.add("d", 5);
        treap.add("e", 25);
        treap.add("f", 15);
        List<String> path = treap.findPath("z");
        assertNull(path);  // Z not found, should return null
    }

    @Test
    public void testFindPathAfterDeletion() {
        // Test after deleting a node (remove D and test findPath again)
        Treap treap = new Treap();
        treap.add("b", 20);
        treap.add("a", 30); // Higher heap value, becomes root due to heap property
        treap.add("c", 10);
        treap.add("d", 5);
        treap.add("e", 25);
        treap.add("f", 15);
        treap.remove("d");
        List<String> path = treap.findPath("d");
        assertNull(path);  // D should no longer be found
    }

    @Test
    public void testFindPathAfterDeletionForOtherNode() {
        // Test for a node after deletion (C should still have the same path)
        Treap treap = new Treap();
        treap.add("b", 20);
        treap.add("a", 30); // Higher heap value, becomes root due to heap property
        treap.add("c", 10);
        treap.add("d", 5);
        treap.add("e", 25);
        treap.add("f", 15);
        treap.remove("d");
        List<String> path = treap.findPath("c");
        assertEquals(List.of("c", "b", "e","a"), path);
    }

    /*-------------size ----------------------------*/

    @Test
    void testSize_EmptyTree() {
        Treap emptyTreap = new Treap();
        assertEquals(0, emptyTreap.size(), "Size of an empty tree should be 0");
    }

    @Test
    void testSize_RootNode() {
        Treap treap = new Treap();
        // Add initial elements for testing size after add
        treap.add("b", 20);

        assertEquals(1, treap.size(), "Size of a tree with just a root node should be 1 if only root is added");
    }

    @Test
    void testSize_MultipleLevels() {
        // Ensure the size reflects all nodes added
        Treap treap = new Treap();
        // Add initial elements for testing size after add
        treap.add("b", 20);
        treap.add("a", 30);
        treap.add("c", 10);
        treap.add("d", 5);
        treap.add("e", 25);
        treap.add("f", 15);
        assertEquals(6, treap.size(), "Size of the tree with multiple levels of nodes should be 6");
    }

    @Test
    void testSize_AfterAddingValue() {
        Treap treap = new Treap();
        // Add initial elements for testing size after add
        treap.add("b", 20);
        treap.add("a", 30);
        treap.add("c", 10);
        treap.add("d", 5);
        treap.add("e", 25);
        treap.add("f", 15);
        assertEquals(6, treap.size(), "Size of the tree with multiple levels of nodes should be 6");
        treap.add("g", 35);
        assertEquals(7, treap.size(), "Size after adding a new value should increase by 1");
    }

    @Test
    void testSize_AfterBuildingTree() {
        String[] values = {"apple", "banana", "cherry", "date", "elderberry", "fig", "grape"};
        int[] priorities = {10, 5, 15, 3, 8, 12, 17};
        Treap treap = new Treap();
        assertTrue(treap.build(values, priorities));
        assertEquals(7, treap.size());
    }

    @Test
    void testSize_AfterDeletingValue() {
        Treap treap = new Treap();
        // Add initial elements for testing size after add
        treap.add("b", 20);
        treap.add("a", 30);
        treap.add("c", 10);
        treap.add("d", 5);
        treap.add("e", 25);
        treap.add("f", 15);
        assertEquals(6, treap.size(), "Size of the tree with multiple levels of nodes should be 6");
        treap.remove("a");
        assertEquals(5, treap.size(), "Size after deleting a value should decrease by 1");
    }

    @Test
    void testSize_AfterFindingValue() {
        Treap treap = new Treap();
        // Add initial elements for testing size after add
        treap.add("b", 20);
        treap.add("a", 30);
        treap.add("c", 10);
        treap.add("d", 5);
        treap.add("e", 25);
        treap.add("f", 15);
        assertEquals(6, treap.size(), "Size of the tree with multiple levels of nodes should be 6");
        treap.find("b"); // Finding a value does not change the size
        assertEquals(6, treap.size(), "Size after finding a value should remain the same");
    }

    @Test
    void testSize_AfterChangingValue() {
        Treap treap = new Treap();
        // Add initial elements for testing size after add
        treap.add("b", 20);
        treap.add("a", 30);
        treap.add("c", 10);
        treap.add("d", 5);
        treap.add("e", 25);
        treap.add("f", 15);
        assertEquals(6, treap.size(), "Size of the tree with multiple levels of nodes should be 6");
        treap.changeOrder("b", 25); // Change should not affect size
        assertEquals(6, treap.size(), "Size after changing a value should remain the same");
    }

}