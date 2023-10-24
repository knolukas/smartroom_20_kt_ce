package se.smartroom.tests;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import se.smartroom.entities.data.Co2SensorData;
import se.smartroom.entities.data.DataInterface;
import se.smartroom.entities.data.TemperatureData;
import org.junit.jupiter.api.Test;
import se.smartroom.entities.environment.EnvironmentData;
import se.smartroom.entities.environment.SEASONSTATUS;

import java.sql.Date;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class EntityDataTest {

    //Interface Tests
    @Test
    public void testConstructorAndGetters1() {
        //Date object
        Date timestamp = new Date(System.currentTimeMillis());

        // DataInterface using the constructor
        DataInterface dataInterface = new DataInterface(timestamp);

        // Assert that the timestamp is set correctly
        assertEquals(timestamp, dataInterface.getTimestamp());
    }

    @Test
    public void testSetters1() {
        // DataInterface using the default constructor
        DataInterface dataInterface = new DataInterface();

        // Create a Date object to use in the test
        Date timestamp = new Date(System.currentTimeMillis());

        // Set the timestamp using the setter method
        dataInterface.setTimestamp(timestamp);

        // Assert that the timestamp is set correctly
        assertEquals(timestamp, dataInterface.getTimestamp());
    }

    @Test
    public void testToString1() {
        // Create an instance of DataInterface using the default constructor
        DataInterface dataInterface = new DataInterface();

        // Assert that the toString method doesn't return null
        assertNotNull(dataInterface.toString());
    }

    @Test
    public void testEqualsAndHashCode1() {
        //two instances of DataInterface with the same timestamp
        Date timestamp = new Date(System.currentTimeMillis());
        DataInterface dataInterface1 = new DataInterface(timestamp);
        DataInterface dataInterface2 = new DataInterface(timestamp);

        // Assertion that the objects are considered equal and have the same hash code
        assertEquals(dataInterface1, dataInterface2);
        assertEquals(dataInterface1.hashCode(), dataInterface2.hashCode());
    }

    //Test C02SensorData
    @Test
    public void testConstructorAndGetters2() {
        //Date object to use in the test
        Date timestamp = new Date(System.currentTimeMillis());
        int id = 1;
        Double Co2Value = 45.0;

        //Constructor with one parameter
        Co2SensorData co2SensorData = new Co2SensorData(Co2Value);

        assertNotNull(co2SensorData.getTimestamp());

        // Assert that the specific fields are set correctly
        assertEquals(Co2Value, co2SensorData.getcO2value());

        //Constructor with two parameters
        Co2SensorData co2SensorData1 = new Co2SensorData(timestamp, Co2Value);
        assertNotNull(co2SensorData1.getTimestamp());
        // Assert that the inherited fields are set correctly
        assertEquals(timestamp, co2SensorData1.getTimestamp());

        // Assert that the specific field is set correctly
        assertEquals(Co2Value, co2SensorData1.getcO2value());

        //Constructor with three parameters
        Co2SensorData co2SensorData2 = new Co2SensorData(id, timestamp, Co2Value);
        // Assert that the superclass constructor is called
        assertNotNull(co2SensorData2.getTimestamp());

        // Assert that the specific fields are set correctly
        assertEquals(id, co2SensorData2.getId());
        assertEquals(Co2Value, co2SensorData2.getcO2value());
    }

    @Test
    public void testSetters2() {
        // instance of Co2SensorData using the default constructor
        Co2SensorData co2SensorData = new Co2SensorData();

        // Date object to use in the test
        Date timestamp = new Date(System.currentTimeMillis());
        Double cO2value = 42.0;

        // Set the inherited field using the setter method from DataInterface
        co2SensorData.setTimestamp(timestamp);

        // Set the specific field using the setter method from Co2SensorData
        co2SensorData.setcO2value(cO2value);

        // Assert that the inherited field is set correctly
        assertEquals(timestamp, co2SensorData.getTimestamp());

        // Assert that the specific field is set correctly
        assertEquals(cO2value, co2SensorData.getcO2value());
    }

    @Test
    public void testToString2() {
        // Create an instance of Co2SensorData using the default constructor
        Co2SensorData co2SensorData = new Co2SensorData();

        // Assert that the toString method doesn't return null
        assertNotNull(co2SensorData.toString());
    }

    @Test
    public void testEqualsAndHashCode2() {
        // Create two instances of Co2SensorData with the same values
        Date timestamp = new Date(System.currentTimeMillis());
        Double cO2value = 42.0;
        Co2SensorData co2SensorData1 = new Co2SensorData(timestamp, cO2value);
        Co2SensorData co2SensorData2 = new Co2SensorData(timestamp, cO2value);

        // Assert that the objects are considered equal and have the same hash code
        assertEquals(co2SensorData1, co2SensorData2);
        assertEquals(co2SensorData1.hashCode(), co2SensorData2.hashCode());
    }

    //Test TemperatureData
    @Test
    public void testConstructorAndGetters3() {
        // Create a Date object to use in the test
        Date timestamp = new Date(System.currentTimeMillis());
        int id = 1;
        Double temperatureValue = 25.0;

        //Constructor with one parameter
        TemperatureData temperatureData = new TemperatureData(temperatureValue);

        assertNotNull(temperatureData.getTimestamp());

        // Assert that the specific fields are set correctly
        assertEquals(temperatureValue, temperatureData.getTemperatureValue());

        //Constructor with two parameters
        TemperatureData temperatureData1 = new TemperatureData(timestamp, temperatureValue);
        assertNotNull(temperatureData1.getTimestamp());
        // Assert that the inherited fields are set correctly
        assertEquals(timestamp, temperatureData1.getTimestamp());

        // Assert that the specific field is set correctly
        assertEquals(temperatureValue, temperatureData1.getTemperatureValue());

        //Constructor with three parameters
        TemperatureData temperatureData2 = new TemperatureData(id, timestamp, temperatureValue);
        // Assert that the superclass constructor is called
        assertNotNull(temperatureData2.getTimestamp());

        // Assert that the specific fields are set correctly
        assertEquals(id, temperatureData2.getId());
        assertEquals(temperatureValue, temperatureData2.getTemperatureValue());
    }

    @Test
    public void testSetters3() {
        // instance of TemperatureData using the default constructor
        TemperatureData temperatureData = new TemperatureData();

        // Date object to use in the test
        Date timestamp = new Date(System.currentTimeMillis());
        Double temperatureValue = 25.0;

        // Set the inherited field using the setter method from DataInterface
        temperatureData.setTimestamp(timestamp);

        // Set the specific field using the setter method from TemperatureData
        temperatureData.setTemperatureValue(temperatureValue);

        // Assert that the inherited field is set correctly
        assertEquals(timestamp, temperatureData.getTimestamp());

        // Assert that the specific field is set correctly
        assertEquals(temperatureValue, temperatureData.getTemperatureValue());
    }

    @Test
    public void testToString3() {
        // instance of TemperatureData using the default constructor
        TemperatureData temperatureData = new TemperatureData();

        // Assert that the toString method doesn't return null
        assertNotNull(temperatureData.toString());
    }

    @Test
    public void testEqualsAndHashCode3() {
        // two instances of TemperatureData with the same values
        Date timestamp = new Date(System.currentTimeMillis());
        Double temperatureValue = 25.0;
        TemperatureData temperatureData1 = new TemperatureData(timestamp, temperatureValue);
        TemperatureData temperatureData2 = new TemperatureData(timestamp, temperatureValue);

        // Assert that the objects are considered equal and have the same hash code
        assertEquals(temperatureData1, temperatureData2);
        assertEquals(temperatureData1.hashCode(), temperatureData2.hashCode());
    }

    //Environment Data
    @Test
    public void testEqualsAndHashCode() {
        // two instances with the same attribute values
        EnvironmentData environment1 = new EnvironmentData(1, 25.0, "noon", SEASONSTATUS.SUMMER);
        EnvironmentData environment2 = new EnvironmentData(1, 25.0, "noon", SEASONSTATUS.SUMMER);

        //another instance with different attribute values
        EnvironmentData differentEnvironment = new EnvironmentData(2, 30.0, "evening", SEASONSTATUS.WINTER);

        // Test equals()
        assertTrue(environment1.equals(environment2)); // Expecting true
        assertFalse(environment1.equals(differentEnvironment)); // Expecting false

        // Test hashCode()
        assertEquals(environment1.hashCode(), environment2.hashCode()); // Expecting equal hash codes
        assertNotEquals(environment1.hashCode(), differentEnvironment.hashCode()); // Expecting different hash codes
    }

}

