package com.proiect.inginerie.software.service;

import com.proiect.inginerie.software.controller.dtos.BillChartDto;
import com.proiect.inginerie.software.controller.dtos.BillDTO;
import com.proiect.inginerie.software.entity.Bill;

import java.util.List;

public interface BillService {
    Bill findById(Long id);

    Long updateBillStatus(Long id);

    Long update(BillDTO billDTO);

    List<BillDTO> filterBills(Boolean status);

    List<BillChartDto> filterBills(String startDate, String endDate);

    List<BillDTO> findAll();

    Boolean delete(Long id);
}
