package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.dto.ReplyDto;
import com.example.whypyprojdect.entity.Reply;
import com.example.whypyprojdect.service.ReplyService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Slf4j
@Controller
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("/createReply")
    public String createReply(@RequestParam Integer postId, @RequestParam Integer parentId, ReplyDto replyDto, HttpSession session) {
        if(session.getAttribute("loginName") == null) {
            return "/login";
        }
        Reply reply = replyDto.toEntity();
        Object writer = session.getAttribute("loginName");
        replyService.setPostID(reply, postId);
        replyService.setParentID(reply, parentId);
        replyService.setWriterId(reply, writer);
        replyService.saveReplyData(reply);
        return "redirect:/post/" + postId;
    }

    @GetMapping("/deleteReply")
    public String deleteReplyById(@RequestParam Integer postId, @RequestParam Integer replyId) {
        replyService.deleteReplyData(replyId);
        return "redirect:/post/" + postId;
    }
}
