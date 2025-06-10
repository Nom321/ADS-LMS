package by.it.group351052.nomerovskiy.lesson12;

import java.util.*;

public class MySplayMap implements NavigableMap<Integer, String> {
    private class Node {
        Integer key;
        String value;
        Node left, right;

        Node(Integer key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node root;
    private int size;

    public MySplayMap() {
        root = null;
        size = 0;
    }

    @Override
    public String put(Integer key, String value) {
        if (key == null) throw new NullPointerException();

        root = splay(root, key);

        if (root != null && root.key.equals(key)) {
            String oldValue = root.value;
            root.value = value;
            return oldValue;
        } else {
            Node newNode = new Node(key, value);
            if (root == null) {
                root = newNode;
            } else if (key < root.key) {
                newNode.right = root;
                newNode.left = root.left;
                root.left = null;
            } else {
                newNode.left = root;
                newNode.right = root.right;
                root.right = null;
            }
            root = newNode;
            size++;
            return null;
        }
    }

    @Override
    public String remove(Object key) {
        if (key == null || !(key instanceof Integer)) throw new NullPointerException("Key must be an Integer");

        Integer intKey = (Integer) key;
        root = splay(root, intKey);
        if (root == null || !root.key.equals(intKey)) {
            return null;
        }

        String value = root.value;
        if (root.left == null) {
            root = root.right;
        } else {
            Node rightSubtree = root.right;
            root = splay(root.left, intKey);
            root.right = rightSubtree;
        }
        size--;
        return value;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

    }


    private boolean containsValueRecursive(Node node, String value) {
        if (node == null) return false;
        if (node.value.equals(value)) return true;
        return containsValueRecursive(node.left, value) || containsValueRecursive(node.right, value);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        if (key == null || !(key instanceof Integer)) return false;

        Integer intKey = (Integer) key;
        root = splay(root, intKey);

        return root != null && root.key.equals(intKey);
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public String get(Object key) {
        if (key == null || !(key instanceof Integer)) {
            return null;
        }

        Integer intKey = (Integer) key;
        root = splay(root, intKey);

        if (root == null || !root.key.equals(intKey)) {
            return null;
        }
        return root.value;
    }
    @Override
    public Integer firstKey() {
        if (root == null) throw new NoSuchElementException();
        Node leftmost = root;
        while (leftmost.left != null) {
            leftmost = leftmost.left;
        }
        return leftmost.key;
    }

    @Override
    public Integer lastKey() {
        if (root == null) throw new NoSuchElementException();
        Node rightmost = root;
        while (rightmost.right != null) {
            rightmost = rightmost.right;
        }
        return rightmost.key;
    }

    @Override
    public Set<Integer> keySet() {
        return Set.of();
    }

    @Override
    public Collection<String> values() {
        return List.of();
    }

    @Override
    public Entry<Integer, String> lowerEntry(Integer key) {
        return null;
    }

    @Override
    public Integer lowerKey(Integer key) {
        Node current = root;
        Integer result = null;
        while (current != null) {
            if (key <= current.key) {
                current = current.left;
            } else {
                result = current.key;
                current = current.right;
            }
        }
        return result;
    }

    @Override
    public Entry<Integer, String> floorEntry(Integer key) {
        return null;
    }

    @Override
    public Integer floorKey(Integer key) {
        Node current = root;
        Integer result = null;
        while (current != null) {
            if (key < current.key) {
                current = current.left;
            } else if (key > current.key) {
                result = current.key;
                current = current.right;
            } else {
                return current.key;
            }
        }
        return result;
    }

    @Override
    public Entry<Integer, String> ceilingEntry(Integer key) {
        return null;
    }

    @Override
    public Integer ceilingKey(Integer key) {
        if (key == null) throw new NullPointerException();

        root = splay(root, key);
        if (root == null) {
            return null;
        }

        if (root.key >= key) {
            return root.key;
        }
        if (root.right != null) {
            Node current = root.right;
            while (current.left != null) {
                current = current.left;
            }
            return current.key;
        }

        return null;
    }

    @Override
    public Entry<Integer, String> higherEntry(Integer key) {
        return null;
    }

    @Override
    public Integer higherKey(Integer key) {
        Node current = root;
        Integer result = null;
        while (current != null) {
            if (key >= current.key) {
                current = current.right;
            } else {
                result = current.key;
                current = current.left;
            }
        }
        return result;
    }

    @Override
    public Entry<Integer, String> firstEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> lastEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> pollFirstEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> pollLastEntry() {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> descendingMap() {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> headMap(Integer toKey, boolean inclusive) {
        MySplayMap headMap = new MySplayMap();
        for (Entry<Integer, String> entry : entrySet()) {
            if (inclusive ? entry.getKey() <= toKey : entry.getKey() < toKey) {
                headMap.put(entry.getKey(), entry.getValue());
            }
        }
        return headMap;
    }

    @Override
    public NavigableMap<Integer, String> tailMap(Integer fromKey, boolean inclusive) {
        MySplayMap tailMap = new MySplayMap();
        for (Entry<Integer, String> entry : entrySet()) {
            if (inclusive ? entry.getKey() >= fromKey : entry.getKey() > fromKey) {
                tailMap.put(entry.getKey(), entry.getValue());
            }
        }
        return tailMap;
    }

    @Override
    public Comparator<? super Integer> comparator() {
        return null;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        return null;
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        if (toKey == null) throw new NullPointerException();

        MySplayMap result = new MySplayMap();
        addLessThan(root, toKey, result);
        return result;
    }

    private void addLessThan(Node node, Integer toKey, MySplayMap result) {
        if (node != null) {
            if (node.key < toKey) {
                result.put(node.key, node.value);
                addLessThan(node.left, toKey, result);
                addLessThan(node.right, toKey, result);
            } else {
                addLessThan(node.left, toKey, result);
            }
        }
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        if (fromKey == null) throw new NullPointerException();

        MySplayMap result = new MySplayMap();
        addGreaterThanOrEqual(root, fromKey, result);
        return result;
    }

    private void addGreaterThanOrEqual(Node node, Integer fromKey, MySplayMap result) {
        if (node != null) {
            if (node.key >= fromKey) {
                result.put(node.key, node.value);
                addGreaterThanOrEqual(node.left, fromKey, result);
                addGreaterThanOrEqual(node.right, fromKey, result);
            } else {
                addGreaterThanOrEqual(node.right, fromKey, result);
            }
        }
    }


    @Override
    public NavigableMap<Integer, String> subMap(Integer fromKey, boolean fromInclusive, Integer toKey, boolean toInclusive) {
        MySplayMap subMap = new MySplayMap();
        for (Entry<Integer, String> entry : entrySet()) {
            if ((fromInclusive ? entry.getKey() >= fromKey : entry.getKey() > fromKey) &&
                    (toInclusive ? entry.getKey() <= toKey : entry.getKey() < toKey)) {
                subMap.put(entry.getKey(), entry.getValue());
            }
        }
        return subMap;
    }

    @Override
    public NavigableSet<Integer> navigableKeySet() {
        NavigableSet<Integer> keySet = new TreeSet<>();
        for (Entry<Integer, String> entry : entrySet()) {
            keySet.add(entry.getKey());
        }
        return keySet;
    }

    @Override
    public NavigableSet<Integer> descendingKeySet() {
        NavigableSet<Integer> descendingSet = new TreeSet<>(Collections.reverseOrder());
        for (Entry<Integer, String> entry : entrySet()) {
            descendingSet.add(entry.getKey());
        }
        return descendingSet;
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        Set<Entry<Integer, String>> entrySet = new LinkedHashSet<>();
        inOrderEntryTraversal(root, entrySet);
        return entrySet;
    }

    private void inOrderEntryTraversal(Node node, Set<Entry<Integer, String>> entrySet) {
        if (node != null) {
            inOrderEntryTraversal(node.left, entrySet);
            entrySet.add(new AbstractMap.SimpleEntry<>(node.key, node.value));
            inOrderEntryTraversal(node.right, entrySet);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        inorderTraversal(root, sb);
        sb.append("}");
        return sb.toString();
    }

    private void inorderTraversal(Node node, StringBuilder sb) {
        if (node != null) {
            inorderTraversal(node.left, sb);
            if (sb.length() > 1) sb.append(", ");
            sb.append(node.key).append("=").append(node.value);
            inorderTraversal(node.right, sb);
        }
    }

    // Метод splay
    private Node splay(Node root, Integer key) {
        if (root == null) return null;
        if (key < root.key) {
            if (root.left == null) return root;
            if (key < root.left.key) {
                root.left.left = splay(root.left.left, key);
                root = rotateRight(root);
            } else if (key > root.left.key) {
                root.left.right = splay(root.left.right, key);
                if (root.left.right != null) root.left = rotateLeft(root.left);
            }
            return (root.left == null) ? root : rotateRight(root);
        } else if (key > root.key) {
            if (root.right == null) return root;
            if (key > root.right.key) {
                root.right.right = splay(root.right.right, key);
                root = rotateLeft(root);
            } else if (key < root.right.key) {
                root.right.left = splay(root.right.left, key);
                if (root.right.left != null) root.right = rotateRight(root.right);
            }
            return (root.right == null) ? root : rotateLeft(root);
        } else {
            return root;
        }
    }

    private Node rotateRight(Node node) {
        Node left = node.left;
        node.left = left.right;
        left.right = node;
        return left;
    }

    private Node rotateLeft(Node node) {
        Node right = node.right;
        node.right = right.left;
        right.left = node;
        return right;
    }
}
