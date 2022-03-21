package Commands.Config;

import Functions.DatabaseHandles;
import Functions.DatabaseParameters;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;

public class SudoPass extends Command {
    public SudoPass(){
        this.userPermissions = new Permission[]{Permission.ADMINISTRATOR};
        this.name = "sudo";
        this.aliases = new String[] {"sudopass", "spass", "pass"};
        this.help = "changes the sudo command for the console";
        this.guildOnly = false;
        this.hidden = false;
    }

    @Override
    protected void execute(CommandEvent e) {
        String message[] = e.getMessage().getContentRaw().split(" ", 2);

        // Single command input
        if (message.length == 1) {
            e.reply(DatabaseParameters.getBotPrefix() + "sudo <current user password> to change my credential for password");

        } else if (message.length == 2) {

            DatabaseParameters.setSudoPass(message[1]);
            DatabaseHandles.FunctionRefresh();
            e.getMessage().delete().queue();
            e.reply("My login credentials has been changed.");
        }
    }
}
