/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.table.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import junit.framework.TestCase;
import net.faustinelli.greedyepsilon.table.ResultsBuffer;
import net.faustinelli.greedyepsilon.table.BufferedResultsList;
import org.junit.Test;

/**
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class BufferedTableStuffTest extends TestCase {

    @Test
    public void testResultsBuffer() {

        ResultsBuffer qqq = new ResultsBuffer(new Integer(2));
        assertEquals(new Double(Double.NaN), (Double) qqq.average());

        qqq.add(new Double(1.0));
        assertEquals(new Double(1.0), (Double) qqq.average());
        qqq.add(new Double(2.0));
        assertEquals(new Double(1.5), (Double) qqq.average());
        qqq.add(new Double(2.0));  // substitutes 1.0
        assertEquals(new Double(2.0), (Double) qqq.average());

        qqq.clear();
        assertEquals(new Double(Double.NaN), (Double) qqq.average());
    }

    @Test
    public void testBufferedResultsList() {

        ResultsBuffer b1 = new ResultsBuffer(2);
        ResultsBuffer b2 = new ResultsBuffer(2);
        b1.add(new Double(1.0));
        b1.add(new Double(2.0));
        b2.add(new Double(1.0));

        List<ResultsBuffer> buffers = new ArrayList<ResultsBuffer>();
        buffers.add(b1);
        buffers.add(b2);

        List<Double> brl = new BufferedResultsList(buffers);

        assertEquals(new Double(1.5), brl.get(0));
        assertEquals(new Double(1.0), brl.get(1));

        // test custom iterator
        assertEquals(new Double(1.5), Collections.max(brl));
        // test custom IndexOf
        assertEquals(0, brl.indexOf(Collections.max(brl)));

        brl.add(1, new Double(2.0));
        assertEquals(new Double(1.5), brl.get(1));

        brl.clear();
        assertEquals(new Double(Double.NaN), brl.get(1));
    }
}
