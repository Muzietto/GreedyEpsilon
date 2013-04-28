/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.faustinelli.greedyepsilon.algo.AnnealingEpsilonGreedy;
import net.faustinelli.greedyepsilon.components.MultiEpsilonCampaigner;
import net.faustinelli.greedyepsilon.algo.BanditAlgorithm;
import net.faustinelli.greedyepsilon.algo.EpsilonGreedy;
import net.faustinelli.greedyepsilon.components.AnnealingInjectableStretcher;
import net.faustinelli.greedyepsilon.components.BanditStretcher;
import net.faustinelli.greedyepsilon.components.BernoulliArm;
import net.faustinelli.greedyepsilon.table.TableRow;

/**
 * This class generates arms with linear distribution
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class MultiEpsilonMain {

    public static void main(String[] args) throws IOException {
        long seed = System.nanoTime();
        Random rnd = new Random(seed);

        String sought = "bestArmPercentage";
        //String sought = "averageReward";
        //String sought = "cumulativeReward";

        Integer armsNum = 200;
        /**
         * linearFactor = 0  --> linear distribution of arms' reward percentage
         * linearFactor = - MAX_INTEGER --> almost all arms are stingy
         * linearFactor = + MAX_INTEGER --> almost all arms are prodigal
         */
        Double linearFactor = 0.0;
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

        for (int armIndex = 0; armIndex < armsNum; armIndex++) {
            double rewardPercentage = _rewardPercentage(armIndex, armsNum, linearFactor);
            System.out.println("arm rewardPercentage is " + rewardPercentage);
            arms.add(new BernoulliArm(rewardPercentage, rnd));
        }

        /**
         * epsilon = 1.0 --> always explore
         * epsilon = 0.0 --> always exploit
         * @author Marco Faustinelli <contatti@faustinelli.net>
         */
        List<BanditAlgorithm> algos = new ArrayList<BanditAlgorithm>();

        algos.add(new EpsilonGreedy(0.1, arms.size(), rnd, "stdEpsi0.1_" + armsNum + _sortArm(linearFactor) + "Arms"));
        algos.add(new EpsilonGreedy(0.3, arms.size(), rnd, "stdEpsi0.3_" + armsNum + _sortArm(linearFactor) + "Arms"));
        algos.add(new EpsilonGreedy(0.5, arms.size(), rnd, "stdEpsi0.5_" + armsNum + _sortArm(linearFactor) + "Arms"));
        algos.add(new EpsilonGreedy(0.7, arms.size(), rnd, "stdEpsi0.7_" + armsNum + _sortArm(linearFactor) + "Arms"));
        algos.add(new EpsilonGreedy(0.9, arms.size(), rnd, "stdEpsi0.9_" + armsNum + _sortArm(linearFactor) + "Arms"));
        algos.add(new AnnealingEpsilonGreedy(1.0, arms.size(), rnd, "annealEpsi1.0_" + armsNum + _sortArm(linearFactor) + "Arms"));

        String sFileName = "test/datafiles/" + Long.toString(seed) + "_" + sought + "_stdMultiEpsiVsAnneal1.0_" + armsNum + _sortArm(linearFactor) + "Arms.csv";
        System.out.println("file is " + sFileName);
        Writer wrrrr = new PrintWriter(new FileWriter(sFileName));

        // this stretcher handles also standard EpsilonGreedy.java algorithms
        BanditStretcher stretcher = new AnnealingInjectableStretcher(wrrrr, horizon);

        Map<String, TableRow> result = new HashMap<String, TableRow>();

        result.put(sought, new TableRow());

        new MultiEpsilonCampaigner(stretcher).campaignAlgorithms(algos, arms, numSims, horizon, result);

        wrrrr.flush();
        wrrrr.close();

    }

    /**
     * @param armIndex
     * @param armsNum
     * @param linearFactor - currently unsupported (linear distribution between 0.0 and 1.0)
     * @return rewardPercentage of arms[armIndex]
     */
    private static double _rewardPercentage(Integer armIndex, Integer armsNum, Double linearFactor) {
        double _delta = 1.0 / (armsNum.doubleValue() + 1.0);
        return _delta * (armIndex + 1);
    }

    private static String _sortArm(Double linearFactor) {
        if (linearFactor == 0) {
            return "Linear";
        } else if (linearFactor > 0) {
            return "Prodigal";
        } else {
            return "Stingy";
        }
    }
}
