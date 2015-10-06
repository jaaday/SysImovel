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
@Table(name = "loteamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Loteamento.findAll", query = "SELECT l FROM Loteamento l"),
    @NamedQuery(name = "Loteamento.findById", query = "SELECT l FROM Loteamento l WHERE l.id = :id"),
    @NamedQuery(name = "Loteamento.findByDescricao", query = "SELECT l FROM Loteamento l WHERE l.descricao = :descricao")})
public class Loteamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(mappedBy = "loteamentoId")
    private Collection<Logradouro> logradouroCollection;
    @JoinColumn(name = "setor_id", referencedColumnName = "id")
    @ManyToOne
    private Setor setorId;

    public Loteamento() {
    }

    public Loteamento(Integer id) {
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

    @XmlTransient
    public Collection<Logradouro> getLogradouroCollection() {
        return logradouroCollection;
    }

    public void setLogradouroCollection(Collection<Logradouro> logradouroCollection) {
        this.logradouroCollection = logradouroCollection;
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
        if (!(object instanceof Loteamento)) {
            return false;
        }
        Loteamento other = (Loteamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.mw.sysimovel.model.Loteamento[ id=" + id + " ]";
    }
    
}
