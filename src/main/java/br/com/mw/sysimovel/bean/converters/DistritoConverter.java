/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.bean.converters;

import br.com.mw.sysimovel.dao.DistritoJpaController;
import br.com.mw.sysimovel.dao.util.JPAUtil;
import br.com.mw.sysimovel.model.Distrito;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author sephi_000
 */
@FacesConverter(value = "distrito", forClass = Distrito.class)
public class DistritoConverter implements Converter {

    private Distrito distrito = new Distrito();
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        DistritoJpaController dao = new DistritoJpaController(JPAUtil.EMF);
        return dao.findDistrito(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        distrito = (Distrito) value;
        return String.valueOf(distrito.getId());
    }
    
}
