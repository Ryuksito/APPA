package com.example.appa.screens;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appa.R;
import com.example.appa.activities.EditActivity;
import com.google.android.material.textfield.TextInputEditText;
import androidx.annotation.Nullable;


public class BookEdit extends Fragment {

    public TextInputEditText lastName;
    public TextInputEditText initials;
    public TextInputEditText publicationYear;
    public TextInputEditText title;
    public TextInputEditText edition;
    public TextInputEditText publicationPlace;
    public TextInputEditText editorial;

    public BookEdit() {
    }

    public static BookEdit newInstance() {
        return new BookEdit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_book_edit, container, false);

        lastName = view.findViewById(R.id.text_input_last_name);
        initials = view.findViewById(R.id.text_input_initials_name);
        publicationYear = view.findViewById(R.id.text_input_publication_year);
        title = view.findViewById(R.id.text_input_title);
        edition = view.findViewById(R.id.text_input_edition);
        publicationPlace = view.findViewById(R.id.text_input_publication_place);
        editorial = view.findViewById(R.id.text_input_editorial);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof EditActivity) {
            ((EditActivity) getActivity()).loadBook();
        }
    }

    public String getLastName(){
        return String.valueOf(lastName.getText());
    }
    public String getInitials(){
        return String.valueOf(initials.getText());
    }
    public String getPublicationYear(){
        return String.valueOf(publicationYear.getText());
    }
    public String getTitle(){
        return String.valueOf(title.getText());
    }
    public String getEdition(){
        return String.valueOf(edition.getText());
    }
    public String getPublicationPlace(){
        return String.valueOf(publicationPlace.getText());
    }
    public String getEditorial(){
        return String.valueOf(editorial.getText());
    }

    public void setLastName(String text){
        lastName.setText(text);
    }
    public void setInitials(String text){
        initials.setText(text);
    }
    public void setPublicationYear(String text){
        publicationYear.setText(text);
    }
    public void setTitle(String text){
        title.setText(text);
    }
    public void setEdition(String text){
        edition.setText(text);
    }
    public void setPublicationPlace(String text){
        publicationPlace.setText(text);
    }
    public void setEditorial(String text){
        editorial.setText(text);
    }
}