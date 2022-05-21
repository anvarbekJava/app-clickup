package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.Task;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

}
