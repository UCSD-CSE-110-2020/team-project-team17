package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class DummyActivity extends AppCompatActivity {

    //Purpose: Initialize FitnessService and Firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
        WalkWalkRevolution.setFitnessService(WalkWalkRevolution.getFitnessServiceFactory().createFitnessService(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        WalkWalkRevolution.getFitnessService().setup();
        if(WalkWalkRevolution.getHasPermissions()){
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            this.overridePendingTransition(0, 0);
            DummyActivity.this.finish();
        }
    }
}
