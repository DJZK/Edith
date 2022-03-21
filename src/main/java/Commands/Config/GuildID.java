package Commands.Config;

import Functions.DatabaseHandles;
import Functions.DatabaseParameters;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;

public class GuildID extends Command {
    public void GuildID(){
        this.name = "guildid";
        this.aliases = new String[] {"guid", "serverid", "servid"};
        this.userPermissions = new Permission[]{Permission.ADMINISTRATOR};
        this.help = "Updates Guild ID in Database!";
        this.guildOnly = true;
        this.hidden = false;
    }

    @Override
    protected void execute(CommandEvent event){
        DatabaseHandles io = new DatabaseHandles();

        DatabaseParameters.setGuildID(event.getGuild().getId());
        io.FunctionRefresh();
        event.reply("Guild ID has been updated!");

    }
}
