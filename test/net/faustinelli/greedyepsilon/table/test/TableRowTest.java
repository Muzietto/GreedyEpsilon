/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.table.test;

import net.faustinelli.greedyepsilon.table.TableRow;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class TableRowTest {

    public TableRowTest() {
    }

    @Test
    public void testTableRow01() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDividedBy() {
        TableRow<Integer> upper = new TableRow<Integer>("upper", 2);
        upper.add(0);
        upper.add(6);
        TableRow<Integer> lower = new TableRow<Integer>("lower", 2);
        lower.add(6);
        lower.add(2);

        TableRow<Double> fractions = upper.dividedBy(lower);
        assertEquals("upper/lower", fractions.rowName());
        assertEquals(new Double(0.0), fractions.get(0));
        assertEquals(new Double(3.0), fractions.get(1));
    }
}
