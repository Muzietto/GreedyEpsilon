/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.table;

import com.google.common.collect.DiscreteDomains;
import com.google.common.collect.Range;

/**
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class RunningAverageTableRow extends TableRow<Double> {

    public RunningAverageTableRow(String rowName, Integer horizon) {
        super(rowName, horizon);
    }

    public RunningAverageTableRow(TableRow tr) {
        _rowName = "ave_" + tr.rowName();
        _horizon = tr.horizon();

        /*        while (this.iterator().hasNext()) {
        System.out.println(iterator.next());
        }
         */

        for (Integer ii : Range.closed(0, tr.size() - 1).asSet(DiscreteDomains.integers())) {
            this.add((Double) tr.get(ii));
        }
    }

    @Override
    public final boolean add(Double newValue) {
        Double dsize = new Double(this.size());
        Double last = (this.size() > 0) ? this.get(this.size() - 1) : 0.0;
        Double next = (last * dsize + newValue) / (dsize + new Double(1.0));
        return super.add(next);
    }
}
