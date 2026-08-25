package unlp.info.bd2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import unlp.info.bd2.model.ItemService;
import unlp.info.bd2.model.Purchase;

public interface ItemServiceRepository extends JpaRepository<ItemService, Long> {
    void deleteByPurchase(Purchase purchase);
}