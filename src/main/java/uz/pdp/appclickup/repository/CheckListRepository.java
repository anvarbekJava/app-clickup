package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appclickup.entity.CheckList;

@Repository
public interface CheckListRepository extends JpaRepository<CheckList, Long> {
}
