package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.Tag;

import java.util.List;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    boolean existsByNameAndWorkspaceId(String name, Long workspace_id);
    List<Tag> findAllByWorkspaceId(Long workspace_id);
}
