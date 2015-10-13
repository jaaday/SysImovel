/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.bean;

import br.com.mw.sysimovel.model.Distrito;
import br.com.mw.sysimovel.negocio.DistritoRN;
import java.util.List;

/**
 *
 * @author sephi_000
 */
public class DistritoMB {

    /**
     * Creates a new instance of DistritoMB
     */
    private final DistritoRN negocio = new DistritoRN();
    private Distrito distrito;

    public DistritoMB(Distrito distrito) {
        this.distrito = distrito;
    }
    /**
     * Creates a new instance of DistritoMB
     */
    public DistritoMB() {
        distrito = new Distrito();
    }
    
    public void inserirDistrito(){
        negocio.novoDistrito(distrito);
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }
    
    public List<Distrito> getDistritos() {
        return negocio.listDistritos();
    }
    
}
