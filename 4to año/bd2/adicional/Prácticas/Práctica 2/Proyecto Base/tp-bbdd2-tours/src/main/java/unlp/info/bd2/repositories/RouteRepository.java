package unlp.info.bd2.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import unlp.info.bd2.dto.RouteSummaryDTO;
import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Stop;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long>
{
    @Query("""
        select distinct r
        from Route r
        join r.stops s
        where s = :stop
    """)
    List<Route> getRoutesWithStop(Stop stop);

    @Query("""
        select max(size(r.stops))
        from Route r
    """)
    Long getMaxStopOfRoutes();

    @Query("""
        from Route r
        where r.id not in (
            select p.route.id
            from Purchase p
        )
    """)
    List<Route> getRoutesNotSell();

    @Query("""
        select p.route
        from Review r
        join r.purchase p
        group by p.route
        order by avg(r.rating) desc
    """)
    List<Route> getTop3RoutesWithMaxRating(Pageable pageable);

    List<Route> findByPriceLessThan(float price);

    List<Route> findByStopsContaining(Stop stop);

    @Query("""
        select new unlp.info.bd2.dto.RouteSummaryDTO(
            r.name,
            count(p),
            avg(p.totalPrice)
        )
        from Route r
        left join Purchase p on p.route = r
        group by r.name
    """)
    List<RouteSummaryDTO> getRouteSummaries();
}