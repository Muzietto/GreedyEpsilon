/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon;

import com.google.common.base.Function;
import com.google.common.collect.DiscreteDomains;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.faustinelli.greedyepsilon.algo.EpsilonGreedy;
import net.faustinelli.greedyepsilon.components.BernoulliArm;

/**
 *
 * @author mfaustinelli
 */
public class AlgoStretcher {

    public static void main(String[] args) {
        long seed = System.nanoTime();
        Random rnd = new Random(seed);

        Integer numSims = 100;
        Integer horizon = 100;

        List<BernoulliArm> arms = Arrays.asList(new BernoulliArm[]{
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.9, rnd)
                });

        List counts = Arrays.asList(new Integer[]{0, 0, 0, 0, 0});
        List values = Arrays.asList(new Double[]{0.0, 0.0, 0.0, 0.0, 0.0});

        EpsilonGreedy algo = new EpsilonGreedy(0.5, counts, values, rnd);

        List<List<Double>> resultArray = testAlgorithm(algo, arms, numSims, horizon);

        /*
        List<Integer> chosenArm = new ArrayList<Integer>();
        List<Integer> bestArm = new ArrayList<Integer>();
        List<Double> reward = new ArrayList<Double>();
        List<Double> cumReward = new ArrayList<Double>();
        List<Integer> simNum = new ArrayList<Integer>();
        List<Integer> draw = new ArrayList<Integer>();
         */

        String sFileName = "test/datafiles/" + Long.toString(seed) + ".csv";
        System.out.println("file is " + sFileName);

        try {
            FileWriter writer = new FileWriter(sFileName);

            // for each draw within the horizon...
            for (Integer curDraw : Range.closed(0, horizon - 1).asSet(DiscreteDomains.integers())) {

                // for each resultElem in the result double array...
                for (Integer resElem : Range.closed(0, resultArray.size() - 1).asSet(DiscreteDomains.integers())) {

                    // for each simulation...
                    for (Integer sim : Range.closed(0, numSims - 1).asSet(DiscreteDomains.integers())) {
                        Integer index = ((sim) * horizon) + curDraw;
                        writer.append(resultArray.get(resElem).get(index).toString() + ",");
                    }
                }
                writer.append("\n");

            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<List<Double>> testAlgorithm(EpsilonGreedy algo, List<BernoulliArm> arms, Integer numSims, Integer horizon) {
        List<Integer> chosenArm = new ArrayList<Integer>();
        List<Integer> bestArm = new ArrayList<Integer>();
        List<Double> reward = new ArrayList<Double>();
        List<Double> cumReward = new ArrayList<Double>();
        final List<Integer> simNum = new ArrayList<Integer>();
        List<Integer> draw = new ArrayList<Integer>();

        for (Integer ii : Range.closed(1, numSims * horizon).asSet(DiscreteDomains.integers())) {
            chosenArm.add(0);
            bestArm.add(0);
            simNum.add(0);
            draw.add(0);
            reward.add(0.0);
            cumReward.add(0.0);
        }

        // for each simulation...
        for (Integer sim : Range.closed(0, numSims - 1).asSet(DiscreteDomains.integers())) {
            Collections.shuffle(arms);

            final List<Double> armProbabilities =
                    Lists.transform(arms, new Function<BernoulliArm, Double>() {

                public Double apply(final BernoulliArm arm) {
                    return arm.probability();
                }
            });

            Integer curBestArm = armProbabilities.indexOf((Collections.max(armProbabilities)));

            algo.initialize();

            // for each draw within the horizon...
            for (Integer curDraw : Range.closed(0, horizon - 1).asSet(DiscreteDomains.integers())) {

                Integer index = ((sim) * horizon) + curDraw;

                bestArm.set(index, curBestArm);

                simNum.set(index, sim);
                draw.set(index, curDraw);

                Integer currArm = algo.selectArm();
                chosenArm.set(index, currArm);

                Double curReward = arms.get(chosenArm.get(index)).draw();

                reward.set(index, curReward);

                if (curDraw == 0) {
                    cumReward.set(index, curReward);
                } else {
                    cumReward.set(index, cumReward.get(index - 1) + curReward);
                }
                algo.update(currArm, curReward);
            }
        }

        /*
        List<Integer> chosenArm = new ArrayList<Integer>();
        List<Integer> bestArm = new ArrayList<Integer>();
        List<Double> reward = new ArrayList<Double>();
        List<Double> cumReward = new ArrayList<Double>();
        List<Integer> simNum = new ArrayList<Integer>();
        List<Integer> draw = new ArrayList<Integer>();
         */

        final Function<Integer, Double> intDou = new Function<Integer, Double>() {

            @Override
            public Double apply(final Integer input) {
                return new Double(input);
            }
        };

        final List<Double> dChosenArm =
                Lists.transform(chosenArm, intDou);

        final List<Double> dBestArm =
                Lists.transform(bestArm, intDou);

        final List<Double> dSimNum =
                Lists.transform(simNum, intDou);

        final List<Double> dDraw =
                Lists.transform(draw, intDou);

        System.out.println("simNum:   " + dSimNum.toString());
        System.out.println("draw:     " + dDraw.toString());
        System.out.println("bestArm:  " + dBestArm.toString());
        System.out.println("chosenArm:" + dChosenArm.toString());
        System.out.println("reward:   " + reward.toString());
        System.out.println("cumReward:" + cumReward.toString());

        List counts = Arrays.asList(new Integer[]{0, 0, 0, 0, 0});

        List<List<Double>> result = new ArrayList();

        result.add(dSimNum);
        result.add(dDraw);
        result.add(dBestArm);
        result.add(dChosenArm);
        result.add(reward);
        result.add(cumReward);

        return result;
    }
}
