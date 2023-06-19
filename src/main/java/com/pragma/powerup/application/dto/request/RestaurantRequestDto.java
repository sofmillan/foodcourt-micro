package com.pragma.powerup.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRequestDto {
    @NotNull(message = "Restaurant name must not be null")
    @NotBlank(message = "Restaurant name cannot be an empty string")
    @Pattern(regexp = "^(?![0-9]*$)[a-zA-Z0-9]+$", message = "Restaurant name is not valid")
    private String name;

    @NotNull(message = "Nit must not be null")
    @NotBlank(message = "Nit cannot be an empty string")
    @Pattern(regexp = "^[0-9]*$", message = "Restaurant name is not valid")
    private String nit;

    @NotNull(message = "Address must not be null")
    @NotBlank(message = "Address cannot be an empty string")
    private String address;

    @NotNull(message = "Phone number must not be null")
    @NotBlank(message = "Phone number cannot be an empty string")
    @Pattern(regexp = "^(\\(?\\+?[0-9]{2,12}\\)?)$", message = "Phone number is not valid")
    private String phoneNumber;

    @NotNull(message = "Url must not be null")
    @NotBlank(message = "Url cannot be an empty string")
    @URL(message = "Logo url is not valid")
    private String logoUrl;

    @NotNull(message = "Owner id must not be null")
    private Long ownerId;
}
