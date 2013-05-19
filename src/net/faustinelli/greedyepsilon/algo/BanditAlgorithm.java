/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.faustinelli.greedyepsilon.algo;

/**
 *
 * @author Marco Faustinelli <contatti@faustinelli.net>
 */
public interface BanditAlgorithm {

    void initialize();

    Integer selectArm();

    void update(Integer armIndex, Double reward);

    /**
     * Explore-Exploit choice parameter
     */
    public Double ee_parameter();

    public void ee_parameter(Double ee_parameter);

    public void identifier(String identifier);

    public String identifier();

    public String logMessage();
}
