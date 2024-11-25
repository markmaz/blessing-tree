package com.blessingtree.controllers;

import com.blessingtree.service.PdfService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PdfController extends BaseController {
    private final PdfService pdfService;

    public PdfController(@Autowired PdfService pdfService){
        this.pdfService = pdfService;
    }

    @GetMapping("/pdf/giftTags")
    public void generateBlankGiftTags(HttpServletResponse response, @RequestParam(name = "tags", defaultValue = "1") int numberOfTags) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=example.pdf");

        // Create a PDF document in memory
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);

        pdfService.printGiftTag(pdf, numberOfTags).close();
    }
}
