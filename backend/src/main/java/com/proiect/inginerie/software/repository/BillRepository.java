package com.proiect.inginerie.software.repository;

import com.proiect.inginerie.software.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
}
