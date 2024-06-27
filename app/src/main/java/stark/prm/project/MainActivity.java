package stark.prm.project;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import stark.prm.project.data.Database;
import stark.prm.project.data.Lecture;
import stark.prm.project.data.Module;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homework);
        createSpinnerElements(Database.getInstance());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        handleSpinner();
        hanldeDatePicker();
    }


    public void createSpinnerElements(Database db){
        Spinner spinner = findViewById(R.id.spinner_lecture);
        List<String> arraySpinner = new ArrayList<>();

        HashMap<UUID, Lecture> lectureMap = db.getLectures();
        for (Lecture item : lectureMap.values()) {
            Module module = item.getModule();
            arraySpinner.add(module.getName());
        }
        /*arraySpinner.add("PRM");
        arraySpinner.add("Algorithmen");
        arraySpinner.add("Analysis");*/
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
}