package com.blessingtree.service;

import com.blessingtree.components.JwtTokenUtil;
import com.blessingtree.dto.PasswordReminder;
import com.blessingtree.dto.PasswordReset;
import com.blessingtree.exceptions.ResourceNotFoundException;
import com.blessingtree.model.User;
import com.blessingtree.repository.UserRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import jakarta.persistence.EntityNotFoundException;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class PasswordService {
    private final UserRepository userRepository;

    private final TemplateEngine templateEngine;

    @Value("${sendgrid.api.key}")
    private String sendgridApiKey;

    @Value("${password.reminder.callback.url}")
    private String callBackUrl;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${email.from.address}")
    private String from_address = "support@blessing-tree.com";
    public PasswordService(@Autowired UserRepository userRepository,
                           @Autowired TemplateEngine templateEngine,
                           @Autowired PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.templateEngine = templateEngine;
        this.passwordEncoder = passwordEncoder;
    }

    public int sendEmailReminder(PasswordReminder passwordReminder){
        User user = new User();
        Optional<User> userOpt = userRepository.findUserByUsername(passwordReminder.getUsername());

        if(userOpt.isEmpty()){
            user.setEmailAddress(passwordReminder.getUsername());
            Example<User> userExample = Example.of(user);
            user = userRepository.findOne(userExample).orElseThrow(() -> new EntityNotFoundException("User not found"));
        }else{
            user = userOpt.get();
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        long expiration = 15 * 60;
        final String token = jwtTokenUtil.generateToken(userDetails, user.getUserId(), expiration);

        Map<String, Object> variables = new HashMap<>();
        variables.put("url", callBackUrl + token);
        variables.put("user", user);
        String passwordReminderTemplateName = "password-reminder.html";
        String subject = "Blessing Tree Password Reset";
        return sendEmail(variables, passwordReminderTemplateName, user.getEmailAddress(), subject);
    }

    public int resetPassword(PasswordReset passwordReset) {
        if(jwtTokenUtil.isTokenExpired(passwordReset.getToken())){
            return HttpStatus.SC_UNAUTHORIZED;
        }

        Long userID = jwtTokenUtil.getUserIDFromToken(passwordReset.getToken());

        if(userID == null){
            return HttpStatus.SC_NOT_FOUND;
        }

        User currentUser = userRepository.findUserByUserId(userID).orElseThrow(() -> new EntityNotFoundException("Not found"));
        Timestamp timestamp = Timestamp.from(Instant.now());
        currentUser.setModifiedDate(timestamp.toString());
        currentUser.setModifiedBy(null);
        currentUser.setPassword(passwordEncoder.encode(passwordReset.getPassword()));

        userRepository.save(currentUser);

        Map<String, Object> map = new HashMap<>();
        map.put("user", currentUser);
        String passwordConfirmationTemplateName = "password-confirmation.html";
        String subject = "Blessing Tree Password Confirmation";
        sendEmail(map, passwordConfirmationTemplateName, currentUser.getEmailAddress(), subject);

        return HttpStatus.SC_OK;
    }

    private int sendEmail(Map<String, Object> variables, String templateName, String toEmailAddress, String subject) {
        Email from = new Email(from_address);
        Email to = new Email(toEmailAddress);

        Context context = new Context();
        context.setVariables(variables);

        String processedText = templateEngine.process(templateName, context);

        Content content = new Content("text/html", processedText);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendgridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
            return response.getStatusCode();
        } catch (IOException ex) {
            throw new ResourceNotFoundException("Template not found");
        }
    }
}
