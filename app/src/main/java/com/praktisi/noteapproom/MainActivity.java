package com.praktisi.noteapproom;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
//import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import java.util.List;
import android.widget.LinearLayout;
import android.content.DialogInterface;
import android.view.LayoutInflater;


public class MainActivity extends AppCompatActivity {
    private EditText titleEditText, contentEditText, idEditText;
    private Button createButton, readButton, updateButton, deleteButton;
    private LinearLayout notesContainer;
    private List<Note> noteList;

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);
        idEditText = findViewById(R.id.idEditText);
        createButton = findViewById(R.id.createButton);
        notesContainer = findViewById(R.id.notesContainer);

        idEditText.setVisibility(View.GONE);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                String content = contentEditText.getText().toString();
                Note note = new Note();
                note.setTitle(title);
                note.setDescription(content);
                noteViewModel.insert(note);
                Toast.makeText(MainActivity.this, "Note created", Toast.LENGTH_SHORT).show();
                titleEditText.setText("");
                contentEditText.setText("");
                loadNotes();
            }
        });

        loadNotes();
    }

    private void loadNotes() {
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                notesContainer.removeAllViews();
                for (Note note : notes) {
                    View noteView = LayoutInflater.from(MainActivity.this).inflate(R.layout.note_item, notesContainer, false);
                    TextView noteTitleTextView = noteView.findViewById(R.id.titleTextView);
                    TextView noteContentTextView = noteView.findViewById(R.id.contentTextView);
                    noteTitleTextView.setText(note.getTitle());
                    noteContentTextView.setText(note.getDescription());

//                    noteView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            showOptionsDialog(note);
//                        }
//                    });

                    notesContainer.addView(noteView);
                }
            }
        });
    }
}