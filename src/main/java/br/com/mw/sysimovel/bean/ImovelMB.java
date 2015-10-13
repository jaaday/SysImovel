/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.bean;

import br.com.mw.sysimovel.model.Imovel;
import br.com.mw.sysimovel.negocio.ImovelRN;
import java.util.List;

/**
 *
 * @author sephi_000
 */
public class ImovelMB {

    /**
     * Creates a new instance of ImovelMB
     */
    private final ImovelRN negocio = new ImovelRN();
    private Imovel imovel;

    public ImovelMB(Imovel imovel) {
        this.imovel = imovel;
    }
    /**
     * Creates a new instance of ImovelMB
     */
    public ImovelMB() {
        imovel = new Imovel();
    }
    
    public void inserirImovel(){
        negocio.novoImovel(imovel);
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }
    
    public List<Imovel> getImovels() {
        return negocio.listImovels();
    }
    
}
