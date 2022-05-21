package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appclickup.entity.CheckListItem;

public interface CheckListItemRepository extends JpaRepository<CheckListItem, Long> {
}
