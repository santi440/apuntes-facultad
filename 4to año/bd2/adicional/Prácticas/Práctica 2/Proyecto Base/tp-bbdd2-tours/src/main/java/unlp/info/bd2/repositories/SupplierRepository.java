package unlp.info.bd2.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import unlp.info.bd2.model.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long>
{
    @Query("""
        select s.supplier
        from ItemService i
        join i.service s
        group by s.supplier
        order by count(i.id) desc
    """)
    List<Supplier> getTopNSuppliersInPurchases(Pageable pageable);

    Optional<Supplier> findByAuthorizationNumber(String authorizationNumber);


}