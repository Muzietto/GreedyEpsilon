/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.algo.test;

import java.util.Random;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import net.faustinelli.greedyepsilon.algo.AnnealingEpsilonGreedy;

/**
 * This class decreases its epsilon linearly from startEpsilon to 0.0
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class AnnealingEpsilonGreedyTest {

    private AnnealingEpsilonGreedy _instance;

    public AnnealingEpsilonGreedyTest() {
    }

    @Test
    public void testUpdateStartEpsilon1() {
        Double startEpsilon = 1.0;
        Integer armsNo = 10;
        Random rnd = new Random(System.nanoTime());
        String identifier = "id";
        _instance = new AnnealingEpsilonGreedy(startEpsilon, armsNo, rnd, identifier);
        _instance.initialize();

        _instance.horizon(new Integer(10));
        assertEquals(new Double(1.0), _instance.ee_parameter());
        _instance.update(1, 1.0);
        assertEquals(new Double(0.9), _instance.ee_parameter());
        _instance.update(2, 0.0);
        assertEquals(new Double(0.8), _instance.ee_parameter());
    }

    @Test
    public void testUpdateStartEpsilon05() {
        Double startEpsilon = 0.5;
        Integer armsNo = 10;
        Random rnd = new Random(System.nanoTime());
        String identifier = "id";
        _instance = new AnnealingEpsilonGreedy(startEpsilon, armsNo, rnd, identifier);
        _instance.initialize();

        _instance.horizon(new Integer(10));
        assertEquals(new Double(0.5), _instance.ee_parameter());
        _instance.update(1, 1.0);
        assertEquals(new Double(0.45), _instance.ee_parameter());
        _instance.update(2, 0.0);
        assertEquals(new Double(0.4), _instance.ee_parameter());
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
}
