package unlp.info.bd2.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import unlp.info.bd2.model.*;
import unlp.info.bd2.repositories.*;
import unlp.info.bd2.utils.ToursException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ToursServiceImpl implements ToursService {

    private final PurchaseRepository purchaseRepository;
    private final RouteRepository routeRepository;
    private final UserRepository userRepository;
    private final DriverUserRepository driverUserRepository;
    private final TourGuideUserRepository tourGuideUserRepository;
    private final ServiceRepository serviceRepository;
    private final SupplierRepository supplierRepository;
    private final ReviewRepository reviewRepository;
    private final StopRepository stopRepository;
    private final ItemServiceRepository itemServiceRepository;

    public ToursServiceImpl(PurchaseRepository purchaseRepository, RouteRepository routeRepository, UserRepository userRepository, DriverUserRepository driverUserRepository, TourGuideUserRepository tourGuideUserRepository, ServiceRepository serviceRepository, SupplierRepository supplierRepository, ReviewRepository reviewRepository, StopRepository stopRepository, ItemServiceRepository itemServiceRepository) {
        this.purchaseRepository = purchaseRepository;
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
        this.driverUserRepository = driverUserRepository;
        this.tourGuideUserRepository = tourGuideUserRepository;
        this.serviceRepository = serviceRepository;
        this.supplierRepository = supplierRepository;
        this.reviewRepository = reviewRepository;
        this.stopRepository = stopRepository;
        this.itemServiceRepository = itemServiceRepository;
    }

    private ToursException error(String message, Exception e) {
        return new ToursException(message);
    }

    @Override
    @Transactional
    public User createUser(String username, String password, String fullName, String email, Date birthdate, String phoneNumber) throws ToursException {
        try {
            if (userRepository.findByUsername(username).isPresent()) {
                throw new ToursException("Username already exists");
            }

            if (userRepository.findByEmail(email).isPresent()) {
                throw new ToursException("Email already exists");
            }
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setName(fullName);
            user.setEmail(email);
            user.setBirthdate(birthdate);
            user.setPhoneNumber(phoneNumber);
            user.setActive(true);
            return userRepository.save(user);
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
            return userRepository.save(user);
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
            return userRepository.save(user);
        } catch (Exception e) {
            throw error("No se pudo crear el guía", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) throws ToursException {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username) throws ToursException {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public User updateUser(User user) throws ToursException {
        try {
            user.setActive(true);
            return userRepository.save(user);
        } catch (Exception e) {
            throw error("No se pudo actualizar el usuario", e);
        }
    }

    @Override
    @Transactional
    public void deleteUser(User user) throws ToursException {
        try {
            user.setActive(true);
            userRepository.save(user);
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
            return stopRepository.save(stop);
        } catch (Exception e) {
            throw error("No se pudo crear la parada", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Stop> getStopByNameStart(String name) {
        return stopRepository.findByNameStartingWithIgnoreCase(name);
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
            return routeRepository.save(route);
        } catch (Exception e) {
            throw error("No se pudo crear la ruta", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Route> getRouteById(Long id) {
        return routeRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Route> getRoutesBelowPrice(float price) {
        return routeRepository.findByPriceLessThan(price);
    }

    @Override
    @Transactional
    public void assignDriverByUsername(String username, Long idRoute) throws ToursException {
        DriverUser driver = driverUserRepository.findDriverUserByUsername(username)
                .orElseThrow(() -> new ToursException("No existe un chofer con ese username"));

        Route route = routeRepository.findById(idRoute)
                .orElseThrow(() -> new ToursException("No existe una ruta con ese id"));

        route.addDriver(driver);
        routeRepository.save(route);
    }

    @Override
    @Transactional
    public void assignTourGuideByUsername(String username, Long idRoute) throws ToursException {
        TourGuideUser guide = tourGuideUserRepository.findTourGuideUserByUsername(username)
                .orElseThrow(() -> new ToursException("No existe un guía con ese username"));

        Route route = routeRepository.findById(idRoute)
                .orElseThrow(() -> new ToursException("No existe una ruta con ese id"));

        route.addTourGuide(guide);
        routeRepository.save(route);
    }

    @Override
    @Transactional
    public Supplier createSupplier(String businessName, String authorizationNumber) throws ToursException {
        try {
            if (supplierRepository.findByAuthorizationNumber(authorizationNumber).isPresent()) {
                throw new ToursException("Constraint Violation");
            }

            Supplier supplier = new Supplier();
            supplier.setBusinessName(businessName);
            supplier.setAuthorizationNumber(authorizationNumber);

            return supplierRepository.save(supplier);
        } catch (ToursException e) {
            throw e;
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

            supplier.getServices().add(service);

            return serviceRepository.save(service);
        } catch (Exception e) {
            throw error("No se pudo agregar el servicio al supplier", e);
        }
    }

    @Override
    @Transactional
    public Service updateServicePriceById(Long id, float newPrice) throws ToursException {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new ToursException("No existe el servicio"));

        service.setPrice(newPrice);
        return serviceRepository.save(service);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Supplier> getSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) {
        return supplierRepository.findByAuthorizationNumber(authorizationNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Service> getServiceByNameAndSupplierId(String name, Long id) throws ToursException {
        return serviceRepository.findByNameAndSupplierId(name, id);
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
            if (purchaseRepository.existsByCode(code)) {
                throw new ToursException("Ya existe una compra con ese código");
            }

            if (purchaseRepository.countByRoute(route) >= route.getMaxNumberUsers()) {
                throw new ToursException("No puede realizarse la compra");
            }

            Purchase purchase = new Purchase();
            purchase.setCode(code);
            purchase.setDate(date);
            purchase.setRoute(route);
            purchase.setUser(user);
            purchase.setTotalPrice(route.getPrice());

            user.getPurchaseList().add(purchase);

            return purchaseRepository.save(purchase);
        } catch (ToursException e) {
            throw e;
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

            purchase.getItemServiceList().add(item);
            service.getItemServiceList().add(item);

            purchase.setTotalPrice(purchase.getTotalPrice() + service.getPrice() * quantity);

            return itemServiceRepository.save(item);
        } catch (Exception e) {
            throw error("No se pudo agregar el item a la compra", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Purchase> getPurchaseByCode(String code) {
        return purchaseRepository.findByCode(code);
    }

    @Override
    @Transactional
    public void deletePurchase(Purchase purchase) throws ToursException {
        try {
            Purchase managedPurchase = purchaseRepository.findById(purchase.getId())
                    .orElseThrow(() -> new ToursException("No existe la compra"));

            itemServiceRepository.deleteByPurchase(managedPurchase);

            purchaseRepository.deleteById(managedPurchase.getId());

            purchaseRepository.flush();

        } catch (ToursException e) {
            throw e;
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

            Review savedReview = reviewRepository.save(review);
            purchaseRepository.save(purchase);

            return savedReview;
        } catch (ToursException e) {
            throw e;
        } catch (Exception e) {
            throw error("No se pudo agregar la review", e);
        }
    }

    @Transactional
    public void deleteRoute(Route route) throws ToursException {
        try {
            if (purchaseRepository.existsByRoute(route)) {
                throw new ToursException("No se puede borrar una ruta con compras");
            }

            routeRepository.delete(route);
        } catch (ToursException e) {
            throw e;
        } catch (Exception e) {
            throw error("No se pudo borrar la ruta", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Purchase> getAllPurchasesOfUsername(String username) {
        return purchaseRepository.findByUserUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUserSpendingMoreThan(float mount) {
        return userRepository.getUserSpendingMoreThan(mount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Supplier> getTopNSuppliersInPurchases(int n) {
        return supplierRepository.getTopNSuppliersInPurchases(PageRequest.of(0, n));
    }

    @Override
    @Transactional(readOnly = true)
    public Long getCountOfPurchasesBetweenDates(Date start, Date end) {
        return purchaseRepository.countByDateBetween(start, end);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Route> getRoutesWithStop(Stop stop) {
        return routeRepository.findByStopsContaining(stop);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getMaxStopOfRoutes() {
        Long max = routeRepository.getMaxStopOfRoutes();
        return max != null ? max : 0L;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Route> getRoutesNotSell() {
        return routeRepository.getRoutesNotSell();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Route> getTop3RoutesWithMaxRating() {
        return routeRepository.getTop3RoutesWithMaxRating(PageRequest.of(0, 3));
    }

    @Override
    @Transactional(readOnly = true)
    public Service getMostDemandedService() {
        List<Service> result = serviceRepository.getMostDemandedService(PageRequest.of(0, 1));
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TourGuideUser> getTourGuidesWithRating1() {
        return userRepository.getTourGuidesWithRating1();
    }
}