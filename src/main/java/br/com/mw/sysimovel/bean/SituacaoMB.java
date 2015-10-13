/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.bean;

import br.com.mw.sysimovel.model.Situacao;
import br.com.mw.sysimovel.negocio.SituacaoRN;
import java.util.List;

/**
 *
 * @author sephi_000
 */
public class SituacaoMB {

    /**
     * Creates a new instance of SituacaoMB
     */
    private final SituacaoRN negocio = new SituacaoRN();
    private Situacao situacao;

    public SituacaoMB(Situacao situacao) {
        this.situacao = situacao;
    }
    /**
     * Creates a new instance of SituacaoMB
     */
    public SituacaoMB() {
        situacao = new Situacao();
    }
    
    public void inserirSituacao(){
        negocio.novoSituacao(situacao);
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }
    
    public List<Situacao> getSituacaos() {
        return negocio.listSituacaos();
    }
    
}
