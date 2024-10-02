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

public class Books extends Fragment {

    public TextInputEditText lastName;
    public TextInputEditText initials;
    public TextInputEditText publicationYear;
    public TextInputEditText title;
    public TextInputEditText edition;
    public TextInputEditText publicationPlace;
    public TextInputEditText editorial;

    public Books() {
        // Required empty public constructor
    }

    public static Books newInstance() {
        return new Books();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);

        lastName = view.findViewById(R.id.text_input_last_name);
        initials = view.findViewById(R.id.text_input_initials_name);
        publicationYear = view.findViewById(R.id.text_input_publication_year);
        title = view.findViewById(R.id.text_input_title);
        edition = view.findViewById(R.id.text_input_edition);
        publicationPlace = view.findViewById(R.id.text_input_publication_place);
        editorial = view.findViewById(R.id.text_input_editorial);

        Button saveButton = view.findViewById(R.id.button_save_book);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {saveBook(v);}
        });

        return view;
    }

    public void saveBook(View view){
        AdminSQLite adminSQLite = new AdminSQLite(getContext(), "db_course", null, '1');
        SQLiteDatabase db = adminSQLite.getWritableDatabase();

        String lastNameValue = String.valueOf(lastName.getText());
        String initialsValue = String.valueOf(initials.getText());
        String publicationYearValue = String.valueOf(publicationYear.getText());
        String titleValue = String.valueOf(title.getText());
        String editionValue = String.valueOf(edition.getText());
        String publicationPlaceValue = String.valueOf(publicationPlace.getText());
        String editorialValue = String.valueOf(editorial.getText());

        if (lastNameValue.isEmpty() || titleValue.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put("author_lastname", lastNameValue);
        values.put("author_initials", initialsValue);
        values.put("year_of_publication", publicationYearValue);
        values.put("title", titleValue);
        values.put("edition", editionValue);
        values.put("place_of_publication", publicationPlaceValue);
        values.put("publisher", editorialValue);

        long result = db.insert("books", null, values);

        if (result != -1) {
            Toast.makeText(getContext(), "Book saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Error saving the Book", Toast.LENGTH_SHORT).show();
        }

        db.close();

        lastName.setText("");
        initials.setText("");
        publicationYear.setText("");
        title.setText("");
        edition.setText("");
        publicationPlace.setText("");
        editorial.setText("");

    }
}