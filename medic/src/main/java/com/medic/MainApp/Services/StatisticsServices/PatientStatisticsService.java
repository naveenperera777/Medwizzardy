package com.medic.MainApp.Services.StatisticsServices;

import com.medic.MainApp.DTO.AreaDiseaseDistributionDto;
import com.medic.MainApp.DTO.PatientsByAreaDto;
import com.medic.MainApp.DTO.PatientsByDiseaseDto;
import com.medic.MainApp.DTO.PatientsByGenderDto;
import com.medic.MainApp.DataMapper.StatisticsDataMapper.GenderDataMapper;

import java.util.List;

//@Component
public interface PatientStatisticsService {
    int getAllPatientCount();
    List<PatientsByGenderDto> getPatientCountByGender();
    List getDiseaseList();
    List<PatientsByDiseaseDto> getPatientCountByDisease();
    List<PatientsByAreaDto> getPatientCountByArea();
    List<AreaDiseaseDistributionDto> getDiseaseDistributionOfAnArea(String district);
    List<AreaDiseaseDistributionDto> getAreaDistributionOfADisease(String disease);

}
