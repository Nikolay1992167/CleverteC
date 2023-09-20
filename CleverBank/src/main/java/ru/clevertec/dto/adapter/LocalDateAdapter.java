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
     * ���������� ������ LocalDate � JSON-������� � �������� �����.
     *
     * @param jsonWriter �������� ����� ��� ������ JSON-������
     * @param date       ������ LocalDate, ������� ����� ��������
     * @throws IOException ���� ��������� ������ �����-������
     */
    @Override
    public void write(JsonWriter jsonWriter, LocalDate date) throws IOException {
        jsonWriter.value(date.toString());
    }

    /**
     * ��������� ������ LocalDate �� JSON-������� �� �������� ������.
     *
     * @param jsonReader ������� ����� ��� ������ JSON-������
     * @return ������ LocalDate, ���������� �� JSON-������
     * @throws IOException             ���� ��������� ������ �����-������
     * @throws LocalDateParseException ���� ������ JSON-������ �� ������������� ������� ���� yyyy-MM-dd
     */

    //��������� ���������
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
