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
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateAndTimeActivity extends AppCompatActivity {
    private static final String TAG = "DateAndTimeActivity";

    public static final String YEAR = "YEAR", MONTH = "MONTH", DAY_OF_MONTH = "DAY_OF_MONTH",
                               HOUR = "HOUR", MINUTE = "MINUTE";

    static int year, dayOfMonth, month, hour, minute;

    private static TextView dateView;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
    private static final Calendar cal = Calendar.getInstance();
    private static Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_and_time);

        Button btnDate = findViewById(R.id.btn_date);
        Button btnTime = findViewById(R.id.btn_time);
        Button btnDone = findViewById(R.id.btn_done);

        dateView = findViewById(R.id.text_date);
        dateView.setText(formatter.format(cal.getTime()));

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
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, dayOfMonth);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            DateAndTimeActivity.year = year;
            DateAndTimeActivity.month = month;
            DateAndTimeActivity.dayOfMonth = dayOfMonth;

            cal.set(cal.YEAR, year);
            cal.set(cal.MONTH, month);
            cal.set(cal.DATE, dayOfMonth);

            bundle.putInt(YEAR, year);
            bundle.putInt(MONTH, month);
            bundle.putInt(DAY_OF_MONTH, dayOfMonth);

            dateView.setText(formatter.format(cal.getTime()));
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            DateAndTimeActivity.hour = hourOfDay;
            DateAndTimeActivity.minute = minute;

            cal.set(cal.HOUR_OF_DAY, hour);
            cal.set(cal.MINUTE, minute);

            bundle.putInt(HOUR, hour);
            bundle.putInt(MINUTE, minute);

            dateView.setText(formatter.format(cal.getTime()));
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
