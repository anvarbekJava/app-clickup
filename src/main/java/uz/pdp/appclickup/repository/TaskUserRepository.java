package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.TaskUser;

import java.util.UUID;

public interface TaskUserRepository extends JpaRepository<TaskUser, UUID> {
     boolean existsByTaskIdAndUsersId(UUID task_id, UUID users_id);
}
