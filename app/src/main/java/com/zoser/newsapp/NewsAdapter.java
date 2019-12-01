package com.zoser.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<NewsClass> {
    public NewsAdapter(@NonNull Context context, int resource, @NonNull List<NewsClass> NewsList) {
        super(context, 0, NewsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater.from(getContext()).inflate(R.layout.my_list, parent);
        }

        TextView webTitle = convertView.findViewById(R.id.web_title);
        TextView section = convertView.findViewById(R.id.section);
        TextView type = convertView.findViewById(R.id.type);
        TextView timing = convertView.findViewById(R.id.timing);
        ImageView image = convertView.findViewById(R.id.image);

        NewsClass currentnews = getItem(position);
        webTitle.setText(currentnews.getWebTitle());
        section.setText(currentnews.getSection());
        type.setText(currentnews.getType());
        timing.setText(currentnews.getTiming());
        image.setImageBitmap(currentnews.getImage());

        return (convertView);
    }
}
