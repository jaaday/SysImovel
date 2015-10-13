/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.bean.converters;

import br.com.mw.sysimovel.dao.NaturezaJpaController;
import br.com.mw.sysimovel.dao.util.JPAUtil;
import br.com.mw.sysimovel.model.Natureza;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author sephi_000
 */
@FacesConverter(value = "natureza", forClass = Natureza.class)
public class NaturezaConverter implements Converter {
    private Natureza natureza = new Natureza();
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        NaturezaJpaController dao = new NaturezaJpaController(JPAUtil.EMF);
        return dao.findNatureza(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        natureza = (Natureza) value;
        return String.valueOf(natureza.getId());
    }
}