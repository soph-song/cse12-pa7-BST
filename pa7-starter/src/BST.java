import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @param <K> The type of the keys of this BST. They need to be comparable by nature of the BST
 * "K extends Comparable" means that BST will only compile with classes that implement Comparable
 * interface. This is because our BST sorts entries by key. Therefore keys must be comparable.
 * @param <V> The type of the values of this BST. 
 */
public class BST<K extends Comparable<? super K>, V> implements DefaultMap<K, V> {
	Node<K,V> root;
	int size;
	List<K> keys = new ArrayList<>();

	public BST() {
		this.root = null;
		size = 0;
	}

	public BST(Node<K,V> root) {
		this.root = root;
		size = 1;

	}

	public Node add(Node<K,V> root, K key, V value) {
		//if we find an empty slot, add the node
		if (root == null) {
			root = new Node<K,V>(key, value, null, null);
			size += 1;
			return root;
		}
		//Check if root key is > new key
		else if (root.key.compareTo(key) > 0) {
			root.left = add(root.left, key, value);
		}
		// if keys are the same then update value
		else if (root.key.compareTo(key) == 0) {
			root.setValue(value);
			return root;
		}
		//if root key is less than new key
		else if (root.key.compareTo(key) < 0) {
			root.right = add(root.right, key, value);
		}
		return root;
	}

	@Override
	public boolean put(K key, V value) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		//Check for duplicate keys
		if (containsKey(key) == true) {
			return false;
		}
		Node nodeAdded = add(root, key, value);
		
		return nodeAdded.getKey() == key;
	}

	@Override
	public boolean replace(K key, V newValue) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		//If key not present return and exit
		if (containsKey(key)== false) {
			return false;
		}
		Node nodeReplced = add(root,key,newValue);

		return nodeReplced.getValue()==newValue;
	}

	public Node remove(Node root, K key) {
		if (root == null) {
			return null;
		}
		//if node is on the left
		else if (root.key.compareTo(key) > 0) {
			root.left = remove(root.left, key);
		}
		//if node on right
		else if (root.key.compareTo(key) < 0) {
			root.right = remove(root.right, key);
		}
		//if we found our node
		else if (root.key.compareTo(key) == 0) {

			size -= 1;

			//If it has both child
			if (root.left != null && root.right!= null) {
				Node maxNode = findMaxNode(root.left);
				root.setValue(maxNode.getValue());
				root.key = maxNode.key;
				root.left = remove(root.left, (K) maxNode.getKey());
				return root;
			}
			
			//If it has only left child
			else if (root.left != null) {
				return root.left;
			}
			//If it has only right child
			else if (root.right != null) {
				return root.left;
			}
			//If it has no child
			else if (root.left == null && root.right == null) {
				return null;
			}
			
		}
		return root;
	}
	public Node findMaxNode(Node root) {
		if (root.right != null) {
			return findMaxNode(root.right);
		}
		else {
			return root;
		}
	}

	@Override
	public boolean remove(K key) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		if (containsKey(key) == false) {
			return false;
		}
		remove(root, key);

		return containsKey(key);
	}

	@Override
	public void set(K key, V value) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		if (containsKey(key) == true) {
			replace(key, value);
		}
		else if (containsKey(key) == false) {
			put(key, value);
		}
		
	}

	public Node findNode(Node root, K key) {
		if (root == null) {
			return null;
		}
		else if (root.key.equals(key)) {
			return root;
		}
		else if (root.key.compareTo(key) > 0) {
			findNode(root.left,key);
		}
		else if (root.key.compareTo(key) < 0) {
			findNode(root.right,key);
		}
		return null;
	}

	@Override
	public V get(K key) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		if (containsKey(key) == false) {
			return null;
		}
		Node target = findNode(root,key);
		V value = (V) target.getValue();
		return value;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	@Override
	public boolean containsKey(K key) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		return findNode(root,key) != null;
	}


	public Node addKEY(Node<K,V> root) {
		//if we find a leaf
		if (root.right == null && root.left == null) {
			keys.add(root.getKey());
			return root;
		}
		//if root has left child
		if (root.left != null) {
			root.left = addKEY(root.left);
			keys.add(root.getKey());
		}

		//if root has right child
		if (root.right != null) {
			root.right = addKEY(root.right);
		}
		return root;
	}
	// Keys must be in ascending sorted order
	// You CANNOT use Collections.sort() or any other sorting implementations
	// You must do inorder traversal of the tree
	@Override
	public List<K> keys() {
		addKEY(root);
		return this.keys;
	}
	
	private static class Node<K extends Comparable<? super K>, V> 
								implements DefaultMap.Entry<K, V> {
		K key;
		V value;
		Node<K,V> left;
		Node<K,V> right;

		public Node(K key, V value, Node<K,V> left, Node<K,V> right) {
			this.key = key;
			this.value = value;
			this.left = left;
			this.right = right;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public void setValue(V value) {
			this.value = value;
			
		}
		
		
	}
	 
}