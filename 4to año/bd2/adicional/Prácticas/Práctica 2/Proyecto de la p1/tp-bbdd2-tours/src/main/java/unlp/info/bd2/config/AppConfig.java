package unlp.info.bd2.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import unlp.info.bd2.repositories.PurchaseRepository;
import unlp.info.bd2.repositories.ReviewRepository;
import unlp.info.bd2.repositories.RouteRepository;
import unlp.info.bd2.repositories.ServiceRepository;
import unlp.info.bd2.repositories.SupplierRepository;
import unlp.info.bd2.repositories.UserRepository;
import unlp.info.bd2.services.ToursService;
import unlp.info.bd2.services.ToursServiceImpl;

@Configuration
public class AppConfig {

    @Bean
    public UserRepository userRepository(SessionFactory sessionFactory) {
        return new UserRepository(sessionFactory);
    }

    @Bean
    public RouteRepository routeRepository(SessionFactory sessionFactory) {
        return new RouteRepository(sessionFactory);
    }

    @Bean
    public SupplierRepository supplierRepository(SessionFactory sessionFactory) {
        return new SupplierRepository(sessionFactory);
    }

    @Bean
    public ServiceRepository serviceRepository(SessionFactory sessionFactory) {
        return new ServiceRepository(sessionFactory);
    }

    @Bean
    public PurchaseRepository purchaseRepository(SessionFactory sessionFactory) {
        return new PurchaseRepository(sessionFactory);
    }

    @Bean
    public ReviewRepository reviewRepository(SessionFactory sessionFactory) {
        return new ReviewRepository(sessionFactory);
    }

    @Bean
    @Primary
    public ToursService toursService(SessionFactory sessionFactory) {
        return new ToursServiceImpl(sessionFactory);
    }
}