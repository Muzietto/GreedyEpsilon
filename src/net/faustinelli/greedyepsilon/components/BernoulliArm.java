/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.components;

import java.util.Random;

/**
 * _probability 0.1 --> 10% chance of reward
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class BernoulliArm {

    private Double _rewardProbability;
    private final Random _randomizer;

    public BernoulliArm(Double rewardProbability, Random randomizer) {
        _rewardProbability = rewardProbability;
        _randomizer = (randomizer != null) ? randomizer : new Random(System.nanoTime());
    }

    public Double draw() {
        return (_randomizer.nextDouble() > _rewardProbability) ? 0.0 : 1.0;
    }

    public Double rewardProbability() {
        return new Double(_rewardProbability);
    }

    public void rewardProbability(Double newProb) {
        _rewardProbability = newProb;
    }
}
