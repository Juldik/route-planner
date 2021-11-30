package eu.julda.routeplanner.client;

import java.util.List;
import eu.julda.routeplanner.model.CountryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Client for communication with data about the countries
 */
@FeignClient(name = "countriesFeignClient", url = "${config.dataUrl}",
        configuration = CountriesFeignConfiguration.class)
public interface CountriesClient {

    @GetMapping()
    List<CountryDto> getCountries();
}
