package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByNameAndProjectId(String name, Long project_id);
}
