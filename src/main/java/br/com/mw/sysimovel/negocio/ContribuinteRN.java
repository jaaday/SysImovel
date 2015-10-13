/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.negocio;

import br.com.mw.sysimovel.dao.ContribuinteJpaController;
import br.com.mw.sysimovel.dao.exceptions.NonexistentEntityException;
import br.com.mw.sysimovel.dao.util.JPAUtil;
import br.com.mw.sysimovel.model.Contribuinte;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sephi_000
 */
public class ContribuinteRN {

    private final ContribuinteJpaController dao;
    
    public ContribuinteRN(){
        dao = new ContribuinteJpaController(JPAUtil.EMF);
    }
    
    public void novoContribuinte(Contribuinte b){
        dao.create(b);
    }
    
    public void alterarContribuinte(Contribuinte b){
        try {
            dao.edit(b);
        } catch (Exception ex) {
            Logger.getLogger(ContribuinteRN.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void excluirContribuinte(Contribuinte b){
        try {
            dao.destroy(b.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ContribuinteRN.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Contribuinte pesquisarContribuinte(Contribuinte e){
        return dao.pesqContribuinteNome(e);
    }
    
    public String removerMascara(String text){
        return text.replaceAll("[.-]", "");  
    }

    public List<Contribuinte> listContribuintes(){
        return dao.findContribuinteEntities();
    }
    
}
