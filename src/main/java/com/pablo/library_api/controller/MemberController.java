package com.pablo.library_api.controller;

import com.pablo.library_api.model.Member;
import com.pablo.library_api.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")

public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping
    public List<Member> findAll(){
        return memberService.findAll();
    }

    @PostMapping
    public Member save(@RequestBody Member member){
        return memberService.save(member);
    }
    @GetMapping("/{id}")
    public Member findById(@PathVariable Long id){
        return memberService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        memberService.delete(id);
    }






}
