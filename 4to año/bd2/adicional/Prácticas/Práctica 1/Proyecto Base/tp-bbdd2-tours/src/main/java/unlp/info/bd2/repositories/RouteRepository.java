package unlp.info.bd2.repositories;

import org.hibernate.SessionFactory;
import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Stop;

import java.util.List;

public class RouteRepository extends RepositoryBase<Route>{
    public RouteRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Route> getRoutesWithStop(Stop stop) {
        return getSession()
                .createQuery("""
                select distinct r
                from Route r
                join r.stops s
                where s = :stop
            """, Route.class)
                .setParameter("stop", stop)
                .getResultList();
    }

    public int getMaxStopOfRoutes() {
        Integer max = getSession()
                .createQuery("""
                select max(size(r.stops))
                from Route r
            """, Integer.class)
                .uniqueResult();

        return max != null ? max : 0;
    }

    public List<Route> getRoutesNotSell() {
        return getSession()
                .createQuery("""
                from Route r
                where r.id not in (
                    select p.route.id
                    from Purchase p
                )
            """, Route.class)
                .getResultList();
    }

    public List<Route> getTop3RoutesWithMaxRating() {
        return getSession()
                .createQuery("""
                select p.route
                from Review r
                join r.purchase p
                group by p.route
                order by avg(r.rating) desc
            """, Route.class)
                .setMaxResults(3)
                .getResultList();
    }
}
