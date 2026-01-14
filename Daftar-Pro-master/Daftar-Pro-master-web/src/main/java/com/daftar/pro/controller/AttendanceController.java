package com.daftar.pro.controller;

import com.daftar.pro.dto.request.AttendanceRequest;
import com.daftar.pro.dto.response.AttendanceDto;
import com.daftar.pro.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<List<AttendanceDto>> findAll(){
        List list= attendanceService.findAll();
        if(list.size()<=0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(list));
    }

    @GetMapping("/{attendanceId}")
    public ResponseEntity<AttendanceDto> findById(@PathVariable Long attendanceId)throws Exception{
        AttendanceDto attendanceDto=attendanceService.findById(attendanceId);
        if(attendanceDto==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(attendanceDto));
    }

    @PostMapping
    public ResponseEntity<AttendanceDto> addAttendance(@RequestBody AttendanceRequest request){
        try{
            attendanceService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttendanceDto> updateAttendance(@PathVariable Long id,@RequestBody AttendanceRequest request){
        try{
            AttendanceDto attendanceDto=attendanceService.update(id,request);
            return ResponseEntity.ok().body(attendanceDto);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<AttendanceDto>> filterEmployeeIdAndDate(@RequestParam Long employeeId,@RequestParam LocalDate date){
        List<AttendanceDto> list=attendanceService.getByEmployeeIdAndDate(employeeId,date);
        if(list==null || employeeId==0 || date==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(list));
    }

    @GetMapping("/monthReport")
    public ResponseEntity<List<AttendanceDto>> monthReport(@RequestParam int month,@RequestParam int year){
        List<AttendanceDto> list=attendanceService.getMonthlyRepot(month,year);
        if(list==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(list));
    }

}
