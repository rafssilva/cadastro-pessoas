package com.crud.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.util.List;
import com.crud.model.base.BaseModel;

@FacesConverter("baseModelConverter")
public class BaseModelConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        @SuppressWarnings("unchecked")
		List<BaseModel> items = (List<BaseModel>) component.getAttributes().get("items");
        if (items != null) {
            for (BaseModel item : items) {
                if (item.getId() != null && item.getId().toString().equals(value)) {
                    return item;
                }
            }
        }
        throw new ConverterException("Objeto não encontrado para o valor: " + value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
        if (object == null || (object instanceof String && ((String) object).trim().isEmpty())) {
            return "";
        }
        if (object instanceof BaseModel) {
            BaseModel model = (BaseModel) object;
            return model.getId() != null ? model.getId().toString() : "";
        }
        throw new ConverterException("Objeto " + object + " não é do tipo BaseModel");
    }
}