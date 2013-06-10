/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.components.test;

import java.util.List;
import junit.framework.TestCase;
import net.faustinelli.greedyepsilon.components.ValuesBufferedListFactory;
import net.faustinelli.greedyepsilon.components.ValuesListFactory;
import org.junit.Test;

/**
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class BufferedFactoryTest extends TestCase {


    @Test
    public void testBufferedListFactory() {
        Integer bufferSize = new Integer(2);
        Integer armsNo = new Integer(5);

        ValuesListFactory factory = new ValuesBufferedListFactory(bufferSize);
        List<Double> values = factory.valuesList(armsNo);
        
        assertEquals(armsNo.intValue(), values.size());

        values.set(0, new Double(0.0));
        values.set(1, new Double(1.0));
        values.set(2, new Double(1.0));
        values.set(2, new Double(2.0));

        assertEquals(new Double(0.0), values.get(0));
        assertEquals(new Double(1.0), values.get(1));
        assertEquals(new Double(1.5), values.get(2));

        values.set(1, new Double(3.0));
        assertEquals(new Double(2.0), values.get(1));

        values.set(1, new Double(4.0));
        assertEquals(new Double(3.5), values.get(1));

    }

}
