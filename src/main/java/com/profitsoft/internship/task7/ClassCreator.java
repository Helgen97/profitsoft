package com.profitsoft.internship.task7;

import com.profitsoft.internship.task7.annotation.Property;
import com.profitsoft.internship.task7.exceptions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Розробити клас-утиліту зі статичним методом, який приймає параметр типу Class і шлях до properties-файлу,
 * створює об'єкт цього класу, наповнює його атрибути значеннями з properties-файлу (використовуючи сетери) і повертає цей об'єкт.
 * <p>
 * Приклад сигнатури метода:
 * public static <T>T loadFromProperties(Class<T> cls, Path propertiesPath)
 * <p>
 * Properties-файл має формат:
 * stringProperty=value1
 * numberProperty=10
 * timeProperty=29.11.2022 18:30
 * <p>
 * Метод має вміти парсити строки, цілі числа (int та Integer) і дати з часом (Instant).
 * Створити анотацію @Property, за допомогою якої можна було б опціонально передати назву property
 * (якщо назва атрибуту класу відрізняється від ключа property в файлі),
 * а для полів типу дата/час задати - очікуваний формат дати.
 * Наприклад, клас, який ми читаємо з файлу вище, міг бі мати такі атрибути:
 * <p>
 * private String stringProperty;
 *
 * @Property(name="numberProperty") private int myNumber
 * @Property(format="dd.MM.yyyy HH:mm")
 * private Instant timeProperty;
 * <p>
 * Складені ключі (prefix.propertyKey) в цьому завданні можуть використовуватися тільки якщо ми задаємо їх в анотації @Property(name="prefix.propertyKey").
 * Якщо щось розпарсити/заповнити не вдалося (не підтримується тип, неправильний формат тощо), метод повинен кидати відповідний Exception.
 * Створити unit-тести для цього метода.
 */
public class ClassCreator {

    private static final Map<String, String> properties = new HashMap<>();
    private static final String DEFAULT_DATE_FORMAT = "dd.MM.yyyy HH:mm";

    public static <T> T createClassFromProperties(Class<T> cls, Path propertiesPath) {
        if(cls == null || propertiesPath == null) {
            throw new IllegalArgumentException("Null arguments. Please pull valid arguments.");
        }

        readPropertiesToMap(propertiesPath);

        T instance = createInstance(cls);

        fillInstanceFields(instance, cls);

        return instance;
    }

    private static <T> void fillInstanceFields(T instance, Class<T> cls) {
        for (Field field : cls.getDeclaredFields()) {
            String fieldName = field.getName();
            String setterMethodName = getSetterMethodName(fieldName);
            Object parsedValue = parseValueDependingOnAnnotation(field, fieldName);
            invokeMethod(instance, cls, field, setterMethodName, parsedValue);
        }
    }

    private static Object parseValueDependingOnAnnotation(Field field, String fieldName) {
        if (!field.isAnnotationPresent(Property.class)) {
            return getParsedValue(field.getType(), properties.get(fieldName), DEFAULT_DATE_FORMAT);
        } else {
            Property annotation = field.getAnnotation(Property.class);
            return getParsedValue(
                    field.getType(),
                    properties.get(annotation.name().isEmpty() ? fieldName : annotation.name()),
                    annotation.format().isEmpty() ? DEFAULT_DATE_FORMAT : annotation.format());
        }
    }

    private static <T> Object getParsedValue(Class<T> fieldType, String value, String dateFormat) {
        if (value == null)
            throw new NoValueOrPropertyException(String.format("No value or property for field %s found in property file", fieldType.getName()));
        Object parsedValue = null;

        if (fieldType.isAssignableFrom(String.class)) {
            parsedValue = value;
        } else if (fieldType.isAssignableFrom(Integer.class)) {
            parsedValue = Integer.parseInt(value);
        } else if (fieldType.isAssignableFrom(Instant.class)) {
            try {
                parsedValue = new SimpleDateFormat(dateFormat).parse(value).toInstant();
            } catch (ParseException e) {
                throw new WrongDateFormatException(String.format("Cannot parse date or time. Bad date format %s or cannot parse value in that date format.", dateFormat));
            }
        } else if (fieldType.isAssignableFrom(int.class)) {
            parsedValue = Integer.parseInt(value);
        } else {
            throw new ParsingFieldException(String.format("Cannot parse %s field type", fieldType.getName()));
        }
        return parsedValue;
    }

    private static <T> void invokeMethod(T instance, Class<T> cls, Field field, String setterMethodName, Object parsedValue) {
        try {
            Method fieldSetter = cls.getDeclaredMethod(setterMethodName, field.getType());
            fieldSetter.invoke(instance, parsedValue);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new SetterNotFoundException(String.format("Setter for field %s not found!", field.getName()));
        }
    }

    private static String getSetterMethodName(String fieldName) {
        return String.format("set%s%s", fieldName.substring(0, 1).toUpperCase(), fieldName.substring(1));
    }

    private static <T> T createInstance(Class<T> cls) {
        T instance = null;
        try {
            instance = findDefaultConstructor(cls).newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return instance;
    }

    private static <T> Constructor<T> findDefaultConstructor(Class<T> cls) {
        Constructor<?> defaultConstructor = null;

        for (Constructor<?> constructor : cls.getDeclaredConstructors()) {
            if (constructor.getParameterTypes().length != 0) continue;
            constructor.setAccessible(true);
            defaultConstructor = constructor;
        }

        return (Constructor<T>) defaultConstructor;
    }

    private static void readPropertiesToMap(Path pathToProperties) {
        try (var bufferedReader = new BufferedReader(new FileReader(pathToProperties.toFile()));
             var scanner = new Scanner(bufferedReader).useDelimiter("\n")) {
            while (scanner.hasNextLine()) {
                String line = scanner.next();
                String[] property = line.split("=");
                if (property.length > 1) {
                    properties.put(property[0].trim(), property[1].trim());
                }
            }
        } catch (IOException e) {
            throw new ReadingFileException("Cannot read file from path.", e);
        }
    }
}
