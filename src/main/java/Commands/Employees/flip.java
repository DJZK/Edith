package Commands.Employees;

import Functions.DatabaseHandles;
import Functions.DatabaseParameters;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;

import java.util.Locale;

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
            e.replyInDm(DatabaseParameters.getBotPrefix() + "flip <UserID> <A/B> \nA = LoggedOn B = OnBreak");
            return;
        }

        // ID should be integer only
        if(!message[1].matches("[0-9]*") || message[1].matches(" ") || message[1].matches("[a-zA-Z ]*")){
            e.replyInDm("Please enter a valid user ID number");
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

        // Actions
        switch (message[2].charAt(0)){
            case 'A':
            case 'a':
                io.updateEligibility(message[1], 'a');
                e.replyInDm(io.findUser(message[1]) + "'s LoggedOn status has been flipped!");
                break;
            case 'B':
            case 'b':
                io.updateEligibility(message[1], 'b');
                e.replyInDm(io.findUser(message[1]) + "'s OnBreak status has been flipped!");
                break;
            default:
                e.replyInDm(DatabaseParameters.getBotPrefix() + "flip <UserID> <A/B> \nA = LoggedOn B = OnBreak");
                break;
        }


    }
}
