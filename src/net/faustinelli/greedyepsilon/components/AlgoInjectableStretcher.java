/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.components;

import com.google.common.base.Function;
import com.google.common.collect.DiscreteDomains;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.faustinelli.greedyepsilon.algo.BanditAlgorithm;
import net.faustinelli.greedyepsilon.table.RunningAverageTableRow;
import net.faustinelli.greedyepsilon.table.TableRow;
import net.faustinelli.greedyepsilon.table.TableRowsAverager;

/** This is the real workhorse of the whole codebase.
 * It exercises (I mean it 'stretches' them to their limit) many sorts of algorithms
 * invoking them and organising their outputs in a consistent manner.
 *
 * It performs data collection for the following results:
 * - "averageReward"
 * - "cumulativeReward"
 * - "bestArmPercentage"
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class AlgoInjectableStretcher implements BanditStretcher {

    private final Writer _writer;

    public AlgoInjectableStretcher(Writer writer) {
        _writer = writer;
    }

    public void testAlgorithm(
            BanditAlgorithm algo,
            List<BernoulliArm> arms,
            Integer numSims,
            Integer horizon,
            Map<String, TableRow> result) throws IOException {

        List<TableRow<Double>> averageRewardData = new ArrayList<TableRow<Double>>();
        List<TableRow<Double>> cumRewardData = new ArrayList<TableRow<Double>>();
        List<TableRow<Double>> bestArmPercentageData = new ArrayList<TableRow<Double>>();

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
                    return arm.rewardProbability();
                }
            });

            // bestArm is found by comparing probabilities
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

                // FIRST CRUCIAL MOMENT! arm gets drawn
                Double curReward = arms.get(chosenArm.get(curDraw)).draw();

                reward.add(curReward);
                averageReward.add(curReward);

                if (curDraw == 0) {
                    cumReward.add(curReward);
                } else {
                    cumReward.add(cumReward.get(curDraw - 1) + curReward);
                }
                // SECOND CRUCIAL MOMENT! update data available to the algorithm
                algo.update(currArm, curReward);

            }  // end single draw

            averageRewardData.add(averageReward);
            cumRewardData.add(cumReward);
            bestArmPercentageData.add(bestArmPercentage);

        }  // end simulation

        TableRowsAverager cumRewardAverages = new TableRowsAverager(cumRewardData, "cumulativeReward");
        TableRowsAverager averageRewardAverages = new TableRowsAverager(averageRewardData, "averageReward");
        TableRowsAverager bestArmPercentageAverages = new TableRowsAverager(bestArmPercentageData, "bestArmPercentage");

        Map<String, TableRow> innerResult = new HashMap<String, TableRow>();
        innerResult.put("averageReward", averageRewardAverages);
        innerResult.put("cumulativeReward", cumRewardAverages);
        innerResult.put("bestArmPercentage", bestArmPercentageAverages);

        Iterator<String> keys = result.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            if (innerResult.get(key) != null) {
                TableRow tr = innerResult.get(key);
                // AAA: copy algorithm identifier from given TableRow to calculation result
                tr.algoIdentifier(result.get(key).algoIdentifier());
                result.put(key, tr);
                // this Stretcher has a side effect: write CSV files
                tr.writeCsvRow(_writer);
            } else {
                throw new RuntimeException("cannot ask result " + key);
            }
        }


    }  // end testAlgorithm
}
