package com.lucas.sistema.controllers;

import com.lucas.sistema.model.User;
import com.lucas.sistema.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Indica que esta classe é um controlador REST
@RequestMapping("/api/users") // Mapeia as requisições para /api/users para este controlador
public class UserController {

    private final UserRepository userRepository;

    @Autowired // Injeta a dependência do UserRepository
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping // Mapeia requisições GET para este método
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userRepository.findAll(); // Busca todos os usuários no repositório
        return new ResponseEntity<>(users, HttpStatus.OK); // Retorna a lista de usuários com status 200 OK
    }

    @GetMapping("/{id}") // Mapeia requisições GET com um ID para este método
    public ResponseEntity<User> findById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id); // Busca um usuário pelo ID
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)) // Se encontrado, retorna o usuário com status 200 OK
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Se não encontrado, retorna status 404 Not Found
    }

    @PostMapping // Mapeia requisições POST para este método
    public ResponseEntity<User> insert(@RequestBody User user) {
        User savedUser = userRepository.save(user); // Salva o novo usuário no repositório
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED); // Retorna o usuário salvo com status 201 Created
    }

    @PutMapping("/{id}") // Mapeia requisições PUT com um ID para este método
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        if (!userRepository.existsById(id)) { // Verifica se o usuário existe
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Se não encontrado, retorna status 404 Not Found
        }
        user.setId(id); // Define o ID do usuário
        User updatedUser = userRepository.save(user); // Atualiza o usuário no repositório
        return new ResponseEntity<>(updatedUser, HttpStatus.OK); // Retorna o usuário atualizado com status 200 OK
    }

    @DeleteMapping("/{id}") // Mapeia requisições DELETE com um ID para este método
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!userRepository.existsById(id)) { // Verifica se o usuário existe
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Se não encontrado, retorna status 404 Not Found
        }
        userRepository.deleteById(id); // Deleta o usuário pelo ID
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna status 204 No Content
    }

    @GetMapping("/searchName")
    public ResponseEntity<List<User>> findByName(@RequestParam String name) {
        List<User> users = userRepository.findByNameContainingIgnoreCase(name); // Busca usuários por nome ignorando maiúsculas e minúsculas
        return new ResponseEntity<>(users, HttpStatus.OK); // Retorna a lista de usuários com status 200 OK
    }
}