package com.martins.wordtopdf.service.impl;

import com.martins.wordtopdf.exception.ConversionException;
import com.martins.wordtopdf.service.ConversionService;
import com.martins.wordtopdf.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversionServiceImpl implements ConversionService {
    private final FileStorageService fileStorageService;

    public byte[] convertToPdf(MultipartFile file) throws ConversionException {
        String filePath = fileStorageService.storeFile(file);

        try {
            InputStream input = new FileInputStream(filePath);
            XWPFDocument document = new XWPFDocument(input);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            PDDocument pdfDocument = new PDDocument();
            PDPage page = new PDPage();
            pdfDocument.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page);

            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    contentStream.showText(text);
                }
            }

            contentStream.close();
            pdfDocument.save(outputStream);
            pdfDocument.close();

            byte[] pdfBytes = outputStream.toByteArray();
            outputStream.close();
            document.close();
            return pdfBytes;
        } catch (Exception e) {
            throw new ConversionException("Failed to convert Word document to PDF", e);
        }
    }
}
