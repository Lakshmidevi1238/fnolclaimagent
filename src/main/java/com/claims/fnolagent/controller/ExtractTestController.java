package com.claims.fnolagent.controller;

import java.io.File;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.claims.fnolagent.model.AgentResult;
import com.claims.fnolagent.model.ClaimData;
import com.claims.fnolagent.model.RoutingResult;
import com.claims.fnolagent.model.ValidationResult;
import com.claims.fnolagent.parser.FnolFieldExtractor;
import com.claims.fnolagent.parser.PdfTextExtractor;
import com.claims.fnolagent.service.RoutingService;
import com.claims.fnolagent.service.ValidationService;

@RestController
public class ExtractTestController {

	@PostMapping("/extract-test")
	public ClaimData extract(
	        @RequestParam("file") MultipartFile file,
	        @RequestParam(value="attachments", required=false)
	        MultipartFile[] attachments) throws Exception {

	    // ---- parse main FNOL ----
	    File temp = File.createTempFile("upload",".pdf");
	    file.transferTo(temp);

	    String text = PdfTextExtractor.extractText(temp);
	    ClaimData main = FnolFieldExtractor.extract(text);

	    // ---- mark attachment presence ----
	    if (attachments != null && attachments.length > 0)
	        main.setAttachments("present");

	   
	    if (attachments != null) {
	        for (MultipartFile a : attachments) {

	            if (a.getOriginalFilename().endsWith(".pdf")) {
	                File t = File.createTempFile("att",".pdf");
	                a.transferTo(t);

	                String at = PdfTextExtractor.extractText(t);
	                ClaimData extra = FnolFieldExtractor.extract(at);

	                com.claims.fnolagent.util.ClaimMergeUtil
	                        .mergeMissing(main, extra);
	            }
	        }
	    }

	    return main;
	}
	
	@PostMapping("/validate-test")
	public Object validate(
	        @RequestParam("file") MultipartFile file) throws Exception {

	    File temp = File.createTempFile("upload",".pdf");
	    file.transferTo(temp);

	    String text = PdfTextExtractor.extractText(temp);
	    ClaimData c = FnolFieldExtractor.extract(text);

	    return com.claims.fnolagent.service.ValidationService.validate(c);
	}
	
	@PostMapping("/process-fnol")
	public AgentResult process(
	        @RequestParam("file") MultipartFile file,
	        @RequestParam(value="attachments", required=false)
	        MultipartFile[] attachments) throws Exception {

	    File temp = File.createTempFile("upload",".pdf");
	    file.transferTo(temp);

	    String text = PdfTextExtractor.extractText(temp);
	    ClaimData main = FnolFieldExtractor.extract(text);

	    // attachment presence
	    if (attachments != null && attachments.length > 0)
	        main.setAttachments("present");

	    // validate
	    ValidationResult v =
	        ValidationService.validate(main);

	    // route
	    RoutingResult rr =
	        RoutingService.route(main, v);

	    return new AgentResult(
	        main,
	        v.getMissingFields(),
	        rr
	    );
	}



}
