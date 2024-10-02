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

public class JournalEdit extends Fragment {

    public TextInputEditText lastName;
    public TextInputEditText initials;
    public TextInputEditText publicationDate;
    public TextInputEditText title;
    public TextInputEditText volume;
    public TextInputEditText issueNumber;
    public TextInputEditText pages;

    public JournalEdit() {
    }

    public static JournalEdit newInstance() {

        return new JournalEdit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_journal_edit, container, false);

        lastName = view.findViewById(R.id.text_input_last_name);
        initials = view.findViewById(R.id.text_input_initials_name);
        publicationDate = view.findViewById(R.id.text_input_publication_date);
        title = view.findViewById(R.id.text_input_title);
        volume = view.findViewById(R.id.text_input_volume);
        issueNumber = view.findViewById(R.id.text_input_issue_number);
        pages = view.findViewById(R.id.text_input_pages);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof EditActivity) {
            ((EditActivity) getActivity()).loadJournal();
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
    public String getVolume(){
        return String.valueOf(volume.getText());
    }
    public String getIssueNumber(){
        return String.valueOf(issueNumber.getText());
    }
    public String getPages(){
        return String.valueOf(pages.getText());
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
    public void setVolume(String text){
        volume.setText(text);
    }
    public void setIssueNumber(String text){
        issueNumber.setText(text);
    }
    public void setPages(String text){
        pages.setText(text);
    }
}