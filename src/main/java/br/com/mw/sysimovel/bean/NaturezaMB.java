/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.bean;

import br.com.mw.sysimovel.model.Natureza;
import br.com.mw.sysimovel.negocio.NaturezaRN;
import java.util.List;

/**
 *
 * @author sephi_000
 */
public class NaturezaMB {

    /**
     * Creates a new instance of NaturezaMB
     */
    private final NaturezaRN negocio = new NaturezaRN();
    private Natureza natureza;

    public NaturezaMB(Natureza natureza) {
        this.natureza = natureza;
    }
    /**
     * Creates a new instance of NaturezaMB
     */
    public NaturezaMB() {
        natureza = new Natureza();
    }
    
    public void inserirNatureza(){
        negocio.novoNatureza(natureza);
    }

    public Natureza getNatureza() {
        return natureza;
    }

    public void setNatureza(Natureza natureza) {
        this.natureza = natureza;
    }
    
    public List<Natureza> getNaturezas() {
        return negocio.listNaturezas();
    }
}
