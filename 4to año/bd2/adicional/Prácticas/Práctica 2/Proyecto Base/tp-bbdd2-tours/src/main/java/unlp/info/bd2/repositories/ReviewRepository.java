package unlp.info.bd2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import unlp.info.bd2.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> { }