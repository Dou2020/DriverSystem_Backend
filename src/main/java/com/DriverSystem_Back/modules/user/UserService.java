package com.DriverSystem_Back.modules.user;

import com.DriverSystem_Back.modules.Userrole.UserRoleRequest;
import com.DriverSystem_Back.modules.Userrole.UserRoleService;
import com.DriverSystem_Back.modules.role.RoleRepository;

import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.Userrole.UserRole;
import com.DriverSystem_Back.modules.Userrole.UserRoleRepository;
import com.DriverSystem_Back.modules.authentication.repository.SessionUserCodeRepository;
import com.DriverSystem_Back.modules.authentication.repository.entity.SessionUserCode;
import com.DriverSystem_Back.modules.user.dto.*;
import com.DriverSystem_Back.modules.user.useravailability.Availability;
import com.DriverSystem_Back.modules.user.useravailability.AvailabilityRepository;
import com.DriverSystem_Back.modules.users.repository.entities.Users;
import com.DriverSystem_Back.modules.authentication.repository.entity.MfaType;
import com.DriverSystem_Back.properties.EmailProperties;
import com.DriverSystem_Back.utils.CodeUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private UserRepository userRepository;
    // reset Password
    private final SessionUserCodeRepository sessionUserCodeRepository;

    private  ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private ResetTokenRepository resetTokenRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    public UserService(UserRepository userRepository, SessionUserCodeRepository sessionUserCodeRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.sessionUserCodeRepository = sessionUserCodeRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;

        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
    }
    @Override
    public UserResponse getUserById(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        String roleName = null;
        var userRoleOpt = userRoleRepository.findFirstByUserId(user.getId());
        if (userRoleOpt.isPresent()) {
            var roleId = userRoleOpt.get().getRoleId();
            var roleOpt = roleRepository.findById(roleId);
            if (roleOpt.isPresent()) {
                roleName = roleOpt.get().getName();
            }
        }
        return new com.DriverSystem_Back.modules.user.dto.UserResponse(user, roleName);
    }


    @Override
    public UserResponse saveUser(UserRequest request) {
        User user= this.modelMapper.map(request, User.class);
        user.setIs_active(false);
        user.setCreated_at(OffsetDateTime.now());
        user.setPasswordHash(passwordEncoder.encode(request.passwordHash()));
        User saved = this.userRepository.save(user);
        String roleName = null;
        if (request.role() != null) {
            var roleOpt = roleRepository.findById(request.role());
            if (roleOpt.isPresent()) {
                roleName = roleOpt.get().getName();
            }
        }
        UserResponse response = new com.DriverSystem_Back.modules.user.dto.UserResponse(saved, roleName);
        this.userRoleRepository.save(new UserRole(response.id(), request.role()));
        return response;
    }

    @Override
    public void deleteUserById(Long id) {

    }

    @Override
    public UserResponse updateUser(UserUpdateRequest request) {
        User existingUser = this.userRepository.findById(request.id())
                .orElseThrow(() -> new HttpException("Usuario no encontrado", HttpStatus.NOT_FOUND));

        if (this.userRepository.existsByEmailAndIdNot(request.email(), request.id())) {
            throw new HttpException("El email ya está en uso", HttpStatus.BAD_REQUEST);
        }

        if (this.userRepository.existsByUserNameAndIdNot(request.userName(), request.id())) {
            throw new HttpException("El nombre de usuario ya está en uso", HttpStatus.BAD_REQUEST);
        }

        if (this.userRepository.existsByPhoneNumberAndIdNot(request.phoneNumber(), request.id())) {
            throw new HttpException("El teléfono ya está en uso", HttpStatus.BAD_REQUEST);
        }

        existingUser.setName(request.name());
        existingUser.setEmail(request.email());
        existingUser.setUserName(request.userName());
        existingUser.setPhoneNumber(request.phoneNumber());
        existingUser.setDocNumber(request.docNumber());
        existingUser.setDocType(request.docType());
        User saved = this.userRepository.save(existingUser);

        UserRoleRequest userRoleRequest = new UserRoleRequest( request.role(),request.id());
        this.userRoleService.updateRole(userRoleRequest);

        String roleName = null;
        var userRoleOpt = userRoleRepository.findFirstByUserId(saved.getId());

        if (userRoleOpt.isPresent()) {
            var roleId = userRoleOpt.get().getRoleId();
            var roleOpt = roleRepository.findById(roleId);
            if (roleOpt.isPresent()) {
                roleName = roleOpt.get().getName();
            }
        }
        return new com.DriverSystem_Back.modules.user.dto.UserResponse(saved,  roleName);

    }

    @Override
    public List<UserResponse> getAllUsers() {
        return this.userRepository.findAll().stream().map(user -> {
            String roleName = null;
            var userRoleOpt = userRoleRepository.findFirstByUserId(user.getId());
            if (userRoleOpt.isPresent()) {
                var roleId = userRoleOpt.get().getRoleId();
                var roleOpt = roleRepository.findById(roleId);
                if (roleOpt.isPresent()) {
                    roleName = roleOpt.get().getName();
                }
            }
            return new com.DriverSystem_Back.modules.user.dto.UserResponse(user, roleName);
        }).toList();
    }


    @Override
    public UserActiveUser updateActiveUser(UserActiveUser user) {
        User existingUser = this.userRepository.findById(user.id())
                .orElseThrow(() ->  new  HttpException("Usuario no encontrado", HttpStatus.NOT_FOUND));
        if (existingUser.getIs_active().equals(user.state()))
            throw new HttpException("El usuario ya tiene el estado indicado!", HttpStatus.UNPROCESSABLE_ENTITY);
        existingUser.setIs_active(user.state());
        this.userRepository.save(existingUser);
        return new UserActiveUser(existingUser.getId(), existingUser.getIs_active());
    }

    @Override
    public UserSendEmail updateSendEmail(UserSendEmail user) {
        User existingUser = this.userRepository.findById(user.id())
                .orElseThrow(() ->  new  HttpException("Usuario no encontrado", HttpStatus.NOT_FOUND));

        if (existingUser.getUsaMfa().equals(user.state()))
            throw new HttpException("El usuario ya tiene el estado indicado!", HttpStatus.UNPROCESSABLE_ENTITY);
        existingUser.setUsaMfa(user.state());
        this.userRepository.save(existingUser);
        return new UserSendEmail(existingUser.getId(), existingUser.getIs_active());
    }

    @Override
    public UserResetPassword updatePassword(UserResetPassword user) {
        User existingUser = null;

        // Primero buscar en reset_tokens (recuperación de contraseña)
        Optional<ResetToken> resetTokenOptional = resetTokenRepository.findByToken(user.code());
        if (resetTokenOptional.isPresent()) {
            ResetToken resetToken = resetTokenOptional.get();

            if (resetToken.isUsed()) {
                throw new HttpException("El código ya fue utilizado", HttpStatus.BAD_REQUEST);
            }

            if (resetToken.isExpired()) {
                throw new HttpException("El código expiró", HttpStatus.BAD_REQUEST);
            }

            // Buscar usuario por email
            existingUser = this.userRepository.findByEmail(resetToken.getEmail())
                    .orElseThrow(() -> new HttpException("Usuario no encontrado", HttpStatus.NOT_FOUND));

            // Marcar el token como usado
            resetToken.setUsed(true);
            resetTokenRepository.save(resetToken);
        } else {
            // Si no está en reset_tokens, buscar en user_mfa (MFA)
            Optional<SessionUserCode> userCodeOptional = sessionUserCodeRepository.findByCode(user.code());

            if (userCodeOptional.isEmpty()) {
                throw new HttpException("El código es inválido", HttpStatus.NOT_FOUND);
            }

            SessionUserCode sessionUserCode = userCodeOptional.get();

            if (sessionUserCode.getTsExpired().isBefore(OffsetDateTime.now())) {
                throw new HttpException("El código expiró", HttpStatus.BAD_REQUEST);
            }

            // Buscar usuario por ID
            existingUser = this.userRepository.findById(sessionUserCode.getUser().getId())
                    .orElseThrow(() -> new HttpException("Usuario no encontrado", HttpStatus.NOT_FOUND));
        }

        // Actualizar la contraseña
        existingUser.setPasswordHash(passwordEncoder.encode(user.newPassword()));
        this.userRepository.save(existingUser);

        return new UserResetPassword(user.code(), user.newPassword());
    }

    @Override
    public UserSendEmail sendCodePassword(UserSendEmail user) {
        // Buscar el usuario por ID
        User existingUser = this.userRepository.findById(user.id())
                .orElseThrow(() -> new HttpException("Usuario no encontrado", HttpStatus.NOT_FOUND));

        // Generar código de verificación
        String verificationCode = CodeUtils.generateVerificationCode();

        // Crear SessionUserCode para almacenar el código
        SessionUserCode sessionUserCode = new SessionUserCode();
        sessionUserCode.setCode(verificationCode);

        // Crear Users entity para la relación (mapeo manual ya que Users y User son similares)
        Users usersEntity = new Users();
        usersEntity.setId(existingUser.getId());
        usersEntity.setEmail(existingUser.getEmail());
        usersEntity.setUserName(existingUser.getUserName());

        sessionUserCode.setUser(usersEntity);
        sessionUserCode.setMfaType(MfaType.EMAIL);
        sessionUserCode.setEnabled(true);
        sessionUserCode.setTsExpired(OffsetDateTime.now().plusMinutes(5)); // Expira en 5 minutos

        // Guardar el código en la base de datos
        this.sessionUserCodeRepository.saveSessionUser(sessionUserCode);

        // Enviar email con el código
        sendPasswordResetCodeEmail(existingUser.getEmail(), verificationCode);

        return new UserSendEmail(existingUser.getId(), true);
    }

    public  Optional<User> validateUser(Long id){
        Optional<User> optional = this.userRepository.findById(id);
        if (optional.isEmpty())
            throw new HttpException("Usuario no encontrado!!", HttpStatus.NOT_FOUND);
        return optional;
    }

    @Override
    public UserResponse  getUserDocType(String docNumer){
        User user = this.userRepository.findByDocNumber(docNumer)
                .orElseThrow(() -> new HttpException("Usuario no encontrado!!", HttpStatus.NOT_FOUND));
        String roleName = null;
        var userRoleOpt = userRoleRepository.findFirstByUserId(user.getId());
        if (userRoleOpt.isPresent()) {
            var roleId = userRoleOpt.get().getRoleId();
            var roleOpt = roleRepository.findById(roleId);
            if (roleOpt.isPresent()) {
                roleName = roleOpt.get().getName();
            }
        }
        return new com.DriverSystem_Back.modules.user.dto.UserResponse(user, roleName);

    }

    private void sendPasswordResetCodeEmail(String email, String code) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Código de recuperación de contraseña - DriverSystem");
            message.setText("Tu código de recuperación de contraseña es: " + code +
                          "\n\nEste código expirará en 5 minutos." +
                          "\n\nSi no solicitaste este código, ignora este mensaje.");
            message.setFrom(emailProperties.getUsername());

            mailSender.send(message);
        } catch (Exception e) {
            throw new HttpException("No se pudo enviar el correo: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Availability> getEmployee() {
        return this.availabilityRepository.findByAvailability(true);
    }


}
