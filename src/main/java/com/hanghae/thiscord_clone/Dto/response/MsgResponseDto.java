package com.hanghae.thiscord_clone.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MsgResponseDto {

	private String msg;
	private int statusCode;

	public MsgResponseDto (String msg, int statusCode) {
		this.msg = msg;
		this.statusCode = statusCode;
	}
}