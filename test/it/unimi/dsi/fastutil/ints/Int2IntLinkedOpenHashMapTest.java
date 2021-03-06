package it.unimi.dsi.fastutil.ints;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import org.junit.Test;

public class Int2IntLinkedOpenHashMapTest {
	@Test
	public void testContainsValue() {
		Int2IntLinkedOpenHashMap m = new Int2IntLinkedOpenHashMap(Hash.DEFAULT_INITIAL_SIZE);
		assertEquals(0, m.put(0, 2));
		assertEquals(0, m.put(1, 3));
		assertTrue(m.containsValue(2));
		assertTrue(m.containsValue(3));
		assertFalse(m.containsValue(4));
		assertTrue(m.containsKey(0));
		assertTrue(m.containsKey(1));
		assertFalse(m.containsKey(2));
	}

	@Test
	public void testFirstLast0() {
		Int2IntLinkedOpenHashMap s;

		s = new Int2IntLinkedOpenHashMap(Hash.DEFAULT_INITIAL_SIZE);
		for (int i = 1; i < 100; i++) {
			assertEquals(0, s.put(i, i));
		}
		for (int i = 1; i < 100; i++) {
			assertEquals(i, s.removeFirstInt());
		}
		assertTrue(s.isEmpty());

		s = new Int2IntLinkedOpenHashMap(Hash.DEFAULT_INITIAL_SIZE);
		for (int i = 0; i < 100; i++) {
			assertEquals(0, s.put(i, i));
		}
		for (int i = 100; i-- != 0; ) {
			assertEquals(i, s.removeLastInt());
		}
		assertTrue(s.isEmpty());

		s = new Int2IntLinkedOpenHashMap(Hash.DEFAULT_INITIAL_SIZE);
		for (int i = 100; i-- != 0; ) {
			assertEquals(0, s.put(i, i));
		}
		for (int i = 0; i < 100; i++) {
			assertEquals(i, s.removeLastInt());
		}
		assertTrue(s.isEmpty());

		s = new Int2IntLinkedOpenHashMap(Hash.DEFAULT_INITIAL_SIZE);
		for (int i = 100; i-- != 0; ) {
			assertEquals(0, s.put(i, i));
		}
		for (int i = 100; i-- != 0; ) {
			assertEquals(i, s.removeFirstInt());
		}
		assertTrue(s.isEmpty());
	}

	@Test
	public void testWrapAround() {
		Int2IntLinkedOpenHashMap m = new Int2IntLinkedOpenHashMap(4, .5f);
		assertEquals(8, m.n);
		// The following code inverts HashCommon.phiMix() and places strategically keys in slots 6, 7 and 0
		m.put(HashCommon.invMix(6), 0);
		m.put(HashCommon.invMix(7), 0);
		m.put(HashCommon.invMix(6 + 8), 0);
		assertNotEquals(0, m.key[0]);
		assertNotEquals(0, m.key[6]);
		assertNotEquals(0, m.key[7]);
		IntOpenHashSet keys = new IntOpenHashSet(m.keySet());
		IntIterator iterator = m.keySet().iterator();
		IntOpenHashSet t = new IntOpenHashSet();
		t.add(iterator.nextInt());
		t.add(iterator.nextInt());
		// Originally, this remove would move the entry in slot 0 in slot 6 and we would return the entry in 0 twice
		iterator.remove();
		t.add(iterator.nextInt());
		assertEquals(keys, t);
	}

	@Test
	public void testWrapAround2() {
		Int2IntLinkedOpenHashMap m = new Int2IntLinkedOpenHashMap(4, .75f);
		assertEquals(8, m.n);
		// The following code inverts HashCommon.phiMix() and places strategically keys in slots 4, 5, 6, 7 and 0
		m.put(HashCommon.invMix(4), 0);
		m.put(HashCommon.invMix(5), 0);
		m.put(HashCommon.invMix(4 + 8), 0);
		m.put(HashCommon.invMix(5 + 8), 0);
		m.put(HashCommon.invMix(4 + 16), 0);
		assertNotEquals(0, m.key[0]);
		assertNotEquals(0, m.key[4]);
		assertNotEquals(0, m.key[5]);
		assertNotEquals(0, m.key[6]);
		assertNotEquals(0, m.key[7]);
		//System.err.println(Arraym.toString(m.key));
		IntOpenHashSet keys = new IntOpenHashSet(m.keySet());
		IntIterator iterator = m.keySet().iterator();
		IntOpenHashSet t = new IntOpenHashSet();
		assertTrue(t.add(iterator.nextInt()));
		iterator.remove();
		//System.err.println(Arraym.toString(m.key));
		assertTrue(t.add(iterator.nextInt()));
		//System.err.println(Arraym.toString(m.key));
		// Originally, this remove would move the entry in slot 0 in slot 6 and we would return the entry in 0 twice
		assertTrue(t.add(iterator.nextInt()));
		//System.err.println(Arraym.toString(m.key));
		assertTrue(t.add(iterator.nextInt()));
		iterator.remove();
		//System.err.println(Arraym.toString(m.key));
		assertTrue(t.add(iterator.nextInt()));
		assertEquals(3, m.size());
		assertEquals(keys, t);
	}

	@Test
	public void testWrapAround3() {
		Int2IntLinkedOpenHashMap m = new Int2IntLinkedOpenHashMap(4, .75f);
		assertEquals(8, m.n);
		// The following code inverts HashCommon.phiMix() and places strategically keys in slots 5, 6, 7, 0 and 1
		m.put(HashCommon.invMix(5), 0);
		m.put(HashCommon.invMix(5 + 8), 0);
		m.put(HashCommon.invMix(5 + 16), 0);
		m.put(HashCommon.invMix(5 + 32), 0);
		m.put(HashCommon.invMix(5 + 64), 0);
		assertNotEquals(0, m.key[5]);
		assertNotEquals(0, m.key[6]);
		assertNotEquals(0, m.key[7]);
		assertNotEquals(0, m.key[0]);
		assertNotEquals(0, m.key[1]);
		//System.err.println(Arraym.toString(m.key));
		IntOpenHashSet keys = new IntOpenHashSet(m.keySet());
		IntIterator iterator = m.keySet().iterator();
		IntOpenHashSet t = new IntOpenHashSet();
		assertTrue(t.add(iterator.nextInt()));
		iterator.remove();
		//System.err.println(Arraym.toString(m.key));
		assertTrue(t.add(iterator.nextInt()));
		iterator.remove();
		//System.err.println(Arraym.toString(m.key));
		// Originally, this remove would move the entry in slot 0 in slot 6 and we would return the entry in 0 twice
		assertTrue(t.add(iterator.nextInt()));
		iterator.remove();
		//System.err.println(Arraym.toString(m.key));
		assertTrue(t.add(iterator.nextInt()));
		iterator.remove();
		//System.err.println(Arraym.toString(m.key));
		assertTrue(t.add(iterator.nextInt()));
		iterator.remove();
		assertEquals(0, m.size());
		assertEquals(keys, t);
	}
}
