package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appclickup.entity.SpaceView;
@Repository
public interface SpaceViewRepository extends JpaRepository<SpaceView, Long> {
  void deleteBySpaceIdAndId(Long space_id, Long id);
}
