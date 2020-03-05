package edu.ucsd.cse110.walkwalkrevolution;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProposeScreenActivity extends AppCompatActivity {

    Button schdWalk, wthdWalk, afterBtn;
    TextView screenTitle, walkName;
    View one, two;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propose_screen);

        screenTitle = findViewById(R.id.pscreen_title);
        walkName = findViewById(R.id.proute_name);

        schdWalk = findViewById(R.id.schedule_walk);
        wthdWalk = findViewById(R.id.withdraw_walk);
        afterBtn = findViewById(R.id.withdraw_walkafter);

        one = findViewById(R.id.buttons_layout);
        two = findViewById(R.id.buttons_after);

        schdWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screenTitle.setText("Scheduled Walk");
                one.setVisibility(View.GONE);
                two.setVisibility(View.VISIBLE);
            }
        });

        wthdWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screenTitle.setText("No Proposed Walk");
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                walkName.setText("");
            }
        });

        afterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screenTitle.setText("No Proposed Walk");
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                walkName.setText("");
            }
        });


    }


}
