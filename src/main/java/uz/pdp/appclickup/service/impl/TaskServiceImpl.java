package uz.pdp.appclickup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.appclickup.entity.*;
import uz.pdp.appclickup.payload.*;
import uz.pdp.appclickup.repository.*;
import uz.pdp.appclickup.service.AttachmentService;
import uz.pdp.appclickup.service.TaskService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    PriorityRepository priorityRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    TaskHistoryRepository taskHistoryRepository;
    @Autowired
    TaskUserRepository taskUserRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    WorkspaceRepository workspaceRepository;
    @Autowired
    TaskTagRepository taskTagRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AttachmentService attachmentService;
    @Autowired
    TaskAttachmentRepository taskAttachmentRepository;

    @Override
    public ApiResponse addTask(TaskDto dto) {
        Optional<Category> optionalCategory = categoryRepository.findById(dto.getCategoryId());
        if (!optionalCategory.isPresent())
            return new ApiResponse("Category topilmadi", false);
        Optional<Status> optionalStatus = statusRepository.findById(dto.getStatusId());
        if (!optionalStatus.isPresent())
            return new ApiResponse("Status topilmadi", false);
        Category category = optionalCategory.get();
        Status status = optionalStatus.get();
        Task task = new Task(
                dto.getName(),
                dto.getDescription(),
                status,
                category,
                null,
                dto.getStartDate(),
                dto.isStartDateHas(),
                dto.getDueDate(),
                dto.isDueDateHas()
                );
        Task savedTask = taskRepository.save(task);
        createTaskHistory(savedTask, null, null, Users.getCurrentUser().getUsername()+" created task");
        TaskUser taskUser =  new TaskUser(task, Users.getCurrentUser());
        taskUserRepository.save(taskUser);
        return new ApiResponse("Saqlandi", true);
    }

    @Override
    public ApiResponse edetTask(TaskDto dto, UUID id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
            return new ApiResponse("Topilmadi", false);
        Optional<Category> optionalCategory = categoryRepository.findById(dto.getCategoryId());
        if (!optionalCategory.isPresent())
            return new ApiResponse("Category topilmadi", false);
        Optional<Status> optionalStatus = statusRepository.findById(dto.getStatusId());
        if (!optionalStatus.isPresent())
            return new ApiResponse("Status topilmadi", false);
        Category category = optionalCategory.get();
        Status status = optionalStatus.get();
        Task task = optionalTask.get();
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setCategory(category);
        task.setDescription(dto.getDescription());
        task.setTaskId(taskRepository.findById(dto.getTaskId()).orElseThrow(() -> new ResourceNotFoundException("Task paremt")));
        task.setStartDate(dto.getStartDate());
        task.setStartTimeHas(dto.isStartDateHas());
        task.setDueDate(dto.getDueDate());
        task.setDueDateHas(dto.isDueDateHas());
        Task savedTask = taskRepository.save(task);
        createTaskHistory(savedTask, null, null, Users.getCurrentUser().getUsername()+" updated task");
        TaskUser taskUser =  new TaskUser(task, Users.getCurrentUser());
        taskUserRepository.save(taskUser);
        return new ApiResponse("Saqlandi", true);
    }

    @Override
    public ApiResponse changeStatus(UUID id, Long statusId) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
            return new ApiResponse("Topilmadi", false);
        Optional<Status> optionalStatus = statusRepository.findById(statusId);
        if (!optionalStatus.isPresent())
            return new ApiResponse("Status topilmadi", false);
        Status status = optionalStatus.get();
        Task task = optionalTask.get();
        task.setStatus(status);
        Task saveTask = taskRepository.save(task);
        createTaskHistory(task,"status", task.getStatus().getName(), status.getName());
        return new ApiResponse("Saqlandi", true);
    }

    @Override
    public ApiResponse createSubTask(TaskDto dto, UUID id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
            return new ApiResponse("Topilmadi", false);
        Task parrentTask = optionalTask.get();
        Optional<Category> optionalCategory = categoryRepository.findById(dto.getCategoryId());
        if (!optionalCategory.isPresent())
            return new ApiResponse("Category topilmadi", false);
        Optional<Status> optionalStatus = statusRepository.findById(dto.getStatusId());
        if (!optionalStatus.isPresent())
            return new ApiResponse("Status topilmadi", false);
        Category category = optionalCategory.get();
        Status status = optionalStatus.get();
        Task task = new Task(
                dto.getName(),
                dto.getDescription(),
                status,
                category,
                parrentTask,
                dto.getStartDate(),
                dto.isStartDateHas(),
                dto.getDueDate(),
                dto.isDueDateHas()
        );
        Task savedTask = taskRepository.save(task);
        createTaskHistory(savedTask, null, null, Users.getCurrentUser().getUsername()+" created subtask");
        TaskUser taskUser =  new TaskUser(task, Users.getCurrentUser());
        taskUserRepository.save(taskUser);
        return new ApiResponse("Saqlandi", true);
    }

    @Override
    public ApiResponse addCommentTask(CommentDto dto, UUID id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
            return new ApiResponse("Topilmadi", false);
        Task task = optionalTask.get();
        Comment comment = new Comment(
                dto.getName(),
                task
                );
        commentRepository.save(comment);
        return new ApiResponse("Comment saqlandi", true);
    }

    @Override
    public ApiResponse deleteComment(UUID id) {
        try {
            commentRepository.deleteById(id);
            return new ApiResponse("Deleted commnent", true);
        }catch (Exception e){
            return new ApiResponse("No delete", false);
        }
    }

    @Override
    public ApiResponse addTag(TagDto dto, UUID id) {
        Tag tag = null;
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task"));
       if(tagRepository.existsByNameAndWorkspaceId(dto.getName(), dto.getWorkspaceId())) {
           Optional<Tag> optionalTag = tagRepository.findById(dto.getId());
           if (!optionalTag.isPresent())
               return new ApiResponse("Topilmadi", false);
           tag = optionalTag.get();
       }else {
           tag = tagRepository.save(new Tag(dto.getName(), dto.getColor(),
                   workspaceRepository.findById(dto.getWorkspaceId()).orElseThrow(() -> new ResourceNotFoundException("task"))));

       }
       TaskTag taskTag = new TaskTag(
               task,
               tag
       );
       taskTagRepository.save(taskTag);
       createTaskHistory(task, Users.getCurrentUser().getUsername()+" "+tag.getName(),null, tag.getName());
       return new ApiResponse("Tag saqlandi", true);
    }

    @Override
    public ApiResponse removeTag(UUID id) {
        try {
            TaskTag taskTag = taskTagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tasktag"));
            createTaskHistory(taskTag.getTask(), Users.getCurrentUser().getUsername()+" taskTag", taskTag.getTag().getName(), null);
            taskTagRepository.deleteById(id);
            createTaskHistory(taskTag.getTask(), Users.getCurrentUser().getUsername(),taskTag.getTag().getName(), null);
            return new ApiResponse("Deleted taskTag", true);
        }catch (Exception e){
            return new ApiResponse("No deleted", false);
        }
    }

    @Override
    public ApiResponse assignAddUser(TaskUserDto dto) {
        Task task = taskRepository.findById(dto.getTaskId()).orElseThrow(() -> new ResourceNotFoundException("Task"));
        List<TaskUser> taskUserList = new ArrayList<>();
        for (UUID list : dto.getUserId()) {
            taskUserList.add(new TaskUser(
                    task,
                    userRepository.findById(list).orElseThrow(() -> new ResourceNotFoundException("User"))
                    ));
        }
        taskUserRepository.saveAll(taskUserList);
        createTaskHistory(task, Users.getCurrentUser().getUsername(), null, null);
        return new ApiResponse("Essign qilindi", true);
    }

    @Override
    public ApiResponse removeAssign(TaskUserDto dto) {
        Task task = taskRepository.findById(dto.getTaskId()).orElseThrow(() -> new ResourceNotFoundException("Task"));
        List<TaskUser> taskUserList = new ArrayList<>();
        for (UUID list : dto.getUserId()) {
            taskUserList.add(new TaskUser(
                    task,
                    userRepository.findById(list).orElseThrow(() -> new ResourceNotFoundException("User"))
            ));
        }
        createTaskHistory(task, Users.getCurrentUser().getUsername()+" remove User", null, null);
        taskUserRepository.deleteAll(taskUserList);
        return new ApiResponse("Deleted all", true);
    }

    @Override
    public ApiResponse addAttachmentTask(TaskAttachmentDto dto, MultipartHttpServletRequest request) {
        Task task = taskRepository.findById(dto.getTaskId()).orElseThrow(() -> new ResourceNotFoundException("Task"));
        ApiResponse apiResponse = attachmentService.uploadFileSystem(request);
        if (apiResponse.getAttachment()!=null) {
            taskAttachmentRepository.save(new TaskAttachment(task, apiResponse.getAttachment(), dto.isPinCoverImaga()));
            createTaskHistory(task, Users.getCurrentUser().getUsername(), null, null);
            return new ApiResponse("Attachment upload", true);
        }else {
            return new ApiResponse("No upload", false);
        }
    }
    
    public  void createTaskHistory(Task task, String changeFieldName, String before, String after){
        TaskHistory taskHistory = new TaskHistory(task, changeFieldName, before, after);
        taskHistoryRepository.save(taskHistory);
    }
    
}
