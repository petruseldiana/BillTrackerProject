package com.proiect.inginerie.software.mapper;

import com.proiect.inginerie.software.controller.dtos.BillDTO;
import com.proiect.inginerie.software.entity.Bill;

import java.text.ParseException;

public interface BillMapper {
    Bill toEntity(final BillDTO billDTO) throws ParseException;

    BillDTO toDto(Bill bill);
}
