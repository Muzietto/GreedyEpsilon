/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Prepares:
 * - 80% of stingy BA's (Bernouilly Arms)
 * - 20% of prodigal BA's
 * e.g. [BA(0.1), BA(0.1), BA(0.1), BA(0.1), BA(0.9)]
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class StingyArmsPreparator implements ArmsPreparator {

    public List<BernoulliArm> prepareArms(Integer armsNum, Random rnd) {
        List<BernoulliArm> result = new ArrayList<BernoulliArm>();
        for (int iii = 0; iii < armsNum; iii++) {
            if (iii < 0.8 * armsNum) {
                result.add(new BernoulliArm(0.1, rnd));
            } else {
                result.add(new BernoulliArm(0.9, rnd));
            }
        }
        Collections.shuffle(result);
        return result;
    }

    public String preparatorType() {
        return "Stingy";
    }
}
