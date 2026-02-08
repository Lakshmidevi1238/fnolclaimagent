package com.claims.fnolagent.model;

public class ClaimData {

    // ---------- Policy ----------
    private String policyNumber;
    private String policyholderName;
    private String effectiveDates;

    // ---------- Incident ----------
    private String incidentDate;
    private String incidentTime;
    private String incidentLocation;
    private String description;

    // ---------- Involved ----------
    private String claimantName;
    private String thirdPartyName;
    private String contactDetails;

    // ---------- Asset ----------
    private String assetType;
    private String assetId;
    private Double estimatedDamage;

    // ---------- Other ----------
    private String claimType;
    private String attachments;
    private Double initialEstimate;
    
    
    private String thirdPartyDetected;
    private String thirdPartyDetails;
    public String getThirdPartyDetected() {
		return thirdPartyDetected;
	}
	public void setThirdPartyDetected(String thirdPartyDetected) {
		this.thirdPartyDetected = thirdPartyDetected;
	}
	public String getThirdPartyDetails() {
		return thirdPartyDetails;
	}
	public void setThirdPartyDetails(String thirdPartyDetails) {
		this.thirdPartyDetails = thirdPartyDetails;
	}
	


    // ===== getters + setters =====

    public String getPolicyNumber() { return policyNumber; }
    public void setPolicyNumber(String v) { policyNumber = v; }

    public String getPolicyholderName() { return policyholderName; }
    public void setPolicyholderName(String v) { policyholderName = v; }

    public String getEffectiveDates() { return effectiveDates; }
    public void setEffectiveDates(String v) { effectiveDates = v; }

    public String getIncidentDate() { return incidentDate; }
    public void setIncidentDate(String v) { incidentDate = v; }

    public String getIncidentTime() { return incidentTime; }
    public void setIncidentTime(String v) { incidentTime = v; }

    public String getIncidentLocation() { return incidentLocation; }
    public void setIncidentLocation(String v) { incidentLocation = v; }

    public String getDescription() { return description; }
    public void setDescription(String v) { description = v; }

    public String getClaimantName() { return claimantName; }
    public void setClaimantName(String v) { claimantName = v; }

    public String getThirdPartyName() { return thirdPartyName; }
    public void setThirdPartyName(String v) { thirdPartyName = v; }

    public String getContactDetails() { return contactDetails; }
    public void setContactDetails(String v) { contactDetails = v; }

    public String getAssetType() { return assetType; }
    public void setAssetType(String v) { assetType = v; }

    public String getAssetId() { return assetId; }
    public void setAssetId(String v) { assetId = v; }

    public Double getEstimatedDamage() { return estimatedDamage; }
    public void setEstimatedDamage(Double v) { estimatedDamage = v; }

    public String getClaimType() { return claimType; }
    public void setClaimType(String v) { claimType = v; }

    public String getAttachments() { return attachments; }
    public void setAttachments(String v) { attachments = v; }

    public Double getInitialEstimate() { return initialEstimate; }
    public void setInitialEstimate(Double v) { initialEstimate = v; }
}
