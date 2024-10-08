package com.redis_Test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redis_Test.entity.Member;
import com.redis_Test.service.RedisTestService;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/redis")
@RequiredArgsConstructor
public class RedisTestController {

	@Autowired
	private RedisTestService redisTestService;
	//redis-server --port 6380 --slaveof 127.0.0.1 6379
	
	@GetMapping("/{memberId}")
	public ResponseEntity<?> getMemberInfo(@PathVariable("memberId") Long memberId) {
		return ResponseEntity.ok(redisTestService.getMemberInfo(memberId));
	}
	
	@PostMapping("/join")
	public ResponseEntity<?> joinMember(@RequestBody Map<String, String> memberInfo) {
		Member member = new Member();
		member.setName(memberInfo.get("name"));
		redisTestService.joinMember(member);
		return ResponseEntity.ok("멤버등록!");
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateMember(@RequestBody Map<String, String> memberInfo) {
		Member member = new Member();
		member.setId(Long.parseLong(memberInfo.get("id")));
		member.setName(memberInfo.get("name").toString());
		redisTestService.updateMember(member, member.getId());
		return ResponseEntity.ok("멤버 정보 변경");
	}
	
	@DeleteMapping("/{memberId}")
	public ResponseEntity<?> deleteMember(@PathVariable("memberId") Long memberId) {
		redisTestService.deleteMember(memberId);
		return ResponseEntity.ok("멤버 탈퇴");
	}
	
	
	
}
