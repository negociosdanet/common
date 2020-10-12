package com.negociosdanet.common.security.bruteforce;

import java.util.Objects;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BruteForceService {

	@Autowired
	private RegisterFailedRepository repository;

	@Value("${auth.security.brute_force.timeRequest}")
	private long timeRequest;

	@Value("${auth.security.brute_force.numberAttempt}")
	private int numberAttempt;
	private RegisterFailed current;

	public void registerAccess(ServletRequest request) {
		current = repository.findById(request.getRemoteHost()).orElse(RegisterFailed.makeRegisterFailed(request));

		if (hasIP() && !isTimeExpired()) {
			RegisterFailed falha = current;
			falha.setAttempt(falha.getAttempt() + 1);
			falha.setLastAttempt(System.currentTimeMillis());
			repository.save(falha);
		}
	}

	public boolean canLogin() {
		return !hasIP() || isTimeExpired() || !hasMaxAttempt();
	}

	private boolean hasIP() {
		return Objects.nonNull(current);
	}

	private boolean hasMaxAttempt() {
		return current.getAttempt() > numberAttempt;
	}

	private boolean isTimeExpired() {
		return current.getTime() > timeRequest;
	}

}
