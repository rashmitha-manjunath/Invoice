package com.springboot.invoice.service;

import com.springboot.invoice.model.Invoice;
import com.springboot.invoice.repositories.InvoiceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceGeneratorServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private InvoiceGeneratorService invoiceGeneratorService;

    @Test
    public void testCreateNewInvoice() {
        double amount = 100.00;
        Invoice newInvoice = new Invoice();
        newInvoice.setAmount(amount);
        newInvoice.setDueDate(LocalDate.now().plusDays(30));
        newInvoice.setStatus("PENDING");

        when(invoiceRepository.save(any(Invoice.class))).thenReturn(newInvoice);

        Invoice createdInvoice = invoiceGeneratorService.createNewInvoice(amount);

        assertEquals(amount, createdInvoice.getAmount(), 0.01);
        assertEquals(LocalDate.now().plusDays(30), createdInvoice.getDueDate());
        assertEquals("PENDING", createdInvoice.getStatus());

        verify(invoiceRepository, times(1)).save(any(Invoice.class));
    }
}
