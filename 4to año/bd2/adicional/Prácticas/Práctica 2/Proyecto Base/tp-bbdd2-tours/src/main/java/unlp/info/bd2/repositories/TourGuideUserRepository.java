package unlp.info.bd2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unlp.info.bd2.model.TourGuideUser;

import java.util.Optional;

public interface TourGuideUserRepository extends JpaRepository<TourGuideUser, Long> {

    Optional<TourGuideUser> findByUsername(String username);

    Optional<TourGuideUser> findTourGuideUserByUsername(String username);
}