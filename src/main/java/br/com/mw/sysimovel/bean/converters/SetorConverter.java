/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mw.sysimovel.bean.converters;

import br.com.mw.sysimovel.dao.SetorJpaController;
import br.com.mw.sysimovel.dao.util.JPAUtil;
import br.com.mw.sysimovel.model.Setor;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author sephi_000
 */
@FacesConverter(value = "setor", forClass = Setor.class)
public class SetorConverter implements Converter {
    private Setor setor = new Setor();
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String value) {
        SetorJpaController dao = new SetorJpaController(JPAUtil.EMF);
        return dao.findSetor(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        setor = (Setor) value;
        return String.valueOf(setor.getId());
    }
}
