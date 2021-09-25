# :gronk:*ify*

Because building your project is otherwise too boring.

Gronkify is a Gradle plugin that plays music
(specifically, Kevin MacLeod's "Le Grand Chase")
during builds.

You can add it to your `plugins` block (preferably before other plugins for
maximal playtime):

```diff
  plugins {
+     id("io.github.juuxel.gronkify") version "1.0.1"
  }
```

You also need to add the Cotton repository to your plugin repositories
in settings.gradle(.kts):

```diff
  pluginManagement {
      repositories {
+         maven {
+             name = 'Cotton'
+             url = 'https://server.bbkr.space/artifactory/libs-release/'
+         }
          gradlePluginPortal()
      }
  }
```
