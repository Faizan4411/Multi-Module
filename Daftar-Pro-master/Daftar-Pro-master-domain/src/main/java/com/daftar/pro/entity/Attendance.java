package com.daftar.pro.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name= "attendance")
public class Attendance extends BaseEntity {

    @Column(name= "employee_id")
    private Long employeeId;
    @Column(name = "attendance_date")
    private LocalDate date;
    @Column(name="check_in")
    private LocalTime checkIn;
    @Column(name="check_out")
    private LocalTime checkOut;
    @Column(name="hours_worked")
    private Double hoursWorked;
    private String status;
    @Column(name="overtime_hours")
    private Double overtimeHours;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalTime checkOut) {
        this.checkOut = checkOut;
    }

    public Double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(Double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(Double overtimeHours) {
        this.overtimeHours = overtimeHours;
    }
}
