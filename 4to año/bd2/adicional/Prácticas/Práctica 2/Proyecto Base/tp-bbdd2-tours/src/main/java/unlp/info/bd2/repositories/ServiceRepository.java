package unlp.info.bd2.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import unlp.info.bd2.model.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long>
{
    @Query("""
        select i.service
        from ItemService i
        group by i.service
        order by sum(i.quantity) desc
    """)
    List<Service> getMostDemandedService(Pageable pageable);

    Optional<Service> findByNameAndSupplierId(String name, Long supplierId);
}