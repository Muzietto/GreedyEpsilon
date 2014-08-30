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
public interface ArmsPreparator {

    public List<BernoulliArm> prepareArms(Integer armsNum, Random rnd);

    public String preparatorType();
}
