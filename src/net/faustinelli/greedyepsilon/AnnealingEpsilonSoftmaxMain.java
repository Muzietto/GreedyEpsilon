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
import net.faustinelli.greedyepsilon.components.MultiEpsilonCampaigner;
import net.faustinelli.greedyepsilon.algo.BanditAlgorithm;
import net.faustinelli.greedyepsilon.algo.BufferedSoftmax;
import net.faustinelli.greedyepsilon.algo.Softmax;
import net.faustinelli.greedyepsilon.components.AnnealingInjectableStretcher;
import net.faustinelli.greedyepsilon.components.ArmsPreparator;
import net.faustinelli.greedyepsilon.components.BanditStretcher;
import net.faustinelli.greedyepsilon.components.BernoulliArm;
import net.faustinelli.greedyepsilon.components.StingyArmsPreparator;
import net.faustinelli.greedyepsilon.table.TableRow;

/**
 * This class may test:
 * - one or more algorithm performance aspects
 *  + accuracy (best arm percentage)
 *  + average reward
 *  + cumulative reward
 * - one or more different algorithms
 *  + any implementation of interface BanditAlgorithm
 * - one or more explore-exploit parameters per algorithm
 * - one or more different arms preparator:
 *  + any implementation of interface ArmsPreparator
 * 
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class AnnealingEpsilonSoftmaxMain {

    public static void main(String[] args) throws IOException {
        long seed = System.nanoTime();
        Random rnd = new Random(seed);

        // this string gets into the filename!!!
        String campaignSummary = "BufferedSoftmaxVsSoftmax";

        // uncomment here at least one of the following strings
        String sought = "bestArmPercentage";
        //String sought = "averageReward";
        //String sought = "cumulativeReward";

        Integer armsNum = 5;
        Integer numSims = 2500;
        Integer horizon = 500;
        Integer bufferSize = 100;

        // single arms preparator
//        ArmsPreparator armsPreparator = new LinearFactorArmsPreparator();
        ArmsPreparator armsPreparator = new StingyArmsPreparator();

        List<BernoulliArm> arms = armsPreparator.prepareArms(armsNum, rnd);

        /**
         * epsilon = 1.0, tau = infinite --> always explore
         * epsilon = 0.0, tau = 0.0      --> always exploit
         * @author Marco Faustinelli <contatti@faustinelli.net>
         */
        List<BanditAlgorithm> algos = new ArrayList<BanditAlgorithm>();

        //algos.add(new EpsilonGreedy(0.1, arms.size(), rnd, algoMessage("stdEpsi0.1", armsNum, armsPreparator)));
        //algos.add(new BufferedEpsilonGreedy(0.1, arms.size(), bufferSize, rnd, algoMessage("buffEpsi0.1", armsNum, armsPreparator)));
        //algos.add(new EpsilonGreedy(0.5, arms.size(), rnd, algoMessage("stdEpsi0.5", armsNum, armsPreparator)));
        //algos.add(new BufferedEpsilonGreedy(0.5, arms.size(), bufferSize, rnd, algoMessage("buffEpsi0.5", armsNum, armsPreparator)));
        //algos.add(new AnnealingEpsilonGreedy(0.1, arms.size(), rnd, algoMessage("annealEpsi0.1", armsNum, armsPreparator)));
        //algos.add(new AnnealingEpsilonGreedy(0.3, arms.size(), rnd, algoMessage("annealEpsi0.3", armsNum, armsPreparator)));
        //algos.add(new AnnealingEpsilonGreedy(0.5, arms.size(), rnd, algoMessage("annealEpsi0.5", armsNum, armsPreparator)));
        //algos.add(new Softmax(0.1, arms.size(), rnd, algoMessage("T0.1Softmax", armsNum, armsPreparator)));
        //algos.add(new Softmax(0.2, arms.size(), rnd, algoMessage("T0.2Softmax", armsNum, armsPreparator)));
        //algos.add(new Softmax(0.3, arms.size(), rnd, algoMessage("T0.3Softmax", armsNum, armsPreparator)));
        //algos.add(new Softmax(0.4, arms.size(), rnd, algoMessage("T0.4Softmax", armsNum, armsPreparator)));
        //algos.add(new Softmax(0.5, arms.size(), rnd, algoMessage("T0.5Softmax", armsNum, armsPreparator)));
        algos.add(new Softmax(0.1, arms.size(), rnd, algoMessage("T0.1Softmax", armsNum, armsPreparator)));
        algos.add(new BufferedSoftmax(0.1, arms.size(), bufferSize, rnd, algoMessage("T0.1BuffSoftmax", armsNum, armsPreparator)));
        algos.add(new Softmax(0.5, arms.size(), rnd, algoMessage("T0.5Softmax", armsNum, armsPreparator)));
        algos.add(new BufferedSoftmax(0.5, arms.size(), bufferSize, rnd, algoMessage("T0.5BuffSoftmax", armsNum, armsPreparator)));

        String sFileName = "test/datafiles/" + Long.toString(seed) + "_";
        sFileName += numSims + "sims" + "X" + horizon + "-";
        sFileName += sought + "_" + campaignSummary + "_" + armsNum + "_";
        sFileName += armsPreparator.preparatorType() + "Arms.csv";

        System.out.println("file is " + sFileName);
        Writer wrrrr = new PrintWriter(new FileWriter(sFileName));

        // NB - this specific stretcher handles all sorts of bandit algorithms (cfr. its javadoc)
        BanditStretcher stretcher = new AnnealingInjectableStretcher(wrrrr, horizon);

        Map<String, TableRow> result = new HashMap<String, TableRow>();

        result.put(sought, new TableRow());

        new MultiEpsilonCampaigner(stretcher).campaignAlgorithms(algos, arms, numSims, horizon, result);

        wrrrr.flush();
        wrrrr.close();
    }

    private static String algoMessage(String algoMsg, Integer armsNum, ArmsPreparator armsPreparator) {
        return algoMsg + "_" + armsNum + "_" + armsPreparator.preparatorType() + "Arms";
    }
}
