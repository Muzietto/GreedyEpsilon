/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.table.test;

import java.util.Arrays;
import net.faustinelli.greedyepsilon.table.TableRow;
import net.faustinelli.greedyepsilon.table.TableRowsAverager;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class TableRowsAveragerTest {

    @Test
    public void testMaxDimAndElse() {
        TableRow<Double> firstRow = new TableRow<Double>();
        firstRow.add(1.0);
        TableRow<Double> secondRow = new TableRow<Double>();
        secondRow.add(0.0);
        secondRow.add(1.0);
        secondRow.add(2.0);

        TableRowsAverager averages = new TableRowsAverager(Arrays.asList(firstRow, secondRow),"testAverages");

        // text rowName
        assertEquals("testAverages", averages.rowName());

        // text maxDim
        assertEquals(new Integer(3), averages.maxDim());

        // text computed averages
        assertEquals(new Double(0.5), averages.get(0));
        assertEquals(new Double(0.5), averages.get(1));
        assertEquals(new Double(1.0), averages.get(2));
    }
}
