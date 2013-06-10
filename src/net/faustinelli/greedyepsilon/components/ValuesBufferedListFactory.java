/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.components;

import java.util.ArrayList;
import java.util.List;
import net.faustinelli.greedyepsilon.table.BufferedResultsList;
import net.faustinelli.greedyepsilon.table.ResultsBuffer;

/**
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class ValuesBufferedListFactory implements ValuesListFactory {

    private final Integer _bufferSize;

    public ValuesBufferedListFactory(Integer bufferSize) {
        _bufferSize = bufferSize;
    }

    public List<Double> valuesList(Integer armsNo) {
        List<ResultsBuffer> buffers = new ArrayList<ResultsBuffer>();
        for (int cc = 0; cc < armsNo; cc++) {
            buffers.add(new ResultsBuffer(_bufferSize));
        }
        return new BufferedResultsList(buffers);
    }
}
