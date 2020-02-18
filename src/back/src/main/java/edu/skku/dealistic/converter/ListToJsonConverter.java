package edu.skku.dealistic.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.List;

/**
 * Hibernate Converter
 * Main Features:
 * - Convert Json to Java list. or reverse.
 *
 * @author Junhyun Kim
 */
@Converter
public class ListToJsonConverter implements AttributeConverter<List<String>, String> {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        try {
            return mapper.writeValueAsString(strings);
        } catch (Exception e) {
            return "[]";
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        try {
            return mapper.readValue(s, new TypeReference<ArrayList<String>>() {
            });
        } catch (Exception e) {
            return List.of();
        }
    }
}
