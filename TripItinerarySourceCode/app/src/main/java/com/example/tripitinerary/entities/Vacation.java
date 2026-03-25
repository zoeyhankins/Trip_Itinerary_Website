package com.example.tripitinerary.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vacations")
public class Vacation {

    @PrimaryKey(autoGenerate = true)
    public final int vacationId;
    private final String vacationTitle;
    private final String vacationHotel;
    private final String startDate;
    private final String endDate;

    public Vacation(int vacationId, String vacationTitle, String vacationHotel, String startDate, String endDate) {
        this.vacationId = vacationId;
        this.vacationTitle = vacationTitle;
        this.vacationHotel = vacationHotel;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String toString() {
        return vacationTitle;
    }

    public int getVacationId() {
        return vacationId;
    }

    public String getVacationTitle() {
        return vacationTitle;
    }

    public String getVacationHotel() {
        return vacationHotel;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getTitle() {
        return getVacationTitle();
    }

    public String getHotel() {
        return getVacationHotel();
    }
}
