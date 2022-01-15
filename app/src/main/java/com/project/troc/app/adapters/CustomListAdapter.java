package com.project.troc.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.project.troc.R;
import com.project.troc.app.activities.MainActivity;
import com.project.troc.app.models.Annonce;


import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<Annonce> implements View.OnClickListener{

    private ArrayList<Annonce> dataSet;
    private Context mainContext;
    private MainActivity current;

    private TextView annoncetitre;
    private TextView annonceCode;
    private ImageButton buttonEdit;
    private ImageButton buttonDelete;

    public CustomListAdapter(ArrayList<Annonce> data, Context mainContext, MainActivity current) {
        super(mainContext, R.layout.list_item_layout, data);
        this.dataSet = data;
        this.mainContext = mainContext;
        this.current = current;
    }

    @Override
    public void onClick(View v) { }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Annonce annonce = getItem(position);

        View result;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.list_item_layout, parent, false);
        annoncetitre = (TextView) convertView.findViewById(R.id.studentNameListItem);
        annonceCode = (TextView) convertView.findViewById(R.id.studentCodeAndEmailListItem);
        buttonEdit = (ImageButton) convertView.findViewById(R.id.buttonEditStudent);
        buttonDelete = (ImageButton) convertView.findViewById(R.id.buttonDeleteStudent);
        
        result = convertView;

        Animation animation = AnimationUtils.loadAnimation(mainContext, R.anim.slide_animation);
        result.startAnimation(animation);

        if (annonce.getName().length() <= 24)
            annoncetitre.setText(annonce.getName());
        else
            annoncetitre.setText(annonce.getName().substring(0, 22) + "...");

        annonceCode.setText(annonce.getCode() + "\n\n" +annonce.getEmail());
        buttonEdit.setTag(position);
        buttonDelete.setTag(position);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer)v.getTag();
                Object object = getItem(position);
                Annonce student = (Annonce) object;
                current.setCurrentView(R.layout.update_layout);
                current.setContentView(current.getCurrentView());

            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer)v.getTag();
                Object object = getItem(position);
                Annonce student = (Annonce) object;
                current.showDeleteDialog(student);
            }
        });

        return convertView;
    }
}