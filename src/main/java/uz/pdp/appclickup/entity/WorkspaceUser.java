package uz.pdp.appclickup.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appclickup.entity.template.AbsUUIDEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkspaceUser extends AbsUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Workspace workspace;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private WorkspaceRole workspaceRole;

    private Timestamp lastActive;

    @Column(nullable = false)
    private Timestamp dateInvited;

    private Timestamp dateJoined;

    public WorkspaceUser(Workspace workspace, Users user, WorkspaceRole workspaceRole, Timestamp dateInvited) {
        this.workspace = workspace;
        this.user = user;
        this.workspaceRole = workspaceRole;
        this.dateInvited = dateInvited;
    }

    public WorkspaceUser(Workspace workspace, Users user, WorkspaceRole workspaceRole, Timestamp dateInvited, Timestamp dateJoined) {
        this.workspace = workspace;
        this.user = user;
        this.workspaceRole = workspaceRole;
        this.dateInvited = dateInvited;
        this.dateJoined = dateJoined;
    }
}
