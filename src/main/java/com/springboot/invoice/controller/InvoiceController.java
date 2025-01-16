package com.springboot.invoice.controller;

import com.springboot.invoice.exception.ResourceNotFoundException;
import com.springboot.invoice.model.Invoice;
import com.springboot.invoice.model.Payment;
import com.springboot.invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<Long> createInvoice(@RequestBody Invoice invoice) {
        Invoice createdInvoice = invoiceService.createInvoice(invoice);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInvoice.getId());
    }

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        Invoice invoice = invoiceService.getInvoiceById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));
        return ResponseEntity.ok(invoice);
    }

    @PostMapping("/{id}/payments")
    public ResponseEntity<Invoice> payInvoice(@PathVariable Long id, @RequestBody Payment payment) {
        Invoice invoice = invoiceService.payInvoice(id, payment.getAmount());
        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }

    @PutMapping("/{id}/payments")
    public ResponseEntity<Invoice> updatepaymentInvoice(@PathVariable Long id, @RequestBody Payment payment) {
        Invoice invoice = invoiceService.payInvoice(id, payment.getAmount());
        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping("/process-overdue")
    public ResponseEntity<String> processOverdueInvoices() {
        invoiceService.processOverdueInvoices();
        return ResponseEntity.ok("Overdue invoices processed.");
    }
}