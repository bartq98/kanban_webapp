package com.example.kanban.controllers;

import com.example.kanban.entities.boards.Board;
import com.example.kanban.entities.boards.BoardRepository;
import com.example.kanban.entities.user.User;
import com.example.kanban.entities.user.UserDetailsImpl;
import com.example.kanban.entities.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping(path="/panel")
public class PanelController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getLoggedUserBoard(@AuthenticationPrincipal UserDetailsImpl principal, ModelAndView modelAndView) {
        User tokenUser = userRepository.findByEmail(principal.getEmail()).get();
        Optional<Board[]> boards = boardRepository.getAllBoards(tokenUser.getId());
        if(boards.isPresent()) {
            modelAndView.addObject("boards", boards.get());
        }
        modelAndView.setViewName("fragments/actions/board");
        return modelAndView;
    }

    @GetMapping(path="/profile")
    public String Info(@AuthenticationPrincipal UserDetailsImpl principal, Model model){
        model.addAttribute("userinfo", userRepository.findByEmail(principal.getEmail()).get());
        return "fragments/userprofile";
    }
}
