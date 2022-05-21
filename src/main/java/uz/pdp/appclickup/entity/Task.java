package uz.pdp.appclickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appclickup.entity.template.AbsUUIDEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task extends AbsUUIDEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    private Status status;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Task taskId;

    private Timestamp startDate;

    private boolean startTimeHas; // Boshlanish vaqtida soat korsatilganligi

    private Timestamp dueDate;

    private boolean dueDateHas; // Tugash muddatini vaqt bor yoqligi

    private Long estimateTime; // Task berilgan muddat

    private Timestamp activedDate; //Task jaroyonga kirgan vaqt

    public Task(String name, String description, Status status, Category category, Task taskId, Timestamp startDate, boolean startTimeHas, Timestamp dueDate, boolean dueDateHas) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.category = category;
        this.taskId = taskId;
        this.startDate = startDate;
        this.startTimeHas = startTimeHas;
        this.dueDate = dueDate;
        this.dueDateHas = dueDateHas;
    }
}
