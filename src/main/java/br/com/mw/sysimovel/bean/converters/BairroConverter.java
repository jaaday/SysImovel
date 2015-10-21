/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.bean.converters;

import br.com.mw.sysimovel.dao.BairroJpaController;
import br.com.mw.sysimovel.dao.util.JPAUtil;
import br.com.mw.sysimovel.model.Bairro;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author sephi_000
 */
@FacesConverter(value = "bairro", forClass = Bairro.class)
public class BairroConverter implements Converter {

    private Bairro bairro = new Bairro();
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        BairroJpaController dao = new BairroJpaController(JPAUtil.EMF);
        return dao.findBairro(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        bairro = (Bairro) value;
        return String.valueOf(bairro.getId());
    }
    
}