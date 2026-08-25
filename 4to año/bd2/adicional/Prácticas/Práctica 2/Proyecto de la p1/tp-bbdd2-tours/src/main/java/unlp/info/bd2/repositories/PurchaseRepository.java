package unlp.info.bd2.repositories;

import org.hibernate.SessionFactory;
import unlp.info.bd2.model.Purchase;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PurchaseRepository extends RepositoryBase<Purchase>
{
    public PurchaseRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Purchase> getAllPurchasesOfUsername(String username){
        return this.getSession().createQuery("from Purchase where user.username = :username", Purchase.class)
                .setParameter("username", username)
                .getResultList();
    }

    public long getCountOfPurchasesBetweenDates(Date start, Date end) {
        Long count = getSession()
                .createQuery("""
                select count(p)
                from Purchase p
                where p.date between :start and :end
            """, Long.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .uniqueResult();
        return count != null ? count : 0L;
    }
}
