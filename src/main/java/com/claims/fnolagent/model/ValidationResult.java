package com.claims.fnolagent.model;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

    private List<String> missingFields = new ArrayList<>();
    private List<String> inconsistentFields = new ArrayList<>();
    private boolean valid;

    public List<String> getMissingFields() {
        return missingFields;
    }

    public List<String> getInconsistentFields() {
        return inconsistentFields;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean v) {
        this.valid = v;
    }
}
