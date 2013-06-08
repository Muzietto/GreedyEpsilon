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

    public BufferedResultsList(List<ResultsBuffer> buffers) {
        Iterator itt = buffers.iterator();
        while (itt.hasNext()) {
            _averages.add(((ResultsBuffer) itt.next())._average);
        }
    }

    public int size() {
        return _averages.size();
    }

    public boolean isEmpty() {
        return _averages.isEmpty();
    }

    public boolean contains(Object o) {
        return _averages.contains(o);
    }

    public Iterator<Double> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object[] toArray() {
        return _averages.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return _averages.toArray(a);
    }

    public boolean add(Double e) {
        throw new UnsupportedOperationException("StoricizedResultsList is immutable");
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException("StoricizedResultsList is immutable");
    }

    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean addAll(Collection<? extends Double> c) {
        throw new UnsupportedOperationException("StoricizedResultsList is immutable");
    }

    public boolean addAll(int index, Collection<? extends Double> c) {
        throw new UnsupportedOperationException("StoricizedResultList is immutable");
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("StoricizedResultsList is immutable");
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("StoricizedResultsList is immutable");
    }

    public void clear() {
        throw new UnsupportedOperationException("StoricizedResultsList is immutable");
    }

    public Double get(int index) {
        return _averages.get(index).doubleValue();
    }

    public Double set(int index, Double element) {
        throw new UnsupportedOperationException("StoricizedResultsList is immutable");
    }

    public void add(int index, Double element) {
        throw new UnsupportedOperationException("StoricizedResultsList is immutable");
    }

    public Double remove(int index) {
        throw new UnsupportedOperationException("StoricizedResultsList is immutable");
    }

    public int indexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
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
