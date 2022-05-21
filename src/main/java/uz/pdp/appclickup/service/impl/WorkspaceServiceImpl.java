package uz.pdp.appclickup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.*;
import uz.pdp.appclickup.entity.enums.AddType;
import uz.pdp.appclickup.entity.enums.WorkspacePermissionName;
import uz.pdp.appclickup.entity.enums.WorkspaceRoleName;
import uz.pdp.appclickup.payload.*;
import uz.pdp.appclickup.repository.*;
import uz.pdp.appclickup.service.WorkspaceService;

import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class WorkspaceServiceImpl implements WorkspaceService {
    @Autowired
    WorkspaceRepository workspaceRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    WorkspaceUserRepository workspaceUserRepository;
    @Autowired
    WorkspaceRoleRepository workspaceRoleRepository;
    @Autowired
    WorkspacePermissionRepository workspacePermissionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public ApiResponse addWorkspace(WorkspaceDTO workspaceDTO, Users user) {
        //WORKSPACE OCHDIK
        if (workspaceRepository.existsByOwnerIdAndName(user.getId(), workspaceDTO.getName()))
            return new ApiResponse("Sizda bunday nomli ishxona mavjud", false);
        Workspace workspace = new Workspace(
                workspaceDTO.getName(),
                workspaceDTO.getColor(),
                user,
                workspaceDTO.getAvatarId() == null ? null : attachmentRepository.findById(workspaceDTO.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("attachment"))
        );
        workspaceRepository.save(workspace);

        //WORKSPACE ROLE OCHDIK
        WorkspaceRole ownerRole = workspaceRoleRepository.save(new WorkspaceRole(
                workspace,
                WorkspaceRoleName.ROLE_OWNER.name(),
                null
        ));
        WorkspaceRole adminRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_ADMIN.name(), null));
        WorkspaceRole memberRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_MEMBER.name(), null));
        WorkspaceRole guestRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_GUEST.name(), null));


        //OWERGA HUQUQLARNI BERYAPAMIZ
        WorkspacePermissionName[] workspacePermissionNames = WorkspacePermissionName.values();
        List<WorkspacePermission> workspacePermissions = new ArrayList<>();

        for (WorkspacePermissionName workspacePermissionName : workspacePermissionNames) {
            WorkspacePermission workspacePermission = new WorkspacePermission(
                    ownerRole,
                    workspacePermissionName);
            workspacePermissions.add(workspacePermission);
            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_ADMIN)) {
                workspacePermissions.add(new WorkspacePermission(
                        adminRole,
                        workspacePermissionName));
            }
            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_MEMBER)) {
                workspacePermissions.add(new WorkspacePermission(
                        memberRole,
                        workspacePermissionName));
            }
            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_GUEST)) {
                workspacePermissions.add(new WorkspacePermission(
                        guestRole,
                        workspacePermissionName));
            }

        }
        workspacePermissionRepository.saveAll(workspacePermissions);

        //WORKSPACE USER OCHDIK
        workspaceUserRepository.save(new WorkspaceUser(
                workspace,
                user,
                ownerRole,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())

        ));

        return new ApiResponse("Ishxona saqlandi", true);
    }

    @Override
    public ApiResponse editWorkspace(Long id, WorkspaceDTO workspaceDTO) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
        if (!optionalWorkspace.isPresent())
            return new ApiResponse("Bunday workspace topilmadi", false);
//        if (workspaceDTO.getAvatarId()!=null) {
//            Optional<Attachment> optionalAttachment = attachmentRepository.findById(workspaceDTO.getAvatarId());
//            if (!optionalAttachment.isPresent())
//                return new ApiResponse("Attachment not found", false);
//            Attachment attachment = optionalAttachment.get();
//        }

        Workspace workspace = optionalWorkspace.get();
        workspace.setColor(workspaceDTO.getColor());
        workspace.setName(workspaceDTO.getName());
        workspace.setAvatar(workspaceDTO.getAvatarId() == null ? null : attachmentRepository.findById(workspaceDTO.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("attachment")));
        workspaceRepository.save(workspace);
        return new ApiResponse("Edet workspace", true);
    }


    @Override
    public ApiResponse changeOwnerWorkspace(ChangeOwnerDto dto) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(dto.getId());
        if (!optionalWorkspace.isPresent())
            return new ApiResponse("Workspace topilmadi", false);
        Workspace workspace = optionalWorkspace.get();
        Optional<Users> optionalUsers = userRepository.findById(dto.getOwnerId());
        if (!optionalUsers.isPresent())
            return new ApiResponse("User topilmadi", false);
        Users users = optionalUsers.get();
        workspace.setOwner(users);
        workspaceRepository.save(workspace);
        return new ApiResponse("Owner ozgartirildi", true);
    }

    @Override
    public ApiResponse deleteWorkspace(Long id) {
        try {
            workspaceRepository.deleteById(id);
            return new ApiResponse("O'chirildi", true);
        } catch (Exception e) {
            return new ApiResponse("Xatolik", false);
        }
    }
    /*
    WorspaceniUser ga ozgartirish va remove yoki add qilish
    @param worksapce id va
     */
    @Override
    public ApiResponse addOrEditOrRemoveWorkspace(Long id, MemberDTO memberDTO, Users users) {
        if (memberDTO.getAddType().equals(AddType.ADD)) {
            WorkspaceUser workspaceUser = new WorkspaceUser(
                    workspaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id")),
                    userRepository.findById(memberDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("id")),
                    workspaceRoleRepository.findById(memberDTO.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("id")),
                    new Timestamp(System.currentTimeMillis())
            );
            workspaceUserRepository.save(workspaceUser);

            //TODO EMAILGA INVITE XABAR YUBORISH QOLDI


        } else if (memberDTO.getAddType().equals(AddType.EDIT)) {
            WorkspaceUser workspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(id, memberDTO.getId()).orElseGet(WorkspaceUser::new);
            workspaceUser.setWorkspaceRole(workspaceRoleRepository.findById(memberDTO.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("id")));
            workspaceUserRepository.save(workspaceUser);
        } else if (memberDTO.getAddType().equals(AddType.REMOVE)) {
            workspaceUserRepository.deleteByWorkspaceIdAndUserId(id, memberDTO.getId());
        }
        return new ApiResponse("Muvaffaqiyatli", true);
    }
    /*
      BU method Workspace ga taklif qilingan WorkspaceUser ga tasdiqlash method
      @param Workspaceni id va Current User keladi
      @return ApiResponce;
     */
    @Override
    public ApiResponse joinToWorkspace(Long id, Users user) {
        Optional<WorkspaceUser> optionalWorkspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(id, user.getId());
        if (optionalWorkspaceUser.isPresent()) {
            WorkspaceUser workspaceUser = optionalWorkspaceUser.get();
            workspaceUser.setDateJoined(new Timestamp(System.currentTimeMillis()));
            workspaceUserRepository.save(workspaceUser);
            return new ApiResponse("Success", true);
        }
        return new ApiResponse("Error", false);
    }

    /*
    Workspace ga tegishli foydalanuvchilarni olish
     @param workspaceId Long id
     @return members
     */
    @Override
    public List<MemberDTO> getMemberGuest(Long id) {
        List<WorkspaceUser> workspaceUsers = workspaceUserRepository.findAllByWorkspaceId(id);
        // == 1- chi yoli
//        List<MemberDTO> members = new ArrayList<>();
//        for (WorkspaceUser workspaceUser : workspaceUsers) {
//            MemberDTO memberDTO = mapWorkspaceUserToMemberDto(workspaceUser);
//            members.add(memberDTO);
//        }
//        return members;
        // 2-chi yoli
        List<MemberDTO> members = workspaceUsers.stream().map(workspaceUser -> mapWorkspaceUserToMemberDto(workspaceUser)).collect(Collectors.toList());
        return members;
    }

    /*
   Ownerga tegishli workspacelarni olish
   @param Users
   @return workspaceDto
     */
    @Override
    public List<WorkspaceDTO> getMyWorkspace(Users users) {
        List<WorkspaceUser> workspaceUsers = workspaceUserRepository.findAllByUserId(users.getId());
        return  workspaceUsers.stream().map(workspaceUser -> mapWorkspaceUserToWorkspaceDto(workspaceUser.getWorkspace())).collect(Collectors.toList());
    }

    /*
    Qoshimcha method | getMyWorkspace uchun kelgan workspaceni WorkspaceDto obektiga oladi
     */
    public WorkspaceDTO mapWorkspaceUserToWorkspaceDto(Workspace workspace){
        WorkspaceDTO workspaceDTO = new WorkspaceDTO();
        workspaceDTO.setId(workspace.getId());
        workspaceDTO.setName(workspace.getName());
        workspaceDTO.setIntialLetter(workspace.getInitialLetter());
        workspaceDTO.setColor(workspace.getColor());
        workspaceDTO.setAvatarId(workspace.getAvatar()==null?null:workspace.getAvatar().getId());
        return workspaceDTO;
    }
    /*
    Qoshimcha method | getMemeberGuest uchun Kelgan WorkspaceUser dan MemberDto obektini shakllantiradi
     */
    public MemberDTO mapWorkspaceUserToMemberDto(WorkspaceUser workspaceUser){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(workspaceUser.getUser().getId());
        memberDTO.setFullName(workspaceUser.getUser().getFullName());
        memberDTO.setEmail(workspaceUser.getUser().getEmail());
        memberDTO.setRoleName(workspaceUser.getWorkspaceRole().getName());
        memberDTO.setLastActive(workspaceUser.getLastActive());
        return memberDTO;
    }

//    //Sending email comleted
//    public void sendForCompleted(String emailTo, String emailFrom){
//        String body = "<button> <a href = 'hht";
//        try {
//            MimeMessage message = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message);
//            helper.setSubject("Task completed");
//            helper.setFrom(emailFrom);
//            helper.setTo(emailTo);
//            helper.setText(body, true);
//            javaMailSender.send(message);
//        } catch (Exception ignored) {
//        }
//
//    }

}
