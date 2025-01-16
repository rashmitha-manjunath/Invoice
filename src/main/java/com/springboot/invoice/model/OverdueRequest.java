package com.springboot.invoice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OverdueRequest {

    private Double lateFee;
    private Integer overdueDays;
}
