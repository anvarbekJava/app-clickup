package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.ClickApps;

public interface ClickAppsRepository extends JpaRepository<ClickApps, Long> {
    boolean existsByName(String name);
    ClickApps getByName(String name);
}
