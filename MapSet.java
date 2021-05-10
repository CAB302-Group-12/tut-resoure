package matrix;

import java.util.*;

public class MapSet<K, V> extends AbstractMap<K, HashSet<V>> implements Iterable<V> {
    private HashMap<K, HashSet<V>> storage = new HashMap<>();

    public void addValue(K key, V value) {
        // have we seen this key before?
        if (storage.containsKey(key)) {
            // add it to that hash set
            storage.get(key).add(value);
        } else {
            // create a new hash set for this key
            // because we haven't seen it yet
            HashSet<V> set = new HashSet<>();
            set.add(value);

            // add it to the lookup table
            storage.put(key, set);
        }
    }

    private class MapSetIterator implements Iterator<V> {
        private Iterator<Iterator<V>> keyIterator;
        private Iterator<V> valuesIterator;

        public MapSetIterator() {
            // create some temporary storage of all the keys which we iterate through
            ArrayList<HashSet<V>> tempList = new ArrayList<>();
            for (Entry<K, HashSet<V>> e : storage.entrySet()) {
                tempList.add(e.getValue());
            }

            /**
             * comparator
             *  left, right
             *    left < right => integer less than zero
             *    left == right => integer zero
             *    left > right => integer greater than zero
             *
             * compare a (left) to b (right)
             */
            tempList.sort((left, right) -> right.size() - left.size());

            // create iterators for each hash set
            ArrayList<Iterator<V>> iteratorList = new ArrayList<>();
            for (HashSet<V> i : tempList) {
                iteratorList.add(i.iterator());
            }

            // iterate through all of the different hash set's iterators
            keyIterator = iteratorList.iterator();
            valuesIterator = keyIterator.next();
        }

        @Override
        public boolean hasNext() {
            return keyIterator.hasNext() || valuesIterator.hasNext();
        }

        @Override
        public V next() {
            // are we at the end of this key
            if (!valuesIterator.hasNext()) {
                // move to the next key
                valuesIterator = keyIterator.next();
            }

            return valuesIterator.next();
        }
    }

    @Override
    public Iterator<V> iterator() {
        return new MapSetIterator();
    }

    @Override
    public Set<Entry<K, HashSet<V>>> entrySet() {
        return storage.entrySet();
    }
}
