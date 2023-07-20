package de.hsos.ooadss23.blogapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC-Konfiguration, dient der Aktivierung des RequireLoginInterceptors.
 * Methoden werden automatisch während der Initialisierung der Applikation aufgerufen.
 * @author Roman Wasenmiller
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    RequireLoginInterceptor requireLoginInterceptor;
    public MvcConfig(RequireLoginInterceptor requireLoginInterceptor) {
        this.requireLoginInterceptor = requireLoginInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // LoginInterceptor aktiv für Pfade unter /blogs/ und /artikel/
        registry.addInterceptor(this.requireLoginInterceptor).addPathPatterns("/blogs/{*}", "/artikel/{*}"); // Enthält auch /blogs und /artikel.
    }
}
