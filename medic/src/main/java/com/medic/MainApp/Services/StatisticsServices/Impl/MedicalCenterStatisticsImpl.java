package com.medic.MainApp.Services.StatisticsServices.Impl;
import com.medic.MainApp.DAO.StatisticsDAO.MedicalCenterStatisticsDAO;
import com.medic.MainApp.DTO.ConsultantLeaderBoardDto;
import com.medic.MainApp.DTO.ConsultatantPricingSummaryDto;
import com.medic.MainApp.DTO.SessionPatientCountDto;
import com.medic.MainApp.DTO.TimeCountDto;
import com.medic.MainApp.Models.ConsultationModels.Pricing;
import com.medic.MainApp.Services.StatisticsServices.MedicalCenterStatisticsService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MedicalCenterStatisticsImpl implements MedicalCenterStatisticsService {
    private final MedicalCenterStatisticsDAO medicalCenterStatisticsDAO;

    public MedicalCenterStatisticsImpl(MedicalCenterStatisticsDAO medicalCenterStatisticsDAO) {
        this.medicalCenterStatisticsDAO = medicalCenterStatisticsDAO;
    }

    @Override
    public List<Pricing> getAllReceiptsOfAConsultant(String consultantId) {
        return medicalCenterStatisticsDAO.getAllReceiptsOfAConsultant(consultantId);
    }

    @Override
    public ConsultatantPricingSummaryDto getPricingSummaryOfAConsultantByDate(String consultantId, String from, String to) {
        List<Pricing> pricingList;
        if (from.equals("0")&& to.equals("0"))
            pricingList = medicalCenterStatisticsDAO.getAllTimePricingSummaryOfAConsultant(consultantId);
        else {
           pricingList = medicalCenterStatisticsDAO.getPricingSummaryOfAConsultantByDate(consultantId, from, to);
        }

      return calculateSummary(pricingList);
    }

    @Override
    public SessionPatientCountDto getTotalPatientsSessionsOfAConsultantByDate(String consultantId, String from, String to) {
        SessionPatientCountDto count;
        if (from.equals("0")&& to.equals("0")){
            count = medicalCenterStatisticsDAO.getAllTimeTotalPatientsSessionsOfAConsultant(consultantId);
        } else {
            count = medicalCenterStatisticsDAO.getTotalPatientsSessionsOfAConsultantByDate(consultantId, from, to);
        }
       return count;
    }

    @Override
    public SessionPatientCountDto getAllTimeTotalPatientsSessionsOfAllConsultantsByDate(String from, String to) {
        SessionPatientCountDto count;
        if (from.equals("0")&& to.equals("0")){
            count = medicalCenterStatisticsDAO.getAllTimeTotalPatientsSessionsOfAllConsultants();
        } else {
            count = medicalCenterStatisticsDAO.getTotalPatientsSessionsOfAllConsultantByDate(from,to);
        }
        return count;
    }

    @Override
    public ConsultatantPricingSummaryDto getPricingSummaryOfAllConsultantByDate(String from, String to) {
        List<Pricing> pricingList;
        if (from.equals("0")&& to.equals("0"))
            pricingList = medicalCenterStatisticsDAO.getAllTimePricingSummaryOfAllConsultant();
        else {
            pricingList = medicalCenterStatisticsDAO.getPricingSummaryOfAllConsultantByDate(from, to);
        }
        return calculateSummary(pricingList);

    }

    @Override
    public List<TimeCountDto> getSessionComparisonByDate(String type, String from, String to) {
       List<TimeCountDto> timeCountList =  medicalCenterStatisticsDAO.getSessionComparisonByDate(type,from,to);
       if (type.equals("year")){
           for (TimeCountDto item : timeCountList){
               String year = item.getTimestamp().split("-")[0];
               item.setTimestamp(year);
           }
           return timeCountList;
       }
       return timeCountList;
    }

    @Override
    public List<TimeCountDto> getRevenueComparisonByDate(String type, String from, String to) {
        List<TimeCountDto> revenueList = medicalCenterStatisticsDAO.getRevenueComparisonByDate(type,from,to);
        if (type.equals("year")){
            for (TimeCountDto item : revenueList){
                String year = item.getTimestamp().split("-")[0];
                item.setTimestamp(year);
            }
            return revenueList;
        }
        return revenueList;
    }

    @Override
    public List<ConsultantLeaderBoardDto> getConsultantLeaderboardByDate(String from, String to) {
        List<ConsultantLeaderBoardDto> leaderBoardList;
        int rank = 0;
        if (from.equals("0")&& to.equals("0")) {
            leaderBoardList = medicalCenterStatisticsDAO.getConsultantLeaderboard();
            for (ConsultantLeaderBoardDto list : leaderBoardList) {
                rank += 1;
                git .setRank(rank);
            }
        }
        else {
            leaderBoardList = medicalCenterStatisticsDAO.getConsultantLeaderboardByDate(from, to);
            for (ConsultantLeaderBoardDto list : leaderBoardList) {
                rank += 1;
                list.setRank(rank);
            }
        }
        return leaderBoardList;
    }

    @Override
    public int getTotalConsultants() {
        List<ConsultantLeaderBoardDto> leaderBoardList = medicalCenterStatisticsDAO.getConsultantLeaderboard();
        return  leaderBoardList.size();
    }

    public ConsultatantPricingSummaryDto calculateSummary(List<Pricing> pricingList){
        double consultationFees = 0.0;
        double medicationFees = 0.0;
        double miscellaneous= 0.0;
        double total= 0.0;
        for (Pricing item : pricingList){
            consultationFees += item.getConsultationFees();
            medicationFees += item.getMedicationFees();
            miscellaneous += item.getMiscellaneous();
        }
        total = consultationFees + medicationFees + miscellaneous;
        ConsultatantPricingSummaryDto dto = new ConsultatantPricingSummaryDto();
        dto.setTotalConsultationFees(consultationFees);
        dto.setTotalMedicationFees(medicationFees);
        dto.setTotalMiscellaneous(miscellaneous);
        dto.setTotal(total);
        return dto;
    }
}
