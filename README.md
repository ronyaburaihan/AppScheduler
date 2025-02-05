# App Scheduler

App Scheduler is an Android application that allows users to schedule the launch of any installed app at a specific date & time. It follows the Clean Architecture pattern to ensure scalability and maintainability. It uses Broadcast Receiver, Foreground Service, and Overlay Permission for smooth background execution.

---

## Features 

- **Schedule Any Installed App**: Users can schedule any app to launch at a set date & time.
- **Cancel Schedule**: Users can cancel the scheduled launch if an app has not started yet.
- **Reschedule App**: Users can modify an existing schedule to a new time before launch.
- **Multiple Schedules**: Supports multiple app schedules without time conflicts.
- **Execution Tracking**: Keeps a record of scheduled apps to check whether they were successfully executed.
- **Background Execution**: Uses BroadcastReceiver and ForegroundService to ensure reliability.
- **Schedule Log**: Uses Room database for store schedule records and execution logs

---

## Tech Stack

- **Kotlin** - Modern Android development language
- **Jetpack Compos** - UI built using declarative Compose framework
- **Foreground Service** - Ensures scheduled tasks run in the background
- **Broadcast Receiver** - Listens for scheduled events and triggers app launch
- **Room Database** - Stores schedule records and execution logs
- **Koin** - Dependency Injection

---

### Project Setup

1. Clone the repository
```properties

https://github.com/ronyaburaihan/AppScheduler
```
2. Open in Android Studio
3. Run on an Android Emulator or Physical Device
