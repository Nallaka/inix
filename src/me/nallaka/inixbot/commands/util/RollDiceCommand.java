package me.nallaka.inixbot.commands.util;

import me.nallaka.inixbot.main.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RollDiceCommand implements Command {

    @Override
    public boolean called(MessageReceivedEvent event, String[] args) {
        return false;
    }

    @Override
    public void runCommand(MessageReceivedEvent event, String[] args) {
        int diceArg = Integer.parseInt(args[1]);
        embeddedMessageBuilder.setTitle("Dice Roll :game_die:");
        if (diceArg < 1000) {
            embeddedMessageBuilder.addField("You Rolled: :game_die:", Double.toString(Math.random() * Integer.parseInt(args[1])), true);
        } else {
            embeddedMessageBuilder.addField("ERROR:", "Dice value too high. Please try again", true );
        }
        messageHandler.sendMessage(event, embeddedMessageBuilder);
        messageHandler.clearEmbeddedBuilder(embeddedMessageBuilder);
    }

    @Override
    public void executed(MessageReceivedEvent event, String[] args) {

    }
}