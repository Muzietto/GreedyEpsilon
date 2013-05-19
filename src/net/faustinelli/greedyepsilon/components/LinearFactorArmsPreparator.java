/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class LinearFactorArmsPreparator implements ArmsPreparator {

    /**
     * linearFactor = 0  --> linear distribution of arms' reward percentage
     * Currently unsupported:
     * - linearFactor = - MAX_INTEGER --> almost all arms are stingy
     * - linearFactor = + MAX_INTEGER --> almost all arms are prodigal
     */
    private Double _linearFactor = 0.0;

    public LinearFactorArmsPreparator() {
    }

    public List<BernoulliArm> prepareArms(Integer armsNum, Random rnd) {
        List<BernoulliArm> arms = new ArrayList<BernoulliArm>();
        for (int armIndex = 0; armIndex < armsNum; armIndex++) {
            double rewardPercentage = _rewardPercentage(armIndex, armsNum, _linearFactor);
            System.out.println("arm rewardPercentage is " + rewardPercentage);
            arms.add(new BernoulliArm(rewardPercentage, rnd));
        }
        return arms;
    }

    public String preparatorType() {
        return _armType(_linearFactor);
    }

    /**
     * @param armIndex
     * @param armsNum
     * @param linearFactor - currently unsupported (linear distribution between 0.0 and 1.0)
     * @return rewardPercentage of arms[armIndex]
     */
    private static double _rewardPercentage(Integer armIndex, Integer armsNum, Double linearFactor) {
        double _delta = 1.0 / (armsNum.doubleValue() + 1.0);
        return _delta * (armIndex + 1);
    }

    private static String _armType(Double linearFactor) {
        if (linearFactor == 0) {
            return "Linear";
        } else if (linearFactor > 0) {
            return "Prodigal";
        } else {
            return "Stingy";
        }
    }
}
