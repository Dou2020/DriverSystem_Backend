package com.DriverSystem_Back.modules.user;

import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.utils.CodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PasswordResetService {

    @Autowired
    private ResetTokenRepository resetTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final int CODE_EXPIRY_MINUTES = 15;

    @Transactional
    public void initiatePasswordReset(String email) {
        // Verificar si el usuario existe
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new HttpException("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }

        // Limpiar tokens expirados
        resetTokenRepository.deleteExpiredTokens(LocalDateTime.now());

        // Invalidar tokens anteriores para este email
        Optional<ResetToken> existingToken = resetTokenRepository.findByEmailAndUsedFalse(email);
        existingToken.ifPresent(token -> {
            token.setUsed(true);
            resetTokenRepository.save(token);
        });

        // Generar código de verificación corto
        String resetCode = CodeUtils.generateVerificationCode();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(CODE_EXPIRY_MINUTES);

        ResetToken resetToken = new ResetToken();
        resetToken.setToken(resetCode);
        resetToken.setEmail(email);
        resetToken.setExpiryDate(expiryDate);
        resetToken.setUsed(false);

        resetTokenRepository.save(resetToken);

        // Enviar email con el código
        sendPasswordResetCodeEmail(email, resetCode);
    }

    @Transactional
    public void resetPassword(String code, String newPassword) {
        // Buscar código
        Optional<ResetToken> resetTokenOptional = resetTokenRepository.findByToken(code);
        if (resetTokenOptional.isEmpty()) {
            throw new HttpException("Código inválido", HttpStatus.BAD_REQUEST);
        }

        ResetToken resetToken = resetTokenOptional.get();

        // Verificar si el código ya fue usado
        if (resetToken.isUsed()) {
            throw new HttpException("Código ya utilizado", HttpStatus.BAD_REQUEST);
        }

        // Verificar si el código expiró
        if (resetToken.isExpired()) {
            throw new HttpException("Código expirado", HttpStatus.BAD_REQUEST);
        }

        // Buscar usuario
        Optional<User> userOptional = userRepository.findByEmail(resetToken.getEmail());
        if (userOptional.isEmpty()) {
            throw new HttpException("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();

        // Actualizar contraseña
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Marcar código como usado
        resetTokenRepository.markTokenAsUsed(code);
    }

    private void sendPasswordResetCodeEmail(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Código de Recuperación de Contraseña - DriverSystem");
        message.setText("Hola,\n\n" +
                      "Hemos recibido una solicitud para restablecer la contraseña de tu cuenta en DriverSystem.\n\n" +
                      "Tu código de recuperación es: " + code + "\n\n" +
                      "Este código expirará en " + CODE_EXPIRY_MINUTES + " minutos.\n\n" +
                      "Para restablecer tu contraseña:\n" +
                      "1. Ve a la aplicación DriverSystem\n" +
                      "2. Ingresa este código y tu nueva contraseña\n\n" +
                      "Si no solicitaste este cambio, ignora este mensaje.\n\n" +
                      "Atentamente,\n" +
                      "El equipo de DriverSystem");

        mailSender.send(message);
    }
}
