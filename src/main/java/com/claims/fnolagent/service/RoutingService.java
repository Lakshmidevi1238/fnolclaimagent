package com.claims.fnolagent.service;

import com.claims.fnolagent.model.*;

import java.util.List;

public class RoutingService {

    public static RoutingResult route(
            ClaimData c,
            ValidationResult v) {

  

        if (!v.getMissingFields().isEmpty()) {
            return new RoutingResult(
                "MANUAL_REVIEW",
                "Mandatory fields missing: " + v.getMissingFields()
            );
        }

        

        String d = c.getDescription() == null ? "" :
                   c.getDescription().toUpperCase();

        if (containsFraudSignal(d)) {
            return new RoutingResult(
                "INVESTIGATION",
                "Fraud risk keywords detected in description"
            );
        }



        if ("INJURY".equalsIgnoreCase(c.getClaimType())) {
            return new RoutingResult(
                "SPECIALIST_QUEUE",
                "Claim classified as injury-related"
            );
        }



        if (c.getEstimatedDamage() != null &&
            c.getEstimatedDamage() < 25000) {

            return new RoutingResult(
                "FAST_TRACK",
                "Low estimated damage (< 25000)"
            );
        }


        return new RoutingResult(
            "STANDARD_QUEUE",
            "Default routing — standard processing"
        );
    }


    private static boolean containsFraudSignal(String d) {

        String[] keys = {
            "FRAUD",
            "STAGED",
            "FAKE",
            "SUSPICIOUS",
            "NOT MY FAULT (CONFLICT)",
            "SETUP ACCIDENT"
        };

        for (String k : keys) {
            if (d.contains(k))
                return true;
        }
        return false;
    }
}
