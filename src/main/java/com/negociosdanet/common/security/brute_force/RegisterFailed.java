package com.negociosdanet.common.security.brute_force;

import java.io.Serializable;

import javax.servlet.ServletRequest;

import org.springframework.data.redis.core.RedisHash;

import lombok.Data;

@Data
@RedisHash("RegisterFailed")
public class RegisterFailed implements Serializable {

	private static final long serialVersionUID = 3103319641196505925L;
	private String ip;
	private Integer attempt;
	private Long lastAttempt;

	public static RegisterFailed makeRegisterFailed(ServletRequest request) {
		RegisterFailed falha = new RegisterFailed();
		falha.setIp(request.getRemoteHost());
		falha.setAttempt(1);
		falha.setLastAttempt(System.currentTimeMillis());
		return falha;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public long getTime() {
		return System.currentTimeMillis() - lastAttempt;
	}

}
