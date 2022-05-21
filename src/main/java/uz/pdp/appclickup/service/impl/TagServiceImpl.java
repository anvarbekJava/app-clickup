package uz.pdp.appclickup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.Tag;
import uz.pdp.appclickup.entity.Users;
import uz.pdp.appclickup.entity.Workspace;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.TagDto;
import uz.pdp.appclickup.repository.TagRepository;
import uz.pdp.appclickup.repository.UserRepository;
import uz.pdp.appclickup.repository.WorkspaceRepository;
import uz.pdp.appclickup.service.TagService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagRepository tagRepository;
    @Autowired
    WorkspaceRepository workspaceRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public ApiResponse createTag(TagDto dto) {
        if (tagRepository.existsByNameAndWorkspaceId(dto.getName(), dto.getWorkspaceId()))
            return new ApiResponse("Tag exists", false);
        Workspace workspace = workspaceRepository.findById(dto.getWorkspaceId()).orElseThrow(() -> new ResourceNotFoundException("Worksapce not found"));

        Tag tag = new Tag(
                dto.getName(),
                dto.getColor(),
                workspace
        );
        tagRepository.save(tag);
        return new ApiResponse("Saqlandi", true);
    }

    @Override
    public ApiResponse deleteTag(UUID id) {
        try {
            tagRepository.deleteById(id);
            return new ApiResponse("deleted ", true);
        }catch (Exception e){
            return new ApiResponse("No deleted", false);
        }
    }

    @Override
    public List<Tag> getTagAll(Long workspaceId) {
        List<Tag> tagList = tagRepository.findAllByWorkspaceId(workspaceId);
        return tagList;
    }

}
