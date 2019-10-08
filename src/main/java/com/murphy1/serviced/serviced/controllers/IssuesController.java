package com.murphy1.serviced.serviced.controllers;

import com.murphy1.serviced.serviced.model.Issue;
import com.murphy1.serviced.serviced.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.murphy1.serviced.serviced.services.IssueService;

@Controller
public class IssuesController {

    private IssueService issueService;
    private UserService userService;

    public IssuesController(IssueService issueService, UserService userService) {
        this.issueService = issueService;
        this.userService = userService;
    }

    @RequestMapping("/issues")
    public String issueList(Model model){

        model.addAttribute("issues", issueService.getAllIssues());

        return "issues.html";
    }

    @GetMapping("/issues/view/{issueId}")
    public String viewIssue(Model model, @PathVariable String issueId){
        model.addAttribute("issues", issueService.findIssueById(Long.valueOf(issueId)));

        return "issues.html";
    }

    @GetMapping("/issues/new")
    public String newIssue(Model model){
        model.addAttribute("issue", new Issue());

        return "forms/new_issue.html";
    }

    @GetMapping("/issues/update/{issueId}")
    public String updateIssue(@PathVariable String issueId, Model model){
        model.addAttribute("issue", issueService.findIssueById(Long.valueOf(issueId)));

        return "forms/new_issue.html";
    }

    @GetMapping("/issues/delete/{issueId}")
    public String deleteIssue(@PathVariable String issueId){
        issueService.deleteIssue(Long.valueOf(issueId));

        return "redirect:/issues";
    }

    @PostMapping("/issue/save")
    public String saveIssue(@ModelAttribute Issue issue){
        issueService.save(issue);

        return "redirect:/issues";
    }

    @GetMapping("/issues/assign/self/{issueId}")
    public String assignMe(@PathVariable String issueId){

        Issue issue = issueService.findIssueById(Long.valueOf(issueId));

        issue.setAssignedTo(userService.getCurrentUserName());

        issueService.save(issue);

        return "redirect:/issues/view/"+issueId;
    }

}
