/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.table;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Receives a list of TableRow's, builds a matrix and
 * computes the averages for each column.
 * If the rows have different sizes, this version fills
 * the shorter ones with zeroes
 * 
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class TableRowsAverager extends TableRow {

    public final List<TableRow> _rows;
    private final Integer _maxDim;

    public TableRowsAverager(List<TableRow<Double>> rows, String rowName) {
        this(rows);
        _rowName = rowName;
    }

    public TableRowsAverager(List<TableRow<Double>> rows) {
        _rowName = (_rowName !=  null) ? _rowName : "averages";
        _rows = new ArrayList<TableRow>(rows);
        _maxDim = Collections.max(Lists.transform(rows, new Function<TableRow, Integer>() {

            public Integer apply(final TableRow tr) {
                return tr.size();
            }
        }));

        // bring all rows to maxDim size
        for (TableRow row : _rows) {
            if (row.size() < _maxDim) {
                for (int ii = row.size(); ii < _maxDim; ii++) {
                    row.add(ii, new Double(0.0));
                }
            }
        }

        // compute averages for each column
        for (Integer x = 0; x < _maxDim; x++) {
            final int currX = x;

            RunningAverageTableRow col = new RunningAverageTableRow(
                    Lists.transform(_rows, new Function<TableRow, Double>() {

                public Double apply(final TableRow row) {
                    return (Double) row.get(currX);
                }
            }));
            this.add(col.get(col.size() - 1));
        }
    }

    public Integer maxDim() {
        return new Integer(_maxDim);
    }
}
