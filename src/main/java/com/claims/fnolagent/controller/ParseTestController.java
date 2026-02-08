package com.claims.fnolagent.controller;

import com.claims.fnolagent.parser.PdfTextExtractor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
public class ParseTestController {

    @PostMapping("/parse-test")
    public String parse(@RequestParam("file") MultipartFile file) throws Exception {

        File temp = File.createTempFile("upload", ".pdf");
        file.transferTo(temp);

        String text = PdfTextExtractor.extractText(temp);

        return text.substring(0, Math.min(text.length(), 2000));
    }
}

