/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.bean;

import br.com.mw.sysimovel.model.Logradouro;
import br.com.mw.sysimovel.model.Taxa;
import br.com.mw.sysimovel.negocio.LogradouroRN;
import java.util.List;

/**
 *
 * @author sephi_000
 */
public class LogradouroMB {

    /**
     * Creates a new instance of LogradouroMB
     */
    private final LogradouroRN negocio = new LogradouroRN();
    private Logradouro logradouro;
    private Taxa taxa;

    public LogradouroMB(Logradouro logradouro) {
        this.logradouro = logradouro;
        this.taxa = new Taxa();
    }
    /**
     * Creates a new instance of LogradouroMB
     */
    public LogradouroMB() {
        this.logradouro = new Logradouro();
        this.taxa = new Taxa();
    }
    
    public void inserirLogradouro(){
        negocio.novoLogradouro(logradouro);
    }

    public Logradouro getLogradouro() {
        return logradouro;
    }

    public void setNatureza(Logradouro logradouro) {
        this.logradouro = logradouro;
    }
    
    public List<Logradouro> getLogradouros() {
        return negocio.listLogradouros();
    }

    public Taxa getTaxa() {
        return taxa;
    }

    public void setTaxa(Taxa taxa) {
        this.taxa = taxa;
    }
    
    public void adicionarTaxa(){
        this.logradouro.getTaxaCollection().add(taxa);
        taxa = new Taxa();
    }
}
