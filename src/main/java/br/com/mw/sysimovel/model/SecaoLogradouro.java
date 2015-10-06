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
@Table(name = "secao_logradouro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecaoLogradouro.findAll", query = "SELECT s FROM SecaoLogradouro s"),
    @NamedQuery(name = "SecaoLogradouro.findById", query = "SELECT s FROM SecaoLogradouro s WHERE s.id = :id"),
    @NamedQuery(name = "SecaoLogradouro.findByCodigo", query = "SELECT s FROM SecaoLogradouro s WHERE s.codigo = :codigo"),
    @NamedQuery(name = "SecaoLogradouro.findByDescricao", query = "SELECT s FROM SecaoLogradouro s WHERE s.descricao = :descricao"),
    @NamedQuery(name = "SecaoLogradouro.findByValorTerreno", query = "SELECT s FROM SecaoLogradouro s WHERE s.valorTerreno = :valorTerreno")})
public class SecaoLogradouro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 7)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 50)
    @Column(name = "descricao")
    private String descricao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_terreno")
    private BigDecimal valorTerreno;
    @JoinColumn(name = "logradouro_id", referencedColumnName = "id")
    @ManyToOne
    private Logradouro logradouroId;

    public SecaoLogradouro() {
    }

    public SecaoLogradouro(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorTerreno() {
        return valorTerreno;
    }

    public void setValorTerreno(BigDecimal valorTerreno) {
        this.valorTerreno = valorTerreno;
    }

    public Logradouro getLogradouroId() {
        return logradouroId;
    }

    public void setLogradouroId(Logradouro logradouroId) {
        this.logradouroId = logradouroId;
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
        if (!(object instanceof SecaoLogradouro)) {
            return false;
        }
        SecaoLogradouro other = (SecaoLogradouro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.mw.sysimovel.model.SecaoLogradouro[ id=" + id + " ]";
    }
    
}
