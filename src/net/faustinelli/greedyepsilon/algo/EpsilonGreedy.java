/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.algo;

import com.google.common.collect.DiscreteDomains;
import com.google.common.collect.Range;
import java.util.ArrayList;
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
    private final Random _randomizer;
    private final List<Integer> _counts;
    private final List<Double> _values;
    private final Integer _armsNo;

    public EpsilonGreedy(Double epsilon, Integer armsNo, Random randomizer) {
        _epsilon = epsilon;
        _randomizer = randomizer;
        _armsNo = armsNo;
        _counts = new ArrayList<Integer>(armsNo);
        _values = new ArrayList<Double>(armsNo);
    }

    public void initialize() {
        _counts.clear();
        _values.clear();

        for (Integer index : Range.closed(0, _armsNo - 1).asSet(DiscreteDomains.integers())) {
            _counts.add(0);
            _values.add(0.0);
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
