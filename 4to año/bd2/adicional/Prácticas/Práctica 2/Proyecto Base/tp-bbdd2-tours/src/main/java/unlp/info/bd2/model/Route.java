package unlp.info.bd2.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false)
    private float totalKm;

    @Column(nullable = false)
    private int maxNumberUsers;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "route_stop",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns  = @JoinColumn(name = "stop_id")
    )
    private List<Stop> stops = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "routes")
    private List<DriverUser> driverList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "routes")
    private List<TourGuideUser> tourGuideList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(float totalKm) {
        this.totalKm = totalKm;
    }

    public int getMaxNumberUsers() {
        return maxNumberUsers;
    }

    public void setMaxNumberUsers(int maxNumberUsers) {
        this.maxNumberUsers = maxNumberUsers;
    }

    public List<Stop> getStops() {
        if (stops == null) {
            stops = new ArrayList<>();
        }
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public List<DriverUser> getDriverList() {
        if (driverList == null) {
            driverList = new ArrayList<>();
        }
        return driverList;
    }

    public void setDriverList(List<DriverUser> driverList) {
        this.driverList = driverList;
    }

    public List<TourGuideUser> getTourGuideList() {
        if (tourGuideList == null) {
            tourGuideList = new ArrayList<>();
        }
        return tourGuideList;
    }

    public void setTourGuideList(List<TourGuideUser> tourGuideList) {
        this.tourGuideList = tourGuideList;
    }

    public void addDriver(DriverUser driverUser) {
        if (driverUser != null && !this.getDriverList().contains(driverUser)) {
            this.getDriverList().add(driverUser);
        }
        if (driverUser != null && !driverUser.getRoutes().contains(this)) {
            driverUser.getRoutes().add(this);
        }
    }

    public void removeDriver(DriverUser driverUser) {
        if (driverUser != null) {
            this.getDriverList().remove(driverUser);
            driverUser.getRoutes().remove(this);
        }
    }

    public void addTourGuide(TourGuideUser tourGuideUser) {
        if (tourGuideUser != null && !this.getTourGuideList().contains(tourGuideUser)) {
            this.getTourGuideList().add(tourGuideUser);
        }
        if (tourGuideUser != null && !tourGuideUser.getRoutes().contains(this)) {
            tourGuideUser.getRoutes().add(this);
        }
    }

    public void removeTourGuide(TourGuideUser tourGuideUser) {
        if (tourGuideUser != null) {
            this.getTourGuideList().remove(tourGuideUser);
            tourGuideUser.getRoutes().remove(this);
        }
    }

}
