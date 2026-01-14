package com.daftar.pro.mapper;

import com.daftar.pro.dto.request.AttendanceRequest;
import com.daftar.pro.dto.response.AttendanceDto;
import com.daftar.pro.entity.Attendance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    Attendance toEntity(AttendanceRequest request);
    AttendanceDto toDto(Attendance attendance);
    List<AttendanceDto> toDtoList(List<Attendance> attendance);
}
