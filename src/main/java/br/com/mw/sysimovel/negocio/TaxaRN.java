/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.negocio;

import br.com.mw.sysimovel.dao.TaxaJpaController;
import br.com.mw.sysimovel.dao.exceptions.NonexistentEntityException;
import br.com.mw.sysimovel.dao.util.JPAUtil;
import br.com.mw.sysimovel.model.Taxa;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sephi_000
 */
public class TaxaRN {
    private final TaxaJpaController dao;
    
    public TaxaRN(){
        dao = new TaxaJpaController(JPAUtil.EMF);
    }
    
    public void novoTaxa(Taxa t){
        dao.create(t);
    }
    
    public void alterarTaxa(Taxa t){
        try {
            dao.edit(t);
        } catch (Exception ex) {
            Logger.getLogger(TaxaRN.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void excluirTaxa(Taxa t){
        try {
            dao.destroy(t.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TaxaRN.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String removerMascara(String text){
        return text.replaceAll("[.-]", "");  
    }

    public List<Taxa> listTaxas(){
        return dao.findTaxaEntities();
    }
}
