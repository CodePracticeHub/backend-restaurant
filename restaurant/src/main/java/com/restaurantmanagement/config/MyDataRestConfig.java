//package com.restaurantmanagement.config;
//
//
//import com.restaurantmanagement.entity.Menu;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
//import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
//import org.springframework.http.HttpMethod;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//
//@Configuration
//public class MyDataRestConfig implements RepositoryRestConfigurer {
//
//    private String theAllowedOrigins = "http://localhost:3000";
//
//    @Override
//    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config,
//                                                     CorsRegistry cors) {
//        HttpMethod[] theUnsupportedActions = {
//                HttpMethod.POST,
//                HttpMethod.PATCH,
//                HttpMethod.DELETE,
//                HttpMethod.PUT};
//
//        config.exposeIdsFor(Menu.class);
////        config.exposeIdsFor(Review.class);
////        config.exposeIdsFor(Message.class);
//
//        disableHttpMethods(Menu.class, config, theUnsupportedActions);
////        disableHttpMethods(Review.class, config, theUnsupportedActions);
////        disableHttpMethods(Message.class, config, theUnsupportedActions);
//
//        /* Configure CORS Mapping */
//        cors.addMapping(config.getBasePath() + "/**")
//                .allowedOrigins(theAllowedOrigins);
//    }
//
//    private void disableHttpMethods(Class theClass,
//                                    RepositoryRestConfiguration config,
//                                    HttpMethod[] theUnsupportedActions) {
//        config.getExposureConfiguration()
//                .forDomainType(theClass)
//                .withItemExposure((metdata, httpMethods) ->
//                        httpMethods.disable(theUnsupportedActions))
//                .withCollectionExposure((metdata, httpMethods) ->
//                        httpMethods.disable(theUnsupportedActions));
//    }
//}
