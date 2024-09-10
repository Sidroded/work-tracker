package com.sidroded.worktracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.sidroded.worktracker.MainActivity;
import com.sidroded.worktracker.R;
import com.sidroded.worktracker.adapters.TasksSpinnerAdapter;
import com.sidroded.worktracker.model.Task;
import com.sidroded.worktracker.service.TimerService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimerFragment extends Fragment {
    Toolbar toolbar;
    TextView dateTextView;
    Spinner tasksSpinner;
    TasksSpinnerAdapter tasksSpinnerAdapter;
    View fragmentView;
    MainActivity mainActivity;
    TextView mainTimerText;
    TextView lunchTimerText;
    AppCompatButton mainTimerButton;
    AppCompatButton lunchTimerButton;
    TimerService mainTimer;
    TimerService lunchTimer;

    public TimerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_timer, container, false);

        mainActivity = (MainActivity) getActivity();
        assert mainActivity != null;
        mainTimerText = fragmentView.findViewById(R.id.text_view_timer_timer_fragment);
        mainTimerButton = fragmentView.findViewById(R.id.button_start_timer_fragment);
        lunchTimerText = fragmentView.findViewById(R.id.text_view_lunch_timer_timer_fragment);
        lunchTimerButton = fragmentView.findViewById(R.id.button_lunch_timer_timer_fragment);

        setTasksSpinner();
        setToolbar();

        setTimerButtonListener();
        setLunchButtonListener();

        return fragmentView;
    }

    private void setLunchButtonListener() {
        lunchTimer = new TimerService(mainActivity, lunchTimerText);
        lunchTimerButton.setOnClickListener(view -> {
            if (!lunchTimer.isTimerStarted()) {
                lunchTimer.setTimerStarted(true);
                lunchTimerButton.setText(R.string.lunch_stop_button);

                mainTimer.getTimerTask().cancel();
                lunchTimer.startTimer();
            } else {
                lunchTimer.setTimerStarted(false);
                lunchTimerButton.setText(R.string.lunch_start_button);

                if (mainTimer.isTimerStarted()) {
                    mainTimer.startTimer();
                }

                lunchTimer.getTimerTask().cancel();
            }
        });
    }

    private void setTimerButtonListener() {
        mainTimer = new TimerService(mainActivity, mainTimerText);
        mainTimerButton.setOnClickListener(view -> {
            if (!mainTimer.isTimerStarted()) {
                mainTimer.setTimerStarted(true);
                setButtonUI(mainTimerButton, "Stop", R.drawable.selector_stop_button);

                mainTimer.startTimer();
            } else {
                mainTimer.setTimerStarted(false);
                setButtonUI(mainTimerButton, "Start", R.drawable.selector_start_button);

                mainTimer.getTimerTask().cancel();
            }
        });
    }

    private void setToolbar() {
        toolbar = mainActivity.getToolbar();
        dateTextView = toolbar.findViewById(R.id.dateTextView);
        updateDateAndDay();
    }

    private void updateDateAndDay() {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd EEEE", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);

        dateTextView.setText(formattedDate);
    }

    private void setTasksSpinner() {
        tasksSpinner = fragmentView.findViewById(R.id.spinner_tasks);
        tasksSpinnerAdapter = new TasksSpinnerAdapter(getActivity(), populateTaskList());
        tasksSpinner.setAdapter(tasksSpinnerAdapter);
    }

    private List<Task> populateTaskList() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Work"));

        return tasks;
    }

    private void setButtonUI(AppCompatButton button, String title, int color) {
        button.setText(title);
        button.setBackgroundResource(color);
    }
}