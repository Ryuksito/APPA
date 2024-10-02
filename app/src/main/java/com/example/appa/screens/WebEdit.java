package com.example.appa.screens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appa.R;
import com.example.appa.activities.EditActivity;
import com.google.android.material.textfield.TextInputEditText;

public class WebEdit extends Fragment {

    public TextInputEditText lastName;
    public TextInputEditText initials;
    public TextInputEditText publicationDate;
    public TextInputEditText title;
    public TextInputEditText consultationtionDate;
    public TextInputEditText url;

    public WebEdit() {
    }

    public static WebEdit newInstance() {
        return new WebEdit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_edit, container, false);

        lastName = view.findViewById(R.id.text_input_last_name);
        initials = view.findViewById(R.id.text_input_initials_name);
        publicationDate = view.findViewById(R.id.text_input_publication_date);
        title = view.findViewById(R.id.text_input_title);
        consultationtionDate = view.findViewById(R.id.text_input_consultation_date);
        url = view.findViewById(R.id.text_input_url);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof EditActivity) {
            ((EditActivity) getActivity()).loadWeb();
        }
    }

    public String getLastName(){
        return String.valueOf(lastName.getText());
    }
    public String getInitials(){
        return String.valueOf(initials.getText());
    }
    public String getPublicationDate(){
        return String.valueOf(publicationDate.getText());
    }
    public String getTitle(){
        return String.valueOf(title.getText());
    }
    public String getConsultationtionDate(){return String.valueOf(consultationtionDate.getText());}
    public String getUrl(){
        return String.valueOf(url.getText());
    }

    public void setLastName(String text){
        lastName.setText(text);
    }
    public void setInitials(String text){
        initials.setText(text);
    }
    public void setPublicationDate(String text){
        publicationDate.setText(text);
    }
    public void setTitle(String text){
        title.setText(text);
    }
    public void setConsultationtionDate(String text){
        consultationtionDate.setText(text);
    }
    public void setUrl(String text){
        url.setText(text);
    }
}