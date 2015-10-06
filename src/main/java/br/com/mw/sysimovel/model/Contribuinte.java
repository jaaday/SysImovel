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
@Table(name = "contribuinte")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contribuinte.findAll", query = "SELECT c FROM Contribuinte c"),
    @NamedQuery(name = "Contribuinte.findById", query = "SELECT c FROM Contribuinte c WHERE c.id = :id"),
    @NamedQuery(name = "Contribuinte.findByTipo", query = "SELECT c FROM Contribuinte c WHERE c.tipo = :tipo"),
    @NamedQuery(name = "Contribuinte.findByCnpj", query = "SELECT c FROM Contribuinte c WHERE c.cnpj = :cnpj"),
    @NamedQuery(name = "Contribuinte.findByCpf", query = "SELECT c FROM Contribuinte c WHERE c.cpf = :cpf"),
    @NamedQuery(name = "Contribuinte.findByTipoLogradouro", query = "SELECT c FROM Contribuinte c WHERE c.tipoLogradouro = :tipoLogradouro"),
    @NamedQuery(name = "Contribuinte.findByNome", query = "SELECT c FROM Contribuinte c WHERE c.nome = :nome"),
    @NamedQuery(name = "Contribuinte.findByEndereco", query = "SELECT c FROM Contribuinte c WHERE c.endereco = :endereco"),
    @NamedQuery(name = "Contribuinte.findByComplemento", query = "SELECT c FROM Contribuinte c WHERE c.complemento = :complemento"),
    @NamedQuery(name = "Contribuinte.findByNumero", query = "SELECT c FROM Contribuinte c WHERE c.numero = :numero"),
    @NamedQuery(name = "Contribuinte.findByBairro", query = "SELECT c FROM Contribuinte c WHERE c.bairro = :bairro"),
    @NamedQuery(name = "Contribuinte.findByCidade", query = "SELECT c FROM Contribuinte c WHERE c.cidade = :cidade"),
    @NamedQuery(name = "Contribuinte.findByUf", query = "SELECT c FROM Contribuinte c WHERE c.uf = :uf"),
    @NamedQuery(name = "Contribuinte.findByCep", query = "SELECT c FROM Contribuinte c WHERE c.cep = :cep"),
    @NamedQuery(name = "Contribuinte.findByFone", query = "SELECT c FROM Contribuinte c WHERE c.fone = :fone"),
    @NamedQuery(name = "Contribuinte.findByEmail", query = "SELECT c FROM Contribuinte c WHERE c.email = :email")})
public class Contribuinte implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "tipo")
    private Integer tipo;
    @Size(max = 18)
    @Column(name = "cnpj")
    private String cnpj;
    @Size(max = 14)
    @Column(name = "cpf")
    private String cpf;
    @Size(max = 20)
    @Column(name = "tipo_logradouro")
    private String tipoLogradouro;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @Size(max = 50)
    @Column(name = "endereco")
    private String endereco;
    @Size(max = 50)
    @Column(name = "complemento")
    private String complemento;
    @Size(max = 10)
    @Column(name = "numero")
    private String numero;
    @Size(max = 50)
    @Column(name = "bairro")
    private String bairro;
    @Size(max = 50)
    @Column(name = "cidade")
    private String cidade;
    @Size(max = 2)
    @Column(name = "uf")
    private String uf;
    @Size(max = 9)
    @Column(name = "cep")
    private String cep;
    @Size(max = 20)
    @Column(name = "fone")
    private String fone;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "contribuinteId")
    private Collection<Imovel> imovelCollection;

    public Contribuinte() {
    }

    public Contribuinte(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTipoLogradouro() {
        return tipoLogradouro;
    }

    public void setTipoLogradouro(String tipoLogradouro) {
        this.tipoLogradouro = tipoLogradouro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        if (!(object instanceof Contribuinte)) {
            return false;
        }
        Contribuinte other = (Contribuinte) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.mw.sysimovel.model.Contribuinte[ id=" + id + " ]";
    }
    
}
