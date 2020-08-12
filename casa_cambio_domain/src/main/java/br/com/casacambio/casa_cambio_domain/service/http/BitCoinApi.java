package br.com.casacambio.casa_cambio_domain.service.http;

import br.com.casacambio.casa_cambio_domain.service.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@Component
public class BitCoinApi {

    Logger LOGGER = LoggerFactory.getLogger(BitCoinApi.class);

    final private RestTemplate restTemplate;

    @Value("${api.bitcoin}")
    private String urlBitCoin;

    @Autowired
    public BitCoinApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BigDecimal getAmount() {
        Map response = restTemplate.getForObject(urlBitCoin, Map.class);
        Map data = (Map) response.get("data");

        if(Objects.nonNull(response)) {
            return new BigDecimal(data.get("amount").toString());
        } else {
            LOGGER.error("getAmount: BitCoinApi timeout.");
            throw new BadRequestException("BitCoinApi timeout.");
        }

    }
}
