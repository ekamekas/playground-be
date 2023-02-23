package id.maseka.playground.service.device.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Target of notification
 */
@Entity
@Table(name = "device")
public class Device {

    @Id
    @Column(name = "id", nullable = false)
    private String id = UUID.randomUUID().toString();

    @NotNull(message = "device type cannot be null")
    @Column(name = "device_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private DeviceType deviceType;

    /**
     * identifier will be a token
     */
    @NotBlank(message = "identifier cannot be null or blank")
    @Column(name = "identifier", nullable = false)
    private String identifier;

    /**
     * a custom user id
     */
    @Length(min = 1, message = "external user id cannot be blank")
    @Column(name = "external_user_id")
    private String externalUserId;

    @Column(name = "last_active")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastActive = LocalDateTime.now();

    public Device() {}

    public Device(Integer deviceTypeOrdinal, String identifier, String externalUserId) {
        if(null != deviceTypeOrdinal) {
            setDeviceType(deviceTypeOrdinal);
        }
        this.identifier = identifier;
        this.externalUserId = externalUserId;
    }

    public Device(DeviceType deviceType, String identifier, String externalUserId) {
        this.deviceType = deviceType;
        this.identifier = identifier;
        this.externalUserId = externalUserId;
    }

    public String getId() {
        return id;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }


    public void setDeviceType(@NotNull(message = "device type cannot be null") Integer deviceTypeOrdinal) {
        try {
            this.deviceType = DeviceType.values()[deviceTypeOrdinal - 1];
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Device Type ordinal " + deviceTypeOrdinal + " is not found");
        }
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getExternalUserId() {
        return externalUserId;
    }

    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getLastActive() {
        return lastActive;
    }

    public void setLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device device)) return false;

        return id.equals(device.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
