package com.example.appa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import android.widget.Toast;


public class Journal extends Fragment {

    public TextInputEditText lastName;
    public TextInputEditText initials;
    public TextInputEditText publicationDate;
    public TextInputEditText title;
    public TextInputEditText volume;
    public TextInputEditText issueNumber;
    public TextInputEditText pages;

    public Journal() {
        // Required empty public constructor
    }

    public static Journal newInstance() {
        return new Journal();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_journal, container, false);

        lastName = view.findViewById(R.id.text_input_last_name);
        initials = view.findViewById(R.id.text_input_initials_name);
        publicationDate = view.findViewById(R.id.text_input_publication_date);
        title = view.findViewById(R.id.text_input_title);
        volume = view.findViewById(R.id.text_input_volume);
        issueNumber = view.findViewById(R.id.text_input_issue_number);
        pages = view.findViewById(R.id.text_input_pages);

        Button saveButton = view.findViewById(R.id.button_save_journal);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveJournal(v);
            }
        });

        return view;
    }

    public void saveJournal(View view){
        AdminSQLite adminSQLite = new AdminSQLite(getContext(), "db_course", null, '1');
        SQLiteDatabase db = adminSQLite.getWritableDatabase();

        String lastNameValue = String.valueOf(lastName.getText());
        String initialsValue = String.valueOf(initials.getText());
        String publicationDateValue = String.valueOf(publicationDate.getText());
        String titleValue = String.valueOf(title.getText());
        String volumeValue = String.valueOf(volume.getText());
        String issueNumberValue = String.valueOf(issueNumber.getText());
        String pagesValue = String.valueOf(pages.getText());

        if (lastNameValue.isEmpty() || titleValue.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put("author_lastname", lastNameValue);
        values.put("author_initials", initialsValue);
        values.put("publication_date", publicationDateValue);
        values.put("title", titleValue);
        values.put("volume", Integer.parseInt(volumeValue));
        values.put("issue_number", Integer.parseInt(issueNumberValue));
        values.put("pages", pagesValue);

        long result = db.insert("journals", null, values);

        if (result != -1) {
            Toast.makeText(getContext(), "Journal saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Error saving the Journal", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}