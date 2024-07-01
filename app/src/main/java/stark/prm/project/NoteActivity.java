package stark.prm.project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import stark.prm.project.data.Database;
import stark.prm.project.data.Lecture;
import stark.prm.project.data.Module;
import stark.prm.project.data.Note;
import stark.prm.project.uiHelper.UiCheckError;
import stark.prm.project.uiHelper.UiSideMenu;
import stark.prm.project.uiHelper.UiSpinner;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.note_drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setUpUiElements();
        handleButton();
    }

    private void setUpUiElements(){
        //Initial setup of Spinner Elements
        Spinner modules = findViewById(R.id.note_spinner_module);
        modules.setAdapter(new UiSpinner(this).createSpinnerElements());

        //Initial setup of SideBar-Navigation-Menu
        UiSideMenu uiSideMenu = new UiSideMenu(this,findViewById(R.id.note_drawer_layout));
        Toolbar toolbar = findViewById(R.id.note_toolbar);
        setSupportActionBar(toolbar);
        uiSideMenu.handleSideMenu(findViewById(R.id.nav_notes_view),toolbar, getSupportActionBar());
    }

    private void handleButton(){
        Button buttonAdd = findViewById(R.id.btn_add_note);
        buttonAdd.setOnClickListener(view -> addNote());
    }

    private void addNote() {
        Spinner spinner =  findViewById(R.id.note_spinner_module);
        EditText editLecture = findViewById(R.id.note_edit_text_lecture);
        String topic = editLecture.getText().toString();
        EditText editDesc = findViewById(R.id.note_edit_text_description);
        Database db = Database.getInstance();

        if(UiCheckError.checkNoteElmenents(editLecture,editDesc)) return;

        //Get Object of the Module selected by the user
        Module module = db.getModuleByName(spinner.getSelectedItem().toString());
        if(module == null) throw new RuntimeException("selected Module doesn't exist");

        //Get Object for the Lecture if existent, otherwise create a new Lecture object
        Lecture lecture = db.getLectureByTopic(topic);
        if(lecture == null) {
            lecture = new Lecture(module, topic);
            db.add(lecture);
        }

        db.add(new Note(
                editDesc.getText().toString(),
                lecture
        ));
    }
}