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
 * Actually its name should be EPSILON-NAIVE!!
 * epsilon = 1.0 --> always explore
 * epsilon = 0.0 --> always exploit
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class EpsilonGreedy implements BanditAlgorithm {

    protected String _identifier = null;
    protected Double _epsilon;
    protected final Random _randomizer;
    /**
     * _counts[i] = number of times arms[i] has been drawn
     */
    protected final List<Integer> _counts;
    /**
     * _values[i] = average reward taken from arms[i]
     */
    protected final List<Double> _values;
    protected final Integer _armsNo;

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
     * it is not possible to modify the ee_parameter
     * of a standard epsilon greedy algorithm.
     * Only subclasses my do that.
     * @param ee_parameter
     */
    public void ee_parameter(Double ee_parameter) {
    }

    /**
     * standard Epsilon Greedy algorithm is
     * extremely pessimistic about unproven arms
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

    public String logMessage() {
        return "Standard EpsilonGreedy epsilon=" + ee_parameter();
    }
}
