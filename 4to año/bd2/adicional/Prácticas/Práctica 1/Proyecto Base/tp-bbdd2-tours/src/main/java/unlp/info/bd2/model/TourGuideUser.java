package unlp.info.bd2.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "tour_guide_users")
public class TourGuideUser extends User {

    @Column(nullable = false)
    private String education;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tour_guide_user_route",
            joinColumns = @JoinColumn(name = "tour_guide_user_id"),
            inverseJoinColumns = @JoinColumn(name = "route_id")
    )
    private List<Route> routes = new ArrayList<>();


    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

}
