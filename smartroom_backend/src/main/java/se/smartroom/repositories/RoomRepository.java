package se.smartroom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.smartroom.entities.Room;
@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

}
