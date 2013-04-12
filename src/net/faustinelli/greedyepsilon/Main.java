/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon;

import net.faustinelli.greedyepsilon.algo.AlgoInjectableStretcher;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.faustinelli.greedyepsilon.algo.EpsilonGreedy;
import net.faustinelli.greedyepsilon.components.BernoulliArm;

/**
 *
 * @author mfaustinelli
 */
public class Main {

    public static void main(String[] args) throws IOException {
        long seed = System.nanoTime();
        Random rnd = new Random(seed);

        Integer numSims = 200;
        Integer horizon = 200;

        List<BernoulliArm> arms = Arrays.asList(new BernoulliArm[]{
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.3, rnd),
                    new BernoulliArm(0.5, rnd),
                    new BernoulliArm(0.7, rnd),
                    new BernoulliArm(0.9, rnd)
                });

        /**
         * epsilon = 1.0 --> always explore
         * epsilon = 0.0 --> always exploit
         * @author Marco Faustinelli <contatti@faustinelli.net>
         */
        Double epsilon = 0.9;

        EpsilonGreedy algo = new EpsilonGreedy(epsilon, arms.size(), rnd);

        String sFileName = "test/datafiles/" + Long.toString(seed) + ".csv";
        System.out.println("file is " + sFileName);

        Writer wrrrr = new PrintWriter(new FileWriter(sFileName));

        new AlgoInjectableStretcher(wrrrr).testAlgorithm(algo, arms, numSims, horizon);

        wrrrr.flush();
        wrrrr.close();

    }
}
