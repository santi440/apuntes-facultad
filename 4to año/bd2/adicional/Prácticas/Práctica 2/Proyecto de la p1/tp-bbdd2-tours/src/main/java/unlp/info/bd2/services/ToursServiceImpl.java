package unlp.info.bd2.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import unlp.info.bd2.model.*;
import unlp.info.bd2.utils.ToursException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ToursServiceImpl implements ToursService {

    private final SessionFactory sessionFactory;

    public ToursServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    private ToursException error(String message, Exception e) {
        return new ToursException(message);
    }

    @Override
    @Transactional
    public User createUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber) throws ToursException {
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setName(fullName);
            user.setEmail(email);
            user.setBirthdate(birthdate);
            user.setPhoneNumber(phoneNumber);
            user.setActive(true);

            getSession().persist(user);
            return user;
        } catch (Exception e) {
            throw error("No se pudo crear el usuario", e);
        }
    }

    @Override
    @Transactional
    public DriverUser createDriverUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String expedient) throws ToursException {
        try {
            DriverUser user = new DriverUser();
            user.setUsername(username);
            user.setPassword(password);
            user.setName(fullName);
            user.setEmail(email);
            user.setBirthdate(birthdate);
            user.setPhoneNumber(phoneNumber);
            user.setActive(true);
            user.setExpedient(expedient);

            getSession().persist(user);
            return user;
        } catch (Exception e) {
            throw error("No se pudo crear el chofer", e);
        }
    }

    @Override
    @Transactional
    public TourGuideUser createTourGuideUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber, String education) throws ToursException {
        try {
            TourGuideUser user = new TourGuideUser();
            user.setUsername(username);
            user.setPassword(password);
            user.setName(fullName);
            user.setEmail(email);
            user.setBirthdate(birthdate);
            user.setPhoneNumber(phoneNumber);
            user.setActive(true);
            user.setEducation(education);

            getSession().persist(user);
            return user;
        } catch (Exception e) {
            throw error("No se pudo crear el guía", e);
        }
    }

    @Override
    @Transactional
    public Optional<User> getUserById(Long id) throws ToursException {
        try {
            return Optional.ofNullable(getSession().get(User.class, id));
        } catch (Exception e) {
            throw error("No se pudo buscar el usuario por id", e);
        }
    }

    @Override
    @Transactional
    public Optional<User> getUserByUsername(String username) throws ToursException {
        try {
            return getSession()
                    .createQuery("from User u where u.username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw error("No se pudo buscar el usuario por username", e);
        }
    }

    @Override
    @Transactional
    public User updateUser(User user) throws ToursException {
        try {
            User merged = (User) getSession().merge(user);
            return merged;
        } catch (Exception e) {
            throw error("No se pudo actualizar el usuario", e);
        }
    }

    @Override
    @Transactional
    public void deleteUser(User user) throws ToursException {
        try {
            User managed = getSession().contains(user) ? user : getSession().merge(user);
            getSession().remove(managed);
        } catch (Exception e) {
            throw error("No se pudo borrar el usuario", e);
        }
    }

    @Override
    @Transactional
    public Stop createStop(String name, String description) throws ToursException {
        try {
            Stop stop = new Stop();
            stop.setName(name);
            stop.setDescription(description);
            getSession().persist(stop);
            return stop;
        } catch (Exception e) {
            throw error("No se pudo crear la parada", e);
        }
    }

    @Override
    @Transactional
    public List<Stop> getStopByNameStart(String name) {
        return getSession()
                .createQuery("from Stop s where lower(s.name) like lower(:name)", Stop.class)
                .setParameter("name", name + "%")
                .getResultList();
    }

    @Override
    @Transactional
    public Route createRoute(String name, float price, float totalKm, int maxNumberOfUsers, List<Stop> stops) throws ToursException {
        try {
            Route route = new Route();
            route.setName(name);
            route.setPrice(price);
            route.setTotalKm(totalKm);
            route.setMaxNumberUsers(maxNumberOfUsers);
            route.setStops(stops != null ? stops : new ArrayList<>());

            getSession().persist(route);
            return route;
        } catch (Exception e) {
            throw error("No se pudo crear la ruta", e);
        }
    }

    @Override
    @Transactional
    public Optional<Route> getRouteById(Long id) {
        return Optional.ofNullable(getSession().get(Route.class, id));
    }

    @Override
    @Transactional
    public List<Route> getRoutesBelowPrice(float price) {
        return getSession()
                .createQuery("from Route r where r.price < :price", Route.class)
                .setParameter("price", price)
                .getResultList();
    }

    @Override
    @Transactional
    public void assignDriverByUsername(String username, Long idRoute) throws ToursException {
        try {
            DriverUser driver = getSession()
                    .createQuery("from DriverUser d where d.username = :username", DriverUser.class)
                    .setParameter("username", username)
                    .uniqueResult();

            Route route = getSession().get(Route.class, idRoute);

            if (driver == null) {
                throw new ToursException("No existe un chofer con ese username");
            }
            if (route == null) {
                throw new ToursException("No existe una ruta con ese id");
            }

            route.addDriver(driver);
            getSession().merge(route);
        } catch (ToursException e) {
            throw e;
        } catch (Exception e) {
            throw error("No se pudo asignar el chofer", e);
        }
    }

    @Override
    @Transactional
    public void assignTourGuideByUsername(String username, Long idRoute) throws ToursException {
        try {
            TourGuideUser guide = getSession()
                    .createQuery("from TourGuideUser g where g.username = :username", TourGuideUser.class)
                    .setParameter("username", username)
                    .uniqueResult();

            Route route = getSession().get(Route.class, idRoute);

            if (guide == null) {
                throw new ToursException("No existe un guía con ese username");
            }
            if (route == null) {
                throw new ToursException("No existe una ruta con ese id");
            }

            route.addTourGuide(guide);
            getSession().merge(route);
        } catch (ToursException e) {
            throw e;
        } catch (Exception e) {
            throw error("No se pudo asignar el guía", e);
        }
    }

    @Override
    @Transactional
    public Supplier createSupplier(String businessName, String authorizationNumber) throws ToursException {
        try {
            Supplier supplier = new Supplier();
            supplier.setBusinessName(businessName);
            supplier.setAuthorizationNumber(authorizationNumber);
            getSession().persist(supplier);
            return supplier;
        } catch (Exception e) {
            throw error("No se pudo crear el supplier", e);
        }
    }

    @Override
    @Transactional
    public Service addServiceToSupplier(String name, float price, String description, Supplier supplier) throws ToursException {
        try {
            Service service = new Service();
            service.setName(name);
            service.setPrice(price);
            service.setDescription(description);
            service.setSupplier(supplier);

            getSession().persist(service);
            return service;
        } catch (Exception e) {
            throw error("No se pudo agregar el servicio al supplier", e);
        }
    }

    @Override
    @Transactional
    public Service updateServicePriceById(Long id, float newPrice) throws ToursException {
        try {
            Service service = getSession().get(Service.class, id);
            if (service == null) {
                throw new ToursException("No existe el servicio");
            }
            service.setPrice(newPrice);
            return (Service) getSession().merge(service);
        } catch (ToursException e) {
            throw e;
        } catch (Exception e) {
            throw error("No se pudo actualizar el precio del servicio", e);
        }
    }

    @Override
    @Transactional
    public Optional<Supplier> getSupplierById(Long id) {
        return Optional.ofNullable(getSession().get(Supplier.class, id));
    }

    @Override
    @Transactional
    public Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) {
        return getSession()
                .createQuery("from Supplier s where s.authorizationNumber = :authorizationNumber", Supplier.class)
                .setParameter("authorizationNumber", authorizationNumber)
                .uniqueResultOptional();
    }

    @Override
    @Transactional
    public Optional<Service> getServiceByNameAndSupplierId(String name, Long id) throws ToursException {
        try {
            return getSession()
                    .createQuery("from Service s where s.name = :name and s.supplier.id = :id", Service.class)
                    .setParameter("name", name)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw error("No se pudo buscar el servicio", e);
        }
    }

    @Override
    @Transactional
    public Purchase createPurchase(String code, Route route, User user) throws ToursException {
        return createPurchase(code, new Date(), route, user);
    }

    @Override
    @Transactional
    public Purchase createPurchase(String code, Date date, Route route, User user) throws ToursException {
        try {
            Purchase purchase = new Purchase();
            purchase.setCode(code);
            purchase.setDate(date);
            purchase.setRoute(route);
            purchase.setUser(user);
            purchase.setTotalPrice(route.getPrice());

            getSession().persist(purchase);
            return purchase;
        } catch (Exception e) {
            throw error("No se pudo crear la compra", e);
        }
    }

    @Override
    @Transactional
    public ItemService addItemToPurchase(Service service, int quantity, Purchase purchase) throws ToursException {
        try {
            ItemService item = new ItemService();
            item.setService(service);
            item.setQuantity(quantity);
            item.setPurchase(purchase);

            getSession().persist(item);

            purchase.setTotalPrice(purchase.getTotalPrice() + service.getPrice() * quantity);
            getSession().merge(purchase);

            return item;
        } catch (Exception e) {
            throw error("No se pudo agregar el item a la compra", e);
        }
    }

    @Override
    @Transactional
    public Optional<Purchase> getPurchaseByCode(String code) {
        return getSession()
                .createQuery("from Purchase p where p.code = :code", Purchase.class)
                .setParameter("code", code)
                .uniqueResultOptional();
    }

    @Override
    @Transactional
    public void deletePurchase(Purchase purchase) throws ToursException {
        try {
            Purchase managed = getSession().contains(purchase) ? purchase : getSession().merge(purchase);
            getSession().remove(managed);
        } catch (Exception e) {
            throw error("No se pudo borrar la compra", e);
        }
    }

    @Override
    @Transactional
    public Review addReviewToPurchase(int rating, String comment, Purchase purchase) throws ToursException {
        try {
            if (purchase.getReview() != null) {
                throw new ToursException("La compra ya tiene review");
            }

            Review review = new Review();
            review.setRating(rating);
            review.setComment(comment);
            review.setPurchase(purchase);

            purchase.setReview(review);

            getSession().persist(review);
            getSession().merge(purchase);

            return review;
        } catch (ToursException e) {
            throw e;
        } catch (Exception e) {
            throw error("No se pudo agregar la review", e);
        }
    }

    @Override
    @Transactional
    public void deleteRoute(Route route) throws ToursException {
        try {
            Long count = getSession()
                    .createQuery("select count(p) from Purchase p where p.route.id = :id", Long.class)
                    .setParameter("id", route.getId())
                    .uniqueResult();

            if (count != null && count > 0) {
                throw new ToursException("No se puede borrar una ruta con compras");
            }

            Route managed = getSession().contains(route) ? route : getSession().merge(route);
            getSession().remove(managed);
        } catch (ToursException e) {
            throw e;
        } catch (Exception e) {
            throw error("No se pudo borrar la ruta", e);
        }
    }

    @Override
    @Transactional
    public List<Purchase> getAllPurchasesOfUsername(String username) {
        return getSession()
                .createQuery("from Purchase p where p.user.username = :username", Purchase.class)
                .setParameter("username", username)
                .getResultList();
    }

    @Override
    @Transactional
    public List<User> getUserSpendingMoreThan(float mount) {
        return getSession()
                .createQuery("""
                    select p.user
                    from Purchase p
                    group by p.user
                    having sum(p.totalPrice) > :mount
                """, User.class)
                .setParameter("mount", mount)
                .getResultList();
    }

    @Override
    @Transactional
    public List<Supplier> getTopNSuppliersInPurchases(int n) {
        return getSession()
                .createQuery("""
                    select s.supplier
                    from ItemService i
                    join i.service s
                    group by s.supplier
                    order by sum(i.quantity) desc
                """, Supplier.class)
                .setMaxResults(n)
                .getResultList();
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
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

    @Override
    @Transactional
    public Long getMaxStopOfRoutes() {
        return getSession()
                .createQuery("""
                    select max(size(r.stops))
                    from Route r
                """, Long.class)
                .uniqueResult();
    }

    @Override
    @Transactional
    public List<Route> getRoutsNotSell() {
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

    @Override
    @Transactional
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

    @Override
    @Transactional
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

    @Override
    @Transactional
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