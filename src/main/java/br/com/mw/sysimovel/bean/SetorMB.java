/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.bean;

import br.com.mw.sysimovel.model.Setor;
import br.com.mw.sysimovel.negocio.SetorRN;
import java.util.List;

/**
 *
 * @author sephi_000
 */
public class SetorMB {

    /**
     * Creates a new instance of SetorMB
     */
    private final SetorRN negocio = new SetorRN();
    private Setor setor;

    public SetorMB(Setor setor) {
        this.setor = setor;
    }
    /**
     * Creates a new instance of SetorMB
     */
    public SetorMB() {
        setor = new Setor();
    }
    
    public void inserirSetor(){
        negocio.novoSetor(setor);
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }
    
    public List<Setor> getSetors() {
        return negocio.listSetors();
    }
    
}
