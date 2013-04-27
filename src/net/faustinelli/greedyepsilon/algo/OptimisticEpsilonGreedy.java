/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.algo;

import java.util.Random;

/**
 * Handles arms with extreme optimism (initial value = 1.0)
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class OptimisticEpsilonGreedy extends EpsilonGreedy implements BanditAlgorithm {

    public OptimisticEpsilonGreedy(Double epsilon, Integer armsNo, Random randomizer, String identifier) {
        super(epsilon, armsNo, randomizer, identifier);
    }

    @Override
    public void initialize() {
        super.initialize(new Double(1.0));
    }
}
