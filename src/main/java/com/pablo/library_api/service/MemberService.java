package com.pablo.library_api.service;
import com.pablo.library_api.dto.MemberRequest;
import com.pablo.library_api.dto.MemberResponse;
import com.pablo.library_api.exception.RecursoNaoEncontradoException;
import com.pablo.library_api.model.Member;
import com.pablo.library_api.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse save(MemberRequest request) {
        Member member = new Member();
        member.setName(request.name().trim());
        member.setEmail(request.email().trim());

        Member saved = memberRepository.save(member);
        return toResponse(saved);
    }

        private MemberResponse toResponse(Member member){
            return new MemberResponse(member.getId(), member.getName(), member.getEmail(), member.getRegistrationDate());
        }
        public List<MemberResponse> findAll () {
            return memberRepository.findAll().stream().map(this::toResponse).toList();

        }
        public MemberResponse findById (Long id){
            Member member = memberRepository.findById(id)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Membro não encontrado"));
            return toResponse(member);
        }
        public void delete (Long id){
            memberRepository.deleteById(id);
        }
    }