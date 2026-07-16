package com.example.kawashimashokudoapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "お名前を入力してください")
    private String name;

    @NotBlank(message = "メールアドレスを入力してください")
    @Email(message = "メールアドレスの形式が正しくありません")
    private String email;

    // BCryptは72バイトを超える部分を無視してしまうため、上限を設定
    @NotBlank(message = "パスワードを入力してください")
    @Size(min = 8, max = 72, message = "パスワードは8〜72文字で入力してください")
    private String password;

    private String phone;

    @NotBlank(message = "roleを指定してください")
    private String role; // customer / store / courier / admin
}
