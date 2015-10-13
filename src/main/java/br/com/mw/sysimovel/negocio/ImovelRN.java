/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.negocio;

import br.com.mw.sysimovel.dao.ImovelJpaController;
import br.com.mw.sysimovel.dao.exceptions.NonexistentEntityException;
import br.com.mw.sysimovel.dao.util.JPAUtil;
import br.com.mw.sysimovel.model.Imovel;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sephi_000
 */
public class ImovelRN {
    private final ImovelJpaController dao;
    
    public ImovelRN(){
        dao = new ImovelJpaController(JPAUtil.EMF);
    }
    
    public void novoImovel(Imovel i){
        dao.create(i);
    }
    
    public void alterarImovel(Imovel i){
        try {
            dao.edit(i);
        } catch (Exception ex) {
            Logger.getLogger(ImovelRN.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void excluirImovel(Imovel i){
        try {
            dao.destroy(i.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ImovelRN.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String removerMascara(String text){
        return text.replaceAll("[.-]", "");  
    }

    public List<Imovel> listImovels(){
        return dao.findImovelEntities();
    }
}
