/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "limitacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Limitacao.findAll", query = "SELECT l FROM Limitacao l"),
    @NamedQuery(name = "Limitacao.findById", query = "SELECT l FROM Limitacao l WHERE l.id = :id"),
    @NamedQuery(name = "Limitacao.findByDescricao", query = "SELECT l FROM Limitacao l WHERE l.descricao = :descricao"),
    @NamedQuery(name = "Limitacao.findByFator", query = "SELECT l FROM Limitacao l WHERE l.fator = :fator")})
public class Limitacao implements Serializable {
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

    public Limitacao() {
    }

    public Limitacao(Integer id) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Limitacao)) {
            return false;
        }
        Limitacao other = (Limitacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.mw.sysimovel.model.Limitacao[ id=" + id + " ]";
    }
    
}
