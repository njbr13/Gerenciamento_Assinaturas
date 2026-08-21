package com.nilton.gerenciamento_assinatura.service;

import com.nilton.gerenciamento_assinatura.dto.UserDTO.*;
import com.nilton.gerenciamento_assinatura.enums.Perfil;
import com.nilton.gerenciamento_assinatura.model.User;
import com.nilton.gerenciamento_assinatura.repository.AssinaturaRepository;
import com.nilton.gerenciamento_assinatura.repository.HistoricoPagRepository;
import com.nilton.gerenciamento_assinatura.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssinaturaRepository assinaturaRepository;

    @Autowired
    private HistoricoPagRepository historicoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Transactional
    public User userCreate(UserCreateDTO user){
        if(userRepository.findByNome(user.username()).isPresent()){
            throw new RuntimeException("Esse username está em uso.");
        }


        if(userRepository.findByEmail(user.email()).isPresent()){
            throw new RuntimeException("Esse email está em uso.");
        }

        String senhaCriptografada = passwordEncoder.encode(user.senha());

        User novoUsuario = new User();

        novoUsuario.setEmail(user.email());
        novoUsuario.setNome(user.username());
        novoUsuario.setSenha(senhaCriptografada);
        novoUsuario.setPerfil(Perfil.ROLE_USER);

       return userRepository.save(novoUsuario);

    }

    @Transactional
    public User userLogin(UserLoginDTO userLogin){
       User usuario = findByEmail(userLogin.email());

       if(!usuario.getSenha().equals(userLogin.senha())){
           throw new RuntimeException("Email ou senha estão incorretos. Tente novamente");
       }
       return usuario;
    }



    @Transactional
    public User userUpdate(Long id, UserUptadeDTO uptadeDTO){
        User newUser = this.findByID(id);
        if(!newUser.getUsername().equals(uptadeDTO.username())){
            if(userRepository.findByNome(uptadeDTO.username()).isPresent()){
                throw new RuntimeException("Esse username está sendo usado.");
            }
        }
        newUser.setNome(uptadeDTO.username());

        if(!newUser.getEmail().equals(uptadeDTO.email())){
            if(userRepository.findByEmail(uptadeDTO.email()).isPresent()){
                throw new RuntimeException("Esse email está sendo usado");
            }
            newUser.setEmail(uptadeDTO.email());
        }

        if(uptadeDTO.senha() != null && !uptadeDTO.senha().isBlank()){

            if(passwordEncoder.matches(uptadeDTO.senha(), newUser.getEmail())){
                throw new IllegalArgumentException("A nova Senha deve ser diferente da atual");
            }

            String novaSenhaCriptografada = passwordEncoder.encode(uptadeDTO.senha());
            newUser.setSenha(novaSenhaCriptografada);
        }

        return userRepository.save(newUser);
    }

    public User userTrocarSenhaLogado(Long id, UserTrocarSenhaLogadoDTO userLogado){
        User user = findByID(id);

        if(!passwordEncoder.matches(userLogado.senhaAntiga(), user.getSenha())){
            throw new RuntimeException("Senha Incorreta. Tente novamente");
        }


        String novaSenhaCriptografada = passwordEncoder.encode(userLogado.senhaNova());
        user.setSenha(novaSenhaCriptografada);

        return userRepository.save(user);

    }

    public void userSolicitarResetSenha(UserSolicitarResetDTO userReset){
        User user = findByEmail(userReset.email());

        String token = UUID.randomUUID().toString();
        LocalDateTime expiracao = LocalDateTime.now().plusMinutes(10);

        user.setResetToken(token);
        user.setExpiracaoToken(expiracao);
        userRepository.save(user);

        emailService.enviarEmail(userReset.email(), token);
    }

    public void userEsquecerSenha(UserRedefinirSenhaDTO userRedefinir){
        User user = findByResetToken(userRedefinir.token());

        if(user.getResetToken() == null || !user.getResetToken().equals(userRedefinir.token())){
            throw new RuntimeException("Token inválido. Tente Novamente");
        }

        if(user.getExpiracaoToken().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Token expirado. Tente novamente uma redefinição de senha");

        }
        if(passwordEncoder.matches(userRedefinir.novaSenha(), user.getSenha())){
            throw new RuntimeException("Senha igual a alguma das anteriores. Tente outra");
        }
        String senhaNovaCriptografada = passwordEncoder.encode(userRedefinir.novaSenha());

        user.setSenha(senhaNovaCriptografada);

        user.setResetToken(null);
        user.setExpiracaoToken(null);

        userRepository.save(user);
    }



    public void userDelete(Long id){
      User user =  findByID(id);
       try {
           userRepository.delete(user);
       } catch (Exception e) {
           throw new RuntimeException(e);
       } // ajeitar esse delete
    }

    public User findByID(Long id){

        Optional<User> user = this.userRepository.findById(id);

        return user.orElseThrow(()-> new RuntimeException(
                "Usuário não encontrado! ID:" + id + ", Tipo:" + User.class.getName() ));

    }

    public User findByResetToken(String token){
        Optional<User> user = this.userRepository.findByResetToken(token);

        return user.orElseThrow(() -> new RuntimeException("Token inválido ou não encontrado."));

    }

    public User findByEmail(String email){
        Optional<User> user = this.userRepository.findByEmail(email);

        return user.orElseThrow(() -> new RuntimeException("Nenhum usuário encontrado com esse email:" +email));
    }

    public User findByUsername(String username){
        Optional<User> user = this.userRepository.findByNome(username);

        return user.orElseThrow(() -> new RuntimeException("Nenhum usuário encontrado com esse username:" +username));
    }

}
