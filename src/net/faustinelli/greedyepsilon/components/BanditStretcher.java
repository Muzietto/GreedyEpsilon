/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.faustinelli.greedyepsilon.components;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import net.faustinelli.greedyepsilon.algo.BanditAlgorithm;
import net.faustinelli.greedyepsilon.table.TableRow;

/**
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public interface BanditStretcher {

    void testAlgorithm(BanditAlgorithm algo, List<BernoulliArm> arms, Integer numSims, Integer horizon, Map<String, TableRow> result) throws IOException;

}
