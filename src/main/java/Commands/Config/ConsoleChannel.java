package Commands.Config;

import Functions.DatabaseHandles;
import Functions.DatabaseParameters;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;

public class ConsoleChannel extends Command {
    public ConsoleChannel() {
        this.userPermissions = new Permission[]{Permission.ADMINISTRATOR};
        this.name = "console";
        this.aliases = new String[] {"condest", "consolechannel"};
        this.help = "Changes my target channel for host console commands";
        this.guildOnly = true;
        this.hidden = false;
    }

    @Override
    protected void execute(CommandEvent e) {
        String message[] = e.getMessage().getContentRaw().split(" ", 2);

        // Single command input
        if (message.length == 1) {
            e.reply(DatabaseParameters.getBotPrefix() + "console <text channel ID> to change my target channel");

        } else if (message.length == 2) {
            DatabaseParameters.setConsoleChannel(message[1]);
            DatabaseHandles.FunctionRefresh();
            e.reply("My target channel for console was set to: <#" + DatabaseParameters.getConsoleChannel() + ">");

        }
    }
}
