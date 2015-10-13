/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.bean;

import br.com.mw.sysimovel.model.Topografia;
import br.com.mw.sysimovel.negocio.TopografiaRN;
import java.util.List;

/**
 *
 * @author sephi_000
 */
public class TopografiaMB {

    /**
     * Creates a new instance of TopografiaMB
     */
    private final TopografiaRN negocio = new TopografiaRN();
    private Topografia topografia;

    public TopografiaMB(Topografia topografia) {
        this.topografia = topografia;
    }
    /**
     * Creates a new instance of TopografiaMB
     */
    public TopografiaMB() {
        topografia = new Topografia();
    }
    
    public void inserirTopografia(){
        negocio.novoTopografia(topografia);
    }

    public Topografia getTopografia() {
        return topografia;
    }

    public void setTopografia(Topografia topografia) {
        this.topografia = topografia;
    }
    
    public List<Topografia> getTopografias() {
        return negocio.listTopografias();
    }
    
}
