package com.example.appa.activities;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.appa.AdminSQLite;
import com.example.appa.R;

import com.example.appa.ReferenceItem;

import com.example.appa.screens.BookEdit;
import com.example.appa.screens.JournalEdit;
import com.example.appa.screens.WebEdit;

import android.database.Cursor;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class EditActivity extends AppCompatActivity {

    public ReferenceItem item;
    private BookEdit bookEdit;
    private JournalEdit journalEdit;
    private WebEdit webEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        item = (ReferenceItem) getIntent().getSerializableExtra("reference_item");
        Toolbar toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Modify " + item.getTableName());

        Button cancelButton = findViewById(R.id.icon_button_cancel_edit);
        cancelButton.setOnClickListener(v -> {
            finish();
        });

        Button acceptButton = findViewById(R.id.icon_button_accept_edit);
        acceptButton.setOnClickListener(v -> {
            switch (item.getTableName()) {
                case "books":
                    modifyBook();
                    break;
                case "journals":
                    modifyJournal();
                    break;
                case "webs":
                    modifyWeb();
                    break;
                default:
                    finish();
                    break;
            }
        });

        bookEdit = new BookEdit();
        journalEdit = new JournalEdit();
        webEdit = new WebEdit();
        switch (item.getTableName()) {
            case "books":
                loadFragment(bookEdit);
                break;
            case "journals":
                loadFragment(journalEdit);
                break;
            case "webs":
                loadFragment(webEdit);
                break;
            default:
                finish();
                break;
        }

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.body_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void loadBook(){
        int id = item.getId();

        AdminSQLite adminSQLite = new AdminSQLite(this, "db_course", null, '1');
        SQLiteDatabase db = adminSQLite.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT author_lastname, author_initials, year_of_publication, title, edition, place_of_publication, publisher FROM books WHERE id = ?", new String[]{Integer.toString(id)});

        if (cursor != null && cursor.moveToFirst()) {
            String authorLastName = cursor.getString(0);
            String authorInitials = cursor.getString(1);
            String publicationYear = cursor.getInt(2) != 0 ? String.valueOf(cursor.getInt(2)) : ""; // Verificar si no es nulo
            String title = cursor.getString(3);
            String edition = cursor.getInt(4) != 0 ? String.valueOf(cursor.getInt(4)) : ""; // Verificar si no es nulo
            String publicationPlace = cursor.getString(5);
            String publisher = cursor.getString(6);

            bookEdit.setLastName(authorLastName);
            bookEdit.setInitials(authorInitials);
            bookEdit.setPublicationYear(publicationYear);
            bookEdit.setTitle(title);
            bookEdit.setEdition(edition);
            bookEdit.setPublicationPlace(publicationPlace);
            bookEdit.setEditorial(publisher);
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
    }
    public void loadJournal(){
        int id = item.getId();

        AdminSQLite adminSQLite = new AdminSQLite(this, "db_course", null, '1');
        SQLiteDatabase db = adminSQLite.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT author_lastname, author_initials, publication_date, title, volume, issue_number, pages FROM journals WHERE id = ?", new String[]{Integer.toString(id)});

        if (cursor != null && cursor.moveToFirst()) {
            String authorLastName = cursor.getString(0);
            String authorInitials = cursor.getString(1);
            String publicationDate = cursor.getString(2);
            String title = cursor.getString(3);
            String volume = cursor.getInt(4) != 0 ? String.valueOf(cursor.getInt(4)) : ""; // Verificar si no es nulo
            String issueNumber = cursor.getInt(5) != 0 ? String.valueOf(cursor.getInt(5)) : ""; // Verificar si no es nulo
            String pages = cursor.getString(6);

            journalEdit.setLastName(authorLastName);
            journalEdit.setInitials(authorInitials);
            journalEdit.setPublicationDate(publicationDate);
            journalEdit.setTitle(title);
            journalEdit.setVolume(volume);
            journalEdit.setIssueNumber(issueNumber);
            journalEdit.setPages(pages);
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
    }
    public void loadWeb(){
        int id = item.getId();

        AdminSQLite adminSQLite = new AdminSQLite(this, "db_course", null, '1');
        SQLiteDatabase db = adminSQLite.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT author_lastname, author_initials, publication_date, title, consultation_date, url FROM webs WHERE id = ?", new String[]{Integer.toString(id)});

        if (cursor != null && cursor.moveToFirst()) {
            String lastName = cursor.getString(0);
            String initials = cursor.getString(1);
            String pubDate = cursor.getString(2);
            String title = cursor.getString(3);
            String consultationDate = cursor.getString(4);
            String url = cursor.getString(5);

            webEdit.setLastName(lastName);
            webEdit.setInitials(initials);
            webEdit.setPublicationDate(pubDate);
            webEdit.setTitle(title);
            webEdit.setConsultationtionDate(consultationDate);
            webEdit.setUrl(url);
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
    }

    private void modifyBook(){
        int id = item.getId();
        String table = item.getTableName();
        AdminSQLite adminSQLite = new AdminSQLite(this, "db_course", null, '1');
        SQLiteDatabase db = adminSQLite.getWritableDatabase();

        String lastNameValue = bookEdit.getLastName();
        String initialsValue = bookEdit.getInitials();
        String publicationYearValue = bookEdit.getPublicationYear();
        String titleValue = bookEdit.getTitle();
        String editionValue = bookEdit.getEdition();
        String publicationPlaceValue = bookEdit.getPublicationPlace();
        String editorialValue = bookEdit.getEditorial();

        if (lastNameValue.isEmpty() || titleValue.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String sqlUpdate = "UPDATE " + table + " SET " +
                "author_lastname = ?, " +
                "author_initials = ?, " +
                "year_of_publication = ?, " +
                "title = ?, " +
                "edition = ?, " +
                "place_of_publication = ?, " +
                "publisher = ? " +
                "WHERE id = ?";

        db.execSQL(sqlUpdate, new Object[]{
                lastNameValue,
                initialsValue,
                publicationYearValue,
                titleValue,
                editionValue,
                publicationPlaceValue,
                editorialValue,
                id
        });

        db.close();

        Toast.makeText(this, "Book successfully modified", Toast.LENGTH_SHORT).show();
        finish();
    }
    private void modifyJournal(){
        int id = item.getId();
        String table = item.getTableName();
        AdminSQLite adminSQLite = new AdminSQLite(this, "db_course", null, '1');
        SQLiteDatabase db = adminSQLite.getWritableDatabase();

        String lastNameValue = journalEdit.getLastName();
        String initialsValue = journalEdit.getInitials();
        String publicationDateValue = journalEdit.getPublicationDate();
        String titleValue = journalEdit.getTitle();
        String volumeValue = journalEdit.getVolume();
        String issueNumberValue = journalEdit.getIssueNumber();
        String pagesValue = journalEdit.getPages();

        if(!isValidDate(publicationDateValue)){
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
            return;
        }

        if (lastNameValue.isEmpty() || titleValue.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String sqlUpdate = "UPDATE " + table + " SET " +
                "author_lastname = ?, " +
                "author_initials = ?, " +
                "publication_date = ?, " +
                "title = ?, " +
                "volume = ?, " +
                "issue_number = ?, " +
                "pages = ? " +
                "WHERE id = ?";

        db.execSQL(sqlUpdate, new Object[]{
                lastNameValue,
                initialsValue,
                publicationDateValue,
                titleValue,
                volumeValue != null && !volumeValue.isEmpty() ? Integer.parseInt(volumeValue) : null,
                issueNumberValue != null && !issueNumberValue.isEmpty() ? Integer.parseInt(issueNumberValue) : null,
                pagesValue,
                id
        });

        db.close();

        Toast.makeText(this, "Journal successfully modified", Toast.LENGTH_SHORT).show();
        finish();
    }
    private void modifyWeb(){
        int id = item.getId();
        String table = item.getTableName();
        AdminSQLite adminSQLite = new AdminSQLite(this, "db_course", null, '1');
        SQLiteDatabase db = adminSQLite.getWritableDatabase();

        String lastNameValue = webEdit.getLastName();
        String initialsValue = webEdit.getInitials();
        String publicationDateValue = webEdit.getPublicationDate();
        String titleValue = webEdit.getTitle();
        String consultationDateValue = webEdit.getConsultationtionDate();
        String urlValue = webEdit.getUrl();

        if(!isValidDate(publicationDateValue) || !isValidDate(consultationDateValue) ){
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
            return;
        }

        if (lastNameValue.isEmpty() || titleValue.isEmpty() || urlValue.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String sqlUpdate = "UPDATE " + table + " SET " +
                "author_lastname = ?, " +
                "author_initials = ?, " +
                "publication_date = ?, " +
                "title = ?, " +
                "consultation_date = ?, " +
                "url = ? " +
                "WHERE id = ?";

        db.execSQL(sqlUpdate, new Object[]{
                lastNameValue,
                initialsValue,
                publicationDateValue,
                titleValue,
                consultationDateValue,
                urlValue,
                id
        });

        db.close();

        Toast.makeText(this, "Web modified successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    public static boolean isValidDate(String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            Log.d("Date: ", date + e);
            return false;
        }
    }
}