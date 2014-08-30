/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.components;

import java.util.Random;
import org.apache.commons.lang3.mutable.MutableDouble;

/**
 * _rewardProbability 0.1 --> 10% chance of reward
 *
 * rewards are always normalized to 1
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class BernoulliArm {

    /**
     * start value in the case of variable arms
     */
    private MutableDouble _rewardProbability;
    private final Random _randomizer;

    public BernoulliArm(Double rewardProbability, Random randomizer) {
        _rewardProbability = new MutableDouble(rewardProbability);
        _randomizer = (randomizer != null) ? randomizer : new Random(System.nanoTime());
    }

    public Double draw() {
        return (_randomizer.nextDouble() > _rewardProbability.doubleValue()) ? 0.0 : 1.0;
    }

    public Double rewardProbability() {
        return new Double(_rewardProbability.doubleValue());
    }

    public void rewardProbability(Double newProb) {
        _rewardProbability = new MutableDouble(newProb);
    }
}
