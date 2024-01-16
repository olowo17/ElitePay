package com.michael.libertybank.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.DashedLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.michael.libertybank.dto.transaction.TransactionDetails;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class PdfGenerator {

    public static void generateTransactionPdf(TransactionDetails transactionDetails, HttpServletResponse response) {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=transaction.pdf");

        try (PdfWriter writer = new PdfWriter(response.getOutputStream());
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            Paragraph centeredText = new Paragraph("LibertyApp Transaction Receipt");
            centeredText.setTextAlignment(TextAlignment.CENTER);
            document.add(centeredText);

            document.add(new LineSeparator(new DashedLine()));

            // Add content to the PDF
            document.add(new Paragraph("Transaction ID: " + transactionDetails.getTransactionId()));
            document.add(new Paragraph("Transaction Date: " + transactionDetails.getTransactionDate()));
            document.add(new Paragraph("Sender: " + transactionDetails.getSenderFullName()));
            document.add(new Paragraph("Receiver: " + transactionDetails.getReceiverFullName()));
            document.add(new Paragraph("Transfer Amount: " + transactionDetails.getTransactionAmount()));
            document.add(new Paragraph("Currency: " + transactionDetails.getTransactionCurrency()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
