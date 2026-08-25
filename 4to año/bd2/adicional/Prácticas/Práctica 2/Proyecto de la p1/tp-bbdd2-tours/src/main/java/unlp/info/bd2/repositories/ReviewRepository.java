package unlp.info.bd2.repositories;

import org.hibernate.SessionFactory;
import unlp.info.bd2.model.Review;

public class ReviewRepository extends RepositoryBase<Review>
{

    public ReviewRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
