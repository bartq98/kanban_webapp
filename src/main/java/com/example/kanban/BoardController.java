package com.example.kanban;

import com.example.kanban.entities.boards.Board;
import com.example.kanban.entities.boards.BoardRepository;
import com.example.kanban.entities.membership.MembershipRepository;
import com.example.kanban.entities.task.Task;
import com.example.kanban.entities.task.TaskRepository;
import com.example.kanban.entities.user.User;
import com.example.kanban.entities.user.UserDetailsImpl;
import com.example.kanban.entities.user.UserRepository;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/{BID}/tasks", method = RequestMethod.GET)
    public ModelAndView TasksBoard(@PathVariable int BID,ModelAndView modelAndView,@AuthenticationPrincipal UserDetailsImpl principal){
        Optional<Board> boardOptional=boardRepository.findById(BID);
        Optional<User> userOptional = userRepository.findByEmail(principal.getEmail());
        if(boardOptional.isPresent() && userOptional.isPresent()){
            Board board=boardOptional.get();
            User user=userOptional.get();
            if(membershipRepository.existsByUserAndBoard(user,board)){
                Optional<Task[]> tasks=taskRepository.tasksFromBoard(BID);
                if(tasks.isPresent()) {
                    modelAndView.addObject("tasks", tasks.get());
                }
            }
            else{
                modelAndView.addObject("MembershipError","You don't have permissions for this board");
            }
        }
        else{
            modelAndView.addObject("MembershipError","Wrong User or Board");
        }
        modelAndView.setViewName("fragments/actions/BoardTasks");
        return modelAndView;
    }

    @RequestMapping(value = "/{SID}/tasks_from_sections", method = RequestMethod.GET)
    public ModelAndView TasksSections(@PathVariable int SID,ModelAndView modelAndView){
        Optional<Task[]> tasks=taskRepository.tasksFromSection(SID);
        if(tasks.isPresent()) {
            modelAndView.addObject("tasks", tasks.get());
        }
        else {
            modelAndView.addObject("TasksNotFound","You have 0 tasks");
        };
        modelAndView.setViewName("fragments/actions/BoardTasks");

        return modelAndView;
    }


}
