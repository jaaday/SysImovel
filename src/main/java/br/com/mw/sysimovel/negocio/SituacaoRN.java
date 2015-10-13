/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.negocio;

import br.com.mw.sysimovel.dao.SituacaoJpaController;
import br.com.mw.sysimovel.dao.exceptions.NonexistentEntityException;
import br.com.mw.sysimovel.dao.util.JPAUtil;
import br.com.mw.sysimovel.model.Situacao;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sephi_000
 */
public class SituacaoRN {
    private final SituacaoJpaController dao;
    
    public SituacaoRN(){
        dao = new SituacaoJpaController(JPAUtil.EMF);
    }
    
    public void novoSituacao(Situacao t){
        dao.create(t);
    }
    
    public void alterarSituacao(Situacao t){
        try {
            dao.edit(t);
        } catch (Exception ex) {
            Logger.getLogger(SituacaoRN.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void excluirUtilizacao(Situacao t){
        try {
            dao.destroy(t.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(SituacaoRN.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Situacao pesquisarSituacao(Situacao t){
        return dao.pesqSituacaoNome(t);
    }
    
    public String removerMascara(String text){
        return text.replaceAll("[.-]", "");  
    }

    public List<Situacao> listSituacaos(){
        return dao.findSituacaoEntities();
    }
}
