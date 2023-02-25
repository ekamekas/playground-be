package id.maseka.playground.service.device.service;

import id.maseka.playground.service.common.error.NotFoundErrorException;
import id.maseka.playground.service.device.service.dto.DeviceGetResponseDTO;
import id.maseka.playground.service.device.service.dto.DeviceUpdateOrCreateRequestDTO;

public interface DeviceService {

    String create(DeviceUpdateOrCreateRequestDTO requestDTO);

    /**
     * Get a particular device
     *
     * @param id unique identifier of device
     * @return device
     */
    DeviceGetResponseDTO get(String id) throws NotFoundErrorException;

}
