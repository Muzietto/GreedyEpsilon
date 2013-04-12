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
import java.util.Collections;
import java.util.List;
import net.faustinelli.greedyepsilon.components.BernoulliArm;
import net.faustinelli.greedyepsilon.table.RunningAverageTableRow;
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
        RunningAverageTableRow bestArmPercentage = new RunningAverageTableRow("goodArmPercentage", horizon);
        TableRow<Double> reward = new TableRow<Double>("reward", horizon);
        RunningAverageTableRow averageReward = new RunningAverageTableRow("averageReward", horizon);
        TableRow<Double> cumReward = new TableRow<Double>("cumReward", horizon);
        TableRow<Integer> simNum = new TableRow<Integer>("simNum", horizon);
        TableRow<Integer> draw = new TableRow<Integer>("draw", horizon);

        // for each simulation...
        for (Integer sim : Range.closed(0, numSims - 1).asSet(DiscreteDomains.integers())) {
            Collections.shuffle(arms);

            chosenArm.clear();
            bestArm.clear();
            bestArmPercentage.clear();
            simNum.clear();
            draw.clear();
            reward.clear();
            averageReward.clear();
            cumReward.clear();

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
                bestArm.add(curBestArm);
                simNum.add(sim);
                draw.add(curDraw);

                Integer currArm = algo.selectArm();
                chosenArm.add(currArm);
                bestArmPercentage.add((currArm == curBestArm) ? 1.0 : 0.0);

                Double curReward = arms.get(chosenArm.get(curDraw)).draw();

                reward.add(curReward);
                averageReward.add(curReward);

                if (curDraw == 0) {
                    cumReward.add(curReward);
                } else {
                    cumReward.add(cumReward.get(curDraw - 1) + curReward);
                }
                algo.update(currArm, curReward);


            }  // end single simulation

            // writeCsvRow(chosenArm);
            // writeCsvRow(bestArm);
//            writeCsvRow(bestArmPercentage);
            // writeCsvRow(simNum);
            // writeCsvRow(draw);
//            writeCsvRow(reward);
            //writeCsvRow(averageReward);
            writeCsvRow(cumReward);
        }  // end all simulations

    }  // end testAlgorithm

    private void writeCsvRow(TableRow list) throws IOException {
        _writer.append(list.rowName() + ",");
        for (Integer ii : Range.closed(0, list.horizon() - 1).asSet(DiscreteDomains.integers())) {
            _writer.append(list.get(ii).toString() + ",");
        }
        _writer.append("\n");
    }
}
