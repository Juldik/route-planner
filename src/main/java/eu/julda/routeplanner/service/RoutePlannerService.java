package eu.julda.routeplanner.service;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service for providing rote planning functionality over given dataset.
 */
@Service
public interface RoutePlannerService {

    /**
     * Calculates route for given origin and destination point as a three letter abbreviation of country.
     *
     * @param origin - 3 letter abbreviation of departure country
     * @param destination - 3 letter abbreviation of arrival country
     * @return - array containing the whole route (meaning every visited country including the given ones).
     */
    List<String> calculateRoute(String origin, String destination);
}
