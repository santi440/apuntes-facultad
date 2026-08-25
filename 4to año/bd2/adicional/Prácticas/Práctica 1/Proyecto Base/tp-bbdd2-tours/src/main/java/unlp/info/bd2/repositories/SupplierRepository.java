package unlp.info.bd2.repositories;

import org.hibernate.SessionFactory;
import unlp.info.bd2.model.Supplier;

import java.util.List;

public class SupplierRepository extends RepositoryBase<Supplier>{
    public SupplierRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Supplier> getTopNSuppliersInPurchases(int n) {
        return getSession()
                .createQuery("""
                select s.supplier
                from ItemService i
                join i.service s
                group by s.supplier
                order by count(i.id) desc
            """, Supplier.class)
                .setMaxResults(n)
                .getResultList();
    }
}
