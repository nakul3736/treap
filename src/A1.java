public class A1 {
    public static void main(String[] args) {
        Treap treap = new Treap();
        SearchTree searchTree = new SearchTree();

        treap.add("Hot", 86);
        treap.add("True", 32);
        treap.add("Red", 33);
        treap.add("Cat", 30);
        treap.add("Wake", 41);
        treap.add("Jack", 60);
        treap.add("Fox", 50);
        treap.add("Bake", 12);
        treap.add("Lime", 77);
        treap.add("Bed", 72);
        treap.add("Good", 8);
        treap.add("Byy", 10);
        treap.add("Dog", 9);
        System.out.println(treap.audit());

        treap.remove("Bed");
        System.out.println(treap.audit());
        treap.remove("Lime");
        System.out.println(treap.audit());
        treap.changeOrder("Hot", 1);
        System.out.println(treap.audit());

        searchTree.add("a");
        searchTree.add("c");
        searchTree.find("a");
        searchTree.add("b");
        searchTree.find("c");
        searchTree.find("c");
        searchTree.find("c");
        searchTree.find("c");
        searchTree.find("b");
        searchTree.find("b");
        searchTree.find("b");
        searchTree.find("b");
        searchTree.find("b");
        searchTree.find("b");
        searchTree.audit();
    }
}
