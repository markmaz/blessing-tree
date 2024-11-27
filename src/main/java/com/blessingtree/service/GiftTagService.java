package com.blessingtree.service;

import com.blessingtree.dto.*;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class GiftTagService {
    private final float boxWidth = 7.5f * 72f; // 4 inches
    private final float boxHeight = 3 * 72f; // 3 inches
    private final float horizontalMargin = 36f; // Left margin
    private final float verticalSpacing = 10f; // Spacing between boxes

    private final float topMargin = 36f; // 0.5 inches
    private final float bottomMargin = 36f; // 0.5 inches

    public PdfDocument printGiftTagFromSponsor(PdfDocument pdf, SponsorDTO sponsorDTO) throws IOException {
        List<TagInfo> tags = extractTagsFromSponsor(sponsorDTO);

        return printGiftTag(pdf, tags);
    }

    public PdfDocument printGiftTagFromParent(PdfDocument pdf, List<ParentDTO> parents) throws IOException {
        List<TagInfo> tags = extractTags(parents);
        return printGiftTag(pdf, tags);
    }

    public PdfDocument printGiftTag(PdfDocument pdf, int numberOfTags) throws IOException {
        List<TagInfo> tags = generateBlankTags(numberOfTags);
        return printGiftTag(pdf, tags);
    }

    private PdfDocument printGiftTag(PdfDocument pdf, List<TagInfo> tags) throws IOException {
        PdfPage page = pdf.addNewPage();

        float pageHeight = page.getPageSize().getHeight();
        float currentY = pageHeight - topMargin;

        for (TagInfo tag : tags) {
            if (checkCurrent(currentY)) {
                page = pdf.addNewPage();
                currentY = pageHeight - topMargin;
            }

            float x = horizontalMargin;
            float y = currentY - boxHeight;

            PdfCanvas pdfCanvas = new PdfCanvas(page);
            Rectangle rectangle = getRectangle(x, y, pdfCanvas);

            Canvas canvas = new Canvas(pdfCanvas, rectangle, true);
            createLabels(rectangle, canvas, tag);
            createTag(x, y, pdfCanvas, rectangle, canvas);
            currentY -= (boxHeight + verticalSpacing);
        }

        return pdf;
    }

    private static List<TagInfo> extractTagsFromSponsor(SponsorDTO sponsorDTO) {
        List<TagInfo> tags = sponsorDTO.getGifts().stream()
                .map(gift -> new TagInfo(
                        gift.getChild().getParent().getBTID(),
                        gift.getChild().getName(),
                        gift.getChild().getAge(),
                        gift.getChild().getGender(),
                        gift.getDescription()
                ))
                .toList();
        return tags;
    }
    private List<TagInfo> extractTags(List<ParentDTO> parents) {
        return parents.stream()
                .flatMap(parent -> parent.getChildren().stream()
                        .flatMap(child -> child.getGifts().stream()
                                .filter(gift -> gift.getSponsor() != null)
                                .map(gift -> new TagInfo(parent.getBtid(), child.getName(), child.getAge(), child.getGender(), gift.getDescription()))
                        )
                )
                .toList();
    }

    private List<TagInfo> generateBlankTags(int numberOfTags) {
        List<TagInfo> tags = new ArrayList<>();
        for (int i = 0; i < numberOfTags; i++) {
            tags.add(new TagInfo());
        }
        return tags;
    }

    private boolean checkCurrent(float currentY) {
        return currentY - boxHeight < bottomMargin;
    }

    private void createTag(float x, float y, PdfCanvas pdfCanvas, Rectangle rectangle, Canvas canvas) throws IOException {
        drawLines(rectangle, pdfCanvas);
        drawInnerRectangle(rectangle, pdfCanvas);
        drawTagImage(x, y, canvas);
    }

    private Rectangle getRectangle(float x, float y, PdfCanvas pdfCanvas) {
        Rectangle rectangle = new Rectangle(x, y, boxWidth, boxHeight);
        pdfCanvas.setStrokeColor(ColorConstants.BLACK) // Set border color
                .setLineWidth(1) // Set border thickness
                .roundRectangle(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), 15) // Define the rectangle
                .stroke(); // Draw the border
        return rectangle;
    }

    private void drawTagImage(float x, float y, Canvas canvas) throws IOException {
        ClassPathResource imgResource = new ClassPathResource("static/x-mas.png");
        InputStream imageStream = imgResource.getInputStream();
        ImageData imageData = ImageDataFactory.create(imageStream.readAllBytes());
        Image image = new Image(imageData);

        float imageX = x + boxWidth - (boxWidth / 4); // Align to the right side
        float imageWidth = boxWidth / 4.5f; // Adjust image width to fit
        image.setFixedPosition(imageX + 15, y + 90);
        image.scaleToFit(imageWidth, boxHeight);

        canvas.add(image);
    }

    private static void drawInnerRectangle(Rectangle rectangle, PdfCanvas pdfCanvas) {
        float padding = 10;
        Rectangle inner = new Rectangle(rectangle.getLeft() + padding,
                rectangle.getBottom() + padding,
                rectangle.getWidth() - 2 * padding,
                54);
        pdfCanvas.rectangle(inner);
        pdfCanvas.setLineWidth(.5f).stroke();
    }

    private static void createLabels(Rectangle rectangle, Canvas canvas, TagInfo tagInfo) throws IOException {
        ClassPathResource fontResource = new ClassPathResource("static/Freedom_Fonts.ttf");
        InputStream fontStream = fontResource.getInputStream();
        PdfFont boldFont = PdfFontFactory.createFont(IOUtils.toByteArray(fontStream), PdfEncodings.WINANSI);
        Color custom = new DeviceRgb(235, 84, 82);

        canvas.add(new Paragraph("2024 Blessing Tree")
                .setMargin(5).setPadding(5).setFontSize(24).setFontColor(custom).setFont(boldFont));
        canvas.showTextAligned(new Paragraph("Office of Social Services").setFontSize(10).setFont(boldFont), rectangle.getX() + 10, rectangle.getY() + 110, TextAlignment.LEFT);
        canvas.showTextAligned(new Paragraph("Magdalene House").setFontSize(10).setFont(boldFont), rectangle.getX() + 10, rectangle.getY() + 95, TextAlignment.LEFT);
        canvas.showTextAligned(new Paragraph("Special Instructions:").setFontSize(10).setFont(boldFont), rectangle.getX() + 10, rectangle.getY() + 65, TextAlignment.LEFT);
        canvas.showTextAligned(new Paragraph("BT:").setFontSize(10).setFont(boldFont), rectangle.getX() + 190, rectangle.getY() + 150, TextAlignment.LEFT);
        canvas.showTextAligned(new Paragraph("Child:").setFontSize(10).setFont(boldFont), rectangle.getX() + 190, rectangle.getY() + 130, TextAlignment.LEFT);
        canvas.showTextAligned(new Paragraph("Gift:").setFontSize(10).setFont(boldFont), rectangle.getX() + 190, rectangle.getY() + 110, TextAlignment.LEFT);

        Color lightGray = new DeviceRgb(237, 237, 237);
        canvas.showTextAligned(new Paragraph(tagInfo.getId()).setFontSize(14).setFont(boldFont), rectangle.getX() + (190 + 45 + 9), rectangle.getY() + 152, TextAlignment.LEFT);
        canvas.showTextAligned(new Paragraph(tagInfo.getChildInfo()).setFontSize(14).setFont(boldFont), rectangle.getX() + (190 + 25), rectangle.getY() + 130, TextAlignment.LEFT);
        canvas.showTextAligned(new Paragraph(tagInfo.getDescription()).setFontSize(14).setFont(boldFont).setFontColor(lightGray), rectangle.getX() + (190 + 25), rectangle.getY() + 110, TextAlignment.LEFT);
//        canvas.showTextAligned(new Paragraph("Test").setFontSize(14).setFont(boldFont), rectangle.getX() + (190 + 45 + 9), rectangle.getY() + 152, TextAlignment.LEFT);
//        canvas.showTextAligned(new Paragraph("test").setFontSize(14).setFont(boldFont), rectangle.getX() + (190 + 45), rectangle.getY() + 130, TextAlignment.LEFT);
//        canvas.showTextAligned(new Paragraph("test").setFontSize(14).setFont(boldFont).setFontColor(lightGray), rectangle.getX() + (190 + 15), rectangle.getY() + 110, TextAlignment.LEFT);
    }

    private static void drawLines(Rectangle rectangle, PdfCanvas pdfCanvas) {
        //BT ID
        pdfCanvas.moveTo(rectangle.getX() + 210, rectangle.getY() + 150); // Starting point
        pdfCanvas.lineTo(rectangle.getX() + 300, rectangle.getY() + 150); // Ending point
        pdfCanvas.setLineWidth(.5f).stroke(); // Render the line
        //Child
        pdfCanvas.moveTo(rectangle.getX() + 215, rectangle.getY() + 130); // Starting point
        pdfCanvas.lineTo(rectangle.getX() + 425, rectangle.getY() + 130); // Ending point
        pdfCanvas.setLineWidth(.5f).stroke();
        //Gift
        pdfCanvas.moveTo(rectangle.getX() + 210, rectangle.getY() + 110); // Starting point
        pdfCanvas.lineTo(rectangle.getX() + 425, rectangle.getY() + 110); // Ending point
        pdfCanvas.setLineWidth(.5f).stroke();

        pdfCanvas.moveTo(rectangle.getX() + 210, rectangle.getY() + 90); // Starting point
        pdfCanvas.lineTo(rectangle.getX() + 425, rectangle.getY() + 90); // Ending point
        pdfCanvas.setLineWidth(.5f).stroke();
    }
}
