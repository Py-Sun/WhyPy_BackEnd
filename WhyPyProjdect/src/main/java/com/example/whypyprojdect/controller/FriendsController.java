package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.entity.*;
import com.example.whypyprojdect.repository.MemberRepository;
import com.example.whypyprojdect.service.FriendsService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Controller
public class FriendsController {
    private final FriendsService friendsService;
    private final MemberRepository memberRepository;

    @GetMapping("/sendFriendRequest/{receiverId}")
    public String sendFriendRequest(@PathVariable long receiverId, HttpSession session) {
        Friends friends = new Friends();
        Object sender = session.getAttribute("loginName");
        friendsService.setSenderID(friends, sender);
        friendsService.saveFriendsData(friends, receiverId);
        return "redirect:/profile/" + receiverId;
    }

    @GetMapping("/profile/{memberId}")
    public String getProfileById(@PathVariable long memberId, HttpSession session, Model model) {
        Optional<MemberEntity> memberEntityById = memberRepository.findById(memberId);
        MemberDto memberDtoById = new MemberDto();
        if(memberEntityById.isPresent()) memberDtoById = MemberDto.toMemberDto((memberEntityById.get()));

        Optional<MemberEntity> memberEntityByName = memberRepository.findByMemberName((String) session.getAttribute("loginName"));
        MemberDto memberDtoByName = new MemberDto();
        if(memberEntityByName.isPresent()) memberDtoByName = MemberDto.toMemberDto((memberEntityByName.get()));
        Friends friend = friendsService.findBySenderIdAndReceiverId(memberId, memberDtoByName.getId());
        if(friend == null) {
            friend = friendsService.findBySenderIdAndReceiverId(memberDtoByName.getId(), memberId);
        }
        model.addAttribute("member", memberDtoById);
        model.addAttribute("friend", friend);
        return "temp/profile";
    }

    @GetMapping("/showFriendsRequest")
    public String showFriendsRequest(HttpSession session, Model model) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberName((String) session.getAttribute("loginName"));
        MemberDto memberDto = new MemberDto();
        if(memberEntity.isPresent()) memberDto = MemberDto.toMemberDto((memberEntity.get()));
        List<Friends> friendsRequest = friendsService.findByReceiverIdAndState(memberDto.getId(), "pending");
        model.addAttribute("friendsRequest", friendsRequest);
        return "temp/friend-request-list-page";
    }

    @GetMapping("/showFriendsList")
    public String showFriendsList(HttpSession session, Model model) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberName((String) session.getAttribute("loginName"));
        MemberDto memberDto = new MemberDto();
        if(memberEntity.isPresent()) memberDto = MemberDto.toMemberDto((memberEntity.get()));
        Long memberId = memberDto.getId();
        List<Friends> friendsList = friendsService.findFriends(memberId);
        List<String> friendsNameList = new ArrayList<>();
        if(!friendsList.isEmpty()) {
            for(Friends friends : friendsList) {
                Optional<MemberEntity> memberEntity2;
                if(friends.getSenderId() != memberId) {
                    memberEntity2 = memberRepository.findById(friends.getSenderId());
                }
                else {
                    memberEntity2 = memberRepository.findById(friends.getReceiverId());
                }
                MemberDto memberDto2 = new MemberDto();
                if(memberEntity2.isPresent()) memberDto2 = MemberDto.toMemberDto((memberEntity2.get()));
                friendsNameList.add(memberDto2.getNickName());
            }
        }
        model.addAttribute("friendsList", friendsList);
        model.addAttribute("friendsNameList", friendsNameList);
        return "temp/friend-list-page";
    }

    @GetMapping("/deleteFriend")
    public String deleteFriend(@RequestParam Integer friendId) {
        friendsService.deleteFriendsData(friendId);
        return "redirect:/showFriendsList";
    }

    @GetMapping("/receiveFriendRequest")
    public String receiveFriendRequest(@RequestParam Long senderId, @RequestParam Long receiverId) {
        friendsService.updateFriendsData(senderId, receiverId, "received");
        return "redirect:/showFriendsRequest";
    }

    @GetMapping("/denyFriendRequest")
    public String denyFriendRequest(@RequestParam Long senderId, @RequestParam Long receiverId) {
        friendsService.updateFriendsData(senderId, receiverId, "deny");
        return "redirect:/showFriendsRequest";
    }
}
