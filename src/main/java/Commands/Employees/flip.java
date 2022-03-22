package Commands.Employees;

import Functions.DatabaseHandles;
import Functions.DatabaseParameters;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;

public class flip extends Command {
    public flip(){
        this.name = "flip";
        this.aliases = new String[] {"invert"};
        this.userPermissions = new Permission[]{Permission.ADMINISTRATOR};
        this.help = "Inverts the boolean function of user";
        this.guildOnly = true;
        this.hidden = false;
    }

    @Override
    protected void execute (CommandEvent e){
        String message[] = e.getMessage().getContentRaw().split(" ", 3);

        // Not more than 2 params
        if(message.length < 2){
            e.replyInDm(DatabaseParameters.getBotPrefix() + "flip <UserID> <A/A> \nA = LoggedOn B = OnBreak");
            return;
        }

        // Not in the user base
        DatabaseHandles io = new DatabaseHandles();
        if(io.findUser(message[1]).equals("")){
            e.replyInDm("The user is not in the database");
            if(!e.getMessage().getTextChannel().getId().equals(DatabaseParameters.getChannelID())){
                e.getMessage().delete().queue();
            }
            return;
        }

        // Not in the designated channel
        if(!e.getMessage().getTextChannel().getId().equals(DatabaseParameters.getChannelID())){
            e.getMessage().delete().queue();
            e.getJDA().getTextChannelById(DatabaseParameters.getChannelID()).sendMessage("Do that here! " + e.getAuthor().getAsMention()).queue();
            return;
        }


    }
}
