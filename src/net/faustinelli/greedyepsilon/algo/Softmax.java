/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.algo;

import com.google.common.collect.DiscreteDomains;
import com.google.common.collect.Range;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Actually its name should be TEMPERATURE-NAIVE!!!
 * temperature = infinite --> always explore
 * temperature = 0.0      --> always exploit
 * 
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class Softmax implements BanditAlgorithm {

    protected String _identifier = null;
    protected Double _temperature;
    protected final Random _randomizer;
    private Boolean _startingUp = true;
    /**
     * _counts[i] = number of times arms[i] has been drawn
     */
    protected final List<Integer> _counts;
    /**
     * _values[i] = average reward taken from arms[i]
     */
    protected final List<Double> _values;
    protected final Integer _armsNo;

    public Softmax(Double temperature, Integer armsNo, Random randomizer) {
        _temperature = temperature;
        _randomizer = (randomizer != null) ? randomizer : new Random(System.nanoTime());
        _armsNo = armsNo;
        _counts = new ArrayList<Integer>(armsNo);
        _values = new ArrayList<Double>(armsNo);
    }

    public Softmax(Double epsilon, Integer armsNo, Random randomizer, String identifier) {
        this(epsilon, armsNo, randomizer);
        _identifier = identifier;
    }

    public Double ee_parameter() {
        return (_temperature != null) ? new Double(_temperature) : null;
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
     * optimism = 0.0 --> extreme scepticism about unknown arms
     * optimism = 1.0 --> extreme faith about unknown arms
     */
    public void initialize() {
        final Double optimismRate = new Double(0.0);
        _counts.clear();
        _values.clear();

        for (Integer index : Range.closed(0, _armsNo - 1).asSet(DiscreteDomains.integers())) {
            _counts.add(optimismRate.intValue());
            _values.add(optimismRate);
        }
    }

    public Integer selectArm() {
        Double totalValue = 0.0;
        for (Double dd : _values) {
            totalValue += Math.exp(dd / _temperature);
        }
        ArrayList<Double> probs = new ArrayList<Double>();
        for (Double dd : _values) {
            probs.add(Math.exp(dd / _temperature) / totalValue);
        }
        return categoricalDraw(probs);
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

    private Integer categoricalDraw(List<Double> probs) {
        Double draw = _randomizer.nextDouble();
        Double probsSoFar = 0.0;
        for (int iii = 0; iii < probs.size(); iii++) {
            Double prob = probs.get(iii);
            probsSoFar += prob;
            if (draw < probsSoFar) {
                return iii;
            }
        }
        // return last arm index
        return probs.size() - 1;
    }

    public String logMessage() {
        return "Standard Softmax - temperature=" + ee_parameter();
    }
}
