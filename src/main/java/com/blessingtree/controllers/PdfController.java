package com.blessingtree.controllers;

import com.blessingtree.dto.ParentDTO;
import com.blessingtree.dto.RosterDTO;
import com.blessingtree.service.GiftTagService;
import com.blessingtree.service.ParentService;
import com.blessingtree.service.ReportService;
import com.blessingtree.service.SponsorService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class PdfController extends BaseController {
    private final GiftTagService giftTagService;
    private final SponsorService sponsorService;
    private final ParentService parentService;

    private final ReportService reportService;

    public PdfController(@Autowired GiftTagService giftTagService,
                         @Autowired SponsorService sponsorService,
                         @Autowired ParentService parentService,
                         @Autowired ReportService reportService){
        this.giftTagService = giftTagService;
        this.sponsorService = sponsorService;
        this.parentService = parentService;
        this.reportService = reportService;
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

    @PostMapping("/pdf/reports/roster")
    public void generateRosterReport(HttpServletResponse response, @RequestBody RosterDTO roster) throws IOException {
        PdfDocument pdf = getPdfDocument(response);
        reportService.printRoster(pdf, roster).close();
    }

    private static PdfDocument getPdfDocument(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=example.pdf");

        // Create a PDF document in memory
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        return new PdfDocument(writer);
    }
}
