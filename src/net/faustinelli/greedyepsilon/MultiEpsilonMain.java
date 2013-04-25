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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.faustinelli.greedyepsilon.algo.MultiEpsilonCampaigner;
import net.faustinelli.greedyepsilon.algo.BanditAlgorithm;
import net.faustinelli.greedyepsilon.algo.BanditStretcher;
import net.faustinelli.greedyepsilon.algo.EpsilonGreedy;
import net.faustinelli.greedyepsilon.algo.OptimisticEpsilonGreedy;
import net.faustinelli.greedyepsilon.components.BernoulliArm;
import net.faustinelli.greedyepsilon.table.TableRow;

/**
 *
 * @author mfaustinelli
 */
public class MultiEpsilonMain {

    public static void main(String[] args) throws IOException {
        long seed = System.nanoTime();
        Random rnd = new Random(seed);

        Integer numSims = 2000;
        Integer horizon = 500;

        /*
        List<BernoulliArm> arms_ref = Arrays.asList(new BernoulliArm[]{
        new BernoulliArm(0.1, rnd),
        new BernoulliArm(0.3, rnd),
        new BernoulliArm(0.5, rnd),
        new BernoulliArm(0.7, rnd),
        new BernoulliArm(0.9, rnd)
        });
         */

        List<BernoulliArm> arms = new ArrayList<BernoulliArm>();

        for (int iii = 0; iii < 1; iii++) {
            arms.add(new BernoulliArm(0.1, rnd));
            arms.add(new BernoulliArm(0.1, rnd));
            arms.add(new BernoulliArm(0.1, rnd));
            arms.add(new BernoulliArm(0.1, rnd));
            arms.add(new BernoulliArm(0.9, rnd));
        }

        /**
         * epsilon = 1.0 --> always explore
         * epsilon = 0.0 --> always exploit
         * @author Marco Faustinelli <contatti@faustinelli.net>
         */
        List<BanditAlgorithm> algos = new ArrayList<BanditAlgorithm>();

        algos.add(new OptimisticEpsilonGreedy(0.1, arms.size(), rnd, "optiEpsi0.1_5arms"));
        algos.add(new OptimisticEpsilonGreedy(0.3, arms.size(), rnd, "optiEpsi0.3_5arms"));
        algos.add(new OptimisticEpsilonGreedy(0.5, arms.size(), rnd, "optiEpsi0.5_5arms"));
        algos.add(new OptimisticEpsilonGreedy(0.7, arms.size(), rnd, "optiEpsi0.7_5arms"));
        algos.add(new OptimisticEpsilonGreedy(0.9, arms.size(), rnd, "optiEpsi0.9_5arms"));

        String sFileName = "test/datafiles/" + Long.toString(seed) + ".csv";
        System.out.println("file is " + sFileName);
        Writer wrrrr = new PrintWriter(new FileWriter(sFileName));

        BanditStretcher stretcher = new AlgoInjectableStretcher(wrrrr);

        Map<String, TableRow> result = new HashMap<String, TableRow>();

        result.put("bestArmPercentage", new TableRow());
        //result.put("averageReward", new TableRow());
        //result.put("cumulativeReward", new TableRow());

        new MultiEpsilonCampaigner(stretcher).campaignAlgorithms(algos, arms, numSims, horizon, result);

        wrrrr.flush();
        wrrrr.close();

    }
}
