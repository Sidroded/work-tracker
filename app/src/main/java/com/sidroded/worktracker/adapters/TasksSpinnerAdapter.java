package com.sidroded.worktracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sidroded.worktracker.R;
import com.sidroded.worktracker.model.Task;

import java.util.List;

public class TasksSpinnerAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<Task> taskList;

    public TasksSpinnerAdapter(Context context, List<Task> taskList) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.taskList = taskList;
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int i) {
        return taskList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.spinner_item_task, viewGroup, false);

        Task task = (Task) getItem(i);

        TextView title = (TextView) view.findViewById(R.id.spinner_item_title_task);
        title.setText(task.getTitle());

        return view;
    }
}
