package com.example.kawashimashokudoapi.security;

import com.example.kawashimashokudoapi.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component // Springが管理するクラス
public class JwtService {

    // application.properties(application.yml)から秘密鍵を読み込む
    @Value("${jwt.secret}")
    private String secret;

    // JWTの有効期限(ミリ秒)を読み込む
    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    // 秘密鍵(String)からJWT署名用のSecretKeyを作成
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // JWT(トークン)を作成する
    public String generateToken(User user) {

        // 現在時刻
        Date now = new Date();

        // 有効期限 = 現在時刻 + expirationMs
        Date expiry = new Date(now.getTime() + expirationMs);

        // JWTを作成
        return Jwts.builder()

                // JWTのSubject(利用者)としてメールアドレスを保存
                .subject(user.getEmail())

                // 独自情報(UserID)を保存
                .claim("userId", user.getId())

                // 独自情報(Role)を保存
                .claim("role", user.getRole())

                // 独自情報(Name)を保存
                .claim("name", user.getName())

                // 発行日時
                .issuedAt(now)

                // 有効期限
                .expiration(expiry)

                // 秘密鍵で署名する(改ざん防止)
                .signWith(getSigningKey())

                // JWT文字列を完成させる
                .compact();
    }

    // JWTを解析して中身(Claims)を取得する
    public Claims parseToken(String token) {

        return Jwts.parser()

                // 署名を検証するための秘密鍵
                .verifyWith(getSigningKey())

                // パーサー作成
                .build()

                // JWTを解析
                .parseSignedClaims(token)

                // JWTの中身を取得
                .getPayload();
    }

    // JWTからメールアドレス(Subject)を取得
    public String extractEmail(String token) {
        return parseToken(token).getSubject();
    }

    // JWTからRoleを取得
    public String extractRole(String token) {
        return parseToken(token).get("role", String.class);
    }

    // JWTが有効かどうか確認
    public boolean isTokenValid(String token) {
        try {
            // 解析できればOK
            parseToken(token);
            return true;
        } catch (Exception e) {
            // 期限切れ・改ざんなどで解析できなければNG
            return false;
        }
    }
}