package by.it.group351052.nomerovskiy.lesson12;

import java.util.*;

public class MyRbMap implements SortedMap<Integer, String> {
    private Node root;
    private int size;

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private static class Node {
        int key;
        String value;
        Node left, right, parent;
        boolean color;

        Node(int key, String value) {
            this.key = key;
            this.value = value;
            this.color = RED;
        }
    }

    public MyRbMap() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (size > 0) {
            toStringHelper(root, sb);
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");
        return sb.toString();
    }

    private void toStringHelper(Node node, StringBuilder sb) {
        if (node != null) {
            toStringHelper(node.left, sb);
            sb.append(node.key).append("=").append(node.value).append(", ");
            toStringHelper(node.right, sb);
        }
    }

    public String put(Integer key, String value) {
        if (key == null || value == null) throw new NullPointerException();

        Node existingNode = getNode(key);
        String oldValue = null;

        if (existingNode != null) {
            oldValue = existingNode.value;
            existingNode.value = value;
        } else {
            Node newNode = new Node(key, value);
            if (root == null) {
                root = newNode;
                root.color = BLACK;
            } else {
                insertNode(root, newNode);
                fixAfterInsertion(newNode);
            }
            size++;
        }
        return oldValue;
    }

    @Override
    public String remove(Object key) {
        if (key == null) throw new NullPointerException();
        Node node = getNode((Integer) key);
        if (node == null) return null;

        String oldValue = node.value;
        deleteNode(node);
        size--;
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

    }

    private void insertNode(Node root, Node newNode) {
        if (newNode.key < root.key) {
            if (root.left == null) {
                root.left = newNode;
                newNode.parent = root;
            } else {
                insertNode(root.left, newNode);
            }
        } else {
            if (root.right == null) {
                root.right = newNode;
                newNode.parent = root;
            } else {
                insertNode(root.right, newNode);
            }
        }
    }

    private void fixAfterInsertion(Node node) {
        while (node != null && node != root && node.parent.color == RED) {
            if (node.parent == node.parent.parent.left) {
                Node uncle = node.parent.parent.right;
                if (uncle != null && uncle.color == RED) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        rotateLeft(node);
                    }
                    // Случай 3
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    rotateRight(node.parent.parent);
                }
            } else {
                Node uncle = node.parent.parent.left;
                if (uncle != null && uncle.color == RED) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rotateRight(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    rotateLeft(node.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }

    private void rotateLeft(Node node) {
        Node right = node.right;
        node.right = right.left;
        if (right.left != null) right.left.parent = node;
        right.parent = node.parent;
        if (node.parent == null) {
            root = right;
        } else if (node == node.parent.left) {
            node.parent.left = right;
        } else {
            node.parent.right = right;
        }
        right.left = node;
        node.parent = right;
    }

    private void rotateRight(Node node) {
        Node left = node.left;
        node.left = left.right;
        if (left.right != null) left.right.parent = node;
        left.parent = node.parent;
        if (node.parent == null) {
            root = left;
        } else if (node == node.parent.right) {
            node.parent.right = left;
        } else {
            node.parent.left = left;
        }
        left.right = node;
        node.parent = left;
    }

    public String remove(Integer key) {
        if (key == null) throw new NullPointerException();
        Node node = getNode(key);
        if (node == null) return null;

        String oldValue = node.value;
        deleteNode(node);
        size--;
        return oldValue;
    }

    private void deleteNode(Node node) {
        if (node.left != null && node.right != null) {
            Node successor = minimum(node.right);
            node.key = successor.key;
            node.value = successor.value;
            node = successor;
        }

        Node replacement = (node.left != null) ? node.left : node.right;

        if (replacement != null) {
            replacement.parent = node.parent;
            if (node.parent == null) {
                root = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else {
                node.parent.right = replacement;
            }
            if (node.color == BLACK) {
                fixAfterDeletion(replacement);
            }
        } else if (node.parent == null) {
            root = null;
        } else {
            if (node.color == BLACK) {
                fixAfterDeletion(node);
            }
            if (node.parent != null) {
                if (node == node.parent.left) {
                    node.parent.left = null;
                } else {
                    node.parent.right = null;
                }
            }
        }
    }

    private void fixAfterDeletion(Node node) {
        while (node != root && node.color == BLACK) {
            if (node == node.parent.left) {
                Node sibling = node.parent.right;
                if (sibling != null && sibling.color == RED) {
                    sibling.color = BLACK;
                    node.parent.color = RED;
                    rotateLeft(node.parent);
                    sibling = node.parent.right;
                }
                if (sibling != null && (sibling.left == null || sibling.left.color == BLACK) &&
                        (sibling.right == null || sibling.right.color == BLACK)) {
                    sibling.color = RED;
                    node = node.parent;
                } else {
                    if (sibling != null && (sibling.right == null || sibling.right.color == BLACK)) {
                        if (sibling.left != null) {
                            sibling.left.color = BLACK;
                        }
                        sibling.color = RED;
                        rotateRight(sibling);
                        sibling = node.parent.right;
                    }
                    if (sibling != null) {
                        sibling.color = node.parent.color;
                        node.parent.color = BLACK;
                        if (sibling.right != null) {
                            sibling.right.color = BLACK;
                        }
                        rotateLeft(node.parent);
                        node = root;
                    }
                }
            } else {
                Node sibling = node.parent.left;
                if (sibling != null && sibling.color == RED) {
                    sibling.color = BLACK;
                    node.parent.color = RED;
                    rotateRight(node.parent);
                    sibling = node.parent.left;
                }
                if (sibling != null && (sibling.right == null || sibling.right.color == BLACK) &&
                        (sibling.left == null || sibling.left.color == BLACK)) {
                    sibling.color = RED;
                    node = node.parent;
                } else {
                    if (sibling != null && (sibling.left == null || sibling.left.color == BLACK)) {
                        if (sibling.right != null) {
                            sibling.right.color = BLACK;
                        }
                        sibling.color = RED;
                        rotateLeft(sibling);
                        sibling = node.parent.left;
                    }
                    if (sibling != null) {
                        sibling.color = node.parent.color;
                        node.parent.color = BLACK;
                        if (sibling.left != null) {
                            sibling.left.color = BLACK;
                        }
                        rotateRight(node.parent);
                        node = root;
                    }
                }
            }
        }
        node.color = BLACK;
    }


    private Node minimum(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public String get(Integer key) {
        if (key == null) throw new NullPointerException();
        Node node = getNode(key);
        return (node != null) ? node.value : null;
    }

    private Node getNode(Integer key) {
        Node current = root;
        while (current != null) {
            if (key.equals(current.key)) {
                return current;
            } else if (key < current.key) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    public boolean containsKey(Integer key) {
        return getNode(key) != null;
    }

    public boolean containsValue(String value) {
        return values().contains(value);
    }

    public int size() {
        return size;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return key instanceof Integer && getNode((Integer) key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        if (value == null) return false;
        for (String val : values()) {
            if (val.equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String get(Object key) {
        return key instanceof Integer ? get((Integer) key) : null;
    }

    public Integer firstKey() {
        if (isEmpty()) throw new NoSuchElementException();
        Node node = minimum(root);
        return node.key;
    }

    public Integer lastKey() {
        if (isEmpty()) throw new NoSuchElementException();
        Node current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.key;
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        if (toKey == null) {
            throw new NullPointerException();
        }
        MyRbMap resultMap = new MyRbMap();
        fillHeadMap(root, toKey, resultMap);
        return resultMap;
    }

    private void fillHeadMap(Node node, Integer toKey, MyRbMap resultMap) {
        if (node == null) {
            return;
        }

        if (node.key < toKey) {
            resultMap.put(node.key, node.value);
            fillHeadMap(node.left, toKey, resultMap);
            fillHeadMap(node.right, toKey, resultMap);
        } else {
            fillHeadMap(node.left, toKey, resultMap);
        }
    }


    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        if (fromKey == null) {
            throw new NullPointerException();
        }
        MyRbMap resultMap = new MyRbMap();
        fillTailMap(root, fromKey, resultMap);
        return resultMap;
    }

    private void fillTailMap(Node node, Integer fromKey, MyRbMap resultMap) {
        if (node == null) {
            return;
        }

        if (node.key >= fromKey) {
            resultMap.put(node.key, node.value);
            fillTailMap(node.left, fromKey, resultMap);
            fillTailMap(node.right, fromKey, resultMap);
        } else {
            fillTailMap(node.right, fromKey, resultMap);
        }
    }

    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        return null;
    }

    public Comparator<? super Integer> comparator() {
        return null;
    }

    public Set<Map.Entry<Integer, String>> entrySet() {
        return null;
    }

    public Collection<String> values() {
        List<String> valuesList = new ArrayList<>();
        valuesHelper(root, valuesList);
        return valuesList;
    }

    private void valuesHelper(Node node, List<String> valuesList) {
        if (node != null) {
            valuesHelper(node.left, valuesList);
            valuesList.add(node.value);
            valuesHelper(node.right, valuesList);
        }
    }

    public Set<Integer> keySet() {
        Set<Integer> keys = new HashSet<>();
        keySetHelper(root, keys);
        return keys;
    }

    private void keySetHelper(Node node, Set<Integer> keys) {
        if (node != null) {
            keySetHelper(node.left, keys);
            keys.add(node.key);
            keySetHelper(node.right, keys);
        }
    }

    public static void main(String[] args) {
        MyRbMap map = new MyRbMap();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        System.out.println(map.toString());
        System.out.println(map.get(2));
        System.out.println(map.size());
        map.remove(2);
        System.out.println(map.toString());
    }
}
