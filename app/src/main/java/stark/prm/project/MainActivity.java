package stark.prm.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import stark.prm.project.data.Database;
import stark.prm.project.data.Module;
import stark.prm.project.uiHelper.UiSideMenu;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Database.getInstance().init(this); // populate the Database with some Modules

        //TODO code restart of App when run in Error
        //Temporarily call the HomeworkActivity directly on Startup
        try {
            //this.startActivity(new Intent(MainActivity.this, HomeworkActivity.class));

            //Initial setup of SideBar-Navigation-Menu
            UiSideMenu uiSideMenu = new UiSideMenu(this, findViewById(R.id.main));
            Toolbar toolbar = findViewById(R.id.main_toolbar);
            setSupportActionBar(toolbar);
            uiSideMenu.handleSideMenu(findViewById(R.id.nav_main_view), toolbar, getSupportActionBar());

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            //restart();
        }

        buttonClick();
    }


    private void buttonClick() {
        Button buttonHomework = findViewById(R.id.btn_Homework);
        buttonHomework.setClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, HomeworkActivity.class);
        });

        Button buttonNotes = findViewById(R.id.btn_Notes);
        buttonHomework.setClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NoteActivity.class);
        });

        Button buttonProgress = findViewById(R.id.btn_progress);
        buttonHomework.setClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ProgressActivity.class);
        });


    }

}