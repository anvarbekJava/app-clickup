package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appclickup.entity.View;
@Repository
public interface ViewRepository extends JpaRepository<View, Long> {
     View getByName(String name);
}
