package com.daftar.pro.service.impl;

import com.daftar.pro.dto.request.AttendanceRequest;
import com.daftar.pro.dto.response.AttendanceDto;
import com.daftar.pro.entity.Attendance;
import com.daftar.pro.mapper.AttendanceMapper;
import com.daftar.pro.repository.AttendanceRepository;
import com.daftar.pro.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;

    private final LocalTime OFFICE_START = LocalTime.of(8, 0);
    private final LocalTime OFFICE_END = LocalTime.of(15, 0);
    private final double STANDARD_HOURS = 7.0;

    @Override// sub attendance show kre ga yeh
    public List<AttendanceDto> findAll() {
        return attendanceMapper.toDtoList(attendanceRepository.findAll());
    }

    @Override// yeh ek attendance ly aae ga
    public AttendanceDto findById(Long attendanceId){
            return attendanceMapper.toDto(attendanceRepository.getById(attendanceId));
    }

    @Override// yeh add kre ga
    public AttendanceDto create(AttendanceRequest request) {

        LocalTime checkIn=request.getCheckIn();
        LocalTime checkOut=request.getCheckOut();

        request.setHoursWorked(calculateHoursWorked(checkIn,checkOut));
        request.setStatus(calculateStatus(checkIn, checkOut));
        request.setOvertimeHours(calculateOvertime(request.getHoursWorked()));

        Attendance attendance= attendanceMapper.toEntity(request);
        return attendanceMapper.toDto(attendanceRepository.save(attendance));
    }

    @Override // yeh update kre ga
    public AttendanceDto update(Long id, AttendanceRequest request) {

        Attendance attendance= attendanceRepository.findById(id).orElseThrow();
        attendance.setDate(request.getDate());
        attendance.setCheckIn(request.getCheckIn());
        attendance.setCheckOut(request.getCheckOut());
        attendance.setHoursWorked(request.getHoursWorked());
        attendance.setStatus(request.getStatus());
        attendance.setOvertimeHours(request.getOvertimeHours());

        return attendanceMapper.toDto(attendanceRepository.save(attendance));
    }

    @Override
    public List<AttendanceDto> getByEmployeeIdAndDate(Long employeeId, LocalDate date) {
        return attendanceMapper.toDtoList(attendanceRepository.findByEmployeeIdAndDate(employeeId,date));
    }

    @Override
    public List<AttendanceDto> getMonthlyRepot(int month, int year) {
        return attendanceMapper.toDtoList(attendanceRepository.findMonthlyReport(month,year));
    }

    private Double calculateHoursWorked(LocalTime checkIn, LocalTime checkOut) {
        if (checkIn == null || checkOut == null) return 0.0;
        Duration duration = Duration.between(checkIn, checkOut);
        return duration.toMinutes() / 60.0;
    }

    private String calculateStatus(LocalTime checkIn, LocalTime checkOut) {
        if (checkIn == null || checkOut == null) return "absent";
        boolean late = checkIn.isAfter(OFFICE_START);
        boolean early = checkOut.isBefore(OFFICE_END);

        if (late && early) return "half-day";
        if (late) return "late";
        if (early) return "early-exit";

        return "present";
    }

    private Double calculateOvertime(Double hoursWorked) {
        if (hoursWorked == null) return 0.0;
        double overtime = hoursWorked - STANDARD_HOURS;
        return overtime > 0 ? overtime : 0.0;
    }




}
