package se.smartroom.services;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.smartroom.entities.Room;
import se.smartroom.entities.data.Co2SensorData;
import se.smartroom.entities.data.TemperatureData;
import se.smartroom.entities.people.PeopleData;

import se.smartroom.entities.physicalDevice.Light;
import se.smartroom.repositories.EnvironmentDataRepository;
import se.smartroom.repositories.RoomRepository;
import se.smartroom.entities.LIFXApi.LIFXApi;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class RoomService {

    @Autowired
    private RoomRepository repository;

    @Autowired
    private EnvironmentDataRepository environmentDataRepository;

    @Value("${body.heat}")
    public double bodyHeat;

    @Autowired
    private LIFXApi lifxApi; // Assuming you have a LIFXApi

    public RoomService(RoomRepository mockRoomRepository) {
        repository = mockRoomRepository;
    }

    public RoomService(){

    }

    public RoomService(RoomRepository mockRoomRepository, EnvironmentDataRepository MockEnvironmentDataRepository) {
        repository = mockRoomRepository;
        environmentDataRepository = MockEnvironmentDataRepository;
    }

    /**
     * Stores and saves a room
     *
     * @param room to store
     * @return stored room
     */
    public Room saveRoom(Room room) {
        return repository.save(room);
    }

    public Room removeRoom(int id) {

        System.out.println("REMOVED ROOOM!!!");
        System.out.println("REMOVED ROOOM!!!");
        System.out.println("REMOVED ROOOM!!!");
        System.out.println("REMOVED ROOOM!!!");
        System.out.println("REMOVED ROOOM!!!");
        System.out.println("REMOVED ROOOM!!!");
        System.out.println("REMOVED ROOOM!!!");
        System.out.println("REMOVED ROOOM!!!");
        System.out.println("REMOVED ROOOM!!!");
        System.out.println("REMOVED ROOOM!!!");
        System.out.println("REMOVED ROOOM!!!");
        System.out.println("REMOVED ROOOM!!!");
        System.out.println("REMOVED ROOOM!!!");
        System.out.println("REMOVED ROOOM!!!");
        System.out.println("REMOVED ROOOM!!!");
        System.out.println("REMOVED ROOOM!!!");
        System.out.println("REMOVED ROOOM!!!");
        System.out.println("REMOVED ROOOM!!!");
        System.out.println("REMOVED ROOOM!!!");
        System.out.println("REMOVED ROOOM!!!");
        System.out.println("REMOVED ROOOM!!!");



        Room room = repository.findById(id).orElse(null);

        if (room != null) {
            repository.delete(room);
        }

        return room;
    }

    public Room updateRoom(Room room) {
        int id = room.getId();

        System.out.println(room);

        Optional<Room> existingRoomOptional = repository.findById(id);

        if (existingRoomOptional.isPresent()) {
        Room existingRoom = existingRoomOptional.get();

        existingRoom.setName(room.getName());
        existingRoom.setSize(room.getSize());



        if(existingRoom.getDoors().size() <= room.getDoors().size()){
            existingRoom.setDoors(room.getDoors());
        }

        if(existingRoom.getRoomWindows().size() <= room.getRoomWindows().size()){
            existingRoom.setRoomWindows(room.getRoomWindows());
        }

        if(existingRoom.getLights().size() <= room.getLights().size()){
            existingRoom.setLights(room.getLights());
        }

        if(existingRoom.getFans().size() <= room.getFans().size()){
            existingRoom.setFans(room.getFans());
        }
            //System.out.println("Still active? " + room.getDoors());

        //existingRoom.setPeopleData(room.getPeopleData());

            //System.out.println("New" + room.peopleData.get(room.peopleData.size() - 1).getCount());
            //System.out.println("Existing" + existingRoom.peopleData.get(room.peopleData.size() - 1).getCount());

           /* if( room.peopleData.size() <= 0) {
                System.out.println("Set new Random People Data");
                room.getPeopleData().add(new PeopleData(Date.valueOf(LocalDate.now()), new Random().nextInt(5 + 1)));
                existingRoom.setPeopleData(room.getPeopleData());

            }else{
                if (room.peopleData.get(room.peopleData.size() - 1).getCount() + 2 > existingRoom.peopleData.get(room.peopleData.size() - 1).getCount() && room.peopleData.get(room.peopleData.size() - 1).getCount() - 2 < existingRoom.peopleData.get(room.peopleData.size() - 1).getCount()) {
                    var count = room.peopleData.get(room.peopleData.size() - 1).getCount();

                    int value = new Random().nextInt(100) + 1;

                    if (value > 92 || count <= 1) {
                        System.out.println("Set new People Data");
                        room.peopleData.get(room.peopleData.size() - 1).setCount(count + 1);
                    } else if (value < 8) {
                        System.out.println("Reduce People Data");
                        room.peopleData.get(room.peopleData.size() - 1).setCount(count - 1);
                    }

                    existingRoom.setPeopleData(room.getPeopleData());
                }else{
                }
            }*/

            //existingRoom.setPeopleData(room.getPeopleData());

        return repository.save(existingRoom);

    } else {
        //throw new RuntimeException("Room with ID " + id + " not found");
            return null;
    }
    }

    public Room addValues(int id) {
        Room room = repository.findById(id).orElse(new Room());
        //System.out.println(room);

        Random random = new Random();
        int minValue = 10;
        int maxValue = 25;

        Timestamp randomTimestamp = new Timestamp(System.currentTimeMillis());

        room.setCo2SensorData(Collections.singletonList(new Co2SensorData(Math.random())));
        room.setTemperatureData(Collections.singletonList(new TemperatureData(random.nextDouble() * (maxValue - minValue + 1.0) + minValue)));
        room.setPeopleData(Collections.singletonList(new PeopleData(Date.valueOf(LocalDate.now()), random.nextInt(30 + 1))));
        return updateRoom(room);
    }

    /**
     * Runs every 5 seconds.
     * Going over all rooms and updating the values based on the number
     * of people in the room, the outside temperature and the number of open
     * windows, running fans.
     */
//    @Transactional
//    @Scheduled(initialDelay = 7000, fixedRate = 5000)
//    public void scheduledIntervalCalculation() {
//        List<Room> rooms = repository.findAll();
//        EnvironmentData environmentData = environmentDataRepository.findAll().get(0);
//        if (!rooms.isEmpty()) {
//            List<Room> updatedRooms = rooms.stream().map(room -> {
//                int numOpenWindows = 0;
//                if (!room.getRoomWindows().isEmpty()) {
//                    numOpenWindows = room.getRoomWindows().stream().filter(Fenster::isOpen).toList().size();
//                }
//                int numPeople = 1;
//                if (room.getPeopleData().size() > 0) {
//                    numPeople = room.getPeopleData().get(room.getPeopleData().size() - 1).getCount();
//                }
//                double temperatureAdjustment = 0.0;
//
//                double co2Adjustment = 0.0;
//                if (numPeople > 0) {
//                    co2Adjustment += 1.0;
//                } else {
//                    co2Adjustment -= 1.0;
//                }
//                double latestCo2Value = 0.0;
//                if (room.getCo2SensorData().size() > 0) {
//                    latestCo2Value = room.getCo2SensorData().get(room.getCo2SensorData().size() - 1).getcO2value();
//                }
//                double newCo2Value = latestCo2Value + co2Adjustment;
//
//                double newTemperatureAdjustment = calculateTemperatureChange(numOpenWindows, numPeople, this.bodyHeat, environmentData.getOutsideTemperature(), room.getSize());
//
//                double newTemperature = environmentData.getOutsideTemperature();
//                if (newTemperatureAdjustment > 0) {
//                    newTemperature += newTemperatureAdjustment;
//                } else {
//                    newTemperature -= newTemperatureAdjustment;
//                }
//
//
//                if( room.peopleData.size() <= 0) {
//                    room.peopleData.add(new PeopleData(Date.valueOf(LocalDate.now()), new Random().nextInt(5 + 1)));
//                }else{
//                        var count = room.peopleData.get(room.peopleData.size() - 1).getCount();
//
//                        int value = new Random().nextInt(100) + 1;
//
//                        if (value > 90 || count <= 1) {
//                            room.peopleData.get(room.peopleData.size() - 1).setCount(count + 1);
//                        } else if (value < 10) {
//                            room.peopleData.get(room.peopleData.size() - 1).setCount(count - 1);
//                        }
//
//                        room.setPeopleData(room.getPeopleData());
//                }
//
//
//
//
//                Date timestamp = new Date(System.currentTimeMillis());
//                TemperatureData newTemperatureData = new TemperatureData();
//                newTemperatureData.setTemperatureValue(newTemperature);
//                newTemperatureData.setTimestamp(timestamp);
//                Co2SensorData co2SensorData = new Co2SensorData();
//                co2SensorData.setcO2value(newCo2Value);
//                co2SensorData.setTimestamp(timestamp);
//                List<TemperatureData> roomsTempData = room.getTemperatureData();
//                roomsTempData.add(newTemperatureData);
//                room.setTemperatureData(roomsTempData);
//                List<Co2SensorData> roomsC02Data = room.getCo2SensorData();
//                roomsC02Data.add(co2SensorData);
//                room.setCo2SensorData(roomsC02Data);
//                return room;
//            }).collect(Collectors.toList());
//            repository.saveAll(updatedRooms);
//        }
//    }

    public Room findRoomById(int roomId) {
        return repository.findById(roomId).orElse(null);
    }




    public void turnOnLights(int roomId, String label, String lightId, String token) throws IOException {
        // Retrieve the room from the database
        Room room = repository.findById(roomId).orElse(null);
        //String token = room.getApiToken();

        if (room != null && token != null) {
            // Token is valid, proceed to control lights
            List<Light> lights = room.getLights();

            for (Light light : lights) {
                if (light.getLabel().equals(label) && !light.isOn()) {
                    light.setOn(true);
                    AsyncHttpClient client = new DefaultAsyncHttpClient();
                    client.prepare("PUT", "https://api.lifx.com/v1/lights/" + lightId + "/state")
                            .setHeader("accept", "text/plain")
                            .setHeader("content-type", "application/json")
                            .setHeader("Authorization","Bearer "+ token)
                            .setBody("{\"duration\":1,\"fast\":true,\"power\":\"on\"}") //only difference is the on here
                            .execute()
                            .toCompletableFuture()
                            .thenAccept(System.out::println)
                            .join();

                    client.close();
                }
            }
            // Save the updated room with lights turned on
            repository.save(room);
        } else {
            // Handle invalid token or room not found
            System.out.println("Invalid token or Room not found with ID: " + roomId);
            // Add appropriate error handling or logging based on your application's needs
        }
    }

    public void turnOffLights(int roomId, String label, String lightId, String token) throws IOException {
        Room room = repository.findById(roomId).orElse(null);
        assert room != null;
        //String token = room.getApiToken();

        if (room != null && token != null) {
            // Assuming that the lights are stored in the 'lights' property of the Room class
            List<Light> lights = room.getLights();

            // Turn on each light using the LIFX API
            for (Light light : lights) {
                if (light.getLabel().equals(label)&& light.isOn()) {
                    light.setOn(false);
                    AsyncHttpClient client = new DefaultAsyncHttpClient();
                    client.prepare("PUT", "https://api.lifx.com/v1/lights/" + lightId + "/state")
                            .setHeader("accept", "text/plain")
                            .setHeader("content-type", "application/json")
                            .setHeader("Authorization","Bearer "+ token)
                            .setBody("{\"duration\":1,\"fast\":true,\"power\":\"off\"}") //only difference is the off here
                            .execute()
                            .toCompletableFuture()
                            .thenAccept(System.out::println)
                            .join();

                    client.close();
                }
            }
            // Save the updated room with lights turned on
            repository.save(room);
        } else {
            // Handle the case where the room is not found
            System.out.println("Room not found with ID: " + roomId);
            // Add appropriate error handling or logging based on your application's needs
        }
    }

    public void updateColor(int id, String lightLabel, String lightId, String token, String colourId) throws IOException {
        Room room = repository.findById(id).orElse(null);
        assert room != null;
        //String token = room.getApiToken();

        if (token != null) {
            // Assuming that the lights are stored in the 'lights' property of the Room class
            List<Light> lights = room.getLights();

            // Turn on each light using the LIFX API
            for (Light light : lights) {
                if (light.getLabel().equals(lightLabel)&& light.isOn()) {
                    AsyncHttpClient client = new DefaultAsyncHttpClient();
                    client.prepare("PUT", "https://api.lifx.com/v1/lights/" + lightId + "/state")
                            .setHeader("accept", "text/plain")
                            .setHeader("content-type", "application/json")
                            .setHeader("Authorization","Bearer "+ token)
                            .setBody("{\"duration\":1,\"fast\":true,\"color\":\"" + colourId + "\"}")
                            .execute()
                            .toCompletableFuture()
                            .thenAccept(System.out::println)
                            .join();

                    client.close();
                }
            }
            // Save the updated room with lights turned on
            repository.save(room);
        } else {
            // Handle the case where the room is not found
            System.out.println("Room not found with ID: " + id);
            // Add appropriate error handling or logging based on your application's needs
        }
    }



    public Light getLightSelector(int roomId, String label) {
        // Retrieve the room from the database
        Room room = repository.findById(roomId).orElse(null);

        if (room != null) {
            // Assuming that the lights are stored in the 'lights' property of the Room class
            List<Light> lights = room.getLights();

            // Find the light with the specified ID
            Optional<Light> optionalLight = lights.stream().filter(light -> light.getLabel() == label).findFirst();

            if (optionalLight.isPresent()) {
                // Assuming that Light class has a method to get its selector
                return optionalLight.get();
            } else {
                // Handle the case where the light with the specified ID is not found
                System.out.println("Light not found with label: " + label);
            }
        } else {
            // Handle the case where the room is not found
            System.out.println("Room not found with ID: " + roomId);
        }

        return null;
    }





    /**
     *
     * @param numOpenWindows
     * @param numPeople
     * @param avgBodyHeat in WATT
     * @param outsideTemperature in Celsius
     * @param roomSize in m2
     * @return
     */
    public static double calculateTemperatureChange(int numOpenWindows, int numPeople, double avgBodyHeat, double outsideTemperature, double roomSize) {
        double temperatureChange = (numOpenWindows > 0 ? numOpenWindows : 0.1 * numPeople * avgBodyHeat * outsideTemperature) / (100 * roomSize);
        return Math.min(Math.max(temperatureChange, -1), 1);
    }

    /**
     * Returns a list of all rooms
     *
     * @return List of Rooms
     */
    public List<Room> getRooms() {
        return repository.findAll();
    }

    public Room getRoomById(int id) {
        return repository.findById(id).orElse(null);
    }



}
