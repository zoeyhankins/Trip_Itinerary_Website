package com.example.tripitinerary.UI;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripitinerary.R;
import com.example.tripitinerary.database.Repository;
import com.example.tripitinerary.entities.Excursion;
import com.example.tripitinerary.entities.Vacation;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ExcursionDetails extends AppCompatActivity {
    String title;
    int excursionID;
    int vacationID;
    EditText editTitle;
    Repository repository;
    Excursion currentExcursion;
    TextView editExcursionDate;
    DatePickerDialog.OnDateSetListener excursionDate;
    final Calendar myCalendarDate = Calendar.getInstance();

    String setDate;

    final Random rand = new Random();
    int numAlert = rand.nextInt(99999);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);

        repository = new Repository(getApplication());
        title = getIntent().getStringExtra("title");
        editTitle = findViewById(R.id.excursionTitle);
        editTitle.setText(title);
        excursionID = getIntent().getIntExtra("id", -1);
        vacationID = getIntent().getIntExtra("vacationID", -1);
        setDate = getIntent().getStringExtra("excursionDate");
        numAlert = rand.nextInt(99999);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (setDate != null) {
            try {
                Date excursionDate = sdf.parse(setDate);
                myCalendarDate.setTime(excursionDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        editExcursionDate = findViewById(R.id.excursionDate);

        editExcursionDate.setOnClickListener(view -> {
            String info = editExcursionDate.getText().toString();
            if (info.isEmpty()) info = setDate;
            try {
                myCalendarDate.setTime(sdf.parse(info));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new DatePickerDialog(ExcursionDetails.this, excursionDate, myCalendarDate.get(Calendar.YEAR), myCalendarDate.get(Calendar.MONTH), myCalendarDate.get(Calendar.DAY_OF_MONTH)).show();
        });

        excursionDate = (view, year, month, dayOfMonth) -> {
            myCalendarDate.set(Calendar.YEAR, year);
            myCalendarDate.set(Calendar.MONTH, month);
            myCalendarDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        MaterialButton saveButton = findViewById(R.id.buttonSaveExcursion);
        saveButton.setOnClickListener(v -> saveExcursion());
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editExcursionDate.setText(sdf.format(myCalendarDate.getTime()));
    }

    private void saveExcursion() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String excursionDateString = sdf.format(myCalendarDate.getTime());

        Vacation vacation = null;
        List<Vacation> vacations = repository.getmAllVacations();
        for (Vacation vac : vacations) {
            if (vac.getVacationId() == vacationID) {
                vacation = vac;
            }
        }

        try {
            Date excursionDate = sdf.parse(excursionDateString);
            Date startDate = sdf.parse(vacation.getStartDate());
            Date endDate = sdf.parse(vacation.getEndDate());

            if (excursionDate.before(startDate) || excursionDate.after(endDate)) {
                Toast.makeText(this, "The Excursion Date must be within the Vacation's Start and End dates", Toast.LENGTH_LONG).show();
            } else {
                Excursion excursion;
                if (excursionID == -1) {
                    if (repository.getmAllExcursions().isEmpty()) excursionID = 1;
                    else
                        excursionID = repository.getmAllExcursions().get(repository.getmAllExcursions().size() - 1).getExcursionID() + 1;
                    excursion = new Excursion(excursionID, editTitle.getText().toString(), vacationID, excursionDateString);
                    repository.insert(excursion);
                } else {
                    excursion = new Excursion(excursionID, editTitle.getText().toString(), vacationID, excursionDateString);
                    repository.update(excursion);
                }
                this.finish();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        if (item.getItemId() == R.id.excursionsave) {
            saveExcursion();
            return true;
        }

        if (item.getItemId() == R.id.excursiondelete) {
            for (Excursion excursion : repository.getmAllExcursions()) {
                if (excursion.getExcursionID() == excursionID) currentExcursion = excursion;
            }
            repository.delete(currentExcursion);
            Toast.makeText(ExcursionDetails.this, currentExcursion.getExcursionTitle() + " was deleted", Toast.LENGTH_LONG).show();
            ExcursionDetails.this.finish();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLabel();
    }
}