package com.blessingtree.service;

import com.blessingtree.dto.ChildDTO;
import com.blessingtree.dto.GiftDTO;
import com.blessingtree.dto.ParentDTO;
import com.blessingtree.dto.RosterDTO;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.event.AbstractPdfDocumentEvent;
import com.itextpdf.kernel.pdf.event.AbstractPdfDocumentEventHandler;
import com.itextpdf.kernel.pdf.event.PdfDocumentEvent;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ReportService {
    public PdfDocument printRoster(PdfDocument pdf, RosterDTO roster) throws IOException {
        PageSize landscape = PageSize.A4.rotate();
        pdf.setDefaultPageSize(landscape);

        Document document = new Document(pdf);
        Color headerColor = new DeviceRgb(240, 242, 242);
        Color parentRow = new DeviceRgb(208, 233, 238);
        Color separator = new DeviceRgb(42, 54, 59);
        Color alt = new DeviceRgb(235, 238, 242);

        document.add(new Paragraph("Blessing Tree 2024 - " + roster.getUnits().toString()).setFontSize(18));

        float[] columnWidths = {50, 100, 80, 80, 50, 100, 150, 100, 80, 50, 50}; // Adjust widths as needed
        Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();
        table.setBorder(new SolidBorder(headerColor, .5f));

        PdfFont regularFont = PdfFontFactory.createFont("Helvetica");
        PdfFont boldFont = PdfFontFactory.createFont("Helvetica-Bold");
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new customEventHandler());

        // Add table headers
        String[] headers = {
                "ID", "Name", "Phone", "Phone", "MHID",
                "Children", "Gifts", "Sponsor", "Phone", "Gft Rcvd", "Picked Up"
        };
        for (String header : headers) {
            Cell headerCell = new Cell().add(new Paragraph(header).setFont(boldFont).setFontSize(10));
            headerCell.setBackgroundColor(headerColor);
            headerCell.setTextAlignment(TextAlignment.LEFT);
            headerCell.setBorder(new SolidBorder(headerColor, .5f));
            table.addHeaderCell(headerCell);
        }

        for(ParentDTO p : roster.getParents()){
            table.addCell(new Cell().add(new Paragraph(p.getBtid()).setFontSize(10).setFont(regularFont)).setBackgroundColor(parentRow).setBorder(new SolidBorder(headerColor, .5f)));
            table.addCell(new Cell().add(new Paragraph(p.getFirstName() + " " + p.getLastName()).setFontSize(10).setFont(regularFont)).setBackgroundColor(parentRow).setBorder(new SolidBorder(headerColor, .5f)));
            table.addCell(new Cell().add(new Paragraph(p.getPrimaryPhone() == null ? "" : p.getPrimaryPhone()).setFontSize(10).setFont(regularFont)).setBackgroundColor(parentRow).setBorder(new SolidBorder(headerColor, .5f)));
            table.addCell(new Cell().add(new Paragraph(p.getSecondaryPhone() == null ? "" : p.getSecondaryPhone()).setFontSize(10).setFont(regularFont)).setBackgroundColor(parentRow).setBorder(new SolidBorder(headerColor, .5f)));
            table.addCell(new Cell().add(new Paragraph(p.getMhid() + "").setFontSize(10).setFont(regularFont)).setBackgroundColor(parentRow).setBorder(new SolidBorder(headerColor, .5f)));
            table.addCell(new Cell().add(new Paragraph(p.getChildren().size() + "").setFontSize(10).setFont(regularFont)).setBackgroundColor(parentRow).setBorder(new SolidBorder(headerColor, .5f)));
            table.addCell(new Cell(1, 5).setBackgroundColor(parentRow).setBorder(new SolidBorder(headerColor, .5f)));

            int x = 0;
            for(ChildDTO c : p.getChildren()){
                Color cellColor = null;

                if(x % 3 > 0){
                    cellColor = alt;
                }else{
                    cellColor = ColorConstants.WHITE;
                }

                for(GiftDTO g : c.getGifts()){
                    table.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setBorder(new SolidBorder(headerColor, .5f)));
                    table.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setBorder(new SolidBorder(headerColor, .5f)));
                    table.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setBorder(new SolidBorder(headerColor, .5f)));
                    table.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setBorder(new SolidBorder(headerColor, .5f)));
                    table.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setBorder(new SolidBorder(headerColor, .5f)));
                    table.addCell(new Cell().add(new Paragraph(c.getName() + "-" + c.getAge() + c.getGender()).setFont(regularFont).setFontSize(10)).setBackgroundColor(cellColor).setBorder(new SolidBorder(headerColor, .5f)));
                    table.addCell(new Cell().add(new Paragraph(g.getDescription()).setFont(regularFont).setFontSize(10)).setBackgroundColor(cellColor).setBorder(new SolidBorder(headerColor, .5f)));
                    String sponsorName = "";
                    String sponsorPhone = "";

                    if(g.getSponsor() != null){
                        sponsorName = g.getSponsor().getFirstName() + " " + g.getSponsor().getLastName();
                        sponsorPhone = g.getSponsor().getPhone() == null? "" : g.getSponsor().getPhone();
                    }

                    table.addCell(new Cell().add(new Paragraph(sponsorName).setFont(regularFont).setFontSize(10)).setBackgroundColor(cellColor).setBorder(new SolidBorder(headerColor, .5f)));
                    table.addCell(new Cell().add(new Paragraph(sponsorPhone).setFont(regularFont).setFontSize(10)).setBackgroundColor(cellColor).setBorder(new SolidBorder(headerColor, .5f)));
                    table.addCell(new Cell().setBackgroundColor(cellColor).setBorder(new SolidBorder(headerColor, .5f)));
                    table.addCell(new Cell().setBackgroundColor(cellColor).setBorder(new SolidBorder(headerColor, .5f)));
                }
                x++;
            }

            table.addCell(new Cell(1, 11).add(new Paragraph("")).setBackgroundColor(separator).setBorder(new SolidBorder(headerColor, .5f)));
        }

        document.add(table);
        return pdf;
    }

    public ByteArrayOutputStream printExcelRoster(RosterDTO roster) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Blessing Tree");

        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Last Name", "First Name", "Phone", "Phone", "MHID", "Children", "BT Gifts(Optional)", "Sponsor", "Phone", "Gift Rcvd", "Picked Up"};

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        return out;
    }

    protected class customEventHandler extends AbstractPdfDocumentEventHandler{
        @Override
        protected void onAcceptedEvent(AbstractPdfDocumentEvent abstractPdfDocumentEvent) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) abstractPdfDocumentEvent;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            int pageNumber = pdfDoc.getPageNumber(page);

            PdfCanvas canvas = new PdfCanvas(page);
            Rectangle rectangle = page.getPageSize();
            Canvas layoutCanvas = new Canvas(canvas, rectangle);

            // Set the position for the page number (bottom center)
            float x = page.getPageSize().getWidth() / 2;
            float y = 25; // Adjust this value to position the page number

            // Add the page number text
            layoutCanvas.showTextAligned(
                    new Paragraph(String.format("Page %d", pageNumber)).setFontSize(10),
                    x, y, TextAlignment.CENTER
            );
        }
    }
}
