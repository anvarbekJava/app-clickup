package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appclickup.entity.Priority;
@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {
}
