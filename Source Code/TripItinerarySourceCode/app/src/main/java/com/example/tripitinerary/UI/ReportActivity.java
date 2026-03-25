package com.example.tripitinerary.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripitinerary.database.Repository;
import com.example.tripitinerary.entities.Vacation;
import com.example.tripitinerary.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    private ReportAdapter adapter;
    private List<Vacation> allVacations = new ArrayList<>();
    private final List<Vacation> filteredVacations = new ArrayList<>();

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        EditText searchInput = findViewById(R.id.searchField);
        TextView reportTimestamp = findViewById(R.id.reportTimestamp);
        RecyclerView reportRecycler = findViewById(R.id.reportRecyclerView);
        reportRecycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ReportAdapter(this, filteredVacations);
        reportRecycler.setAdapter(adapter);

        Repository repo = new Repository(getApplication());
        allVacations = repo.getmAllVacations();

        filteredVacations.addAll(allVacations);
        adapter.notifyDataSetChanged();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy h:mm a");
        reportTimestamp.setText("Generated: " + now.format(formatter));

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterVacations(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void filterVacations(String query) {
        filteredVacations.clear();
        for (Vacation v : allVacations) {
            if (v.getTitle().toLowerCase().contains(query.toLowerCase()) || v.getHotel().toLowerCase().contains(query.toLowerCase())) {
                filteredVacations.add(v);
            }
        }
        adapter.notifyDataSetChanged();
    }
}