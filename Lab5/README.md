# Lab 5 – User Registration Form (JavaFX MVC)

A small JavaFX desktop app that collects a user's **name, age and email**, validates the input, and shows the submitted details in a pop-up. The project follows the **Model–View–Controller (MVC)** pattern, with the View defined in FXML and styled with CSS.

## Features

- Form with **Name**, **Age** and **Email** fields, plus **Clear** and **Submit** buttons
- Input validation with an error message under each invalid field and a red border on it
- All errors are shown together, both inline and in an error pop-up
- Live re-validation: once a field shows an error, the error updates as you type
- Age field only accepts digits (up to 3)
- On a valid submit, a confirmation dialog shows the entered details and the form resets
- Enter triggers Submit (it is the default button)

## Validation Rules

| Field | Rules |
|-------|-------|
| Name  | Required, 2–50 characters; letters (any language), spaces, apostrophes, hyphens and dots only |
| Age   | Required, whole number between 1 and 120 |
| Email | Required, valid format such as `name@example.com`, no consecutive dots, at most 254 characters |

## Project Structure

```
Lab5/
├── src/
│   ├── app/
│   │   └── Main.java                  # Entry point, loads the FXML view
│   ├── controller/
│   │   └── UserFormController.java    # Controller: handles input, validation and dialogs
│   ├── model/
│   │   ├── User.java                  # Model: user data (name, age, email)
│   │   └── UserValidator.java         # Model: validation rules
│   └── view/
│       ├── UserForm.fxml              # View: form layout
│       └── style.css                  # View: styling
├── lab 5 output screenshots/          # Output screenshots
├── nbproject/                         # NetBeans project configuration
├── build.xml                          # Ant build script
└── manifest.mf
```

## How MVC Is Applied

- **Model** (`model/`): `User` holds the data; `UserValidator` contains the validation rules. It has no UI code, so it can be reused or tested on its own.
- **View** (`view/`): `UserForm.fxml` describes the layout and `style.css` the appearance. It can be edited visually in Scene Builder.
- **Controller** (`controller/`): `UserFormController` reads the input from the View, asks the Model to validate it, and then either shows the errors or creates a `User` and displays it.

## Requirements

- JDK 24 or later
- JavaFX SDK (built with OpenJFX 27)
- Apache NetBeans (or Ant)

## How to Run

### NetBeans

1. Open the `Lab5` folder as a project in NetBeans.
2. In **Project Properties → Libraries**, point the JavaFX jars (`javafx.base`, `javafx.controls`, `javafx.fxml`, `javafx.graphics`) to your JavaFX SDK `lib` folder.
3. In **Project Properties → Run → VM Options**, set:
   ```
   --module-path "PATH_TO_JAVAFX_SDK/lib" --add-modules javafx.controls,javafx.fxml
   ```
4. Run the project (main class: `app.Main`).

### Command Line

```bash
javac --module-path "PATH_TO_JAVAFX_SDK/lib" --add-modules javafx.controls,javafx.fxml -d out src/app/*.java src/controller/*.java src/model/*.java
```

Copy `src/view/` into `out/view/`, then run:

```bash
java --module-path "PATH_TO_JAVAFX_SDK/lib" --add-modules javafx.controls,javafx.fxml -cp out app.Main
```

## Output

Submitting valid details shows a confirmation dialog with the entered name, age and email:

![Details Submitted](lab%205%20output%20screenshots/Screenshot%202026-10-19%20181121.png)

## Author

Girish Raja Thiyagarajan
