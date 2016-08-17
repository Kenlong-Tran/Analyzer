import static org.junit.Assert.*;

import org.junit.Test;


public class ScopedMapTest {
	
	@Test
	public void test() {
		ScopedMap<String, Integer> sm = new ScopedMap<String, Integer>();
		assertEquals(0, sm.getNestingLevel());
		sm.enterScope();
		assertEquals(1, sm.getNestingLevel());
		assertFalse(sm.isLocal("x"));
		sm.put("x", 1);
		assertEquals(null, sm.get("y"));
		assertFalse(sm.isLocal("y"));
		sm.put("y", 2);
		sm.enterScope();
		assertEquals(2, sm.getNestingLevel());
		assertEquals(1, sm.get("x").intValue());
		assertFalse(sm.isLocal("x"));
		sm.put("x", 3);
		assertEquals(3, sm.get("x").intValue());
		assertTrue(sm.isLocal("x"));
		assertEquals(3, sm.get("x").intValue());
		assertFalse(sm.isLocal("y"));
		sm.put("y", 4);
		sm.exitScope();
		assertEquals(1, sm.getNestingLevel());
		assertEquals(1, sm.get("x").intValue());
		sm.exitScope();
		assertEquals(0, sm.getNestingLevel());
	}

}