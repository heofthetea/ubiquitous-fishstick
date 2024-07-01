package stark.prm.project;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import stark.prm.project.data.Database;
import stark.prm.project.data.Homework;
import stark.prm.project.uiHelper.UiSideMenu;

public class HomeworkListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homework_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.hwl_drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        setupUI();
        createHWL();
        handleButton();
    }
    private void setupUI(){
        //Initial setup of SideBar-Navigation-Menu
        UiSideMenu uiSideMenu = new UiSideMenu(this,findViewById(R.id.hwl_drawer_layout));
        Toolbar toolbar = findViewById(R.id.hwl_toolbar);
        setSupportActionBar(toolbar);
        uiSideMenu.handleSideMenu(findViewById(R.id.nav_hwl_view),toolbar, getSupportActionBar());
    }
    private void createHWL() {
        LinearLayout parent = findViewById(R.id.hwl_lin_layout);
        LinearLayout layout;
        TextView text;
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(15,15,15,15);
        Map<UUID, Homework> homeworkMap = Database.getInstance().getNotes().values().stream()
                .filter(n -> n instanceof Homework).map(n -> (Homework) n).collect(Collectors.toMap(Homework::getId, n -> n));

        for(Map.Entry<UUID,Homework> entry: homeworkMap.entrySet()) {
            Homework hw = entry.getValue();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);

            layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(12,12,12,12);
            layout.setBackgroundResource(R.drawable.input_background);
            layout.setLayoutParams(params);

            text = new TextView(this);
            text.setText(hw.getLecture().getTopic()); //Title from DB
            text.setTextSize(24);
            layout.addView(text);

            text = new TextView(this);
            text.setText("Modul: " + hw.getModule().getName()); //ModuleName from DB
            text.setTextSize(16);
            layout.addView(text);

            text = new TextView(this);
            text.setText(dateFormat.format(hw.getDueDate()));
            text.setTextSize(16);
            layout.addView(text);

            parent.addView(layout);
        }
    }
    private void handleButton(){
        Button buttonAdd = findViewById(R.id.btn_add_new_homework);
        buttonAdd.setOnClickListener(view -> {
            this.startActivity(new Intent(this.getApplicationContext(), HomeworkActivity.class));

        });
    }
}