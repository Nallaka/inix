package me.nallaka.inixbot.main.commandmeta;

import me.nallaka.inixbot.main.BotMain;
import me.nallaka.inixbot.main.permissionmeta.PermissionLevel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandHandler extends ListenerAdapter {
    private CommandRegistry commandRegistry = new CommandRegistry();

    private boolean isCommand(MessageReceivedEvent event, String[] commandArgs) {
        return event.getAuthor().getJDA().getSelfUser() != event.getAuthor() && commandRegistry.containsKey(commandArgs[0].toLowerCase()) && !commandArgs[0].equalsIgnoreCase("help");
    }

    private boolean isHelpCommand(MessageReceivedEvent event, String[] commandArgs) {
        return event.getAuthor().getJDA().getSelfUser() != event.getAuthor() && commandArgs[0].equalsIgnoreCase("help");
    }

    private boolean userHasPermission(MessageReceivedEvent event, String[] commandArgs, PermissionLevel permissionLevel) {
        //TODO: Create User map on start up and check map for permission level
        return true;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String beheadedCommand;
        if (event.getMessage().getContent().startsWith(BotMain.COMMAND_PREFIX)) {
            beheadedCommand = event.getMessage().getContent().replaceFirst(BotMain.COMMAND_PREFIX, "").toLowerCase();
            String[] commandArgs = beheadedCommand.split("\\s");

            executeCommand(event, commandArgs);
        } else if(event.getMessage().getContent().startsWith(BotMain.COMMAND_DEFAULT_PREFIX)) {
            beheadedCommand = event.getMessage().getContent().replaceFirst(BotMain.COMMAND_DEFAULT_PREFIX, "").toLowerCase();
            String[] commandArgs = beheadedCommand.split("\\s");

            executeCommand(event, commandArgs);
        }

    }

    private void executeCommand(MessageReceivedEvent event, String[] commandArgs) {
        if (isCommand(event, commandArgs) || isHelpCommand(event, commandArgs) && commandArgs.length == 1) {
            commandRegistry.getCommand(commandArgs[0]).runCommand(event, commandArgs);
            commandRegistry.getCommand(commandArgs[0]).executed(event, commandArgs);
        } else if (isHelpCommand(event, commandArgs) && commandArgs.length >= 1) {
            commandRegistry.getCommand(commandArgs[1]).runHelpCommand(event, commandArgs);
            commandRegistry.getCommand(commandArgs[0]).executed(event, commandArgs);
        }
    }


}
