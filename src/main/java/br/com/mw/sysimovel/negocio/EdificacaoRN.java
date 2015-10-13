/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.negocio;

import br.com.mw.sysimovel.dao.EdificacaoJpaController;
import br.com.mw.sysimovel.dao.exceptions.NonexistentEntityException;
import br.com.mw.sysimovel.dao.util.JPAUtil;
import br.com.mw.sysimovel.model.Edificacao;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sephi_000
 */
public class EdificacaoRN {
    private final EdificacaoJpaController dao;
    
    public EdificacaoRN(){
        dao = new EdificacaoJpaController(JPAUtil.EMF);
    }
    
    public void novoEdificacao(Edificacao e){
        dao.create(e);
    }
    
    public void alterarEdificacao(Edificacao e){
        try {
            dao.edit(e);
        } catch (Exception ex) {
            Logger.getLogger(EdificacaoRN.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void excluirEdificacao(Edificacao e){
        try {
            dao.destroy(e.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EdificacaoRN.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String removerMascara(String text){
        return text.replaceAll("[.-]", "");  
    }

    public List<Edificacao> listEdificacaos(){
        return dao.findEdificacaoEntities();
    }
}
