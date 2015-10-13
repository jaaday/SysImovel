/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.negocio;

import br.com.mw.sysimovel.dao.TopografiaJpaController;
import br.com.mw.sysimovel.dao.exceptions.NonexistentEntityException;
import br.com.mw.sysimovel.dao.util.JPAUtil;
import br.com.mw.sysimovel.model.Topografia;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sephi_000
 */
public class TopografiaRN {
    private final TopografiaJpaController dao;
    
    public TopografiaRN(){
        dao = new TopografiaJpaController(JPAUtil.EMF);
    }
    
    public void novoTopografia(Topografia t){
        dao.create(t);
    }
    
    public void alterarTopografia(Topografia t){
        try {
            dao.edit(t);
        } catch (Exception ex) {
            Logger.getLogger(TopografiaRN.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void excluirUtilizacao(Topografia t){
        try {
            dao.destroy(t.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TopografiaRN.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Topografia pesquisarTopografia(Topografia t){
        return dao.pesqTopografiaNome(t);
    }
    
    public String removerMascara(String text){
        return text.replaceAll("[.-]", "");  
    }

    public List<Topografia> listTopografias(){
        return dao.findTopografiaEntities();
    }
}
