package unlp.info.bd2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import unlp.info.bd2.model.TourGuideUser;
import unlp.info.bd2.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{
    @Query("""
        select p.user
        from Purchase p
        group by p.user
        having sum(p.totalPrice) > :mount
    """)
    List<User> getUserSpendingMoreThan(float mount);

    @Query("""
        select distinct tg
        from TourGuideUser tg
        join tg.routes r
        join Purchase p on p.route = r
        join Review rev on rev.purchase = p
        where rev.rating = 1
    """)
    List<TourGuideUser> getTourGuidesWithRating1();

    Optional<User> findByUsername(String username);

    Optional<User> findTourGuideUserByUsername(String username);


    Optional<Object> findByEmail(String email);
}