# kawashima-shokudo-api

UberEats風のフードデリバリーアプリ「川島食堂」のバックエンドAPI。
Spring Boot + PostgreSQL(Neon)で構築。customer / store / courier / admin の4ロールが関わる、注文の状態遷移を中心とした業務システムを想定して設計。

フロントエンド(customer/store/courier/admin 全11画面)は別リポジトリ [`kawashima-shokudo-app`](https://github.com/tayu135711/kawashima-shokudo-app) で実装。

## 実装状況

設計(仕様書)は業務要件〜DB設計まで全8シート完了。実装は認証基盤までで区切りをつけている。

| # | タスク | 状態 |
|---|---|---|
| 1 | JWT認証(登録・ログイン・ロール別アクセス制御) | ✅ 完了 |
| 2 | Store/Menu CRUD | 設計のみ・実装は見送り |
| 3 | 注文・決済フロー(KOMOJU連携・状態遷移) | 設計のみ・実装は見送り |
| 4 | WebSocketでのリアルタイム通知 | 設計のみ・実装は見送り |

進捗はGitHub Issuesで管理: [Issues一覧](https://github.com/tayu135711/kawashima-shokudo-api/issues)

## 実装済み機能(Issue #1)

- ユーザー登録・ログイン(`POST /api/auth/register`, `POST /api/auth/login`)
- BCryptによるパスワードハッシュ化
- JWTの発行・検証(`io.jsonwebtoken` / jjwt 0.12.6)
- ロールベースのアクセス制御(customer / store / courier / admin、Spring Security)
- 管理者(admin)ロールの自己登録禁止
- 入力バリデーション(Bean Validation)とエラーメッセージの共通化
- CORS設定(フロントエンドとの連携用)

## 設計ドキュメント

`docs/spec.xlsx` に業務要件・機能一覧・画面一覧・状態遷移図・権限表・シーケンス図・API設計・DB設計の8シートをまとめている。特に状態遷移図は、注文ステータス(`pending_payment → ordered → accepted → preparing → ready → assigned → delivered`)の設計根拠。

## 技術スタック

- Java 21 / Spring Boot 4.1
- Spring Data JPA / Spring Security
- PostgreSQL(Neon、サーバーレス)
- JWT(jjwt)
- Docker(Renderへのデプロイ用)

## エンティティ設計

User / Store / Menu / Order / OrderItem / Cart / CartItem / Payment の8エンティティ。外部キー・ユニーク制約・インデックスまで設計し、Hibernateの自動DDLで反映を確認済み。

## セットアップ(ローカル開発)

機密情報は環境変数化しており、リポジトリには含まれない。

1. `src/main/resources/application-local.properties` を作成し、以下を設定:
   ```properties
   spring.datasource.url=jdbc:postgresql://<Neonのホスト名>/neondb?sslmode=require
   spring.datasource.username=<ユーザー名>
   spring.datasource.password=<パスワード>
   jwt.secret=<ランダムな64バイト以上の文字列>
   jwt.expiration-ms=86400000
   ```
2. `--spring.profiles.active=local` を付けて起動

## デプロイ

Render(Docker)+ Neon PostgreSQL。環境変数(`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`, `JWT_EXPIRATION_MS`)をRender側のEnvironmentタブで設定。
