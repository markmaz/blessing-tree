package com.blessingtree.service;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class PdfService {
    public PdfDocument printGiftTag(PdfDocument pdf, int numberOfTags) throws IOException {
        PdfPage page = pdf.addNewPage();

        // Get the page dimensions
        float pageWidth = page.getPageSize().getWidth();
        float pageHeight = page.getPageSize().getHeight();
        float topMargin = 36f; // 0.5 inches
        float bottomMargin = 36f; // 0.5 inches
        float availableHeight = pageHeight - topMargin - bottomMargin;

        // Box dimensions
        float boxWidth = 7.5f * 72f; // 4 inches
        float boxHeight = 3 * 72f; // 3 inches
        float horizontalMargin = 36f; // Left margin
        float verticalSpacing = 10f; // Spacing between boxes

        // Track current position
        float currentY = pageHeight - topMargin;

        for (int i = 0; i < numberOfTags; i++) {
            if (currentY - boxHeight < bottomMargin) {
                // Add a new page
                page = pdf.addNewPage();
                currentY = pageHeight - topMargin; // Reset position to top of the new page
            }

            float pageAdjustment = (i + 1);

            // Position the box at the upper-left corner
//            float x = .35f * 72; // Left edge of the page
//            float y = pageHeight - topMargin - ((boxHeight + 10) * (i + 1));
            float x = horizontalMargin;
            float y = currentY - boxHeight;

            Rectangle rectangle = new Rectangle(x, y, boxWidth, boxHeight);

            // Draw the rectangle
            PdfCanvas pdfCanvas = new PdfCanvas(page);
            pdfCanvas.setStrokeColor(ColorConstants.BLACK) // Set border color
                    .setLineWidth(1) // Set border thickness
                    .rectangle(rectangle) // Define the rectangle
                    .stroke(); // Draw the border

            ClassPathResource fontResource = new ClassPathResource("static/Freedom_Fonts.ttf");
            InputStream fontStream = fontResource.getInputStream();
            PdfFont boldFont = PdfFontFactory.createFont(IOUtils.toByteArray(fontStream), PdfEncodings.WINANSI);
//            ClassPathResource fontResource = new ClassPathResource("static/Freedom_Fonts.ttf");
//            File fontResourceFile = fontResource.getFile();
            Color custom = new DeviceRgb(235, 84, 82);
            // Optionally add text inside the box
            //PdfFont boldFont = PdfFontFactory.createFont(fontResourceFile.getAbsolutePath(), "WinAnsi");
            Canvas canvas = new Canvas(pdfCanvas, rectangle, true);
            canvas.add(new com.itextpdf.layout.element.Paragraph("2024 Blessing Tree")
                    .setMargin(5).setPadding(5).setFontSize(24).setFontColor(custom).setFont(boldFont));
            canvas.showTextAligned(new Paragraph("Office of Social Services").setFontSize(10).setFont(boldFont), rectangle.getX() + 10, rectangle.getY() + 110, TextAlignment.LEFT);
            canvas.showTextAligned(new Paragraph("Magdalene House").setFontSize(10).setFont(boldFont), rectangle.getX() + 10, rectangle.getY() + 95, TextAlignment.LEFT);
            canvas.showTextAligned(new Paragraph("Special Instructions:").setFontSize(10).setFont(boldFont), rectangle.getX() + 10, rectangle.getY() + 65, TextAlignment.LEFT);
            canvas.showTextAligned(new Paragraph("BT:").setFontSize(10).setFont(boldFont), rectangle.getX() + 190, rectangle.getY() + 150, TextAlignment.LEFT);
            canvas.showTextAligned(new Paragraph("Boy:").setFontSize(10).setFont(boldFont), rectangle.getX() + 190, rectangle.getY() + 130, TextAlignment.LEFT);
            canvas.showTextAligned(new Paragraph("Girl:").setFontSize(10).setFont(boldFont), rectangle.getX() + 315, rectangle.getY() + 130, TextAlignment.LEFT);
            canvas.showTextAligned(new Paragraph("Gift:").setFontSize(10).setFont(boldFont), rectangle.getX() + 190, rectangle.getY() + 110, TextAlignment.LEFT);
            pdfCanvas.moveTo(rectangle.getX() + 210, rectangle.getY() + 150); // Starting point
            pdfCanvas.lineTo(rectangle.getX() + 300, rectangle.getY() + 150); // Ending point
            pdfCanvas.setLineWidth(.5f).stroke(); // Render the line

            pdfCanvas.moveTo(rectangle.getX() + 210, rectangle.getY() + 130); // Starting point
            pdfCanvas.lineTo(rectangle.getX() + 300, rectangle.getY() + 130); // Ending point
            pdfCanvas.setLineWidth(.5f).stroke();

            pdfCanvas.moveTo(rectangle.getX() + 335, rectangle.getY() + 130); // Starting point
            pdfCanvas.lineTo(rectangle.getX() + 425, rectangle.getY() + 130); // Ending point
            pdfCanvas.setLineWidth(.5f).stroke();

            pdfCanvas.moveTo(rectangle.getX() + 210, rectangle.getY() + 110); // Starting point
            pdfCanvas.lineTo(rectangle.getX() + 425, rectangle.getY() + 110); // Ending point
            pdfCanvas.setLineWidth(.5f).stroke();

            float padding = 10;
            Rectangle inner = new Rectangle(rectangle.getLeft() + padding,
                    rectangle.getBottom() + padding,
                    rectangle.getWidth() - 2 * padding,
                    54);
            pdfCanvas.rectangle(inner);
            pdfCanvas.setLineWidth(.5f).stroke();

            ClassPathResource imgResource = new ClassPathResource("static/xmas_tree.png");
            //File imgFile = imgResource.getFile();
            InputStream imageStream = imgResource.getInputStream();
            ImageData imageData = ImageDataFactory.create(imageStream.readAllBytes());
            Image image = new Image(imageData);

            // Set image position and size
            float imageX = x + boxWidth - (boxWidth / 4); // Align to the right side
            float imageWidth = boxWidth / 4.5f; // Adjust image width to fit
            image.setFixedPosition(imageX + 15, y + 90);
            image.scaleToFit(imageWidth, boxHeight);

            canvas.add(image);
            currentY -= (boxHeight + verticalSpacing);
        }

        return pdf;
    }
}
