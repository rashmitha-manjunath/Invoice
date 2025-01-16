package com.springboot.invoice.service;

import com.springboot.invoice.model.Invoice;
import com.springboot.invoice.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;

@Service
public class InvoiceGeneratorService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice createNewInvoice(double amount) {
        Invoice newInvoice = new Invoice();
        newInvoice.setAmount(amount);
        newInvoice.setDueDate(LocalDate.now().plusDays(30)); // Set new due date
        newInvoice.setStatus("PENDING"); // Set to pending initially
        newInvoice.setPaidAmount(0.0);
        return invoiceRepository.save(newInvoice); // Save and return the new invoice
    }
}