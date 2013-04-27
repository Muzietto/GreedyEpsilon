/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.table.test;

import net.faustinelli.greedyepsilon.table.TableRow;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class TableRowTest {

    @Test
    public void testDividedBy() {
        TableRow<Integer> upper = new TableRow<Integer>("upper", 3);
        upper.add(0);
        upper.add(null);
        upper.add(6);
        TableRow<Integer> lower = new TableRow<Integer>("lower", 3);
        lower.add(6);
        lower.add(null);
        lower.add(2);

        TableRow<Double> fractions = upper.dividedBy(lower);
        assertEquals("upper/lower", fractions.rowName());
        assertEquals(new Double(0.0), fractions.get(0));
        assertNull(fractions.get(1));
        assertEquals(new Double(3.0), fractions.get(2));
    }
    @Test
    public void testDividedByWithZeroes() {
        TableRow<Integer> upper = new TableRow<Integer>("upper", 2);
        upper.add(0);
        upper.add(6);
        TableRow<Integer> lower = new TableRow<Integer>("lower", 2);
        lower.add(0);
        lower.add(0);

        TableRow<Double> fractions = upper.dividedBy(lower);
        assertEquals("upper/lower", fractions.rowName());
        assertEquals(Double.NaN, (Object)fractions.get(0));
        assertEquals(Double.POSITIVE_INFINITY, (Object)fractions.get(1));
    }
}
