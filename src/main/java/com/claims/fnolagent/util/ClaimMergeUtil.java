package com.claims.fnolagent.util;

import com.claims.fnolagent.model.ClaimData;

public class ClaimMergeUtil {

    public static void mergeMissing(ClaimData main, ClaimData extra) {

        if (extra == null) return;

        if (main.getClaimantName() == null)
            main.setClaimantName(extra.getClaimantName());

        if (main.getThirdPartyName() == null)
            main.setThirdPartyName(extra.getThirdPartyName());

        if (main.getContactDetails() == null)
            main.setContactDetails(extra.getContactDetails());

        if (main.getAssetId() == null)
            main.setAssetId(extra.getAssetId());

        if (main.getAssetType() == null)
            main.setAssetType(extra.getAssetType());

        if (main.getEstimatedDamage() == null)
            main.setEstimatedDamage(extra.getEstimatedDamage());

        if (main.getInitialEstimate() == null)
            main.setInitialEstimate(extra.getInitialEstimate());
    }
}
