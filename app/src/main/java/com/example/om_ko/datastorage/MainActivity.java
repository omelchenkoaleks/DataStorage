package com.example.om_ko.datastorage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private static final String COMMENTS_FILE = "comments.txt";
    private static final String TAG_FILE = "TAG_FILE";

    FileOutputStream fileOutputStream;
    FileInputStream fileInputStream;
    InputStreamReader inputStreamReader;
    BufferedReader bufferedReader;
    StringBuilder stringBuilder;
    TextView tvComments;
    EditText etComments;
    String commentsWriteText;
    String commentRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etComments = findViewById(R.id.et_comments);
        tvComments = findViewById(R.id.tv_comments);
    }

    // ЗАПИСЬ В ФАЙЛ
    public void writeFile(View view) {
        // инициализируем переменную выходного потока файла для записи данных в файл
        fileOutputStream = null;
        try {
            // производим съем данных из поля ввода
            commentsWriteText = etComments.getText().toString();

            // производим валидацию на пустом поле и запись, если поле не пустое
            if (!commentsWriteText.isEmpty()) {

                // MODE_PRIVATE закрывает доступ приложений к данным файла
                fileOutputStream = openFileOutput(COMMENTS_FILE, MODE_PRIVATE);
                // конвертация в поток байтов
                fileOutputStream.write(commentsWriteText.getBytes());

                // очищаем поле ввода после записи в файл
                etComments.getText().clear();
                // или так:
                // etComments.setText("");

                // уведомляем об успешной записи в файл
                Toast.makeText(this, "Сохранено в файл", Toast.LENGTH_SHORT).show();
            } else {
                // уведомляем о заполнении поля ввода
                Toast.makeText(this, "Введите текст", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException ex) {
            // вывод исключений в Log
            Log.e(TAG_FILE, ex.getMessage(), ex);
        } finally {
            try {
                if (fileOutputStream != null) {
                    // закрываем файл
                    fileOutputStream.close();
                }
            } catch (IOException ex) {
                // вывод исключений в Log
                Log.e(TAG_FILE, ex.getMessage(), ex);
            }
        }
    }
}
