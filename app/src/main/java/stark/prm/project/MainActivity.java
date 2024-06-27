package stark.prm.project;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.appcompat.widget.Toolbar;


import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import stark.prm.project.data.Database;
import stark.prm.project.data.Lecture;
import stark.prm.project.data.Module;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homework);
        createSpinnerElements(Database.getInstance());
        TextView seekText = findViewById(R.id.seek_bar_progress_text);
        SeekBar seekbar = findViewById(R.id.seek_bar_progress);
        seekText.setText("Fortschritt:   " + seekbar.getProgress() + "/10");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.homework_drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        handleSpinner();
        hanldeDatePicker();
        handleSeekBar();
        handleButton();


        drawerLayout = findViewById(R.id.homework_drawer_layout);
        navigationView = findViewById(R.id.nav_homework_view);
        toolbar = findViewById(R.id.homework_toolbar);

        navigationView.bringToFront();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav_drawer, R.string.close_nav_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    finish();
                }
            }
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();
        if(itemID == R.id.nav_home) {
        }else if(itemID == R.id.nav_homework){
            startActivity(new Intent(MainActivity.this,homework.class));
        }else if(itemID == R.id.nav_notes) {
            startActivity(new Intent(MainActivity.this,notes.class));
        }else if(itemID ==R.id.nav_progress){
            startActivity(new Intent(MainActivity.this,progress.class));
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void createSpinnerElements(Database db){
        Spinner spinner = findViewById(R.id.spinner_lecture);
        List<String> arraySpinner = new ArrayList<>();

        HashMap<UUID, Lecture> lectureMap = db.getLectures();
        for (Lecture item : lectureMap.values()) {
            Module module = item.getModule();
            arraySpinner.add(module.getName());
        }
        arraySpinner.add("PRM");
        arraySpinner.add("Algorithmen");
        arraySpinner.add("Analysis");
        arraySpinner.add("+ Neue Vorlesung");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    private void hanldeDatePicker() {
        Locale.setDefault(Locale.GERMANY);
        Calendar cal = Calendar.getInstance();
        EditText editText = findViewById(R.id.edit_text_due_date);

        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH,month);
            cal.set(Calendar.DAY_OF_MONTH,day);
            updateLabel(editText, cal);
        };
        editText.setOnClickListener(view -> {
            DatePickerDialog datepicker = new DatePickerDialog(MainActivity.this,date,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
            datepicker.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
            datepicker.show();
        });
    }

    private void handleSpinner() {
        Spinner spinner = findViewById(R.id.spinner_lecture);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                handleSpinnerElement(spinner.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                handleSpinnerElement(spinner.getSelectedItem().toString());
            }
        });
    }
    private void handleSeekBar(){
        SeekBar seekbar = findViewById(R.id.seek_bar_progress);
        TextView seekText = findViewById(R.id.seek_bar_progress_text);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int progress = seekbar.getProgress();
                String text = "Fortschritt: ";
                if(progress < 10) text += "  ";
                text += progress + "/10";
                seekText.setText(text);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void handleButton(){
        Button buttonAdd = findViewById(R.id.btn_add_homework);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addHomework();
            }
        });
    }

    private void handleSpinnerElement(String currentElement){
        EditText text = findViewById(R.id.edit_text_new_lecture);
        if(currentElement.equals("+ Neue Vorlesung") && text.getVisibility() == View.GONE){
            text.setVisibility(View.VISIBLE); //Set EditText for new Lecture to visible
        } else if(!currentElement.equals("+ Neue Vorlesung") && text.getVisibility() == View.VISIBLE) {
            text.setVisibility(View.GONE); //Set EditText for new Lecture to be invisible
        }
    }


    private void updateLabel(EditText editText ,Calendar cal){
        String myFormat="dd.MM.yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.GERMANY);
        editText.setText(dateFormat.format(cal.getTime()));
    }


    private void addHomework() {
        Spinner spinner = findViewById(R.id.spinner_lecture);
        EditText newLecture = findViewById(R.id.edit_text_new_lecture);
        EditText title = findViewById(R.id.edit_text_title);
        EditText dueDate = findViewById(R.id.edit_text_due_date);
        EditText pageNum = findViewById(R.id.edit_text_number);
        EditText description = findViewById(R.id.edit_text_text_description);
        Database db = Database.getInstance();

        if(spinner.getSelectedItem().toString().equals("+ Neue Vorlesung")) {
            Toast.makeText(this, "Neue Vorlesung wird erstellt", Toast.LENGTH_SHORT).show();
        }
    }


}