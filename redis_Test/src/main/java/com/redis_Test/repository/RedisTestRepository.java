package com.redis_Test.repository;

import com.redis_Test.entity.Member;

public interface RedisTestRepository {
	Member save(Member member);
	Member findOne(Long memberId);
	void delete(Member member);
}
