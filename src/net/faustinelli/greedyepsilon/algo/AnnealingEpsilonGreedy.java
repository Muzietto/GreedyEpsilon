/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.algo;

import java.util.Random;

/**
 * epsilon = 1.0 --> always explore
 * epsilon = 0.0 --> always exploit
 * This algo decreases its epsilon linearly from startEpsilon to 0.0
 * during each simulation.
 * 
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class AnnealingEpsilonGreedy extends EpsilonGreedy implements BanditAlgorithm {

    private Integer _horizon;
    private Double _eeDelta;

    public AnnealingEpsilonGreedy(Double startEpsilon, Integer armsNo, Random randomizer, String identifier) {
        super(startEpsilon, armsNo, randomizer, identifier);
    }

    public void horizon(Integer horizon) {
        _horizon = horizon;
        _eeDelta = _epsilon / horizon.doubleValue();
    }

    @Override
    public void update(Integer armIndex, Double reward) {
        super.update(armIndex, reward);
        // decrement epsilon -= f(_horizon)
        _epsilon -= _eeDelta;
        //System.out.println("ee=" + _epsilon);
    }
}
