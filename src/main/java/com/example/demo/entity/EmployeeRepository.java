package com.example.demo.entity;
/*
*Create by User on 7/27/2021
*/

import com.example.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    //برای استفاده از خاصیت spring data  کافی است که از این اینترفیس ارث بری شود
}
