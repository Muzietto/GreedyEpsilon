/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.table;

import java.util.Iterator;
import org.apache.commons.collections.buffer.CircularFifoBuffer;
import org.apache.commons.lang3.mutable.MutableDouble;

/**
 * Id we had generics here, that would be a CFB<Double>
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class ResultsBuffer extends CircularFifoBuffer {

    /* package */ MutableDouble _average = new MutableDouble(Double.NaN);

    public ResultsBuffer(Integer i) {
        super(i);
    }

    /**
     *
     * @param obj it's gonna be always a Double
     * @return
     */
    @Override
    public boolean add(Object obj) {
        boolean result = super.add(obj);
        _computeAverage();
        return result;
    }

    @Override
    public void clear() {
        super.clear();
        _average.setValue(Double.NaN);
    }

    private void _computeAverage() {
        Double result = new Double(0.0);
        Iterator itt = this.iterator();
        int counter = 0;
        while (itt.hasNext()) {
            Double dd = (Double) itt.next();
            if (counter == 0) {
                result = dd;
                counter++;
            } else {
                result = result * counter + dd;
                counter++;
                result = result / counter;
            }
        }
        _average.setValue(result);
    }

    public Double average() {
        return _average.doubleValue();
    }
}
