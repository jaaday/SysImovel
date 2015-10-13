/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.negocio;

import br.com.mw.sysimovel.dao.UtilizacaoJpaController;
import br.com.mw.sysimovel.dao.exceptions.NonexistentEntityException;
import br.com.mw.sysimovel.dao.util.JPAUtil;
import br.com.mw.sysimovel.model.Utilizacao;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sephi_000
 */
public class UtilizacaoRN {
    
    private final UtilizacaoJpaController dao;
    
    public UtilizacaoRN(){
        dao = new UtilizacaoJpaController(JPAUtil.EMF);
    }
    
    public void novoUtilizacao(Utilizacao u){
        dao.create(u);
    }
    
    public void alterarUtilizacao(Utilizacao u){
        try {
            dao.edit(u);
        } catch (Exception ex) {
            Logger.getLogger(UtilizacaoRN.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void excluirUtilizacao(Utilizacao u){
        try {
            dao.destroy(u.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(UtilizacaoRN.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Utilizacao pesquisarUtilizacao(Utilizacao u){
        return dao.pesqUtilizacaoNome(u);
    }
    
    public String removerMascara(String text){
        return text.replaceAll("[.-]", "");  
    }

    public List<Utilizacao> listUtilizacaos(){
        return dao.findUtilizacaoEntities();
    }
}
