package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appclickup.entity.Space;
import uz.pdp.appclickup.entity.SpaceUser;

import java.util.List;
import java.util.UUID;

public interface SpaceUserRepository extends JpaRepository<SpaceUser, UUID> {
    List<SpaceUser> findAllBySpaceId(Long space_id);


}
