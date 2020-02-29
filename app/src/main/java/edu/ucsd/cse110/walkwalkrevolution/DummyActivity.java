package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessService;
import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessServiceFactory;

public class DummyActivity extends AppCompatActivity {

    private FitnessService fitnessService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
        fitnessService = FitnessServiceFactory.create(WalkWalkRevolution.currentKey, this);
        WalkWalkRevolution.setFitnessService(fitnessService);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fitnessService.setup();
        if(WalkWalkRevolution.getHasPermissions()){
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            this.overridePendingTransition(0, 0);
            DummyActivity.this.finish();
        }
    }
}
