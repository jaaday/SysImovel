/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.negocio;

import br.com.mw.sysimovel.dao.NaturezaJpaController;
import br.com.mw.sysimovel.dao.exceptions.NonexistentEntityException;
import br.com.mw.sysimovel.dao.util.JPAUtil;
import br.com.mw.sysimovel.model.Natureza;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sephi_000
 */
public class NaturezaRN {
    private final NaturezaJpaController dao;
    
    public NaturezaRN(){
        dao = new NaturezaJpaController(JPAUtil.EMF);
    }
    
    public void novoNatureza(Natureza n){
        dao.create(n);
    }
    
    public void alterarNatureza(Natureza n){
        try {
            dao.edit(n);
        } catch (Exception ex) {
            Logger.getLogger(NaturezaRN.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void excluirNatureza(Natureza n){
        try {
            dao.destroy(n.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(NaturezaRN.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String removerMascara(String text){
        return text.replaceAll("[.-]", "");  
    }

    public List<Natureza> listNaturezas(){
        return dao.findNaturezaEntities();
    }
}
