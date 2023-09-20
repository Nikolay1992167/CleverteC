package ru.clevertec.dto.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.SneakyThrows;
import ru.clevertec.exception.conflict.LocalDateParseException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    /**
     * Записывает объект LocalDate в JSON-формате в выходной поток.
     *
     * @param jsonWriter выходной поток для записи JSON-данных
     * @param date       объект LocalDate, который нужно записать
     * @throws IOException если произошла ошибка ввода-вывода
     */
    @Override
    public void write(JsonWriter jsonWriter, LocalDate date) throws IOException {
        jsonWriter.value(date.toString());
    }

    /**
     * Считывает объект LocalDate из JSON-формата из входного потока.
     *
     * @param jsonReader входной поток для чтения JSON-данных
     * @return объект LocalDate, полученный из JSON-данных
     * @throws IOException             если произошла ошибка ввода-вывода
     * @throws LocalDateParseException если строка JSON-данных не соответствует формату даты yyyy-MM-dd
     */

    //проверить аннотацию
    @SneakyThrows
    @Override
    public LocalDate read(JsonReader jsonReader) throws IOException {
        try {
            return LocalDate.parse(jsonReader.nextString());
        } catch (DateTimeParseException e) {
            throw new LocalDateParseException("Date is out of pattern: yyyy-MM-dd. Right example: 2023-08-30");
        }
    }
}
