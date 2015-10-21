/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.bean;

import br.com.mw.sysimovel.model.Taxa;
import br.com.mw.sysimovel.negocio.TaxaRN;
import java.util.List;

/**
 *
 * @author sephi_000
 */
public class TaxaMB {

    /**
     * Creates a new instance of TaxaMB
     */
    private final TaxaRN negocio = new TaxaRN();
    private Taxa taxa;

    public TaxaMB(Taxa taxa) {
        this.taxa = taxa;
    }
    /**
     * Creates a new instance of TaxaMB
     */
    public TaxaMB() {
        taxa = new Taxa();
    }
    
    public void inserirTaxa(){
        negocio.novoTaxa(taxa);
    }

    public Taxa getTaxa() {
        return taxa;
    }

    public void setTaxa(Taxa taxa) {
        this.taxa = taxa;
    }
    
    public List<Taxa> getTaxas() {
        return negocio.listTaxas();
    }
}
