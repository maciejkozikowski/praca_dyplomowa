package praca_dyplomowa.praca.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserCreateDto {
    @NotNull
    private String name;
    @NotNull
    private String password;
    @NotNull
    private Boolean isAdmin;

}
