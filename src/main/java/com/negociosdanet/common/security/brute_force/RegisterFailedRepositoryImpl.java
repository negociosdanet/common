package com.negociosdanet.common.security.brute_force;

import java.util.Optional;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RegisterFailedRepositoryImpl implements RegisterFailedRepository {

	private RedisTemplate<String, RegisterFailed> redisTemplate;
	private HashOperations<String, String, RegisterFailed> hashOperations;
	private static final String KEY = "REGISTER_FAILED";

	public RegisterFailedRepositoryImpl(RedisTemplate<String, RegisterFailed> redisTemplate) {
		this.setRedisTemplate(redisTemplate);
		this.hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public Optional<RegisterFailed> findById(String remoteHost) {
		return Optional.ofNullable(hashOperations.get(KEY, remoteHost));
	}

	@Override
	public void save(RegisterFailed falha) {
		hashOperations.put(KEY, falha.getIp(), falha);
	}
	
	@Override
	public void delete(String remoteHost) {
		hashOperations.delete(KEY, remoteHost);
	}

	public RedisTemplate<String, RegisterFailed> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, RegisterFailed> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

}
