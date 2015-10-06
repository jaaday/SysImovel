/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.negocio;

import br.com.mw.sysimovel.dao.BairroJpaController;
import br.com.mw.sysimovel.dao.exceptions.NonexistentEntityException;
import br.com.mw.sysimovel.dao.util.JPAUtil;
import br.com.mw.sysimovel.model.Bairro;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sephi_000
 */
public class BairroRN {
    
    private final BairroJpaController dao;
    
    public BairroRN(){
        dao = new BairroJpaController(JPAUtil.EMF);
    }
    
    public void novoBairro(Bairro b){
        dao.create(b);
    }
    
    public void alterarBairro(Bairro b){
        try {
            dao.edit(b);
        } catch (Exception ex) {
            Logger.getLogger(BairroRN.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void excluirEditora(Bairro b){
        try {
            dao.destroy(b.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BairroRN.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Bairro pesquisarBairro(Bairro e){
        return dao.pesqBairroNome(e);
    }
    
    public String removerMascara(String text){
        return text.replaceAll("[.-]", "");  
    }

    public List<Bairro> listBairros(){
        return dao.findBairroEntities();
    }
}
