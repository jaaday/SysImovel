/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.bean.converters;

import br.com.mw.sysimovel.dao.TaxaJpaController;
import br.com.mw.sysimovel.dao.util.JPAUtil;
import br.com.mw.sysimovel.model.Taxa;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author sephi_000
 */
@FacesConverter(value = "taxa", forClass = Taxa.class)
public class TaxaConverter implements Converter {
    private Taxa taxa = new Taxa();
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        TaxaJpaController dao = new TaxaJpaController(JPAUtil.EMF);
        return dao.findTaxa(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        taxa = (Taxa) value;
        return String.valueOf(taxa.getId());
    }
}

