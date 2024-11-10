# План та флоу роботи над проєктом

## Розподіл завдань:

### Augustin
**Моя зона відповідальності (User):**
- Add new User to the Application.
- Update / Delete / Read existing Users.

### Volodymyr (Task)
- Add new Task to an existing 'To-Do List'.
- Update / Delete / Read existing Tasks.

### Andrii (To-Do List)
- Add new 'To-Do List' to an existing User.
- Update / Delete / Read existing 'To-Do Lists'.

---

## Флоу роботи з Git:

### Основна гілка:
- Ми створюємо гілку `develop` від основної гілки `main`.
- Гілка `develop` є основною робочою гілкою, до якої будуть додаватися завершені й протестовані функціонали.

### Індивідуальні гілки:
- Кожен учасник створює окрему гілку від `develop` для роботи над своїм функціоналом. Наприклад:
  - Для роботи з користувачами: `feature/user-management`.
  - Для завдань: `feature/task-management`.
  - Для списків To-Do: `feature/todo-list-management`.

---

### Правила роботи з гілками:
1. У гілці `develop` **не працюємо безпосередньо**.
2. Усі зміни тестуються локально перед створенням **Pull Request**.
3. Гілки з робочим кодом мержаться у `develop` тільки після **рев'ю**.

---

### Завершення проєкту:
- Коли весь функціонал завершений і протестований, ми об'єднуємо (**merge**) гілку `develop` у `main`.
- Гілка `main` використовується для демонстрації готової програми.
