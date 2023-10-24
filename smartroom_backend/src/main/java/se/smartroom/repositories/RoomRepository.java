package se.smartroom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.smartroom.entities.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {

}
