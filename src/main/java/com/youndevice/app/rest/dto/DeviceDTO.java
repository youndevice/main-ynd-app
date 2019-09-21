package com.youndevice.app.rest.dto;


import com.youndevice.app.enums.DeviceType;

public class DeviceDTO {

    private Long id;

    private Boolean enabled = Boolean.FALSE;

    private DeviceType deviceType;

    private String userFriendlyName;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getUserFriendlyName() {
        return userFriendlyName;
    }

    public void setUserFriendlyName(String userFriendlyName) {
        this.userFriendlyName = userFriendlyName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DeviceDTO() {
    }

    public DeviceDTO(Long id, Boolean enabled, DeviceType deviceType, String userFriendlyName, String status) {

        this.id = id;
        this.enabled = enabled;
        this.deviceType = deviceType;
        this.userFriendlyName = userFriendlyName;
        this.status = status;
    }
}
