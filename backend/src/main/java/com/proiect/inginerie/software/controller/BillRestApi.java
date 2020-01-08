package com.proiect.inginerie.software.controller;

import com.proiect.inginerie.software.controller.dtos.BillDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api")
public interface BillRestApi {

    @GetMapping("/get/{id}")
    ResponseEntity<?> findById(@PathVariable("id") Long id);

    @PostMapping("/updateBillStatus/{id}")
    ResponseEntity<Boolean> updateStatus(@PathVariable("id") Long id);

    @PostMapping("/updateBill")
    ResponseEntity<Boolean> update(BillDTO billDTO);

    @PostMapping("/saveBill")
    ResponseEntity<Boolean> save(BillDTO billDTO);

    @GetMapping("/getFilteredBills")
    ResponseEntity<?> filterBills(@RequestParam(value="status") Boolean status);

    @GetMapping("/getFilteredBillsByDate")
    ResponseEntity<?> filterBillsByDate(@RequestParam(value="startDate") String startDate, @RequestParam(value="endDate") String endDate);

    @GetMapping("/getAll")
    ResponseEntity<?> findAll();

    @GetMapping("/delete/{id}")
    ResponseEntity<?> delete(@PathVariable("id") Long id);

}
