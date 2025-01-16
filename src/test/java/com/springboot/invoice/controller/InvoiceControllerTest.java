package com.springboot.invoice.controller;

import com.springboot.invoice.model.Invoice;
import com.springboot.invoice.model.Payment;
import com.springboot.invoice.service.InvoiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class InvoiceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private InvoiceService invoiceService;

    @InjectMocks
    private InvoiceController invoiceController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(invoiceController).build();
    }

    @Test
    public void testCreateInvoice() throws Exception {
        Invoice invoice = new Invoice();
        when(invoiceService.createInvoice(any(Invoice.class))).thenReturn(invoice);

        mockMvc.perform(post("/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"amount\": 100.0}")) // Example JSON
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAllInvoices() throws Exception {
        when(invoiceService.getAllInvoices()).thenReturn(Collections.singletonList(new Invoice()));

        mockMvc.perform(get("/invoices"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetInvoiceById() throws Exception {
        Invoice invoice = new Invoice();
        when(invoiceService.getInvoiceById(anyLong())).thenReturn(Optional.of(invoice));

        mockMvc.perform(get("/invoices/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void testPayInvoice() throws Exception {
        Payment payment = new Payment();
        payment.setAmount(50.0);
        Invoice invoice = new Invoice();

        when(invoiceService.payInvoice(anyLong(), anyDouble())).thenReturn(invoice);
        when(invoiceService.getInvoices()).thenReturn(Collections.singletonList(invoice)); // Mocking getInvoices

        mockMvc.perform(post("/invoices/{id}/payments", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 50.0}"))
                .andExpect(status().isOk());

    }

    @Test
    public void testUpdatePaymentInvoice() throws Exception {
        Payment payment = new Payment();
        payment.setAmount(75.0);
        Invoice invoice = new Invoice();

        when(invoiceService.payInvoice(anyLong(), anyDouble())).thenReturn(invoice);
        when(invoiceService.getInvoices()).thenReturn(Collections.singletonList(invoice));

        mockMvc.perform(put("/invoices/{id}/payments", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 75.0}")) // Example JSON for payment
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteInvoice() throws Exception {
        doNothing().when(invoiceService).deleteInvoice(anyLong());

        mockMvc.perform(delete("/invoices/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testProcessOverdueInvoices() throws Exception {
        doNothing().when(invoiceService).processOverdueInvoices();

        mockMvc.perform(post("/invoices/process-overdue"))
                .andExpect(status().isOk())
                .andExpect(content().string("Overdue invoices processed."));
    }
}
