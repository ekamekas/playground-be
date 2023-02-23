package id.maseka.playground.service.device.service;

import id.maseka.playground.service.device.service.dto.DeviceUpdateOrCreateRequestDTO;

public interface DeviceService {

    String create(DeviceUpdateOrCreateRequestDTO requestDTO);

}
