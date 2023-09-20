package ru.clevertec.dto.adapter;



import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeAdapter extends TypeAdapter<LocalTime> {

    /**
     * ���������� ������ LocalTime � JSON-������� � �������� �����.
     *
     * @param jsonWriter   �������� ����� ��� ������ JSON-������
     * @param localTime ������ LocalTime, ������� ����� ��������
     * @throws IOException ���� ��������� ������ �����-������
     */
    @Override
    public void write(JsonWriter jsonWriter, LocalTime localTime) throws IOException {
        jsonWriter.value(localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    /**
     * ��������� ������ LocalTime �� JSON-������� �� �������� ������.
     *
     * @param jsonReader ������� ����� ��� ������ JSON-������
     * @return ������ LocalTime, ���������� �� JSON-������
     * @throws IOException ���� ��������� ������ �����-������
     */
    @Override
    public LocalTime read(JsonReader jsonReader) throws IOException {
        return LocalTime.parse(jsonReader.nextString());
    }
}
