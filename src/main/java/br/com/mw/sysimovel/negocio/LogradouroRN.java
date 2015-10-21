/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.negocio;

import br.com.mw.sysimovel.dao.LogradouroJpaController;
import br.com.mw.sysimovel.dao.exceptions.NonexistentEntityException;
import br.com.mw.sysimovel.dao.util.JPAUtil;
import br.com.mw.sysimovel.model.Logradouro;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sephi_000
 */
public class LogradouroRN {
    private final LogradouroJpaController dao;
    
    public LogradouroRN(){
        dao = new LogradouroJpaController(JPAUtil.EMF);
    }
    
    public void novoLogradouro(Logradouro l){
        dao.create(l);
    }
    
    public void alterarLogradouro(Logradouro l){
        try {
            dao.edit(l);
        } catch (Exception ex) {
            Logger.getLogger(LogradouroRN.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void excluirLogradouro(Logradouro l){
        try {
            dao.destroy(l.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(LogradouroRN.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String removerMascara(String text){
        return text.replaceAll("[.-]", "");  
    }

    public List<Logradouro> listLogradouros(){
        return dao.findLogradouroEntities();
    }
}
