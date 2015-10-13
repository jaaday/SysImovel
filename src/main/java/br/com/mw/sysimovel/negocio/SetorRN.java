/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.negocio;

import br.com.mw.sysimovel.dao.SetorJpaController;
import br.com.mw.sysimovel.dao.exceptions.NonexistentEntityException;
import br.com.mw.sysimovel.dao.util.JPAUtil;
import br.com.mw.sysimovel.model.Setor;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sephi_000
 */
public class SetorRN {
    private final SetorJpaController dao;
    
    public SetorRN(){
        dao = new SetorJpaController(JPAUtil.EMF);
    }
    
    public void novoSetor(Setor s){
        dao.create(s);
    }
    
    public void alterarSetor(Setor s){
        try {
            dao.edit(s);
        } catch (Exception ex) {
            Logger.getLogger(SetorRN.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void excluirSetor(Setor s){
        try {
            dao.destroy(s.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(SetorRN.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Setor pesquisarSetor(Setor s){
        return dao.pesqSetorNome(s);
    }
    
    public String removerMascara(String text){
        return text.replaceAll("[.-]", "");  
    }

    public List<Setor> listSetors(){
        return dao.findSetorEntities();
    }
}
