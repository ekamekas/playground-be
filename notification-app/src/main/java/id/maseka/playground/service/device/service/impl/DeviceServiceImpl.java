package id.maseka.playground.service.device.service.impl;

import id.maseka.playground.service.device.domain.Device;
import id.maseka.playground.service.device.repository.DeviceRepository;
import id.maseka.playground.service.device.service.DeviceService;
import id.maseka.playground.service.device.service.dto.DeviceUpdateOrCreateRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository repository;

    public DeviceServiceImpl(DeviceRepository repository) {
        this.repository = repository;
    }

    @Override
    public String create(DeviceUpdateOrCreateRequestDTO requestDTO) {
        var entity = toEntity(requestDTO);

        entity = repository.save(entity);

        return entity.getId();
    }

    // mappers
    private Device toEntity(DeviceUpdateOrCreateRequestDTO requestDTO) {
        if(null == requestDTO) {
            return null;
        }

        return new Device(
                requestDTO.deviceType(), requestDTO.identifier(), requestDTO.externalUserId()
        );
    }
}
