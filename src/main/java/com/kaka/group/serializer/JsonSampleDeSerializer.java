package com.kaka.group.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.errors.SerializationException;

public class JsonSampleDeSerializer<T> implements org.apache.kafka.common.serialization.Deserializer<T> {

    ObjectMapper objectMapper = new ObjectMapper();

    private Class<T> classs;
    public JsonSampleDeSerializer(Class<T> classs){
        this.classs = classs;
    }

    @Override
    public T deserialize(String s, byte[] data) {
        //objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        if (data == null) {
            return null;
        }
        try {
            return objectMapper.readValue(data, classs);
        } catch (Exception e) {
            throw new SerializationException(e);
        }
    }
}
