/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.algo;

import java.util.Random;
import net.faustinelli.greedyepsilon.components.ValuesBufferedListFactory;

/**
 * Epsilon-Greedy with sized buffer
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class BufferedEpsilonGreedy extends EpsilonGreedy {

    Integer _bufferSize;

    public BufferedEpsilonGreedy(Double epsilon, Integer armsNo, Integer bufferSize, Random randomizer, String identifier) {
        super(epsilon, armsNo, randomizer, new ValuesBufferedListFactory(bufferSize), identifier);
        _bufferSize = bufferSize;
    }

    public BufferedEpsilonGreedy(Double epsilon, Integer armsNo, Integer bufferSize, Random randomizer) {
        super(epsilon, armsNo, randomizer, new ValuesBufferedListFactory(bufferSize));
        _bufferSize = bufferSize;
    }

    @Override
    public String logMessage() {
        return "Buffered EpsilonGreedy epsilon=" + ee_parameter() + ", bufferSize=" + _bufferSize;
    }
}
