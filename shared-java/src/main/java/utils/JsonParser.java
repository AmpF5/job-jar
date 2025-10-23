package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.generics.IOfferResponse;

import java.net.http.HttpResponse;

public abstract class JsonParser {
//    private final static Logger log = LoggerFactory.getLogger(JsonParser.class);

    public static <TClass extends IOfferResponse> TClass parse(ObjectMapper mapper, HttpResponse<String> resp, Class<TClass> valueType) {
        try {
            return mapper.readValue(resp.body(), valueType);
        } catch (JsonProcessingException e) {
//            log.error("Error while mapping data {}", resp.body(), e);
            throw new RuntimeException(e);
        }
    }
}
