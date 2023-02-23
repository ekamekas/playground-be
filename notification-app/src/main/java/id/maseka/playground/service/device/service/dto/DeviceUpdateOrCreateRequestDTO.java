package id.maseka.playground.service.device.service.dto;

public record DeviceUpdateOrCreateRequestDTO(
        Integer deviceType,
        String identifier,
        String externalUserId
){}
