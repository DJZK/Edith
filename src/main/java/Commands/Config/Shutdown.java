package Commands.Config;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;

public class Shutdown extends Command {
    public Shutdown(){
        this.userPermissions = new Permission[]{Permission.ADMINISTRATOR};
        this.name = "shutdown";
        this.help = "Shutdown the application";
        this.guildOnly = true;
        this.hidden = false;
    }

    @Override
    protected void execute(CommandEvent event){
        System.exit(0);
    }
}
