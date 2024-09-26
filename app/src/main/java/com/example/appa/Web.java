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

public class Web extends Fragment {

    public TextInputEditText lastName;
    public TextInputEditText initials;
    public TextInputEditText publicationDate;
    public TextInputEditText title;
    public TextInputEditText consultationtionDate;
    public TextInputEditText url;

    public Web() {
        // Required empty public constructor
    }

    public static Web newInstance(String param1, String param2) {
        return new Web();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);

        lastName = view.findViewById(R.id.text_input_last_name);
        initials = view.findViewById(R.id.text_input_initials_name);
        publicationDate = view.findViewById(R.id.text_input_publication_date);
        title = view.findViewById(R.id.text_input_title);
        consultationtionDate = view.findViewById(R.id.text_input_consultation_date);
        url = view.findViewById(R.id.text_input_url);

        Button saveButton = view.findViewById(R.id.button_save_web);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWeb(v);
            }
        });

        return view;
    }

    public void saveWeb(View view){
        AdminSQLite adminSQLite = new AdminSQLite(getContext(), "db_course", null, '1');
        SQLiteDatabase db = adminSQLite.getWritableDatabase();

        String lastNameValue = String.valueOf(lastName.getText());
        String initialsValue = String.valueOf(initials.getText());
        String publicationDateValue = String.valueOf(publicationDate.getText());
        String titleValue = String.valueOf(title.getText());
        String consultationDateValue = String.valueOf(consultationtionDate.getText());
        String urlValue = String.valueOf(url.getText());

        if (lastNameValue.isEmpty() || titleValue.isEmpty() || urlValue.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put("author_lastname", lastNameValue);
        values.put("author_initials", initialsValue);
        values.put("publication_date", publicationDateValue);  // SQLite almacenará la fecha como TEXT
        values.put("title", titleValue);
        values.put("consultation_date", consultationDateValue);  // Fecha de consulta
        values.put("url", urlValue);

        long result = db.insert("webs", null, values);

        if (result != -1) {
            Toast.makeText(getContext(), "Web saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Error saving the web", Toast.LENGTH_SHORT).show();
        }

        db.close();

    }
}