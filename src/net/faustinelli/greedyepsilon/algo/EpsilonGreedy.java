/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.algo;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * epsilon = 1.0 --> always explore
 * epsilon = 0.0 --> always exploit
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class EpsilonGreedy {

    private final Double _epsilon;
    private final List<Integer> _counts;
    private final List<Double> _values;
    private final Random _randomizer;

    public EpsilonGreedy(Double epsilon, List<Integer> counts, List<Double> values, Random randomizer) {
        _epsilon = epsilon;
        // _counts[12] = no. of arm12 pulls so far --> draw
        _counts = counts;
        // _values[12] = runnin average of rewards from arm12 -->
        _values = values;
        _randomizer = randomizer;
    }

    public void initialize() {
        for (Integer i : _counts) {
            i = 0;
        }
        for (Double d : _values) {
            d = 0.0;
        }

    }

    public Integer selectArm() {
        if (_randomizer.nextDouble() > _epsilon) {  // exploit
            return _values.indexOf((Collections.max(_values)));
        } else {  // explore
            return _randomizer.nextInt(_values.size());
        }
    }

    public void update(Integer armIndex, Double reward) {
        _counts.set(armIndex, _counts.get(armIndex) + 1);
        Integer cc = _counts.get(armIndex);
        Double newValue = _values.get(armIndex) * (cc - 1) / cc + reward * 1 / cc;
        _values.set(armIndex, newValue);

    }
}
