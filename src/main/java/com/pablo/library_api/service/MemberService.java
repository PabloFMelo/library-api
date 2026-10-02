package com.pablo.library_api.service;
import com.pablo.library_api.model.Member;
import com.pablo.library_api.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService (MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    public Member save(Member member) {
        return memberRepository.save(member);
    }
    public List<Member> findAll(){
        return memberRepository.findAll();
    }
    public Optional<Member> findById(Long id){
        return memberRepository.findById(id);
    }
    public void delete(Long id){
        memberRepository.deleteById(id);
    }
}
