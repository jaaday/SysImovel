/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sephi_000
 */
@Entity
@Table(name = "imovel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Imovel.findAll", query = "SELECT i FROM Imovel i"),
    @NamedQuery(name = "Imovel.findById", query = "SELECT i FROM Imovel i WHERE i.id = :id"),
    @NamedQuery(name = "Imovel.findByLote", query = "SELECT i FROM Imovel i WHERE i.lote = :lote"),
    @NamedQuery(name = "Imovel.findByUnidade", query = "SELECT i FROM Imovel i WHERE i.unidade = :unidade"),
    @NamedQuery(name = "Imovel.findByQuadra", query = "SELECT i FROM Imovel i WHERE i.quadra = :quadra"),
    @NamedQuery(name = "Imovel.findByEnderecoCorrespondencia", query = "SELECT i FROM Imovel i WHERE i.enderecoCorrespondencia = :enderecoCorrespondencia"),
    @NamedQuery(name = "Imovel.findByNumeroCorrespondencia", query = "SELECT i FROM Imovel i WHERE i.numeroCorrespondencia = :numeroCorrespondencia"),
    @NamedQuery(name = "Imovel.findByBairroCorrespondencia", query = "SELECT i FROM Imovel i WHERE i.bairroCorrespondencia = :bairroCorrespondencia"),
    @NamedQuery(name = "Imovel.findByCidadeCorrespondencia", query = "SELECT i FROM Imovel i WHERE i.cidadeCorrespondencia = :cidadeCorrespondencia"),
    @NamedQuery(name = "Imovel.findByCepCorrespondencia", query = "SELECT i FROM Imovel i WHERE i.cepCorrespondencia = :cepCorrespondencia"),
    @NamedQuery(name = "Imovel.findByComplementoCorrespondencia", query = "SELECT i FROM Imovel i WHERE i.complementoCorrespondencia = :complementoCorrespondencia")})
public class Imovel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 4)
    @Column(name = "lote")
    private String lote;
    @Size(max = 3)
    @Column(name = "unidade")
    private String unidade;
    @Size(max = 3)
    @Column(name = "quadra")
    private String quadra;
    @Size(max = 50)
    @Column(name = "endereco_correspondencia")
    private String enderecoCorrespondencia;
    @Size(max = 10)
    @Column(name = "numero_correspondencia")
    private String numeroCorrespondencia;
    @Size(max = 50)
    @Column(name = "bairro_correspondencia")
    private String bairroCorrespondencia;
    @Size(max = 50)
    @Column(name = "cidade_correspondencia")
    private String cidadeCorrespondencia;
    @Size(max = 9)
    @Column(name = "cep_correspondencia")
    private String cepCorrespondencia;
    @Size(max = 50)
    @Column(name = "complemento_correspondencia")
    private String complementoCorrespondencia;
    @JoinColumn(name = "contribuinte_id", referencedColumnName = "id")
    @ManyToOne
    private Contribuinte contribuinteId;
    @JoinColumn(name = "edificacao_id", referencedColumnName = "id")
    @ManyToOne
    private Edificacao edificacaoId;
    @JoinColumn(name = "logradouro_id", referencedColumnName = "id")
    @ManyToOne
    private Logradouro logradouroId;
    @JoinColumn(name = "natureza_id", referencedColumnName = "id")
    @ManyToOne
    private Natureza naturezaId;
    @JoinColumn(name = "setor_id", referencedColumnName = "id")
    @ManyToOne
    private Setor setorId;

    public Imovel() {
        contribuinteId = new Contribuinte();
        edificacaoId = new Edificacao();
        logradouroId = new Logradouro();
        naturezaId = new Natureza();
        setorId = new Setor();
    }

    public Imovel(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getQuadra() {
        return quadra;
    }

    public void setQuadra(String quadra) {
        this.quadra = quadra;
    }

    public String getEnderecoCorrespondencia() {
        return enderecoCorrespondencia;
    }

    public void setEnderecoCorrespondencia(String enderecoCorrespondencia) {
        this.enderecoCorrespondencia = enderecoCorrespondencia;
    }

    public String getNumeroCorrespondencia() {
        return numeroCorrespondencia;
    }

    public void setNumeroCorrespondencia(String numeroCorrespondencia) {
        this.numeroCorrespondencia = numeroCorrespondencia;
    }

    public String getBairroCorrespondencia() {
        return bairroCorrespondencia;
    }

    public void setBairroCorrespondencia(String bairroCorrespondencia) {
        this.bairroCorrespondencia = bairroCorrespondencia;
    }

    public String getCidadeCorrespondencia() {
        return cidadeCorrespondencia;
    }

    public void setCidadeCorrespondencia(String cidadeCorrespondencia) {
        this.cidadeCorrespondencia = cidadeCorrespondencia;
    }

    public String getCepCorrespondencia() {
        return cepCorrespondencia;
    }

    public void setCepCorrespondencia(String cepCorrespondencia) {
        this.cepCorrespondencia = cepCorrespondencia;
    }

    public String getComplementoCorrespondencia() {
        return complementoCorrespondencia;
    }

    public void setComplementoCorrespondencia(String complementoCorrespondencia) {
        this.complementoCorrespondencia = complementoCorrespondencia;
    }

    public Contribuinte getContribuinteId() {
        return contribuinteId;
    }

    public void setContribuinteId(Contribuinte contribuinteId) {
        this.contribuinteId = contribuinteId;
    }

    public Edificacao getEdificacaoId() {
        return edificacaoId;
    }

    public void setEdificacaoId(Edificacao edificacaoId) {
        this.edificacaoId = edificacaoId;
    }

    public Logradouro getLogradouroId() {
        return logradouroId;
    }

    public void setLogradouroId(Logradouro logradouroId) {
        this.logradouroId = logradouroId;
    }

    public Natureza getNaturezaId() {
        return naturezaId;
    }

    public void setNaturezaId(Natureza naturezaId) {
        this.naturezaId = naturezaId;
    }

    public Setor getSetorId() {
        return setorId;
    }

    public void setSetorId(Setor setorId) {
        this.setorId = setorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Imovel)) {
            return false;
        }
        Imovel other = (Imovel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.mw.sysimovel.model.Imovel[ id=" + id + " ]";
    }
    
}
