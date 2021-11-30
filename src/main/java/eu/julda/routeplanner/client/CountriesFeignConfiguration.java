package eu.julda.routeplanner.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * Configuration of object mapper even for non json header provided for API.
 */
public class CountriesFeignConfiguration {

    @Autowired
    private ObjectMapper mapper;

    /**
     * Provides proper message decoding.
     * @return decoder used for parsing received json from request.
     */
    @Bean
    public Decoder feignDecoder() {
        return new JacksonDecoder(mapper);
    }

}
