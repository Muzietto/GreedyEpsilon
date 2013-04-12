/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.table;

import com.google.common.collect.DiscreteDomains;
import com.google.common.collect.Range;
import java.util.LinkedList;

/**
 * single-simulation row for a variable during a given simulation
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class TableRow<T> extends LinkedList<T> {

    protected Integer _horizon = null;
    protected String _rowName = null;

    public TableRow() {
    }



    public TableRow(String rowName, Integer horizon) {
        _horizon = horizon;
        _rowName = rowName;
    }

    public String rowName() {
        return new String(_rowName);
    }

    public Integer horizon() {
        return new Integer(_horizon);
    }

    public TableRow<Double> dividedBy(TableRow divisor) {
        TableRow<Double> result = new TableRow<Double>(this._rowName + "/" + divisor.rowName(), _horizon);
        for (Integer ii : Range.closed(0, _horizon-1).asSet(DiscreteDomains.integers())) {
            Double dThisUp = (this.get(ii) instanceof Integer) ? (double) ((Integer) this.get(ii)).intValue() : (Double) this.get(ii);
            Double dThisDown = (this.get(ii) instanceof Integer) ? (double) ((Integer) divisor.get(ii)).intValue() : (Double) divisor.get(ii);
            result.add(ii, (dThisUp * 1.0 / dThisDown));
        }
        return result;
    }
}
