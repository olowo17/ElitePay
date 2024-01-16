package com.michael.libertybank.services;

import com.michael.libertybank.dto.transaction.TransactionDetails;
import com.michael.libertybank.util.PdfGenerator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {

    public void generateReceipt(TransactionDetails transactionDetails, HttpServletResponse response) {
        PdfGenerator.generateTransactionPdf(transactionDetails, response);
    }
}

