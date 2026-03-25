package com.example.tripitinerary.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursions")
public class Excursion {

    @PrimaryKey(autoGenerate = true)
    private final int excursionID;
    private final String excursionTitle;
    private final int vacationID;
    private final String excursionDate;

    public Excursion(int excursionID, String excursionTitle, int vacationID, String excursionDate) {
        this.excursionID = excursionID;
        this.excursionTitle = excursionTitle;
        this.vacationID = vacationID;
        this.excursionDate = excursionDate;
    }

    public int getExcursionID() {
        return excursionID;
    }

    public String getExcursionTitle() {
        return excursionTitle;
    }

    public int getVacationID() {
        return vacationID;
    }

    public String getExcursionDate() {
        return excursionDate;
    }

}
