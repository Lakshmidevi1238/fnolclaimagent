package com.claims.fnolagent.model;

import java.util.List;

public class AgentResult {

    private ClaimData extractedFields;
    private List<String> missingFields;
    private String recommendedRoute;
    private String reasoning;

    public AgentResult(
            ClaimData data,
            List<String> missing,
            RoutingResult route) {

        this.extractedFields = data;
        this.missingFields = missing;
        this.recommendedRoute = route.getRecommendedRoute();
        this.reasoning = route.getReasoning();
    }

    public ClaimData getExtractedFields() {
        return extractedFields;
    }

    public List<String> getMissingFields() {
        return missingFields;
    }

    public String getRecommendedRoute() {
        return recommendedRoute;
    }

    public String getReasoning() {
        return reasoning;
    }
}
