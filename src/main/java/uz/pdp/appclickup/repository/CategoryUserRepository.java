package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.CategoryUser;

public interface CategoryUserRepository extends JpaRepository<CategoryUser, Long> {
}
