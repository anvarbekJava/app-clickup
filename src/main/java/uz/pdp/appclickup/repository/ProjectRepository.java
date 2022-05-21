package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appclickup.entity.Project;
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByNameAndSpaceId(String name, Long space_id);
}
