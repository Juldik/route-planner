package eu.julda.routeplanner.resource;

import javax.validation.constraints.Pattern;
import eu.julda.routeplanner.model.RouteResponseDto;
import eu.julda.routeplanner.service.RoutePlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import static eu.julda.routeplanner.Contants.COUNTRY_ABBREVIATION_PATTERN;

/**
 * Resource serving route planning endpoint, can calculate optimal route
 */
@Controller
@RequiredArgsConstructor
@Validated
public class RoutePlannerResource {

    private final RoutePlannerService routePlannerService;

    /*no root versioning was used, because it was not specified in the requirement,
     but I would prefer adding prefix /api/v1/ **/

    /**
     * Returns optimal route for given origin and destination country.
     * Length of given country specification is validated.
     *
     * @param origin      - 3 letter country specification.
     * @param destination - 3 letter country specification.
     * @return - optimal route containing the origin and destination country and all visited countries.
     */
    @GetMapping(value = "/routing/{origin}/{destination}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RouteResponseDto calculateRoute(@PathVariable("origin")
                                           @Pattern(regexp = COUNTRY_ABBREVIATION_PATTERN) String origin,
                                        @PathVariable("destination") @Pattern(regexp = COUNTRY_ABBREVIATION_PATTERN) String destination) {
        return new RouteResponseDto(routePlannerService.calculateRoute(origin, destination));
    }
}
