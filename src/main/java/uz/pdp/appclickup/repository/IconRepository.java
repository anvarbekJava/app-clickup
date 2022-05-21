package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.appclickup.entity.Icon;
@Repository
public interface IconRepository extends JpaRepository<Icon, Long> {
}
