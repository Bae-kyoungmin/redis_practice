package com.redis_Test.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.redis_Test.entity.Member;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisTestRepositoryImpl implements RedisTestRepository {
	
	@Autowired
	private EntityManager em;
	
	@Override
    public Member save(Member member) {
        if (member.getId() == null) {
            em.persist(member);
        } else {
            Member findMember = em.find(Member.class, member.getId());
            findMember.setName(member.getName());
        }

        return member;
    }

	@Override
	public Member findOne(Long memberId) {
		return em.find(Member.class, memberId);
	}

	@Override
	public void delete(Member member) {
		em.remove(member);
	}
}
