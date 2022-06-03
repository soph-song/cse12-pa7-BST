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
	V value;

	BST() {
		this.root = null;
		this.size = 0;
		this.value = null;
	}

	BST(V value) {
		this.root = null;
		this.value = value;
		this.size = 0;
		
	}

	
	/**
	 * Adds the specified key, value pair to this DefaultMap
	 * Note: duplicate keys are not allowed
	 * 
	 * @return true if the key value pair was added to this DefaultMap
	 * @throws IllegalArgumentException if the key is null
	 */
	
	@Override
	public boolean put(K key, V value) throws IllegalArgumentException {   //the method doesn't successfully add the node
		// TODO Auto-generated method stub
		//Boolean result = false;
		if(key == null) {
			throw new IllegalArgumentException("key is null");
		}
		
	
		Node<K,V> cur = this.root;
		
		if(cur == null) {
			Node<K,V> node = new Node<K, V>(key, value, null, null);
			root = node;
			size++;
			return true;
		}
		
	//	Node<K,V> newNode = this.root;
		
		
		  while(cur != null) {
			if(key.compareTo(cur.key) > 0) {
				if(cur.right == null) {  
					cur.right = new Node<K,V>(key, value, null, null);
					size++;
					break;
				}
	
				else {
					cur = cur.right;
					continue;
				}
			}
			
			
			if(key.compareTo(cur.key) < 0) {
				if(cur.left == null) {      //add the node if the right is null 
					cur.left = new Node<K,V>(key, value, null, null);
					size++;
					break;
				}
				
				else {
					cur = cur.left; 
				}
			}
			
			else {
				return false;
			}
			
		 }
	
		
		return true;
	}

	
	/**
	 * Replaces the value that maps to the key if it is present
	 * @param key The key whose mapped value is being replaced
	 * @param newValue The value to replace the existing value with
	 * @return true if the key was in this DefaultMap
	 * @throws IllegalArgumentException if the key is null
	 */
	
	@Override
	public boolean replace(K key, V newValue) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(key == null) {
			throw new IllegalArgumentException("key is null");
		}
		
	//	Node<K,V> newNode = new Node<K, V>(key, newValue, null,null);
	//	Node<K,V> cur = this.root;
		
		if(!containsKey(key)) {    //if the key isn't present, add to the map
			this.put(key, newValue);
			return false;
		}
		
		if(containsKey(key)) {
			if(key.equals(root.key)) {
				root.value = newValue;
				return true; 
			}
			
			if(key.compareTo(root.key) > 0) {
				if(root.right == null) {  
					return false;
				}
				
				else {
					root = root.right;
					this.replace(key, newValue);
				}
			}
			
			if(key.compareTo(root.key) < 0) {
				if(root.left == null) {  
					return false;
				}
				
				else {
					root = root.left;
					this.replace(key, newValue);
				}
			}
		}
		return false;
	}
	
	/**
	 * Remove the entry corresponding to the given key
	 * 
	 * @return true if an entry for the given key was removed
	 * @throws IllegalArgumentException if the key is null
	 */
	
	private Node<K, V> nodeWithMinimumKey(Node<K, V> root){ 
        while (root.left != null){ 
            root = root.left; 
        } 
        return root; 
    }
	
	private Node<K, V> removeHelper(Node<K, V> node, K key) throws IllegalArgumentException{
		// If tree is empty
		if (node == null) { return node; }
  
		if (node.key.compareTo(key) > 0) {
			
			node.left = removeHelper(node.left, key);
			if (node.left == null) {
				return null;
			}
			if (node.left.key.compareTo(key)==0) {
				node.left = null;
			}
			return node.left;
		} 
		
		else if (node.key.compareTo(key) < 0){
			  node.right = removeHelper(node.right, key);
			  if (node.right == null) {
				  return null;
			  }
			  if (node.right.key.compareTo(key)==0) {
				  node.right = null;
			  }
			  return node.right;
		}
		// node has the key we're looking to remove
		else {
			if (size == 1) {
				this.root = null;
			}

			// Case: no children
			if (node.left == null && node.right == null) {
				if (node != this.root) {
					return null;
				}
				else {
					this.root = null;
					return null;
				}

			}
			// Case: node with two children
			else if (node.left != null && node.right != null) {
				// Get minimum from right subtree, then remove it
				Node<K, V> succesor = nodeWithMinimumKey(node.right);
				K Succkey = succesor.key;
				V Succvalue = succesor.value;
				// Remove successor
				node.right = removeHelper(node, succesor.key);		
				//update the node with succesor info
				node.key = Succkey;
				node.value = Succvalue;		
			}

			//Cse: one right child
			else if (node.left == null){
				if (node != this.root) {
					return node.right;
				}
				else {
					this.root = node.right;
					return root;
				}

			}
			//Case: one left child
			else if (node.right == null){ 
				if (node != this.root) {
					return node.left;
				}
				else {
					this.root = node.left;
					return root;
				}
			}


			}
			return node;
		}

	

	@Override
	public boolean remove(K key) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(key == null) {
			throw new IllegalArgumentException("key is null");
		}
		
		
	//	Node<K,V> cur = this.root;
		if (this.root == null) {
			return false;
		}

		if(containsKey(key)) {
			root = this.removeHelper(root, key);
			size--;
			return true;
		}
		
		return false;
		
		/*
        int initialsize = this.keys().size();
        this.removeHelper(this.root, key);
        size--;
        if(initialsize == this.keys().size()) {
        	return false;
        }
	
		*/
		//return true;
	}
	
	
	/**
	 * Adds the key, value pair to this DefaultMap if it is not present,
	 * otherwise, replaces the value with the given value
	 * @throws IllegalArgumentException if the key is null
	 */

	@Override
	public void set(K key, V value) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(key == null) {
			throw new IllegalArgumentException("key is null");
		}
		
		Node<K,V> cur = this.root;
	
		if(cur == null) {
			this.put(key, value);
		}
		
		else {
		
		if(key.compareTo(cur.key) > 0) {
			cur = cur.right;
		//	this.set(key, value);
		}
		
		if(key.compareTo(cur.key) < 0) {
			cur = cur.left;
		//	this.set(key, value);
		}
		
		else {
			cur.value = value;
		}
		
		}
	}
	
	
	/**
	 * @return the value corresponding to the specified key
	 * @throws IllegalArgumentException if the key is null
	 */

	@Override
	public V get(K key) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(key == null) {
			throw new IllegalArgumentException("key is null");
		}
		
		Node<K,V> cur = this.root;

		
		 while(cur != null) {

				if(key.compareTo(cur.key) > 0) {


					if(cur.right == null) {    
						break;
					}
					
					else {
						cur = cur.right;
					}

					
				}
				
				else if(key.compareTo(cur.key) < 0) {
					if(cur.left == null) {
						break;
					}
					
					else {
						cur = cur.left;
					}
				}
			
				else {
					return cur.value;
				}

		 }
		return null;
	}

	/**
	 * 
	 * @return The number of (key, value) pairs in this DefaultMap
	 */
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
	
		return this.size;
		
	}

	/**
	 * 
	 * @return true iff this.size() == 0 is true
	 */ 
	
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		if(this.size() == 0) {
			return true;
		}
		
		return false;
	}

	private boolean search(Node root,K key) {
        if (root == null) {
            return false;
        } else if (root.key.compareTo(key) == 0) {
            return true;
        } else if (root.key.compareTo(key)> 0) {
            return search(root.left, key);
        }
        return search(root.right, key);
    }
	
	/**
	 * @return true if the specified key is in this DefaultMap
	 * @throws IllegalArgumentException if the key is null
	 */
	
	@Override
	public boolean containsKey(K key) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(key == null) {
			throw new IllegalArgumentException("key is null");
		}
		boolean inBST = search(this.root,key);
		return inBST;
	}

	// Keys must be in ascending sorted order
	// You CANNOT use Collections.sort() or any other sorting implementations
	// You must do inorder traversal of the tree
	
	/**
	 * 
	 * @return an array containing the keys of this DefaultMap. If this DefaultMap is 
	 * empty, returns array of length zero. 
	 */
	
	
	private void inOrder(Node<K,V> node, List l){
		  if(node !=null) {
			inOrder(node.left,l);
			l.add(node.key);
			inOrder(node.right,l);
		  }

	}
	
	@Override
	public List<K> keys() {
		// TODO Auto-generated method stub
		
		ArrayList<K> list = new ArrayList<>();
		inOrder(this.root,list);
	
		
		return list;
	}
	private static class Node<K extends Comparable<? super K>, V> 
								implements DefaultMap.Entry<K, V> {
		/* 
		 * TODO: Add instance variables
		 * 
		 */
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
			// TODO Auto-generated method stub
			return key;
		}

		@Override
		public V getValue() {
			// TODO Auto-generated method stub
			return value;
		}

		@Override
		public void setValue(V value) {
			// TODO Auto-generated method stub
			this.value = value;
			
		}
		
		
	}
	 
}