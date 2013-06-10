/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.algo;

import java.util.Random;
import net.faustinelli.greedyepsilon.components.ValuesBufferedListFactory;

/**
 * Softmax with sized buffer
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class BufferedSoftmax extends Softmax {

    Integer _bufferSize;

    public BufferedSoftmax(Double temperature, Integer armsNo, Integer bufferSize, Random randomizer, String identifier) {
        super(temperature, armsNo, randomizer, new ValuesBufferedListFactory(bufferSize), identifier);
        _bufferSize = bufferSize;
    }

    public BufferedSoftmax(Double temperature, Integer armsNo, Integer bufferSize, Random randomizer) {
        super(temperature, armsNo, randomizer, new ValuesBufferedListFactory(bufferSize));
        _bufferSize = bufferSize;
    }

    @Override
    public String logMessage() {
        return "Buffered Softmax temperature=" + ee_parameter() + ", bufferSize=" + _bufferSize;
    }
}
