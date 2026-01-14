package com.daftar.pro.repository;

import com.daftar.pro.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance,Long> {

    List<Attendance> findByEmployeeIdAndDate(Long employeeId, LocalDate date);

    @Query("select a from Attendance a where MONTH(a.date) = :month AND YEAR(a.date) = :year")
    List<Attendance> findMonthlyReport(@Param("month") int month, @Param("year") int year);

}
