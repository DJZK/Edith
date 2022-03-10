package Commands;

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
    protected void execute(CommandEvent commandEvent) {

    }
}
