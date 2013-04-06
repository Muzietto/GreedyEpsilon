/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.components;

import java.util.Random;

/**
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class Arm {

    private final Double seed;
    private final Random randomizer;

    public Arm(Double aSeed, Random aRandomizer) {
        seed = aSeed;
        randomizer = aRandomizer;
    }

    public Double reward() {
        return (randomizer.nextDouble() > seed) ? 1.0 : 0.0;
    }
}
