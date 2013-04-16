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
import net.faustinelli.greedyepsilon.table.RunningAverageTableRow;
import net.faustinelli.greedyepsilon.table.TableRow;
import net.faustinelli.greedyepsilon.table.TableRowsAverager;

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

        List<TableRow<Double>> cumRewardData = null;
        List<TableRow<Double>> bestArmPercentageData = null;
        cumRewardData = new ArrayList<TableRow<Double>>();
        bestArmPercentageData = new ArrayList<TableRow<Double>>();

        // for each simulation...
        for (Integer sim : Range.closed(0, numSims - 1).asSet(DiscreteDomains.integers())) {
            Collections.shuffle(arms);

            TableRow<Integer> chosenArm = new TableRow<Integer>("chosenArm", horizon);
            TableRow<Integer> bestArm = new TableRow<Integer>("bestArm", horizon);
            RunningAverageTableRow bestArmPercentage = new RunningAverageTableRow("bestArmPercentage", horizon);
            TableRow<Double> reward = new TableRow<Double>("reward", horizon);
            RunningAverageTableRow averageReward = new RunningAverageTableRow("averageReward", horizon);
            TableRow<Double> cumReward = new TableRow<Double>("cumReward", horizon);
            TableRow<Integer> simNum = new TableRow<Integer>("simNum", horizon);
            TableRow<Integer> draw = new TableRow<Integer>("draw", horizon);

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


            }  // end single draw

            cumRewardData.add(cumReward);
            bestArmPercentageData.add(bestArmPercentage);

            // writeCsvRow(chosenArm);
            // writeCsvRow(bestArm);
//            bestArmPercentage.writeCsvRow(_writer);
            // writeCsvRow(simNum);
            // writeCsvRow(draw);
//            writeCsvRow(reward);
            //writeCsvRow(averageReward);
//            cumReward.writeCsvRow(_writer);

        }  // end simulation

        TableRowsAverager cumRewardAverages = new TableRowsAverager(cumRewardData, "cumulativeReward");
        TableRowsAverager bestArmPercentageAverages = new TableRowsAverager(bestArmPercentageData, "bestArmPercentage");

        bestArmPercentageAverages.writeCsvRow(_writer);
        //cumRewardAverages.writeCsvRow(_writer);

    }  // end testAlgorithm
}
