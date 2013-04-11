/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.algo;

import com.google.common.base.Function;
import com.google.common.collect.DiscreteDomains;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.faustinelli.greedyepsilon.components.BernoulliArm;
import net.faustinelli.greedyepsilon.table.TableRow;

/**
 *
 * @author mfaustinelli
 */
public class AlgoInjectableStretcher {

    private final Writer _writer;

    public AlgoInjectableStretcher(Writer writer) {
        _writer = writer;
    }

    public void testAlgorithm(EpsilonGreedy algo, List<BernoulliArm> arms, Integer numSims, Integer horizon) throws IOException {
        TableRow<Integer> chosenArm = new TableRow<Integer>("chosenArm", horizon);
        TableRow<Integer> bestArm = new TableRow<Integer>("bestArm", horizon);
        TableRow<Double> reward = new TableRow<Double>("reward", horizon);
        TableRow<Double> cumReward = new TableRow<Double>("cumReward", horizon);
        TableRow<Integer> simNum = new TableRow<Integer>("simNum", horizon);
        TableRow<Integer> draw = new TableRow<Integer>("draw", horizon);

        // for each simulation...
        for (Integer sim : Range.closed(0, numSims - 1).asSet(DiscreteDomains.integers())) {
            Collections.shuffle(arms);

            chosenArm.clear();
            bestArm.clear();
            simNum.clear();
            draw.clear();
            reward.clear();
            cumReward.clear();

            for (Integer ii : Range.closed(1, horizon).asSet(DiscreteDomains.integers())) {
                chosenArm.add(0);
                bestArm.add(0);
                simNum.add(0);
                draw.add(0);
                reward.add(0.0);
                cumReward.add(0.0);
            }

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
                bestArm.set(curDraw, curBestArm);

                simNum.set(curDraw, sim);
                draw.set(curDraw, curDraw);

                Integer currArm = algo.selectArm();
                chosenArm.set(curDraw, currArm);

                Double curReward = arms.get(chosenArm.get(curDraw)).draw();

                reward.set(curDraw, curReward);

                if (curDraw == 0) {
                    cumReward.set(curDraw, curReward);
                } else {
                    cumReward.set(curDraw, cumReward.get(curDraw - 1) + curReward);
                }
                algo.update(currArm, curReward);


            }  // end single simulation

            writeCsvRow(chosenArm);
            writeCsvRow(bestArm);
            writeCsvRow(simNum);
            writeCsvRow(draw);
            writeCsvRow(reward);
            writeCsvRow(cumReward);
        }  // end all simulations

        /*
        List<Integer> simNum = new ArrayList<Integer>();
        List<Integer> draw = new ArrayList<Integer>();
        List<Integer> bestArm = new ArrayList<Integer>();
        List<Integer> chosenArm = new ArrayList<Integer>();
        List<Double> reward = new ArrayList<Double>();
        List<Double> cumReward = new ArrayList<Double>();
         */
    }  // end testAlgorithm

    private void writeCsvRow(TableRow list) throws IOException {
        _writer.append(list.rowName() + ",");
        for (Integer ii : Range.closed(0, list.horizon() - 1).asSet(DiscreteDomains.integers())) {
            _writer.append(list.get(ii).toString() + ",");
        }
        _writer.append("\n");
    }
}
