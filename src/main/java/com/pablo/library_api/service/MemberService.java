package com.pablo.library_api.service;
import com.pablo.library_api.exception.RecursoNaoEncontradoException;
import com.pablo.library_api.model.Member;
import com.pablo.library_api.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Membro não encontrado"));
    }
    public void delete(Long id){
        memberRepository.deleteById(id);
    }
}
