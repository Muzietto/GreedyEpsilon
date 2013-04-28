/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.components;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import net.faustinelli.greedyepsilon.algo.AnnealingEpsilonGreedy;
import net.faustinelli.greedyepsilon.algo.BanditAlgorithm;
import net.faustinelli.greedyepsilon.table.TableRow;

/**
 * This class can also test standard EpsilonGreedy.java algorithms
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class AnnealingInjectableStretcher extends AlgoInjectableStretcher implements BanditStretcher {

    private final Integer _horizon;

    public AnnealingInjectableStretcher(Writer writer, Integer horizon) {
        super(writer);
        _horizon = horizon;
    }

    @Override
    public void testAlgorithm(BanditAlgorithm algo, List<BernoulliArm> arms, Integer numSims, Integer horizon, Map<String, TableRow> result) throws IOException {
        if (algo instanceof AnnealingEpsilonGreedy) {
            ((AnnealingEpsilonGreedy) algo).horizon(_horizon);
        }
        super.testAlgorithm(algo, arms, numSims, horizon, result);
    }
}
