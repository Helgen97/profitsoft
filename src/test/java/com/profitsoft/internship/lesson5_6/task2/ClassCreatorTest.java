package com.profitsoft.internship.lesson5_6.task2;

import com.profitsoft.internship.lesson5_6.task2.exceptions.*;
import com.profitsoft.internship.lesson5_6.task2.testClasses.*;
import org.junit.Test;

import java.nio.file.Path;
import java.time.Instant;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ClassCreatorTest {

    private final Path pathToProperties = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource("lesson5_6Task2TestFiles/class.properties")).getPath());

    @Test
    public void createClassFromPropertiesTest_inputClassWithoutAnnotation() {
        TestClassWithoutAnnotation testClassWithoutAnnotation = ClassCreator.createClassFromProperties(TestClassWithoutAnnotation.class, pathToProperties);

        assertEquals("value1", testClassWithoutAnnotation.getStringProperty());
        assertEquals(10, testClassWithoutAnnotation.getNumberProperty());
        assertEquals(Instant.parse("2022-11-29T16:30:00Z"), testClassWithoutAnnotation.getTimeProperty());
    }

    @Test
    public void createClassFromPropertiesTest_inputClassWithAnnotation() {
        TestClassWithAnnotation testClassWithAnnotation = ClassCreator.createClassFromProperties(TestClassWithAnnotation.class, pathToProperties);

        assertEquals("value1", testClassWithAnnotation.getStringProperty());
        assertEquals(10, testClassWithAnnotation.getNumber());
        assertEquals(Instant.parse("2022-11-29T16:30:00Z"), testClassWithAnnotation.getTimeProperty());
    }

    @Test
    public void createClassFromPropertiesTest_inputClassWithComplexProperty() {
        TestClassWithComplexProperty testClassWithComplexProperty = ClassCreator.createClassFromProperties(TestClassWithComplexProperty.class, pathToProperties);

        assertEquals(10, testClassWithComplexProperty.getProperty());
        assertEquals(Integer.valueOf(10), testClassWithComplexProperty.getInteger());
        assertEquals("parse", testClassWithComplexProperty.getString());
    }

    @Test
    public void createClassFromPropertiesTest_inputClassIsNull() {
        assertThrows(IllegalArgumentException.class, () -> ClassCreator.createClassFromProperties(null, pathToProperties));
    }

    @Test
    public void createClassFromPropertiesTest_inputPathIsNull() {
        assertThrows(IllegalArgumentException.class, () -> ClassCreator.createClassFromProperties(TestClassWithPrivateDefaultConstructor.class, null));
    }

    @Test
    public void createClassFromPropertiesTest_inputPathIsWrong() {
        assertThrows(ReadingFileException.class, () -> ClassCreator.createClassFromProperties(TestClassWithPrivateDefaultConstructor.class, Path.of("/pathDoesntExist")));
    }

    @Test
    public void createClassFromPropertiesTest_inputClassWithPrivateDefaultConstructor() {
        TestClassWithPrivateDefaultConstructor testClassWithPrivateDefaultConstructor = ClassCreator.createClassFromProperties(TestClassWithPrivateDefaultConstructor.class, pathToProperties);

        assertEquals("value1", testClassWithPrivateDefaultConstructor.getStringProperty());
        assertEquals(10, testClassWithPrivateDefaultConstructor.getNumber());
        assertEquals(Instant.parse("2022-11-29T16:30:00Z"), testClassWithPrivateDefaultConstructor.getTimeProperty());
    }

    @Test
    public void createClassFromPropertiesTest_NoSetterForField() {
        assertThrows(SetterNotFoundException.class, () -> ClassCreator.createClassFromProperties(TestClassWithoutSetter.class, pathToProperties));
    }

    @Test
    public void createClassFromPropertiesTest_NoValueForFieldInPropertiesFile() {
        assertThrows(NoValueOrPropertyException.class, () -> ClassCreator.createClassFromProperties(TestClassFieldWithNoValueInProperties.class, pathToProperties));
    }

    @Test
    public void createClassFromPropertiesTest_BadValueForFieldInPropertiesFile() {
        assertThrows(NumberFormatException.class, () -> ClassCreator.createClassFromProperties(TestClassWithBadValueInProperties.class, pathToProperties));
    }

    @Test
    public void createClassFromPropertiesTest_BadDateFormatAnnotation() {
        assertThrows(WrongDateFormatException.class, () -> ClassCreator.createClassFromProperties(TestClassWithBadDateFormat.class, pathToProperties));
    }

    @Test
    public void createClassFromPropertiesTest_PropertiesDontExist() {
        assertThrows(NoValueOrPropertyException.class, () -> ClassCreator.createClassFromProperties(TestClassPropertyDoesntExist.class, pathToProperties));
    }

    @Test
    public void createClassFromPropertiesTest_FieldCannotBeParsed() {
        assertThrows(ParsingFieldException.class, () -> ClassCreator.createClassFromProperties(TestClassFieldCannotBeParsed.class, pathToProperties));
    }


}