/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.algo.test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.ByteArrayOutputStream;
import java.util.List;
import net.faustinelli.greedyepsilon.algo.AlgoInjectableStretcher;
import net.faustinelli.greedyepsilon.algo.EpsilonGreedy;
import net.faustinelli.greedyepsilon.components.BernoulliArm;
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
public class AlgoInjectableStretcherTest {

    public AlgoInjectableStretcherTest() {
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

    /**
     * Test of testAlgorithm method, of class AlgoInjectableStretcher.
     */
    @Test
    public void testTestAlgorithm() throws IOException {

        Random rnd = new Random(System.nanoTime());
        EpsilonGreedy algo = new EpsilonGreedy(0.1, 2, rnd);

        List<BernoulliArm> arms = Arrays.asList(new BernoulliArm[]{
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.9, rnd)
                });

        Integer numSims = 1;
        Integer horizon = 1;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Writer wrrrr = new PrintWriter(baos);


        AlgoInjectableStretcher instance = new AlgoInjectableStretcher(wrrrr);
        instance.testAlgorithm(algo, arms, numSims, horizon);

        wrrrr.flush();
        wrrrr.close();

        assertEquals("0", baos.toString().substring(0,1));

    }
}
