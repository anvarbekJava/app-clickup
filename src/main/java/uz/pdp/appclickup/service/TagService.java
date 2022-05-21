package uz.pdp.appclickup.service;

import uz.pdp.appclickup.entity.Tag;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.TagDto;

import java.util.List;
import java.util.UUID;

public interface TagService {
    ApiResponse createTag(TagDto dto);

    ApiResponse deleteTag(UUID id);

    List<Tag> getTagAll(Long workspaceId);
}
