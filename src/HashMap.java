private  final double LOADFACTOR = 0.75;
private final int CAPACITY = 16;

public class HashMap<T,U> {
    private Node<T, U>[] table;
    private int size = 0;
    private int kol_expansion;
    private final double loadFactor;

    private  class Node<T, U> {
        int hash;
        T key;
        U value;
        Node<T, U> next;

        public Node(int hash, T key, U value, Node<T, U> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public String toString() {
            return key + "=" + value;
        }
    }

    private int hash(T key) {
        return key == null ? 0 : Math.abs(key.hashCode()*31);
    }

    public HashMap() {
        this(CAPACITY,LOADFACTOR);
    }

    public HashMap(int capacity, double loadFactor) {
        this.table = new Node[capacity];
        this.loadFactor = loadFactor;
        this.kol_expansion = (int) (capacity * loadFactor);
    }

    public int size(int hash) {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(T key) {
        return get(key) != null;
    }


    public void put(T key, U value) {
        int hash = hash(key);
        int index= hash % table.length;
        Node<T,U> node = table[index];
        for (Node<T,U> current = node; current != null; current = current.next) {
            if (current.hash == node.hash && Objects.equals(current.key, key)) {
                current.value = node.value;
                return;
            }
        }
        Node<T, U> newNode = new Node<>(hash, key, value, node);
        table[index] = newNode;
        size++;
        if (size >= kol_expansion) {
            resize();
        }
    }

    public U getKey(T key) {
        int hash = hash(key);
        int index= hash % table.length;
        Node<T,U> node = table[index];
        for (Node<T,U> current = node; current != null; current = current.next) {
            if (current.hash == node.hash && Objects.equals(current.key, key)) {
                return current.value;
            }
        }
        return null;
    }

    public Node<T, U> get(T key) {
        int hash = hash(key);
        int index= hash % table.length;
        Node<T,U> node = table[index];
        for (Node<T,U> current = node; current != null; current = current.next) {
            if (current.hash == node.hash && Objects.equals(current.key, key)) {
                return current;
            }
        }
        return null;
    }

    public U  remove(T key) {
        int hash = Math.abs(key.hashCode()*31);
        int index= hash % table.length;
        Node<T,U> node = table[index];
        Node<T,U> prev = null;
        for (Node<T,U> current = node; current != null; current = current.next) {
            if (current.hash == node.hash && Objects.equals(current.key, key)) {
                if (prev == null)
                    table[index] = current.next;
                else
                    prev.next = current.next;

                size--;
                return current.value;
            }
            prev = current;
        }
        return null;
    }

    private void resize() {
        int newCapacity = table.length * 2;
        Node<T,U>[] newTable = (Node<T,U>[]) new Node[newCapacity];
        for (Node<T,U> node : table) {
            while (node != null) {
                Node<T, U> next = node.next;
                int index = node.hash % newCapacity;
                node.next = newTable[index];
                newTable[index] = node;
                node = next;
            }
        }
        table = newTable;
        kol_expansion = (int) (newCapacity * loadFactor);
    }
}

void main() {
    HashMap<String, Integer> map = new HashMap<>();
    for (int i = 0; i < 30; i++) {
        map.put("key" + i, i);
    }
    System.out.println("size=" + map.size);
    System.out.println("get(key15) -> " + map.get("key15"));
    System.out.println("remove(key15) -> " + map.remove("key15"));
    System.out.println("contains key15 -> " + map.containsKey("key15"));
    System.out.println("size=" + map.size);
}
