package com.springboot.invoice.service;

import com.springboot.invoice.exception.ResourceNotFoundException;
import com.springboot.invoice.model.Invoice;
import com.springboot.invoice.repositories.InvoiceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private InvoiceService invoiceService;

    @Mock
    private InvoiceGeneratorService invoiceGeneratorService;

    @Before
    public void setup() {
        when(invoiceGeneratorService.createNewInvoice(any())).thenReturn(new Invoice());
    }

    @Test
    public void testCreateInvoice() {
        Invoice invoice = new Invoice();
        when(invoiceRepository.save(any())).thenReturn(invoice);

        Invoice result = invoiceService.createInvoice(invoice);

        assertEquals(invoice, result);
    }

    @Test
    public void testGetAllInvoices() {
        List<Invoice> invoices = Arrays.asList(new Invoice(), new Invoice());
        when(invoiceRepository.findAll()).thenReturn(invoices);

        List<Invoice> result = invoiceService.getAllInvoices();

        assertEquals(invoices, result);
    }

    @Test
    public void testGetInvoiceByIdFound() {
        Invoice invoice = new Invoice();
        when(invoiceRepository.findById(any())).thenReturn(Optional.of(invoice));

        Optional<Invoice> result = invoiceService.getInvoiceById(1L);

        assertTrue(result.isPresent());
        assertEquals(invoice, result.get());
    }

    @Test
    public void testGetInvoiceByIdNotFound() {
        when(invoiceRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> invoiceService.getInvoiceById(1L));
    }

    @Test
    public void testUpdateInvoice() {
        Long id = 1L;
        Invoice newInvoice = new Invoice();
        when(invoiceRepository.findById(any())).thenReturn(Optional.of(new Invoice()));
        when(invoiceRepository.save(any())).thenReturn(newInvoice);

        Invoice result = invoiceService.updateInvoice(id, newInvoice);

        assertEquals(newInvoice, result);
        verify(invoiceRepository, times(1)).save(any());
    }

    @Test
    public void testDeleteInvoice() throws Exception {
        Long id = 1L;
        Invoice existingInvoice = new Invoice();
        when(invoiceRepository.findById(any())).thenReturn(Optional.of(existingInvoice));
        doNothing().when(invoiceRepository).delete(any());

        invoiceService.deleteInvoice(id);
        verify(invoiceRepository, times(1)).delete(any());
    }

    @Test
    public void testPayInvoice() {
        Long id = 1L;
        Invoice existingInvoice = new Invoice();
        existingInvoice.setPaidAmount(0.00);
        existingInvoice.setStatus("pending");
        when(invoiceRepository.findById(any())).thenReturn(Optional.of(existingInvoice));
        when(invoiceRepository.save(any())).thenReturn(existingInvoice);

        Invoice result = invoiceService.payInvoice(id, 10.00);

        assertEquals(existingInvoice, result);
        assertEquals(10.00, result.getPaidAmount(), 0.01);
    }

    @Test
    public void testProcessOverdueInvoices() {
        List<Invoice> overdueInvoices = Arrays.asList(new Invoice(), new Invoice());
        when(invoiceRepository.findOverdueInvoices()).thenReturn(overdueInvoices);

        invoiceService.processOverdueInvoices();

    }
}
