package stark.prm.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import stark.prm.project.data.Database;
import stark.prm.project.data.Module;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.landing_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Database.getInstance().init(this); // populate the Database with some Modules
        Database.getInstance().add(
                new Module("PRM", "Wolfgang Stark")
        );
        buttonClick();
    }

    private void buttonClick() {
        Button buttonHomework = findViewById(R.id.btn_landing_homework);
        buttonHomework.setOnClickListener(view -> {
            this.startActivity(new Intent(MainActivity.this, HomeworkActivity.class)); // muss im Merge auf HomeworkListActivity angepasst werden
        });

        Button buttonNotes = findViewById(R.id.btn_landing_notes);
        buttonNotes.setOnClickListener(view -> {
            this.startActivity(new Intent(MainActivity.this, NoteActivity.class));
        });

        Button buttonProgress = findViewById(R.id.btn_landing_progress);
        buttonProgress.setOnClickListener(view -> {
            this.startActivity(new Intent(MainActivity.this, ProgressActivity.class));
        });
    }
}