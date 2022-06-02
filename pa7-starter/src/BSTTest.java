import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.*;

public class BSTTest {
	
	/* TODO: Add your own tests */
	@Test
	public void test_basics() {
		BST testBST = new BST<>();
		testBST.put("key1","1");
		assertEquals("1",testBST.get("key1"));
		assertEquals(1,testBST.size());
		assertEquals(true,testBST.containsKey("key1"));
		assertEquals(false,testBST.containsKey("non-exist-key"));

		testBST.replace("key1","2");
		assertEquals("2",testBST.get("key1"));

		testBST.remove("key1");
		assertEquals(true,testBST.isEmpty());

	}

	@Test
	public void test_remove() {
		BST bst = new BST<>();
		bst.put("a",null);
		bst.put("b",null);
		bst.put("c",null);
		bst.put("d",null);

		bst.remove("c");
		System.out.println(bst.size);
		System.out.println(bst.keys().toString());
		assertEquals("[a, b, d]", bst.keys().toString());

	}
}
