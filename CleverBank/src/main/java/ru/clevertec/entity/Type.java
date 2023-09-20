package ru.clevertec.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {
    REPLENISHMENT("����������"),
    WITHDRAWAL("������"),
    TRANSFER("�������");

    private final String name;
}
