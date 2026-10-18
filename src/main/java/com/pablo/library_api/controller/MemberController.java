package com.pablo.library_api.controller;

import com.pablo.library_api.dto.MemberRequest;
import com.pablo.library_api.dto.MemberResponse;
import com.pablo.library_api.service.MemberService;
import jakarta.validation.Valid;
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
    public List<MemberResponse> findAll(){
        return memberService.findAll();
    }

    @PostMapping
    public MemberResponse save(@Valid @RequestBody MemberRequest request){
        return memberService.save(request);
    }
    @GetMapping("/{id}")
    public MemberResponse findById(@PathVariable Long id){
        return memberService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        memberService.delete(id);
    }






}
