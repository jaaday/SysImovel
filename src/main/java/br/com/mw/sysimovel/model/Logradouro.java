/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sephi_000
 */
@Entity
@Table(name = "logradouro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Logradouro.findAll", query = "SELECT l FROM Logradouro l"),
    @NamedQuery(name = "Logradouro.findById", query = "SELECT l FROM Logradouro l WHERE l.id = :id"),
    @NamedQuery(name = "Logradouro.findByTipo", query = "SELECT l FROM Logradouro l WHERE l.tipo = :tipo"),
    @NamedQuery(name = "Logradouro.findByDescricao", query = "SELECT l FROM Logradouro l WHERE l.descricao = :descricao"),
    @NamedQuery(name = "Logradouro.findByCep", query = "SELECT l FROM Logradouro l WHERE l.cep = :cep")})
public class Logradouro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 50)
    @Column(name = "descricao")
    private String descricao;
    @Size(max = 9)
    @Column(name = "cep")
    private String cep;
    @JoinTable(name = "logradouro_taxa", joinColumns = {
        @JoinColumn(name = "logradouro_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "taxa_id", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Taxa> taxaCollection;
    @OneToMany(mappedBy = "logradouroId")
    private Collection<Imovel> imovelCollection;
    @JoinColumn(name = "bairro_id", referencedColumnName = "id")
    @ManyToOne
    private Bairro bairroId;
    @JoinColumn(name = "loteamento_id", referencedColumnName = "id")
    @ManyToOne
    private Loteamento loteamentoId;
    @OneToMany(mappedBy = "logradouroId")
    private Collection<SecaoLogradouro> secaoLogradouroCollection;

    public Logradouro() {
    }

    public Logradouro(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @XmlTransient
    public Collection<Taxa> getTaxaCollection() {
        return taxaCollection;
    }

    public void setTaxaCollection(Collection<Taxa> taxaCollection) {
        this.taxaCollection = taxaCollection;
    }

    @XmlTransient
    public Collection<Imovel> getImovelCollection() {
        return imovelCollection;
    }

    public void setImovelCollection(Collection<Imovel> imovelCollection) {
        this.imovelCollection = imovelCollection;
    }

    public Bairro getBairroId() {
        return bairroId;
    }

    public void setBairroId(Bairro bairroId) {
        this.bairroId = bairroId;
    }

    public Loteamento getLoteamentoId() {
        return loteamentoId;
    }

    public void setLoteamentoId(Loteamento loteamentoId) {
        this.loteamentoId = loteamentoId;
    }

    @XmlTransient
    public Collection<SecaoLogradouro> getSecaoLogradouroCollection() {
        return secaoLogradouroCollection;
    }

    public void setSecaoLogradouroCollection(Collection<SecaoLogradouro> secaoLogradouroCollection) {
        this.secaoLogradouroCollection = secaoLogradouroCollection;
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
        if (!(object instanceof Logradouro)) {
            return false;
        }
        Logradouro other = (Logradouro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.mw.sysimovel.model.Logradouro[ id=" + id + " ]";
    }
    
}
