package com.claims.fnolagent.model;

public class RoutingResult {

    private String recommendedRoute;
    private String reasoning;

    public RoutingResult(String route, String reason) {
        this.recommendedRoute = route;
        this.reasoning = reason;
    }

    public String getRecommendedRoute() {
        return recommendedRoute;
    }

    public String getReasoning() {
        return reasoning;
    }
}
