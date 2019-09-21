package com.youndevice.app.dto;


import com.youndevice.app.domain.Device;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplianceDTO {

    private Long id;
    private String userFriendlyName;
    private Device device;

}
