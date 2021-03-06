/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.algo.test;

import net.faustinelli.greedyepsilon.table.TableRow;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.ByteArrayOutputStream;
import java.util.List;
import net.faustinelli.greedyepsilon.components.AlgoInjectableStretcher;
import net.faustinelli.greedyepsilon.algo.EpsilonGreedy;
import net.faustinelli.greedyepsilon.components.BernoulliArm;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class AlgoInjectableStretcherTest {

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

        Map<String, TableRow> result = new HashMap<String, TableRow>();
        result.put("bestArmPercentage", new TableRow("whatever","algo1"));

        AlgoInjectableStretcher instance = new AlgoInjectableStretcher(wrrrr);
        instance.testAlgorithm(algo, arms, numSims, horizon, result);

        wrrrr.flush();
        wrrrr.close();

        assertEquals("algo1_bestArmPercentage,1.0", baos.toString().substring(0, 27));

    }
}
