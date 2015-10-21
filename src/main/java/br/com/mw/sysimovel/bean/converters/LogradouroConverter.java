/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.bean.converters;

import br.com.mw.sysimovel.dao.LogradouroJpaController;
import br.com.mw.sysimovel.dao.util.JPAUtil;
import br.com.mw.sysimovel.model.Logradouro;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author sephi_000
 */
@FacesConverter(value = "logradouro", forClass = Logradouro.class)
public class LogradouroConverter implements Converter {

    private Logradouro logradouro = new Logradouro();
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        LogradouroJpaController dao = new LogradouroJpaController(JPAUtil.EMF);
        return dao.findLogradouro(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        logradouro = (Logradouro) value;
        return String.valueOf(logradouro.getId());
    }
    
}
