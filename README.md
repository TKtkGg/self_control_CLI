# self_control (CLI Application)

## プロジェクト概要

このアプリは、ユーザーごとの週間スケジュールとタスクを管理する Java のコンソールアプリケーションです。  
ログイン後に「今日の予定確認」「曜日指定での予定確認」「予定やタスクの追加・編集・削除」「タスク名検索」「ユーザー検索」「他ユーザーの予定閲覧といいね」を行えます。
また、自己紹介・職業・年齢などを設定できるプロフィール機能を備えており、ユーザー詳細画面でプロフィールを確認できます。
まずはコンソールで機能とデータ操作を検証しやすくするために作られており、`view / service / dao / model` に分けた構成で、Spring Boot × Next.js による Web アプリ化を見据えたデモ版として扱いやすい形になっています。

---

## 使用技術

コードから確認できる使用技術は次のとおりです。

- Java 17（`.classpath` と Maven のコンパイル設定は Java 17 を参照）
- Maven（`pom.xml`）
- PostgreSQL
- JDBC（`java.sql.*` と `DriverManager`）
- ScheduledExecutorService（アラームの定期実行）
- Java Time API（`LocalDate`, `LocalTime`, `DayOfWeek` など）
- Git（GitHub リポジトリとして管理）

---

## 主な機能

- **ユーザー登録**：ユーザーネーム・メールアドレス・パスワードを入力して `users` テーブルに登録します。  
- **ログイン**：メールアドレスとパスワード照合で認証し、成功時にセッションへユーザーを保持します。  
- **ログアウト**：現在のセッションユーザーをクリアしてログアウトします。  
- **セッション管理**：`SessionManager` でログイン中ユーザーを静的に保持・参照します。  
- **プロフィール機能**：自己紹介・職業・年齢を設定・編集でき、ユーザー詳細画面で表示します。新規ユーザー登録時には初期プロフィールを作成します。
- **スケジュール作成**：曜日を選び、タイトルとタスク情報を入力してスケジュールを作成します。  
- **スケジュール編集**：曜日ごとの既存スケジュールを選択し、タイトルやタスク内容を更新します。  
- **スケジュール削除**：曜日を指定してスケジュール全体を削除します。  
- **今日のスケジュール表示**：`LocalDate.now()` の曜日を使って当日のスケジュールを表示します。  
- **曜日指定でのスケジュール表示**：入力した曜日（月〜日）に対応するスケジュールを表示します。  
- **タスク追加**：既存または新規スケジュールに開始時刻・終了時刻・名前・メモ付きタスクを追加します。  
- **タスク編集**：対象タスクを番号選択して時間帯や内容を更新します。  
- **タスク削除**：曜日とタスク番号を指定して削除します。  
- **タスク検索**：タスク確認画面から、タスク名を部分一致で検索できます。
- **他ユーザー一覧表示**：登録済みユーザーを ID 順で一覧表示します。  
- **ユーザー検索**：ユーザー一覧画面から、ユーザーネームを部分一致・大文字小文字を区別せず検索できます。
- **他ユーザーのスケジュール閲覧**：他ユーザーを選択し、曜日ごとのスケジュールとタスクを閲覧します。  
- **いいね機能**：他ユーザーのスケジュールに対して `likes` テーブルへ登録します。  
- **いいね解除機能**：既存のいいねを `likes` テーブルから削除します。  
- **アラーム機能**：`ScheduledExecutorService` が 1 秒間隔で現在時刻とタスク開始時刻を照合し、一致時に通知を表示します。  

---

## 使い方

アプリ起動後の大まかな流れは次のとおりです。

1. ログインまたはサインアップを選択する  
2. 認証成功後、ホームメニューへ進む  
3. 自分のスケジュール確認（今日 / 曜日指定）を行う  
4. タスク名でタスクを検索する
5. スケジュール・タスクを追加 / 編集 / 削除する
6. プロフィール（自己紹介・職業・年齢）を編集する
7. ユーザー一覧でユーザーネームを検索し、他ユーザーの予定閲覧やいいね操作を行う
8. ログアウトして認証画面へ戻る
9. 必要に応じてアプリ終了を選択する

---

## セットアップ手順

### 1. 必要環境

- Java 17（`JavaSE-17`）
- Maven
- PostgreSQL

### 2. データベース作成

`DBConnection.java` では以下に接続する実装です。

- URL: `jdbc:postgresql://localhost:5432/self_control`
- USER: `self_control_user`
- PASSWORD: `postgres`

まず PostgreSQL 側でユーザーとデータベースを作成します。

```bash
psql -U postgres
CREATE ROLE self_control_user WITH LOGIN PASSWORD 'postgres';
CREATE DATABASE self_control OWNER self_control_user;
\q
```

次に `self_control` データベースへ接続し、DAO が参照しているカラムに合わせてテーブルを作成します。

```bash
psql -U self_control_user -d self_control

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  username VARCHAR(255),
  email VARCHAR(255),
  password VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE profiles (
  user_id INTEGER PRIMARY KEY REFERENCES users(id),
  bio TEXT,
  job VARCHAR(255),
  age INTEGER
);

CREATE TABLE schedules (
  id SERIAL PRIMARY KEY,
  user_id INTEGER,
  day_of_week INTEGER,
  title VARCHAR(255)
);

CREATE TABLE tasks (
  id SERIAL PRIMARY KEY,
  schedule_id INTEGER,
  start_time TIME,
  end_time TIME,
  task_name VARCHAR(255),
  memo TEXT
);

CREATE TABLE likes (
  id SERIAL PRIMARY KEY,
  user_id INTEGER,
  schedule_id INTEGER
);
\q
```

### 3. 接続設定の確認（`DBConnection.java`）

必要に応じて次のファイル内の接続情報を環境に合わせて変更してください。  

`src/main/java/com/tktkgg/self_control/util/DBConnection.java`

### 4. 依存ライブラリ取得とビルド

```bash
# self_control リポジトリのルートディレクトリで実行
mvn clean compile
```

### 5. 実行

```bash
mvn exec:java -Dexec.mainClass=com.tktkgg.self_control.Main
```

---

## ディレクトリ構成

`src/main/java` 以下の主要構成です。

```text
src/main/java/com/tktkgg/self_control
├── Main.java
├── alarm
│   ├── AlarmManager.java
│   └── AlarmTask.java
├── dao
│   ├── LikeDao.java
│   ├── ProfileDao.java
│   ├── ScheduleDao.java
│   ├── TaskDao.java
│   └── UserDao.java
├── exception
│   ├── DatabaseException.java
│   └── InvalidTimeException.java
├── model
│   ├── Like.java
│   ├── Profile.java
│   ├── Schedule.java
│   ├── Task.java
│   └── User.java
├── service
│   ├── AlarmService.java
│   ├── AuthService.java
│   ├── ConnectionService.java
│   ├── LikeService.java
│   ├── ProfileService.java
│   ├── ScheduleService.java
│   ├── ScheduleTaskService.java
│   ├── TaskService.java
│   └── UserService.java
├── util
│   ├── DBConnection.java
│   ├── Input.java
│   ├── InputUtils.java
│   └── SessionManager.java
└── view
    ├── AuthView.java
    ├── HomeView.java
    ├── LogoutView.java
    ├── MenuAction.java
    ├── ViewUtils.java
    ├── schedule
    │   ├── AddScheduleView.java
    │   ├── CheckScheduleView.java
    │   ├── DeleteScheduleTaskView.java
    │   ├── EditScheduleView.java
    │   └── ScheduleInputView.java
    └── user
        ├── EditProfileView.java
        ├── UserView.java
        └── UsersView.java
```

- **Main.java**：アプリ全体の起動・終了制御を行います。  
- **alarm**：定期実行でタスク開始時刻を監視し、通知を表示します。  
- **dao**：PostgreSQL への CRUD 操作を担当します。  
- **exception**：DB エラーや時刻入力エラーをアプリ内例外として扱います。  
- **model**：ユーザー・プロフィール・予定・タスク・いいねのデータ構造を定義します。
- **service**：認証、プロフィール、検索などの業務ロジックと DAO の連携処理を担当します。
- **util**：DB 接続、入力補助、セッション保持などの共通処理を提供します。  
- **view**：コンソール画面の入力/表示とメニュー遷移を担当します。  
- **view/schedule**：自分のスケジュールとタスクの表示・作成・編集・削除を扱います。  
- **view/user**：プロフィール編集、他ユーザーの一覧表示・検索・詳細表示・いいね操作を扱います。
