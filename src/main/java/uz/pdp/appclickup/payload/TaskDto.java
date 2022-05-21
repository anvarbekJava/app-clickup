package uz.pdp.appclickup.payload;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class TaskDto {

    private String name;

    private String description;

    private Long statusId;

    private Long categoryId;

    private UUID taskId;

    private Timestamp startDate;

    private boolean startDateHas;

    private Timestamp dueDate;

    private boolean dueDateHas;

    private Long estimateTime;

}
