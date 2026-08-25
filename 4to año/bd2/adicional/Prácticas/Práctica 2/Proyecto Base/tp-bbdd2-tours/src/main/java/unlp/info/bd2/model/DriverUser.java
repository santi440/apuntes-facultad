package unlp.info.bd2.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "driver_users")
public class DriverUser extends User {
    @Column(nullable = false)
    private String expedient;

    @ManyToMany
    @JoinTable(
            name="driver_user_route",
            joinColumns = @JoinColumn(name="driver_user_id"),
            inverseJoinColumns = @JoinColumn(name="route_id")
    )
    private List<Route> routes = new ArrayList<>();

    public String getExpedient() {
        return expedient;
    }

    public void setExpedient(String expedient) {
        this.expedient = expedient;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRouts(List<Route> routs) {
        this.routes = routs;
    }
}
