/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import net.faustinelli.greedyepsilon.algo.EpsilonGreedy;
import net.faustinelli.greedyepsilon.components.Arm;
import net.faustinelli.greedyepsilon.components.Exploiter;
import net.faustinelli.greedyepsilon.components.Explorer;

/**
 *
 * @author mfaustinelli
 */
public class Main {

    public static void main(String[] args) {

        Random rnd = new Random();

        ArrayList<Arm> arms = new ArrayList(Arrays.asList(new Arm[]{
                    new Arm(0.1, rnd),
                    new Arm(0.1, rnd),
                    new Arm(0.1, rnd),
                    new Arm(0.1, rnd)
                }));

        Explorer explorer = new Explorer(arms, rnd);
        Exploiter exploiter = new Exploiter(arms, rnd);

        EpsilonGreedy algo = new EpsilonGreedy(0.3, explorer, exploiter, rnd);

    }
}
