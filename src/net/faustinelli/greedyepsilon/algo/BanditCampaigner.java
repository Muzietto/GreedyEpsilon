/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.algo;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import net.faustinelli.greedyepsilon.components.BernoulliArm;
import net.faustinelli.greedyepsilon.table.TableRow;

/**
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public interface BanditCampaigner {

    void campaignAlgorithms(List<BanditAlgorithm> algos, List<BernoulliArm> arms, Integer numSims, Integer horizon, Map<String, TableRow> result) throws IOException;
}
