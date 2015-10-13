/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.bean;

import br.com.mw.sysimovel.model.Contribuinte;
import br.com.mw.sysimovel.negocio.ContribuinteRN;
import java.util.List;

/**
 *
 * @author sephi_000
 */
public class ContribuinteMB {

    /**
     * Creates a new instance of ContribuinteMB
     */
    private final ContribuinteRN negocio = new ContribuinteRN();
    private Contribuinte contribuinte;

    public ContribuinteMB(Contribuinte contribuinte) {
        this.contribuinte = contribuinte;
    }
    /**
     * Creates a new instance of ContribuinteMB
     */
    public ContribuinteMB() {
        contribuinte = new Contribuinte();
    }
    
    public void inserirContribuinte(){
        negocio.novoContribuinte(contribuinte);
    }

    public Contribuinte getContribuinte() {
        return contribuinte;
    }

    public void setContribuinte(Contribuinte contribuinte) {
        this.contribuinte = contribuinte;
    }
    
    public List<Contribuinte> getContribuintes() {
        return negocio.listContribuintes();
    }
    
}
