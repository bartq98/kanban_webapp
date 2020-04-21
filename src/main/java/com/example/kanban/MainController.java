package com.example.kanban;

import com.example.kanban.entities.ConfirmationToken.ConfirmationToken;
import com.example.kanban.entities.ConfirmationToken.ConfirmationTokenRepository;
import com.example.kanban.entities.ConfirmationToken.EmailSenderService;
import com.example.kanban.entities.Exceptions.EmailNotFoundResetPassword;
import com.example.kanban.entities.Exceptions.ObjectNotFoundException;
import com.example.kanban.entities.membership.Membership;
import com.example.kanban.entities.membership.MembershipRepository;
import com.example.kanban.entities.task.Task;
import com.example.kanban.entities.task.TaskRepository;
import com.example.kanban.entities.user.User;
import com.example.kanban.entities.user.UserDetailsImpl;
import com.example.kanban.entities.user.UserRepository;
import com.example.kanban.entities.boards.Board;
import com.example.kanban.entities.boards.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private EmailSenderService emailSenderService;


    @GetMapping(path = "/register")
    public String addNewUserForm(Model model) {

        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping(path = "/register") // Map ONLY POST Requests
    public String addNewUserSubmit(@Valid @ModelAttribute User user,
                                   BindingResult result,
                                   RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("register_fail", "Check if you have all fields");
            return "register";
        } else {
            if (userRepository.existsByUserName(user.getUserName())) {
                attributes.addFlashAttribute("register_fail", "User with that name already exists");
                return "redirect:/register";
            } else if (userRepository.existsByEmail(user.getEmail())) {
                attributes.addFlashAttribute("register_fail", "This email is already in use");
                return "redirect:/register";
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                attributes.addFlashAttribute("register_success", "Your registration was successful");
                return "redirect:/login";
            }
        }
    }

    @PostMapping(path = "/add_task") // Map ONLY POST Requests
    public @ResponseBody
    String addNewTask(
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


    @GetMapping(path = "/all_users")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        List<Membership> membershipList = userRepository.getAllMemberships(2);
        System.out.println("HEJ");
        for (Membership m : membershipList) {
            System.out.println(m.getUserId());
        }
        return userRepository.findAll();
    }

    @GetMapping(path = "/all_tasks")
    public @ResponseBody
    Iterable<Task> getAllTasks() {
        // This returns a JSON or XML with the users
        return taskRepository.findAll();
    }

    @PostMapping(path = "/add_board") // Map ONLY POST Requests
    public @ResponseBody
    String addNewBoard(@RequestParam String name
            , @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdAt
            , @RequestParam String slug) {
        Board n = new Board();
        n.setName(name);
        n.setCreated_at(createdAt);
        n.setSlug(slug);
        boardRepository.save(n);
        return "Saved";
    }

    @PostMapping(path = "/add_membership") // Map ONLY POST Requests
    public @ResponseBody
    String addNewMembership(@RequestParam Integer uid
            , @RequestParam Integer boardId) {
        Membership n = new Membership();

        Optional<User> users = userRepository.findById(uid);
        Optional<Board> board = boardRepository.findById(boardId);
        if (users.isPresent() && board.isPresent()) {
            n.setUserId(users.get());
            n.setBoardId(board.get());
            membershipRepository.save(n);
            return "Saved";
        } else return "Error";
    }

    @ModelAttribute("text")
    @GetMapping(path = "/hello")
    public @ResponseBody
    String Hello(@AuthenticationPrincipal UserDetailsImpl principal) {
        return userRepository.findByEmail(principal.getEmail()).get().getName();
    }

    @ModelAttribute("userinfo")
    @GetMapping(path = "/info")
    public @ResponseBody
    User Info(@AuthenticationPrincipal UserDetailsImpl principal) {
        return userRepository.findByEmail(principal.getEmail()).get();
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
    public ModelAndView displayResetPassword(ModelAndView modelAndView, User user) {
        modelAndView.addObject("user", user);
        modelAndView.setViewName("forgot-password");
        return modelAndView;
    }

    // Receive the address and send an email
    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    public String forgotUserPassword(ModelAndView modelAndView, User user, RedirectAttributes attributes) throws EmailNotFoundResetPassword {
        Optional<User> existingUserOptional = userRepository.findByEmail(user.getEmail());
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            ConfirmationToken confirmationToken = new ConfirmationToken(existingUser);
            confirmationTokenRepository.save(confirmationToken);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(existingUser.getEmail());
            mailMessage.setSubject("Complete Password Reset!");
            mailMessage.setFrom("szymon.barszcz99@gmail.com");
            mailMessage.setText("To complete the password reset process, please click here: "
                    + "http://localhost:8080/confirm-reset?token=" + confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);

        } else {
            /*attributes.addFlashAttribute("send_error", "Nie znaleziono adresu E-mail !");
            return "redirect:/forgot-password";*/
            throw new EmailNotFoundResetPassword("Nie znaleziono podanego adresu E-mail");
        }
        attributes.addFlashAttribute("send_success", "Link do zmiany hasła został wysłany na adres mail");
        return "redirect:/login";
    }

    @RequestMapping(value = "/confirm-reset", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView validateResetToken(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) throws ObjectNotFoundException {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null) {
            User user = userRepository.findByEmail(token.getUser().getEmail()).get();
            modelAndView.addObject("user", user);
            modelAndView.addObject("email", user.getEmail());
            modelAndView.addObject("token", confirmationToken);
            modelAndView.setViewName("resetPassword");
        } else {
            throw new ObjectNotFoundException("Token error");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public ModelAndView resetUserPassword(ModelAndView modelAndView, User user) {
        if (user.getEmail() != null) {

            User tokenUser = userRepository.findByEmail(user.getEmail()).get();
            tokenUser.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(tokenUser);

            confirmationTokenRepository.deleteByUser(tokenUser);
            modelAndView.addObject("reset_success", "Zmiana hasła się powiodła");
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

}