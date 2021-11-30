package eu.julda.routeplanner;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception is fired when no route is found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoRouteFoundException extends RuntimeException {

    public NoRouteFoundException(String origin, String destination) {
        super(String.format("No route was found for given origin %s and destionation %s", origin, destination));
    }
}
