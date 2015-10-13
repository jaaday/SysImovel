/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.bean;

import br.com.mw.sysimovel.model.Edificacao;
import br.com.mw.sysimovel.negocio.EdificacaoRN;
import java.util.List;

/**
 *
 * @author sephi_000
 */
public class EdificacaoMB {

    /**
     * Creates a new instance of EdificacaoMB
     */
    private final EdificacaoRN negocio = new EdificacaoRN();
    private Edificacao edificacao;

    public EdificacaoMB(Edificacao edificacao) {
        this.edificacao = edificacao;
    }
    /**
     * Creates a new instance of EdificacaoMB
     */
    public EdificacaoMB() {
        edificacao = new Edificacao();
    }
    
    public void inserirEdificacao(){
        negocio.novoEdificacao(edificacao);
    }

    public Edificacao getEdificacao() {
        return edificacao;
    }

    public void setEdificacao(Edificacao edificacao) {
        this.edificacao = edificacao;
    }
    
    public List<Edificacao> getEdificacaos() {
        return negocio.listEdificacaos();
    }
    
}
