package id.maseka.playground.service.device.service.impl;

import id.maseka.playground.service.common.error.NotFoundErrorException;
import id.maseka.playground.service.device.domain.Device;
import id.maseka.playground.service.device.repository.DeviceRepository;
import id.maseka.playground.service.device.service.DeviceService;
import id.maseka.playground.service.device.service.dto.DeviceGetResponseDTO;
import id.maseka.playground.service.device.service.dto.DeviceUpdateOrCreateRequestDTO;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Map;

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

    @Override
    public DeviceGetResponseDTO get(String id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new NotFoundErrorException(String.format("resource %s of %s is not found", id, Device.class.getSimpleName()), Map.of(Device.class, id)));
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

    private DeviceGetResponseDTO toDTO(Device entity) {
        if(null == entity) {
            return null;
        }

        return new DeviceGetResponseDTO(
               entity.getId(), entity.getDeviceType().name(), entity.getIdentifier(), entity.getExternalUserId(), entity.getLastActive().atZone(ZoneId.systemDefault())
        );
    }
}
