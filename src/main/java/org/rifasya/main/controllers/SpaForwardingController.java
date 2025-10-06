package org.rifasya.main.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This controller solves the common problem in Single Page Applications (SPAs)
 * where reloading a frontend route (e.g., /dashboard/admin) causes the server
 * to return a 404 error.
 *
 * Its function is to catch all routes not handled by other controllers
 * (especially those not starting with /api) and simply return the main page
 * of the Angular application (index.html).
 *
 * Once Angular loads in the browser, its own router will take control and
 * display the correct component based on the URL.
 */
@Controller
public class SpaForwardingController {

    /**
     * Forwards all routes that are not API calls and not static files to the
     * SPA's entry point.
     * The regular expression `/{path:[^\\.]*}/**` matches paths that do not contain a dot,
     * thus avoiding interference with file loading like .js, .css, etc.
     *
     * @return The entry point of the application, allowing Angular to handle routing.
     */
    @RequestMapping(value = "/{path:[^\\.]*}/**")
    public String forward() {
        // "forward:/" tells Spring to return the content of the root path,
        // which by default is the index.html of your Angular application.
        return "forward:/";
    }
}
