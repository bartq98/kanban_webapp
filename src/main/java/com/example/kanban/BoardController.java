package com.example.kanban;

import com.example.kanban.entities.Exceptions.JSONException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.Optional;

@Controller
@RequestMapping(path="/get-current-boards")
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


}
