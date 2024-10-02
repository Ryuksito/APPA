package com.example.appa;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView;
import com.google.android.material.textfield.TextInputEditText;
import android.text.TextWatcher;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



public class Search extends Fragment {

    Spinner spinner;
    private List<String> apaReferences;
    private List<ReferenceItem> referenceItems;
    private RecyclerView recyclerView;
    private ReferenceAdapter referenceAdapter;
    private List<String> referenceList;

    private String searchOption;
    private TextInputEditText searchInput;

    public Search() {
        // Required empty public constructor
    }

    public static Search newInstance() {
        return new Search();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apaReferences = new ArrayList<>();
        initializeApaTemplates();
    }

    @Override
    public void onResume() {
        super.onResume();
        dbSearch(String.valueOf(searchInput.getText()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_references);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        referenceList = new ArrayList<>();
        referenceItems = new ArrayList<>();
        referenceAdapter = new ReferenceAdapter(referenceList, referenceItems);
        recyclerView.setAdapter(referenceAdapter);

        spinner = view.findViewById(R.id.spinner_search_options);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.type_array,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchOption = parent.getItemAtPosition(position).toString();
                dbSearch(String.valueOf(searchInput.getText()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        searchInput = view.findViewById(R.id.text_input_search);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Se llama antes de que el texto cambie
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dbSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
        return view;
    }

    @SuppressLint({"Range", "NotifyDataSetChanged"})
    public void dbSearch(String value){
        referenceItems.clear();

        if (value.isEmpty()) {
            referenceList.clear();
            referenceAdapter.notifyDataSetChanged();
            return;
        }

        AdminSQLite adminSQLite = new AdminSQLite(getContext(), "db_course", null, '1');
        SQLiteDatabase db = adminSQLite.getReadableDatabase();

        String query = null;
        switch (searchOption) {
            case "books":
                query = "SELECT * FROM books WHERE title LIKE ?";
                break;
            case "journals":
                query = "SELECT * FROM journals WHERE title LIKE ?";
                break;
            case "webs":
                query = "SELECT * FROM webs WHERE title LIKE ?";
                break;
            default:
                return; // Maneja el caso por defecto si es necesario
        }

        // Ejecuta la consulta y maneja el resultado
        Cursor cursor = db.rawQuery(query, new String[]{"%" + value + "%"});

        List<String> references = new ArrayList<>();

        while (cursor.moveToNext()) {
            String reference = "";
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            switch (searchOption) {
                case "books":
                    referenceItems.add(new ReferenceItem(id, "books"));
                    String authorInitials = cursor.getString(cursor.getColumnIndex("author_initials"));
                    String authorLastname = cursor.getString(cursor.getColumnIndex("author_lastname"));
                    String yearOfPublication = cursor.getString(cursor.getColumnIndex("year_of_publication"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String edition = cursor.getString(cursor.getColumnIndex("edition"));
                    String placeOfPublication = cursor.getString(cursor.getColumnIndex("place_of_publication"));
                    String publisher = cursor.getString(cursor.getColumnIndex("publisher"));

                    reference = apaReferences.get(0) // Plantilla para libros
                            .replace("{author_initials}", authorInitials)
                            .replace("{author_lastname}", authorLastname)
                            .replace("{year_of_publication}", yearOfPublication)
                            .replace("{title}", title)
                            .replace("{edition}", edition)
                            .replace("{place_of_publication}", placeOfPublication)
                            .replace("{publisher}", publisher);
                    break;

                case "journals":
                    referenceItems.add(new ReferenceItem(id, "journals"));
                    authorInitials = cursor.getString(cursor.getColumnIndex("author_initials"));
                    authorLastname = cursor.getString(cursor.getColumnIndex("author_lastname"));
                    String publicationDate = cursor.getString(cursor.getColumnIndex("publication_date"));
                    String volume = cursor.getString(cursor.getColumnIndex("volume"));
                    String issueNumber = cursor.getString(cursor.getColumnIndex("issue_number"));
                    String pages = cursor.getString(cursor.getColumnIndex("pages"));
                    title = cursor.getString(cursor.getColumnIndex("title"));

                    reference = apaReferences.get(1) // Plantilla para journals
                            .replace("{author_initials}", authorInitials)
                            .replace("{author_lastname}", authorLastname)
                            .replace("{publication_date}", Objects.requireNonNull(convertDate(publicationDate)))
                            .replace("{title}", title)
                            .replace("{volume}", volume)
                            .replace("{issue_number}", issueNumber)
                            .replace("{pages}", pages);
                    break;

                case "webs":
                    referenceItems.add(new ReferenceItem(id, "webs"));
                    authorInitials = cursor.getString(cursor.getColumnIndex("author_initials"));
                    authorLastname = cursor.getString(cursor.getColumnIndex("author_lastname"));
                    publicationDate = cursor.getString(cursor.getColumnIndex("publication_date"));
                    String consultationDate = cursor.getString(cursor.getColumnIndex("consultation_date"));
                    String url = cursor.getString(cursor.getColumnIndex("url"));
                    title = cursor.getString(cursor.getColumnIndex("title"));

                    reference = apaReferences.get(2) // Plantilla para webs
                            .replace("{author_initials}", authorInitials)
                            .replace("{author_lastname}", authorLastname)
                            .replace("{publication_date}", Objects.requireNonNull(convertDate(publicationDate)))
                            .replace("{title}", title)
                            .replace("{consultation_date}", Objects.requireNonNull(convertDateToWords(consultationDate)))
                            .replace("{url}", url);
                    break;
            }
            references.add(reference); // Agrega la referencia a la lista
        }

        for (String reference : references) {
            Log.d("SearchFragment", reference);
        }

        referenceList.clear();
        referenceList.addAll(references);
        referenceAdapter.notifyDataSetChanged();

        cursor.close();
        db.close();
    }

    private void initializeApaTemplates() {
        String bookTemplate = "{author_initials}. {author_lastname} ({year_of_publication}). {title}, ({edition}da ed.), {place_of_publication}, {publisher}.";
        String journalTemplate = "{author_initials}. {author_lastname} ({publication_date}). {title}, vol. {volume}, No. {issue_number}, pp. {pages}.";
        String webTemplate = "{author_initials}. {author_lastname}. ({publication_date}). {title}. [{consultation_date}]: {url}";

        apaReferences.add(bookTemplate);
        apaReferences.add(journalTemplate);
        apaReferences.add(webTemplate);
    }

    public static String convertDate(String date) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy, MMMM", Locale.ENGLISH);

        try {
            Date parsedDate = inputFormat.parse(date);
            return outputFormat.format(parsedDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String convertDateToWords(String date) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM, dd, yyyy", new Locale("es", "ES"));

        try {
            Date parsedDate = inputFormat.parse(date);
            return outputFormat.format(parsedDate);
        } catch (ParseException e) {
            return null;
        }
    }

}