package com.daftar.pro.service;

import com.daftar.pro.dto.request.AttendanceRequest;
import com.daftar.pro.dto.response.AttendanceDto;

import java.time.LocalDate;
import java.util.List;


public interface AttendanceService {

    List<AttendanceDto> findAll();
    AttendanceDto findById(Long attendanceId);
    AttendanceDto create(AttendanceRequest request);
    AttendanceDto update(Long id, AttendanceRequest request);
    List<AttendanceDto> getByEmployeeIdAndDate(Long employeeId, LocalDate date);
    List<AttendanceDto> getMonthlyRepot(int month,int year);



 }
