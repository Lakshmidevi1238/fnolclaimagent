package com.claims.fnolagent.service;

import com.claims.fnolagent.model.ClaimData;
import com.claims.fnolagent.model.ValidationResult;

public class ValidationService {

    public static ValidationResult validate(ClaimData c) {

        ValidationResult r = new ValidationResult();


        if (empty(c.getPolicyNumber()))
            r.getMissingFields().add("policyNumber");

        if (empty(c.getPolicyholderName()))
            r.getMissingFields().add("policyholderName");

        if (empty(c.getIncidentDate()))
            r.getMissingFields().add("incidentDate");

        if (empty(c.getIncidentLocation()))
            r.getMissingFields().add("incidentLocation");

        if (empty(c.getDescription()))
            r.getMissingFields().add("description");

        if (c.getEstimatedDamage() == null)
            r.getMissingFields().add("estimatedDamage");

        if (empty(c.getClaimType()))
            r.getMissingFields().add("claimType");

        if (empty(c.getAssetId()) && empty(c.getAssetType()))
            r.getMissingFields().add("asset");

        // -------- inconsistency checks --------

        if (c.getEstimatedDamage() != null && c.getEstimatedDamage() < 0)
            r.getInconsistentFields().add("negativeEstimate");

        if (c.getEstimatedDamage() != null &&
            empty(c.getDescription()))
            r.getInconsistentFields().add("estimateWithoutDescription");

        if ("INJURY".equalsIgnoreCase(c.getClaimType()) &&
            empty(c.getThirdPartyDetected()))
            r.getInconsistentFields().add("injuryWithoutThirdParty");

        if (!empty(c.getIncidentDate()) &&
            !looksLikeDate(c.getIncidentDate()))
            r.getInconsistentFields().add("badDateFormat");

  

        r.setValid(
            r.getMissingFields().isEmpty() &&
            r.getInconsistentFields().isEmpty()
        );

        return r;
    }


 

    private static boolean empty(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static boolean looksLikeDate(String s) {
        return s.matches(".*\\d{2}.*\\d{2}.*\\d{2,4}.*");
    }
}
