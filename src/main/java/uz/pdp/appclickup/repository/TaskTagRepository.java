package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.TaskTag;

import java.util.UUID;

public interface TaskTagRepository extends JpaRepository<TaskTag, UUID> {
}
