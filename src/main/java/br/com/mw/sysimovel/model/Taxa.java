/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sephi_000
 */
@Entity
@Table(name = "taxa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Taxa.findAll", query = "SELECT t FROM Taxa t"),
    @NamedQuery(name = "Taxa.findById", query = "SELECT t FROM Taxa t WHERE t.id = :id"),
    @NamedQuery(name = "Taxa.findByDescricao", query = "SELECT t FROM Taxa t WHERE t.descricao = :descricao"),
    @NamedQuery(name = "Taxa.findByTaxaTerreno", query = "SELECT t FROM Taxa t WHERE t.taxaTerreno = :taxaTerreno"),
    @NamedQuery(name = "Taxa.findByTaxaConstrucao", query = "SELECT t FROM Taxa t WHERE t.taxaConstrucao = :taxaConstrucao")})
public class Taxa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "descricao")
    private String descricao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "taxa_terreno")
    private BigDecimal taxaTerreno;
    @Column(name = "taxa_construcao")
    private BigDecimal taxaConstrucao;
    @ManyToMany(mappedBy = "taxaCollection")
    private Collection<Logradouro> logradouroCollection;

    public Taxa() {
    }

    public Taxa(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getTaxaTerreno() {
        return taxaTerreno;
    }

    public void setTaxaTerreno(BigDecimal taxaTerreno) {
        this.taxaTerreno = taxaTerreno;
    }

    public BigDecimal getTaxaConstrucao() {
        return taxaConstrucao;
    }

    public void setTaxaConstrucao(BigDecimal taxaConstrucao) {
        this.taxaConstrucao = taxaConstrucao;
    }

    @XmlTransient
    public Collection<Logradouro> getLogradouroCollection() {
        return logradouroCollection;
    }

    public void setLogradouroCollection(Collection<Logradouro> logradouroCollection) {
        this.logradouroCollection = logradouroCollection;
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
        if (!(object instanceof Taxa)) {
            return false;
        }
        Taxa other = (Taxa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.mw.sysimovel.model.Taxa[ id=" + id + " ]";
    }
    
}
