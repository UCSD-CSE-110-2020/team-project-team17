package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

public class DateAndTimeActivity extends AppCompatActivity {
    private static final String TAG = "DateAndTimeActivity";

    public static final String YEAR = "YEAR", MONTH = "MONTH", DAY_OF_MONTH = "DAY_OF_MONTH",
                               HOUR = "HOUR", MINUTE = "MINUTE";

    static int year, dayOfMonth, month, hour, minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_and_time);

        Button btnDate = findViewById(R.id.btn_date);
        Button btnTime = findViewById(R.id.btn_time);
        Button btnDone = findViewById(R.id.btn_done);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDickerDialog(view);
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view);
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(YEAR, year);
                bundle.putInt(MONTH, month);
                bundle.putInt(DAY_OF_MONTH, dayOfMonth);
                bundle.putInt(HOUR, hour);
                bundle.putInt(MINUTE, minute);

                setResult(RESULT_OK, getIntent().putExtras(bundle));

                Log.d(TAG, "onClick: " + year + " " + dayOfMonth + " " + month + " " + hour + " " + minute);
                finish();
            }
        });
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, dayOfMonth);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            DateAndTimeActivity.year = year;
            DateAndTimeActivity.month = month;
            DateAndTimeActivity.dayOfMonth = dayOfMonth;
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            DateAndTimeActivity.hour = hourOfDay;
            DateAndTimeActivity.minute = minute;
        }
    }

    public void showDateDickerDialog(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
}
