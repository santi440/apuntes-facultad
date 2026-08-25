package unlp.info.bd2.repositories;

import org.hibernate.SessionFactory;
import unlp.info.bd2.model.TourGuideUser;
import unlp.info.bd2.model.User;

import java.util.List;

public class UserRepository extends RepositoryBase<User>{
    public UserRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<User> getUserSpendingMoreThan(float mount) {
        return getSession()
                .createQuery("""
                select p.user
                from Purchase p
                group by p.user
                having sum(p.totalPrice) >= :mount
            """, User.class)
                .setParameter("mount", mount)
                .getResultList();
    }

    public List<TourGuideUser> getTourGuidesWithRating1() {
        return getSession()
                .createQuery("""
                select distinct tg
                from TourGuideUser tg
                join tg.routes r
                join Purchase p on p.route = r
                join Review rev on rev.purchase = p
                where rev.rating = 1
            """, TourGuideUser.class)
                .getResultList();
    }
}
