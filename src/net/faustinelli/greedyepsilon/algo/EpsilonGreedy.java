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
public class EpsilonGreedy implements BanditAlgorithm {

    private String _identifier = null;
    private final Double _epsilon;
    private final Random _randomizer;
    private final List<Integer> _counts;
    private final List<Double> _values;
    private final Integer _armsNo;

    public EpsilonGreedy(Double epsilon, Integer armsNo, Random randomizer) {
        _epsilon = epsilon;
        _randomizer = (randomizer != null) ? randomizer : new Random(System.nanoTime());
        _armsNo = armsNo;
        _counts = new ArrayList<Integer>(armsNo);
        _values = new ArrayList<Double>(armsNo);
    }

    public EpsilonGreedy(Double epsilon, Integer armsNo, Random randomizer, String identifier) {
        this(epsilon, armsNo, randomizer);
        _identifier = identifier;
    }

    public Double ee_parameter() {
        return new Double(_epsilon);
    }

    /**
     * standard Epsilon Greedy algorithm is
     * extremely pessimistic
     */
    public void initialize() {
        this.initialize(new Double(0.0));
    }

    /**
     * optimism = 0.0 --> extreme scepticism about unknown arms
     * optimism = 1.0 --> extreme faith about unknown arms
     */
    protected void initialize(Double optimismRate) {
        _counts.clear();
        _values.clear();

        for (Integer index : Range.closed(0, _armsNo - 1).asSet(DiscreteDomains.integers())) {
            _counts.add(optimismRate.intValue());
            _values.add(optimismRate);
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

    public String identifier() {
        return (_identifier != null) ? _identifier : "";
    }

    public void identifier(String identifier) {
        _identifier = identifier;
    }
}
