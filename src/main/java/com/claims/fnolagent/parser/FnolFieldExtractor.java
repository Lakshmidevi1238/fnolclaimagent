package com.claims.fnolagent.parser;

import com.claims.fnolagent.model.ClaimData;
import java.util.regex.*;

public class FnolFieldExtractor {

    public static ClaimData extract(String text) {

        ClaimData c = new ClaimData();
        String U = text.toUpperCase();

        Matcher pol = Pattern.compile(
            "POLICY NUMBER\\s+([A-Z0-9-]{5,})")
            .matcher(text);
        if (pol.find())
            c.setPolicyNumber(pol.group(1));

        Matcher name = Pattern.compile(
            "NAME OF INSURED.*?\\)\\s+([^\\n]{3,60})")
            .matcher(text);
        if (name.find()) {
            String v = extractPersonName(name.group(1));
            c.setPolicyholderName(v);
            c.setClaimantName(v);
        }

        Matcher loc = Pattern.compile(
            "LOCATION OF LOSS\\s+([^\\n]{3,80})")
            .matcher(text);
        if (loc.find()) {
            String v = loc.group(1).trim();
            if (!v.toUpperCase().contains("IF NOT"))
                c.setIncidentLocation(v);
        }

        Matcher desc = Pattern.compile(
        	    "DESCRIPTION OF ACCIDENT.*?\\)\\s+([^\\n]{5,200})")
        	    .matcher(text);

        	if (desc.find()) {
        	    String d = desc.group(1).trim();

        	    if (!d.equals(d.toUpperCase()) &&   
        	        !d.contains("VEHICLE") &&
        	        !d.contains("INSURED")) {

        	        c.setDescription(d);
        	    }
        	}

        Matcher est = Pattern.compile(
            "ESTIMATE AMOUNT[:\\s]+(\\d+)")
            .matcher(text);
        if (est.find()) {
            Double amt = Double.parseDouble(est.group(1));
            c.setEstimatedDamage(amt);
            c.setInitialEstimate(amt);
        }


        Matcher vin = Pattern.compile(
            "V\\.I\\.N\\.:?\\s*([A-Z0-9]+)")
            .matcher(text);
        if (vin.find())
            c.setAssetId(vin.group(1));

        Matcher make = Pattern.compile(
            "MAKE:\\s*([A-Za-z]+)")
            .matcher(text);
        if (make.find())
            c.setAssetType(make.group(1));

        Matcher date = Pattern.compile(
            "\\b\\d{2}/\\d{2}/\\d{4}\\b")
            .matcher(text);
        if (date.find())
            c.setIncidentDate(date.group());

        Matcher time = Pattern.compile(
            "\\b\\d{1,2}:\\d{2}\\s*(AM|PM)\\b",
            Pattern.CASE_INSENSITIVE)
            .matcher(text);
        if (time.find())
            c.setIncidentTime(time.group());

        if (U.contains("OTHER VEHICLE")
         || U.contains("WITNESSES"))
            c.setThirdPartyDetected("YES");

        deriveClaimType(c);

        return c;
    }


    private static String extractPersonName(String v) {

        if (v == null) return null;

        String U = v.toUpperCase();

        String[] stops = {
            " INSURED",
            " MAILING",
            " ADDRESS",
            " PHONE",
            " DATE",
            " FEIN"
        };

        for (String s : stops) {
            int idx = U.indexOf(s);
            if (idx > 0) {
                v = v.substring(0, idx).trim();
                break;
            }
        }

        Matcher m = Pattern.compile(
            "^[A-Za-z.]+(?:\\s+[A-Za-z.]+){0,2}")
            .matcher(v);

        return m.find() ? m.group().trim() : v.trim();
    }



    private static void deriveClaimType(ClaimData c) {

        String D = c.getDescription()==null ? "" :
                   c.getDescription().toUpperCase();

        if (contains(D,"INJURY","INJURED","HOSPITAL","MEDICAL"))
            c.setClaimType("INJURY");
        else if (contains(D,"COLLISION","VEHICLE","DAMAGE"))
            c.setClaimType("AUTO_DAMAGE");
        else
            c.setClaimType("GENERAL");
    }

    private static boolean contains(String s,String...k){
        for(String x:k)
            if(s.contains(x))
                return true;
        return false;
    }
}
