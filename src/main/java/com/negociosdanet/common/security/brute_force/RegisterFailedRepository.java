package com.negociosdanet.common.security.brute_force;

import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface RegisterFailedRepository {

	Optional<RegisterFailed> findById(String remoteHost);

	void save(RegisterFailed falha);
	
	void delete(String remoteHost);

}
