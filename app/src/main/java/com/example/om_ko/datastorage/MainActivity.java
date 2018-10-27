package com.example.om_ko.datastorage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    // ЧТЕНИЕ ИЗ ФАЙЛА
    public void readFile(View view) {
        // инициализируем переменную входного потока файла для чтения данных из файла
        fileInputStream = null;

        try {
            // FileInputStream открываем соединение с файлоа
            fileInputStream = openFileInput(COMMENTS_FILE);
            // InputStreamReader - мост от потоков байтов к потокам символов:
            // он считываем байты и декодирует их на символы с использованием
            // указанной кодировки
            inputStreamReader = new InputStreamReader(fileInputStream);
            // BufferedReader читаем текст из потока ввода символов, буферизуя символы,
            // чтобы обеспечить эффективное считывание символов, строк
            bufferedReader = new BufferedReader(inputStreamReader);
            // инициализация StringBuilder
            stringBuilder = new StringBuilder();
            // инициализация String переменной
            commentRows = "";
            // наполняем StringBuilder в цикле
            while ((commentRows = bufferedReader.readLine()) != null) {
                stringBuilder.append(commentRows).append("\n");
            }
            tvComments.setText(stringBuilder.toString());
            tvComments.setTextColor(getResources().getColor(R.color.colorBlack));
        } catch (FileNotFoundException ex) {
            Toast.makeText(this, "Файл не найден!", Toast.LENGTH_SHORT).show();
            Log.e(TAG_FILE, ex.getMessage(), ex);
        } catch (IOException ex) {
            // вывод исключений в Log
            Log.e(TAG_FILE, ex.getMessage(), ex);
        } finally {
            try {
                if (fileInputStream != null)
                    // закрываем файл
                    fileInputStream.close();
            } catch (IOException ex) {
                // вывод исключений в Log
                Log.e(TAG_FILE, ex.getMessage(), ex);
            }
        }
    }

    // ЗАГРУЗКА ИЗ ФАЙЛА ДЛЯ РЕДАКТИРОВАНИЯ
    public void editFile(View view) {
        try {
            // FileInputStream открываем соединение с файлом
            fileInputStream = openFileInput(COMMENTS_FILE);
            // InputStream - мост от потоков байтов к потокам символов:
            // он считываем байты и декодирует их на символы с использованием
            // указанной кодировки
            inputStreamReader = new InputStreamReader(fileInputStream);
            // BufferedReader читаем текст из потока ввода символов, буферизуя символы,
            // чтобы обеспечить эффективное считывание символов, строк
            bufferedReader = new BufferedReader(inputStreamReader);
            // инициализация StringBuilder
            stringBuilder = new StringBuilder();
            // инициализация String переменной
            commentRows = "";
            // наполняем StringBuilder в цикле
            while ((commentRows = bufferedReader.readLine()) != null) {
                stringBuilder.append(commentRows).append("\n");
            }
            // выводим данные файла
            etComments.setText(stringBuilder.toString());
        } catch (FileNotFoundException ex) {
            Toast.makeText(this, "Файл не найден!", Toast.LENGTH_SHORT).show();
            Log.e(TAG_FILE, ex.getMessage(), ex);
        } catch (IOException ex) {
            // вывод исключений в Log
            Log.e(TAG_FILE, ex.getMessage(), ex);
        } finally {
            try {
                if (fileInputStream != null)
                    // закрываем файл
                    fileInputStream.close();
            } catch (IOException ex) {
                // вывод исключений в Log
                Log.e(TAG_FILE, ex.getMessage(), ex);
            }
        }
    }

    // УДАЛЕНИЕ ФАЙЛА
    public void deleteFile(View view) {
        // самый простой способ удалить файл = вызов delete() в объекте File
        String dir = getFilesDir().getAbsolutePath();
        File file = new File(dir, COMMENTS_FILE);
        boolean isDel = file.delete();
        Toast.makeText(this, "Файл удален!", Toast.LENGTH_SHORT).show();
        Log.w("Delete Check", "File deleted: " + dir + "/" + COMMENTS_FILE + " " + isDel);

        // Удаление определенного приватного файла, связанного с пакетом приложений Context
        // через deleteFile()
//        File file = new File(getFilesDir(), COMMENTS_FILE);
//        if (file.exists()) {
//            deleteFile(COMMENTS_FILE);
//            Toast.makeText(this, R.string.toast_file_deleted, Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, R.string.toast_file_not_found, Toast.LENGTH_SHORT).show();
//        }
    }

    // метод отвечает за появление меню в активности
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.cache_activity:
                startActivity(new Intent(this, CacheActivity.class));
                return true;
            case R.id.external_public_activity:
                startActivity(new Intent(this, ExternalPublicActivity.class));
                return true;
            case R.id.external_private_activity:
                startActivity(new Intent(this, ExternalPrivateActivity.class));
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }
    }
}
