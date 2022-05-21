package uz.pdp.appclickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appclickup.entity.enums.AccessType;
import uz.pdp.appclickup.entity.template.AbsLongEntity;


import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Project extends AbsLongEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @ManyToOne
    private Space space;

    @Enumerated(EnumType.STRING)
    private AccessType accessType;

    private boolean archived;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Category> categories;

    public Project(String name, String color, Space space, AccessType accessType) {
        this.name = name;
        this.color = color;
        this.space = space;
        this.accessType = accessType;
    }



}
