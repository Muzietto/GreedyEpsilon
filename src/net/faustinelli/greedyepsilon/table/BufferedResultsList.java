/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.apache.commons.lang3.mutable.MutableDouble;

/**
 * Immutable wrapper for a List<MutableDouble>
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class BufferedResultsList implements List<Double> {

    private final List<MutableDouble> _averages = new ArrayList<MutableDouble>();
    private final List<ResultsBuffer> _buffers;

    public BufferedResultsList(List<ResultsBuffer> buffers) {
        Iterator itt = buffers.iterator();
        while (itt.hasNext()) {
            _averages.add(((ResultsBuffer) itt.next())._average);
        }
        _buffers = buffers;
    }

    public Double get(int index) {
        return _averages.get(index).doubleValue();
    }

    /**
     * NB: set acts on the buffers, not on the own values
     * @param index
     * @param element
     * @return
     */
    public Double set(int index, Double element) {
        if (_buffers.get(index).add(element)) {
            return element;
        } else {
            return null;
        }
    }

    public int size() {
        return _averages.size();
    }

    /**
     *  Clear each single buffer, not yourself
     */
    public void clear() {
        for (ResultsBuffer buf : _buffers) {
            buf.clear();
        }
    }

    /**
    // add to the buffer, not to yourself
     */
    public void add(int index, Double element) {
        _buffers.get(index).add(element);
    }

    /**
     * We iterate among the averages!
     * @return
     */
    public Iterator<Double> iterator() {
        return new Iterator<Double>() {

            private Iterator aveIte = _averages.iterator();

            public boolean hasNext() {
                return aveIte.hasNext();
            }

            public Double next() {
                return ((MutableDouble) aveIte.next()).doubleValue();
            }

            public void remove() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    public boolean isEmpty() {
        return _averages.isEmpty();
    }

    public int indexOf(Object o) {
        return _averages.indexOf(new MutableDouble((Double) o));
    }

    public int lastIndexOf(Object o) {
        return _averages.lastIndexOf(new MutableDouble((Double) o));
    }

    public Object[] toArray() {
        return _averages.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return _averages.toArray(a);
    }

    public boolean add(Double e) {
        throw new UnsupportedOperationException("BufferedResultsList is immutable");
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException("BufferedResultsList is immutable");
    }

    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean addAll(Collection<? extends Double> c) {
        throw new UnsupportedOperationException("BufferedResultsList is immutable");
    }

    public boolean addAll(int index, Collection<? extends Double> c) {
        throw new UnsupportedOperationException("StoricizedResultList is immutable");
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("BufferedResultsList is immutable");
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("BufferedResultsList is immutable");
    }

    public Double remove(int index) {
        throw new UnsupportedOperationException("BufferedResultsList is immutable");
    }

    public ListIterator<Double> listIterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ListIterator<Double> listIterator(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Double> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
