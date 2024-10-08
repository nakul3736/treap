import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SearchTreeTest {
    @Test
    void testAdd() {
        SearchTree tree = new SearchTree();
        assertTrue(tree.add("c"));
        assertTrue(tree.add("d"));
        assertTrue(tree.add("a"));
        assertTrue(tree.add("b"));
        assertTrue(tree.audit());
        assertTrue(tree.add(""));
        assertFalse(tree.add(null));
        assertTrue(tree.audit());
    }

    @Test
    void testRemove() {
        SearchTree tree = new SearchTree();
        assertTrue(tree.add("c"));
        assertTrue(tree.add("d"));
        assertTrue(tree.add("a"));
        assertTrue(tree.add("b"));
        assertTrue(tree.audit());
        assertTrue(tree.remove("c"));
        assertTrue(tree.audit());
        assertFalse(tree.remove("c"));
        assertTrue(tree.audit());
        assertTrue(tree.remove("b"));
        assertTrue(tree.audit());
        assertTrue(tree.remove("d"));
        assertTrue(tree.audit());
        assertFalse(tree.remove("A"));
        assertTrue(tree.audit());
    }

    @Test
    void testSearch() {
        SearchTree tree = new SearchTree();
        assertTrue(tree.add("c"));
        assertTrue(tree.add("d"));
        assertTrue(tree.add("a"));
        assertTrue(tree.add("b"));
        assertTrue(tree.audit());

        assertTrue(tree.find("c"));
        assertTrue(tree.audit());
        assertTrue(tree.find("d"));
        assertTrue(tree.audit());
        assertTrue(tree.find("c"));
        assertTrue(tree.audit());
        assertTrue(tree.find("b"));
        assertTrue(tree.audit());
        assertTrue(tree.find("b"));
        assertTrue(tree.audit());
        assertTrue(tree.find("b"));
        assertTrue(tree.audit());
        assertTrue(tree.find("b"));
        assertTrue(tree.audit());

        assertFalse(tree.find("A"));
        assertTrue(tree.audit());
    }
}
