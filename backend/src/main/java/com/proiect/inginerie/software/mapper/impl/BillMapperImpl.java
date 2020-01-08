package com.proiect.inginerie.software.mapper.impl;

import com.proiect.inginerie.software.controller.dtos.BillDTO;
import com.proiect.inginerie.software.mapper.BillMapper;
import com.proiect.inginerie.software.entity.Bill;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class BillMapperImpl implements BillMapper {
    @Override
    public Bill toEntity(BillDTO billDTO) throws ParseException {
        if (billDTO != null) {
            // convert the string date into a Date() object
            //pattern of string date should be "dd-MM-yy"
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            //convert the due date
            Date date = formatter.parse(billDTO.getDue());
            java.sql.Date dueDate = new java.sql.Date(date.getTime());

            //convert the creation date
            Date date1 = formatter.parse(billDTO.getCreationDate());
            java.sql.Date creationDate = new java.sql.Date(date1.getTime());

            Bill bill = new Bill();
            bill.setId(billDTO.getId());
            bill.setPrice(billDTO.getPrice());
            bill.setName(billDTO.getName());
            bill.setDue(dueDate);
            bill.setStatus(billDTO.getStatus());
            bill.setCreationDate(creationDate);
            bill.setDetails(billDTO.getDetails());

            return bill;
        } else return null;
    }

    @Override
    public BillDTO toDto(Bill bill) {
        if (bill != null) {
            //convert due date
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dueDate = dateFormat.format(bill.getDue());

            //convert creationDate
            String creationDate = dateFormat.format(bill.getCreationDate());

            BillDTO billDTO = new BillDTO();
            billDTO.setId(bill.getId());
            billDTO.setPrice(bill.getPrice());
            billDTO.setName(bill.getName());
            billDTO.setDue(dueDate);
            billDTO.setStatus(bill.getStatus());
            billDTO.setDetails(bill.getDetails());
            billDTO.setCreationDate(creationDate);

            return billDTO;
        } else return null;
    }
}
