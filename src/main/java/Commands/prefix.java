package Commands;

import Functions.DatabaseParameters;
import Functions.DatabaseHandles;
import Functions.Commander;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;

public class prefix extends Command {
    private final JDA jda;
    public prefix (JDA jda){
        this.jda = jda;
        this.userPermissions = new Permission[]{Permission.ADMINISTRATOR};
        this.name = "prefix";
        this.help = "Changes my prefix";
        this.guildOnly = true;
        this.hidden = false;
    }
    @Override
    protected void execute(CommandEvent e){
        if(!e.getChannel().toString().equals(DatabaseParameters.getChannelID())){
            return;
        }

        String message [] = e.getMessage().getContentRaw().split(" ");

        // Single command input
        if(message.length == 1){
            e.reply(DatabaseParameters.getBotPrefix() + "prefix <desired prefix> to change my command prefix");
        }
        else if(message.length == 2){
            DatabaseParameters.setBotPrefix(message[1]);
            DatabaseHandles.FunctionRefresh();
            e.reply("Prefix was set to: " + DatabaseParameters.getBotPrefix());
            Commander.commander(jda);
        }
    }
}
