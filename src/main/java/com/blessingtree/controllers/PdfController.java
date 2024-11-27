package com.blessingtree.controllers;

import com.blessingtree.service.GiftTagService;
import com.blessingtree.service.ParentService;
import com.blessingtree.service.SponsorService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class PdfController extends BaseController {
    private final GiftTagService giftTagService;
    private final SponsorService sponsorService;
    private final ParentService parentService;

    public PdfController(@Autowired GiftTagService giftTagService,
                         @Autowired SponsorService sponsorService,
                         @Autowired ParentService parentService){
        this.giftTagService = giftTagService;
        this.sponsorService = sponsorService;
        this.parentService = parentService;
    }

    @GetMapping("/pdf/giftTags")
    public void generateBlankGiftTags(HttpServletResponse response, @RequestParam(name = "tags", defaultValue = "1") int numberOfTags) throws IOException {
        PdfDocument pdf = getPdfDocument(response);
        giftTagService.printGiftTag(pdf, numberOfTags).close();
    }

    @GetMapping("/pdf/giftTags/sponsors/{id}")
    public void generateSponsorGiftTags(HttpServletResponse response, @PathVariable Long id) throws IOException {
        PdfDocument pdf = getPdfDocument(response);
        giftTagService.printGiftTagFromSponsor(pdf, sponsorService.findSponsor(id)).close();
    }

    @GetMapping("/pdf/giftTags/parents/{id}")
    public void generateAFamilyGiftTags(HttpServletResponse response, @PathVariable Long id) throws IOException {
        PdfDocument pdf = getPdfDocument(response);
        giftTagService.printGiftTagFromParent(pdf, List.of(parentService.findParentByID(id))).close();
    }

    @GetMapping("/pdf/giftTags/parents")
    public void generateFamilyGiftTags(HttpServletResponse response) throws IOException {
        PdfDocument pdf = getPdfDocument(response);
        giftTagService.printGiftTagFromParent(pdf, parentService.getParents()).close();
    }

    private static PdfDocument getPdfDocument(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=example.pdf");

        // Create a PDF document in memory
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        return pdf;
    }
}
