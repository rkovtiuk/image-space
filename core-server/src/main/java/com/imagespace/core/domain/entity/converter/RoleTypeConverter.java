package com.imagespace.core.domain.entity.converter;

import com.imagespace.core.domain.entity.Role.RoleType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RoleTypeConverter implements AttributeConverter<RoleType, String> {

    @Override
    public String convertToDatabaseColumn(RoleType attribute) {
        return attribute.name();
    }

    @Override
    public RoleType convertToEntityAttribute(String dbData) {
        return RoleType.valueOf(dbData);
    }

}
