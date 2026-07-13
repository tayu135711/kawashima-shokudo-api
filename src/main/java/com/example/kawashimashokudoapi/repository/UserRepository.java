package com.example.kawashimashokudoapi.repository;

import com.example.kawashimashokudoapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // ログイン時にメールアドレスからユーザーを検索するため
    Optional<User> findByEmail(String email);

    // 新規登録時に「そのメールアドレスは既に使われてないか」チェックするため
    boolean existsByEmail(String email);
}
