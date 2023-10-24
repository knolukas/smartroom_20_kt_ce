package se.smartroom.tests;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;
import se.smartroom.entities.people.PeopleData;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;


@ExtendWith(MockitoExtension.class)
public class PeopleTest {
    @Test
    public void testPeopleDataConstructor() {
        // Arrange
        Date expectedTimestamp = new Date(System.currentTimeMillis());
        int expectedCount = 10;

        // Act
        PeopleData peopleData = new PeopleData(expectedTimestamp, expectedCount);

        // Assert
        assertEquals(expectedTimestamp, peopleData.getTimestamp());
        assertEquals(expectedCount, peopleData.getCount());
    }

    @Test
    public void testConstructorAndGetIdSetId() {
        // instance using the constructor with three variables
        int initialId = 1;
        Date timestamp = new Date(System.currentTimeMillis());
        int count = 5;
        PeopleData peopleData = new PeopleData(initialId, timestamp, count);

        // Test getId()
        assertEquals(initialId, peopleData.getId()); // Expecting initialId

        // Use setId() to update the ID
        int updatedId = 2;
        peopleData.setId(updatedId);

        // Test getId() after updating the ID
        assertEquals(updatedId, peopleData.getId()); // Expecting updatedId
    }

    @Test
    public void testPeopleDataEqualsAndHashCode() {
        // instances of PeopleData with the same values
        Date timestamp = new Date(System.currentTimeMillis());
        int count = 10;

        PeopleData data1 = new PeopleData(timestamp, count);
        PeopleData data2 = new PeopleData(timestamp, count);

        // Assert that the instances are equal
        assertEquals(data1, data2);

        // Assert that the hash codes are equal
        assertEquals(data1.hashCode(), data2.hashCode());

        // Create an instance with different values
        PeopleData data3 = new PeopleData(timestamp, count + 1);

        // Assert that the instances are not equal
        assertNotEquals(data1, data3);

        // Assert that the hash codes are different
        assertNotEquals(data1.hashCode(), data3.hashCode());
    }

    @Test
    public void testPeopleDataToString() {
        // instance of PeopleData
        Date timestamp = new Date(System.currentTimeMillis());
        int count = 10;
        PeopleData peopleData = new PeopleData(timestamp, count);

        // Assert that the toString method returns the expected string representation
        assertEquals("PeopleData{id=0, timestamp=" + timestamp + ", count=" + count + "}", peopleData.toString());
    }

    @Test
    public void testPeopleDataGettersAndSetters() {
        // instance of PeopleData
        PeopleData peopleData = new PeopleData();

        // Set values using the setter methods
        Date expectedTimestamp = new Date(System.currentTimeMillis());
        int expectedCount = 10;

        peopleData.setTimestamp(expectedTimestamp);
        peopleData.setCount(expectedCount);

        // Retrieve the values using the getter methods
        Date actualTimestamp = peopleData.getTimestamp();
        int actualCount = peopleData.getCount();

        // Assert that the retrieved values match the expected values
        assertEquals(expectedTimestamp, actualTimestamp);
        assertEquals(expectedCount, actualCount);
    }
}
