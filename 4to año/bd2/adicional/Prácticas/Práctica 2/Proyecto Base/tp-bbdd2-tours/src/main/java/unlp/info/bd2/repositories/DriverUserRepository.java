package unlp.info.bd2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unlp.info.bd2.model.DriverUser;
import unlp.info.bd2.model.User;

import java.util.Optional;

public interface DriverUserRepository extends JpaRepository<DriverUser, Long> {

    Optional<DriverUser> findByUsername(String username);

    Optional<DriverUser> findDriverUserByUsername(String username);

}