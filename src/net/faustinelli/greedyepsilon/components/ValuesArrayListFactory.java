/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.faustinelli.greedyepsilon.components;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public class ValuesArrayListFactory implements ValuesListFactory{

    public List<Double> valuesList(Integer armsNo) {
       return new ArrayList<Double>(armsNo);
    }

}
