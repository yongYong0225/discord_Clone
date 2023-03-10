package com.hanghae.thiscord_clone.Dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
@Getter
public class SignUpRequestDto {
	@NotNull(message = "이메일은 공백이 될 수 없습니다.")
	@Pattern(regexp = "\\w+@\\w+\\.\\w+(\\.\\w+)?", message = "이메일 형식이 아닙니다.")
	private String email;

	@NotNull(message = "유저명은 공백이 될 수 없습니다.")
	private String username;

	@NotNull(message = "비밀번호는 공백이 될 수 없습니다.")
	@Pattern(regexp ="^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{6,18}$", message = "비밀번호는 영문,숫자 6~18자리여야 합니다")
	private String password;

}