/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.table;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
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

        for (T temp : this) {
            Integer ii = this.indexOf(temp);
            Double dThisUp = (this.get(ii) instanceof Integer) ? (double) ((Integer) this.get(ii)).intValue() : (Double) this.get(ii);
            Double dThisDown = (this.get(ii) instanceof Integer) ? (double) ((Integer) divisor.get(ii)).intValue() : (Double) divisor.get(ii);
            result.add(ii, (dThisUp * 1.0 / dThisDown));
        }

        return result;
    }

    public void writeCsvRow(Writer writer) throws IOException {
        this.writeCsvRow(writer, ",");
    }

    public void writeCsvRow(Writer writer, String separator) throws IOException {
        String _separator = (separator != null) ? separator : ",";
        Iterator thisIt = this.iterator();

        writer.append(this._rowName + _separator);
        while (thisIt.hasNext()) {
            writer.append(thisIt.next().toString() + _separator);
        }
        writer.append("\n");
    }

    @Override
    public String toString() {
        String result = "{" + _rowName+ ";";// + "/" + this.size() + ";";
        for (Integer ii = 0; ii< this.size();ii++) {
            result += this.get(ii).toString();// + "/" + ii;
            if (ii < this.size()-1) {
                result += ",";
            }
        }
/*        for (T item : this) {
            result += item.toString() + "/" + this.indexOf(item);
            if (this.indexOf(item) < this.size()) {
                result += ",";
            }
        }
 *
 */
        result += "}";
        return result;
    }
}
