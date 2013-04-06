/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.algo;

import java.util.Random;
import net.faustinelli.greedyepsilon.components.Exploiter;
import net.faustinelli.greedyepsilon.components.Explorer;

/**
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class EpsilonGreedy {

    private final Double epsilon;
    private final Explorer explorer;
    private final Exploiter exploiter;
    private final Random randomizer;

    public EpsilonGreedy(Double anEpsilon, Explorer anExplorer, Exploiter anExploiter, Random aRandomizer) {
        epsilon = anEpsilon;
        explorer = anExplorer;
        exploiter = anExploiter;
        randomizer = aRandomizer;
    }

    public void takeChance() {
        Double rnd = randomizer.nextDouble();

        if (rnd <= epsilon){
            explorer.pullArm();
        }
        else {
            exploiter.exploit();
        }
    }
}
