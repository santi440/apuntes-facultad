package unlp.info.bd2.repositories;

import org.hibernate.SessionFactory;
import unlp.info.bd2.model.Service;

public class ServiceRepository extends RepositoryBase<Service> {
    public ServiceRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Service getMostDemandedService() {
        return getSession()
                .createQuery("""
                select i.service
                from ItemService i
                group by i.service
                order by sum(i.quantity) desc
            """, Service.class)
                .setMaxResults(1)
                .uniqueResult();
    }
}
