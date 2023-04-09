package com.example.shri;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Text_To_Braille extends AppCompatActivity {

    EditText editText;
    TextView textView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_braille);

        editText = findViewById(R.id.copy_edit_text);
        textView = findViewById(R.id.text_view);

        findViewById(R.id.generate_pdf_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generatePdf();
            }
        });
    }

    private void generatePdf() {
        String text = editText.getText().toString();
        String brailleCode = convertToBraille(text);

        try {
            // Create a new file in the Download directory
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "braille_code.pdf");
            OutputStream outputStream = new FileOutputStream(file);

            // Create a new PDF document and add the braille code
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.newPage();
            document.add(new Paragraph(brailleCode));
            document.close();


            textView.setText("Location:PDF file saved to " + file.getAbsolutePath()+"\nOutput: \n"+brailleCode);


        } catch (Exception e) {
            e.printStackTrace();
            textView.setText("Error: " + e.getMessage());
        }
    }

    private String convertToBraille(String text) {
        Map<Character, String> brailleMap = new HashMap<Character, String>() {{
            put('a', "⠁");
            put('b', "⠃");
            put('c', "⠉");
            put('d', "⠙");
            put('e', "⠑");
            put('f', "⠋");
            put('g', "⠛");
            put('h', "⠓");
            put('i', "⠊");
            put('j', "⠚");
            put('k', "⠅");
            put('l', "⠇");
            put('m', "⠍");
            put('n', "⠝");
            put('o', "⠕");
            put('p', "⠏");
            put('q', "⠟");
            put('r', "⠗");
            put('s', "⠎");
            put('t', "⠞");
            put('u', "⠥");
            put('v', "⠧");
            put('w', "⠺");
            put('x', "⠭");
            put('y', "⠽");
            put('z', "⠵");
            put(' ', " ");
        }};

        StringBuilder brailleChars = new StringBuilder();
        for (char c : text.toLowerCase().toCharArray()) {
            if (brailleMap.containsKey(c)) {
                brailleChars.append(brailleMap.get(c));
            }
        }

        return brailleChars.toString();
    }
}
