/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.components;

import java.util.List;
import java.util.Random;

/**
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class Explorer {

    private final List<Arm> arms;
    private final Random randomizer;

    public Explorer(List<Arm> someArms, Random aRandomizer) {
        arms = someArms;
        randomizer = aRandomizer;
    }

    public Double pullArm() {
        Integer rnd = randomizer.nextInt(arms.size());

        return arms.get(rnd).reward();
    }
}
