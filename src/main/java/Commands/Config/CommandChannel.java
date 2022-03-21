package Commands.Config;

import Functions.DatabaseHandles;
import Functions.DatabaseParameters;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;

public class CommandChannel extends Command {
    public CommandChannel(){
        this.userPermissions = new Permission[]{Permission.ADMINISTRATOR};
        this.name = "channel";
        this.help = "Changes my target channel for commands";
        this.guildOnly = true;
        this.hidden = false;
    }

    @Override
    protected void execute(CommandEvent e){
        String message [] = e.getMessage().getContentRaw().split(" ");

        // Single command input
        if(message.length == 1){
            e.reply(DatabaseParameters.getBotPrefix() + "channel <text channel ID> to change my target channel");

        }
        else if(message.length == 2){
            DatabaseParameters.setChannelID(message[1]);
            DatabaseHandles.FunctionRefresh();
            e.reply("My target channel was set to: <#" + DatabaseParameters.getChannelID() + ">");
        }
    }
}
