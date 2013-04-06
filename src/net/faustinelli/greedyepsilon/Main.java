/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon;

import com.google.common.collect.DiscreteDomains;
import com.google.common.collect.Range;
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
public class Main {

    public static void main(String[] args) {
        long seed = System.nanoTime();
        Random rnd = new Random(seed);

        List<BernoulliArm> arms = Arrays.asList(new BernoulliArm[]{
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.1, rnd),
                    new BernoulliArm(0.9, rnd)
                });

        Collections.shuffle(arms);

        List counts = Arrays.asList(new Integer[]{0, 0, 0, 0, 0});
        List values = Arrays.asList(new Double[]{0.0, 0.0, 0.0, 0.0, 0.0});

        EpsilonGreedy algo = new EpsilonGreedy(0.5, counts, values, rnd);

        testAlgorithm(algo, arms, 50, 13);

    }

    public static Object[] testAlgorithm(EpsilonGreedy algo, List<BernoulliArm> arms, Integer numSims, Integer horizon) {
        List<Integer> chosenArm = new ArrayList<Integer>();
        List<Double> reward = new ArrayList<Double>();
        List<Double> cumReward = new ArrayList<Double>();
        List<Integer> simNum = new ArrayList<Integer>();
        List<Integer> draw = new ArrayList<Integer>();

        for (Integer ii : Range.closed(1, numSims * horizon).asSet(DiscreteDomains.integers())) {
            chosenArm.add(0);
            simNum.add(0);
            draw.add(0);
            reward.add(0.0);
            cumReward.add(0.0);
        }

        for (Integer sim : Range.closed(0, numSims - 1).asSet(DiscreteDomains.integers())) {
            //sim = sim + 1;
            algo.initialize();

            for (Integer curDraw : Range.closed(0, horizon - 1).asSet(DiscreteDomains.integers())) {
                //t = t + 1;
                Integer index = (sim) * horizon + curDraw;

                simNum.set(index, sim);
                draw.set(index, curDraw);

                Integer curArm = algo.selectArm();
                chosenArm.set(index, curArm);

                Double curReward = arms.get(chosenArm.get(index)).draw();

                reward.set(index, curReward);

                if (curDraw == 0) {
                    cumReward.set(index, curReward);
                } else {
                    cumReward.set(index, cumReward.get(index - 1) + curReward);
                }

                algo.update(curArm, curReward);
            }
        }

        System.out.println("simNum:   " + simNum.toString());
        System.out.println("draw:     " + draw.toString());
        System.out.println("chosenArms:" + chosenArm.toString());
        System.out.println("reward:   " + reward.toString());
        System.out.println("cumReward:" + cumReward.toString());

        return new Object[]{simNum, draw, chosenArm, reward, cumReward};
    }
}
