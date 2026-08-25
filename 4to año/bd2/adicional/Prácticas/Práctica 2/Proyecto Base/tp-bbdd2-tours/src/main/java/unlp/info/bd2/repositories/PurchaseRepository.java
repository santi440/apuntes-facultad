package unlp.info.bd2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import unlp.info.bd2.model.Purchase;
import unlp.info.bd2.model.Route;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>
{
    List<Purchase> findByUserUsername(String username);

    long countByDateBetween(Date start, Date end);

    @Query("""
    select distinct p
    from Purchase p
    left join fetch p.itemServiceList
    where p.code = :code
    """)
    Optional<Purchase> findByCode(String code);

    boolean existsByCode(String code);

    boolean existsByRoute(Route route);

    long countByRoute(Route route);

    void deleteByCode(String code);
}