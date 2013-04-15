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

    private Double _probability;
    private final Random _randomizer;

    public BernoulliArm(Double probability, Random randomizer) {
        _probability = probability;
        _randomizer = (randomizer != null) ? randomizer : new Random(System.nanoTime());
    }

    public Double draw() {
        return (_randomizer.nextDouble() > _probability) ? 0.0 : 1.0;
    }

    public Double probability() {
        return new Double(_probability);
    }

    public void probability(Double newProb) {
        _probability = newProb;
    }
}
