package com.dct.base.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static java.net.URLDecoder.decode;

/**
 * Configuration of web application with Servlet 3.0 APIs
 * @author thoaidc
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer, ServletContextInitializer, WebServerFactoryCustomizer<WebServerFactory> {

    private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);
    private final Environment env;

    public WebConfigurer(Environment env) {
        this.env = env;
    }

    @Override
    public void onStartup(ServletContext servletContext) {
        if (env.getActiveProfiles().length != 0) {
            log.info("Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
        }

        log.info("Web application fully configured");
    }

    /**
     * Customize the Servlet engine: Mime types, the document root, the cache.
     */
    @Override
    public void customize(WebServerFactory server) {
        // When running in an IDE or with ./mvnw spring-boot:run, set location of the static web assets
        setLocationForStaticAssets(server);
    }

    /**
     * When running the application in a development or testing environment (e.g. using Maven with the target folder),
     * static resources will be served from the <a href="">target/classes/static/</a> directory instead of the default.<p>
     * This is useful when static resources are created or changed during development
     * and need to be reflected directly on the server without recompiling the entire application.
     * @param server used to check if the server is a {@link ConfigurableServletWebServerFactory}
     *               (a factory for embedded servlet servers such as Tomcat, Jetty, or Undertow).
     */
    private void setLocationForStaticAssets(WebServerFactory server) {
        if (server instanceof ConfigurableServletWebServerFactory servletWebServer) {
            String prefixPath = resolvePathPrefix();
            File root = new File(prefixPath + "target/classes/static/");

            if (root.exists() && root.isDirectory()) {
                servletWebServer.setDocumentRoot(root);
            }
        }
    }

    /**
     * Resolve path prefix for static resources. <p>
     * Ensure that the path to static resources is determined correctly
     * even when the application is run from different locations (e.g. IDE, command line).<p>
     * Handle situations where the path may change due to directory structure or runtime environment.
     */
    private String resolvePathPrefix() {
        // Get the path where the current class is compiled and stored. Use UTF-8 standard to handle special characters
        String fullExecutablePath = decode(
            Objects.requireNonNull(this.getClass().getResource("")).getPath(),
            StandardCharsets.UTF_8
        );

        // Specifies the current project root directory path (.).
        String rootPath = Paths.get(".").toUri().normalize().getPath();
        String extractedPath = fullExecutablePath.replace(rootPath, "");

        // Find the path part from the root to the target/ directory
        // or return an empty string if the /target directory does not exist
        int extractionEndIndex = extractedPath.indexOf("target/");

        if (extractionEndIndex <= 0)
            return "";

        return extractedPath.substring(0, extractionEndIndex);
    }

    @Bean
    public CorsFilter corsFilter() {
        log.debug("Registering CORS filter");
        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedOriginPattern("*");
        config.setAllowedHeaders(List.of(
            "Content-Type",     // Content format
            "Authorization",    // Authentication token
            "Accept",           // Client-expected content
            "Origin",           // Origin of the request
            "X-CSRF-Token",     // Anti-CSRF token
            "X-Requested-With", // Ajax request markup
            "Access-Control-Allow-Origin", // Server response header
            "X-App-Version",    // Application version (optional)
            "X-Device-ID"
        ));

        config.setAllowedMethods(List.of("GET", "PUT", "POST", "DELETE"));
        config.setAllowCredentials(true);  // Allow sending cookies or authentication information

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Apply CORS to all endpoints

        return new CorsFilter(source);
    }

    /**
     * This configuration defines a RestTemplate bean in Spring, which is used to make HTTP requests <p>
     * Purpose: Create an instance of RestTemplate, a tool that makes sending HTTP requests and handling responses
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // Create an HTTP message converter, using Jackson to convert between JSON and Java objects
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        restTemplate.getMessageConverters().add(converter);
        return restTemplate;
    }
}
