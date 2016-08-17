import java.util.ArrayList;
import java.util.HashMap;

/** A ScopedMap is similar to a Map, but with nested scopes. */
public class ScopedMap<K, V> {
	private ArrayList<HashMap<K, V>> map;

	/**
	 * makes a ScopedMap that maps no keys to values and is set to the global
	 * scope (nesting level 0)
	 */
	public ScopedMap() {
		map = new ArrayList<HashMap<K, V>>();

	}

	/**
	 * sets the ScopedMap to a new scope nested inside the previous one; the
	 * nesting level increases by one
	 */
	public void enterScope() {
		map.add(new HashMap<K, V>());
	}

	/**
	 * exits from the current scope back to the containing one; the nesting
	 * level, which must have been positive, decreases by one
	 */
	public void exitScope() {
		if (map.size() > 0) {
			map.remove(map.size() - 1);
		} else {
			throw new IllegalArgumentException("There is no other level.");
		}
	}

	/**
	 * puts the key/value pair in at the current scope; if the key is already in
	 * at the current nesting level, the new value replaces the old one; neither
	 * the key nor the value may be null
	 */
	public void put(K key, V value) {
		if (key == null || value == null) {
			throw new IllegalArgumentException("At least one of the inputs are null");
		} else {
			HashMap<K, V> current = map.get(map.size() - 1);
			current.put(key, value);
			map.remove(map.size() - 1);
			map.add(current);
		}
	}

	/**
	 * gets the value corresponding to the key, at the innermost scope for which
	 * there is one; if there is none, returns null
	 */
	public V get(K key) {
		for (int i = map.size() - 1; i >= 0; i--) {
			if (map.get(i).containsKey(key)) {
				return map.get(i).get(key);
			}
		}
		return null;
	}

	/**
	 * returns true if the key has a value at the current scope (ignoring
	 * surrounding ones)
	 */
	public boolean isLocal(K key) {
		return map.get(map.size() - 1).containsKey(key);
	}

	/** returns the current nesting level */
	public int getNestingLevel() {
		return map.size();
	}
}
