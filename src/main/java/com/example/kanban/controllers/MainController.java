package com.example.kanban.controllers;

import com.example.kanban.entities.confirmationtoken.ConfirmationToken;
import com.example.kanban.entities.confirmationtoken.ConfirmationTokenRepository;
import com.example.kanban.entities.membership.MemberType;
import com.example.kanban.entities.membership.Membership;
import com.example.kanban.entities.sections.ColorType;
import com.example.kanban.entities.sections.Section;
import com.example.kanban.entities.sections.SectionRepository;
import com.example.kanban.services.EmailSenderService;
import com.example.kanban.exceptions.exceptions.EmailNotFoundResetPassword;
import com.example.kanban.exceptions.exceptions.ObjectNotFoundException;
import com.example.kanban.entities.membership.MembershipRepository;
import com.example.kanban.entities.task.Task;
import com.example.kanban.entities.task.TaskRepository;
import com.example.kanban.entities.user.User;
import com.example.kanban.entities.user.UserDetailsImpl;
import com.example.kanban.entities.user.UserRepository;
import com.example.kanban.entities.boards.Board;
import com.example.kanban.entities.boards.BoardRepository;
import com.example.kanban.services.ExceptionsFacade;
import com.example.kanban.services.Slugify;
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
import java.util.*;

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
    @Autowired
    private SectionRepository sectionRepository;


    @GetMapping(path = "/register")
    public String addNewUserForm(Model model) {
        Slugify slug = new Slugify();
        System.out.println(slug.parse("123-test   ASDAÓŁŻĆĘ       ńążśćłóę    hejo !!! #@#$_ ẞ ß"));

        com.github.slugify.Slugify slug2 = new com.github.slugify.Slugify();
        System.out.println(slug2.slugify("123-test     ASDAÓŁŻĆĘ     ńążśćłóę    hejo !!! #@#$_ ẞ ß"));

        model.addAttribute("user", new User());
        return "fragments/forms/register";
    }

    @PostMapping(path = "/register") // Map ONLY POST Requests
    public String addNewUserSubmit(@Valid @ModelAttribute User user,
                                   BindingResult result,
                                   RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("register_fail", "Check if you have all fields");
            return "fragments/forms/register";
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


    @RequestMapping(value="/forgot-password", method=RequestMethod.GET)
    public ModelAndView displayResetPassword(ModelAndView modelAndView, User user) {
        modelAndView.addObject("user", user);
        modelAndView.setViewName("fragments/forms/forgot-password");
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
            mailMessage.setFrom("test.kanban.996@gmail.com");
            mailMessage.setText("To complete the password reset process, please click here: "
                    + "http://localhost:8080/confirm-reset?token=" + confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);
            attributes.addFlashAttribute("send_success", "Link do zmiany hasła został wysłany na adres mail");
            return "redirect:/login";

        } else {
            ExceptionsFacade exceptionsFacade=new ExceptionsFacade();
            throw exceptionsFacade.throwEmailNotFoundResetPassword("Nie znaleziono podanego adresu E-mail");
            //throw new EmailNotFoundResetPassword("Nie znaleziono podanego adresu E-mail");

        }

    }

    @RequestMapping(value = "/confirm-reset", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView validateResetToken(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) throws ObjectNotFoundException {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null) {
            User user = userRepository.findByEmail(token.getUser().getEmail()).get();
            modelAndView.addObject("user", user);
            modelAndView.addObject("email", user.getEmail());
            modelAndView.setViewName("fragments/forms/reset-password");
            confirmationTokenRepository.deleteByConfirmationToken(confirmationToken);
        } else {
            modelAndView.addObject("link_error","Niepoprawny link");
            modelAndView.setViewName("fragments/forms/login");
            throw new ExceptionsFacade().throwObjectNotFoundException("Token error");
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
            modelAndView.setViewName("fragments/forms/login");
        }

        modelAndView.setViewName("fragments/forms/login");
        return modelAndView;
    }

    @GetMapping(path = "/add-new-board")
    public String addNewBoardForm(Model model) {
        model.addAttribute("board", new Board());
        model.addAttribute("sections", new Section());
        return "fragments/forms/add-new-board";
    }

    @PostMapping(path = "/add-new-board")
    public String addNewBoardSubmit(@Valid @ModelAttribute Board board,
                                    @Valid @ModelAttribute Membership membership,
                                    @Valid @ModelAttribute Section sections,
                                    @AuthenticationPrincipal UserDetailsImpl principal,
                                    BindingResult result,
                                    RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("create_board_fail", "Check if you have all fields");
            return "fragments/forms/add-new-board";
        } else {
            board.setCreated_at(LocalDateTime.now());
            Slugify slug = new Slugify();
            board.setSlug(slug.parse(board.getName()));
            boardRepository.save(board);

            User user = userRepository.findByEmail(principal.getEmail()).get();
            membership.setMember_type(MemberType.OWNER);
            membership.setBoardId(board);
            membership.setUserId(user);
            membershipRepository.save(membership);

            List<String> section_titles = Arrays.asList(sections.getTitle().split(","));;
            for(String title : section_titles){
                Section section = new Section();
                section.setTitle(title);
                section.setBoard(board);
                section.setColor(ColorType.BLUE_BASIC);
                section.setOrdering(1);
                sectionRepository.save(section);
            }
            attributes.addFlashAttribute("create_board_success", "You successfully added a new board!");
            return "redirect:/";
        }
    }
}
