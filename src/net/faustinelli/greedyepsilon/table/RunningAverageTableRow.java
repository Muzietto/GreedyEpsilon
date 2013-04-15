/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class RunningAverageTableRow extends TableRow<Double> {

    public RunningAverageTableRow(String rowName, Integer horizon) {
        super(rowName, horizon);
    }

    public RunningAverageTableRow(List tr) {
        if (tr instanceof TableRow) {
            _rowName = "ave_" + ((TableRow) tr).rowName();
            _horizon = ((TableRow) tr).horizon();
        } else {
            _rowName = "ave_anonymous";
        }

        Iterator thatIt = tr.iterator();
        while (thatIt.hasNext()) {
            this.add((Double) thatIt.next());
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
