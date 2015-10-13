/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.bean;

import br.com.mw.sysimovel.model.Utilizacao;
import br.com.mw.sysimovel.negocio.UtilizacaoRN;
import java.util.List;

/**
 *
 * @author sephi_000
 */
public class UtilizacaoMB {

    /**
     * Creates a new instance of UtilizacaoMB
     */
    private final UtilizacaoRN negocio = new UtilizacaoRN();
    private Utilizacao utilizacao;

    public UtilizacaoMB(Utilizacao utilizacao) {
        this.utilizacao = utilizacao;
    }
    /**
     * Creates a new instance of UtilizacaoMB
     */
    public UtilizacaoMB() {
        utilizacao = new Utilizacao();
    }
    
    public void inserirUtilizacao(){
        negocio.novoUtilizacao(utilizacao);
    }

    public Utilizacao getUtilizacao() {
        return utilizacao;
    }

    public void setUtilizacao(Utilizacao utilizacao) {
        this.utilizacao = utilizacao;
    }
    
    public List<Utilizacao> getUtilizacaos() {
        return negocio.listUtilizacaos();
    }
    
}
