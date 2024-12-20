package com.blessingtree.controllers;

import com.blessingtree.dto.RosterDTO;
import com.blessingtree.dto.SponsorDTO;
import com.blessingtree.dto.TagInfo;
import com.blessingtree.service.GiftTagService;
import com.blessingtree.service.ParentService;
import com.blessingtree.service.ReportService;
import com.blessingtree.service.SponsorService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class ReportController extends BaseController {
    private final GiftTagService giftTagService;
    private final SponsorService sponsorService;
    private final ParentService parentService;

    private final ReportService reportService;

    public ReportController(@Autowired GiftTagService giftTagService,
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

    @PostMapping("/pdf/giftTags")
    public void printSingleGiftTag(HttpServletResponse response, @RequestBody TagInfo tagInfo) throws IOException {
        PdfDocument pdf = getPdfDocument(response);
        giftTagService.printGiftTag(pdf, List.of(tagInfo)).close();
    }
    @PostMapping("/pdf/reports/roster")
    public void generateRosterReport(HttpServletResponse response, @RequestBody RosterDTO roster) throws IOException {
        PdfDocument pdf = getPdfDocument(response);
        reportService.printRoster(pdf, roster).close();
    }

    @PostMapping("/pdf/reports/sponsor")
    public void generateSponsorReport(HttpServletResponse response, @RequestBody List<SponsorDTO> sponsors) throws Exception {
        PdfDocument pdf = getPdfDocument(response);
        reportService.printSponsorReport(pdf, sponsors).close();
    }

    @PostMapping("/excel/reports/roster")
    public ResponseEntity<byte[]> generateExcelRosterReport(@RequestBody RosterDTO roster){
        try{
            ByteArrayOutputStream outputStream = reportService.printExcelRoster(roster);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sample.xlsx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(outputStream.toByteArray());
        }catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    private static PdfDocument getPdfDocument(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=example.pdf");

        // Create a PDF document in memory
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        return new PdfDocument(writer);
    }
}
