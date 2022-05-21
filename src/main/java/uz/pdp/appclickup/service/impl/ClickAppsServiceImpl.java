package uz.pdp.appclickup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.ClickApps;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.ClickAppsDto;
import uz.pdp.appclickup.repository.ClickAppsRepository;
import uz.pdp.appclickup.repository.IconRepository;
import uz.pdp.appclickup.service.ClickAppsService;

import java.util.Optional;

@Service
public class ClickAppsServiceImpl implements ClickAppsService {
    @Autowired
    ClickAppsRepository clickAppsRepository;

    @Autowired
    IconRepository iconRepository;

    @Override
    public ApiResponse addClickApps(ClickAppsDto dto) {
        if (clickAppsRepository.existsByName(dto.getName()))
            return new ApiResponse("Bunday clickApps mavjud", false);

        ClickApps clickApps = new ClickApps(
                dto.getName(),
                dto.getIconId()==null?null:iconRepository.findById(dto.getIconId()).orElseThrow(() -> new ResourceNotFoundException("Icon"))
        );
        clickAppsRepository.save(clickApps);
        return new ApiResponse("Saqlandi", true);
    }

    @Override
    public ApiResponse edetClickApps(Long id, ClickAppsDto dto) {
        Optional<ClickApps> optionalClickApps = clickAppsRepository.findById(id);
        if (!optionalClickApps.isPresent())
            return new ApiResponse("Bunday clickApps yo'q", false);
        ClickApps clickApps = optionalClickApps.get();
        clickApps.setName(dto.getName());
        clickApps.setIcon(dto.getIconId()==null?null:iconRepository.findById(dto.getIconId()).orElseThrow(() -> new ResourceNotFoundException("Icon")));
        clickAppsRepository.save(clickApps);
        return new ApiResponse("Edet qilindi", true);
    }
}
