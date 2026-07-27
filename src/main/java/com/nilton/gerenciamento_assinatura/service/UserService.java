package com.nilton.gerenciamento_assinatura.service;

import com.nilton.gerenciamento_assinatura.dto.UserDTO.UserCreateDTO;
import com.nilton.gerenciamento_assinatura.dto.UserDTO.UserLoginDTO;
import com.nilton.gerenciamento_assinatura.dto.UserDTO.UserUptadeDTO;
import com.nilton.gerenciamento_assinatura.model.User;
import com.nilton.gerenciamento_assinatura.repository.AssinaturaRepository;
import com.nilton.gerenciamento_assinatura.repository.HistoricoPagRepository;
import com.nilton.gerenciamento_assinatura.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssinaturaRepository assinaturaRepository;

    @Autowired
    private HistoricoPagRepository historicoRepository;

    private PasswordEncoder passwordEncoder;

    @Transactional
    public User userCreate(UserCreateDTO user){
        if(userRepository.findByUsername(user.username()).isPresent()){
            throw new RuntimeException("Esse username está em uso.");
        }


        if(userRepository.findByEmail(user.email()).isPresent()){
            throw new RuntimeException("Esse email está em uso.");
        }

        String senhaCriptografada = passwordEncoder.encode(user.senha());

        User novoUsuario = new User();

        novoUsuario.setEmail(user.email());
        novoUsuario.setUsername(user.username());
        novoUsuario.setSenha(senhaCriptografada);

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
            if(userRepository.findByUsername(uptadeDTO.username()).isPresent()){
                throw new RuntimeException("Esse username está sendo usado.");
            }
        }
        newUser.setUsername(uptadeDTO.username());

        if(!newUser.getEmail().equals(uptadeDTO.email())){
            if(userRepository.findByEmail(uptadeDTO.email()).isPresent()){
                throw new RuntimeException("Esse email está sendo usado");
            }
            newUser.setEmail(uptadeDTO.email());
        }

        if(newUser.getSenha().equals((uptadeDTO.senha()))){
            throw new RuntimeException("A nova senha deve ser diferente da senha atual.");

        }
        newUser.setSenha(uptadeDTO.senha());

        return userRepository.save(newUser);
    }



    public void userDelete(Long id){
       findByID(id);
       try {
           userRepository.deleteById(id);
       } catch (Exception e) {
           throw new RuntimeException(e);
       } // ajeitar esse delete
    }

    public User findByID(Long id){

        Optional<User> user = this.userRepository.findById(id);

        return user.orElseThrow(()-> new RuntimeException(
                "Usuário não encontrado! ID:" + id + ", Tipo:" + User.class.getName() ));

    }

    public User findByEmail(String email){
        Optional<User> user = this.userRepository.findByEmail(email);

        return user.orElseThrow(() -> new RuntimeException("Nenhum usuário encontrado com esse email:" +email));
    }

    public User findByUsername(String username){
        Optional<User> user = this.userRepository.findByUsername(username);

        return user.orElseThrow(() -> new RuntimeException("Nenhum usuário encontrado com esse username:" +username));
    }

}
