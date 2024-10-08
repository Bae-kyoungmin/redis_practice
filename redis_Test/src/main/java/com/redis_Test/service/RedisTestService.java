package com.redis_Test.service;

import com.redis_Test.entity.Member;

public interface RedisTestService {
	void joinMember(Member member);
	Member updateMember(Member member, Long memberId);
	Member getMemberInfo(Long memberId);
	void deleteMember(Long memberId);
}
