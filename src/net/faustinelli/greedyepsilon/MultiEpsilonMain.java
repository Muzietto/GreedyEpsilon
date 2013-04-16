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

        Integer numSims = 250;
        Integer horizon = 500;

        List<BernoulliArm> arms = Arrays.asList(new BernoulliArm[]{
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    //new BernoulliArm(0.3, rnd),
                    //new BernoulliArm(0.5, rnd),
                    //new BernoulliArm(0.7, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd),
                    new BernoulliArm(0.9, rnd)
                });

        /**
         * epsilon = 1.0 --> always explore
         * epsilon = 0.0 --> always exploit
         * @author Marco Faustinelli <contatti@faustinelli.net>
         */
        List<BanditAlgorithm> algos = new ArrayList<BanditAlgorithm>();

        algos.add(new EpsilonGreedy(0.1, arms.size(), rnd, "epsi0.1_85arms"));
        algos.add(new EpsilonGreedy(0.3, arms.size(), rnd, "epsi0.3_85arms"));
        algos.add(new EpsilonGreedy(0.5, arms.size(), rnd, "epsi0.5_85arms"));
        algos.add(new EpsilonGreedy(0.7, arms.size(), rnd, "epsi0.7_85arms"));
        algos.add(new EpsilonGreedy(0.9, arms.size(), rnd, "epsi0.9_85arms"));

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
