package com.sidroded.worktracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.sidroded.worktracker.MainActivity;
import com.sidroded.worktracker.R;
import com.sidroded.worktracker.adapters.TasksSpinnerAdapter;
import com.sidroded.worktracker.model.Task;

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

        setTasksSpinner();
        setToolbar();

        return fragmentView;
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
        tasksSpinner = (Spinner) fragmentView.findViewById(R.id.spinner_tasks);
        tasksSpinnerAdapter = new TasksSpinnerAdapter(getActivity(), populateTaskList());
        tasksSpinner.setAdapter(tasksSpinnerAdapter);
    }

    private List<Task> populateTaskList() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Work"));

        return tasks;
    }
}