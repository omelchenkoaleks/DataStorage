package com.example.om_ko.datastorage;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CacheActivity extends AppCompatActivity {

    private static final String CACHE_FILE = "komenty.txt";
    private static final String TAG_CACHE = "TAG_CACHE";
    Context context;
    EditText etComments;
    String commentsWriteText;
    File file;
    File cacheFileDir;
    FileOutputStream fileOutputStream;
    FileInputStream fileInputStream;
    OutputStreamWriter outputStreamWriter;
    BufferedWriter bufferedWriter;
    InputStreamReader inputStreamReader;
    BufferedReader bufferedReader;
    StringBuilder stringBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);

        etComments = findViewById(R.id.et_comments);
    }

    // ЗАПИСЬ В КЭШ ФАЙЛ
    public void writeToCache(View view) {
        commentsWriteText = etComments.getText().toString();
        if (commentsWriteText.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Введите текст!", Toast.LENGTH_SHORT).show();
        } else {
            file = new File(getCacheDir(), CACHE_FILE);
            writeDataIntoFile(file, commentsWriteText);
            etComments.getText().clear();
            Toast.makeText(getApplicationContext(), "Сохранено в файл", Toast.LENGTH_SHORT).show();
        }
    }

    // ЧТЕНИЕ ИЗ КЭШ ФАЙЛА
    public void readFromCache(View view) {
        try {
            context = getApplicationContext();
            cacheFileDir = new File(getCacheDir(), CACHE_FILE);
            fileInputStream = new FileInputStream(cacheFileDir);
            String fileData = readFromFileInputStream(fileInputStream);

            if (fileData.length() > 0) {
                etComments.setText(fileData);
                // etComments.setSelection(fileData.length());
                Toast.makeText(context, "Данные загружены!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Не загружено!", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException ex) {
            Log.e(TAG_CACHE, ex.getMessage(), ex);
        }
    }

    // Запись данных в файл
    private void writeDataIntoFile(File file, String data) {
        try {
            fileOutputStream = new FileOutputStream(file);
            this.writeDataIntoFile(fileOutputStream, data);
            fileOutputStream.close();
        } catch (FileNotFoundException ex) {
            Log.e(TAG_CACHE, ex.getMessage(), ex);
        } catch (IOException ex) {
            Log.e(TAG_CACHE, ex.getMessage(), ex);
        }
    }

    //  Данные в FileOutputStream
    private void writeDataIntoFile(FileOutputStream fileOutputStream, String data) {
        try {
            outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(data);
            // проверка полноты записи и закрытие файла, потока
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStreamWriter.close();
        } catch (FileNotFoundException ex) {
            Log.e(TAG_CACHE, ex.getMessage(), ex);
        } catch (IOException ex) {
            Log.e(TAG_CACHE, ex.getMessage(), ex);
        }
    }

    // Чтение из FileInputStream
    private String readFromFileInputStream(FileInputStream fileInputStream) {
        stringBuilder = new StringBuilder();

        try {
            if (fileInputStream != null) {
                inputStreamReader = new InputStreamReader(fileInputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                String lineData = bufferedReader.readLine();
                while (lineData != null) {
                    stringBuilder.append(lineData).append("\n");
                    lineData = bufferedReader.readLine();
                }
            }
        } catch (IOException ex) {
            Log.e(TAG_CACHE, ex.getMessage(), ex);
        } finally {
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                    bufferedReader.close();
                }
            } catch (IOException ex) {
                Log.e(TAG_CACHE, ex.getMessage(), ex);
            }
        }
        return  stringBuilder.toString();
    }
}
