package id.maseka.playground.service.device.service.dto;

import java.time.ZonedDateTime;

public record DeviceGetResponseDTO(
        String id,
        String deviceType,
        String identifier,
        String externalUserId,
        ZonedDateTime lastActive
) {}
