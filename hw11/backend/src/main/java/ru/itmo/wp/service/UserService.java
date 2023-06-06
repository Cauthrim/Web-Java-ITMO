package ru.itmo.wp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.PostForm;
import ru.itmo.wp.form.RegisterCredentials;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.repository.PostRepository;
import ru.itmo.wp.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public UserService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public User findByLoginAndPassword(String login, String password) {
        return login == null || password == null ? null : userRepository.findByLoginAndPassword(login, password);
    }

    public User findByLogin(String login) {
        return login == null ? null : userRepository.findByLogin(login);
    }

    public User findById(Long id) {
        return id == null ? null : userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAllByOrderByIdDesc();
    }

    public User register(RegisterCredentials registerForm) {
        User user = new User();
        user.setLogin(registerForm.getLogin());
        user.setName(registerForm.getName());
        userRepository.save(user);
        userRepository.updatePasswordSha(user.getId(), registerForm.getLogin(), "baka");
        return user;
    }

    public void writePost(PostForm postForm) {
        Post post = new Post();
        User user = findById(postForm.getUserId());
        post.setTitle(postForm.getTitle());
        post.setText(postForm.getText());
        post.setUser(user);
        user.addPost(post);
        postRepository.save(post);
        userRepository.save(user);
    }
}
