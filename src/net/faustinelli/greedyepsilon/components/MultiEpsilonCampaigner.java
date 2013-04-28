/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.components;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.faustinelli.greedyepsilon.algo.AnnealingEpsilonGreedy;
import net.faustinelli.greedyepsilon.algo.BanditAlgorithm;
import net.faustinelli.greedyepsilon.table.TableRow;

/**
 *
 * @author mfaustinelli
 */
public class MultiEpsilonCampaigner implements BanditCampaigner {

    private final BanditStretcher _stretcher;

    public MultiEpsilonCampaigner(BanditStretcher stretcher) {
        _stretcher = stretcher;
    }

    public void campaignAlgorithms(List<BanditAlgorithm> algos, List<BernoulliArm> arms, Integer numSims, Integer horizon, Map<String, TableRow> result) throws IOException {
        // prepare map for the calculations
        Map<String, TableRow> accumulator = new HashMap<String, TableRow>();

        Iterator<BanditAlgorithm> algosIte = algos.iterator();
        while (algosIte.hasNext()) {
            BanditAlgorithm algo = algosIte.next();
            String algIde = algo.identifier();

            // prepare Map for the stretcher to process
            Map<String, TableRow> specificResult = new HashMap<String, TableRow>();
            // prepare generic multi-purpose result to pass to stretcher
            Map<String, TableRow> genericResult = new HashMap<String, TableRow>();
            genericResult.put("averageReward", new TableRow("averageReward", algIde));
            genericResult.put("cumulativeReward", new TableRow("cumulativeReward", algIde));
            genericResult.put("bestArmPercentage", new TableRow("bestArmPercentage", algIde));

            // filter away results and store those requested by the caller
            Iterator<String> keys = result.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                if (genericResult.get(key) != null) {
                    TableRow tr = genericResult.get(key);
                    specificResult.put(key, tr);
                } else {
                    throw new RuntimeException("cannot ask result " + key);
                }
            }

            String msg = (algo instanceof AnnealingEpsilonGreedy) ? "annealing from " : "standard ";

            System.out.println(msg + "ee_parameter " + algo.ee_parameter());
            _stretcher.testAlgorithm(algo, arms, numSims, horizon, specificResult);

            // filter away results and store those requested by the caller
            keys = result.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                if (specificResult.get(key) != null) {
                    TableRow tr = specificResult.get(key);
                    accumulator.put(tr.algoIdentifier() + tr.rowName(), tr);
                } else {
                    throw new RuntimeException("cannot ask result " + key);
                }
            }
        }
        // swap result with acc
        result = accumulator;
    }
}
