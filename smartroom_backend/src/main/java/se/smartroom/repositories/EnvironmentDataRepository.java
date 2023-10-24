package se.smartroom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.smartroom.entities.environment.EnvironmentData;

public interface EnvironmentDataRepository extends JpaRepository<EnvironmentData, Integer> {
}
