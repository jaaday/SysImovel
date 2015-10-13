/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.negocio;

import br.com.mw.sysimovel.dao.DistritoJpaController;
import br.com.mw.sysimovel.dao.exceptions.NonexistentEntityException;
import br.com.mw.sysimovel.dao.util.JPAUtil;
import br.com.mw.sysimovel.model.Distrito;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sephi_000
 */
public class DistritoRN {
    private final DistritoJpaController dao;
    
    public DistritoRN(){
        dao = new DistritoJpaController(JPAUtil.EMF);
    }
    
    public void novoDistrito(Distrito d){
        dao.create(d);
    }
    
    public void alterarDistrito(Distrito d){
        try {
            dao.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(DistritoRN.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void excluirDistrito(Distrito t){
        try {
            dao.destroy(t.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(DistritoRN.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Distrito pesquisarDistrito(Distrito d){
        return dao.pesqDistritoNome(d);
    }
    
    public String removerMascara(String text){
        return text.replaceAll("[.-]", "");  
    }

    public List<Distrito> listDistritos(){
        return dao.findDistritoEntities();
    }
}
