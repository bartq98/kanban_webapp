package com.example.kanban;

import com.example.kanban.entities.membership.Membership;
import com.example.kanban.entities.membership.MembershipRepository;
import com.example.kanban.entities.task.Task;
import com.example.kanban.entities.task.TaskRepository;
import com.example.kanban.entities.user.User;
import com.example.kanban.entities.user.UserRepository;
import com.example.kanban.entities.boards.Board;
import com.example.kanban.entities.boards.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/")
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MembershipRepository membershipRepository;

    @GetMapping(path = "/register")
    public String addNewUserForm(Model model) {

        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping(path="/register") // Map ONLY POST Requests
    public String addNewUserSubmit (@Valid @ModelAttribute User user,
                                          BindingResult result,
                                          RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("register_fail", "Check if you have all fields");

            // todo: Improve this, because for now after redirection template doesn't show any error message from fields
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.User", result);
            attributes.addFlashAttribute("User", user);
            return "redirect:/register";
        } else {
            userRepository.save(user);
            attributes.addFlashAttribute("register_success", "Your registration was successful");
            return "redirect:/login";
        }
    }


    @PostMapping(path="/add_task") // Map ONLY POST Requests
    public @ResponseBody String addNewTask (
            @RequestParam Integer column_id
            , @RequestParam Integer executive_id
            , @RequestParam String title
            , @RequestParam String description) {

        Task t = new Task();
        t.setColumn_id(column_id);
        t.setExecutive_id(executive_id);
        t.setCreated_at();
        t.setExpires_at(LocalDateTime.now().plusDays(1));
        t.setTitle(title);
        t.setDescription(description);
        taskRepository.save(t);
        return "Saved";
    }


    @GetMapping(path="/all_users")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        List<Membership> membershipList = userRepository.getAllMemberships(2);
        System.out.println("HEJ");
        for (Membership m : membershipList) {
            System.out.println(m.getUserId());
        }
        return userRepository.findAll();
    }

    @GetMapping(path="/all_tasks")
    public @ResponseBody Iterable<Task> getAllTasks() {
        // This returns a JSON or XML with the users
        return taskRepository.findAll();
    }
    @PostMapping(path="/add_board") // Map ONLY POST Requests
    public @ResponseBody String addNewBoard (@RequestParam String name
            , @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdAt
            , @RequestParam String slug) {
        Board n = new Board();
        n.setName(name);
        n.setCreated_at(createdAt);
        n.setSlug(slug);
        boardRepository.save(n);
        return "Saved";
    }
    @PostMapping(path="/add_membership") // Map ONLY POST Requests
    public @ResponseBody String addNewMembership (@RequestParam Integer uid
            , @RequestParam Integer boardId) {
        Membership n = new Membership();

        Optional<User> users=userRepository.findById(uid);
        Optional<Board> board=boardRepository.findById(boardId);
        if(users.isPresent() && board.isPresent()){
            n.setUserId(users.get());
            n.setBoardId(board.get());
            membershipRepository.save(n);
            return "Saved";
        }
        else return "Error";
    }
}