package de.poweruser.powerserver.commands;

import java.util.HashMap;
import java.util.Map;

import de.poweruser.powerserver.logger.LogLevel;
import de.poweruser.powerserver.logger.Logger;

public class CommandRegistry {

    private Map<String, CommandInterface> commandMap;

    public CommandRegistry() {
        this.commandMap = new HashMap<String, CommandInterface>();
    }

    public void register(CommandBase command) {
        this.commandMap.put(command.getCommandString(), command);
    }

    public void issueCommand(String line) {
        if(line == null || line.trim().isEmpty()) { return; };
        String formated = line.trim().replaceAll("\\s+", " ");
        String[] items = formated.split(" ");
        String[] arguments = new String[0];
        if(items.length > 1) {
            arguments = new String[items.length - 1];
            System.arraycopy(items, 1, arguments, 0, items.length - 1);
        }
        String commandString = items[0];
        if(this.commandMap.containsKey(commandString)) {
            Logger.logStatic(LogLevel.VERY_LOW, "Entered command: '" + formated + "'");
            CommandInterface com = this.commandMap.get(commandString);
            if(!com.handle(arguments)) {
                com.showCommandHelp();
            }
        } else {
            Logger.logStatic(LogLevel.VERY_LOW, "Unknown command '" + commandString + "'. Type 'commands' to get a list of all available commands.");
        }
    }
}
