package com.example.kanban.controllers;

import com.example.kanban.entities.membership.MemberType;
import com.example.kanban.entities.membership.Membership;
import com.example.kanban.exceptions.BoardNotFoundException;
import com.example.kanban.exceptions.JSONException;
import com.example.kanban.entities.boards.Board;
import com.example.kanban.entities.boards.BoardRepository;
import com.example.kanban.entities.membership.MembershipRepository;
import com.example.kanban.entities.sections.Section;
import com.example.kanban.entities.sections.SectionRepository;
import com.example.kanban.entities.task.Task;
import com.example.kanban.entities.task.TaskRepository;
import com.example.kanban.entities.user.User;
import com.example.kanban.entities.user.UserDetailsImpl;
import com.example.kanban.entities.user.UserRepository;
import com.example.kanban.exceptions.PermissionDeniedException;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping(path="/board")
public class BoardController {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private MembershipRepository membershipRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SectionRepository sectionRepository;

    @RequestMapping(value = "{id}")
    public ModelAndView getBoardById(@PathVariable("id") Integer boardId,
                                     @AuthenticationPrincipal UserDetailsImpl principal,
                                     ModelAndView modelAndView) throws BoardNotFoundException, PermissionDeniedException {

        User tokenUser = userRepository.findByEmail(principal.getEmail()).get();
        Optional<Board> board = boardRepository.findById(boardId);
        Optional<Membership> membership =
                membershipRepository.getMembershipsByBoardIdAndUserId(boardId, tokenUser.getId());

        if(board.isPresent()) {
            if(membership.isPresent()){
                modelAndView.addObject("board", board.get());
                MemberType this_user=membership.get().getMember_type();
                if(this_user==MemberType.MANAGER){
                    modelAndView.addObject("permission","allowed");
                }
            }
            else {
                throw new PermissionDeniedException("You have no permission to see this board");
            }
        }
        else {
            throw new BoardNotFoundException("Board with the given id does not exist");
        }


        String redirectURL="/board/"+boardId+"/add_new_user_to_board";
        modelAndView.addObject("redirect_add",redirectURL);
        String redirectURL2="/board/"+boardId.toString()+"/delete_user";
        modelAndView.addObject("redirect_delete",redirectURL2);
        modelAndView.setViewName("fragments/actions/board-by-id");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/{BID}/tasks", method = RequestMethod.GET)
    public Optional<Task[]> TasksBoard(@PathVariable int BID,ModelAndView modelAndView,@AuthenticationPrincipal UserDetailsImpl principal) throws JSONException {
        Optional<Board> boardOptional=boardRepository.findById(BID);
        Optional<User> userOptional = userRepository.findByEmail(principal.getEmail());
        if(boardOptional.isPresent() && userOptional.isPresent()){
            Board board=boardOptional.get();
            User user=userOptional.get();
            if(membershipRepository.existsByUserAndBoard(user,board)){
                Optional<Task[]> tasks=taskRepository.tasksFromBoard(BID);
                if(tasks.isPresent()){
                    return tasks;
                }
                else{
                    throw new JSONException("You have 0 tasks");
                }
            }
            else {
                throw new JSONException("You don't have permissions for this board");
            }
        }
        else{
           throw new JSONException("Wrong user or board");
        }
    }
    @ResponseBody
    @RequestMapping(value = "/{BID}/sections_from_board", method = RequestMethod.GET)
    public Optional<Section[]> SectionsBoard(@PathVariable int BID) throws JSONException {
        Optional<Section[]> sections=sectionRepository.getSectionsFromBoard(BID);
        if(sections.isPresent()) {
            return sections;
        }
        else {
            throw new JSONException("Section not found");
        }
    }
    @PostMapping(path="/{BID}/add_new_user_to_board")
    public ModelAndView addNewUserToBoard(@PathVariable Integer BID, @RequestParam String user_email, @RequestParam MemberType user_type,RedirectAttributes redirectAttributes){
        Optional<User> userOptional=userRepository.findByEmail(user_email);
        Optional<Board> boardOptional=boardRepository.findById(BID);
        ModelAndView modelAndView=new ModelAndView();
        String redirect="redirect:/board/"+BID;
        modelAndView.setViewName(redirect);
        if(userOptional.isPresent()){
            User user=userOptional.get();
            Board board=boardOptional.get();
            if(!membershipRepository.existsByUserAndBoard(user,board)){
                Membership membership=new Membership();
                membership.setUser(user);
                membership.setBoard(board);
                membership.setMember_type(user_type);
                membershipRepository.save(membership);
                redirectAttributes.addFlashAttribute("message","Dodano użytkownika");
            }
            else{
                Optional<Membership> membershipOptional=membershipRepository.findByUserAndBoard(user,board);
                Membership membership=membershipOptional.get();
                membership.setMember_type(user_type);
                membershipRepository.save(membership);
                redirectAttributes.addFlashAttribute("message","Użytkownik już ma dostęp do tablicy, zaaktualizowano dla niego ustawienia");
            }
        }
        else{
            redirectAttributes.addFlashAttribute("message","Nie znaleziono użytkownika z tym mailem");
        }
        return modelAndView;
    }
    @PostMapping(path="/{BID}/delete_user")
    public ModelAndView DeleteUser(@PathVariable Integer BID, @RequestParam String user_email_to_delete,RedirectAttributes redirectAttributes){
        Optional<User> userOptional=userRepository.findByEmail(user_email_to_delete);
        Optional<Board> boardOptional=boardRepository.findById(BID);
        ModelAndView modelAndView=new ModelAndView();
        String redirect="redirect:/board/"+BID;
        modelAndView.setViewName(redirect);
        if(userOptional.isPresent()){
            User user=userOptional.get();
            Board board=boardOptional.get();
            if(membershipRepository.existsByUserAndBoard(user,board)){
                Membership membership=membershipRepository.findByUserAndBoard(user,board).get();
                membershipRepository.deleteById(membership.getId());
                redirectAttributes.addFlashAttribute("message","Skasowano użytkownika");
            }
            else{
                redirectAttributes.addFlashAttribute("message","Użytkownik nie ma dostępu do tablicy");
            }
        }
        else{
            redirectAttributes.addFlashAttribute("message","Nie znaleziono użytkownika z tym mailem");
        }
        return modelAndView;
    }
}
