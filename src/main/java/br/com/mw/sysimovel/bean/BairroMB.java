/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.bean;

import br.com.mw.sysimovel.model.Bairro;
import br.com.mw.sysimovel.negocio.BairroRN;
import java.util.List;

/**
 *
 * @author sephi_000
 */
public class BairroMB {

    private final BairroRN negocio = new BairroRN();
    private Bairro bairro;

    public BairroMB(Bairro bairro) {
        this.bairro = bairro;
    }
    /**
     * Creates a new instance of BairroMB
     */
    public BairroMB() {
        bairro = new Bairro();
    }
    
    public void inserirBairro(){
        negocio.novoBairro(bairro);
    }

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }
    
    public List<Bairro> getBairros() {
        return negocio.listBairros();
    }
    
}
