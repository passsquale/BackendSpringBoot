package ru.roslyackov.springboot.auth.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import java.util.concurrent.Future;

@Service
@Log
public class EmailService {

    @Value("${client.url}")
    private String clientURL;

    @Value("${email.from}")
    private String emailFrom;

    private JavaMailSender sender;

    @Autowired
    public EmailService(JavaMailSender sender) {
        this.sender = sender;
    }
    @Async
    public Future<Boolean> sendActivationEmail(String email, String username, String uuid) {
        try {
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "utf-8");


            String url = clientURL + "/activate-account/" + uuid;


            // текст письма в формате HTML
            String htmlMsg = String.format(
                    "Здравствуйте.<br/><br/>" +
                            "Вы создали аккаунт для веб приложения \"Планировщик дел\": %s <br/><br/>" +
                            "<a href='%s'>%s</a><br/><br/>",  username, url, "Для подтверждения регистрации нажмите на эту ссылку"); // вместо %s будет подставляться значение в порядке следования

            mimeMessage.setContent(htmlMsg, "text/html");

            message.setTo(email); // email получателя
            message.setFrom(emailFrom); // обратный адрес
            message.setSubject("Требуется активация аккаунта"); // тема
            message.setText(htmlMsg, true);
            sender.send(mimeMessage); // отправка

            return new AsyncResult<>(true); // true - успешная отправка, оборачиваем результат в спец. объект AsyncResult, чтобы результат вернулся после выполнение параллельного потока

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return new AsyncResult<>(false); // false - отправка не удалась, оборачиваем результат в спец. объект AsyncResult, чтобы результат вернулся после выполнение параллельного потока

    }

    @Async
    public Future<Boolean> sendResetPasswordEmail(String email, String token) {
        try {
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "utf-8");

            String url = clientURL + "/update-password/" + token;


            String htmlMsg = String.format(
                    "Здравствуйте.<br/><br/>" +
                            "Кто-то запросил сброс пароля для веб приложения \"Планировщик дел\".<br/><br/>" +
                            "Если это были не вы - просто удалите это письмо.<br/><br/> Нажмите на ссылку ниже, если хотите сбросить пароль: <br/><br/> " +
                            "<a href='%s'>%s</a><br/><br/>", url, "Сбросить пароль"); // вместо %s будет подставляться значение в порядке следования

            mimeMessage.setContent(htmlMsg, "text/html");

            message.setTo(email);
            message.setSubject("Сброс пароля");
            message.setFrom(emailFrom);
            message.setText(htmlMsg, true);
            sender.send(mimeMessage);

            return new AsyncResult<>(true);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return new AsyncResult<>(false);
    }
}
