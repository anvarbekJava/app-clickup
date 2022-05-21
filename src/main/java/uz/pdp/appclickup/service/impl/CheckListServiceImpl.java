package uz.pdp.appclickup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.*;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.CheckListDto;
import uz.pdp.appclickup.payload.CheckListItemDto;
import uz.pdp.appclickup.repository.*;
import uz.pdp.appclickup.service.CheckListService;

import java.util.Optional;
import java.util.UUID;

@Service
public class CheckListServiceImpl implements CheckListService {

    @Autowired
    CheckListRepository checkListRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    CheckListItemRepository checkListItemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskHistoryRepository taskHistoryRepository;
    @Override
    public ApiResponse addCheckList(CheckListDto dto) {
        Task task = taskRepository.findById(dto.getTaskId()).orElseThrow(() -> new ResourceNotFoundException("task"));
        CheckList saveCheck = checkListRepository.save(new CheckList(dto.getName(), task));
        createTaskHistory(task, Users.getCurrentUser().getUsername(), null, dto.getName());
        return new ApiResponse("Save checkList", true);
    }

    @Override
    public ApiResponse edetCheckList(CheckListDto dto, Long id) {
        CheckList checkList = checkListRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("checkList"));
        createTaskHistory(checkList.getTask(), Users.getCurrentUser().getUsername()+ " edet checkList", checkList.getName(), dto.getName());
        checkList.setName(dto.getName());
        checkListRepository.save(checkList);
        return new ApiResponse("Edeting", true);
    }

    @Override
    public ApiResponse deleteCheckList(Long id) {
        try {
            checkListRepository.deleteById(id);
            return new ApiResponse("Deleted checkList", true);
        }catch (Exception e){
            return new ApiResponse("No deleted checkList", false);
        }
    }

    @Override
    public ApiResponse addCheckListItem(CheckListItemDto dto) {
        CheckList checkList = checkListRepository.findById(dto.getCheckId()).orElseThrow(() -> new ResourceNotFoundException("checkList"));
        Users user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("user"));
        CheckListItem checkListItem = new CheckListItem(
                dto.getName(),
                checkList,
                dto.isResolved(),
                user
        );
        checkListItemRepository.save(checkListItem);
        createTaskHistory(checkList.getTask(), Users.getCurrentUser().getUsername(), null, dto.getName());
        return new ApiResponse("Saqlandi", true);
    }

    @Override
    public ApiResponse addAssignCheckList(Long id, UUID userId) {
        CheckListItem checkListItem = checkListItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Checklist"));
            Users user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User"));
            checkListItem.setUsers(user);
            checkListItemRepository.save(checkListItem);

        createTaskHistory(checkListItem.getCheckList().getTask(), Users.getCurrentUser().getUsername(), checkListItem.getUsers().getEmail(), user.getEmail());
        return new ApiResponse("Ozgartirildi", true);
    }

    @Override
    public ApiResponse edetItemCheckList(CheckListItemDto dto, Long id) {
        Optional<CheckListItem> optionalCheckListItem = checkListItemRepository.findById(id);
        if (!optionalCheckListItem.isPresent())
            return new ApiResponse("Topilmadi", false);
        CheckListItem checkListItem = optionalCheckListItem.get();
        CheckList checkList = checkListRepository.findById(dto.getCheckId()).orElseThrow(() -> new ResourceNotFoundException("checkList"));
        Users user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("user"));
        checkListItem.setName(dto.getName());
        checkListItem.setCheckList(checkList);
        checkListItem.setResolved(dto.isResolved());
        checkListItem.setUsers(user);
        checkListItemRepository.save(checkListItem);
        createTaskHistory(checkList.getTask(), Users.getCurrentUser().getUsername()+" edet checkListItem", null, dto.getName());
        return new ApiResponse("Saqlandi", true);
    }

    public  void createTaskHistory(Task task, String changeFieldName, String before, String after){
        TaskHistory taskHistory = new TaskHistory(task, changeFieldName, before, after);
        taskHistoryRepository.save(taskHistory);
    }
}
