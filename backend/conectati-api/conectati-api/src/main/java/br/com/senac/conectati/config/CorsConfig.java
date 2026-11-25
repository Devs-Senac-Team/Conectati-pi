package br.com.senac.conectati.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Pattern;

/** Configuração global de CORS para o frontend e previews da Vercel. */
@Configuration
public class CorsConfig {

    private static final Pattern VERCEL_PREVIEW_ORIGIN = Pattern.compile(
            "^https://conectati-pi-conectati-senac-react-[a-z0-9]+(-[a-z0-9-]+)?\\.vercel\\.app$");
    private static final List<String> ALLOWED_ORIGINS = List.of(
            "https://conectati-pi-conectati-senac-react.vercel.app",
            "http://localhost:3000",
            "http://localhost:5173");

    private boolean isAllowedOrigin(String origin) {
        return origin == null
                || ALLOWED_ORIGINS.contains(origin)
                || VERCEL_PREVIEW_ORIGIN.matcher(origin).matches();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return request -> corsConfigurationFor(request);
    }

    private CorsConfiguration corsConfigurationFor(HttpServletRequest request) {
        String origin = request.getHeader(HttpHeaders.ORIGIN);
        CorsConfiguration configuration = new CorsConfiguration();

        if (origin != null && isAllowedOrigin(origin)) {
            configuration.setAllowedOriginPatterns(List.of(origin));
        } else if (origin != null) {
            configuration.setAllowedOriginPatterns(List.of());
        }

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Content-Type", "Authorization"));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);
        return configuration;
    }
}
