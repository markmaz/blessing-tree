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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
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
        byte[] rgbH = {(byte)255, (byte)217, (byte)102};
        byte[] rgb = {(byte) 255, (byte) 235, (byte) 156};
        XSSFColor color = new XSSFColor(rgbH);
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Last Name", "First Name", "Phone", "Phone", "MHID", "Children", "BT Gifts(Optional)", "Sponsor", "Phone", "Gift Rcvd", "Picked Up"};

        for (int i = 0; i < headers.length; i++) {
            org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            style.setFillForegroundColor(color);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
        }

        int row = 1;
        for(ParentDTO parent : roster.getParents()){
            Row pRow = sheet.createRow(row);
            pRow.createCell(0).setCellValue(parent.getBtid());
            pRow.createCell(1).setCellValue(parent.getLastName());
            pRow.createCell(2).setCellValue(parent.getFirstName());
            pRow.createCell(3).setCellValue(parent.getPrimaryPhone());
            pRow.createCell(4).setCellValue(parent.getSecondaryPhone());
            pRow.createCell(5).setCellValue(parent.getMhid() + "");
            pRow.createCell(6).setCellValue(parent.getChildren().size());
            pRow.createCell(7);
            pRow.createCell(8);
            pRow.createCell(9);
            pRow.createCell(10);
            pRow.createCell(11);
            row++;

            for(ChildDTO child: parent.getChildren()){
                for(GiftDTO gift: child.getGifts()){
                    Row cRow = sheet.createRow(row);
                    cRow.createCell(0);
                    cRow.createCell(1);
                    cRow.createCell(2);
                    cRow.createCell(3);
                    cRow.createCell(4);
                    cRow.createCell(5);
                    cRow.createCell(6).setCellValue(child.getName() + "-" + child.getGender().substring(0, 1).toUpperCase() + (child.getAge() == null ? "" : child.getAge()));
                    cRow.createCell(7).setCellValue(gift.getDescription());

                    if(gift.getSponsor() != null){
                        cRow.createCell(8).setCellValue(gift.getSponsor().getFirstName() + " " + gift.getSponsor().getLastName());
                        cRow.createCell(9).setCellValue(gift.getSponsor().getPhone());
                        cRow.createCell(10).setCellValue(gift.getSponsor().getGiftStatus());
                    }else{
                        cRow.createCell(8);
                        cRow.createCell(9);
                        cRow.createCell(10);
                    }

                    cRow.createCell(11);
                    row ++;
                }
            }
            Row sRow = sheet.createRow(row);
            CellStyle spaceStyle = workbook.createCellStyle();
            XSSFColor cellColor = new XSSFColor(rgb);
            spaceStyle.setFillForegroundColor(cellColor);
            spaceStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            for(int i = 0; i < headers.length; i++){
                sRow.createCell(i).setCellStyle(spaceStyle);
            }

            row++;
        }

        autoSizeColumns(workbook);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        return out;
    }

    public void autoSizeColumns(Workbook workbook) {
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet.getPhysicalNumberOfRows() > 0) {
                Row row = sheet.getRow(sheet.getFirstRowNum());
                Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();
                    sheet.autoSizeColumn(columnIndex);
                }
            }
        }
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
