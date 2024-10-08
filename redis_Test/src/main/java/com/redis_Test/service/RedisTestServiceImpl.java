package com.redis_Test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.redis_Test.entity.Member;
import com.redis_Test.repository.RedisTestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class RedisTestServiceImpl implements RedisTestService {

	@Autowired
	private RedisTestRepository redisTestRepository;
	
	@Override
	@Transactional
	public void joinMember(Member member) {
		System.out.println("joinmember service =========> " + member);
		redisTestRepository.save(member);
	}
	
	@Override
    @Cacheable(value = "Member", key = "'_' + #memberId", cacheManager = "cacheManager", unless = "#result == null")
// @Cacheable - 메서드 호출 결과가 캐시되어야 할 때 사용합니다. 
	// 메서드를 실행하기 전 메서드 호출을 가로챔 
	// 캐시에 결과가 없으면 메서드가 호출되고 결과가 있다면 캐시에서 결과를 리턴함
// value
	// 사용할 캐시 이름을 지정할 수 있습니다. redis에 저장되는 캐시 이름입니다.
	// 최초 해당 캐시명이 없으면 spring에서 생성하도록 합니다.
// key
	// 캐시된 결과가 저장될 키를 정의합니다.
	// #p0은 메서드의 첫번째 매개변수를 나타냅니다.
	// # 기호를 넣어 SpEL표현식으로 나타냄
// cacheManager
	// 사용할 캐시매니저 Bean을 지정합니다.
	// RedisConfig에서 만든 Bean을 나타냄
// unless
	// 캐시가 되지 않는 조건을 정의합니다.
	// 결과값이 null인 경우 캐시되지 않도록 지정함
// condition
	// 캐시 저장 시 조건 지정을 할 수 있습니다.
	// 객체, 필드에 대한 조건을 지정하며 SpEL문법으로 작성합니다.("Member.id.length() < 2")
// keyGenerator
	// Spring은 메서드의 파라미터와 값을 기반해 기본적으로 키를 생성합니다.
	// 자체적으로 키를 생성해야할 때 인터페이스를 구현하고 keyGenerator에 입력하면 기본키 대신 자체 키를 생성합니다.
// CacheResolver
	// 마찬가지로 사용자정의 캐시리졸버를 구현해 사용할 때 선언합니다.
// sync
	// 캐시 작업을 동기/비동기적으로 처리할지에 대한 선택이 가능합니다.
	// 기본 값을 true 입니다.
	@Transactional
	public Member getMemberInfo(Long memberId) {
		System.out.println("getMemberInfo service =========> " + memberId);
		return redisTestRepository.findOne(memberId);
	}
	
	@Override
	@CachePut(value="Member", key="'_' + #memberId", cacheManager="cacheManager")
// @CachePut - 항상 메서드 실행을 강제하고 해당 결과로 캐시를 업데이트 합니다.
	// 메서드가 데이터를 업데이트하거나 생성하고 이 결과가 캐시에 업데이트 돼야할 때 사용
	// 내부 선언은 Cacheable과 거의 동일하며 자세한 내용은 class를 확인해보시길 바랍니다.
	@Transactional
	public Member updateMember(Member member, Long memberId) {
		System.out.println("test -=====> " + memberId);
		return redisTestRepository.save(member);
	}
	
	@Override
	@CacheEvict(value="Member", key="'_' + #memberId", cacheManager="cacheManager", allEntries = true)
// @CacheEvict - 메서드 호출 시 캐시에서 키에 해당하는 캐시를 제거하는데 사용합니다.
// allEntries = true - 캐시에 저장된 값을 모두 제거해야할 때 사용
// beforeInvocation = true - 메서드 실행 전 캐시를 제거해야할 때 사용
	@Transactional
	public void deleteMember(Long memberId) {
		Member member = redisTestRepository.findOne(memberId);
		redisTestRepository.delete(member);
	}
}
