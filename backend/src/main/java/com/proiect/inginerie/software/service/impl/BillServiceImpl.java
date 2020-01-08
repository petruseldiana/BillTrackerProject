package com.proiect.inginerie.software.service.impl;

import com.proiect.inginerie.software.controller.dtos.BillChartDto;
import com.proiect.inginerie.software.controller.dtos.BillDTO;
import com.proiect.inginerie.software.repository.UserRepository;
import com.proiect.inginerie.software.entity.Bill;
import com.proiect.inginerie.software.entity.CurrentUser;
import com.proiect.inginerie.software.mapper.BillMapper;
import com.proiect.inginerie.software.repository.BillRepository;
import com.proiect.inginerie.software.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final BillMapper billMapper;

    @Autowired
    public BillServiceImpl(BillRepository billRepository, UserRepository userRepository, BillMapper billMapper) {
        this.billRepository = billRepository;
        this.userRepository = userRepository;
        this.billMapper = billMapper;
    }


    @Override
    public Bill findById(Long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find bill with ID: " + id));
    }

    @Override
    public Long updateBillStatus(Long id) {
        Bill bill = billRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Cannot find article with ID: " + id));
        bill.setStatus(true);
      return billRepository.save(bill).getId();
    }

    @Override
    public List<BillDTO> filterBills(Boolean status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CurrentUser userPrincipal = (CurrentUser) authentication.getPrincipal();
        return billRepository.findAll()
                .stream()
                .filter(bill -> bill.getStatus().equals(status) && bill.getUser().getId().equals(userPrincipal.getId()))
                .map(billMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BillChartDto> filterBills(String startDate, String endDate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CurrentUser userPrincipal = (CurrentUser) authentication.getPrincipal();

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        List<BillChartDto> listDto = new ArrayList<>();

        //convert dates
        java.sql.Date start;
        java.sql.Date end;
        try {
            start = new java.sql.Date(formatter.parse(startDate).getTime());
            end = new java.sql.Date(formatter.parse(endDate).getTime());

            List<Bill> list =  billRepository.findAll()
                    .stream()
                    .filter(bill ->
                            bill.getDue().compareTo(start) >= 0
                                    && bill.getDue().compareTo(end) <= 0
                                    && bill.getUser().getId().equals(userPrincipal.getId()))
                    .collect(Collectors.toList());

            Map<java.sql.Date,Double> map = list.stream().collect(Collectors.groupingBy(Bill::getDue, TreeMap::new, Collectors.summingDouble(Bill::getPrice)));

            listDto = map.entrySet()
                .stream()
                .map(e -> new BillChartDto(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

            System.out.println(listDto);
                
        } catch (ParseException e) {
           return listDto;
        }
        return  listDto;
    }

    @Override
    public List<BillDTO> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CurrentUser userPrincipal = (CurrentUser) authentication.getPrincipal();
        return billRepository.findAll()
                .stream()
                .filter(bill -> bill.getUser().getId().equals(userPrincipal.getId()))
                .map(billMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean delete(Long id) {
        Optional<Bill> optionalBill;
        optionalBill = billRepository.findById(id);

        if (optionalBill.isPresent()) {
            billRepository.deleteById(optionalBill.get().getId());
            return true;
        } else return false;
    }

    @Override
    public Long update(BillDTO billDTO) {
        try {
            Bill bill = billMapper.toEntity(billDTO);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CurrentUser userPrincipal = (CurrentUser) authentication.getPrincipal();
            bill.setUser(userRepository.findById(userPrincipal.getId()).get());

            return billRepository.save(bill).getId();

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
