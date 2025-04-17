# Fabric Example Mod

## Setup

1. Go to https://wiki.fabricmc.net/tutorial:setup and follow the recommended JDK and IDE setup instructions.
    For VSCode setup, refer to:             
    https://wiki.fabricmc.net/tutorial:setup:vscode.

2. Open VSCode, then go to:
    File > Open Folder → Select the fabric-example-mod-1.21 folder.

3. In the left sidebar, click on Extensions and search for:
    "Language Support for Java(TM) by Red Hat" → Install.

4. Search for and install the following extensions:
    "Gradle for Java"
    "Debugger for Java"

5. Open the terminal by going to:
View > Terminal → Then run the command: ./gradlew vscode

6. After setup completes (BUILD SUCCESSFUL), click on Run and Debug in the left sidebar.

7. In the top-left dropdown menu (RUN AND DEBUG), select "Minecraft Client" and run it.
    The Minecraft client will launch → Click Continue.

8. Go to Singleplayer:
You can either create a new world (make sure "Allow Commands" is set to ON)
Or load an existing world (e.g., "New World").

9. In the game, use the following command to obtain different Logic Gate blocks:
    /give @a mymod:objectname

## License

This template is available under the CC0 license. Feel free to learn from it and incorporate it in your own projects.
