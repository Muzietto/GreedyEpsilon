/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.table.test;

import net.faustinelli.greedyepsilon.table.TableRow;
import net.faustinelli.greedyepsilon.table.RunningAverageTableRow;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class RunningAverageTableRowTest {

    @Test
    public void testAdd() {
        RunningAverageTableRow ratr = new RunningAverageTableRow("test_average", 4);
        ratr.add(0.0);
        assertEquals(new Double(0.0), ratr.get(0));

        ratr.add(null);
        assertEquals(new Double(0.0), ratr.get(1));

        ratr.add(1.0);
        assertEquals(new Double(0.5), ratr.get(2));

        ratr.add(2.0);
        assertEquals(new Double(1.0), ratr.get(3));
    }

    @Test
    public void testAddAll() {
        TableRow<Double> tr = new TableRow<Double>("test_row", 5);
        tr.add(0.0);
        tr.add(1.0);
        tr.add(null);
        tr.add(2.0);

        RunningAverageTableRow ratr = new RunningAverageTableRow(tr);

        assertEquals(new Double(0.5), ratr.get(1));
        assertEquals(new Double(1.0), ratr.get(3));
    }
}
