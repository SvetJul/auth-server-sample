package club.kuzyayo.stars.common.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Configuration
@Order(value = HIGHEST_PRECEDENCE)
public class JacksonConverterConfig {

    public static final String JSON_OBJECT_MAPPER_BEAN_NAME = "cgObjectMapper";
    public static final String PROJECT_DATE_FORMAT = "dd/MM/yyyy";

    @Bean(name = JSON_OBJECT_MAPPER_BEAN_NAME)
    public ObjectMapper jsonObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writer()
                .with(SerializationFeature.INDENT_OUTPUT)
                .without(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        objectMapper.setVisibility(
                objectMapper.getDeserializationConfig().getDefaultVisibilityChecker()
                        .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
        );

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        JavaTimeModule simpleModule = new JavaTimeModule();
        simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(PROJECT_DATE_FORMAT)));
        simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(PROJECT_DATE_FORMAT)));
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    @Bean(name = "jacksonHttpMessageConverterProvider")
    public HttpMessageConverter<Object> jsonMessageConverter(
            @Qualifier(JSON_OBJECT_MAPPER_BEAN_NAME) ObjectMapper jsonObjectMapper) {
        return new MappingJackson2HttpMessageConverter(jsonObjectMapper);
    }

    @Bean(name = "stringMessageConverterProvider")
    public HttpMessageConverter<String> stringHttpMessageConverterProvider() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_HTML,
                MediaType.TEXT_PLAIN, MediaType.TEXT_XML));
        return converter;
    }

    @Bean(name = "resourceMessageConverterProvider")
    public HttpMessageConverter<?> resourceHttpMessageConverterProvider() {
        ResourceHttpMessageConverter converter = new ResourceHttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        return converter;
    }

    @Bean(name = "xmlMessageConverterProvider")
    public HttpMessageConverter<?> xmlHttpMessageConverterProvider() {
        Jaxb2RootElementHttpMessageConverter converter = new Jaxb2RootElementHttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_XML));
        return converter;
    }

    @Bean(name = "formHttpConverter")
    public HttpMessageConverter<?> formHttpMessageConverter() {
        FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        formHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
        return formHttpMessageConverter;
    }

    @Bean("byteArrayHttpMessageConverter")
    public HttpMessageConverter<byte[]> byteArrayHttpMessageConverter() {
        return new ByteArrayHttpMessageConverter();
    }

    @Bean
    @Primary
    public HttpMessageConverters messageConverters(List<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters);
    }

}
