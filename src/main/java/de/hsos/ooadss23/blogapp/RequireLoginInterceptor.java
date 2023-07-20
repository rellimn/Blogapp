package de.hsos.ooadss23.blogapp;

import de.hsos.ooadss23.blogapp.util.NutzerSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * Interceptor, der vor Seitenaufrufen pr&uuml;ft, ob ein Nutzer eingeloggt ist.
 * Falls keine Session besteht, wird der Nutzer auf die Login-Seite weitergeleitet.
 * Konfiguration erfolgt innerhalb MvcConfig.
 * @see de.hsos.ooadss23.blogapp.MvcConfig
 * @author Roman Wasenmiller
 */
@Component
@SessionAttributes("nutzerSession")
public class RequireLoginInterceptor implements HandlerInterceptor {
    /**
     * Prüft vor Aufrufen der Seiten-Handler, ob ein Nutzer eingeloggt ist und leitet entsprechend weiter.
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return True wenn NutzerSession besteht, falls wenn nicht. In dem Fall wird auf die Login-Seite weitergeleitet.
     * @throws IOException falls senRedirect fehlschl&auml;gt
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        NutzerSession nutzerSession = (NutzerSession) request.getSession().getAttribute("nutzerSession");
        if (nutzerSession == null) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}
