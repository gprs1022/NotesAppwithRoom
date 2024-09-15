# NoteApp with Room and Google Sign-In 🎇📝📔

A simple Android app that allows users to log in using Google Sign-In and manage their personal notes. Each logged-in user can view, add, update, and delete their own notes, which are stored in a Room database.

## Features

- **Google Sign-In**: Users can log in using their Google account.
- **Multi-User Support**: Each user can only see their own notes after logging in.
- **Room Database**: Local data storage using Room, with support for migrations.
- **RecyclerView**: Displays a list of notes for the logged-in user.
- **Add, Update, and Delete Notes**: CRUD operations are supported for managing user notes.
- **Single Activity with Multiple Fragments**: The app uses one activity and multiple fragments for different screens.

## Screenshots
![ss](https://github.com/user-attachments/assets/9ce9515d-fbc3-433f-bb42-cf33ea903941)
![ddfsd](https://github.com/user-attachments/assets/96a3b2a9-6a8b-4a54-a89d-3968746ae08a)



## Prerequisites

- Android Studio
- A Google API project (for Google Sign-In)

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/yourusername/NoteAppWithRoom.git
```
### 2. Open the project in Android Studio

```bash
cd NoteAppWithRoom
```
### 3. Set up Google Sign-In
You need to set up a project on Google API Console and get your OAuth 2.0 Client IDs. Follow the steps here to set up Google Sign-In.
-  Add your OAuth credentials to the google-services.json file in the project
### 4. Run the App
-  Build and run the project on an emulator or physical device.
```bash
app
├── java/com/example/noteappwithroom
│   ├── MainActivity.kt              # The main and only activity
│   ├── LoginFragment.kt             # Fragment for Google Sign-In
│   ├── NoteListFragment.kt          # Fragment to display notes using RecyclerView
│   ├── AddNoteFragment.kt           # Fragment to add or update notes
│   ├── NoteAdapter.kt               # Adapter for RecyclerView to display notes
│   ├── Note.kt                      # Data class representing a Note entity
│   ├── NoteDao.kt                   # Data Access Object (DAO) for notes
│   ├── NoteDatabase.kt              # Room Database implementation
│   └── utils
│       └── PrefsManager.kt          # SharedPreferences manager for tracking logged-in user
├── res
│   ├── layout/                      # XML files for app layout
│   └── values/                      # Strings, colors, dimensions
├── AndroidManifest.xml              # App manifest file
└── google-services.json             # Google Sign-In configuration
```

### How It Works
### Google Sign-In
The app uses the GoogleSignIn API for user authentication. After a successful login, the app saves the user's information in SharedPreferences to keep track of the logged-in user.

### Room Database
Notes are stored locally using Room, which is an abstraction layer over SQLite. The Note entity has fields like id, text, and userId. The userId field ensures that each user can only access their own notes.

### Fragments
-  LoginFragment: The login screen where users can sign in using their Google account.
-  NoteListFragment: The screen that displays the notes of the logged-in user.
-  AddNoteFragment: Allows users to add or update their notes.
-  RecyclerView & Adapter
The NoteAdapter is used to populate the RecyclerView with the notes from the Room database. Users can also delete or update notes directly from this list.

### Room Database Schema Migration
The database schema supports migration to handle changes in the app's data structure. If the schema is updated, Room ensures data consistency by using migrations. Here's how the migration is set up:
```bash
kotlin
Copy code
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE notes ADD COLUMN userId TEXT NOT NULL DEFAULT ''")
    }
}
```
### Contributing
-  Fork the project
-  Create your feature branch (git checkout -b feature/YourFeature)
-  Commit your changes (git commit -m 'Add YourFeature')
-  Push to the branch (git push origin feature/YourFeature)
-  open a pull request
