package ru.clevertec.dto.adapter;



import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeAdapter extends TypeAdapter<LocalTime> {

    /**
     * Записывает объект LocalTime в JSON-формате в выходной поток.
     *
     * @param jsonWriter   выходной поток для записи JSON-данных
     * @param localTime объект LocalTime, который нужно записать
     * @throws IOException если произошла ошибка ввода-вывода
     */
    @Override
    public void write(JsonWriter jsonWriter, LocalTime localTime) throws IOException {
        jsonWriter.value(localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    /**
     * Считывает объект LocalTime из JSON-формата из входного потока.
     *
     * @param jsonReader входной поток для чтения JSON-данных
     * @return объект LocalTime, полученный из JSON-данных
     * @throws IOException если произошла ошибка ввода-вывода
     */
    @Override
    public LocalTime read(JsonReader jsonReader) throws IOException {
        return LocalTime.parse(jsonReader.nextString());
    }
}
