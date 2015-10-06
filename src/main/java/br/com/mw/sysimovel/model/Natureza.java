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
@Table(name = "natureza")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Natureza.findAll", query = "SELECT n FROM Natureza n"),
    @NamedQuery(name = "Natureza.findById", query = "SELECT n FROM Natureza n WHERE n.id = :id"),
    @NamedQuery(name = "Natureza.findByDescricao", query = "SELECT n FROM Natureza n WHERE n.descricao = :descricao"),
    @NamedQuery(name = "Natureza.findByFator", query = "SELECT n FROM Natureza n WHERE n.fator = :fator")})
public class Natureza implements Serializable {
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
    @Column(name = "fator")
    private BigDecimal fator;
    @OneToMany(mappedBy = "naturezaId")
    private Collection<Imovel> imovelCollection;

    public Natureza() {
    }

    public Natureza(Integer id) {
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

    public BigDecimal getFator() {
        return fator;
    }

    public void setFator(BigDecimal fator) {
        this.fator = fator;
    }

    @XmlTransient
    public Collection<Imovel> getImovelCollection() {
        return imovelCollection;
    }

    public void setImovelCollection(Collection<Imovel> imovelCollection) {
        this.imovelCollection = imovelCollection;
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
        if (!(object instanceof Natureza)) {
            return false;
        }
        Natureza other = (Natureza) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.mw.sysimovel.model.Natureza[ id=" + id + " ]";
    }
    
}
